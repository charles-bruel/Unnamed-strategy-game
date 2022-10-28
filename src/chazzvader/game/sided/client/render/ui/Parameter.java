package chazzvader.game.sided.client.render.ui;

import chazzvader.game.other.Console;
import chazzvader.game.sided.client.render.GraphicsWrapper;

public abstract class Parameter<T> {

	protected int x, y;
	
	public abstract int getHeight(GraphicsWrapper g);
	public abstract int getWidth(GraphicsWrapper g);
	public abstract void draw(GraphicsWrapper g);
	public abstract void processClick(int rx, int ry);
	
	public int getX() {
		return x;
	}
	
	public T getValue() {
		Console.print("(UI) Trying to retreive a valuen from a " + getClass().getSimpleName() + " which doesn't support values.", 1);
		return null;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
}