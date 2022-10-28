package chazzvader.game.engine.render;

import chazzvader.lib.helper.render.VertexArray;

public class VertexArrayTemplate {

	private float[] vertices, textureCoordinates;
	private int[] indices;
	
	public VertexArray generate() {
		return new VertexArray(vertices, indices, textureCoordinates);
	}
	
	public VertexArrayTemplate(float[] vertices, float[] textureCoordinates, int[] indices) {
		this.vertices = vertices;
		this.textureCoordinates = textureCoordinates;
		this.indices = indices;
	}

}
