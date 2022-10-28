package chazzvader.game;

import java.awt.Toolkit;

import chazzvader.game.content.ContentBaseGame;
import chazzvader.game.content.ContentManager;
import chazzvader.game.localization.LocalizationManager;
import chazzvader.game.other.Console;
import chazzvader.game.sided.client.swing.GameFrame;
@Deprecated
public class MainOld extends Thread {

	private static Toolkit tk = Toolkit.getDefaultToolkit();

	private static GameFrame frame;

	private static MainOld main;

	public static void main(String[] args) {
		try {
			begin();
		} catch (Exception e) {
			Console.error(e, true);
		}
	}

	private static void begin() {
		main = new MainOld();
	}

	private MainOld() {
		Console.print("Starting up, Hello World!", 0);
		ContentManager.init();
		//ImageManager.init();
		LocalizationManager.init();
		ContentBaseGame.initBaseContent();
		frame = new GameFrame();
		Runtime.getRuntime().addShutdownHook(this);
	}

	public static GameFrame getFrame() {
		return frame;
	}

	public static MainOld getMain() {
		return main;
	}

	@Override
	public void run() {
		Console.end();
	}

	public static Toolkit getTk() {
		return tk;
	}

}
