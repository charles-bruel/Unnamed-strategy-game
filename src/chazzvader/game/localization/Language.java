package chazzvader.game.localization;

/**
 * A enum to represent a language
 * @author csbru
 * @since 1.0
 * @version 1.0
 */
public enum Language {

	/**
	 * All languages go here
	 */
	ENGLISH("en", "English");
	
	/**
	 * Fields
	 */
	private String prefix, name;

	/**
	 * Constructor
	 * @param prefix The prefix used for file naming
	 * @param name The name that the user might see
	 */
	private Language(String prefix, String name) {
		this.prefix = prefix;
		this.name = name;
	}

	/**
	 * Returns the prefix
	 * @return The prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Returns the name
	 * @return The name
	 */
	public String getName() {
		return name;
	}
	
}
