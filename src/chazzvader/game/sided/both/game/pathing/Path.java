package chazzvader.game.sided.both.game.pathing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import chazzvader.game.content.tiles.Tile;
import chazzvader.game.input.InputHelper;
import chazzvader.game.other.Console;
import chazzvader.game.other.SubTileCoordinate;
import chazzvader.game.sided.both.game.map.Map;

public class Path {

	private final SubTileCoordinate GOAL;
	private final SubTileCoordinate START;

	private Map map;

	private HashMap<SubTileCoordinate, Node> open;

	public HashMap<SubTileCoordinate, Node> getClosed() {
		return closed;
	}

	private HashMap<SubTileCoordinate, Node> closed;

	private ArrayList<SubTileCoordinate> path;

	private final boolean WATER;

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

		open = new HashMap<>(256);// create open
		closed = new HashMap<>(1024);// create closed
		open.put(START, new Node(START, START, GOAL, map.getWidth()));// add the start node to open

		int iterations = 0;
		while (true) {// loop
			iterations++;

			if (iterations > 4096) {
				Console.print("(Pathing) Unable to find path (exceeded max iterations)", 1);
				break;
			}

			// Find node with lowest f-cost
			ArrayList<Node> openCollection = new ArrayList<Node>(open.values());

			if (openCollection.isEmpty()) {
				Console.print("(Pathing) Unable to find path (ran out of open nodes)", 1);
				break;
			}

			double lowestF = Double.MAX_VALUE;
			int lowestIndex = 0;

			for (int i = 0; i < openCollection.size(); i++) {
				if (openCollection.get(i).getFcost() < lowestF) {
					lowestF = openCollection.get(i).getFcost();
					lowestIndex = i;
				}
			}

			Node current = openCollection.get(lowestIndex);// Still finding node with lowest f-cost

			open.remove(current.getCoord());
			closed.put(current.getCoord(), current);

			if (current.getCoord().sameAs(GOAL)) {
				Console.print("(Pathing) Found goal", -1);
				break;
			}

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

				if (open.containsKey(c)) {
					n = open.get(c);
					if (n.getGcost() > pathLength) {
						n.setGcost(pathLength);
						n.setPointer(current.getCoord());
					}
				} else {
					n = new Node(c, START, GOAL, map.getWidth());
					open.put(c, n);
					n.setPointer(current.getCoord());
					n.setGcost(pathLength);
				}
			}
		}
		Console.print("(Pathing) Completed with " + closed.size() + " built nodes at " + iterations + " iterations",
				-1);
		path = traceBack();
	}

	private int pathLengthFrom(SubTileCoordinate next, Node prev, Map m) {
		Tile t = m.tileAt(next);
		int pl = t.getMoveCost(next.getSubTile(), m);
		return pl + prev.getGcost();
	}

	/**
	 * @return the gOAL
	 */
	public SubTileCoordinate getGOAL() {
		return GOAL;
	}

	/**
	 * @return the sTART
	 */
	public SubTileCoordinate getSTART() {
		return START;
	}

	/**
	 * Method to retrieve the path. Due to internal reasons, its backwards.
	 * @return The path, represented by an ArrayList of SubTileCoordinates
	 */
	public ArrayList<SubTileCoordinate> getPath() {
		return path;
	}

	/**
	 * @return the water
	 */
	public boolean isWater() {
		return WATER;
	}

	private ArrayList<SubTileCoordinate> traceBack() {
		ArrayList<SubTileCoordinate> r = new ArrayList<>(5);
		if(closed == null || open == null || closed.size() == 0) {
			Console.print("(Pathing) Unable to find path (pathing not initilized) (" + GOAL.toString() + ")", 1);
			return null;
		}
		Node start = closed.get(GOAL);
		if (start == null) {
			Console.print("(Pathing) Unable to find path (invalid start) (" + GOAL.toString() + ")", 1);
			return null;
		}
		SubTileCoordinate current = start.getCoord();
		while (true) {
			r.add(current);
			if (current.sameAs(START)) {
				return r;
			}
			Node n = closed.get(current);
			if (n == null) {
				Console.print("(Pathing) Null node at index " + r.size(), 1);
				Collections.reverse(r);
				return r;
			}
			if (n.getPointer() == null) {
				Console.print("(Pathing) Null pointer at index " + r.size(), 1);
			}
			if (n.getPointer().sameAs(current)) {
				Console.print("(Pathing) Pointer points to itself at index " + r.size(), 1);
				Collections.reverse(r);
				return r;
			}
			if (r.size() > 1024) {
				Console.print("(Pathing) Traceback exceeds max size", 1);
				Collections.reverse(r);
				return r;
			}
			current = n.getPointer();
		}
	}
	
	/**
	 * Returns the movement cost of all nodes but the starting node, added together
	 * @return
	 */
	public int pathLength() {
		ArrayList<SubTileCoordinate> a = traceBack();
		if (a == null)
			return -1;
		int r = 0;
		for (int i = 0; i < a.size() - 1; i++) {
			r += map.tileAt(a.get(i)).getMoveCost(a.get(i).getSubTile(), map);
		}
		return r;
	}

	public Path(SubTileCoordinate start, SubTileCoordinate goal, Map map, boolean water) {
		GOAL = goal;
		START = start;
		this.map = map;
		this.WATER = water;
		update();
	}
	
	private class Node {

		private SubTileCoordinate coord;
		private SubTileCoordinate pointer;
		private int gcost;//Distance to here from start
		private double hcost;//Distance to end

		public SubTileCoordinate getPointer() {
			return pointer;
		}

		public void setPointer(SubTileCoordinate pointer) {
			this.pointer = pointer;
		}

		public SubTileCoordinate getCoord() {
			return coord;
		}

		public int getGcost() {
			return gcost;
		}

		public void setGcost(int gcost) {
			this.gcost = gcost;
		}

		public double getFcost(){
			return hcost + gcost;
		}

		public Node(SubTileCoordinate coord, SubTileCoordinate start, SubTileCoordinate goal, int w) {
			this.coord = coord;
			hcost = InputHelper.distance(coord, goal, w);
			if(coord == start) gcost = 0;
		}
		
	}

}
