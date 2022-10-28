package chazzvader.game.content.features;

import chazzvader.game.content.Yields;
import chazzvader.game.content.tiles.Tile;
import chazzvader.game.sided.both.game.Player;
import chazzvader.game.sided.client.render.ImageManager;

class FeatureWoods extends Feature{

	@Override
	public Yields addYields(Yields y, Player p) {
		return y.add(1, 1, 0, 0, 0, 0);
	}

	public FeatureWoods() {
		super(ImageManager.WOODS);
	}

	@Override
	public int addedMoveCost() {
		return 5;
	}

	@Override
	public boolean validate(Tile p) {
		boolean b = false;
		if (p.getInName().equalsIgnoreCase("TilePlains") || p.getInName().equalsIgnoreCase("TilePlainsHills")
				|| p.getInName().equalsIgnoreCase("TileGrassland") || p.getInName().equalsIgnoreCase("TileGrasslandHills")) {
			b = true;
		}
		return b;
	}

}
