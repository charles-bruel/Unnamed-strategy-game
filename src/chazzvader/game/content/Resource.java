package chazzvader.game.content;

import chazzvader.game.content.ContentManager.ContentNodeTyped;
import chazzvader.game.sided.both.game.Player;

/**
 * Represent a resource
 * @author csbru
 * @since 1.0
 * @version 1.0
 */
public class Resource extends AContent {
		
	private IValidator validator;
	private Yields baseYields;
	private ContentNodeTyped<Resource> parentNode;
	private ResourceType resourceType;
	
	public Resource(IValidator validator, Yields baseYields, ResourceType resourceType, ContentNodeTyped<Resource> ref) {
		this.validator = validator;
		this.baseYields = baseYields;	
		this.resourceType = resourceType;
		this.parentNode = ref;
	}
	
	/**
	 * Is the resource valid on the tile
	 * @param tile The tile to check
	 * @return Whether the resource is valid
	 */
	public boolean validate(Tile p) {
		return validator.validate(p);
	}
	
	/**
	 * Given base yields, what should the yields be
	 * @param yields Base yields
	 * @param player The player (used for tile yield boosts)
	 * @return The combine yields of the yields parameter and the resource yields
	 */
	public Yields addYields(Yields yields, Player player) {
		return yields.add(baseYields.getFood(), baseYields.getProduction(), baseYields.getScience(), baseYields.getCulture(), baseYields.getFaith(), baseYields.getGold());
	};
	
	/**
	 * Is the resource strategic or luxury
	 * @return The ResourceType of the resource
	 */
	public ResourceType type() {
		return resourceType;
	}
	
	/**
	 * Returns the internal name (class name). Used for determine what type of
	 * resource it is
	 * @return The internal (class) name
	 */
	public String getInName() {
		String[] cp = this.getClass().getName().split("\\.");
		return cp[cp.length-1];
	}
	
	public ContentNodeTyped<Resource> getParentNode(){
		return parentNode;
	}
	
}
