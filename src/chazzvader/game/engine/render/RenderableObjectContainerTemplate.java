package chazzvader.game.engine.render;

import chazzvader.game.engine.render.RenderableObjectContainer.IRenderableObjectContainerPreRender;
import chazzvader.game.engine.render.TextureManager.Texture;
import chazzvader.lib.helper.render.Shader;

public class RenderableObjectContainerTemplate {
	
	public IRenderableObjectContainerPreRender pre = null;
	
	public VertexArrayTemplate vertexArray;
	public Texture texture;
	public Shader shader;
	
	public RenderableObjectContainer generate() {
		return new RenderableObjectContainer(vertexArray.generate(), texture.getRenderTexture(), shader, pre);
	}

	public RenderableObjectContainerTemplate(VertexArrayTemplate vertexArray, Texture texture,
			Shader shader, IRenderableObjectContainerPreRender pre) {
		this.pre = pre;
		this.vertexArray = vertexArray;
		this.texture = texture;
		this.shader = shader;
	}
	
	public RenderableObjectContainerTemplate(VertexArrayTemplate vertexArray,
			Texture texture, Shader shader) {
		this.vertexArray = vertexArray;
		this.texture = texture;
		this.shader = shader;
	}

}
