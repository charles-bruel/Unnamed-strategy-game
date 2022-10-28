package chazzvader.game.engine;

import java.io.File;

import chazzvader.game.engine.render.RenderManager;

public class Utils {
	
	public static final String SHADER_PATH = "assets/shaders/";
	public static final String TEXTURE_PATH = "assets/textures/";

	public static String filePathShader(String filePath) {
		filePath = SHADER_PATH+filePath;
		filePath = filePath.replace("/", File.separator);
		return filePath;
	}
	
	public static String filePathTex(String filePath) {
		filePath = TEXTURE_PATH+filePath;
		filePath = filePath.replace("/", File.separator);
		filePath = filePath + ".png";
		return filePath;
	}
	
	private static float X_WIDTH = RenderManager.RIGHT_BOUND - RenderManager.LEFT_BOUND;
	private static float Y_HEIGHT = RenderManager.TOP_BOUND - RenderManager.BOTTOM_BOUND;

	public static float fromXPos(int xpos) {
		return (((float) xpos / (float) RenderManager.getWindowObj().getWidth())*X_WIDTH)-X_WIDTH/2;
	}
	
	public static float fromYPos(int ypos) {
		return (((float) ypos / (float) RenderManager.getWindowObj().getHeight())*Y_HEIGHT)-Y_HEIGHT/2;
	}
	
	private Utils () {}

}
