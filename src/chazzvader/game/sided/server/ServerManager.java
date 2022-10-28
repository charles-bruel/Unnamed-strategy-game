package chazzvader.game.sided.server;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import chazzvader.game.content.Tile;
import chazzvader.game.content.mapgen.MapGenStandard;
import chazzvader.game.content.units.Unit;
import chazzvader.game.input.InputHelper;
import chazzvader.game.other.Console;
import chazzvader.game.other.Coordinate;
import chazzvader.game.other.SubTileCoordinate;
import chazzvader.game.other.SubTileCoordinate.SubTile;
import chazzvader.game.sided.both.comm.ExternalComm;
import chazzvader.game.sided.both.comm.protocol.ProtocolAPI;
import chazzvader.game.sided.both.comm.protocol.ProtocolManager;
import chazzvader.game.sided.both.game.Entity;
import chazzvader.game.sided.both.game.Game;
import chazzvader.game.sided.both.game.Player;
import chazzvader.game.sided.both.game.map.MapSize;
import chazzvader.game.sided.both.game.pathing.Path;

public class ServerManager implements Runnable {

	private final boolean INTERNAL;
	private Game game;
	private Thread mainThread;
	private Thread connectorThread;

	/**
	 * Fills a server with AI and a player
	 * 
	 * @param pc The amount of players (includes the human player)
	 */
	public ServerManager(MapSize ms) {
		this(new Game(MapGenStandard.class, ms));
	}

