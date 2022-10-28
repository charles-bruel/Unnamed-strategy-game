package chazzvader.game.content;

import chazzvader.game.content.ContentManager.ContentNodeTyped;

/**
 * Abstract class, represent a feature (woods, oasis, etc)
 * 
 * @author csbru
 * @since 1.0
 * @version 1.0
 */
public class Feature extends AContent {

	private IValidator validator;
	private Yields baseYields;
	private int moveCost;
	private ContentNodeTyped<Feature> parentNode;
	
	/**
	 * Is the feature valid on the tile
	 * 
	 * @param tile The tile to check
	 * @return Whether the feature is valid
	 */
	public boolean validate(Tile tile) {
		return validator.validate(tile);
	}

	/**
	 * Given base yields, what should the yields be
	 * 
	 * @param yields Base yields
	 * @param player The player (used for tile yield boosts)
	 * @return The combine yields of the yields parameter and the features yields
	 */
	public Yields addYields(Yields yields) {
		return yields.add(baseYields.getFood(), baseYields.getProduction(), baseYields.getScience(), baseYields.getCulture(), baseYields.getFaith(), baseYields.getGold());
	}

	/**
	 * How much this feature adds to the move cost
	 * 
	 * @return
	 */
	public int addedMoveCost() {
		return moveCost;
	}

	/**
	 * Returns the internal name (class name). Used for determine what type of
	 * feature it is
	 * 
	 * @return The internal (class) name
	 */
	public String getInName() {
		String[] cp = this.getClass().getName().split("\\.");
		return cp[cp.length - 1];
	}

	/**
	 * Can the feature have a river on it
	 * 
	 * @return
	 */
	public boolean canHaveRiver() {
		return true;
	}
	/**
	 * @param validator
	 * @param baseYields
	 * @param moveCost
	 * @param ref
	 */
	public Feature(IValidator validator, Yields baseYields, int moveCost, ContentNodeTyped<Feature> ref) {
		this.validator = validator;
		this.baseYields = baseYields;
		this.moveCost = moveCost;
		this.parentNode = ref;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseYields == null) ? 0 : baseYields.hashCode());
		result = prime * result + moveCost;
		result = prime * result + ((parentNode == null) ? 0 : parentNode.hashCode());
		result = prime * result + ((validator == null) ? 0 : validator.hashCode());
		return result;
	}
	
	public ContentNodeTyped<Feature> getParentNode(){
		return parentNode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Feature other = (Feature) obj;
		if (baseYields == null) {
			if (other.baseYields != null)
				return false;
		} else if (!baseYields.equals(other.baseYields))
			return false;
		if (moveCost != other.moveCost)
			return false;
		if (parentNode == null) {
			if (other.parentNode != null)
				return false;
		} else if (!parentNode.equals(other.parentNode))
			return false;
		if (validator == null) {
			if (other.validator != null)
				return false;
		} else if (!validator.equals(other.validator))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Feature [validator=" + validator + ", baseYields=" + baseYields + ", moveCost=" + moveCost + ", ref="
				+ parentNode + "]";
	}

}
