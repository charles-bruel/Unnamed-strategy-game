package chazzvader.game.content;

import chazzvader.game.content.ContentManager.ContentNode;
import chazzvader.game.content.ContentManager.ContentNodeParent;
import chazzvader.game.content.ContentManager.ContentNodeTyped;
import chazzvader.game.content.building.Building;
import chazzvader.game.content.civilizations.Civilization;
import chazzvader.game.content.units.Unit;
import chazzvader.game.engine.render.TextureManager;
import chazzvader.game.other.Console;

public class ContentBaseGame {

	public static final ContentNode BASE_GAME = new ContentNode("Base Game", ContentManager.getCore());
	
	public static final ContentNodeParent<Tile> TILES = new ContentNodeParent<>("Tiles", BASE_GAME);
	public static final ContentNodeParent<Unit> UNITS = new ContentNodeParent<>("Units", BASE_GAME);
	public static final ContentNodeParent<Feature> FEATURES = new ContentNodeParent<>("Features", BASE_GAME);
	public static final ContentNodeParent<Resource> RESOURCES = new ContentNodeParent<>("Resources", BASE_GAME);
	public static final ContentNodeParent<Building> BUIDLINGS = new ContentNodeParent<>("Buildings", BASE_GAME);
	public static final ContentNodeParent<Civilization> CIVILIZATIONS = new ContentNodeParent<>("Civilizations", BASE_GAME);

	public static final ContentNodeTyped<Tile> TILE_UNDISCOVERED;
	
	public static final ContentNodeTyped<Tile> TILE_OCEAN;
	public static final ContentNodeTyped<Tile> TILE_COAST;
	
	public static final ContentNodeTyped<Tile> TILE_TUNDRA_MOUNTAIN;
	public static final ContentNodeTyped<Tile> TILE_GRASSLAND_MOUNTAIN;
	public static final ContentNodeTyped<Tile> TILE_PLAINS_MOUNTAIN;
	public static final ContentNodeTyped<Tile> TILE_SNOW_MOUNTAIN;
	public static final ContentNodeTyped<Tile> TILE_DESERT_MOUNTAIN;
	
	public static final ContentNodeTyped<Tile> TILE_TUNDRA_HILL;
	public static final ContentNodeTyped<Tile> TILE_GRASSLAND_HILL;
	public static final ContentNodeTyped<Tile> TILE_PLAINS_HILL;
	public static final ContentNodeTyped<Tile> TILE_SNOW_HILL;
	public static final ContentNodeTyped<Tile> TILE_DESERT_HILL;
	
	public static final ContentNodeTyped<Tile> TILE_TUNDRA;
	public static final ContentNodeTyped<Tile> TILE_GRASSLAND;
	public static final ContentNodeTyped<Tile> TILE_PLAINS;
	public static final ContentNodeTyped<Tile> TILE_SNOW;
	public static final ContentNodeTyped<Tile> TILE_DESERT;
	
	public static final ContentNodeTyped<Feature> FEATURE_WOODS;
	public static final ContentNodeTyped<Feature> FEATURE_PINE_WOODS;
	public static final ContentNodeTyped<Feature> FEATURE_RAINFOREST;
	public static final ContentNodeTyped<Feature> FEATURE_OASIS;
	
	public static final ContentNodeTyped<Resource> RESOURCE_DEER;
	public static final ContentNodeTyped<Resource> RESOURCE_DIAMOND;
	public static final ContentNodeTyped<Resource> RESOURCE_IRON;
	public static final ContentNodeTyped<Resource> RESOURCE_OIL;
	public static final ContentNodeTyped<Resource> RESOURCE_URANIUM;

