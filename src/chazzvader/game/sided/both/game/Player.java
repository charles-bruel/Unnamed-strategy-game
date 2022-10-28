package chazzvader.game.sided.both.game;

import java.awt.Color;
import java.util.ArrayList;

import chazzvader.game.content.civilizations.Civilization;
import chazzvader.game.content.civilizations.Civilizations;
import chazzvader.game.other.Coordinate;
import chazzvader.game.sided.both.comm.CommManager;

public class Player extends Entity{

	private Civilization civilization;
	
	private boolean[][] discovered;
	
	public boolean[][] getDiscovered() {
		return discovered;
	}

	/**
	 * @param discovered the discovered to set
	 */
	public void setDiscovered(boolean[][] discovered) {
		this.discovered = discovered;
	}

	private Entity entity;
	
	private CommManager comm;

	/**
	 * @return the communications manager
	 */
	public CommManager getComm() {
		return comm;
	}

	@Override
	public Color getColor() {
		return civilization.getColor();
	}
	
	/**
	 * @param communications manager of this player
	 */
	public Player(CommManager comm) {
		this(comm, Civilizations.generic());
	}
	
	public Player(CommManager comm, Civilization civilization) {
		super(civilization.getName(), civilization.getColor());
		this.comm = comm;
		this.civilization = civilization;
	}

	public void setup(Game game) {
		this.discovered = new boolean[game.getMap().getWidth()][game.getMap().getWidth()];
	}
	
	/**
	 * @return the entity
	 */
	public Entity getEntity() {
		return entity;
	}
	
	public void setDiscovered(Coordinate c) {
		discovered[c.getX()][c.getY()] = true;
	}
	
	public void setDiscovered(ArrayList<Coordinate> c) {
		for(int i = 0;i < c.size();i ++) {
			setDiscovered(c.get(i));
		}
	}

	public Civilization getCivilization() {
		return civilization;
	}

	
}