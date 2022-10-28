package chazzvader.game.content.features;

import chazzvader.game.content.Yields;
import chazzvader.game.content.tiles.Tile;
import chazzvader.game.sided.both.game.Player;
import chazzvader.game.sided.client.render.ImageManager;

class FeatureRainforest extends Feature {

	@Override
	public Yields addYields(Yields y, Player p) {
		return y.add(2, 2, 0, 0, 0, 0);
	}

	public FeatureRainforest() {
		super(ImageManager.RAINFOREST);
	}

	@Override
	public int addedMoveCost() {
		return 10;
	}

	@Override
	public boolean validate(Tile p) {
		boolean b = false;
		if (p.getInName().equalsIgnoreCase("TileGrassland") || p.getInName().equalsIgnoreCase("TileGrasslandHills")) {
			b = true;
		}
		return b;
	}

}
