package chazzvader.game.other;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Console {

	private static ArrayList<String> logContents = new ArrayList<String>();
	
	public static void error(Exception msg, boolean critical){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		msg.printStackTrace(pw);
		
		String out = sw.toString();
		
		if(critical) error("Critical Error!!!!");
		error(out);
		if(critical) System.exit(0);
	}
	
	public static void error(String msg){
		print(msg, 2);
	}
	
	public static void printRaw(String msg) {
		System.out.println(msg);
	}

	public static void print(String msg, int level){
		if(level < 0 && !Settings.getDebug()) return;
		OutputLevel io = OutputLevel.get(level);
		String ts = new Timestamp(new Date().getTime()).toString();
		String print = io.getPrefix()+"["+ts+"]: "+msg;
		logContents.add(print);
		if(level < 1){
			System.out.println(print);
		}else{
			System.err.println(print);
		}
	}
	
	public static void end() {
		String ts = new Timestamp(new Date().getTime()).toString();
		ts = ts.replace(' ', '-');
		ts = ts.replace(':', '-');
		File f = new File("logs/"+ts+".log");
		if(f.exists()) return;
		try {
			f.createNewFile();
			PrintWriter pr = new PrintWriter(f);
			for(int i = 0;i < logContents.size();i ++) {
				pr.append(logContents.get(i)+"\n");	
			}
			pr.close();
		} catch (IOException e) {
			Console.error(e, false);
		}
	}
	
}
