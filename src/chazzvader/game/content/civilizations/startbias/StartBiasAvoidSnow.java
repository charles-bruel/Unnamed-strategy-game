package chazzvader.game.content.civilizations.startbias;

import chazzvader.game.content.Tile;

public class StartBiasAvoidSnow extends StartBias {

	public StartBiasAvoidSnow(int level) {
		super(level);
	}

	@Override
	public StartBiasResult bias(Tile t) {
		return (t.getInName().contains("Tundra") || t.getInName().contains("Snow")) ? StartBiasResult.AVOIDED : StartBiasResult.NONE;
	}

}
