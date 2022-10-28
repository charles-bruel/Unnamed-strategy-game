package chazzvader.game.content.mapgen;

import java.util.ArrayList;

import chazzvader.game.content.manager.Content;
import chazzvader.game.sided.both.game.map.Map;
import chazzvader.game.sided.client.render.ui.Parameter;

public abstract class MapGenerator extends Content {

	@SuppressWarnings("rawtypes")
	public abstract Map generate(int w, int h, ArrayList<Parameter> parameters);
	
	@SuppressWarnings("rawtypes")
	public ArrayList<Parameter> parameters(){
		return new ArrayList<Parameter>();
	}
	
}
