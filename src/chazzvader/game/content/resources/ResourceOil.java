package chazzvader.game.content.resources;

import chazzvader.game.content.Yields;
import chazzvader.game.content.tiles.Tile;
import chazzvader.game.sided.both.game.Player;
import chazzvader.game.sided.client.render.ImageManager;

public class ResourceOil extends Resource {

	@Override
	public boolean validate(Tile p) {
		boolean b = false;
		if ((p.getInName().contains("Coast") || p.getInName().contains("Snow")
				|| p.getInName().contains("Tundra") || p.getInName().contains("Desert"))
				&& p.getFeature() == null && !p.getInName().contains("Mountain")) {
			b = true;
		}
		return b;
	}

	@Override
	public Yields addYields(Yields y, Player p) {
		return y.add(0, 3, 1, 0, 0, 0);
	}

	public ResourceOil() {
		super(ImageManager.RESOURCE_OIL);
	}

	@Override
	public ResourceType type() {
		return ResourceType.STRATEGIC;
	}

}