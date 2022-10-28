package chazzvader.game.content.tiles;

import chazzvader.game.content.Yields;
import chazzvader.game.other.Coordinate;
import chazzvader.game.sided.both.game.map.Map;
import chazzvader.game.sided.client.render.ImageManager;

public class TileTundraHills extends Tile {

	public TileTundraHills(Map m, Coordinate c) {
		super(ImageManager.TUNDRA_HILLS, "LOC_TUNDRA_HILLS", true, true, null, null, m, c);
	}

	@Override
	public int moveCost() {
		return 15;
	}

	@Override
	protected Yields getTileYields() {
		return new Yields(1, 1, 0, 0, 0, 0);
	}

}