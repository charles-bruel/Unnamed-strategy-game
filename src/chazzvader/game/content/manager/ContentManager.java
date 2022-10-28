package chazzvader.game.content.manager;

import java.util.ArrayList;

import chazzvader.game.content.building.Building;
import chazzvader.game.content.civilizations.Civilization;
import chazzvader.game.content.features.Feature;
import chazzvader.game.content.resources.Resource;
import chazzvader.game.content.tiles.Tile;
import chazzvader.game.content.units.Unit;
import chazzvader.game.other.Console;
import chazzvader.game.other.Coordinate;
import chazzvader.game.sided.both.game.map.Map;

public class ContentManager {

	private ContentManager() {
	}

	public static ContentNode getCore() {
		return core;
	}

	private static ContentNode core;

	public static void init() {
		Console.print("(Content) Initialized the content manager", 0);
		core = new ContentNode("Content");
	}

	private static ArrayList<TileGetter> tiles = new ArrayList<>();
	private static ArrayList<ContentGetter<Unit>> units = new ArrayList<>();
	private static ArrayList<ContentGetter<Feature>> features = new ArrayList<>();
	private static ArrayList<ContentGetter<Resource>> resources = new ArrayList<>();
	private static ArrayList<ContentGetter<Building>> buildings = new ArrayList<>();
	private static ArrayList<ContentGetter<Civilization>> civilizations = new ArrayList<>();

	public static ArrayList<TileGetter> getTiles() {
		return tiles;
	}

	public static ArrayList<ContentGetter<Unit>> getUnits() {
		return units;
	}

	public static ArrayList<ContentGetter<Feature>> getFeatures() {
		return features;
	}

	public static ArrayList<ContentGetter<Resource>> getResources() {
		return resources;
	}

	public static ArrayList<ContentGetter<Building>> getBuildings() {
		return buildings;
	}

	public static ArrayList<ContentGetter<Civilization>> getCivilizations() {
		return civilizations;
	}

	private static void printNode(ContentNode node, int indentationLevel) {
		String prefix = "";
		for (int i = 0; i < indentationLevel; i++) {
			prefix += "| ";
		}
		Console.printRaw(prefix + node.getName());
		ArrayList<ContentNode> nodes = node.getChildren();
		for (int i = 0; i < nodes.size(); i++) {
			printNode(nodes.get(i), indentationLevel + 1);
		}
	}

	public static class AllContent<T extends Content> {
		
	}
	
	public static class ContentNode {

		protected ArrayList<ContentNode> children;
		protected ContentNode parent;
		protected String name;

		public ContentNode(String name, ContentNode parent) {
			this(name);
			if (parent == null) {
				Console.print("(Content) There is no parent to node " + name, 1);
			}
			this.parent = parent;
			if(!parent.addChild(this)) {
				Console.print("(Content) Content mismatch! "+name+" is not valid for "+parent.getName(), 2);
			}
		}

		private ContentNode(String name) {
			this.name = name;
			children = new ArrayList<ContentNode>();
		}

		public ArrayList<ContentNode> getChildren() {
			return children;
		}

		public ContentNode getParent() {
			return parent;
		}

		public String getName() {
			return name;
		}

		public boolean addChild(ContentNode child) {
			children.add(child);
			return true;
		}

	}

	public static class ContentNodeParent<T extends Content> extends ContentNode {

		public ContentNodeParent(String name, ContentNode parent) {
			super(name, parent);
		}
		
		@Override
		public boolean addChild(ContentNode child) {
			super.addChild(child);
			if(child instanceof ContentNodeTyped) {
				return true;
			}
			return false;
		}
		
		/**
		 * Parameters only used by tile
		 */
		public T getByID(int id, Object p1, Object p2) {
			if(id < 0 || id >= children.size()) {
				throw new IllegalArgumentException("ID was too big or too small");
			}
			ContentNode node = children.get(id);
			@SuppressWarnings("unchecked")
			ContentNodeTyped<T> node2 = (ContentNodeTyped<T>) node;
			return node2.get(p1, p2);
		}
		
		public int getIDByObject(T obj) {
			String name = obj.getInName();
			for(int i = 0;i < children.size();i ++) {
				@SuppressWarnings("rawtypes")
				ContentNodeTyped node = (ContentNodeTyped) children.get(i);
				if(name.equalsIgnoreCase(node.get(null, null).getInName())) {
					return i;
				}
			}
			return -1;
		}

	}

	public static abstract class ContentNodeTyped<T extends Content> extends ContentNode {
		
		public ContentNodeTyped(String name, ContentNode parent) {
			super(name, parent);
		}

		/**
		 * Parameters only used by tile
		 */
		public abstract T get(Object p1, Object p2);

	}

	public static class ContentNodeTile extends ContentNodeTyped<Tile> {

		private TileGetter getter;

		public ContentNodeTile(String name, ContentNode parent, TileGetter getter) {
			super(name, parent);
			this.getter = getter;
			tiles.add(getter);
		}

		public Tile get(Object p1, Object p2) {
			if (p1 instanceof Map && p2 instanceof Coordinate) {
				return getter.get((Map) p1, (Coordinate) p2);
			}

			return getter.get(null, null);
		}

	}

	public static class ContentNodeUnit extends ContentNodeTyped<Unit> {

		private ContentGetter<Unit> getter;

		public ContentNodeUnit(String name, ContentNode parent, ContentGetter<Unit> getter) {
			super(name, parent);
			this.getter = getter;
			units.add(getter);
		}

		public Unit get(Object p1, Object p2) {
			return getter.get();
		}

	}

	public static class ContentNodeFeature extends ContentNodeTyped<Feature> {

		private ContentGetter<Feature> getter;

		public ContentNodeFeature(String name, ContentNode parent, ContentGetter<Feature> getter) {
			super(name, parent);
			this.getter = getter;
			features.add(getter);
		}

		public Feature get(Object p1, Object p2) {
			return getter.get();
		}

	}

	public static class ContentNodeResource extends ContentNodeTyped<Resource> {

		private ContentGetter<Resource> getter;

		public ContentNodeResource(String name, ContentNode parent, ContentGetter<Resource> getter) {
			super(name, parent);
			this.getter = getter;
			resources.add(getter);
		}

		public Resource get(Object p1, Object p2) {
			return getter.get();
		}

	}

	public static class ContentNodeBuilding extends ContentNodeTyped<Building> {

		private ContentGetter<Building> getter;

		public ContentNodeBuilding(String name, ContentNode parent, ContentGetter<Building> getter) {
			super(name, parent);
			this.getter = getter;
			buildings.add(getter);
		}

		public Building get(Object p1, Object p2) {
			return getter.get();
		}

	}

	public static class ContentNodeCivilization extends ContentNodeTyped<Civilization> {

		private ContentGetter<Civilization> getter;

		public ContentNodeCivilization(String name, ContentNode parent, ContentGetter<Civilization> getter) {
			super(name, parent);
			this.getter = getter;
			civilizations.add(getter);
		}

		public Civilization get(Object p1, Object p2) {
			return getter.get();
		}

	}

	public static interface TileGetter {
		public Tile get(Map map, Coordinate coordinate);
	}

	public static interface ContentGetter<T extends Content> {
		public T get();
	}

	public static void printContent() {
		Console.print("(Content) Printing", 0);
		printNode(core, 0);
	}

}
