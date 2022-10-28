package chazzvader.game.content.units.military;

import java.awt.image.BufferedImage;

import chazzvader.game.other.SubTileCoordinate.SubTile;
import chazzvader.game.sided.both.game.Entity;

public class UnitAxeman extends UnitMilitary {

	public UnitAxeman(Entity owner, int x, int y, SubTile subTile, int uid) {
		super(owner, x, y, subTile, uid);
	}

	@Override
	public BufferedImage getIconImage() {
		return null;
	}

	@Override
	public int maxHealth() {
		return 40;
	}

	@Override
	public String getName() {
		return "LOC_UNIT_AXEMAN";
	}

}