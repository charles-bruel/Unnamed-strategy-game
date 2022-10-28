package chazzvader.game.engine;

import chazzvader.game.content.ContentBaseGame;
import chazzvader.game.content.ContentManager;
import chazzvader.game.engine.render.TextureManager;
import chazzvader.game.other.Console;

public class LoadingManager {
	
	private LoadingManager() {}

	private static LoadingState loadingState = LoadingState.PRE_INIT;
	
	public static boolean update() {
		switch(loadingState) {
		case CONTENT_INIT:
			contentInit();
			break;
		case DONE:
			return true;
		case PRE_INIT:
			preInit();
			break;
		case SHADER_LOAD://TODO: 2 Shader loading
			loadingState = LoadingState.CONTENT_INIT;
			break;
		case TEXTURE_INIT:
			textureInit();
			break;
		case TEXTURE_LOAD:
			textureLoad();
			break;
		case TEXTURE_PRE_LOAD:
			texturePreLoad();
			break;
		case TEXTURE_COPY:
			textureCopy();
			break;
		case RENDER_INIT:
			renderInit();
			break;
		default:
			break;
		}
		return false;
	}
	
	public static double overallProgress() {
		return (double) loadingState.id / (double) LoadingState.total;
	}
	
	private static boolean contentInitStarted = false;
	private static boolean contentInitFinished = false;

	private static void contentInit() {
		if(!contentInitStarted) {
			Console.print("(Loading Manager) Starting content init", -1);
			startContentInit();
			contentInitStarted = true;
		} else if(contentInitFinished) {
			Console.print("(Loading Manager) Finished content init", -1);
			loadingState = LoadingState.RENDER_INIT;
		}
	}
	
	private static void startContentInit() {
		Thread thread = new Thread(new Runnable() {	
			@Override
			public void run() {
				ContentManager.init();
				ContentBaseGame.initBaseContent();
				ContentManager.printContent(-1);
				contentInitFinished = true;
			}
		});
		thread.setName("Content Init Thread");
		thread.start();
	}

	private static void renderInit() {
		loadingState = LoadingState.DONE;
	}

	private static boolean textureInitStarted = false;
	private static boolean textureInitFinished = false;
	
	private static void textureInit() {
		if(!textureInitStarted) {
			Console.print("(Loading Manager) Starting texture init", -1);
			startTextureInit();
			textureInitStarted = true;
		} else if(textureInitFinished) {
			Console.print("(Loading Manager) Finished texture init", -1);
			loadingState = LoadingState.TEXTURE_PRE_LOAD;
		}
	}
	
	private static void startTextureInit() {
		Thread thread = new Thread(new Runnable() {	
			@Override
			public void run() {
				TextureManager.init();
				textureInitFinished = true;
			}
		});
		thread.setName("Texture Init Thread");
		thread.start();
	}
	
	private static void preInit() {
		loadingState = LoadingState.TEXTURE_INIT;
		Console.print("(Loading Manager) Pre init finished", -1);
	}
	
	private static boolean texturePreLoadStarted = false;
	
	private static void texturePreLoad() {
		if(!texturePreLoadStarted) {
			Console.print("(Loading Manager) Pre-loading textures", -1);
			TextureManager.preLoad();
			texturePreLoadStarted = true;
		} else if(TextureManager.donePreLoading()) {
			loadingState = LoadingState.TEXTURE_COPY;
			Console.print("(Loading Manager) Done pre-loading textures", -1);
		}
	}
	
	private static boolean textureCopyStarted = false;
	
	private static void textureCopy() {
		if(!textureCopyStarted) {
			Console.print("(Loading Manager) Copying textures to texture atlas", -1);
			TextureManager.textureCopy();
			textureCopyStarted = true;
		} else if(TextureManager.doneTextureCopy()) {
			loadingState = LoadingState.TEXTURE_LOAD;
			Console.print("(Loading Manager) Done copying textures to texture atlas", -1);
		}
	}

	private static boolean textureLoadStarted = false;

	private static void textureLoad() {
		if(!textureLoadStarted) {
			Console.print("(Loading Manager) Loading textures", -1);
			textureLoadStarted = true;
		}
		TextureManager.load(10);
		if(TextureManager.doneLoading()) {
			loadingState = LoadingState.SHADER_LOAD;
			Console.print("(Loading Manager) Done loading textures", -1);
		}
	}
	
	public static double progress() {
		switch(loadingState) {
		case CONTENT_INIT:
			return 0;
		case DONE:
			return 1;
		case PRE_INIT:
			return 0;
		case SHADER_LOAD:
			return 0;//TODO: 2 Shader Load Progress
		case TEXTURE_INIT:
			return 0;
		case RENDER_INIT:
			return 1;
		case TEXTURE_LOAD:
		case TEXTURE_COPY:
		case TEXTURE_PRE_LOAD:
			return TextureManager.progress();
		default:
			return 0;
		}
	}
	
	private static enum LoadingState {//Note, have to be in order or loading bar wont work
		PRE_INIT,//No loading yet
		TEXTURE_INIT,//Creating all texture objects
		TEXTURE_PRE_LOAD,//Pre-loading all texture objects
		TEXTURE_COPY,//Copy all texture objects to texture atlas
		TEXTURE_LOAD,//Loading all texture objects
		SHADER_LOAD,//Create & loading all shader objects
		CONTENT_INIT,//Loading content manager
		RENDER_INIT,//Setup basic rendering
		DONE;//Done
		
		private int id;
		private static int total = 0;
	
		static {
			LoadingState[] values = LoadingState.values();
			for(int i = 0;i < values.length;i ++) {
				LoadingState value = values[i];
				value.id = total++;
			}
		}
		
	}

}
