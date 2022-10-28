package chazzvader.game.content.manager;

import chazzvader.game.content.building.Building;
import chazzvader.game.content.civilizations.Civilization;
import chazzvader.game.content.features.Feature;
import chazzvader.game.content.manager.ContentManager.ContentNode;
import chazzvader.game.content.manager.ContentManager.ContentNodeParent;
import chazzvader.game.content.manager.ContentManager.ContentNodeTile;
import chazzvader.game.content.resources.Resource;
import chazzvader.game.content.tiles.Tile;
import chazzvader.game.content.tiles.TileCoast;
import chazzvader.game.content.tiles.TileGrassland;
import chazzvader.game.content.tiles.TileGrasslandHills;
import chazzvader.game.content.tiles.TileMountain;
import chazzvader.game.content.tiles.TilePlains;
import chazzvader.game.content.tiles.TilePlainsHills;
import chazzvader.game.content.tiles.TileTundra;
import chazzvader.game.content.tiles.TileTundraHills;
import chazzvader.game.content.tiles.TileUndiscovered;
import chazzvader.game.content.tiles.TileUseless;
import chazzvader.game.content.tiles.TileUselessHills;
import chazzvader.game.content.units.Unit;
import chazzvader.game.other.Console;
import chazzvader.game.sided.client.render.ImageManager;

public class ContentBaseGame {

	public static final ContentNode BASE_GAME = new ContentNode("Base Game", ContentManager.getCore());
	
	public static final ContentNodeParent<Tile> TILES = new ContentNodeParent<>("Tiles", BASE_GAME);
	public static final ContentNodeParent<Unit> UNITS = new ContentNodeParent<>("Units", BASE_GAME);
	public static final ContentNodeParent<Feature> FEATURES = new ContentNodeParent<>("Features", BASE_GAME);
	public static final ContentNodeParent<Resource> RESOURCES = new ContentNodeParent<>("Resources", BASE_GAME);
	public static final ContentNodeParent<Building> BUIDLINGS = new ContentNodeParent<>("Buildings", BASE_GAME);
	public static final ContentNodeParent<Civilization> CIVILIZATIONS = new ContentNodeParent<>("Civilizations", BASE_GAME);

	public static final ContentNodeTile UNDISCOVERED = new ContentNodeTile("Undiscovered", TILES, (map, coordinate) -> new TileUndiscovered(map, coordinate));
	
	public static final ContentNodeTile OCEAN = new ContentNodeTile("Ocean", TILES, (map, coordinate) -> new TileUseless(ImageManager.OCEAN, "LOC_OCEAN", false, 15, "Ocean", map, coordinate));
	public static final ContentNodeTile COAST = new ContentNodeTile("Coast", TILES, (map, coordinate) -> new TileCoast(map, coordinate));
	
	public static final ContentNodeTile TUNDRA_MOUNTAIN = new ContentNodeTile("Tundra Mountain", TILES, (map, coordinate) -> new TileMountain(ImageManager.TUNDRA_MOUNTAIN, "LOC_TUNDRA_MOUNTAIN", "Tundra", map, coordinate));
	public static final ContentNodeTile SNOW_MOUNTAIN = new ContentNodeTile("Snow Mountain", TILES, (map, coordinate) -> new TileMountain(ImageManager.SNOW_HILLS, "LOC_SNOW_MOUNTAIN", "Snow", map, coordinate));
	public static final ContentNodeTile PLAINS_MOUNTAIN = new ContentNodeTile("Plains Mountain", TILES, (map, coordinate) -> new TileMountain(ImageManager.PLAINS_MOUNTAIN, "LOC_PLAINS_MOUNTAIN", "Plains", map, coordinate));
	public static final ContentNodeTile GRASSLAND_MOUNTAIN = new ContentNodeTile("Grassland Mountain", TILES, (map, coordinate) -> new TileMountain(ImageManager.GRASSLAND_MOUNTAIN, "LOC_GRASSLAND_MOUNTAIN", "Grassland", map, coordinate));
	public static final ContentNodeTile DESERT_MOUNTAIN = new ContentNodeTile("Desert Mountain", TILES, (map, coordinate) -> new TileMountain(ImageManager.DESERT_MOUNTAIN, "LOC_DESERT_MOUNTAIN", "Desert", map, coordinate));
	
	public static final ContentNodeTile TUNDRA_HILLS = new ContentNodeTile("Tundra Hills", TILES, (map, coordinate) -> new TileTundraHills(map, coordinate));
	public static final ContentNodeTile SNOW_HILLS = new ContentNodeTile("Snow Hills", TILES, (map, coordinate) -> new TileUselessHills(ImageManager.SNOW_HILLS, "LOC_SNOW_HILLS", true, 10, "Snow", map, coordinate));
	public static final ContentNodeTile PLAINS_HILLS = new ContentNodeTile("Plains Hills", TILES, (map, coordinate) -> new TilePlainsHills(map, coordinate));
	public static final ContentNodeTile GRASSLAND_HILLS = new ContentNodeTile("Grassland Hills", TILES, (map, coordinate) -> new TileGrasslandHills(map, coordinate));
	public static final ContentNodeTile DESERT_HILLS = new ContentNodeTile("Desert Hills", TILES, (map, coordinate) -> new TileUselessHills(ImageManager.DESERT_HILLS, "LOC_DESERT_HILLS", true, 10, "Desert", map, coordinate));
	
	public static final ContentNodeTile TUNDRA = new ContentNodeTile("Tundra", TILES, (map, coordinate) -> new TileTundra(map, coordinate));
	public static final ContentNodeTile SNOW = new ContentNodeTile("Snow", TILES, (map, coordinate) -> new TileUseless(ImageManager.SNOW, "LOC_SNOW", true, 10, "Snow", map, coordinate));
	public static final ContentNodeTile PLAINS = new ContentNodeTile("Plains", TILES, (map, coordinate) -> new TilePlains(map, coordinate));
	public static final ContentNodeTile GRASSLAND = new ContentNodeTile("Grassland", TILES, (map, coordinate) -> new TileGrassland(map, coordinate));
	public static final ContentNodeTile DESERT = new ContentNodeTile("Desert", TILES, (map, coordinate) -> new TileUseless(ImageManager.DESERT, "LOC_DESERT", true, 10, "Desert", map, coordinate));
	
	public static void initBaseContent() {
		Console.print("(Content) Loading base game content", 0);
		ContentManager.printContent();
	}

}
