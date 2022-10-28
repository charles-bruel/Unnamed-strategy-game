package chazzvader.game.content.resources;

import chazzvader.game.content.Yields;
import chazzvader.game.content.tiles.Tile;
import chazzvader.game.sided.both.game.Player;
import chazzvader.game.sided.client.render.ImageManager;

public class ResourceUranium extends Resource{

	@Override
	public boolean validate(Tile p) {
		boolean b = true;
		if(!p.getInName().contains("Hill") || p.getFeature() != null) {
			b = false;
		}
		return b;
	}

	@Override
	public Yields addYields(Yields y, Player p) {
		return y.add(0, 4, 2, 0, 0, 0);
	}

	public ResourceUranium() {
		super(ImageManager.RESOURCE_URANIUM);
	}

	@Override
	public ResourceType type() {
		return ResourceType.STRATEGIC;
	}

}