package chazzvader.game.engine.render;

import org.lwjgl.opengl.GL13;

import chazzvader.game.engine.Utils;
import chazzvader.game.other.Console;
import chazzvader.lib.helper.render.Shader;

public class ShaderManager {
	
	private ShaderManager() {}
	
	public static void init() {
		Console.print("(Shader Manager) Initializing", 0);
	}
	
	public static final Shader TEXTURE_TRANSPARENCY = new Shader(Utils.filePathShader("basic.vert"), Utils.filePathShader("basic_transparency.frag"));
	public static final Shader WORLD_SHADER = new Shader(Utils.filePathShader("world.vert"), Utils.filePathShader("basic_transparency.frag"));

	static {
		TEXTURE_TRANSPARENCY.enable();
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		TEXTURE_TRANSPARENCY.setUniformMat4f("pr_matrix", RenderManager.PR_MATRIX);
		TEXTURE_TRANSPARENCY.setUniform1i("tex", 1);
		WORLD_SHADER.enable();
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		WORLD_SHADER.setUniformMat4f("pr_matrix", RenderManager.PR_MATRIX);
		WORLD_SHADER.setUniform1i("tex", 1);
	}
	
}
