package chazzvader.game.sided.both.game.pathing;

import chazzvader.game.input.InputHelper;
import chazzvader.game.other.SubTileCoordinate;

public class Node {

	private SubTileCoordinate coord;
	private SubTileCoordinate pointer;
	private double gcost;//Distance to here from start
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

	public void setCoord(SubTileCoordinate coord) {
		this.coord = coord;
	}

	public double getGcost() {
		return gcost;
	}

	public void setGcost(double gcost) {
		this.gcost = gcost;
	}

	public double getHcost() {
		return hcost;
	}

	public void setHcost(double hcost) {
		this.hcost = hcost;
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
