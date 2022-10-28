package chazzvader.game.content.tiles;

import java.awt.Image;

import chazzvader.game.content.Yields;
import chazzvader.game.other.Coordinate;
import chazzvader.game.sided.both.game.map.Map;

public class TileMountain extends Tile {

	private String suffix;
	
	public TileMountain(Image tex, String uloc, String nameSuffix, Map m, Coordinate c) {
		super(tex, uloc, false, true, null, null, m, c);
		this.suffix = nameSuffix;
	}

	@Override
	public int moveCost() {
		return 20;
	}

	@Override
	protected Yields getTileYields() {
		return new Yields(0, 0, 0, 0, 1, 0);
	}
	
	@Override
	public String getInName() {
		return super.getInName()+suffix;
	}

}