package chazzvader.game.engine.layer;

public abstract class Layer {
	
	//TODO: 1 Add layer enabling/disabling
	
	public Layer() {
		setup();
	}
	
	public abstract void render();
	public abstract void update();
	public abstract void setup();
	public abstract void regen();
	public abstract boolean handleKeyEvent(int key, int actionType);
	public abstract boolean handleMouseEvent(int button, int actionType);
	public abstract boolean handleMouseScrollEvent(double scroll);
	
	public boolean enabled = true;
}