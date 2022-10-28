package chazzvader.game.engine.render;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;

import chazzvader.game.engine.GameManager;
import chazzvader.game.engine.LoadingManager;
import chazzvader.game.engine.Utils;
import chazzvader.game.engine.layer.Layer;
import chazzvader.game.localization.LocalizationManager;
import chazzvader.game.other.Console;
import chazzvader.lib.helper.other.Window;
import chazzvader.lib.helper.render.Shader;
import chazzvader.lib.helper.render.VertexArray;

public class RenderManager {	
	
	private static Window windowObj = null;
	private static long windowPtr;
		
	public static float LEFT_BOUND =   -16.0f;
	public static float RIGHT_BOUND =   16.0f;
	public static float BOTTOM_BOUND = -9.0f;
	public static float TOP_BOUND =     9.0f;
	public static float NEAR_BOUND =   -5.0f;
	public static float DEPTH_BOUND =  15.0f;
	
	public static final float BACKGROUND_Z = -3.00f;
	public static final float TILE_Z =       -0.90f;
	public static final float RIVER_Z =      -0.80f;
	public static final float FEATURE_Z =    -0.70f;
	public static final float BUILDING_Z =   -0.60f;
	public static final float OVERLAY_Z =    -0.50f;
	public static final float RESOURCE_Z =   -0.40f;
	public static final float YIELD_Z =      -0.30f;
	public static final float UNIT_Z =       -0.20f;
	public static final float UI_Z =          0.70f;
	public static final float UI_TEXT_Z =     0.80f;
	
	public static final float MIN_ZOOM = 0.01f;
	public static final float MAX_ZOOM = 8.00f;

	public static final Matrix4f PR_MATRIX = new Matrix4f().setOrtho(LEFT_BOUND, RIGHT_BOUND, BOTTOM_BOUND, TOP_BOUND, NEAR_BOUND, DEPTH_BOUND);
	
	public static Window createWindow() {
		if(windowObj != null) {
			Console.print("(Render Manager) Window already created!", 2);
			return null;
		}
		
		glInit();
		
		return windowObj;
	}
	
	/**
	 * Various openGL housekeeping things
	 */
	private static void glInit() {
		Console.print("(Render Manager) Beginning initializing GLInit", 0);
		windowObj = Window.create(1920, 1080, LocalizationManager.get("LOC_TITLE"));
		GLFW.glfwSwapInterval(0);
		windowPtr = windowObj.id;
		Console.print("(Render Manager) Window Initilized", 0);
		Console.print("(LWJGL) Version: " + GL11.glGetString(GL11.GL_VERSION), 0);
		
		GLFWErrorCallback.createPrint(System.err).set();
		GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
				
		Console.print("(Render Manager) Done initializing GLInit", 0);
	}
	
	
	public static void render() {
		windowObj.update();//Updates the windows, does resizing
		GLFW.glfwMakeContextCurrent(windowPtr);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		switch(GameManager.getGameState()) {
		case MENU:
		case GAME:
			renderGame();
			break;
		case LOADING:
			renderLoading();
			break;
		}

		GLFW.glfwSwapBuffers(windowPtr);
	}

	private static boolean firstLoadFrame = true;
	
	private static Shader sTempLoading = null;//Loading only objects
	private static VertexArray vaoTempLoading1 = null;
	private static VertexArray vaoTempLoading2 = null;

	private static void renderLoading() {
		if(firstLoadFrame) {
			sTempLoading = new Shader(Utils.filePathShader("loading.vert"), Utils.filePathShader("loading.frag"));
			sTempLoading.enable();
			float[] vertices1 = new float[] {
				-10.0f, -6.0f, 0.0f,
				 10.0f, -6.0f, 0.0f,
				-10.0f, -5.5f, 0.0f,
				 10.0f, -5.5f, 0.0f
			};
			float[] vertices2 = new float[] {
				-10.0f, -5.0f, 0.0f,
				 10.0f, -5.0f, 0.0f,
				-10.0f, -4.5f, 0.0f,
				 10.0f, -4.5f, 0.0f
			};
			int[] indices = new int[] {
				0, 1, 2,
				1, 2, 3
			};
			float[] textureCoordinates = new float[] {
				0, 1,
				1, 1,
				0, 0,
				1, 0
			};
				
			vaoTempLoading1 = new VertexArray(vertices1, indices, textureCoordinates);
			vaoTempLoading2 = new VertexArray(vertices2, indices, textureCoordinates);

			sTempLoading.setUniformMat4f("pr_matrix", PR_MATRIX);
			sTempLoading.setUniform1f("progress", 0);
			firstLoadFrame = false;
		}
		
		sTempLoading.enable();
		sTempLoading.setUniform1f("progress", (float) LoadingManager.progress());
		vaoTempLoading1.render();
		sTempLoading.setUniform1f("progress", (float) LoadingManager.overallProgress());
		vaoTempLoading2.render();
		sTempLoading.disable();
		
		int glError = GL11.glGetError();
		if(glError != GL11.GL_NO_ERROR) {
			Console.print("(LWJGL) OpenGL error: " + glError, 2);
		}
	}

	public static Window getWindowObj() {
		return windowObj;
	}

	public static long getWindowPtr() {
		return windowPtr;
	}
	
	
	private static void renderGame() {
		ArrayList<Layer> layers = GameManager.getLayers();
		for(int i = 0;i < layers.size();i ++) {
			if(layers.get(i).enabled) layers.get(i).render();
		}
	}

	public static Matrix4f getTransformationMatrixForGame() {
		return new Matrix4f().translate(new Vector3f(x/10.0f, y/10.0f, 0));
	}
	
	public static Matrix4f getScalingMatrixForGame() {
		return new Matrix4f().scale(scale, scale, 1);
	}
	
	/*
	CAMERA SETTINGS SECTION
	 */
	private static int x = 0, y = 0;
	private static float scale = 1f;
	
	public static void moveLeft() {
		x++;
	}
	
	public static void moveRight() {
		x--;
	}
	
	public static void moveUp() {
		y--;
	}
	
	public static void moveDown() {
		y++;
	}
	
	public static void scroll(double scroll) {
		scale += scroll/10;
		if(scale < MIN_ZOOM) {
			scale = MIN_ZOOM;
		} else if(scale > MAX_ZOOM) {
			scale = MAX_ZOOM;
		}
	}

	public static float getScale() {
		return scale;
	}
}
