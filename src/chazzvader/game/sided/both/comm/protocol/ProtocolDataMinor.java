package chazzvader.game.sided.both.comm.protocol;

public class ProtocolDataMinor {
	
	private boolean[] data;
	private String name;
	
	public ProtocolDataMinor(boolean[] data, String name) {
		this.data = data;
		this.name = name;
	}
	
	public ProtocolDataMinor(int size, String name) {
		this(new boolean[size], name);
	}

	public ProtocolDataMinor(ProtocolTemplateMinor template) {
		this(template.getSize(), template.getName());
	}

	public boolean[] getData() {
		return data;
	}

	public void setData(boolean[] data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
