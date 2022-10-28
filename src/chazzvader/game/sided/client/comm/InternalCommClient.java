package chazzvader.game.sided.client.comm;

import chazzvader.game.sided.both.comm.CommManager;
import chazzvader.game.sided.both.comm.InternalComms;

public class InternalCommClient extends CommManager {

	@Override
	public void println(String s) {
		InternalComms.clientServer().add(s);
	}

	@Override
	public String receiveln() {
		return InternalComms.serverClient().get();
	}

	@Override
	public boolean internal() {
		return true;
	}
	
	@Override
	public boolean hasItems() {
		return InternalComms.serverClient().hasItems();
	}

}
