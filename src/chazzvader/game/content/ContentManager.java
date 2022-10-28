package chazzvader.game.content;

import java.util.ArrayList;
import java.util.HashMap;

import chazzvader.game.content.building.Building;
import chazzvader.game.content.civilizations.Civilization;
import chazzvader.game.content.units.Unit;
import chazzvader.game.engine.render.TextureManager.Texture;
import chazzvader.game.other.Console;
import chazzvader.game.other.Settings;

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

	private static int tileCount = 0, featureCount = 0, resourceCount = 0;
	
	public static void addTileCount(int tileCount) {
		ContentManager.tileCount += tileCount;
	}
	
	public static void addFeatureCount(int featureCount) {
		ContentManager.featureCount += featureCount;
	}
	
	public static void addResourceCount(int resourceCount) {
		ContentManager.resourceCount += resourceCount;
	}
	
	public static AContent getFromID(Class<? extends AContent> type, int id) {
		if(!content.containsKey(type) || id >= content.get(type).size() || id < 0) {
			Console.print("(Content) Invalid content type "+type.getName() + " and id "+id, 2);
			return null;
		}
		AContent ret = content.get(type).get(id).get();
		if(!type.isInstance(ret)) {
			Console.print("(Content) Content type mismatch "+ret + " and id "+id, 2);
			return null;
		}
		return ret;
	}
	
	public static int toID(Class<? extends AContent> type, AContent target) {
		if(target.getClass() != type) {
			Console.print("(Content) Content type mismatch "+type.getName(), 2);
			return -1;
		}
		if(!content.containsKey(type)) {
			Console.print("(Content) Invalid content type "+type.getName(), 2);
			return -1;
		}
		ArrayList<ContentNodeTyped<? extends AContent>> arrayList = content.get(type);
		for(int i = 0;i < arrayList.size();i ++) {
			ContentNodeTyped<? extends AContent> item = arrayList.get(i);
			AContent get = item.get();
			if(get.equals(target)) {
				return i;
			}
		}
		Console.print("(Content) Couldn't find content "+target, 1);
		return -1;
	}
	
	private static HashMap<Class<? extends AContent>, ArrayList<ContentNodeTyped<? extends AContent>>> content = new HashMap<>();
	
	private static ArrayList<ContentGetter<Tile>> tiles = new ArrayList<>();
	private static ArrayList<ContentGetter<Unit>> units = new ArrayList<>();
	private static ArrayList<ContentGetter<Feature>> features = new ArrayList<>();
	private static ArrayList<ContentGetter<Resource>> resources = new ArrayList<>();
	private static ArrayList<ContentGetter<Building>> buildings = new ArrayList<>();
	private static ArrayList<ContentGetter<Civilization>> civilizations = new ArrayList<>();

	public static ArrayList<ContentGetter<Tile>> getTiles() {
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

	public static class AllContent<T extends AContent> {
		
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

	public static class ContentNodeParent<T extends AContent> extends ContentNode {

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
		
		public T getByID(int id) {
			if(id < 0 || id >= children.size()) {
				throw new IllegalArgumentException("ID was too big or too small");
			}
			ContentNode node = children.get(id);
			@SuppressWarnings("unchecked")
			ContentNodeTyped<T> node2 = (ContentNodeTyped<T>) node;
			return node2.get();
		}
		
		public int getIDByObject(T obj) {
			String name = obj.getInName();
			for(int i = 0;i < children.size();i ++) {
				@SuppressWarnings("rawtypes")
				ContentNodeTyped node = (ContentNodeTyped) children.get(i);
				if(name.equalsIgnoreCase(node.get().getInName())) {
					return i;
				}
			}
			return -1;
		}

	}

	public static class ContentNodeTyped<T extends AContent> extends ContentNode {
		
		public final Texture tex;
		
		private ContentGetter<T> getter;
		
		public ContentNodeTyped(String name, ContentNode parent, ContentGetter<T> getter, Texture tex) {
			super(name, parent);
			this.getter = getter;
			this.tex = tex;
			Class<? extends AContent> classType = getter.get(null).getClass();
			if(!content.containsKey(classType)) {
				content.put(classType, new ArrayList<ContentManager.ContentNodeTyped<? extends AContent>>());
			}
			content.get(classType).add(this);
		}

		public T get() {
			return getter.get(this);
		}

	}

	public static interface ContentGetter<T extends AContent> {
		public T get(ContentNodeTyped<T> parentNode);
	}

	public static void printContent(int level) {
		if(level <= -1 && !Settings.getDebug()) {
			return;
		}
		Console.print("(Content) Printing", level);
		printNode(core, 0);
	}

	public static int getTileCount() {
		return tileCount;
	}

	public static int getFeatureCount() {
		return featureCount;
	}

	public static int getResourceCount() {
		return resourceCount;
	}

}
