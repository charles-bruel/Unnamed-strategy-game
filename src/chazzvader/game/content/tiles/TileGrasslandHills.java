package chazzvader.game.content.tiles;

import chazzvader.game.content.Yields;
import chazzvader.game.other.Coordinate;
import chazzvader.game.sided.both.game.map.Map;
import chazzvader.game.sided.client.render.ImageManager;

public class TileGrasslandHills extends Tile {

	public TileGrasslandHills(Map m, Coordinate c) {
		super(ImageManager.GRASSLAND_HILLS, "LOC_GRASSLAND_HILLS", true, true, null, null, m, c);
	}

	@Override
	public int moveCost() {
		return 15;
	}

	@Override
	protected Yields getTileYields() {
		return new Yields(2, 1, 0, 0, 0, 0);
	}

}