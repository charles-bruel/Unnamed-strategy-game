package chazzvader.game.content.tiles;

import chazzvader.game.content.Yields;
import chazzvader.game.other.Coordinate;
import chazzvader.game.sided.both.game.map.Map;
import chazzvader.game.sided.client.render.ImageManager;

public class TileCoast extends Tile{

	public TileCoast(Map m, Coordinate c) {
		super(ImageManager.COAST, "LOC_COAST", true, false, null, null, m, c);
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