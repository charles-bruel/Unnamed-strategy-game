package chazzvader.game.content.units;

import chazzvader.game.other.SubTileCoordinate.SubTile;
import chazzvader.game.sided.both.game.Entity;

public abstract class UnitSelector {
	public abstract int toIndex(Unit unit);
	public abstract Unit fromIndex(int index, Entity owner, int x, int y, SubTile subTile, int uid);
}
