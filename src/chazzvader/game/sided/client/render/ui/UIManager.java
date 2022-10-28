package chazzvader.game.sided.client.render.ui;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import chazzvader.game.Main;
import chazzvader.game.content.units.Unit;
import chazzvader.game.input.ISelectable;
import chazzvader.game.input.InputHelper;
import chazzvader.game.localization.LocalizationManager;
import chazzvader.game.other.Settings;
import chazzvader.game.other.SubTileCoordinate;
import chazzvader.game.sided.client.ClientManager;
import chazzvader.game.sided.client.render.GraphicsWrapper;

/**
 * Manages UI (drawing and clicking)
 * @author csbru
 * @since 1.0
 * @version 1.0
 */
public class UIManager {

	/**
	 * Sets basic stuff
	 * @param client
	 */
	public UIManager(ClientManager client) {
		super();
		this.client = client;
		w = Main.getTk().getScreenSize().width;
		h = Main.getTk().getScreenSize().height;
	}
	
	public static final int BACKGROUND_BLUE = 0xAF4455FF;
	public static final int BUTTON_HEIGHT = 40;
	public static final int BUTTON_WIDTH_1 = 300;
	public static final int TOP_BAR_SIZE = 50;
	
	public static final int TOP_BAR_TEXT_SIZE = 40;
	public static final int TEXT_SIZE = 30;
	public static final int HEADER_TEXT_SIZE = 100;
	public static final Font TEXT_FONT = new Font("Courier", 0, TEXT_SIZE);
	public static final Font TOP_BAR_FONT = new Font("Courier", 0, TOP_BAR_TEXT_SIZE);
	public static final Font HEADER_FONT = new Font("Serif", 0, HEADER_TEXT_SIZE);

	/**
	 * Has a click been registered since last update?
	 */
	private boolean click = false;
	
	
	/**
	 * A copy of the client manager
	 */
	private ClientManager client;
	
	/**
	 * A list of option params
	 */
	@SuppressWarnings("rawtypes")
	private ArrayList<Parameter> options_params = new ArrayList<>();

	/**
	 * How far have we scrolled?
	 */
	private int scroll;
	
	/**
	 * The state of the UI
	 */
	private UIState state = UIState.MAIN;
	
	/**
	 * The substate of the UI
	 */
	private int substate = 0;

	/**
	 * Variable to store width and height of window
	 */
	private int w, h;
	private UIMode uiMode = UIMode.NONE;
	
	/**
	 * Checks if a click been registered since last update
	 * @return If a click been registered since last update
	 */
	public boolean click() {
		return click;
	}
	
	/**
	 * @return the scroll
	 */
	public int getScroll() {
		return scroll;
	}

	/**
	 * Gets the state of the UI
	 * @return The state of the UI
	 */
	public UIState getUIState() {
		return state;
	}
	
	/**
	 * Gets the substate of the UI
	 * @return The substate of the UI
	 */
	public int getSubState() {
		return substate;
	}
	
	/**
	 * Render method
	 * @param g The graphics object, all rendering is done from this object
	 */
	@SuppressWarnings("rawtypes")
	public void render(GraphicsWrapper g) {
		boolean ig = client.isGame();
		if(ig) {
			switch(state) {
			case GAME:
				game(g);
				break;
			default:
				break;
			}
		} else {
			switch(state) {
			case MAIN:
				mainMenu(g);
				break;
			case OPTIONS:
				options(g);
				break;
			case CREATE_GAME:
				newGame(g);
			default:
				break;
			}
		}
		if(!(uiMode == UIMode.MOVE)) {
			int mouseX = Main.getFrame().getMouseX(), mouseY = Main.getFrame().getMouseY();
			ArrayList<Parameter> params = getCurrentParamList();
			for(int i = 0;i < params.size();i ++) {
				Parameter p = params.get(i);
				if(p == null) continue;
				p.draw(g);
				if(click) {
					if(mouseX > p.getX() && mouseY > p.getY() && mouseX < p.getX() + p.getWidth(g) && mouseY < p.getY() + p.getHeight(g)) {
						p.processClick(mouseX - p.getX(), mouseY - p.getY());
					}
				}
			}
		}
		click = false;
	}

	/**
	 * @param scroll the scroll to set
	 */
	public void setScroll(int scroll) {
		this.scroll = scroll;
	}
	/**
	 * Sets the state of the UI
	 * @param state The state of the UI to set
	 */
	public void setUIState(UIState state) {
		this.state = state;
	}
	
