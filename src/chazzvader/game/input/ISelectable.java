package chazzvader.game.input;

import java.util.ArrayList;

import chazzvader.game.sided.client.render.ui.Parameter;

/**
 * Interface to mark something as selectable
 * @author csbru
 * @since 1.0
 * @version 1.0
 */
public interface ISelectable {
	
	@SuppressWarnings("rawtypes")
	public ArrayList<Parameter> getActions();
	
}
