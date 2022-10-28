package chazzvader.game.sided.both.comm.protocol;

import java.util.ArrayList;
import java.util.LinkedList;

import chazzvader.game.content.features.Feature;
import chazzvader.game.content.features.Features;
import chazzvader.game.content.manager.ContentBaseGame;
import chazzvader.game.content.resources.Resource;
import chazzvader.game.content.resources.Resources;
import chazzvader.game.content.tiles.Tile;
import chazzvader.game.content.units.Unit;
import chazzvader.game.content.units.Units;
import chazzvader.game.other.Console;
import chazzvader.game.other.Coordinate;
import chazzvader.game.other.SubTileCoordinate.SubTile;
import chazzvader.game.sided.both.game.Entity;
import chazzvader.game.sided.both.game.Game;
import chazzvader.game.sided.both.game.Player;
import chazzvader.game.sided.both.game.map.Map;
//TODO: Proper javadocs for all of these
/**
 * Class that manages server client communication protocol. Provides many
 * methods to decode messages as well as a javadoc description of the entire
 * protocol<br>
 * <br>
 * Servers communicate through text. The protocol will be set up as follows:
 * <b>COMMAND;PARAMETER1:PARAMETER2......</b><br>
 * Or just (For simpler or more terse things): <br>
 * <b>COMMAND;PARAMETERS</b><br>
 * No parameters is just the command name.<br>
 * <b>COMMAND</b> <br>
 * Commands:<br>
 * <ol>
 * <li><b>GAME_S</b>: Command to signal start of game data transmission. No
 * parameters</li>
 * <li><b>GAME_E</b>: Command to signal end of game data transmission. No
 * parameters</li>
 * <li><b>MAP_S</b>: Command to signal start of map transmission. No
 * parameters</li>
 * <li><b>MAP_D</b>: Sets the map size. Two parameters, both <b>int</b>s. The
 * two parameters multiplied together should equal the amount of <b>MAP_I</b>
 * commands.</li>
 * <li><b>MAP_I</b>: Takes 1 parameter, 10 ascii characters. Using DataEncoder,
 * convert the 10 characters into 7 bits each for a total of 70. Bits 1-5 are
 * for the terrain (all standard terrain types and 1 special type for natural
 * wonders and another for undiscovered). Bits 6-9 are for features. Bits 10-14
 * and 59 (I realized I needed more and didn't want to rewrite the javadoc) are
 * for resources. Bits 15-28 specify the city (details later). Bits 29-34 and
 * 59-70 are for building metadata. Bits 45-52 are for buildings. Bits 53-58 are
 * for rivers. All other bits are unused. Optional parameters for position, used
 * ONLY when updating.</li>
 * <li><b>MAP_W</b>: Does the map wrap?</li>
 * <li><b>MAP_E</b>: Command to signal end of map transmission. No
 * parameters</li>
 * <li><b>UNIT_S</b>: Command to signal start of unit data transmission. No
 * parameters
 * <li><b>UNIT_I</b>: Command for unit. Takes 1 parameter, 11 ascii characters.
 * Using DataEncoder, convert the 11 characters into 7 bits each for a total of
 * 77. Bits 1-3 is for unit class. Bits 4-10 is unit type. Bits 11-14 is unit
 * charges (generic - can be for anything, depends on the unit). Bits 15-22 is
 * unit owner. Bits 23-29 is unit xp (again - if applicable). Bits 30-39 is unit
 * health. Bits 30-41 are unit x position and bits 42-52 are unit y position.
 * Bits 53 - 65 are unit id. Bits 66 - 69 are for unit subtile position. Bits
 * 70-77 are for unit movement left.</li>
 * <li><b>UNIT_E</b>: Command to signal end of unit data transmission. No
 * parameters</li>
 * <li><b>NEXT_TURN</b>: Command to signal end of turn</li>
 * <li><b>TURN</b>: Tells what the current turn is, 1 parameter, the turn as an
 * plain text int
 * <li><b>UNIT_M</b>: Command for unit movement. First parameter is the unit ID
 * number. The 2nd and 3rd parameters are coordinate for the unit to go to and
 * the 4th parameter is unit subtile. It is up to the server to ensure the move
 * is possible./li>
 * <li><b>UPDATE</b>: Generic command to signal the next command will be an
 * update.</li>
 * </ol>
 * 
 * @author csbru
 * @version 1.0
 * @since 1.0
 */
public final class ProtocolManager {

	private ProtocolManager() {}

	public static final int BITS_PER_CHAR = 7;
	public static final int MAX_UNIT_NUMBER = 8192;
	public static final int TILE_DATA_LENGTH = 10;
	public static final int UNIT_DATA_LENGTH = 11;
	
