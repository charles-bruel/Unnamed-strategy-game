package chazzvader.game.content.manager;

public abstract class Content {
	
	public String getInName() {
		String[] cp = this.getClass().getName().split("\\.");
		return cp[cp.length-1];
	}

}
