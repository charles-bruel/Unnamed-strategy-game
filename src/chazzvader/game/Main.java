package chazzvader.game;

import chazzvader.game.engine.GameManager;
import chazzvader.game.localization.LocalizationManager;
import chazzvader.game.other.Console;

/**
 * Entry point
 * @author csbru
 * @since 1.1
 */
public class Main {
		
	public static void main(String[] args) {
		Console.print("(Main) Starting up", 0);
		LocalizationManager.init();//TODO: 2 Move this somewhere in the loading process
		GameManager.start();
	}	
}
