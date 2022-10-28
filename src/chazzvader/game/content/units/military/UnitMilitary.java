package chazzvader.game.content.units.military;

import java.awt.image.BufferedImage;

import chazzvader.game.content.tiles.Tile;
import chazzvader.game.content.units.Unit;
import chazzvader.game.other.SubTileCoordinate.SubTile;
import chazzvader.game.sided.both.game.Entity;
import chazzvader.game.sided.client.render.ImageManager;

public abstract class UnitMilitary extends Unit {

	
	public UnitMilitary(Entity owner, int x, int y, SubTile subTile, int uid) {
		super(owner, x, y, subTile, uid);
	}

	@Override
	public BufferedImage getBackgroundImage() {
		return ImageManager.MILITARY_UNIT_BACKGROUND;
	}

	@Override
	public boolean isTileValid(Tile t) {
		return t.isLand() && t.isPassable();
	}
	
}