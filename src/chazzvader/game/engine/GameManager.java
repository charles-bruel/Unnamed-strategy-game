package chazzvader.game.engine;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.opengl.GL11;

import chazzvader.game.engine.layer.GameLayer;
import chazzvader.game.engine.layer.Layer;
import chazzvader.game.engine.layer.TestLayer;
import chazzvader.game.engine.layer.UILayer;
import chazzvader.game.engine.render.RenderManager;
import chazzvader.game.other.Console;
import chazzvader.game.sided.client.ClientManager;
import chazzvader.lib.helper.other.Window;

public class GameManager {

	private GameManager() {}
	
	public static enum GameState { LOADING,GAME,MENU; }
	
	private static GameState gameState = GameState.LOADING;
	
	public static void start() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				init();
				loop();
			}
		});
		thread.setName("Game Thread");
		thread.start();
	}
	
	private static boolean running = true;
	
	private static long lastNanoTime = 0;
	
	private static Window windowObj;
	private static long windowPtr;
	
	private static ClientManager clientManager;
	
	private static void loop() {
		while(running) {
			GLFW.glfwMakeContextCurrent(windowPtr);
			if(GLFW.glfwWindowShouldClose(windowPtr)) {
				running = false;
			}
			GLFW.glfwPollEvents();
			long delta = 0;
			if(lastNanoTime == 0) {
				delta = 1;
			} else {
				delta = System.nanoTime() - lastNanoTime;
			}
			update(delta);
			lastNanoTime = System.nanoTime();
			RenderManager.render();
		}
	}

	private static void init() {
		windowObj = RenderManager.createWindow();
		windowPtr = windowObj.id;
		GLFW.glfwSetKeyCallback(windowPtr, new KeyListener());
		GLFW.glfwSetMouseButtonCallback(windowPtr, new MouseListener());
		GLFW.glfwSetScrollCallback(windowPtr, new MouseScrollListener());
		clientManager = new ClientManager();
	}
	
	private static void update(long delta) {
		int eBuffer = -1;
		do {
			if(eBuffer != -1) {
				Console.print("(LWJGL) OpenGL error: "+eBuffer, 1);
			}
			eBuffer = GL11.glGetError();
		} while(eBuffer != GL11.GL_NO_ERROR);
		
		switch (gameState) {
		case GAME:
			for(int i = 0;i < layers.size();i ++) {
				layers.get(i).update();
			}
			break;
		case MENU:
			for(int i = 0;i < layers.size();i ++) {
				layers.get(i).update();
			}
			break;
		case LOADING:
			if(LoadingManager.update()) {
				doneLoading();
				setGameState(GameState.GAME);
			}
			break;
		default:
			break;
		}
	}

	private static void setGameState(GameState gameState) {
		if(GameManager.gameState != gameState) {
			GameManager.gameState = gameState;
			layerRegen();
			Console.print("(Game Manager) Changing gamestate to "+gameState, 0);
		}
	}

	public static GameState getGameState() {
		return gameState;
	}

	public static ClientManager getClientManager() {
		return clientManager;
	}
	
	private static class KeyListener extends GLFWKeyCallback {

		@Override
		public void invoke(long window, int key, int scancode, int action, int mods) {
			handleKeyEvent(key, action);
		}
		
	}
	
	private static class MouseListener extends GLFWMouseButtonCallback {

		@Override
		public void invoke(long window, int button, int action, int mods) {
			handleMouseEvent(button, action);
		} 
		
	}
	
	private static class MouseScrollListener extends GLFWScrollCallback {

		@Override
		public void invoke(long window, double xoffset, double yoffset) {
			handleMouseScrollEvent(yoffset);
		} 
		
	}

	private static ArrayList<Layer> layers = new ArrayList<>();

	public static ArrayList<Layer> getLayers() {
		return layers;
	}
	
	private static GameLayer gameLayer;
	private static UILayer uiLayer;

	private static void layerInit() {
		layers.add(new TestLayer());//Top layer
		uiLayer = new UILayer();
		layers.add(uiLayer);
		gameLayer = new GameLayer();
		layers.add(gameLayer);//Game layer
	}
	
	private static void layerRegen() {
		if(gameState == GameState.LOADING) return;
		for(int i = 0;i < layers.size();i ++) {
			layers.get(i).regen();
		}
	}
	
	public static void handleKeyEvent(int key, int actionType) {
		handleKeyEvent(key, actionType, 0);
	}
	
	private static void handleKeyEvent(int key, int actionType, int startIndex) {
		for(int i = startIndex;i < layers.size();i ++) {
			if(!layers.get(i).enabled) continue;
			if(layers.get(i).handleKeyEvent(key, actionType)) {
				handleKeyEvent(key, GLFW.GLFW_RELEASE, i+1);
			}
		}
	}
	
	public static void handleMouseEvent(int button, int actionType) {
		handleMouseEvent(button, actionType, 0);
	}
	
	private static void handleMouseEvent(int button, int actionType, int startIndex) {
		for(int i = startIndex;i < layers.size();i ++) {
			if(!layers.get(i).enabled) continue;
			if(layers.get(i).handleMouseEvent(button, actionType)) {
				handleMouseEvent(button, GLFW.GLFW_RELEASE, i+1);
			}
		}
	}
	
	public static void handleMouseScrollEvent(double scroll) {
		for(int i = 0;i < layers.size();i ++) {
			if(!layers.get(i).enabled) continue;
			if(layers.get(i).handleMouseScrollEvent(scroll)) {
				break;
			}
		}
	}

	public static void doneLoading() {
		layerInit();
	}
	
}
