package chazzvader.game.engine.render;

import java.util.ArrayList;

import chazzvader.game.content.ContentBaseGame;
import chazzvader.game.content.Tile;
import chazzvader.game.content.Yields;
import chazzvader.game.engine.render.RenderableObjectContainer.IRenderableObjectContainerPreRender;
import chazzvader.game.engine.render.TextureManager.Texture;
import chazzvader.game.engine.text.TextCharacter;
import chazzvader.game.engine.text.TextCharacters;
import chazzvader.game.other.Console;
import chazzvader.game.other.HexagonData;
import chazzvader.game.other.SubTileCoordinate.SubTile;
import chazzvader.game.sided.both.game.Game;
import chazzvader.lib.helper.render.Shader;
import chazzvader.lib.helper.render.VertexArray;

public class RenderableObjectContainerGenerator {
	
	public static final float TILE_SIZE = 2;
	public static final float SQUISH = 0.99f;
	public static final float RESOURCE_SIZE = 0.15f;
	private static final float H_RESOURCE_SIZE = RESOURCE_SIZE/2;
	public static final float YIELD_SIZE = 1/13f;
	private static final float H_YIELD_SIZE = YIELD_SIZE/2;
	
	private static float[] _tcs = new float[] {
		0, 1,
		1, 1,
		0, 0,
		1, 0
	};
	private static float[] _tcs_inverted = new float[] {
		1, 0,
		0, 0,
		1, 1,
		0, 1
	};

	private static float[] _basic_va = new float[] {
		0, 1, 0,
		1, 1, 0,
		0, 0, 0,
		1, 0, 0
	};
	
	private static float[] _map_vaResource = new float[] {
		0.5f-H_RESOURCE_SIZE, 0.8f+H_RESOURCE_SIZE, 0,
		0.5f+H_RESOURCE_SIZE, 0.8f+H_RESOURCE_SIZE, 0,
		0.5f-H_RESOURCE_SIZE, 0.8f-H_RESOURCE_SIZE, 0,
		0.5f+H_RESOURCE_SIZE, 0.8f-H_RESOURCE_SIZE, 0
	};
	private static int[] _map_ia = new int[] {
		0, 1, 2,
		1, 2, 3
	};
	
