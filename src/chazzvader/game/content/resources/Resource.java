package chazzvader.game.content.resources;

import java.awt.Image;

import chazzvader.game.content.Yields;
import chazzvader.game.content.manager.Content;
import chazzvader.game.content.tiles.Tile;
import chazzvader.game.sided.both.game.Player;

/**
 * Represent a resource
 * @author csbru
 * @since 1.0
 * @version 1.0
 */
public abstract class Resource extends Content {
	
	private Image i;
	
	public Resource(Image i) {
		this.i = i;
	}
	
	/**
	 * Is the resource valid on the tile
	 * @param tile The tile to check
	 * @return Whether the resource is valid
	 */
	public abstract boolean validate(Tile p);
	
	/**
	 * Given base yields, what should the yields be
	 * @param yields Base yields
	 * @param player The player (used for tile yield boosts)
	 * @return The combine yields of the yields parameter and the resource yields
	 */
	public abstract Yields addYields(Yields yields, Player player);
	
	/**
	 * Is the resource strategic or luxury
	 * @return The ResourceType of the resource
	 */
	public abstract ResourceType type();
	
	/**
	 * Returns the internal name (class name). Used for determine what type of
	 * resource it is
	 * @return The internal (class) name
	 */
	public String getInName() {
		String[] cp = this.getClass().getName().split("\\.");
		return cp[cp.length-1];
	}
	
	/**
	 * Returns the image used to draw the resource
	 * 
	 * @return The image used
	 */
	public Image getImage() {
		return i;
	}
	
}