	public static Entity entityFromProto(String msg) {
		return null;
	}
	
	public static String entityToProto(Entity entity) {
		return null;
	}

	/**
	 * Takes a list of messages, and converts them to a Game object
	 * 
	 * @param msgs The list of messages
	 * @return The Game object
	 */
	public static Game gameFromProto(LinkedList<String> msgs) {
		Game g = new Game();
		Console.print("(Protocol) Loading game", 0);
		Map m = null;
		ArrayList<Unit> u = null;
		while (!msgs.isEmpty()) {
			String s = msgs.peek();
			if (s.equalsIgnoreCase("MAP_S")) {
				m = mapFromProto(msgs);// the method handles all input to MAP_E, inclusive (and deletes it)
				continue;
			}
			if (s.equalsIgnoreCase("UNIT_S")) {
				u = unitsFromProto(msgs);// the method handles all input to UNIT_E, inclusive (and deletes it)
				continue;
			}
			msgs.pollFirst();// Remove gotten item
			if (s.equalsIgnoreCase("GAME_S"))
				continue;
			if (s.equalsIgnoreCase("GAME_E"))
				break;
			String[] sa = s.split(";");
			String c = sa[0];
			if (sa.length > 1) {
				sa = sa[1].split(":");
			}
			if (c.equalsIgnoreCase("TURN")) {
				g.setTurn(Integer.parseInt(sa[0]));// Turn
			}
		}
		g.setMap(m);
		g.setUnits(u);
		Console.print("(Protocol) Game loaded", 0);
		return g;
	}
	
	/*
	 * Transmit from item to proto
	 */
	public static LinkedList<String> gameToProto(Game g, Player p) {
		LinkedList<String> r = new LinkedList<>();
		r.addFirst("GAME_E");
		LinkedList<String> ta = mapToProto(g.getMap(), p);
		while (!ta.isEmpty()) {
			r.addFirst(ta.poll());
		}
		ta = unitsToProto(g.getUnits());
		while (!ta.isEmpty()) {
			r.addFirst(ta.poll());
		}
		r.addFirst("TURN;" + g.getTurn());
		r.addFirst("GAME_S");
		return r;
	}
	
