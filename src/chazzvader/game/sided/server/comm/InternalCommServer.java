package chazzvader.game.sided.server.comm;

import chazzvader.game.sided.both.comm.CommManager;
import chazzvader.game.sided.both.comm.InternalComms;

/**
 * Comm manager for server side players
 * @author csbru
 *
 */
public class InternalCommServer extends CommManager {

	@Override
	public void println(String s) {
		InternalComms.serverClient().add(s);
	}

	@Override
	public String receiveln() {
		return InternalComms.clientServer().get();
	}

	@Override
	public boolean internal() {
		return true;
	}

	@Override
	public boolean hasItems() {
		return InternalComms.clientServer().hasItems();
	}

}