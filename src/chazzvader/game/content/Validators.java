package chazzvader.game.content;

public class Validators {

	private Validators() {}

	public static IValidator plainsGrasslandValidator() {
		return ((Tile tile) -> {
			String name = tile.getInName().toLowerCase();
			boolean a = name.contains("plains");
			boolean b = name.contains("grassland");
			boolean c = name.contains("mountain");
			return (a || b) && !c;
		});
	}
	
	public static IValidator plainsGrasslandSnowValidator() {
		return ((Tile tile) -> {
			String name = tile.getInName().toLowerCase();
			boolean a = name.contains("plains");
			boolean b = name.contains("grassland");
			boolean c = name.contains("tundra");
			boolean d = name.contains("snow");
			boolean e = name.contains("mountain");
			return (a || b || c || d) && !e;
		});
	}
	
	public static IValidator desertFlatValidator() {
		return ((Tile tile) -> {
			String name = tile.getInName().toLowerCase();
			boolean a = name.contains("desert");
			boolean b = name.contains("mountain");
			boolean c = name.contains("hill");
			return a && !b && !c;
		});
	}

	public static IValidator woodsValidator() {
		return ((Tile tile) -> {
			if(tile.getFeature() == null) {
				return false;
			}
			String name = tile.getFeature().getInName().toLowerCase();
			boolean a = name.contains("woods");
			return a;
		});
	}
	
	public static IValidator hillsValidator() {
		return ((Tile tile) -> {
			String name = tile.getInName().toLowerCase();
			boolean a = name.contains("hill");
			return a;
		});
	}
	
	public static IValidator flatDesertSnowCoastValidator() {
		return ((Tile tile) -> {
			String name = tile.getInName().toLowerCase();
			boolean a = name.contains("coast");
			boolean b = name.contains("desert");
			boolean c = name.contains("snow");
			boolean d = name.contains("tundra");
			boolean e = name.contains("hill");
			boolean f = name.contains("mountain");
			return (a || b || c || d) && !(e || f);
		});
	}

}
