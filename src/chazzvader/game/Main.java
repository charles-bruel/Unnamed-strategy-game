package chazzvader.game;

import java.awt.Toolkit;

import chazzvader.game.content.manager.ContentBaseGame;
import chazzvader.game.content.manager.ContentManager;
import chazzvader.game.localization.LocalizationManager;
import chazzvader.game.other.Console;
import chazzvader.game.sided.client.render.ImageManager;
import chazzvader.game.sided.client.swing.GameFrame;

public class Main extends Thread {

	private static Toolkit tk = Toolkit.getDefaultToolkit();

	private static GameFrame frame;

	private static Main main;

	public static void main(String[] args) {
		try {
			begin();
		} catch (Exception e) {
			Console.error(e, true);
		}
	}

	private static void begin() {
		main = new Main();
	}

	private Main() {
		Console.print("Starting up, Hello World!", 0);
		ContentManager.init();
		ImageManager.init();
		LocalizationManager.init();
		ContentBaseGame.initBaseContent();
		frame = new GameFrame();
		Runtime.getRuntime().addShutdownHook(this);
	}

	public static GameFrame getFrame() {
		return frame;
	}

	public static Main getMain() {
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
