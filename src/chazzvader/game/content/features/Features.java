package chazzvader.game.content.features;

public class Features {
	
	public static Feature woods() { return new FeatureWoods(); }
	public static Feature pineWoods() { return new FeaturePineWoods(); }
	public static Feature rainForest() { return new FeatureRainforest(); }
	public static Feature oasis() { return new FeatureOasis(); }
	
	public static Feature fromIndex(int i) {
		switch(i) {
		case 1: return woods();
		case 2: return pineWoods();
		case 3: return rainForest();
		case 4: return oasis();
		}
		return null;
	}
	
	public static int toIndex(Feature f) {
		if(f == null) return 0;
		switch(f.getInName()) {
		case "FeatureWoods": return 1;
		case "FeaturePineWoods": return 2;
		case "FeatureRainforest": return 3;
		case "FeatureOasis": return 4;
		}
		return 0;
	}

}
