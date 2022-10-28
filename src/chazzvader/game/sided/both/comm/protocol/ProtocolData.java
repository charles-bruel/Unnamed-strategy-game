package chazzvader.game.sided.both.comm.protocol;

public class ProtocolData {

	private boolean[] raw;
	private ProtocolDataMinor[] data;
	private final String id;
	private final String name;
	
	ProtocolData(ProtocolTemplate protocolTemplate) {
		this.id = protocolTemplate.getId();
		this.name = protocolTemplate.getName();
		ProtocolTemplateMinor[] templates = protocolTemplate.getData();
		data = new ProtocolDataMinor[templates.length];
		for(int i = 0;i < templates.length;i ++) {
			data[i] = new ProtocolDataMinor(templates[i]);
		}
		raw = new boolean[protocolTemplate.getSizeRequired()];
		updateRaw();
	}

	public boolean[] getRaw() {
		return raw;
	}

	public ProtocolDataMinor[] getData() {
		return data;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	private void updateRaw() {
		for(int i = 0, k = 0;i < data.length;i ++) {
			boolean[] currentData = data[i].getData();
			for(int j = 0;j < currentData.length;j ++, k ++) {
				raw[k] = currentData[j];
			}
		}
	}
	
}
