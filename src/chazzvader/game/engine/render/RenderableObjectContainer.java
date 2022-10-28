package chazzvader.game.engine.render;

import chazzvader.game.engine.render.TextureManager.Texture;
import chazzvader.lib.helper.render.Shader;
import chazzvader.lib.helper.render.VertexArray;

public class RenderableObjectContainer {
	
	/**
	 * @param vertexArray
	 * @param texture
	 * @param shader
	 */
	public RenderableObjectContainer(VertexArray vertexArray, Texture texture, Shader shader) {
		this.vertexArray = vertexArray;
		this.texture = texture;
		this.shader = shader;
		this.name = "ROC0x"+Integer.toHexString(currentID);
		currentID++;
	}
	
	public RenderableObjectContainer(VertexArray vertexArray, Texture texture, Shader shader, IRenderableObjectContainerPreRender pre) {
		this(vertexArray, texture, shader);
		this.pre = pre;
	}
	
	private static int currentID = 0;
	
	public RenderableObjectContainer() {}

	public IRenderableObjectContainerPreRender pre = null;
	
	public VertexArray vertexArray;
	public Texture texture;
	public Shader shader;
	
	public boolean enabled = true;
	
	public String name;

	public void render() {
		if(!enabled) return;
		texture.bind();
		shader.enable();
		if(pre != null) {
			pre.run(shader, texture, vertexArray);
		}
		vertexArray.render();
	}

	@Override
	public String toString() {
		return "RenderableObjectContainer [vertexArray=" + vertexArray + ", texture=" + texture + ", shader=" + shader
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pre == null) ? 0 : pre.hashCode());
		result = prime * result + ((shader == null) ? 0 : shader.hashCode());
		result = prime * result + ((texture == null) ? 0 : texture.hashCode());
		result = prime * result + ((vertexArray == null) ? 0 : vertexArray.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RenderableObjectContainer other = (RenderableObjectContainer) obj;
		if (enabled != other.enabled)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pre == null) {
			if (other.pre != null)
				return false;
		} else if (!pre.equals(other.pre))
			return false;
		if (shader == null) {
			if (other.shader != null)
				return false;
		} else if (!shader.equals(other.shader))
			return false;
		if (texture == null) {
			if (other.texture != null)
				return false;
		} else if (!texture.equals(other.texture))
			return false;
		if (vertexArray == null) {
			if (other.vertexArray != null)
				return false;
		} else if (!vertexArray.equals(other.vertexArray))
			return false;
		return true;
	}
	
	public interface IRenderableObjectContainerPreRender {
		
		public void run(Shader shader, Texture texture, VertexArray vertexArray);
		
	}
	
}