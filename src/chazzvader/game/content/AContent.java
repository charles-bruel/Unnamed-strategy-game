package chazzvader.game.content;

public abstract class AContent {
	
	public String getInName() {
		String[] cp = this.getClass().getName().split("\\.");
		return cp[cp.length-1];
	}

}
