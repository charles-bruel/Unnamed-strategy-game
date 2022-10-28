package chazzvader.game.content.units;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import chazzvader.game.Main;
import chazzvader.game.content.manager.Content;
import chazzvader.game.content.tiles.Tile;
import chazzvader.game.input.ISelectable;
import chazzvader.game.other.SubTileCoordinate;
import chazzvader.game.other.SubTileCoordinate.SubTile;
import chazzvader.game.other.IAction;
import chazzvader.game.sided.both.game.Entity;
import chazzvader.game.sided.client.render.GraphicsWrapper;
import chazzvader.game.sided.client.render.ImageManager;
import chazzvader.game.sided.client.render.ui.Parameter;
import chazzvader.game.sided.client.render.ui.ParameterButton;
import chazzvader.game.sided.client.render.ui.UIManager;

public abstract class Unit extends Content implements ISelectable {
	
	/**
	 * @param owner
	 * @param pos
	 */
	public Unit(Entity owner, int x, int y, SubTile subTile, int uid) {
		charges = 0;
		xp = 0;
		health = maxHealth();
		pos = new SubTileCoordinate(x, y, subTile);
		this.UID = uid;
		this.owner = owner;
	}
	
	public String getInName() {
		String[] cp = this.getClass().getName().split("\\.");
		return cp[cp.length-1];
	}

	private int charges;
	
	private int currentIndex = 2;
	
	private int health;
	
	private Entity owner;
	
	public int movement = getMaxMovement();
	
	public int getMaxMovement() {
		return 50;
	}
	
	public int getMovement() {
		return movement;
	}

	public void setMovement(int movement) {
		this.movement = movement;
	}

	private SubTileCoordinate pos;
	
	private int UID;
	
	private int xp;	
	public void draw(GraphicsWrapper g, int x, int y, float z) {
		Color c = owner == null ? new Color(0xFF0000) : owner.getColor();
		BufferedImage bg = getBackgroundImage();
		BufferedImage ig = getIconImage();
		int bgiw = bg.getWidth();
		int bgih = bg.getHeight();
		int igiw = ig.getWidth();
		int igih = ig.getHeight();
		bg = ImageManager.getColoredImage(bg, c);
		g.renderImageCenter(bg, x, y, (int) (bgiw*z*1.2*0.3), (int) (bgih*z*1.2*0.3));
		g.renderImageCenter(ig, x, y, (int) (igiw*z*0.6*0.3), (int) (igih*z*0.6*0.3));
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public ArrayList<Parameter> getActions() {
		currentIndex = 1;
		return new ArrayList<Parameter>(Arrays.asList(new Parameter[]{moveParam()}));
	}

	public abstract BufferedImage getBackgroundImage();
	
	public abstract String getName();

	/**
	 * @return the charges
	 */
	public int getCharges() {
		return charges;
	}
	
	/**
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}
	
	public abstract BufferedImage getIconImage();
	
	/**
	 * @return the owner
	 */
	public Entity getOwner() {
		return owner;
	}
	
	public SubTileCoordinate getPos() {
		return pos;
	}
	
	public SubTile getSubTile() {
		return getPos().getSubTile();
	}
	
	/**
	 * @return the uID
	 */
	public int getUID() {
		return UID;
	} 
	public int getX() {
		return pos.getX();
	}
		
	/**
	 * @return the xp
	 */
	public int getXp() {
		return xp;
	}

	public int getY() {
		return pos.getY();
	}

	public abstract boolean isTileValid(Tile t);

	public abstract int maxHealth();

	public void setPos(SubTileCoordinate pos) {
		this.pos = pos;
	}
	
	public void setSubTile(SubTile st) {
		getPos().setSubTile(st);
	}

	/**
	 * @param uID the uID to set
	 */
	public void setUID(int uID) {
		UID = uID;
	}
	
	public SubTileCoordinate setX(int x) {
		pos.setX(x);
		return pos;
	}

	public SubTileCoordinate setY(int y) {
		pos.setY(y);
		return pos;
	}

	@SuppressWarnings("rawtypes")
	private Parameter moveParam() {
		if(movement == 0) return null;
		currentIndex++;
		int bw1 = UIManager.BUTTON_WIDTH_1;
		IAction action = () -> Main.getFrame().getPanel().getClient().getUi().move(this);
		int x = Main.getTk().getScreenSize().width-bw1;
		int y = Main.getTk().getScreenSize().height-(currentIndex*40);	
		return new ParameterButton(bw1, "LOC_MOVE", action, x, y);
	}
	
}