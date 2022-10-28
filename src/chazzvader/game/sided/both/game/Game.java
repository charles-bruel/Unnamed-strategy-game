package chazzvader.game.sided.both.game;

import java.util.ArrayList;
import java.util.Random;

import chazzvader.game.content.Tile;
import chazzvader.game.content.civilizations.startbias.StartBias;
import chazzvader.game.content.mapgen.MapGenerator;
import chazzvader.game.content.units.Unit;
import chazzvader.game.content.units.Units;
import chazzvader.game.input.InputHelper;
import chazzvader.game.other.Console;
import chazzvader.game.other.Coordinate;
import chazzvader.game.other.SubTileCoordinate.SubTile;
import chazzvader.game.sided.both.comm.protocol.ProtocolManager;
import chazzvader.game.sided.both.game.map.Map;
import chazzvader.game.sided.both.game.map.MapSize;
import chazzvader.game.sided.server.comm.InternalCommServer;

public class Game {
	
	private Random rand;
	private int turn = 1;
	private Map map;
	private ArrayList<Player> players;
	private ArrayList<Unit> units;
	
	private Game(ArrayList<Player> players, Class<?> mapGen, int width, int height) {
		rand = new Random();
		if(players.size() >= 63) Console.error(new IllegalArgumentException(), true);
		try {
			Object mg = mapGen.newInstance();
			if(!(mg instanceof MapGenerator)) {
				Console.error(new IllegalArgumentException(), true);
			}
			this.map = ((MapGenerator) mapGen.newInstance()).generate(width, height);
		} catch (InstantiationException e) {
			Console.error(e, true);
		} catch (IllegalAccessException e) {
			Console.error(e, true);
		}
		this.players = players;
		for(int i = 0;i < players.size();i ++) {
			players.get(i).setup(this);
		}
		units = new ArrayList<Unit>();
	}
	
	public Game(Class<?> mapGen, MapSize ms) {
		this(defaultPlayerConfig(ms.getPlayers()), mapGen, ms.getWidth(), ms.getHeight());
	}
	
	public Game() {
		units = new ArrayList<Unit>();
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	/**
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	private static ArrayList<Player> defaultPlayerConfig(int pc) {
		ArrayList<Player> p = new ArrayList<Player>(pc);
		p.add(0, new Player(new InternalCommServer()));
		for(int i = 1;i < pc;i ++) {
			
		}
		return p;
	}

	public Unit getUnitById(int uid) {
		Unit u = units.get(uid);
		if(u.getUID() == uid) {
			return u;
		}
		for(int i = 0;i < units.size();i ++) {
			if(units.get(i).getUID() == uid) {
				return units.get(i);
			}
		}
		return null;
	}
	
	/**
	 * @return the turn
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * @param turn the turn to set
	 */
	public void setTurn(int turn) {
		this.turn = turn;
	}
	
	public void nextTurn() {
		this.turn++;
	}

	public ArrayList<Unit> getUnits() {
		return units;
	}

	/**
	 * @param units the units to set
	 */
	public void setUnits(ArrayList<Unit> units) {
		this.units = units;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public int getNextUID() {
		for(int i = 0;i < units.size();i ++) {
			if(units.get(i) == null) {
				return i;
			}
		}
		return units.size();
	}
	
	/**
	 * Organizes Unit list
	 */
	public void fixUIDs() {
		int tl = 0;
		for(int i = 0;i < units.size();i ++) {
			if(units.get(i) != null) {
				tl++;
			}
		}
		if(tl > ProtocolManager.MAX_UNIT_NUMBER) {
			tl = ProtocolManager.MAX_UNIT_NUMBER;
		}
		int ci = 0;
		ArrayList<Unit> newArray = new ArrayList<>(tl);
		for(int i = 0;i < tl;i ++) {//Fill with null
			newArray.add(null);
		}
		for(int i = 0;i < units.size();i ++) {
			if(units.get(i) != null) {
				if(i >= ProtocolManager.MAX_UNIT_NUMBER) {
					break;
				}
				Unit u = units.get(i);
				u.setUID(ci);
				newArray.set(ci, u);
				ci++;
			}
		}
		units = newArray;
	}

	/**
	 * @return the rand
	 */
	public Random getRand() {
		return rand;
	}

	public void newRound() {
		for(int i = 0;i < units.size();i ++) {
			Unit u = units.get(i);
			if(u != null) {
				u.setMovement(u.getMaxMovement());
			}
		}
	}

	public void placePlayers() {
		for(int i = 0;i < players.size();i ++) {
			placePlayer(players.get(i));
		}
	}
	
	public void placePlayer(Player player) {
		int startingVisibilityRadius = 2;//TODO: 5 Something make these editable
		int considerSize = 5;
		int randomnessFactor = 7;
		ArrayList<StartBias> startBias = player.getCivilization().getStartBias();
		int[][] weights = new int[map.getWidth()][map.getHeight()];
		for(int x = 0;x < weights.length;x ++) {
			for(int y = 0;y < weights[0].length;y ++) {
				Tile t = map.getTiles()[x][y];
				if(!t.isPassable() || !t.isLand()) {
					weights[x][y] = Integer.MIN_VALUE;
					continue;
				}
				int currentWeight = 0;
				ArrayList<Coordinate> tiles = new ArrayList<Coordinate>(1);
				tiles.add(new Coordinate(x, y));
				for(int j = 0;j < considerSize;j ++) {
					tiles = InputHelper.adjacent(tiles, map.getWidth(), map.getHeight());
					for(int k = 0;k < tiles.size();k ++) {
						Coordinate c = tiles.get(k);
						Tile t2 = map.getTiles()[c.getX()][c.getY()];
						for(int l = 0;l < startBias.size();l ++) {
							int result = startBias.get(l).bias(t2).getNumber()*startBias.get(l).level();
							currentWeight += result;
						}
					}
				}
				weights[x][y] = currentWeight;
			}
		}
		int maxWeight = Integer.MIN_VALUE;
		for(int x = 0;x < weights.length;x ++) {
			for(int y = 0;y < weights[0].length;y ++) {
				if(weights[x][y] > maxWeight) {
					maxWeight = weights[x][y];	
				}
			}
		}
		int currentMaxWeight = Integer.MIN_VALUE;
		Coordinate target = null;
		for(int x = 0;x < weights.length;x ++) {
			for(int y = 0;y < weights[0].length;y ++) {
				int currentWeight;
				if(maxWeight > 0) {
					currentWeight = (int) (weights[x][y] + rand.nextInt(maxWeight*randomnessFactor) - maxWeight*(randomnessFactor/2.0));
				} else {
					currentWeight = weights[x][y];
				}
				if(weights[x][y] > currentMaxWeight) {
					currentMaxWeight = currentWeight;
					target = new Coordinate(x, y);
				}
			}
		}
		units.add(Units.MILITARY.axeman(player, target.getX(), target.getY(), SubTile.CENTER, getNextUID()));
		ArrayList<Coordinate> startingVisibility = new ArrayList<Coordinate>(1);
		startingVisibility.add(target);
		for(int j = 0;j < startingVisibilityRadius;j ++) {
			startingVisibility = InputHelper.adjacent(startingVisibility, map.getWidth(), map.getHeight());
		}
		player.setDiscovered(startingVisibility);
	}
	
}