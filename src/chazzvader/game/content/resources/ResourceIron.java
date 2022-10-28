package chazzvader.game.content.resources;

import chazzvader.game.content.Yields;
import chazzvader.game.content.tiles.Tile;
import chazzvader.game.sided.both.game.Player;
import chazzvader.game.sided.client.render.ImageManager;

public class ResourceIron extends Resource {

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
		return y.add(0, 3, 0, 0, 0, 0);
	}

	public ResourceIron() {
		super(ImageManager.RESOURCE_IRON);
	}

	@Override
	public ResourceType type() {
		return ResourceType.STRATEGIC;
	}

}