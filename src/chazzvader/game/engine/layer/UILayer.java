package chazzvader.game.engine.layer;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import chazzvader.game.engine.GameManager;
import chazzvader.game.engine.Utils;
import chazzvader.game.engine.render.RenderManager;
import chazzvader.game.engine.render.RenderableObjectContainerGenerator;
import chazzvader.game.engine.render.TextureManager;
import chazzvader.lib.helper.input.MousePosInput;

public class UILayer extends BasicLayer {

	@Override
	public void update() {

	}

	@Override
	public void setup() {

	}

	@Override
	public void regen() {
		objects = new ArrayList<>();
		addBackground();
	}
	
	private void addBackground() {
		switch(GameManager.getGameState()) {
		case GAME:
			break;
		case MENU:
			objects.add(RenderableObjectContainerGenerator.fromTexture(TextureManager.BACKGROUND_MENU, RenderManager.LEFT_BOUND, RenderManager.TOP_BOUND, RenderManager.RIGHT_BOUND, RenderManager.BOTTOM_BOUND, RenderManager.BACKGROUND_Z).generate());
			break;
		}
	}

	@Override
	public boolean handleMouseEvent(int button, int actionType) {
		if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			float mx = Utils.fromXPos((int) MousePosInput.xpos), my = Utils.fromYPos((int) MousePosInput.ypos);
			System.out.println(mx);
			if(true) {
				
			}
		}
		return false;
	}

	@Override
	public boolean handleMouseScrollEvent(double scroll) {
		return false;
	}
	
	@Override
	public boolean handleKeyEvent(int key, int actionType) {
		return false;
	}


}
