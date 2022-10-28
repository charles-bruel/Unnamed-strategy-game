package chazzvader.game.content.civilizations;

import java.awt.Color;
import java.util.ArrayList;

import chazzvader.game.content.AContent;
import chazzvader.game.content.civilizations.startbias.StartBias;

public abstract class Civilization extends AContent {

	private ArrayList<StartBias> startBias;
	
	private Color color;
	
	private String name;

	public ArrayList<StartBias> getStartBias() {
		return startBias;
	}

	public Color getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public Civilization(ArrayList<StartBias> startBias, Color color, String name) {
		this.startBias = startBias;
		this.color = color;
		this.name = name;
	}
	
	public String getInName() {
		String[] cp = this.getClass().getName().split("\\.");
		return cp[cp.length - 1];
	}
	
}
