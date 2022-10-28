package chazzvader.game.content.tiles;

import chazzvader.game.other.Coordinate;
import chazzvader.game.sided.both.game.map.Map;
import chazzvader.game.sided.client.render.ImageManager;

@Deprecated
public class Tiles {
	
	public static Tile ocean(Map m, Coordinate c) { return new TileUseless(ImageManager.OCEAN, "LOC_OCEAN", false, 15, "Ocean", m, c); }
	public static Tile coast(Map m, Coordinate c) { return new TileCoast(m, c); }
	public static Tile tundraMountain(Map m, Coordinate c) { return new TileMountain(ImageManager.TUNDRA_MOUNTAIN, "LOC_TUNDRA_MOUNTAIN", "Tundra", m, c); }
	public static Tile snowMountain(Map m, Coordinate c) { return new TileMountain(ImageManager.SNOW_MOUNTAIN, "LOC_SNOW_MOUNTAIN", "Snow", m, c); }
	public static Tile plainsMountain(Map m, Coordinate c) { return new TileMountain(ImageManager.PLAINS_MOUNTAIN, "LOC_PLAINS_MOUNTAIN", "Plains", m, c); }
	public static Tile grasslandMountain(Map m, Coordinate c) { return new TileMountain(ImageManager.GRASSLAND_MOUNTAIN, "LOC_GRASSLAND_MOUNTAIN", "Grassland", m, c); }
	public static Tile desertMountain(Map m, Coordinate c) { return new TileMountain(ImageManager.DESERT_MOUNTAIN, "LOC_DESERT_MOUNTAIN", "Desert", m, c); }
	public static Tile tundraHills(Map m, Coordinate c) { return new TileTundraHills(m, c); }
	public static Tile desertHills(Map m, Coordinate c) { return new TileUselessHills(ImageManager.DESERT_HILLS, "LOC_DESERT_HILLS", true, 10, "Desert", m, c); }
	public static Tile snowHills(Map m, Coordinate c) { return new TileUselessHills(ImageManager.SNOW_HILLS, "LOC_SNOW_HILLS", true, 10, "Snow", m, c); }
	public static Tile plainsHills(Map m, Coordinate c) { return new TilePlainsHills(m, c); }
	public static Tile grasslandHills(Map m, Coordinate c) { return new TileGrasslandHills(m, c); }
	public static Tile tundra(Map m, Coordinate c) { return new TileTundra(m, c); }
	public static Tile desert(Map m, Coordinate c) { return new TileUseless(ImageManager.DESERT, "LOC_DESERT", true, 10, "Desert", m, c); }
	public static Tile snow(Map m, Coordinate c) { return new TileUseless(ImageManager.SNOW, "LOC_SNOW", true, 10, "Snow", m, c); }
	public static Tile plains(Map m, Coordinate c) { return new TilePlains(m, c); }
	public static Tile grassland(Map m, Coordinate c) { return new TileGrassland(m, c); }
	
	public static Tile fromIndex(int i, Map m, Coordinate c) {
		switch(i) {
		case 0: return null;
		case 1: return null;//TODO: Natrual wonder type stuff here
		case 2: return ocean(m, c);
		case 3: return coast(m, c);
		case 4: return tundraMountain(m, c);
		case 5: return snowMountain(m, c);
		case 6: return plainsMountain(m, c);
		case 7: return grasslandMountain(m, c);
		case 8: return desertMountain(m, c);
		case 9: return tundraHills(m, c);
		case 10: return desertHills(m, c);
		case 11: return snowHills(m, c);
		case 12: return plainsHills(m, c);
		case 13: return grasslandHills(m, c);
		case 14: return tundra(m, c);
		case 15: return desert(m, c);
		case 16: return snow(m, c);
		case 17: return plains(m, c);
		case 18: return grassland(m, c);
		}
		return ocean(m, c);
	}
	
	public static int toIndex(Tile t) {
		switch(t.getInName()) {
		case "TileUselessOcean": return 2;
		case "TileCoast": return 3;
		case "TileMountainTundra": return 4;
		case "TileMountainSnow": return 5;
		case "TileMountainPlains": return 6;
		case "TileMountainGrassland": return 7;
		case "TileMountainDesert": return 8;
		case "TileTundraHills": return 9;
		case "TileUselessHillsDesert": return 10;
		case "TileUselessHillsSnow": return 11;
		case "TilePlainsHills": return 12;
		case "TileGrasslandHills": return 13;
		case "TileTundra": return 14;
		case "TileUselessDesert": return 15;
		case "TileUselessSnow": return 16;
		case "TilePlains": return 17;
		case "TileGrassland": return 18;
		}
		return 0;
	}
	
}