package chazzvader.game.engine.render;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

import chazzvader.game.engine.Utils;
import chazzvader.game.other.Console;
import chazzvader.lib.helper.other.BufferUtils;

public class TextureManager {
		
	private static ArrayList<Texture> textures = new ArrayList<>();
	private static int textureCount = 0;
	private static int totalPreLoaded = 0;
	private static int totalLoaded = 0;
	
	public static TextureAtlas
		TILES = new TextureAtlas(1024, 8, "Tiles"),
		ICONS = new TextureAtlas(128, 16, "Icons");

	//Tex list
	public static Texture 
		FALLBACK = new Texture("other/fallback", null),
		BACKGROUND_MENU = new Texture("background/menu", null),
		TILE_GRASSLAND = new Texture("tiles/grassland", TILES),
		TILE_GRASSLAND_HILL = new Texture("tiles/grassland_hill", TILES),
		TILE_GRASSLAND_MOUNTAIN = new Texture("tiles/grassland_mountain", TILES),
		TILE_PLAINS = new Texture("tiles/plains", TILES),
		TILE_PLAINS_HILL = new Texture("tiles/plains_hill", TILES),
		TILE_PLAINS_MOUNTAIN = new Texture("tiles/plains_mountain", TILES),
		TILE_DESERT = new Texture("tiles/desert", TILES),
		TILE_DESERT_HILL = new Texture("tiles/desert_hill", TILES),
		TILE_DESERT_MOUNTAIN = new Texture("tiles/desert_mountain", TILES),
		TILE_SNOW = new Texture("tiles/snow", TILES),
		TILE_SNOW_HILL = new Texture("tiles/snow_hill", TILES),
		TILE_SNOW_MOUNTAIN = new Texture("tiles/snow_mountain", TILES),
		TILE_TUNDRA = new Texture("tiles/tundra", TILES),
		TILE_TUNDRA_HILL = new Texture("tiles/tundra_hill", TILES),
		TILE_TUNDRA_MOUNTAIN = new Texture("tiles/tundra_mountain", TILES),
		TILE_OCEAN = new Texture("tiles/ocean", TILES),
		TILE_COAST = new Texture("tiles/coast", TILES),
		TILE_UNDISCOVERED = new Texture("tiles/undiscovered", TILES),
		TILE_OVERLAY = new Texture("tiles/overlay", TILES),
		FEATURE_WOODS = new Texture("tiles/woods", TILES),
		FEATURE_PINE_WOODS = new Texture("tiles/pine_woods", TILES),
		FEATURE_RAINFOREST = new Texture("tiles/rainforest", TILES),
		FEATURE_OASIS = new Texture("tiles/oasis", TILES),
		RESOURCE_DEER = new Texture("tiles/resources/deer", ICONS),
		RESOURCE_DIAMOND = new Texture("tiles/resources/diamond", ICONS),
		RESOURCE_IRON = new Texture("tiles/resources/iron", ICONS),
		RESOURCE_OIL = new Texture("tiles/resources/oil", ICONS),
		RESOURCE_URANIUM = new Texture("tiles/resources/uranium", ICONS),
		ICON_q = new Texture("icons/text/lq", ICONS),
		ICON_Q = new Texture("icons/text/uq", ICONS),
		ICON_w = new Texture("icons/text/lw", ICONS),
		ICON_W = new Texture("icons/text/uw", ICONS),
		ICON_e = new Texture("icons/text/le", ICONS),
		ICON_E = new Texture("icons/text/ue", ICONS),
		ICON_r = new Texture("icons/text/lr", ICONS),
		ICON_R = new Texture("icons/text/ur", ICONS),
		ICON_t = new Texture("icons/text/lt", ICONS),
		ICON_T = new Texture("icons/text/ut", ICONS),
		ICON_y = new Texture("icons/text/ly", ICONS),
		ICON_Y = new Texture("icons/text/uy", ICONS),
		ICON_u = new Texture("icons/text/lu", ICONS),
		ICON_U = new Texture("icons/text/uu", ICONS),
		ICON_i = new Texture("icons/text/li", ICONS),
		ICON_I = new Texture("icons/text/ui", ICONS),
		ICON_o = new Texture("icons/text/lo", ICONS),
		ICON_O = new Texture("icons/text/uo", ICONS),
		ICON_p = new Texture("icons/text/lp", ICONS),
		ICON_P = new Texture("icons/text/up", ICONS),
		ICON_a = new Texture("icons/text/la", ICONS),
		ICON_A = new Texture("icons/text/ua", ICONS),
		ICON_s = new Texture("icons/text/ls", ICONS),
		ICON_S = new Texture("icons/text/us", ICONS),
		ICON_d = new Texture("icons/text/ld", ICONS),
		ICON_D = new Texture("icons/text/ud", ICONS),
		ICON_f = new Texture("icons/text/lf", ICONS),
		ICON_F = new Texture("icons/text/uf", ICONS),
		ICON_g = new Texture("icons/text/lg", ICONS),
		ICON_G = new Texture("icons/text/ug", ICONS),
		ICON_h = new Texture("icons/text/lh", ICONS),
		ICON_H = new Texture("icons/text/uh", ICONS),
		ICON_j = new Texture("icons/text/lj", ICONS),
		ICON_J = new Texture("icons/text/uj", ICONS),
		ICON_k = new Texture("icons/text/lk", ICONS),
		ICON_K = new Texture("icons/text/uk", ICONS),
		ICON_l = new Texture("icons/text/ll", ICONS),
		ICON_L = new Texture("icons/text/ul", ICONS),
		ICON_z = new Texture("icons/text/lz", ICONS),
		ICON_Z = new Texture("icons/text/uz", ICONS),
		ICON_x = new Texture("icons/text/lx", ICONS),
		ICON_X = new Texture("icons/text/ux", ICONS),
		ICON_c = new Texture("icons/text/lc", ICONS),
		ICON_C = new Texture("icons/text/uc", ICONS),
		ICON_v = new Texture("icons/text/lv", ICONS),
		ICON_V = new Texture("icons/text/uv", ICONS),
		ICON_b = new Texture("icons/text/lb", ICONS),
		ICON_B = new Texture("icons/text/ub", ICONS),
		ICON_n = new Texture("icons/text/ln", ICONS),
		ICON_N = new Texture("icons/text/un", ICONS),
		ICON_m = new Texture("icons/text/lm", ICONS),
		ICON_M = new Texture("icons/text/um", ICONS),
		ICON_0 = new Texture("icons/text/0", ICONS),
		ICON_1 = new Texture("icons/text/1", ICONS),
		ICON_2 = new Texture("icons/text/2", ICONS),
		ICON_3 = new Texture("icons/text/3", ICONS),
		ICON_4 = new Texture("icons/text/4", ICONS),
		ICON_5 = new Texture("icons/text/5", ICONS),
		ICON_6 = new Texture("icons/text/6", ICONS),
		ICON_7 = new Texture("icons/text/7", ICONS),
		ICON_8 = new Texture("icons/text/8", ICONS),
		ICON_9 = new Texture("icons/text/9", ICONS),
		ICON_AND = new Texture("icons/text/and", ICONS),
		ICON_ASTERISK = new Texture("icons/text/asterisk", ICONS),
		ICON_AT = new Texture("icons/text/at", ICONS),
		ICON_BACKSLASH = new Texture("icons/text/backslash", ICONS),
		ICON_CARROT = new Texture("icons/text/carrot", ICONS),
		ICON_COLON = new Texture("icons/text/colon", ICONS),
		ICON_COMMA = new Texture("icons/text/comma", ICONS),
		ICON_DASH = new Texture("icons/text/dash", ICONS),
		ICON_DOLLAR_SIGN = new Texture("icons/text/dollar-sign", ICONS),
		ICON_DOUBLE_QUOTES = new Texture("icons/text/double-quotes", ICONS),
		ICON_EQUALS = new Texture("icons/text/equals", ICONS),
		ICON_EXCLAMATION_MARK = new Texture("icons/text/exclamation-mark", ICONS),
		ICON_HASHTAG = new Texture("icons/text/hashtag", ICONS),
		ICON_LEFT_ANGLE_BRACKET = new Texture("icons/text/left-angle-bracket", ICONS),
		ICON_LEFT_CURLY_BRACKET = new Texture("icons/text/left-curly-bracket", ICONS),
		ICON_LEFT_PARENTHESES = new Texture("icons/text/left-parentheses", ICONS),
		ICON_LEFT_SQUARE_BRACKET = new Texture("icons/text/left-square-bracket", ICONS),
		ICON_PERCENT = new Texture("icons/text/percent", ICONS),
		ICON_PEROID = new Texture("icons/text/peroid", ICONS),
		ICON_PIPE = new Texture("icons/text/pipe", ICONS),
		ICON_PLUS = new Texture("icons/text/plus", ICONS),
		ICON_QUESTION_MARK = new Texture("icons/text/question-mark", ICONS),
		ICON_QUOTE = new Texture("icons/text/quote", ICONS),
		ICON_RIGHT_ANGLE_BRACKET = new Texture("icons/text/right-angle-bracket", ICONS),
		ICON_RIGHT_CURLY_BRACKET = new Texture("icons/text/right-curly-bracket", ICONS),
		ICON_RIGHT_PARENTHESES = new Texture("icons/text/right-parentheses", ICONS),
		ICON_RIGHT_SQUARE_BRACKET = new Texture("icons/text/right-square-bracket", ICONS),
		ICON_SEMICOLON = new Texture("icons/text/semicolon", ICONS),
		ICON_SLASH = new Texture("icons/text/slash", ICONS),
		ICON_UNDERSCORE = new Texture("icons/text/underscore", ICONS),
		ICON_CULTURE = new Texture("icons/culture", ICONS),
		ICON_FAITH = new Texture("icons/faith", ICONS),
		ICON_FOOD = new Texture("icons/food", ICONS),
		ICON_GOLD = new Texture("icons/gold", ICONS),
		ICON_PRODUCTION = new Texture("icons/production", ICONS),
		ICON_SCIENCE = new Texture("icons/science", ICONS),
		ICON_CULTURE_SIMPLE = new Texture("icons/culture_simple", ICONS),
		ICON_FAITH_SIMPLE = new Texture("icons/faith_simple", ICONS),
		ICON_FOOD_SIMPLE = new Texture("icons/food_simple", ICONS),
		ICON_GOLD_SIMPLE = new Texture("icons/gold_simple", ICONS),
		ICON_PRODUCTION_SIMPLE = new Texture("icons/production_simple", ICONS),
		ICON_SCIENCE_SIMPLE = new Texture("icons/science_simple", ICONS),
		ICON_CULTURE_3x = new Texture("icons/culture_3x", ICONS),
		ICON_FAITH_3x = new Texture("icons/faith_3x", ICONS),
		ICON_FOOD_3x = new Texture("icons/food_3x", ICONS),
		ICON_GOLD_3x = new Texture("icons/gold_3x", ICONS),
		ICON_PRODUCTION_3x = new Texture("icons/production_3x", ICONS),
		ICON_SCIENCE_3x = new Texture("icons/science_3x", ICONS),
		ICON_BLANK = new Texture("blank", ICONS);


	
	public static void init() {
		Console.print("(Texture Manager) Initilized the texture manager", -1);
	}
	
