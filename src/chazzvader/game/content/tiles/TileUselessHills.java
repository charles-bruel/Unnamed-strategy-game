package chazzvader.game.content.tiles;

import java.awt.Image;

import chazzvader.game.content.Yields;
import chazzvader.game.other.Coordinate;
import chazzvader.game.sided.both.game.map.Map;

public class TileUselessHills extends Tile {

	private int move;
	private String suffix;

	public TileUselessHills(Image tex, String uloc, boolean land, int move, String nameSuffix, Map m, Coordinate c) {
		super(tex, uloc, false, land, null, null, m, c);
		this.move = move;
		this.suffix = nameSuffix;
	}

	@Override
	public int moveCost() {
		return move+5;
	}

	@Override
	protected Yields getTileYields() {
		return new Yields(0, 1, 0, 0, 0, 0);
	}
	
	@Override
	public String getInName() {
		return super.getInName()+suffix;
	}
}