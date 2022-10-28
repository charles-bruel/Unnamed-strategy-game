package chazzvader.game.content.resources;

import chazzvader.game.content.Yields;
import chazzvader.game.content.tiles.Tile;
import chazzvader.game.sided.both.game.Player;
import chazzvader.game.sided.client.render.ImageManager;

public class ResourceDiamond extends Resource{

	@Override
	public boolean validate(Tile p) {
		boolean b = false;
		if (p.getInName().equalsIgnoreCase("TilePlainsHills") || p.getInName().equalsIgnoreCase("TileGrasslandHills")) {
			b = true;
		}
		return b;
	}

	@Override
	public Yields addYields(Yields y, Player p) {
		return y.add(0, 2, 0, 0, 0, 2);
	}

	public ResourceDiamond() {
		super(ImageManager.RESOURCE_DIAMOND);
	}
	
	@Override
	public ResourceType type() {
		return ResourceType.LUXURY;
	}

}
