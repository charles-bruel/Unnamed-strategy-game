package chazzvader.game.other;

/**
 * 
 * @author csbru
 *
 */
public enum OutputLevel {

	DEBUG(-1, "[DEBUG]", "Debug"),INFO(0, "[INFO]", "Info"),WARN(1, "[WARN]", "Warning"),ERROR(2, "[ERROR]", "Error");
	
	int level;
	String prefix, name;
	
	OutputLevel(int level, String prefix, String name){
		this.level = level;
		this.prefix = prefix;
		this.name= name;
	}
	
	public static OutputLevel get(int level){
		OutputLevel r = INFO;
		for (OutputLevel io : OutputLevel.values()) {
			if(level == io.level){
				r = io;
			}
		}
		return r;
	}

	public int getLevel() {
		return level;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getName() {
		return name;
	}
	
}
