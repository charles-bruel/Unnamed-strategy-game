package chazzvader.game.sided.both.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import chazzvader.game.other.Console;

/**
 * Comm manager for server side players
 * 
 * @author csbru
 *
 */
public class ExternalComm extends CommManager {

	private Socket socket;
	private BufferedReader inputStream;
	private PrintWriter outputStream;

	public ExternalComm(Socket socket) {
		this.socket = socket;
		try {
			this.inputStream = new BufferedReader(new InputStreamReader(this.socket.getInputStream()), 65536);//Large buffer size
			this.outputStream = new PrintWriter(this.socket.getOutputStream());
		} catch (IOException e) {
			Console.error(e, false);
		}
	}

	@Override
	public String toString() {
		return "ExternalComm [socket=" + socket + ", inputStream=" + inputStream + ", outputStream=" + outputStream
				+ "]";
	}

	@Override
	public void println(String s) {
		outputStream.println(s);
		outputStream.flush();
	}

	@Override
	public String receiveln() {
		if (this.hasItems()) {
			try {
				String msg = inputStream.readLine();
				return msg;
			} catch (IOException e) {
				Console.error(e, false);
			}
		}
		return "";
	}

	@Override
	public boolean internal() {
		return false;
	}

	@Override
	public boolean hasItems() {
		try {
			return inputStream.ready();
		} catch (IOException e) {
			Console.error(e, false);
		}
		return false;
	}

}
