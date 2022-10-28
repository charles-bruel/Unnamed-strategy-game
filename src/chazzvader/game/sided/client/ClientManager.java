package chazzvader.game.sided.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;

import chazzvader.game.content.units.Unit;
import chazzvader.game.input.ISelectable;
import chazzvader.game.other.Console;
import chazzvader.game.other.Coordinate;
import chazzvader.game.other.Settings;
import chazzvader.game.sided.both.comm.CommManager;
import chazzvader.game.sided.both.comm.ExternalComm;
import chazzvader.game.sided.both.comm.protocol.ProtocolAPI;
import chazzvader.game.sided.both.comm.protocol.ProtocolManager;
import chazzvader.game.sided.both.game.Game;
import chazzvader.game.sided.both.game.Player;
import chazzvader.game.sided.both.game.map.MapSize;
import chazzvader.game.sided.client.comm.InternalCommClient;
import chazzvader.game.sided.client.render.RenderManager;
import chazzvader.game.sided.client.render.WorldRenderer;
import chazzvader.game.sided.client.render.ui.UIManager;
import chazzvader.game.sided.client.render.ui.UIManager.UIState;
import chazzvader.game.sided.client.swing.GameFrame;
import chazzvader.game.sided.client.swing.GamePanel;
import chazzvader.game.sided.server.ServerManager;

/**
 * Class to manage all client-side (player) action and activities.
 * @author csbru
 * @since 1.0
 * @version 1.0
 */
public class ClientManager extends Thread {

	/**
	 * Constructor.
	 * @param frame The window that a user sees.
	 * @param panel The panel that deals with rendering etc.
	 */
	public ClientManager(GameFrame frame, GamePanel panel) {
		this.frame = frame;
		this.panel = panel;
		this.ui = new UIManager(this);
		this.render = new RenderManager(this);
		
		x = 0;
		y = 0;
		
		this.setName("Client Thread");
	}
		
	private CommManager comms;
	
	/**
	 * The window that a user sees.
	 */
	private GameFrame frame;
	
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
	
	/**
	 * The panel that deals with rendering etc.
	 */
	private GamePanel panel;
	
	private double pdelta = 0;
	
	private Player player = null;
	
	/**
	 * The RenderManager thats renders everything.
	 */
	private RenderManager render;

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
	/**
	 * The UIManager used for rendering and dealing with the UI.
	 */
	private UIManager ui;
	
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
	/**
	 * The optimal time for each frame to take to get the target FPS.
	 */
	final long SLEEP_TIME = 1000 / Settings.getTargetFps();
	
	public void createGame() {
		this.igame = true;
		this.comms = new InternalCommClient();
		ui.setUIState(UIState.GAME);
		new ServerManager(MapSize.DUEL);
	}

	public void endTurn() {
		turn = false;
		selected = null;
		this.sendMSG("NEXT_TURN");
	}
	
	/**
	 * Gets the window that a user sees.
	 * @return The window that a user sees.
	 */
	public GameFrame getFrame() {
		return frame;
	}
	
	public void setCameraPosByCoord(Coordinate c) {
		x = (WorldRenderer.getTileWidth()/2)*c.getX();
		y = (int) ((WorldRenderer.getTileHeight()/2)*c.getY()*0.75);
	}
	
	/**
	 * The game object
	 * @return The game object
	 */
	public Game getGame() {
		return game;
	}
	
	/**
	 * Gets the panel that deals with rendering etc.
	 * @return The panel that deals with rendering etc.
	 */
	public GamePanel getPanel() {
		return panel;
	}
		
	/**
	 * Gets the RenderManager thats renders everything.
	 * @return The RenderManager thats renders everything.
	 */
	public RenderManager getRender() {
		return render;
	}

	/**
	 * Gets the incrementing time variable, used for animations.
	 * @return A incrementing time variable, used for animations.
	 */
	public long getTime() {
		return time;
	}
	
	/**
	 * Gets the UIManager used for rendering and dealing with the UI.
	 * @return The UIManager used for rendering and dealing with the UI.
	 */
	public UIManager getUi() {
		return ui;
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
	 * Run loop. Called as a thread. Deals with timings.
	 */
	@Override
	public void run() {
		long lastNano = System.nanoTime();
		while (gameRunning) {
			long currentNano = System.nanoTime();
			
			long delta = currentNano - lastNano;
			doGameUpdates(delta/1000000);
			
			panel.repaint();
			lastNano = System.nanoTime();

			try {
				long tt = SLEEP_TIME;
				if (tt > 0) {
					Thread.sleep(tt);
				}
			} catch (InterruptedException e) {
				Console.error(e, true);
			} catch (IllegalArgumentException e) {
				Console.error(e, true);
			}
			
		}
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
	private void doGameUpdates(double delta) {
		//Update time, used for animations
		pdelta += delta;
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
				WorldRenderer.callUpdate();
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
				ui.setUIState(UIState.GAME);
				Console.print("(Client) Connection successful", 0);
				return;
			} catch (UnknownHostException e) {
				Console.error(e, false);
			} catch (IOException e) {
				Console.error(e, false);
			}
		}
	}
}