	public static void preLoad() {
		if(loadingState != LoadingState.NONE) {
			Console.print("(Texture Manager) Textures already pre-loaded", 1);
			return;
		}
		Thread loadingThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				preLoadInternal();
				donePreLoading = true;
			}
		});
		loadingThread.setName("Texture Loading Thread");
		loadingThread.start();
	}
	
	private static boolean loadComplete = false;
	
	private static void preLoadInternal() {
		loadingState = LoadingState.PRE_LOAD;
		for(int i = 0;i < textures.size();i ++) {
			Texture texture = textures.get(i);
			if(texture != null && texture.loadingState == LoadingState.NONE) {
				texture.preLoad();
			}
		}
		loadingState = LoadingState.PRE_LOAD_COMPLETE;
	}
	
	public static void loadAll() {
		if(!donePreLoading) {
			Console.print("Preloading not completed", 1);
			return;
		}
		if(loadingState != LoadingState.PRE_LOAD_COMPLETE) {
			Console.print("(Texture Manager) Textures already loaded", 1);
			return;
		}
		loadingState = LoadingState.LOAD;
		for(int i = 0;i < textures.size();i ++) {
			Texture texture = textures.get(i);
			if(texture != null && texture.loadingState == LoadingState.PRE_LOAD_COMPLETE) {
				texture.load();
			}
		}
		loadingState = LoadingState.LOAD_COMPLETE;
		loadComplete = true;
	}
	
	private static int currentLoadID = 0;
	
	public static void load(int count) {
		if(!donePreLoading) {
			Console.print("Preloading not completed", 1);
			return;
		}
		if(loadingState != LoadingState.COPY_COMPLETE && loadingState != LoadingState.LOAD) {
			Console.print("(Texture Manager) Textures already loaded", 1);
			return;
		}
		loadingState = LoadingState.LOAD;
		for(int numLoaded = 0;currentLoadID < textures.size() && numLoaded < count;currentLoadID ++, numLoaded ++) {
			Texture texture = textures.get(currentLoadID);
			if(texture != null) {
				texture.load();
			}
		}
		if(currentLoadID >= textures.size()-1) {
			loadingState = LoadingState.LOAD_COMPLETE;
			loadComplete = true;
		}
	}
	
	private static LoadingState loadingState = LoadingState.NONE;
	
	/**
	 * @return A number 0-1, inclusive
	 */
	public static double progress() {
		int loaded = 0;
		switch(loadingState) {
		case LOAD:
			loaded = totalLoaded;
			break;
		case LOAD_COMPLETE:
			return 1;
		case NONE:
			return 0;
		case PRE_LOAD:
			loaded = totalPreLoaded;
			break;
		case PRE_LOAD_COMPLETE:
			return 1;
		case COPY:
			loaded = texturesCopied;
			break;
		case COPY_COMPLETE:
			return 1;
		default:
			break;
		}
		
		return (double) loaded/(double) textureCount;
	}
	
	private static boolean donePreLoading = false;
	
	public static boolean donePreLoading() {
		return donePreLoading;
	}
		
	public static class Texture {
		
		private LoadingState loadingState = LoadingState.NONE;
		
		private int atlasIndex = 0;
		
		private int[] data;
		
		public Texture getRenderTexture() {
			if(atlas == null) {
				return this;
			} else {
				return atlas.texture;
			}
		}
		
		public float lowerXTC() {
			if(atlas == null) {
				return 0;
			} else {
				return (float) (atlasIndex % atlas.atlasSize)/(float) (atlas.atlasSize);
			}
		}
		
		public float lowerYTC() {
			if(atlas == null) {
				return 0;
			} else {
				return (float) (atlasIndex / atlas.atlasSize)/(float) (atlas.atlasSize);
			}
		}
		
		public float upperXTC() {
			if(atlas == null) {
				return 1;
			} else {
				return (float) ((atlasIndex % atlas.atlasSize) + 1)/(float) (atlas.atlasSize);
			}
		}
		
		public float upperYTC() {
			if(atlas == null) {
				return 1;
			} else {
				return (float) ((atlasIndex / atlas.atlasSize) + 1)/(float) (atlas.atlasSize);
			}
		}
		
		public float[] textureCoordinatesf() {
			float lxtc, lytc, uxtc, uytc;
			lxtc = lowerXTC();
			lytc = lowerYTC();
			uxtc = upperXTC();
			uytc = upperYTC();
			return new float[] {
				lxtc, uytc,
				uxtc, uytc,
				lxtc, lytc,
				uxtc, lytc
			};
		}
		
		public Vector2f[] textureCoordinatesVec2f() {
			float lxtc, lytc, uxtc, uytc;
			lxtc = lowerXTC();
			lytc = lowerYTC();
			uxtc = upperXTC();
			uytc = upperYTC();
			return new Vector2f[] {
				new Vector2f(lxtc, uytc),
				new Vector2f(uxtc, uytc),
				new Vector2f(lxtc, lytc),
				new Vector2f(uxtc, lytc)
			};
		}
		
		public void preLoad() {
			Console.print("(Texture Manager) Pre-loading texture "+name, -1);
			if(loadingState != LoadingState.NONE) {
				Console.print("(Texture Manager) Already pre-loaded texture "+name, 1);
				return;
			}
			if(isAtlas) {
				Console.print("(Texture Manager) Texture not valid for pre-load ", -1);
				return;
			}
			int[] pixels = null;
			int totalPixels = 0;
			try {
				BufferedImage image = ImageIO.read(new FileInputStream(filePath));
				width = image.getWidth();
				height = image.getHeight();
				totalPixels = width*height;
				pixels = new int[totalPixels];
				image.getRGB(0, 0, width, height, pixels, 0, width);
			} catch(IOException e) {
				if(this == FALLBACK) {
					Console.error(e, true);
				} else {
					Console.error(e, false);
					data = FALLBACK.data;
				}
				return;
			}
			
			data = new int[totalPixels];
			for(int i = 0;i < totalPixels;i ++) {
				int a = (pixels[i] & 0xff000000) >> 24;
				int r = (pixels[i] & 0xff0000) >> 16;
				int g = (pixels[i] & 0xff00) >> 8;
				int b = (pixels[i] & 0xff);
				
				data[i] = a << 24 | b << 16 | g << 8 | r;
			}
			
			preLoaded = true;
			totalPreLoaded ++;
			loadingState = LoadingState.PRE_LOAD_COMPLETE;
			Console.print("(Texture Manager) Preloaded texture "+name, -1);
		}
		
		public void load() {
			Console.print("(Texture Manager) Loading texture "+name, -1);
			if(atlas != null) {
				Console.print("(Texture Manager) Texture not valid for load ", -1);
				totalLoaded ++;
				return;
			}
			if(!isAtlas && loadingState != LoadingState.PRE_LOAD_COMPLETE) {
				Console.print("(Texture Manager) Texture not pre-loaded ", 1);
				return;
			}
			
			glTextureID = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, glTextureID);
			
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
						
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

			loaded = true;
			totalLoaded ++;
			loadingState = LoadingState.LOAD_COMPLETE;
			Console.print("(Texture Manager) Loaded texture "+name, -1);
			data = null;
		}
		
		private int gameTextureId, glTextureID;
		private int width, height;
		
		private String name;
		private String filePath;
		private TextureAtlas atlas;
		
		public Texture(String filePath, TextureAtlas atlas) {
			this((byte) 0);
			this.atlas = atlas;
			filePath = Utils.filePathTex(filePath);
			this.filePath = filePath;
			this.name = filePath;
		}
		
		private boolean isAtlas = false;
		
		private Texture(String atlasName) {
			this((byte) 0);
			isAtlas = true;
			this.name = atlasName;
		}

		public void bind() {
			if(loadingState != LoadingState.LOAD_COMPLETE) {
				Console.print("(Texture Manager) Tried to use unloaded texture", 1);
				return;
			}
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, glTextureID);
		}
		
		public void unbind() {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		}
		
		/**
		 * DO NOT USE
		 */
		private Texture(byte dummy) {
			if(loadingState != LoadingState.NONE) {
				Console.print("(Texture Manager) Textures are already loaded! This texture will not work!", 1);
			}
			this.atlas = null;
			gameTextureId = textureCount++;
			textures.add(this);
		}
		
		public int getGameTextureId() {
			return gameTextureId;
		}
		
		public int getGlTextureID() {
			return glTextureID;
		}
		
		private boolean preLoaded = false, loaded = false;
		
		public boolean isLoaded() {
			return loaded;
		}
		
		public boolean isPreLoaded() {
			return preLoaded;
		}
		
		public int getWidth() {
			return width;
		}
		
		public int getHeight() {
			return height;
		}

		@Override
		public String toString() {
			return "Texture [loadingState=" + loadingState + ", atlasIndex=" + atlasIndex + ", gameTextureId="
					+ gameTextureId + ", glTextureID=" + glTextureID + ", width=" + width + ", height=" + height
					+ ", name=" + name + ", filePath=" + filePath + ", atlas=" + atlas + ", isAtlas=" + isAtlas
					+ ", preLoaded=" + preLoaded + ", loaded=" + loaded + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((atlas == null) ? 0 : atlas.hashCode());
			result = prime * result + atlasIndex;
			result = prime * result + Arrays.hashCode(data);
			result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
			result = prime * result + gameTextureId;
			result = prime * result + glTextureID;
			result = prime * result + height;
			result = prime * result + (isAtlas ? 1231 : 1237);
			result = prime * result + (loaded ? 1231 : 1237);
			result = prime * result + ((loadingState == null) ? 0 : loadingState.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + (preLoaded ? 1231 : 1237);
			result = prime * result + width;
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
			Texture other = (Texture) obj;
			if (atlas == null) {
				if (other.atlas != null)
					return false;
			} else if (!atlas.equals(other.atlas))
				return false;
			if (atlasIndex != other.atlasIndex)
				return false;
			if (!Arrays.equals(data, other.data))
				return false;
			if (filePath == null) {
				if (other.filePath != null)
					return false;
			} else if (!filePath.equals(other.filePath))
				return false;
			if (gameTextureId != other.gameTextureId)
				return false;
			if (glTextureID != other.glTextureID)
				return false;
			if (height != other.height)
				return false;
			if (isAtlas != other.isAtlas)
				return false;
			if (loaded != other.loaded)
				return false;
			if (loadingState != other.loadingState)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (preLoaded != other.preLoaded)
				return false;
			if (width != other.width)
				return false;
			return true;
		}
		
	}
	
	public static int getXCoord(int index, Texture texture) {
		return getXCoord(index, texture.width, texture.height);
	}
	
	public static int getXCoord(int index, int width, int height) {
		return index % width;
	}
	
	public static int getYCoord(int index, Texture texture) {
		return getXCoord(index, texture.width, texture.height);
	}
	
	public static int getYCoord(int index, int width, int height) {
		return index / width;
	}
	
	public static int toIndex(int x, int y, Texture texture) {
		return toIndex(x, y, texture.width, texture.height);
	}
	
	public static int toIndex(int x, int y, int width, int height) {
		return x + y * width;
	}
	
	private enum LoadingState {
		PRE_LOAD,PRE_LOAD_COMPLETE,COPY,COPY_COMPLETE,LOAD,LOAD_COMPLETE,NONE;
	}
	
	private TextureManager() {}

	public static boolean doneLoading() {
		return loadComplete;
	}

	public static class TextureAtlas {
		
		private int indTexSize, atlasSize, totalPixels, texSize;
		private Texture texture;
		private int currentLoadIndex = 0;

		public TextureAtlas(int texSize, int atlasSize, String name) {
			this.indTexSize = texSize;
			this.atlasSize = atlasSize;
			this.texture = new Texture("atlas"+name);
			this.texSize = this.indTexSize * this.atlasSize;
			this.totalPixels = this.texSize * this.texSize;
			initData();
		}
		
		private void initData() {
			this.texture.data = new int[totalPixels];
			for(int i = 0;i < totalPixels;i ++) {
				texture.data[i] = 0xFFFFFFFF;
			}
			this.texture.width = texSize;
			this.texture.height = texSize;
		}
		
		public double getOffsetPos(int textureID) {
			return (double) textureID/(double) atlasSize;
		}
		
		private int nextLoad() {
			return currentLoadIndex++;
		}

		public Texture getTexture() {
			return texture;
		}

		@Override
		public String toString() {
			return "TextureAtlas [indTexSize=" + indTexSize + ", atlasSize=" + atlasSize + ", totalPixels="
					+ totalPixels + ", texSize=" + texSize + ", texture=" + texture + ", currentLoadIndex="
					+ currentLoadIndex + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + atlasSize;
			result = prime * result + currentLoadIndex;
			result = prime * result + indTexSize;
			result = prime * result + texSize;
			result = prime * result + ((texture == null) ? 0 : texture.hashCode());
			result = prime * result + totalPixels;
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
			TextureAtlas other = (TextureAtlas) obj;
			if (atlasSize != other.atlasSize)
				return false;
			if (currentLoadIndex != other.currentLoadIndex)
				return false;
			if (indTexSize != other.indTexSize)
				return false;
			if (texSize != other.texSize)
				return false;
			if (texture == null) {
				if (other.texture != null)
					return false;
			} else if (!texture.equals(other.texture))
				return false;
			if (totalPixels != other.totalPixels)
				return false;
			return true;
		}
		
	}
	
	private static boolean doneTextureCopying = false;

	public static void textureCopy() {
		if(loadingState != LoadingState.PRE_LOAD_COMPLETE) {
			Console.print("(Texture Manager) Textures already copied", 1);
			return;
		}
		Thread loadingThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				textureCopyInternal();
				doneTextureCopying = true;
			}
		});
		loadingThread.setName("Texture Copying Thread");
		loadingThread.start();
	}

	private static int texturesCopied = 0;	
	
	private static void textureCopyInternal() {
		loadingState = LoadingState.COPY;
		
		for(int i = 0;i < textures.size();i ++) {
			Texture texture = textures.get(i);
			TextureAtlas atlas = texture.atlas;
			texturesCopied ++;
			if(atlas == null || texture.isAtlas) {
				continue;
			}
			int index = atlas.nextLoad();
			int indexX = index % atlas.atlasSize;
			int indexY = index / atlas.atlasSize;
			int startX = indexX * atlas.indTexSize;
			int startY = indexY * atlas.indTexSize;
			int endX = startX + atlas.indTexSize;
			int endY = startY + atlas.indTexSize;
			if(texture.width != texture.height) {
				Console.print("Texture "+texture.name+" is not a square texture, and will be stretched", 1);
			}
			texture.atlasIndex = index;
			for(int x = startX;x < endX;x ++) {
				for(int y = startY;y < endY;y ++) {
					int targetX = findClosest(x - startX, atlas.indTexSize, texture.width);
					int targetY = findClosest(y - startY, atlas.indTexSize, texture.height);
					int targetIndex = toIndex(targetX, targetY, texture);
					int srcIndex = toIndex(x, y, atlas.texSize, atlas.texSize);
					if(srcIndex >= atlas.texture.data.length) {
						srcIndex  = atlas.texture.data.length-1;
					}
					if(targetIndex >= texture.data.length) {
						targetIndex = texture.data.length-1;
					}
					atlas.texture.data[srcIndex] = texture.data[targetIndex];//TODO: 2 Fix bug with index out of bounds exception when image is smaller than atlas texture size
				}
			}
		}
		
		loadingState = LoadingState.COPY_COMPLETE;
	}
	
	/**
	 * Gets closest pixel, from tex1 to tex2. TargetPos is the the pixel pos on tex1, sourceRes is tex1 res, targetRes is tex2 res.'
	 * Returns the closest pixel on tex2
	 */
	public static int findClosest(int targetPos, int sourceRes, int targetRes) {
		double pCoordinate = (double) targetPos/(double) sourceRes;
		double approx = pCoordinate * targetRes;
		int approxRounded = (int) Math.round(approx);
		return approxRounded;
	}
	
	public static boolean doneTextureCopy() {
		return doneTextureCopying;
	}
	
}