	static {
		TILE_UNDISCOVERED = new ContentNodeTyped<>("Undiscovered", TILES, (ContentNodeTyped<Tile> ref) -> new Tile("TILE_UNDISCOVERED", false, false, new Yields(), 0, ref), TextureManager.TILE_UNDISCOVERED);
		
		TILE_OCEAN = new ContentNodeTyped<>("Ocean", TILES, (ContentNodeTyped<Tile> ref) -> new Tile("TILE_OCEAN", true, false, new Yields(1, 0, 0, 0, 0, 0), 10, ref), TextureManager.TILE_OCEAN);
		TILE_COAST = new ContentNodeTyped<>("Coast", TILES, (ContentNodeTyped<Tile> ref) -> new Tile("TILE_COAST", true, false, new Yields(1, 0, 0, 0, 0, 0), 10, ref), TextureManager.TILE_COAST);
		
		TILE_TUNDRA_MOUNTAIN = new ContentNodeTyped<>("Tundra Mountain", TILES, (ContentNodeTyped<Tile> ref) -> new Tile("TILE_TUNDRA_MOUNTAIN", true, true, new Yields(0, 0, 0, 0, 1, 0), 20, ref), TextureManager.TILE_TUNDRA_MOUNTAIN);
		TILE_GRASSLAND_MOUNTAIN = new ContentNodeTyped<>("Grassland Mountain", TILES, (ContentNodeTyped<Tile> ref) -> new Tile("TILE_GRASSLAND_MOUNTAIN", true, true, new Yields(0, 0, 0, 0, 1, 0), 20, ref), TextureManager.TILE_GRASSLAND_MOUNTAIN);
		TILE_PLAINS_MOUNTAIN = new ContentNodeTyped<>("Plains Mountain", TILES, (ContentNodeTyped<Tile> ref) -> new Tile("TILE_PLAINS_MOUNTAIN", true, true, new Yields(0, 0, 0, 0, 1, 0), 20, ref), TextureManager.TILE_PLAINS_MOUNTAIN);
		TILE_SNOW_MOUNTAIN = new ContentNodeTyped<>("Snow Mountain", TILES, (ContentNodeTyped<Tile> ref) -> new Tile("TILE_SNOW_MOUNTAIN", true, true, new Yields(0, 0, 0, 0, 1, 0), 20, ref), TextureManager.TILE_SNOW_MOUNTAIN);
		TILE_DESERT_MOUNTAIN = new ContentNodeTyped<>("Desert Mountain", TILES, (ContentNodeTyped<Tile> ref) -> new Tile("TILE_DESERT_MOUNTAIN", true, true, new Yields(0, 0, 0, 0, 1, 0), 20, ref), TextureManager.TILE_DESERT_MOUNTAIN);
		
		TILE_TUNDRA_HILL = new ContentNodeTyped<>("Tundra Hill", TILES, (ContentNodeTyped<Tile> ref) -> new Tile("TILE_TUNDRA_HILL", true, false, new Yields(1, 1, 0, 0, 0, 0), 15, ref), TextureManager.TILE_TUNDRA_HILL);
		TILE_GRASSLAND_HILL = new ContentNodeTyped<>("Grassland Hill", TILES, (ContentNodeTyped<Tile> ref) -> new Tile("TILE_TUNDRA_HILL", true, false, new Yields(2, 1, 0, 0, 0, 0), 15, ref), TextureManager.TILE_GRASSLAND_HILL);
		TILE_PLAINS_HILL = new ContentNodeTyped<>("Plains Hill", TILES, (ContentNodeTyped<Tile> ref) -> new Tile("TILE_TUNDRA_HILL", true, false, new Yields(1, 2, 0, 0, 0, 0), 15, ref), TextureManager.TILE_PLAINS_HILL);
		TILE_SNOW_HILL = new ContentNodeTyped<>("Snow Hill", TILES, (ContentNodeTyped<Tile> ref) -> new Tile("TILE_TUNDRA_HILL", true, false, new Yields(0, 1, 0, 0, 0, 0), 15, ref), TextureManager.TILE_SNOW_HILL);
		TILE_DESERT_HILL = new ContentNodeTyped<>("Desert Hill", TILES, (ContentNodeTyped<Tile> ref) -> new Tile("TILE_TUNDRA_HILL", true, false, new Yields(0, 1, 0, 0, 0, 0), 15, ref), TextureManager.TILE_DESERT_HILL);
		
		TILE_TUNDRA = new ContentNodeTyped<Tile>("Tundra", TILES, (ContentNodeTyped<Tile> ref) -> new Tile("TILE_TUNDRA", true, false, new Yields(1, 0, 0, 0, 0, 0), 10, ref), TextureManager.TILE_TUNDRA);
		TILE_GRASSLAND = new ContentNodeTyped<Tile>("Grassland", TILES, (ContentNodeTyped<Tile> ref) -> new Tile("TILE_GRASSLAND", true, false, new Yields(2, 0, 0, 0, 0, 0), 10, ref), TextureManager.TILE_GRASSLAND);
		TILE_PLAINS = new ContentNodeTyped<Tile>("Plains", TILES, (ContentNodeTyped<Tile> ref) -> new Tile("TILE_PLAINS", true, false, new Yields(1, 1, 0, 0, 0, 0), 10, ref), TextureManager.TILE_PLAINS);
		TILE_SNOW = new ContentNodeTyped<Tile>("Snow", TILES, (ContentNodeTyped<Tile> ref) -> new Tile("TILE_SNOW", true, false, new Yields(), 10, ref), TextureManager.TILE_SNOW);
		TILE_DESERT = new ContentNodeTyped<Tile>("Desert", TILES, (ContentNodeTyped<Tile> ref) -> new Tile("TILE_DESERT", true, false, new Yields(), 10, ref), TextureManager.TILE_DESERT);
		
		ContentManager.addTileCount(18);
		
		FEATURE_WOODS = new ContentNodeTyped<>("Woods", FEATURES, (ContentNodeTyped<Feature> ref) -> new Feature(Validators.plainsGrasslandValidator(), new Yields(1, 1, 0, 0, 0, 0), 5, ref), TextureManager.FEATURE_WOODS);
		FEATURE_PINE_WOODS = new ContentNodeTyped<>("Pine Woods", FEATURES, (ContentNodeTyped<Feature> ref) -> new Feature(Validators.plainsGrasslandSnowValidator(), new Yields(1, 1, 0, 0, 0, 0), 5, ref), TextureManager.FEATURE_PINE_WOODS);
		FEATURE_RAINFOREST = new ContentNodeTyped<>("Rainforest", FEATURES, (ContentNodeTyped<Feature> ref) -> new Feature(Validators.plainsGrasslandValidator(), new Yields(1, 1, 0, 0, 0, 0), 5, ref), TextureManager.FEATURE_RAINFOREST);
		FEATURE_OASIS = new ContentNodeTyped<>("Oasis", FEATURES, (ContentNodeTyped<Feature> ref) -> new Feature(Validators.desertFlatValidator(), new Yields(3, 0, 0, 0, 0, 0), 5, ref), TextureManager.FEATURE_OASIS);
		
		ContentManager.addFeatureCount(4);
		
		RESOURCE_DEER = new ContentNodeTyped<>("Deer", RESOURCES, (ContentNodeTyped<Resource> ref) -> new Resource(Validators.woodsValidator(), new Yields(2, 0, 0, 0, 0, 2), ResourceType.LUXURY, ref), TextureManager.RESOURCE_DEER);
		RESOURCE_DIAMOND = new ContentNodeTyped<>("Diamond", RESOURCES, (ContentNodeTyped<Resource> ref) -> new Resource(Validators.hillsValidator(), new Yields(0, 1, 0, 0, 0, 3), ResourceType.LUXURY, ref), TextureManager.RESOURCE_DIAMOND);
		RESOURCE_IRON = new ContentNodeTyped<>("Iron", RESOURCES, (ContentNodeTyped<Resource> ref) -> new Resource(Validators.hillsValidator(), new Yields(0, 2, 0, 0, 0, 0), ResourceType.STRATEGIC, ref), TextureManager.RESOURCE_IRON);
		RESOURCE_OIL = new ContentNodeTyped<>("Oil", RESOURCES, (ContentNodeTyped<Resource> ref) -> new Resource(Validators.flatDesertSnowCoastValidator(), new Yields(0, 3, 1, 0, 0, 0), ResourceType.STRATEGIC, ref), TextureManager.RESOURCE_OIL);
		RESOURCE_URANIUM = new ContentNodeTyped<>("Uranium", RESOURCES, (ContentNodeTyped<Resource> ref) -> new Resource(Validators.hillsValidator(), new Yields(0, 3, 2, 0, 0, 0), ResourceType.STRATEGIC, ref), TextureManager.RESOURCE_URANIUM);
		
		ContentManager.addResourceCount(5);
	}
	
	public static void initBaseContent() {
		Console.print("(Content) Loading base game content", 0);
	}

}
