package chazzvader.game.content.units.military;

import java.awt.image.BufferedImage;

import chazzvader.game.content.Tile;
import chazzvader.game.content.units.Unit;
import chazzvader.game.other.SubTileCoordinate.SubTile;
import chazzvader.game.sided.both.game.Entity;

public abstract class UnitMilitary extends Unit {

	
	public UnitMilitary(Entity owner, int x, int y, SubTile subTile, int uid) {
		super(owner, x, y, subTile, uid);
	}

	@Override
	public BufferedImage getBackgroundImage() {
		return null;
	}

	@Override
	public boolean isTileValid(Tile t) {
		return t.isLand() && t.isPassable();
	}
	
}