	private ServerManager(Game g) {
		this.game = g;
		ArrayList<Player> players = g.getPlayers();
		boolean ii = false;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getComm().internal()) {
				ii = true;
			}
		}
		game.placePlayers();
		INTERNAL = ii;
		mainThread = new Thread(this);
		mainThread.setName("Server Thread");
		mainThread.start();
		connectorThread = new Thread(new ConnectioManager(this));
		connectorThread.setName("Server Connection Manager Thread");
		connectorThread.start();
	}
	
	private void newPlayer(Socket s) {
		synchronized (game) {//TODO: 5 Add logic to stop super late players
			Console.print("(Server) Connecting new player", 0);
			Player newPlayer = new Player(new ExternalComm(s));
			newPlayer.setup(game);
			game.placePlayer(newPlayer);
			game.getPlayers().add(newPlayer);
			pushPlayerUpdate(newPlayer);
		}
	}
	
	private void pushPlayerUpdate(Player player) {
		LinkedList<String> msg = ProtocolManager.gameToProto(game, player);
		Console.print("(Server) Updating player", -1);
		ProtocolAPI.send(player.getComm(), msg);
	}

	public void updateDiscoveredTiles() {
		ArrayList<Unit> units = game.getUnits();
		for (int i = 0; i < units.size(); i++) {
			Unit u = units.get(i);
			if(u.getOwner() instanceof Player) {
				Player owner = (Player) u.getOwner();
				discoverTilesNoSend(owner, InputHelper.adjacent(u.getPos(), game.getMap().getWidth(), game.getMap().getHeight()));
			}
		}
	}

	public boolean isInternal() {
		return INTERNAL;
	}

	public Game getGame() {
		return game;
	}
	
	private Entity activeEntity;

	private void round() {
		updateDiscoveredTiles();
		game.fixUIDs();
		game.newRound();
		for (int i = 0; i < game.getPlayers().size(); i++) {
			if (game.getPlayers().get(i) == null) {
				if(i == 0) {
					Console.print("Null First Player", 2);
				}
				continue;
			}
			turn(game.getPlayers().get(i));
		}
		game.nextTurn();
	}

	private void turn(Player player) {
		Console.print("(Server) New turn", -1);
		activeEntity = player;
		pushPlayerUpdate(player);
		while (processLn()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				Console.error(e, false);
			}
		}
		Console.print("(Server) End of turn", -1);
	}

	/**
	 * Process a lines, deals with all client-server comms
	 * 
	 * @return a boolean, false if an error, true otherwise, except NEXT_TURN which
	 *         returns false as well
	 */
	private boolean processLn() {
		Player player;
		if(activeEntity instanceof Player) {
			player = (Player) activeEntity;
		} else {
			Console.print("(Server) Tried to process line for non-player active entity", 1);
			return false;
		}
		
		if(player.getComm().hasItems() == false) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				Console.error(e, false);
			}
			return true; //Not always going to be info. This is a lazy way to program this, i know
		}
		String s = player.getComm().receiveln();
		if (s == null) {
			Console.print("(Server) Received null line", 2);
			return true;
		}
		String[] sa = s.split(";");
		String c = sa[0];
		Console.print("(Server) MSG received: "+s, -1);
		if(s.equalsIgnoreCase("NEXT_TURN")) {
			return false;
		}
		if(sa.length == 2) {
			String[] pa = sa[1].split(":");
			if(c.equalsIgnoreCase("UNIT_M")) {
				if(pa.length != 4) {
					Console.print("(Server) Invalid # of arguments", 1);
					return true;
				}
				unitMove(Integer.parseInt(pa[0]), Integer.parseInt(pa[1]), Integer.parseInt(pa[2]), Integer.parseInt(pa[3]));
			}
			return true;
		}
		Console.print("(Server) Unprocessed string: " + s, 1);
		return false;
	}

	private void unitMove(int uid, int tx, int ty, int st) {
		if(uid < 0 || uid >= game.getUnits().size()) {
			Console.print("(Server) Unit ID invalid", 2);
			return;
		}
		Unit u = game.getUnitById(uid);
		if(u == null) {
			return;
		}
		if(u.getOwner() != activeEntity) {
			return;
		}
		Path path = new Path(u.getPos(), new SubTileCoordinate(tx, ty, SubTile.fromID(st)), game.getMap(), false);//TODO: 5 Add water/land differenation
		ArrayList<SubTileCoordinate> cs = path.getPath();
		if(cs == null) {
			return;
		}
		int pathLength = path.pathLength();
		if(u.getMovement() < pathLength) {
			return;
		}
		u.setMovement(u.getMovement()-pathLength);
		u.setX(tx);
		u.setY(ty);
		u.setSubTile(SubTile.fromID(st));
		sendAll("UPDATE");
		sendAll(ProtocolManager.unitToProto(u));
		
		if(u.getOwner() instanceof Player) {
			Player owner = (Player) u.getOwner();
			Coordinate prev = null;
			for(int i = 0;i < cs.size();i ++) {
				if(prev == null || !prev.sameAs(cs.get(i))) {
					prev = cs.get(i);
					discoverTiles(owner, InputHelper.adjacent(prev, game.getMap().getWidth(), game.getMap().getHeight()));
				}
			}
		}
	}
	
	private void sendAll(String message) {
		ArrayList<Player> players = game.getPlayers();
		for(int i = 0;i < players.size();i ++) {
			Player currentPlayer = players.get(i);
			ProtocolAPI.send(currentPlayer.getComm(), message);
		}
	}

	private void discoverTiles(Player p, ArrayList<Coordinate> tiles) {
		p.setDiscovered(tiles);
		updateTiles(p, tiles);
	}
	
	private void discoverTilesNoSend(Player p, ArrayList<Coordinate> tiles) {
		p.setDiscovered(tiles);
	}
	
	private void updateTiles(Player p, ArrayList<Coordinate> tiles) {
		for(int i = 0;i < tiles.size();i ++) {
			Coordinate c = tiles.get(i);
			Tile t = game.getMap().getTiles()[c.getX()][c.getY()];
			p.getComm().println("UPDATE");
			p.getComm().println(ProtocolManager.protoFromTileUpdate(t));
		}
	}

	@Override
	public void run() {
		boolean playing = true;
		while(playing) {
			round();	
		}
		//save();
	}
	
	public void save() {
		String ts = new Timestamp(new Date().getTime()).toString();
		ts = ts.replace(' ', '-');
		ts = ts.replace(':', '-');
		File f = new File("saves/" + ts + ".save");
		LinkedList<String> l = ProtocolManager.gameToProto(game, null);
		if (f.exists())
			return;
		try {
			f.createNewFile();
			PrintWriter pr = new PrintWriter(f);
			while (!l.isEmpty()) {
				pr.append(l.poll() + "\n");
			}
			pr.close();
		} catch (IOException e) {
			Console.error(e, false);
		}
	}
	
	private class ConnectioManager implements Runnable {

		ServerManager server;
		
		public ConnectioManager(ServerManager server) {
			this.server = server;
		}
		
		@Override
		public void run() {
			
			try (ServerSocket serverSocket = new ServerSocket(54321);) {
				while(true) {
					Socket socket = serverSocket.accept();
					server.newPlayer(socket);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
