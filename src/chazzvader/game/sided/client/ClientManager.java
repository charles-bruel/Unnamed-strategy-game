package chazzvader.game.sided.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;

import chazzvader.game.content.mapgen.MapGenStandard;
import chazzvader.game.content.units.Unit;
import chazzvader.game.input.ISelectable;
import chazzvader.game.other.Console;
import chazzvader.game.other.Coordinate;
import chazzvader.game.sided.both.comm.CommManager;
import chazzvader.game.sided.both.comm.ExternalComm;
import chazzvader.game.sided.both.comm.protocol.ProtocolAPI;
import chazzvader.game.sided.both.comm.protocol.ProtocolManager;
import chazzvader.game.sided.both.game.Game;
import chazzvader.game.sided.both.game.Player;
import chazzvader.game.sided.both.game.map.Map;

/**
 * Class to manage all client-side (player) action and activities.
 * @author csbru
 * @since 1.0
 * @version 1.0
 */
public class ClientManager {

	/**
	 * Constructor.
	 * @param frame The window that a user sees.
	 * @param panel The panel that deals with rendering etc.
	 */
	public ClientManager() {
		//TODO: 3 Constructor
	}
		
	private CommManager comms;
	
	/**
	 * A game object
	 */
	private Game game;
	
	private Socket socket;
	
	/**
	 * Is the game loop running
	 */
	private boolean gameRunning = true;
	
	/**
	 * If the player is in-game
	 */
	private boolean igame = false;
	private LinkedList<String> l = new LinkedList<>();
	
	private Player player = null;

	private boolean tGame = false;

	/**
	 * A incrementing time variable, used for animations.
	 */
	private long time;

	private ISelectable selected;
	
	/**
	 * Is it your turn?
	 */
	private boolean turn = false;

	private boolean update;
	
	/**
	 * Coordinates of player in the game
	 */
	private int x,y;

	/**
	 * The zoom of the camera. <br>
	 * 0.1f - 1.0f
	 */
	private float zoom = 1;

	private double pdelta = 0;
	
	public void createGame() {
		/*this.igame = true;
		this.comms = new InternalCommClient();
		ui.setUIState(UIState.GAME);
		new ServerManager(MapSize.STANDARD);*/
		//TODO: 2 Method
	}

	public void endTurn() {
		turn = false;
		selected = null;
		this.sendMSG("NEXT_TURN");
	}
	
	public void setCameraPosByCoord(Coordinate c) {
		/*
		x = (WorldRenderer.getTileWidth()/2)*c.getX();
		y = (int) ((WorldRenderer.getTileHeight()/2)*c.getY()*0.75);
		*/
		//TODO: 2 Method
	}
	
	/**
	 * The game object
	 * @return The game object
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Gets the incrementing time variable, used for animations.
	 * @return A incrementing time variable, used for animations.
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Gets the x coordinate of player in the game
	 * @return The x coordinate of player in the game
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y coordinate of player in the game
	 * @return The y coordinate of player in the game
	 */
	public int getY() {
		return y;
	}
	/**
	 * Gets the zoom of the camera.
	 * @return The zoom of the camera.
	 */
	public float getZoom() {
		return zoom;
	}
	/**
	 * Is the player is in-game
	 * @return Is the player is in-game
	 */
	public boolean isGame() {
		return igame;
	}
	/**
	 * Is the game loop running
	 * @return Is the game loop running
	 */
	public boolean isGameRunning() {
		return gameRunning;
	}
	/**
	 * @return the turn
	 */
	public boolean isTurn() {
		return turn;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	public void sendMSG(String string) {
		comms.println(string);
	}

	/**
	 * Sets the x coordinate of player in the game
	 * @param x The x coordinate of player in the game
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Sets the y coordinate of player in the game
	 * @param y The y coordinate of player in the game
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Sets the zoom of the camera.
	 * @param zoom The zoom of the camera. (0.1f - 1.0f)
	 */
	public void setZoom(float zoom) {
		this.zoom = zoom;
	}
	
	/**
	 * Performs all game updates. Not a lot here, as most happens server side and nothing happens in real time.
	 * @param delta
	 */
	public void update(double delta) {
		//Update time, used for animations
		pdelta  += delta;
		while(pdelta > 5){
			pdelta -= 5;
			time++;
		}
		//Manage stuff
		if(igame) {
			if(comms != null && comms.hasItems()) {
				LinkedList<String> items = ProtocolAPI.receive(comms);
				while(!items.isEmpty()) {
					processLn(items.pollLast());
				}
			}
		}
	}

	private void processLn(String s) {
 		if(s == null) {
			Console.print("(Client) Received a null line.", 2);
			return;
		}
		if(update) {
			update = false;
			String[] sa = s.split(";");
			String c = sa[0];
			if(c.equalsIgnoreCase("UNIT_I")) {
				ProtocolManager.updateUnit(s, game.getUnits());
				refreshSelected();
				return;
			}
			if(c.equalsIgnoreCase("MAP_I")) {
				ProtocolManager.updateTile(s, game.getMap(), player);
				return;
			}
		} else {
			if(s.equalsIgnoreCase("UPDATE")) {
				update = true;
				return;
			}
			if(s.equalsIgnoreCase("GAME_S")) {
				tGame = true;
			}
			if(s.equalsIgnoreCase("GAME_E")) {
				tGame = false;
				l.add(s);
				game = ProtocolManager.gameFromProto(l);
				l = new LinkedList<String>();
				turn = true;
				//WorldRenderer.callUpdate();
				return;
			}
			if(tGame) {
				l.add(s);
				return;
			}
		}
		Console.print("(Client) Unprocessed string: "+s, 1);
	}

	private void refreshSelected() {
		if(selected instanceof Unit) {
			Unit unit = (Unit) selected;
			ArrayList<Unit> units = game.getUnits();
			if(!units.contains(unit)) {
				selected = game.getUnitById(unit.getUID());
			}
		}
	}

	/**
	 * @return the selected
	 */
	public ISelectable getSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(ISelectable selected) {
		this.selected = selected;
	}

	public void connect(String ip, int port) {
		synchronized (this) {
			try {
				socket = new Socket(ip, port);
				Console.print("(Client) Attempting connection at "+ip+":"+port, 0);
				this.comms = new ExternalComm(socket);
				this.igame = true;
				//ui.setUIState(UIState.GAME);
				Console.print("(Client) Connection successful", 0);
				return;
			} catch (UnknownHostException e) {
				Console.error(e, false);
			} catch (IOException e) {
				Console.error(e, false);
			}
		}
	}

	public void createDebugGame() {
		game = new Game();
		Map map = new MapGenStandard().generate(1000, 750);
		game.setMap(map);
	}
}