package chazzvader.game.content.tiles;

import chazzvader.game.content.Yields;
import chazzvader.game.other.Coordinate;
import chazzvader.game.sided.both.game.map.Map;
import chazzvader.game.sided.client.render.ImageManager;

public class TilePlainsHills extends Tile {

	public TilePlainsHills(Map m, Coordinate c) {
		super(ImageManager.PLAINS_HILLS, "LOC_PLAINS_HILLS", true, true, null, null, m, c);
	}

	@Override
	public int moveCost() {
		return 15;
	}

	@Override
	protected Yields getTileYields() {
		return new Yields(1, 2, 0, 0, 0, 0);
	}

	@Override
	public String getInName() {
		return "TilePlainsHills";
	}

}