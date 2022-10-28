package chazzvader.game.content.features;

import chazzvader.game.content.Yields;
import chazzvader.game.content.tiles.Tile;
import chazzvader.game.sided.both.game.Player;
import chazzvader.game.sided.client.render.ImageManager;

class FeaturePineWoods extends Feature {

	@Override
	public Yields addYields(Yields y, Player p) {
		return y.add(0, 1, 0, 0, 0, 0);
	}

	public FeaturePineWoods() {
		super(ImageManager.PINE_WOODS);
	}

	@Override
	public int addedMoveCost() {
		return 5;
	}

	@Override
	public boolean validate(Tile p) {
		boolean b = false;
		if (p.getInName().equalsIgnoreCase("TileTundra") || p.getInName().equalsIgnoreCase("TileTundraHills")
				|| p.getInName().equalsIgnoreCase("TileUselessSnow") || p.getInName().equalsIgnoreCase("TileUselessHillsSnow")) {
			b = true;
		}
		return b;
	}

}
