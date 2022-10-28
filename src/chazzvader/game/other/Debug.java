package chazzvader.game.other;

import javax.swing.JOptionPane;

import chazzvader.game.MainOld;
@Deprecated
public class Debug {
	
	private static String helpMsg;
	
	static {
		helpMsg += "Commands:\n";
		helpMsg += "Remeber: Use / to seperate commands and parameters";
		helpMsg += "help: Lists all commands\n";
		helpMsg += "stp: Shows position labels for tiles\n";
		helpMsg += "sudo: Send a msg directly to the server\n";
		helpMsg += "scp: Shows the position of the camera\n";
		helpMsg += "sfps: Shows the current fps\n";
		helpMsg += "connect: Connect to server at given ip and port\n";
	}
	
	public static void input() {
		String i = JOptionPane.showInputDialog("Debug console: ");
		if(i == null) return;
		if(i.equalsIgnoreCase("help")) {
			JOptionPane.showMessageDialog(null, helpMsg);
			return;
		}
		if(i.equalsIgnoreCase("stp")) {
			showTileNumbers = !showTileNumbers;
			return;
		}
		if(i.equalsIgnoreCase("scp")) {
			showXYcoordinate = !showXYcoordinate;
			return;
		}
		if(i.equalsIgnoreCase("sfps")) {
			showFPS = !showFPS;
			return;
		}
		String[] sa = i.split("\\/");
		if(sa[0].equalsIgnoreCase("sudo")) {
			MainOld.getFrame().getPanel().getClient().sendMSG(sa[1]);
			return;
		}
		if(sa[0].equalsIgnoreCase("connect")) {
			String ip = sa[1];
			int port = Integer.parseInt(sa[2]);
			MainOld.getFrame().getPanel().getClient().connect(ip, port);
			return;
		}
		/*String[] sa = i.split(":");
		if(sa[0].equalsIgnoreCase("cu")) {
			Main.getFrame().getPanel().getClient().getGame().getUnits().add(Units.Military.axeman(null, Integer.parseInt(sa[1]), Integer.parseInt(sa[2])));
			return;
		}*/
		
		JOptionPane.showMessageDialog(null, "Unknown command! Enter help for all commands!");
	}
	
	private static boolean showTileNumbers = false;
	private static boolean showXYcoordinate = false;
	private static boolean showFPS = false;
	
	/**
	 * @return the showTileNumbers
	 */
	public static boolean showTileNumbers() {
		return showTileNumbers;
	}
	/**
	 * @return the showXYcoordinate
	 */
	public static boolean showXYcoordinate() {
		return showXYcoordinate;
	}
	/**
	 * @return the showFPS
	 */
	public static boolean showFPS() {
		return showFPS;
	}


	
}
