package chazzvader.game.engine.layer;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import chazzvader.game.engine.render.RenderableObjectContainer;

public abstract class BasicLayer extends Layer {

	protected ArrayList<RenderableObjectContainer> objects = new ArrayList<>();
	
	@Override
	public void render() {
		for(int i = 0;i < objects.size();i ++) {
			RenderableObjectContainer roc = objects.get(i);
			roc.render();
		}
		unbindAll();
	}

	private void unbindAll() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL20.glUseProgram(0);
	}

}