	public static Map mapFromProto(LinkedList<String> msgs) {
		Map m = null;
		Console.print("(Protocol) Loading map", 0);
		int i = 0;
		while (!msgs.isEmpty()) {
			String s = msgs.pollFirst();
			String[] temp = s.split(";");
			String c = temp[0];
			if (temp.length == 2) {
				String[] p = temp[1].split(":");
				if (c.equalsIgnoreCase("MAP_S"))
					continue;
				if (c.equalsIgnoreCase("MAP_D")) {
					m = new Map(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
				}
				if (c.equalsIgnoreCase("MAP_W")) {
					m.setWrap(true);
				}
				if (c.equalsIgnoreCase("MAP_I") && m != null) {
					int x = i / m.getHeight();
					int y = i % m.getHeight();
					m.setTile(x, y, tileFromProto(p[0], new Coordinate(x, y)));
					i++;
				}
			}
		}
		m.mapWideUpdateYields(null);
		Console.print("(Protocol) Map loaded", 0);
		return m;
	}
	
	public static LinkedList<String> mapToProto(Map m, Player p) {
		LinkedList<String> r = new LinkedList<>();
		r.addFirst("MAP_S");
		r.addFirst("MAP_D;" + m.getWidth() + ":" + m.getHeight());
		if (m.isWrap())
			r.addFirst("MAP_W");
		Tile[][] ttt = m.getTiles();
		for (int i = 0; i < ttt.length; i++) {
			Tile[] tt = ttt[i];
			for (int j = 0; j < tt.length; j++) {
				Tile t = tt[j];
				r.addFirst(tileToProto(t, p));
			}
		}
		r.addFirst("MAP_E");
		return r;
	}

	public static String protoFromTileUpdate(Tile t) {
		return tileToProto(t, null)+":"+t.getCoordinate().getX()+":"+t.getCoordinate().getY();
	}

	/**
	 * Returns null if tile is undiscovered
	 * @param param
	 * @param ec
	 * @return
	 */
	public static Tile tileFromProto(String param, Coordinate ec) {
		if (param.contains("MAP_I;")) {
			param = param.substring(6);
		}
		if (param.length() != TILE_DATA_LENGTH) {
			return null;
		}
		boolean[] b = ProtocolAPI.stringToBits(param);// Everything
		boolean[] b1 = new boolean[] { b[0], b[1], b[2], b[3], b[4] };

		int tileId = ProtocolAPI.bitsToInt(b1);
		
		Tile t = ContentBaseGame.TILES.getByID(tileId, ec, null);

		if(t == null) {
			return t;
		}
		
		boolean[] b2 = new boolean[] { b[5], b[6], b[7], b[8] };

		Feature f = Features.fromIndex(ProtocolAPI.bitsToInt(b2));
		if (f != null) {
			t.addFeature(f, null);
		}

		boolean[] b3 = new boolean[] { b[9], b[10], b[11], b[12], b[13], b[58] };

		Resource r = Resources.fromIndex(ProtocolAPI.bitsToInt(b3));
		if (r != null) {
			t.addResource(r, null);
		}

		boolean[] b4 = new boolean[] { b[52], b[53], b[54], b[55], b[56], b[57] };
		t.setRivers(b4);

		return t;
	}

	public static String tileToProto(Tile t, Player p) {
		boolean[] b = new boolean[TILE_DATA_LENGTH * BITS_PER_CHAR];
		/*if(p != null && !p.getDiscovered()[t.getCoordinate().getX()][t.getCoordinate().getY()]) {
			for(int i = 0;i < b.length;i ++) {
				b[i] = false;
			}
		} else */{
			boolean[] b1 = ProtocolAPI.intToBits(ContentBaseGame.TILES.getIDByObject(t), true, 5);
			b[0] = b1[0];
			b[1] = b1[1];
			b[2] = b1[2];
			b[3] = b1[3];
			b[4] = b1[4];
			boolean[] b2 = ProtocolAPI.intToBits(Features.toIndex(t.getFeature()), true, 4);
			b[5] = b2[0];
			b[6] = b2[1];
			b[7] = b2[2];
			b[8] = b2[3];
			boolean[] b3 = ProtocolAPI.intToBits(Resources.toIndex(t.getResource()), true, 6);
			b[9] = b3[0];
			b[10] = b3[1];
			b[11] = b3[2];
			b[12] = b3[3];
			b[13] = b3[4];
			b[58] = b3[5];
			boolean[] b4 = t.getRivers();
			b[52] = b4[0];
			b[53] = b4[1];
			b[54] = b4[2];
			b[55] = b4[3];
			b[56] = b4[4];
			b[57] = b4[5];
		}
		return "MAP_I;" + ProtocolAPI.bitsToString(b);
	}
	
	public static Unit unitFromProto(String param) {
		if (param.contains("UNIT_I;")) {
			param = param.substring(7);
		}
		if (param.length() != UNIT_DATA_LENGTH) {
			return null;
		}
		boolean[] b = ProtocolAPI.stringToBits(param);
		boolean[] b1 = new boolean[] { b[0], b[1], b[2] };// Unit class
		boolean[] b2 = new boolean[] { b[3], b[4], b[5], b[6], b[7], b[8], b[9] };// Unit type
		boolean[] b3 = new boolean[] { b[29], b[30], b[31], b[32], b[33], b[34], b[35], b[36], b[37], b[38], b[39],
				b[40] };// XPOS
		boolean[] b4 = new boolean[] { b[41], b[42], b[44], b[44], b[45], b[46], b[47], b[48], b[49], b[50], b[51] };// YPOS
		boolean[] b5 = new boolean[] { b[52], b[53], b[54], b[55], b[56], b[57], b[58], b[59], b[60], b[61], b[62], // UID
				b[63], b[64] };
		boolean[] b6 = new boolean[] { b[65], b[66], b[67], b[68] }; // Subtile pos
		boolean[] b7 = new boolean[] { b[69], b[70], b[71], b[72], b[73], b[74], b[75], b[76] }; // Movement
		int unitClass = ProtocolAPI.bitsToInt(b1);
		int unitType = ProtocolAPI.bitsToInt(b2);
		int xpos = ProtocolAPI.bitsToInt(b3);
		int ypos = ProtocolAPI.bitsToInt(b4);
		int uid = ProtocolAPI.bitsToInt(b5);
		int subtileID = ProtocolAPI.bitsToInt(b6);
		int movement = ProtocolAPI.bitsToInt(b7);
		Unit u = Units.fromIndex(unitClass).fromIndex(unitType, null, xpos, ypos, SubTile.fromID(subtileID), uid);
		u.setMovement(movement);
		return u;
	}

	/**
	 * Takes a list of messages, and converts them to an Arraylist of Units
	 * 
	 * @param msgs The list of messages
	 * @return The Arraylist of Units
	 */
	public static ArrayList<Unit> unitsFromProto(LinkedList<String> msgs) {
		ArrayList<Unit> u = new ArrayList<Unit>();
		Console.print("Units Loading", 0);
		while (!msgs.isEmpty()) {
			String s = msgs.pollFirst();
			String[] temp = s.split(";");
			String c = temp[0];
			if (c.equalsIgnoreCase("UNIT_E"))
				return u;
			if (c.equalsIgnoreCase("UNIT_S"))
				continue;
			if (temp.length == 2) {
				String[] p = temp[1].split(":");// Get command and parameters
				if (c.equalsIgnoreCase("UNIT_I")) {
					u.add(unitFromProto(p[0]));// Does all the work
				}
			}
		}
		Console.print("Units Loaded", 0);
		return u;
	}

	public static LinkedList<String> unitsToProto(ArrayList<Unit> units) {
		LinkedList<String> r = new LinkedList<>();
		r.addFirst("UNIT_S");
		for (int i = 0; i < units.size(); i++) {
			r.addFirst(unitToProto(units.get(i)));
		}
		r.addFirst("UNIT_E");
		return r;
	}

	public static String unitToProto(Unit unit) {
		boolean[] b = new boolean[UNIT_DATA_LENGTH * BITS_PER_CHAR];
		boolean[] b1 = ProtocolAPI.intToBits(Units.toUpperIndex(unit), true, 3);
		b[0] = b1[0];
		b[1] = b1[1];
		b[2] = b1[2];
		boolean[] b2 = ProtocolAPI.intToBits(Units.fromIndex(Units.toUpperIndex(unit)).toIndex(unit), true, 7);// Unit																								// type
		b[3] = b2[0];
		b[4] = b2[1];
		b[5] = b2[2];
		b[6] = b2[3];
		boolean[] b3 = ProtocolAPI.intToBits(unit.getX(), true, 12);
		b[29] = b[0];
		b[30] = b3[1];
		b[31] = b3[2];
		b[32] = b3[3];
		b[33] = b3[4];
		b[34] = b3[5];
		b[35] = b3[6];
		b[36] = b3[7];
		b[37] = b3[8];
		b[38] = b3[9];
		b[39] = b3[10];
		b[40] = b3[11];
		boolean[] b4 = ProtocolAPI.intToBits(unit.getY(), true, 11);
		b[41] = b4[0];
		b[42] = b4[1];
		b[43] = b4[2];
		b[44] = b4[3];
		b[45] = b4[4];
		b[46] = b4[5];
		b[47] = b4[6];
		b[48] = b4[7];
		b[49] = b4[8];
		b[50] = b4[9];
		b[51] = b4[10];
		boolean[] b5 = ProtocolAPI.intToBits(unit.getUID(), true, 13);
		b[52] = b5[0];
		b[53] = b5[1];
		b[54] = b5[2];
		b[55] = b5[3];
		b[56] = b5[4];
		b[57] = b5[5];
		b[58] = b5[6];
		b[59] = b5[7];
		b[60] = b5[8];
		b[61] = b5[9];
		b[62] = b5[10];
		b[63] = b5[11];
		b[64] = b5[12];
		boolean[] b6 = ProtocolAPI.intToBits(SubTile.toID(unit.getPos().getSubTile()), true, 4);
		b[65] = b6[0];
		b[66] = b6[1];
		b[67] = b6[2];
		b[68] = b6[3];
		boolean[] b7 = ProtocolAPI.intToBits(unit.getMovement(), true, 8);
		b[69] = b7[0];
		b[70] = b7[1];
		b[71] = b7[2];
		b[72] = b7[3];
		b[73] = b7[4];
		b[74] = b7[5];
		b[75] = b7[6];
		b[76] = b7[7];
		return "UNIT_I;" + ProtocolAPI.bitsToString(b);
	}

	public static void updateTile(String s, Map map, Player p) {
		String[] sa = s.split(":");
		int x = Integer.parseInt(sa[1]);
		int y = Integer.parseInt(sa[2]);
		Tile t = tileFromProto(sa[0], new Coordinate(x, y));
		t.setCoordinate(new Coordinate(x, y));
		map.setTile(x, y, t);
		t.setMap(map);
		t.updateYields(p);
	}

	public static void updateUnit(String s, ArrayList<Unit> units) {
		Unit u = unitFromProto(s);
		int ti = -1;
		for (int i = 0; i < units.size(); i++) {
			if (units.get(i).getUID() == u.getUID()) {
				ti = i;
			}
		}
		if (ti == -1) {
			units.add(u);
		} else {
			units.set(ti, u);
		}
	}

}