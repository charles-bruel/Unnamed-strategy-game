package chazzvader.game.content.features;

import java.awt.Image;

import chazzvader.game.content.Yields;
import chazzvader.game.content.manager.Content;
import chazzvader.game.content.tiles.Tile;
import chazzvader.game.sided.both.game.Player;

/**
 * Abstract class, represent a feature (woods, oasis, etc)
 * 
 * @author csbru
 * @since 1.0
 * @version 1.0
 */
public abstract class Feature extends Content {

	private Image i;

	/**
	 * Is the feature valid on the tile
	 * 
	 * @param tile The tile to check
	 * @return Whether the feature is valid
	 */
	public abstract boolean validate(Tile tile);

	/**
	 * Given base yields, what should the yields be
	 * 
	 * @param yields Base yields
	 * @param player The player (used for tile yield boosts)
	 * @return The combine yields of the yields parameter and the features yields
	 */
	public abstract Yields addYields(Yields yields, Player player);

	/**
	 * How much this feature adds to the move cost
	 * 
	 * @return
	 */
	public abstract int addedMoveCost();

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
	 * Constructor
	 * 
	 * @param image The image used to draw the feature
	 */
	public Feature(Image image) {
		this.i = image;
	}

	/**
	 * Returns the image used to draw the feature
	 * 
	 * @return The image used
	 */
	public Image getImage() {
		return i;
	}

	/**
	 * Can the feature have a river on it
	 * 
	 * @return
	 */
	public boolean canHaveRiver() {
		return true;
	}

}
