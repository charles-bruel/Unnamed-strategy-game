package chazzvader.game.other;

import chazzvader.game.localization.Language;

public class Settings {

	static Language clang = Language.ENGLISH;

	static int targetFps = 60;
	
	static boolean debug = true;
	
	public static Language getCurrentLang() {
		return clang;
	}

	public static boolean getDebug() {
		return debug;
	}

	public static int getTargetFps() {
		return targetFps;
	}

	public static void setCurrentLang(Language clang) {
		Settings.clang = clang;
	}

	public static void setTargetFps(int targetFps) {
		Settings.targetFps = targetFps;
	}

	public static void setDebug(boolean debug) {
		Settings.debug = debug;
	}

	/**
	 * Pushes all settings to file
	 */
	public static void push() {
		// TODO: Finish this
	}
	
	/**
	 * Retrieves all settings from file
	 */
	public static void pull() {
		// TODO: Finish this
	}
	
}