	public static ArrayList<RenderableObjectContainerTemplate> fromGame(Game game) {
		long time = System.nanoTime();
		Tile[][] tiles = game.getMap().getTiles();
		tiles[0][0] = ContentBaseGame.TILE_DESERT_MOUNTAIN.get();//TODO 1 Finish debugging
		int featureCount = 0;
		int resourceCount = 0;
		int yieldCount = 0;
		for(int i = 0;i < tiles.length;i ++) {
			Tile[] tileRow = tiles[i];
			for(int j = 0;j < tileRow.length;j ++) {
				Tile tile = tileRow[j];
				if(tile.getFeature() != null) {
					featureCount ++;
				}
				if(tile.getResource() != null) {
					resourceCount ++;
				}
				if(tile.getYields() != null) {
					Yields[] array = tile.getYields();
					for(int k = 0;k < array.length;k ++) {
						if(array[k] != null) {
							yieldCount += array[k].getTextures().length;
						}
					}
				}
			}
		}
		int tileLength = (tiles.length * tiles[0].length) + featureCount;//Assume even 2d array		
		int overlayLength = (tiles.length * tiles[0].length);//Assume even 2d array
		float[] vaTileFeature = new float[tileLength * 12];
		int[] iaTileFeature = new int[tileLength * 6];
		float[] tcaTileFeature = new float[tileLength * 8];
		float[] vaResource = new float[resourceCount * 12];
		int[] iaResource = new int[resourceCount * 6];
		float[] tcaResource = new float[resourceCount * 8];
		float[] vaYield = new float[yieldCount * 12];
		int[] iaYield = new int[yieldCount * 6];
		float[] tcaYield = new float[yieldCount * 8];
		float[] vaOverlay = new float[overlayLength * 12];
		int[] iaOverlay = new int[overlayLength * 6];
		float[] tcaOverlay = new float[overlayLength * 8];
		final double mult = TILE_SIZE/HexagonData.HEXAGON_HEIGHT;
		float[] xOffset = new float[9];
		float[] yOffset = new float[9];
		for(int k = 0;k < 9;k ++) {
			SubTile st = SubTile.fromID(k);
			xOffset[k] = (float) (HexagonData.getXBySubTile(st) *  mult * TILE_SIZE) + TILE_SIZE/2 - H_YIELD_SIZE;
			yOffset[k] = (float) (HexagonData.getYBySubTile(st) *  mult * TILE_SIZE) + TILE_SIZE/2 - H_YIELD_SIZE;
		}
		for(int i = 0, tileFeatureID = 0, resourceID = 0, yieldID = 0, overlayID = 0;i < tiles.length;i ++) {
			Tile[] tileRow = tiles[i];
			for(int j = 0;j < tileRow.length;j ++, tileFeatureID++) {
				Tile tile = tileRow[j];
				float baseX = i*TILE_SIZE*SQUISH - RenderManager.RIGHT_BOUND;
				baseX *= HexagonData.HEXAGON_RATIO;
				if(j%2 == 1) {
					baseX += (0.5*TILE_SIZE) * HexagonData.HEXAGON_RATIO;
				}
				float baseY = -(j*TILE_SIZE*SQUISH - RenderManager.TOP_BOUND);
				baseY *= 0.75;
				vaTileFeature = _map_fillVertexArray(vaTileFeature, baseX, baseY, RenderManager.TILE_Z, tileFeatureID);
				tcaTileFeature = _fillTextureCoordinateArray(tcaTileFeature, tileFeatureID, tile.getParentNode().tex);
				iaTileFeature = _fillIndicesArray(iaTileFeature, tileFeatureID);
				vaOverlay = _map_fillVertexArray(vaOverlay, baseX, baseY, RenderManager.OVERLAY_Z, overlayID);
				tcaOverlay = _fillTextureCoordinateArray(tcaOverlay, overlayID, TextureManager.TILE_OVERLAY);
				iaOverlay = _fillIndicesArray(iaOverlay, overlayID);
				overlayID++;
				if(tile.getFeature() != null) {
					tileFeatureID++;
					vaTileFeature = _map_fillVertexArray(vaTileFeature, baseX, baseY, RenderManager.FEATURE_Z, tileFeatureID);
					tcaTileFeature = _fillTextureCoordinateArray(tcaTileFeature, tileFeatureID, tile.getFeature().getParentNode().tex);
					iaTileFeature = _fillIndicesArray(iaTileFeature, tileFeatureID);
				}
				if(tile.getResource() != null) {
					vaResource = _map_fillVertexArrayResource(vaResource, baseX, baseY, RenderManager.RESOURCE_Z, resourceID);
					tcaResource = _fillTextureCoordinateArray(tcaResource, resourceID, tile.getResource().getParentNode().tex);
					iaResource = _fillIndicesArray(iaResource, resourceID);
					resourceID++;
				}
				Yields[] yields = tile.getYields();
				if(yields == null) {
					continue;
				}
				for(int k = 0;k < yields.length;k ++) {
					if(yields[k] == null) {
						continue;
					}
					Texture[] textures = yields[k].getTextures();
					int gridSize = (int) Math.ceil(Math.sqrt(textures.length));
					for(int l = 0;l < textures.length;l ++) {
						int x = l % gridSize;
						int y = l / gridSize;
						float offsetX = (-(gridSize-1)/2.0f + x)*YIELD_SIZE*TILE_SIZE;
						float offsetY = ( (gridSize-1)/2.0f + y)*YIELD_SIZE*TILE_SIZE;
						vaYield = _map_fillVertexArrayYield(vaYield, baseX+xOffset[k] - H_YIELD_SIZE + offsetX , baseY - yOffset[k] - H_YIELD_SIZE - offsetY, RenderManager.YIELD_Z, yieldID);
						tcaYield = _fillTextureCoordinateArray(tcaYield, yieldID, textures[l]);
						iaYield = _fillIndicesArray(iaYield, yieldID);
						yieldID++;
					}
					
				}
			}
		}
		VertexArrayTemplate vaoTileFeature = new VertexArrayTemplate(vaTileFeature, tcaTileFeature, iaTileFeature);
		VertexArrayTemplate vaoResource = new VertexArrayTemplate(vaResource, tcaResource, iaResource);
		VertexArrayTemplate vaoYield = new VertexArrayTemplate(vaYield, tcaYield, iaYield);
		VertexArrayTemplate vaoOverlay = new VertexArrayTemplate(vaOverlay, tcaOverlay, iaOverlay);
		ArrayList<RenderableObjectContainerTemplate> ret = new ArrayList<>();
		IRenderableObjectContainerPreRender iapTileFeature = new IRenderableObjectContainerPreRender() {
			
			final float w = tiles.length*HexagonData.HEXAGON_RATIO*TILE_SIZE*SQUISH;//Dunno but it works
			
			@Override
			public void run(Shader shader, Texture texture, VertexArray vertexArray) {
				shader.setUniform1i("render", 1);
				shader.setUniform1f("wrapWidth", w);
				shader.setUniformMat4f("tr_matrix", RenderManager.getTransformationMatrixForGame());
				shader.setUniformMat4f("sc_matrix", RenderManager.getScalingMatrixForGame());
				shader.setUniform1f("scale", RenderManager.getScale());
				shader.setUniform1f("offSize", 1f);
			}
		};
		IRenderableObjectContainerPreRender iapResource = new IRenderableObjectContainerPreRender() {
			
			final float w = tiles.length*HexagonData.HEXAGON_RATIO*TILE_SIZE*SQUISH;//Dunno but it works
			
			@Override
			public void run(Shader shader, Texture texture, VertexArray vertexArray) {
				shader.setUniform1i("render", 1);
				shader.setUniform1f("wrapWidth", w);
				shader.setUniformMat4f("tr_matrix", RenderManager.getTransformationMatrixForGame());
				shader.setUniformMat4f("sc_matrix", RenderManager.getScalingMatrixForGame());
				shader.setUniform1f("scale", RenderManager.getScale());
				shader.setUniform1f("offSize", RESOURCE_SIZE);
			}
		};
		IRenderableObjectContainerPreRender iapYield = new IRenderableObjectContainerPreRender() {
			
			final float w = tiles.length*HexagonData.HEXAGON_RATIO*TILE_SIZE*SQUISH;//Dunno but it works
			
			@Override
			public void run(Shader shader, Texture texture, VertexArray vertexArray) {
				if(RenderManager.getScale() < 1.5) {
					shader.setUniform1i("render", 0);
				} else {
					shader.setUniform1i("render", 1);
					shader.setUniform1f("wrapWidth", w);
					shader.setUniformMat4f("tr_matrix", RenderManager.getTransformationMatrixForGame());
					shader.setUniformMat4f("sc_matrix", RenderManager.getScalingMatrixForGame());
					shader.setUniform1f("scale", RenderManager.getScale());
					shader.setUniform1f("offSize", YIELD_SIZE);
				}
			}
		};
		IRenderableObjectContainerPreRender iapOverlay = new IRenderableObjectContainerPreRender() {
			
			final float w = tiles.length*HexagonData.HEXAGON_RATIO*TILE_SIZE*SQUISH;//Dunno but it works
			
			@Override
			public void run(Shader shader, Texture texture, VertexArray vertexArray) {
				if(RenderManager.getScale() < 1.5) {
					shader.setUniform1i("render", 0);
				} else {
					shader.setUniform1i("render", 1);
					shader.setUniform1f("wrapWidth", w);
					shader.setUniformMat4f("tr_matrix", RenderManager.getTransformationMatrixForGame());
					shader.setUniformMat4f("sc_matrix", RenderManager.getScalingMatrixForGame());
					shader.setUniform1f("scale", RenderManager.getScale());
					shader.setUniform1f("offSize", 1f);
				}
			}
		};
		ret.add(new RenderableObjectContainerTemplate(vaoTileFeature, TextureManager.TILES.getTexture(), ShaderManager.WORLD_SHADER, iapTileFeature));
		ret.add(new RenderableObjectContainerTemplate(vaoResource, TextureManager.ICONS.getTexture(), ShaderManager.WORLD_SHADER, iapResource));
		ret.add(new RenderableObjectContainerTemplate(vaoYield, TextureManager.ICONS.getTexture(), ShaderManager.WORLD_SHADER, iapYield));
		ret.add(new RenderableObjectContainerTemplate(vaoOverlay, TextureManager.TILES.getTexture(), ShaderManager.WORLD_SHADER, iapOverlay));
		double duration = (System.nanoTime()-time)/1000000.0;
		Console.print("(ROCG) Created map in "+ duration +"ms", -1);
		return ret;
	}
	
