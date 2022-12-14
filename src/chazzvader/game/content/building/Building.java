package chazzvader.game.content.building;

import chazzvader.game.content.Yields;
import chazzvader.game.content.manager.Content;
import chazzvader.game.sided.both.game.Player;

/**
 * Building.
 * @author csbru
 * @since 1.0
 * @version 1.0
 */
public abstract class Building extends Content {

	public abstract Yields addYields(Yields y, Player p);

	
}