	/**
	 * Sets the substate of the UI
	 * @Param state The substate of the UI to set
	 */
	public void setSubState(int substate) {
		this.substate = substate;
	}
	
	/**
	 * Triggers a click
	 */
	public void triggerClick(MouseEvent e) {
		if(isMouseOnGame()) {
			if(SwingUtilities.isLeftMouseButton(e)) {
				gameClick(InputHelper.determineTilePositionAtMouse(client));
			} else if(SwingUtilities.isRightMouseButton(e)) {
				moveClick(InputHelper.determineTilePositionAtMouse(client));
			}
		} else {
			click = true;
		}
	}
	
	private void gameClick(SubTileCoordinate tileClicked) {
		switch(uiMode) {
		case NONE:
			ArrayList<Unit> u = client.getGame().getUnits();
			for(int i = 0;i < u.size();i ++) {
				Unit cu = u.get(i);
				if(cu.getPos().sameAs(tileClicked)) {
					client.setSelected(cu);
					return;
				}
			}
			client.setSelected(null);
			return;
		case MOVE:
			moveClick(tileClicked);
			break;
		default:
			break;
		}
	}
	
	public void moveClick(SubTileCoordinate tm) {
		ISelectable selected = client.getSelected();
		if(!(selected instanceof Unit)) {
			return;
		}
		Unit unit = (Unit) selected;
		client.sendMSG("UNIT_M;"+unit.getUID()+":"+tm.getX()+":"+tm.getY()+":"+tm.getSubTile().toID());
		selected = null;
	}
	
