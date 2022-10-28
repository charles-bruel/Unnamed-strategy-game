package chazzvader.game.content.civilizations;

import java.awt.Color;
import java.util.ArrayList;

import chazzvader.game.content.civilizations.startbias.StartBias;
import chazzvader.game.content.civilizations.startbias.StartBiases;

public class CivilizationGeneric extends Civilization {

	public CivilizationGeneric() {
		super(startBias(), new Color(0x999999), "Generic");
	}
	
	private static ArrayList<StartBias> startBias() {
		ArrayList<StartBias> a = new ArrayList<>();
		a.add(StartBiases.river(2));
		a.add(StartBiases.avoidSnow(1));
		a.add(StartBiases.avoidDesert(1));
		return a;
	}

}
