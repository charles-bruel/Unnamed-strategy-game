package chazzvader.game.engine.layer;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import chazzvader.game.engine.GameManager;
import chazzvader.game.engine.render.RenderManager;
import chazzvader.game.engine.render.RenderableObjectContainerGenerator;
import chazzvader.game.engine.render.RenderableObjectContainerTemplate;
import chazzvader.game.other.Console;
import chazzvader.lib.helper.input.MousePosInput;

public class GameLayer extends BasicLayer {

	@Override
	public void setup() {

	}

	public void regen() {
		objects = new ArrayList<>();
		switch(GameManager.getGameState()) {
		case MENU:
			break;
		case GAME:
			Console.print("(Game Layer) Rebuilding world", 0);
			GameManager.getClientManager().createDebugGame();//TODO: 2 Proper worldgen
			ArrayList<RenderableObjectContainerTemplate> tempObjects = RenderableObjectContainerGenerator.fromGame(GameManager.getClientManager().getGame());
			objects = new ArrayList<>(tempObjects.size());
			for(int i = 0;i < tempObjects.size();i ++) {
				objects.add(tempObjects.get(i).generate());
			}
			break;
		case LOADING:
			break;
		default:
			break;
		}
		
	}
	
	
	/* INPUT */
	private static boolean[] arrowKeys = new boolean[4];
	private static boolean dragMove = false;
	
	@Override
	public boolean handleKeyEvent(int key, int actionType) {
		switch(key) {
		case GLFW.GLFW_KEY_LEFT:
			arrowKeys[0] = !(actionType == GLFW.GLFW_RELEASE);
			return true;
		case GLFW.GLFW_KEY_RIGHT:
			arrowKeys[1] = !(actionType == GLFW.GLFW_RELEASE);
			return true;
		case GLFW.GLFW_KEY_UP:
			arrowKeys[2] = !(actionType == GLFW.GLFW_RELEASE);
			return true;
		case GLFW.GLFW_KEY_DOWN:
			arrowKeys[3] = !(actionType == GLFW.GLFW_RELEASE);
			return true;
		}
		return false;
	}

	@Override
	public boolean handleMouseEvent(int button, int actionType) {
		if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			dragMove = !(actionType == GLFW.GLFW_RELEASE);
		}
		return true;
	}
	
	private double pastX, pastY;

	private boolean firstFrameDragMove = false;
	
	@Override
	public void update() {
		if(arrowKeys[0]) {
			RenderManager.moveLeft();
		}
		if(arrowKeys[1]) {
			RenderManager.moveRight();
		}
		if(arrowKeys[2]) {
			RenderManager.moveUp();
		}
		if(arrowKeys[3]) {
			RenderManager.moveDown();
		}
		if(dragMove) {
			double mx = MousePosInput.xpos, my = MousePosInput.ypos;
			if(!firstFrameDragMove) {
				pastX = mx;
				pastY = my;
				firstFrameDragMove = true;
			} else {
				double scaleRep = RenderManager.getScale()*RenderableObjectContainerGenerator.TILE_SIZE;
				while(pastX > mx) {
					pastX -= scaleRep;
					RenderManager.moveRight();
				}
				while(pastX < mx) {
					pastX += scaleRep;
					RenderManager.moveLeft();
				}
				while(pastY > my) {
					pastY -= scaleRep;
					RenderManager.moveDown();
				}
				while(pastY < my) {
					pastY += scaleRep;
					RenderManager.moveUp();
				}
			}
		} else {
			firstFrameDragMove = false;
		}
	}
	
	@Override
	public boolean handleMouseScrollEvent(double scroll) {
		RenderManager.scroll(scroll);
		return true;
	}

}
