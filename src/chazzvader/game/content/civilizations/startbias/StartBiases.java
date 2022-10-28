package chazzvader.game.content.civilizations.startbias;

public class StartBiases {

	public static StartBias river(int level) { return new StartBiasRiver(level); }
	public static StartBias avoidSnow(int level) { return new StartBiasAvoidSnow(level); }
	public static StartBias avoidDesert(int level) { return new StartBiasAvoidDesert(level); }
	
}
