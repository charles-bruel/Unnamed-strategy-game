package chazzvader.game.sided.client.render.ui;

import chazzvader.game.localization.LocalizationManager;
import chazzvader.game.other.IAction;
import chazzvader.game.sided.client.render.GraphicsWrapper;

@SuppressWarnings("rawtypes")
public class ParameterButton extends Parameter {

	private int width;
	private String text;
	private IAction click;
	
	@Override
	public int getHeight(GraphicsWrapper g) {
		return 40;
	}

	@Override
	public int getWidth(GraphicsWrapper g) {
		return width;
	}

	@Override
	public void draw(GraphicsWrapper g) {
		g.renderButton(x, y, width, LocalizationManager.get(text));
	}

	@Override
	public void processClick(int rx, int ry) {
		click.run();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@SuppressWarnings("unchecked")
	public ParameterButton(int width, String text, IAction click, int x, int y) {
		this.width = width;
		this.text = text;
		this.click = click;
		this.x = x;
		this.y = y;
	}

}
