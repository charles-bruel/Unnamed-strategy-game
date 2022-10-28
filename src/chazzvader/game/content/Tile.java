package chazzvader.game.content;

import java.util.Arrays;

import chazzvader.game.content.ContentManager.ContentNodeTyped;
import chazzvader.game.content.building.Building;
import chazzvader.game.other.Coordinate;
import chazzvader.game.other.SubTileCoordinate.SubTile;
import chazzvader.game.sided.both.game.Player;
import chazzvader.game.sided.both.game.map.Map;

/**
 * Class represnting a tile, contains the resource, feature and buildings on the
 * tile
 * 
 * @author csbru
 * @since 1.0
 * @version 1.0
 */
public class Tile extends AContent {

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate c) {
		this.coordinate = c;
	}
	
	private Map map;
	
	private ContentNodeTyped<Tile> parentNode;

	public Tile(String uloc, boolean passable, boolean land, Feature feature, Resource resource, Building[] buildings,
			Map map, Coordinate coordinate, Yields defaultTileYields, int moveCost, ContentNodeTyped<Tile> parentNode) {
		this.uloc = uloc;
		this.passable = passable;
		this.land = land;
		this.feature = feature;
		this.coordinate = coordinate;
		this.defaultTileYields = defaultTileYields;
		if (buildings != null) {
			this.buildings = buildings;
		}
		this.map = map;
		this.moveCost = moveCost;
		this.resource = resource;
		this.parentNode = parentNode;
	}
	
	public Tile(String uloc, boolean passable, boolean land, Yields defaultTileYields, int moveCost, ContentNodeTyped<Tile> parentNode) {
		this(uloc, passable, land, null, null, null, null, null, defaultTileYields, moveCost, parentNode);
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public boolean isNatrualWonder() {
		return false;
	}
	
	protected Yields baseYields;

	protected Building[] buildings = new Building[9];

	protected Coordinate coordinate;

	protected Feature feature;

	protected boolean passable, land;

	protected Resource resource;

	protected boolean[] rivers = new boolean[6];// NW NE E SE SW W // TODO: 4 Change rivers to class, add direction

	protected String uloc;

	protected Yields[] yields = new Yields[9];

	/**
	 * Adds a feature to a tile. Fails if the is already a feature or the feature is
	 * invalid on the tile
	 * 
	 * @param f The feature to add
	 * @param m The map the tile is on
	 * @return The tile you called it from
	 */
	public Tile addFeature(Feature f, Map m) {
		if (feature != null)
			return this;
		return setFeature(f);
	}

	/**
	 * Adds a resource to a tile. Fails if the is already a resource or the resource
	 * is invalid on the tile
	 * 
	 * @param r The resource to add
	 * @param m The map the tile is on
	 * @return The tile you called it from
	 */
	public Tile addResource(Resource r, Map m) {
		if (resource != null)
			return this;
		return setResource(r);
	}

	/**
	 * Returns the base yields of the tile
	 * 
	 * @return
	 */
	public Yields getBaseYields() {
		if (baseYields == null) {
			Yields y = getTileYields();
			y = addItemYields(y, null);
			return y;
		}
		return baseYields;
	}

	/**
	 * Returns an array containing all the buildings on the tile
	 * 
	 * @return An array containing all the buildings on the tile
	 */
	public Building[] getBuildings() {
		return buildings;
	}

	/**
	 * Returns the feature on the tile
	 * 
	 * @return The feature on the tile
	 */
	public Feature getFeature() {
		return feature;
	}

	public String getInName() {
		return parentNode.getName().replace(" ", "");
	}

	/**
	 * Returns the movement cost of the tile, or in cases when the subtile is north
	 * or south, the average move cost of the three tiles that the subtile covers
	 * 
	 * @param st The subtile to check from
	 * @param m  The map used to average yields on north and south subtiles
	 * @return The proper movement cost
	 */
	public int getMoveCost(SubTile st, Map m) {
		if (st == SubTile.FLOATING_NORTH || st == SubTile.FLOATING_SOUTH) {
			Tile[][] tiles = m.getTiles();
			Tile tileA, tileB;
			if(coordinate == null) {
				return getTileMoveCost();
			}
			int xp = coordinate.getX(), yp = coordinate.getY();
			if (yp >= tiles[0].length - 1) {
				return getTileMoveCost();
			}

			int typ;

			if (st == SubTile.FLOATING_NORTH) {
				typ = yp - 1;
				if (typ < 0) {
					typ += tiles[0].length;
				}
			} else {
				typ = yp + 1;
				if (typ >= tiles[0].length) {
					typ -= tiles[0].length;
				}
			}
			if (yp % 2 == 1) {
				tileA = tiles[xp][typ];
				int txp = xp + 1;
				if (txp >= tiles.length) {
					txp -= tiles.length;
				}
				tileB = tiles[txp][typ];
			} else {
				int txp = xp - 1;
				if (txp < 0) {
					txp += tiles.length;
				}
				tileA = tiles[txp][typ];
				tileB = tiles[xp][typ];
			}

			return (tileA.getTileMoveCost() + tileB.getTileMoveCost() + getTileMoveCost()) / 3;
		}
		return getMoveCost(st);
	}

	/**
	 * Returns the resource on the tile
	 * 
	 * @return The resource on the tile
	 */
	public Resource getResource() {
		return resource;
	}

	/**
	 * Returns an array representing the rivers on the tile
	 * 
	 * @return An array representing the rivers on the tile
	 */
	public boolean[] getRivers() {
		return rivers;
	}

	/**
	 * Returns the movement cost of the tile
	 * 
	 * @return The movement cost of the tile
	 */
	public int getTileMoveCost() {
		return moveCost() + (feature == null ? 0 : feature.addedMoveCost());
	}

	/**
	 * Returns the unlocalized name of the tile
	 * 
	 * @return The unlocalized name of the tile
	 */
	public String getUlocName() {
		return uloc;
	}

	/**
	 * Returns an array representing the yields on each of the subtiles
	 * 
	 * @return An array representing the yields on each of the subtiles
	 */
	public Yields[] getYields() {
		return yields;
	}

	/**
	 * Returns true if there is any river on the tile
	 * 
	 * @return Returns true if there is any river on the tile
	 */
	public boolean hasRiver() {
		return rivers[0] || rivers[1] || rivers[2] || rivers[3] || rivers[4] || rivers[5];
	}

	/**
	 * Returns true if the tile is land
	 * 
	 * @return Returns true if the tile is land
	 */
	public boolean isLand() {
		return land;
	}

	/**
	 * Returns true if the tile is passable (by land or water)
	 * 
	 * @return Returns true if the tile is passable (by land or water)
	 */
	public boolean isPassable() {
		return passable;
	}

	private int moveCost;
	
	/**
	 * Returns the movement cost of the tile alone
	 * 
	 * @return The movement cost of the tile alone
	 */
	public int moveCost() {
		return moveCost;
	}
	
	/**
	 * Adds a resource to a tile. Fails if the feature is invalid on the tile
	 * 
	 * @param f The feature to set
	 * @param m The map the tile is on
	 * @return The tile you called it from
	 */
	public Tile setFeature(Feature f) {
		if (!f.validate(this))
			return this;
		feature = f;
		return this;
	}

	public void setPassable(boolean passable) {
		this.passable = passable;
	}

	/**
	 * Adds a resource to a tile. Fails if the resource is invalid on the tile
	 * 
	 * @param r The resource to set
	 * @param m The map the tile is on
	 * @return The tile you called it from
	 */
	public Tile setResource(Resource r) {
		if (!r.validate(this))
			return this;
		resource = r;
		return this;
	}

	public void setRiver(boolean river, int id) {
		if (id < 0 || id >= rivers.length)
			return;
		this.rivers[id] = river;
	}

	public void setRivers(boolean[] rivers) {
		this.rivers = rivers;
	}

	public void setUlocName(String uloc) {
		this.uloc = uloc;
	}

	public void updateYields(Player p) {
		if (map == null) {
			return;
		}
		Yields y = getTileYields();
		y = addItemYields(y, p);
		baseYields = y;
		for (int i = 0; i < yields.length; i++) {
			SubTile subTile = SubTile.fromID(i);
			if (subTile == SubTile.FLOATING_SOUTH) {
				if(coordinate == null) {
					yields[i] = new Yields();
					continue;
				}
				Tile[][] tiles = map.getTiles();
				Tile tileA, tileB;
				int xp = coordinate.getX(), yp = coordinate.getY();
				if (yp >= tiles[0].length - 1) {
					yields[i] = new Yields();
					continue;
				}
				if (yp % 2 == 1) {
					tileA = tiles[xp][yp + 1];
					int txp = xp + 1;
					if (txp >= tiles.length) {
						txp -= tiles.length;
					}
					tileB = tiles[txp][yp + 1];
				} else {
					int txp = xp - 1;
					if (txp < 0) {
						txp += tiles.length;
					}
					tileA = tiles[txp][yp + 1];
					tileB = tiles[xp][yp + 1];
				}
				if(tileA == null || tileB == null) {
					yields[i] = new Yields();
					continue;
				}
				yields[i] = Yields.combine(baseYields, tileA.getBaseYields(), tileB.getBaseYields());
			} else if (subTile == SubTile.FLOATING_NORTH) {
				if(coordinate == null) {
					yields[i] = new Yields();
					continue;
				}
				Tile[][] tiles = map.getTiles();
				Tile tileA, tileB;
				int xp = coordinate.getX(), yp = coordinate.getY();
				if (yp <= 0) {
					yields[i] = new Yields();
					continue;
				}
				if (yp % 2 == 1) {
					tileA = tiles[xp][yp - 1];
					int txp = xp + 1;
					if (txp >= tiles.length) {
						txp -= tiles.length;
					}
					tileB = tiles[txp][yp - 1];
				} else {
					int txp = xp - 1;
					if (txp < 0) {
						txp += tiles.length;
					}
					tileA = tiles[txp][yp - 1];
					tileB = tiles[xp][yp - 1];
				}
				if(tileA == null || tileB == null) {
					yields[i] = new Yields();
					continue;
				}
				yields[i] = Yields.combine(baseYields, tileA.getBaseYields(), tileB.getBaseYields());
			} else {
				yields[i] = baseYields;
			}
			if (buildings[i] != null)
				yields[i] = buildings[i].addYields(y, p);
		}
	}

	private Yields addRiverYields(Yields y) {
		boolean r = rivers[0] || rivers[1] || rivers[2] || rivers[3] || rivers[4] || rivers[5];
		if (!r) {
			return y;
		}
		int g = 0;
		if (r) {
			for (int i = 0; i < rivers.length; i++) {
				if (rivers[i])
					g++;
			}
		}
		return y.add(g, 1, 0, 0, 0, 0);
	}

	private int getMoveCost(SubTile st) {
		return getTileMoveCost() + (riverOnSubTile(st) ? 15 : 0);
	}

	private boolean riverOnSubTile(SubTile st) {
		if (!hasRiver())
			return false;
		if (st == SubTile.FLOATING_NORTH || st == SubTile.FLOATING_SOUTH)
			return false;
		if (st == SubTile.CENTER)
			return true;
		if (st == SubTile.NORTH_WEST && rivers[0])
			return true;
		if (st == SubTile.NORTH_EAST && rivers[1])
			return true;
		if (st == SubTile.EAST && rivers[2])
			return true;
		if (st == SubTile.SOUTH_EAST && rivers[3])
			return true;
		if (st == SubTile.SOUTH_WEST && rivers[4])
			return true;
		if (st == SubTile.WEST && rivers[5])
			return true;
		return false;
	}

	protected Yields addItemYields(Yields y, Player p) {
		y = addRiverYields(y);
		if (feature != null)
			y = feature.addYields(y);
		if (resource != null)
			y = resource.addYields(y, p);
		return y;
	}
	
	private Yields defaultTileYields;

	protected Yields getTileYields() {
		return defaultTileYields.copy();
	}

	public ContentNodeTyped<Tile> getParentNode() {
		return parentNode;
	}

	@Override
	public String toString() {
		return "Tile [map=" + map + ", parentNode=" + parentNode + ", baseYields=" + baseYields + ", buildings="
				+ Arrays.toString(buildings) + ", coordinate=" + coordinate + ", feature=" + feature + ", passable="
				+ passable + ", land=" + land + ", resource=" + resource + ", rivers=" + Arrays.toString(rivers)
				+ ", uloc=" + uloc + ", yields=" + Arrays.toString(yields) + ", moveCost=" + moveCost
				+ ", defaultTileYields=" + defaultTileYields + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseYields == null) ? 0 : baseYields.hashCode());
		result = prime * result + Arrays.hashCode(buildings);
		result = prime * result + ((coordinate == null) ? 0 : coordinate.hashCode());
		result = prime * result + ((defaultTileYields == null) ? 0 : defaultTileYields.hashCode());
		result = prime * result + ((feature == null) ? 0 : feature.hashCode());
		result = prime * result + (land ? 1231 : 1237);
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		result = prime * result + moveCost;
		result = prime * result + ((parentNode == null) ? 0 : parentNode.hashCode());
		result = prime * result + (passable ? 1231 : 1237);
		result = prime * result + ((resource == null) ? 0 : resource.hashCode());
		result = prime * result + Arrays.hashCode(rivers);
		result = prime * result + ((uloc == null) ? 0 : uloc.hashCode());
		result = prime * result + Arrays.hashCode(yields);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (baseYields == null) {
			if (other.baseYields != null)
				return false;
		} else if (!baseYields.equals(other.baseYields))
			return false;
		if (!Arrays.equals(buildings, other.buildings))
			return false;
		if (coordinate == null) {
			if (other.coordinate != null)
				return false;
		} else if (!coordinate.equals(other.coordinate))
			return false;
		if (defaultTileYields == null) {
			if (other.defaultTileYields != null)
				return false;
		} else if (!defaultTileYields.equals(other.defaultTileYields))
			return false;
		if (feature == null) {
			if (other.feature != null)
				return false;
		} else if (!feature.equals(other.feature))
			return false;
		if (land != other.land)
			return false;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		if (moveCost != other.moveCost)
			return false;
		if (parentNode == null) {
			if (other.parentNode != null)
				return false;
		} else if (!parentNode.equals(other.parentNode))
			return false;
		if (passable != other.passable)
			return false;
		if (resource == null) {
			if (other.resource != null)
				return false;
		} else if (!resource.equals(other.resource))
			return false;
		if (!Arrays.equals(rivers, other.rivers))
			return false;
		if (uloc == null) {
			if (other.uloc != null)
				return false;
		} else if (!uloc.equals(other.uloc))
			return false;
		if (!Arrays.equals(yields, other.yields))
			return false;
		return true;
	}

}
