package chazzvader.game.content.resources;

import chazzvader.game.content.Yields;
import chazzvader.game.content.tiles.Tile;
import chazzvader.game.sided.both.game.Player;
import chazzvader.game.sided.client.render.ImageManager;

public class ResourceDeer extends Resource{

	@Override
	public boolean validate(Tile p) {
		boolean b = false;
		if (p.getFeature() != null && p.getFeature().getInName().contains("Woods")) {
			b = true;
		}
		return b;
	}

	public ResourceDeer() {
		super(ImageManager.RESOURCE_DEER);
	}
	
	@Override
	public Yields addYields(Yields y, Player p) {
		return y.add(3, 0, 0, 0, 0, 1);
	}

	@Override
	public ResourceType type() {
		return ResourceType.LUXURY;
	}

}