	private static float[] _map_fillVertexArrayYield(float[] vertexArray, float baseX, float baseY, float z, int id) {
		int baseID = id *12;
		for(int k = 0;k < _map_vaResource.length;k += 3) {
			vertexArray[baseID+k  ] = baseX + (_basic_va[k  ]*YIELD_SIZE * TILE_SIZE);
			vertexArray[baseID+k+1] = baseY + (_basic_va[k+1]*YIELD_SIZE) * TILE_SIZE;
			vertexArray[baseID+k+2] = z;
		}
		return vertexArray;
	}

	private static float[] _map_fillVertexArray(float[] vertexArray, float baseX, float baseY, float z, int id) {
		int baseID = id *12;
		for(int k = 0;k < _basic_va.length;k += 3) {
			vertexArray[baseID+k  ] = baseX + _basic_va[k  ] * TILE_SIZE;
			vertexArray[baseID+k+1] = baseY - _basic_va[k+1] * TILE_SIZE;
			vertexArray[baseID+k+2] = z;
		}
		return vertexArray;
	}
	
	private static float[] _map_fillVertexArrayResource(float[] vertexArray, float baseX, float baseY, float z, int id) {
		int baseID = id *12;
		for(int k = 0;k < _map_vaResource.length;k += 3) {
			vertexArray[baseID+k  ] = baseX + _map_vaResource[k  ] * TILE_SIZE;
			vertexArray[baseID+k+1] = baseY - _map_vaResource[k+1] * TILE_SIZE;
			vertexArray[baseID+k+2] = z;
		}
		return vertexArray;
	}
	
