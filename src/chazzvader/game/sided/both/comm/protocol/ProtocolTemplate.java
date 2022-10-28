package chazzvader.game.sided.both.comm.protocol;

public class ProtocolTemplate {
	
	private static int currentID = ProtocolAPI.CONTROL_PROTOCOLS;
	
	private final ProtocolTemplateMinor[] data;
	private final String name;
	private final String id;
	
	public ProtocolTemplate(String name, ProtocolTemplateMinor[] data) {
		this.id = getNextID();
		this.name = name;
		this.data = data;
	}
	
	public ProtocolTemplateMinor[] getData() {
		return data;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	private String getNextID() {
		return ProtocolAPI.bitsToString(ProtocolAPI.intToBits(++currentID, true, 14));
	}
	
	public ProtocolData getEmpty() {
		return new ProtocolData(this);
	}
	
	public int getSizeRequired() {
		int bitsRequired = 0;
		for(int i = 0;i < data.length;i ++) {
			bitsRequired += data[i].getSize();
		}
		return (int) (Math.ceil(bitsRequired/7.0)*7);
	}

}
