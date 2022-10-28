package chazzvader.game.engine.layer;

import org.lwjgl.opengl.GL13;

import chazzvader.game.engine.render.RenderManager;
import chazzvader.game.engine.render.RenderableObjectContainerGenerator;
import chazzvader.game.engine.render.TextureManager;
import chazzvader.game.engine.render.TextureManager.Texture;
import chazzvader.lib.helper.render.Shader;
import chazzvader.lib.helper.render.VertexArray;

public class TestLayer extends Layer {
	
	@Override
	public void render() {
		Texture t = TextureManager.ICONS.getTexture();
		//t = TextureManager.FALLBACK;
		t.bind();
		S_TEST1.enable();
		//VA_TEST1.render();
		S_TEST1.disable();
		t.unbind();
	}

	public static Shader S_TEST1;
	public static VertexArray VA_TEST1;
	
	@Override
	public void setup() {
		S_TEST1 = new Shader("assets/shaders/basic.vert", "assets/shaders/basic_transparency.frag");
		
		VA_TEST1 = RenderableObjectContainerGenerator.fromText("QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm0123456789!@#$%^&*()_+-={}|[]\\:\";'<>?,./'", 0.7f, -15.0f, -5.0f, 10.0f).generate().vertexArray;
			
		S_TEST1.enable();
		
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
			
		S_TEST1.setUniformMat4f("pr_matrix", RenderManager.PR_MATRIX);
		S_TEST1.setUniform1i("tex", 1);
	}

	@Override
	public boolean handleKeyEvent(int key, int actionType) {
		return false;
	}

	@Override
	public boolean handleMouseEvent(int key, int actionType) {
		return false;
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean handleMouseScrollEvent(double scroll) {
		return false;
	}

	@Override
	public void regen() {
		
	}

}
