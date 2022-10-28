package chazzvader.game.content.features;

import chazzvader.game.content.Yields;
import chazzvader.game.content.tiles.Tile;
import chazzvader.game.sided.both.game.Player;
import chazzvader.game.sided.client.render.ImageManager;

class FeatureOasis extends Feature{

	@Override
	public Yields addYields(Yields y, Player p) {
		return y.add(2, 1, 0, 0, 0, 0);
	}

	public FeatureOasis() {
		super(ImageManager.OASIS);
	}

	@Override
	public int addedMoveCost() {
		return 1;
	}

	@Override
	public boolean validate(Tile p) {
		boolean b = false;
		if (p.getInName().equalsIgnoreCase("TileUselessDesert")) {
			b = true;
		}
		return b;
	}
	
	@Override
	public boolean canHaveRiver() {
		return false;
	}

}