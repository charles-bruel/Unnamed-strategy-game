package chazzvader.game.content.civilizations.startbias;

import chazzvader.game.content.Tile;

public abstract class StartBias {

	private int level;
	
	public abstract StartBiasResult bias(Tile t);
	
	public int level() {
		return level;
	}

	public StartBias(int level) {
		this.level = level;
	}
	
	public enum StartBiasResult {
		PREFFERED(1),AVOIDED(-1),NONE(0);
		
		int num;
		
		StartBiasResult(int num){
			this.num = num;
		}
		
		public int getNumber() {
			return num;
		}
		
	}
	
}
