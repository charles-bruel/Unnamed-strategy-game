package chazzvader.game.sided.both.game.pathing;

import java.util.ArrayList;
import java.util.HashMap;

import chazzvader.game.content.Tile;
import chazzvader.game.input.InputHelper;
import chazzvader.game.other.Console;
import chazzvader.game.other.SubTileCoordinate;
import chazzvader.game.sided.both.game.map.Map;

public class AreaPath {
	
	private final SubTileCoordinate START;

	private Map map;

	private HashMap<SubTileCoordinate, Node> open;

	public HashMap<SubTileCoordinate, Node> getClosed() {
		return closed;
	}

	private HashMap<SubTileCoordinate, Node> closed;
	
	private ArrayList<SubTileCoordinate> movableTiles;

	public ArrayList<SubTileCoordinate> getMovableTiles() {
		return movableTiles;
	}

	private final boolean WATER;

	private int maxPathLength;

	/**
	 * Updates the map, called by constructor. The only reason for an external call
	 * for this would be changes to the map. This method is public mostly for
	 * flexibility.
	 */
	public void update() {
		Console.print("(Pathing) Updating", -1);
		Tile t = map.getTiles()[START.getX()][START.getY()];
		if (t == null || !t.isPassable() || t.isLand() == WATER) {
			return;
		}
		
		open = new HashMap<>();
		closed = new HashMap<>();
		movableTiles = new ArrayList<>();

		open.put(START, new Node(START, START));
		
		while(open.size() > 0) {
			Node current = open.values().iterator().next();
			
			open.remove(current.getCoord());
			if(current.getGcost() > maxPathLength) {
				continue;
			}
			closed.put(current.getCoord(), current);
			movableTiles.add(current.getCoord());
			ArrayList<SubTileCoordinate> adjacent = InputHelper.adjacentSubTile(current.getCoord(), map.getWidth(),
					map.getHeight());
	
			for (int i = 0; i < adjacent.size(); i++) {
				SubTileCoordinate c = adjacent.get(i);
				Node n;
				t = map.tileAt(c);
				if (t == null || !t.isPassable() || t.isLand() == WATER || closed.containsKey(c)) {
					continue;
				}
	
				int pathLength = pathLengthFrom(c, current, map);
	
				if(pathLength > maxPathLength)
					continue;
				
				if (open.containsKey(c)) {
					n = open.get(c);
					if (n.getGcost() > pathLength) {
						n.setGcost(pathLength);
					}
				} else {
					n = new Node(c, START);
					open.put(c, n);
					n.setGcost(pathLength);
				}
			}
		}
	}

	private int pathLengthFrom(SubTileCoordinate next, Node prev, Map m) {
		Tile t = m.tileAt(next);
		int pl = t.getMoveCost(next.getSubTile(), m);
		return pl + prev.getGcost();
	}

	/**
	 * @return the sTART
	 */
	public SubTileCoordinate getSTART() {
		return START;
	}

	/**
	 * @return the water
	 */
	public boolean isWater() {
		return WATER;
	}

	public AreaPath(SubTileCoordinate start, Map map, boolean water, int maxPathLength) {
		START = start;
		this.map = map;
		this.WATER = water;
		this.maxPathLength = maxPathLength;
		update();
	}
	
	private class Node {

		private SubTileCoordinate coord;
		private int gcost;//Distance to here from start

		public SubTileCoordinate getCoord() {
			return coord;
		}

		public int getGcost() {
			return gcost;
		}

		public void setGcost(int gcost) {
			this.gcost = gcost;
		}

		public Node(SubTileCoordinate coord, SubTileCoordinate start) {
			this.coord = coord;
			if(coord == start) gcost = 0;
		}
		
	}

}
