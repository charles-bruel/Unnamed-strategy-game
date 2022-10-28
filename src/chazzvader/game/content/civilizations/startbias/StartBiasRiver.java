package chazzvader.game.content.civilizations.startbias;

import chazzvader.game.content.Tile;

public class StartBiasRiver extends StartBias{

	public StartBiasRiver(int level) {
		super(level);
	}

	@Override
	public StartBiasResult bias(Tile t) {
		return t.hasRiver() ? StartBiasResult.PREFFERED : StartBiasResult.NONE;
	}

}
