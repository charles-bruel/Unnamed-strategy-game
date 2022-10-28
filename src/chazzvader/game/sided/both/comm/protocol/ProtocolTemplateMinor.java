package chazzvader.game.sided.both.comm.protocol;

public class ProtocolTemplateMinor {
	
	private String name;
	private int size;

	public ProtocolTemplateMinor(String name, int size) {
		this.name = name;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
}