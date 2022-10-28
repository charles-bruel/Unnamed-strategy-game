package chazzvader.game.content.civilizations.startbias;

import chazzvader.game.content.tiles.Tile;

public class StartBiasAvoidDesert extends StartBias {

	public StartBiasAvoidDesert(int level) {
		super(level);
	}

	@Override
	public StartBiasResult bias(Tile t) {
		return t.getInName().contains("Desert") ? StartBiasResult.AVOIDED : StartBiasResult.NONE;
	}

}