	/**
	 * Renders ingame UI
	 * @param g The graphics object, all rendering is done from this object
	 */
	private void game(GraphicsWrapper g) {
		if(client.getGame() == null) return;
		g.setColor(0x000000);
		g.renderLine(0, TOP_BAR_SIZE, g.getWidth(), TOP_BAR_SIZE);
		g.rectangle(0, 0, w, 50);
		g.setColorA(BACKGROUND_BLUE);
		g.rectangle(g.getWidth()-BUTTON_WIDTH_1, TOP_BAR_SIZE, BUTTON_WIDTH_1, g.getHeight()-TOP_BAR_SIZE);
		g.setColor(0xFFFFFF);
		g.setFont(TOP_BAR_FONT);
		g.renderString("Turn #"+client.getGame().getTurn(), 5, TOP_BAR_SIZE-5);
		if(client.isTurn()) {
			g.renderFunctionalButton(g.getWidth()-BUTTON_WIDTH_1, g.getHeight()-BUTTON_HEIGHT, BUTTON_WIDTH_1, LocalizationManager.get("LOC_END_TURN"), () -> client.endTurn());
		}
		switch(uiMode) {
		case MOVE:
			if(client.getSelected() == null) {
				uiMode = UIMode.NONE;
				break;
			}
			g.renderStringCenter(LocalizationManager.get("LOC_WHERE_MOVE"), g.getWidth()/2, g.getHeight()/4);
			break;
		case NONE:
			break;
		default:
			break;
		}
		ISelectable selected = client.getSelected();
		if(selected != null) {
			if(selected instanceof Unit) {
				Unit u = (Unit) selected;
				g.setColorA(BACKGROUND_BLUE);
				g.rectangle(0, g.getHeight()/2, BUTTON_WIDTH_1, g.getHeight()/2);
				u.draw(g, BUTTON_WIDTH_1/2, g.getHeight()/2+(BUTTON_WIDTH_1/2), 5f);
				g.setColor(0x000000);
				g.setFont(TEXT_FONT);
				g.renderString(LocalizationManager.get(u.getName()), 0, g.getHeight()/2+BUTTON_WIDTH_1);
				g.renderString(LocalizationManager.get("LOC_MOVEMENT")+": "+(u.getMovement()/10.0d), 0, g.getHeight()/2+BUTTON_WIDTH_1+TOP_BAR_TEXT_SIZE);
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private ArrayList<Parameter> getCurrentParamList(){
		if(!client.isGame()) {
			switch(state) {
			case OPTIONS:
				return options_params;
			default:
				break;
			}
		} else if(client.isTurn() && client.getSelected() != null) {
			return client.getSelected().getActions();
		}
		return new ArrayList<>();
	}
	
	private void gotoMainMenu() {
		state = UIState.MAIN;
	}
	
	private void gotoOptions(GraphicsWrapper g) {
		state = UIState.OPTIONS;
		resetOptions(g);
	}

	private void leaveSettings() {
		Settings.setDebug((Boolean) options_params.get(0).getValue());
		Settings.push();// TODO: Add other settings
		gotoMainMenu();
	}
	
	/**
	 * Renders the main menu
	 * @param g The graphics object, all rendering is done from this object
	 */
	private void mainMenu(GraphicsWrapper g) {
		g.setColor(0x000000);//Black
		g.setFont(HEADER_FONT);
		g.renderString(LocalizationManager.get("LOC_TITLE"), w/4, h/2-50);
		
		g.renderFunctionalButton(w/3, h/2 + 0, BUTTON_WIDTH_1, LocalizationManager.get("LOC_SINGLE_PLAYER"), () -> gotoCreateGame());
		g.renderFunctionalButton(w/3, h/2 + 45, BUTTON_WIDTH_1, LocalizationManager.get("LOC_OPTIONS"), () -> gotoOptions(g));
		g.renderFunctionalButton(w/3, h/2 + 90, BUTTON_WIDTH_1, LocalizationManager.get("LOC_EXIT_DESKTOP"), () -> System.exit(0));
	}
	
	private void gotoCreateGame() {
		state = UIState.CREATE_GAME;
	}

	/**
	 * Draws the single player new game screen.
	 * @param g The graphics object, all rendering is done from this object
	 */
	private void newGame(GraphicsWrapper g) {// TODO: Add options
		g.setColorA(BACKGROUND_BLUE); //Transparent blue
		g.renderBox(w/4, h/4, (w/4)*3, (h/4)*3);
		g.setColor(0x031E49); //Blue
		g.renderLine(w/4+320, h/3-75, w/4+320, (h/4)*3-25);
		
		g.setColor(0x000000);//Black
		g.setFont(HEADER_FONT);
		g.renderString(LocalizationManager.get("LOC_CREATE_GAME"), w/4, h/4+75);
		
		g.renderFunctionalButton(((w/4)*3)-310, (h/4)*3-50, BUTTON_WIDTH_1, LocalizationManager.get("LOC_START"), () -> client.createGame());
		g.renderFunctionalButton(w/4+10, (h/4)*3-50, BUTTON_WIDTH_1, LocalizationManager.get("LOC_BACK"), () -> gotoMainMenu());
	}

	/**
	 * Renders the options panel
	 * @param g The graphics object, all rendering is done from this object
	 */
	private void options(GraphicsWrapper g) {
		g.setColorA(BACKGROUND_BLUE); //Transparent blue
		g.renderBox(w/4, h/4, (w/4)*3, (h/4)*3);
		g.setColor(0x031E49); //Blue
		g.renderLine(w/4+320, h/3-75, w/4+320, (h/4)*3-25);
		
		g.setColor(0x000000);//Black
		g.setFont(new Font("Serif", Font.PLAIN, 100));
		g.renderString(LocalizationManager.get("LOC_OPTIONS"), w/4, h/4+75);
		
		g.renderFunctionalButton(w/4+10, (h/4)*3-50, BUTTON_WIDTH_1, LocalizationManager.get("LOC_BACK"), () -> leaveSettings());
	}

	private void resetOptions(GraphicsWrapper g) {
		options_params = new ArrayList<>(1);
		int currenty = h/4+200;
		int x = w/4+320;
		options_params.add(new ParameterCheckbox(Settings.getDebug(), LocalizationManager.get("LOC_DEBUG"), x, currenty));// TOOD: Add other settings
	}

	public boolean isMouseOnGame() {
		if(!client.isGame()) {
			return false;
		}
		if (InputHelper.within(client.getFrame().getMouseX(), client.getFrame().getMouseY(), 0, 40, Main.getTk().getScreenSize().width-BUTTON_WIDTH_1, Main.getTk().getScreenSize().height)) {
			return true;
		} else {
			return false;
		}
	}

	public void move(Unit unit) {
		uiMode = UIMode.MOVE;
	}
	
	public enum UIMode {
		NONE,MOVE;
	}
	
	public enum UIState {
		NONE,MAIN,OPTIONS,CREATE_GAME,GAME;
	}

	/**
	 * @return the uiMode
	 */
	public UIMode getUIMode() {
		return uiMode;
	}

	/**
	 * @param uiMode the uiMode to set
	 */
	public void setUIMode(UIMode uiMode) {
		this.uiMode = uiMode;
	}
	
}
