package chazzvader.game.content.mapgen;

import chazzvader.game.content.AContent;
import chazzvader.game.sided.both.game.map.Map;

public abstract class MapGenerator extends AContent {

	public abstract Map generate(int w, int h);
	
}
