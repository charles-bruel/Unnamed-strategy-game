package chazzvader.game.content.tiles;

import chazzvader.game.content.Yields;
import chazzvader.game.other.Coordinate;
import chazzvader.game.sided.both.game.map.Map;
import chazzvader.game.sided.client.render.ImageManager;

public class TileTundra extends Tile {

	public TileTundra(Map m, Coordinate c) {
		super(ImageManager.TUNDRA, "LOC_TUNDRA", true, true, null, null, m, c);
	}

	@Override
	public int moveCost() {
		return 10;
	}

	@Override
	protected Yields getTileYields() {
		return new Yields(1, 0, 0, 0, 0, 0);
	}

}