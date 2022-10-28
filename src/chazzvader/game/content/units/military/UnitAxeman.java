package chazzvader.game.content.units.military;

import java.awt.image.BufferedImage;

import chazzvader.game.other.SubTileCoordinate.SubTile;
import chazzvader.game.sided.both.game.Entity;
import chazzvader.game.sided.client.render.ImageManager;

public class UnitAxeman extends UnitMilitary {

	public UnitAxeman(Entity owner, int x, int y, SubTile subTile, int uid) {
		super(owner, x, y, subTile, uid);
	}

	@Override
	public BufferedImage getIconImage() {
		return ImageManager.MILITARY_UNIT_AXEMAN;
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