package chazzvader.game.content.tiles;

import chazzvader.game.content.Yields;
import chazzvader.game.other.Coordinate;
import chazzvader.game.sided.both.game.map.Map;

public class TileUndiscovered extends Tile {

	public TileUndiscovered(Map m, Coordinate c) {
		super(null, "LOC_UNDISCOVERED", true, true, null, null, m, c);
	}

	@Override
	public int moveCost() {
		return 100;
	}

	@Override
	protected Yields getTileYields() {
		return new Yields();
	}

}
