package chazzvader.game.sided.client.render.ui;

import java.awt.Font;

import chazzvader.game.localization.LocalizationManager;
import chazzvader.game.sided.client.render.GraphicsWrapper;

public class ParameterCheckbox extends Parameter<Boolean> {

	private boolean value;
	private String text;
	private static final int FONT_SIZE = 30;
	private Font f = new Font("Serif", Font.PLAIN, FONT_SIZE);
	
	@Override
	public int getHeight(GraphicsWrapper g) {
		g.setFont(f);
		return g.raw().getFontMetrics().getHeight()+10;
	}

	@Override
	public int getWidth(GraphicsWrapper g) {
		return g.raw().getFontMetrics().stringWidth(text)+15+FONT_SIZE;
	}

	@Override
	public void draw(GraphicsWrapper g) {
		g.setColor(0xAAAAAA);
		g.circle(x + 5, y + 5, FONT_SIZE, FONT_SIZE);
		g.setColor(0x000000);
		if(value) {
			g.circle(x + 8, y + 8, FONT_SIZE-6, FONT_SIZE-6);
		}
		g.renderString(LocalizationManager.get(text), x + 10 + FONT_SIZE, y + FONT_SIZE + 2);
	}

	@Override
	public Boolean getValue() {
		return new Boolean(value);
	}

	@Override
	public void processClick(int rx, int ry) {
		value = !value;
	}
	
	public ParameterCheckbox(String text, int x, int y) {
		this(false, text, x, y);
	}
	
	public ParameterCheckbox(boolean value, String text, int x, int y) {
		this.value = value;
		this.text = text;
		this.x = x;
		this.y = y;
	}

}
