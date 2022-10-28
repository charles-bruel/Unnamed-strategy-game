package chazzvader.game.localization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import chazzvader.game.other.Console;
import chazzvader.game.other.Settings;

public class LocalizationManager {

	private static File f;

	private static LanguageDictionaryEntry[] dictionary;

	private static Language oldLang = null;

	public static void init() {
		Console.print("(Localization) Initilized", 0);
		update();
	}

	public static void update() {
		if (oldLang == Settings.getCurrentLang()) {
			return;
		} else {
			oldLang = Settings.getCurrentLang();
		}
		f = new File("assets/lang/" + Settings.getCurrentLang().getPrefix() + ".lang");
		if (f.exists()) {
			try {
				BufferedReader bf = new BufferedReader(new FileReader(f));
				// Dictionary size required
				int size = 0;
				while (bf.ready()) {
					String l = bf.readLine();
					if (!l.isEmpty()) {
						String[] p = l.split(":");
						if (p.length == 2) {
							size++;
						}
					}
				}
				dictionary = new LanguageDictionaryEntry[size];
				bf.close();
				bf = new BufferedReader(new FileReader(f));
				// Add to dictionary
				int i = 0;
				while (bf.ready()) {
					if (i >= dictionary.length) {
						Console.print("(Localization) Something went wrong, don't know what because I don't comment my code", 1);
					}
					String l = bf.readLine();
					if (!l.isEmpty()) {
						String[] p = l.split(":");
						if (p.length == 2) {
							dictionary[i] = new LanguageDictionaryEntry(p[0], p[1]);
							i++;
						}
					}
				}
				bf.close();
			} catch (IOException e) {
				Console.error(e, false);
			}
		} else {
			dictionary = new LanguageDictionaryEntry[0];
			Console.print("(Localization) No language file for language: " + Settings.getCurrentLang(), 1);
		}
	}

	public static String get(String uloc) {
		int i = 0;
		String f = null;
		while (i < dictionary.length && (f = dictionary[i].test(uloc)) == null)
			i++;
		if (f == null) {
			f = uloc;
		}
		return f;
	}

	private static final class LanguageDictionaryEntry {
		private final String uloc, loc;

		public LanguageDictionaryEntry(String uloc, String loc) {
			this.uloc = uloc;
			this.loc = loc;
		}

		public String test(String input) {
			return input.equals(uloc) ? loc : null;
		}

	}

}