	private static float[] _fillTextureCoordinateArray(float[] textureCoordinateArray, int id, Texture texture) {
		int baseID = id * 8;
		float lxtc = texture.lowerXTC();
		float uxtc = texture.upperXTC();
		float lytc = texture.lowerYTC();
		float uytc = texture.upperYTC();
		for(int k = 0;k < _tcs.length;k += 2) {
			textureCoordinateArray[baseID+k  ] += _tcs[k  ] * uxtc;
			textureCoordinateArray[baseID+k  ] += _tcs_inverted[k  ] * lxtc;
			textureCoordinateArray[baseID+k+1] += _tcs[k+1] * uytc;
			textureCoordinateArray[baseID+k+1] += _tcs_inverted[k+1] * lytc;
		}
		return textureCoordinateArray;
	}
	
	private static int[] _fillIndicesArray(int[] indiceArray, int id) {
		int baseI = id * 4;
		int baseID = id * 6;
		for(int k = 0;k < _map_ia.length;k ++) {
			indiceArray[baseID+k] = baseI+_map_ia[k];
		}
		return indiceArray;
	}
	
	public static RenderableObjectContainerTemplate fromText(String text, float textSize, float x, float y) {
		return fromText(text, textSize, x, y, Float.MAX_VALUE);
	}
	
	public static RenderableObjectContainerTemplate fromText(String text, float textSize, float x, float y, float maxWidth) {//TODO 1 Add non-single-char strings
		ArrayList<TextCharacter> characters = new ArrayList<>();//TODO 1 Add spacing betweem every third number
		char[] charArray = text.toCharArray();
		for(int i = 0;i < charArray.length;i ++) {
			characters.add(TextCharacters.getByChar(charArray[i]));
		}
		int length = characters.size();
		float[] vertexArray = new float[length*12];
		int[] indiceArray = new int[length*6];
		float[] textureCorodinateArray = new float[length*8];
		int numCount = 0;
		maxWidth += x;
		float newX = x;
		for(int i = 0;i < length;i ++) {
			TextCharacter tc = characters.get(i);
			if(tc.number) {
				numCount++;
			} else {
				numCount = 0;
			}
			newX += (textSize*tc.width)/2;
			vertexArray = _text_fillVertexArray(vertexArray, newX, y, RenderManager.UI_TEXT_Z, i, textSize);
			indiceArray = _fillIndicesArray(indiceArray, i);
			textureCorodinateArray = _fillTextureCoordinateArray(textureCorodinateArray, i, tc.linkTex);
			newX += (textSize*tc.width)/2;
			if(numCount == 3) {
				numCount = 0;
				newX += textSize/5;
			}
			if(newX >= maxWidth) {
				newX = x;
				y -= textSize;
			}
		}
		
		VertexArrayTemplate vertexArrayObject = new VertexArrayTemplate(vertexArray, textureCorodinateArray, indiceArray);
		return new RenderableObjectContainerTemplate(vertexArrayObject, TextureManager.ICONS.getTexture(), ShaderManager.TEXTURE_TRANSPARENCY);
	}
	
	private static float[] _text_fillVertexArray(float[] vertexArray, float baseX, float baseY, float z, int id, float textSize) {
		int baseID = id *12;
		for(int k = 0;k < _basic_va.length;k += 3) {
			vertexArray[baseID+k  ] = baseX + _basic_va[k  ]*textSize;
			vertexArray[baseID+k+1] = baseY + _basic_va[k+1]*textSize;
			vertexArray[baseID+k+2] = z;
		}
		return vertexArray;
	}
	
	private static float[] _fillVertexArray(float[] vertexArray, float x1, float y1, float x2, float y2, float z, int id) {
		int baseID = id *12;
		for(int k = 0;k < _basic_va.length;k += 3) {
			vertexArray[baseID+k  ] = _basic_va[k  ] == 1 ? x2 : x1;
			vertexArray[baseID+k+1] = _basic_va[k+1] == 1 ? y2 : y1;
			vertexArray[baseID+k+2] = z;
		}
		return vertexArray;
	}
	
	public static RenderableObjectContainerTemplate fromTexture(Texture texture, float x1, float y1, float x2, float y2, float z) {
		VertexArrayTemplate vertexArrayObject = new VertexArrayTemplate(_fillVertexArray(new float[12], x1, y1, x2, y2, z, 0), _fillTextureCoordinateArray(new float[8], 0, texture), _fillIndicesArray(new int[6], 0));
		return new RenderableObjectContainerTemplate(vertexArrayObject, texture, ShaderManager.TEXTURE_TRANSPARENCY);
	}

}
