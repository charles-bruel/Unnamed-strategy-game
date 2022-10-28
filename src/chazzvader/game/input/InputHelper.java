package chazzvader.game.input;

import java.util.ArrayList;

import chazzvader.game.other.Coordinate;
import chazzvader.game.other.Direction;
import chazzvader.game.other.HexagonData;
import chazzvader.game.other.SubTileCoordinate;
import chazzvader.game.other.SubTileCoordinate.SubTile;
import chazzvader.game.sided.client.ClientManager;

/**
 * Various methods to help with input
 * @author csbru
 * @since 1.0
 * @version 1.0
 */
public final class InputHelper {

	private InputHelper() {}
	
	/**
	 * Basic method for checking whether a point is within a regular hexagon with the upper left corner at 0, 0
	 * @param xm The x-pos to check from
	 * @param ym The y-pos to check from
	 * @param x1 The width of the hexagon
	 * @param y1 The height of the hexagon
	 */
	public static boolean withinHexagon(int xm, int ym, int x1, int y1) {
		if(xm < 0 || xm > x1 || ym < 0 || ym > y1) {
			return false;
		}
		
		double r = y1/2;
		double y2 = r/2;
		
		if (xm >= 0 && xm <= x1 && ym >= y2 && ym <= y1 - y2) {
			return true;
		}
		
		double z = x1/2;
		double s = y2/z;
		
		if (ym >= -s * xm + y2 && ym <= y2 && xm <= z) {
			return true;
		}
		
		if (ym >= s * xm - y2 && ym <= y2 && xm >= z) {
			return true;
		}
		
		if (ym <= -s * xm + y1 + y2 && ym >= y1 - y2 && xm >= z) {
			return true;
		}
		
		if (ym <= s * xm + y1 - y2 && ym >= y1 - y2 && xm <= z) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Checks if an x and y coordinate is within a box
	 * @param x The x coordinate to check
	 * @param y The y coordinate to check
	 * @param x1 The 1st x position of the area
	 * @param y1 The 1st y position of the area
	 * @param x2 The 2nd x position of the area
	 * @param y2 The 2nd y position of the area
	 * @return If x and y are within x1, y1, x2, y2
	 */
	public static boolean within(int x, int y, int x1, int y1, int x2, int y2) {
		if(x > x1 && x < x2 && y > y1 && y < y2) return true;
		return false;
	}
	
	/**
	 * Basic method for checking whether a point is within a regular hexagon with the upper left corner at xp, yp
	 * @param xm The x-pos to check from
	 * @param ym The y-pos to check from
	 * @param xp The x of the upper left corner of the hexagon
	 * @param xp The y of the upper left corner of the hexagon
	 * @param x1 The width of the hexagon
	 * @param y1 The height of the hexagon
	 */
	public static boolean withinHexagon(int xm, int ym, int xp, int yp, int x1, int y1) {
		return withinHexagon(xm-xp, ym-yp, x1, y1);
	}
	
	/**
	 * Basic method for checking whether a point is within a regular hexagon with the center at xp, yp
	 * @param xm The x-pos to check from
	 * @param ym The y-pos to check from
	 * @param xp The x of the upper left corner of the hexagon
	 * @param xp The y of the upper left corner of the hexagon
	 * @param x1 The width of the hexagon
	 * @param y1 The height of the hexagon
	 */
	public static boolean withinHexagonCenter(int xm, int ym, int xp, int yp, int x1, int y1) {
		return withinHexagon(xm-xp+x1/2, ym-yp+y1/2, x1, y1);
	}
	
	public static ArrayList<Coordinate> addIfNew(ArrayList<Coordinate> a, Coordinate c, int w, int h){
		for(int i = 0;i < a.size();i ++) {
			if(a.get(i).sameAs(c)) return a;
		}
		if(c.getX() < 0) {
			c.setX(c.getX()+w);
		}
		if(c.getX() >= w) {
			c.setX(c.getX()-w);
		}
		if(c.getY() < 0) {
			return a;
		}
		if(c.getY() >= h) {
			return a;
		}
		a.add(c);
		return a;
	}
	
	public static ArrayList<Coordinate> adjacent(ArrayList<Coordinate> adj, int w, int h){
		ArrayList<Coordinate> r = new ArrayList<Coordinate>(adj.size()*4);
		for(int i = 0;i < adj.size();i ++) {//Add all given values
			r = addIfNew(r, adj.get(i), w, h);
		}
		for(int i = 0;i < adj.size();i ++) {//For all values, get adjacents
			Coordinate tc = adj.get(i);
			int m = tc.getY() % 2 == 1 ? 1 : -1;
			r = addIfNew(r, new Coordinate(tc.getX()-1, tc.getY()), w, h);
			r = addIfNew(r, new Coordinate(tc.getX()+1, tc.getY()), w, h);
			r = addIfNew(r, new Coordinate(tc.getX(), tc.getY()+1), w, h);
			r = addIfNew(r, new Coordinate(tc.getX(), tc.getY()-1), w, h);
			r = addIfNew(r, new Coordinate(tc.getX()+m, tc.getY()+1), w, h);
			r = addIfNew(r, new Coordinate(tc.getX()+m, tc.getY()-1), w, h);
		}
		return r;
	}
	
	public static ArrayList<Coordinate> adjacent(Coordinate adj, int w, int h){
		ArrayList<Coordinate> a = new ArrayList<>(1);
		a.add(adj);
		return adjacent(a, w, h);
	}
	
	public static SubTileCoordinate determineTilePositionAtMouse(ClientManager client) {
		//return determineTilePositionAtPoint(client, MainOld.getFrame().getMouseX(), MainOld.getFrame().getMouseY());
		return null;
	}
	
	public static SubTileCoordinate determineTilePositionAtPoint(ClientManager client, int x, int y) {
		/*Coordinate c = new Coordinate(0, 0);
		float z = client.getZoom();
		c.setY(WorldRenderer.getYTilePos(y, client, client.getGame().getMap(), z));
		c.setX(WorldRenderer.getXTilePos(x, c.getY(), client, client.getGame().getMap(), z));
		ArrayList<Coordinate> coordsToCheck = adjacent(c, client.getGame().getMap().getWidth(), client.getGame().getMap().getHeight());
		Coordinate f = null;
		int etw = (int) (WorldRenderer.getTileWidth()/2*z);
		int eth = (int) (WorldRenderer.getTileHeight()/2*z);
		boolean offsetted = true;
		for(int i = 0;i < coordsToCheck.size();i ++) {
			Coordinate t = coordsToCheck.get(i);
			int xc = WorldRenderer.getXPos(t.getX(), t.getY(), client, client.getGame().getMap(), z, false);
			int yc = WorldRenderer.getYPos(t.getY(), client, client.getGame().getMap(), z);
			if(yc == -1) continue;
			if(!(xc == -1) && withinHexagonCenter(x, y, xc, yc, (int) (WorldRenderer.getTileWidth()/2*z), (int) (WorldRenderer.getTileHeight()/2*z))) {
				f = t;
				offsetted = false;
			}
			xc = WorldRenderer.getXPos(t.getX(), t.getY(), client, client.getGame().getMap(), z, true);
			if(xc == -1) continue;
			if(withinHexagonCenter(x, y, xc, yc, etw, eth)) {
				f = t;
				offsetted = true;
			}
		}
		if(f == null) {
			return null;
		} else {
			int bx = WorldRenderer.getXPos(f.getX(), f.getY(), client, client.getGame().getMap(), z, offsetted);
			int by = WorldRenderer.getYPos(f.getY(), client, client.getGame().getMap(), z);
			etw /= 3;
			eth /= 3;
			SubTileCoordinate temp;
			if((temp = testSubTile(f, x, y, bx, by, z, etw, eth, SubTile.CENTER)) != null) {
				return temp;
			}
			if((temp = testSubTile(f, x, y, bx, by, z, etw, eth, SubTile.EAST)) != null) {
				return temp;
			}
			if((temp = testSubTile(f, x, y, bx, by, z, etw, eth, SubTile.FLOATING_NORTH)) != null) {
				return temp;
			}
			if((temp = testSubTile(f, x, y, bx, by, z, etw, eth, SubTile.FLOATING_SOUTH)) != null) {
				return temp;
			}
			if((temp = testSubTile(f, x, y, bx, by, z, etw, eth, SubTile.NORTH_WEST)) != null) {
				return temp;
			}
			if((temp = testSubTile(f, x, y, bx, by, z, etw, eth, SubTile.NORTH_EAST)) != null) {
				return temp;
			}
			if((temp = testSubTile(f, x, y, bx, by, z, etw, eth, SubTile.SOUTH_EAST)) != null) {
				return temp;
			}
			if((temp = testSubTile(f, x, y, bx, by, z, etw, eth, SubTile.SOUTH_WEST)) != null) {
				return temp;
			}
			if((temp = testSubTile(f, x, y, bx, by, z, etw, eth, SubTile.WEST)) != null) {
				return temp;
			}
			if(withinHexagonCenter(x, y, (int) (bx+HexagonData.HEXAGON_SUBTILE_FNW_X*z), (int) (by+HexagonData.HEXAGON_SUBTILE_FNW_Y*z), etw, eth)) {
				SubTileCoordinate nc = new SubTileCoordinate(f, SubTile.FLOATING_SOUTH);
				if(f.getY() % 2 == 1) {
					nc.setY(nc.getY()-1);
				} else {
					nc.setY(nc.getY()-1);
					nc.setX(nc.getX()-1);
				}
				return nc;
			}
			if(withinHexagonCenter(x, y, (int) (bx+HexagonData.HEXAGON_SUBTILE_FSW_X*z), (int) (by+HexagonData.HEXAGON_SUBTILE_FSW_Y*z), etw, eth)) {
				SubTileCoordinate nc = new SubTileCoordinate(f, SubTile.FLOATING_NORTH);
				if(f.getY() % 2 == 1) {
					nc.setY(nc.getY()+1);
				} else {
					nc.setY(nc.getY()+1);
					nc.setX(nc.getX()-1);
				}
				return nc;
			}
			if(withinHexagonCenter(x, y, (int) (bx+HexagonData.HEXAGON_SUBTILE_FNE_X*z), (int) (by+HexagonData.HEXAGON_SUBTILE_FNE_Y*z), etw, eth)) {
				SubTileCoordinate nc = new SubTileCoordinate(f, SubTile.FLOATING_SOUTH);
				if(f.getY() % 2 == 0) {
					nc.setY(nc.getY()-1);
				} else {
					nc.setY(nc.getY()-1);
					nc.setX(nc.getX()+1);
				}
				return nc;
			}
			if(withinHexagonCenter(x, y, (int) (bx+HexagonData.HEXAGON_SUBTILE_FSE_X*z), (int) (by+HexagonData.HEXAGON_SUBTILE_FSE_Y*z), etw, eth)) {
				SubTileCoordinate nc = new SubTileCoordinate(f, SubTile.FLOATING_NORTH);
				if(f.getY() % 2 == 0) {
					nc.setY(nc.getY()+1);
				} else {
					nc.setY(nc.getY()+1);
					nc.setX(nc.getX()+1);
				}
				return nc;
			}
		}*/
		return null;
	}
	
	@SuppressWarnings("unused")
	private static SubTileCoordinate testSubTile(Coordinate c, int mx, int my, int bx, int by, float z, int etw, int eth, SubTile subtile) {
		if(withinHexagonCenter(mx, my, (int) (bx+HexagonData.getXBySubTile(subtile)*z), (int) (by+HexagonData.getYBySubTile(subtile)*z), etw, eth)) {
			return new SubTileCoordinate(c, subtile);
		}
		return null;
	}
	
	public static ArrayList<SubTileCoordinate> adjacentSubTile(ArrayList<SubTileCoordinate> adj, int w, int h){
		ArrayList<SubTileCoordinate> r = new ArrayList<SubTileCoordinate>();
		for(int i = 0;i < adj.size();i ++) {//Add all given values
			r = addIfNewSubTile(r, adj.get(i), w, h);
		}
		for(int i = 0;i < adj.size();i ++) {//For all values, get adjacents
			SubTileCoordinate tc = adj.get(i);
			boolean odd = tc.getY() % 2 == 1;
			switch(tc.getSubTile()) {
			case CENTER:
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.NORTH_WEST), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.NORTH_EAST), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.EAST), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.SOUTH_EAST), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.SOUTH_WEST), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.WEST), w, h);
				break;
			case EAST:
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.CENTER), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.NORTH_EAST), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.SOUTH_EAST), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() + 1, tc.getY(), SubTile.WEST), w, h);
				if(odd) {
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() + 1, tc.getY() - 1, SubTile.FLOATING_SOUTH), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() + 1, tc.getY() + 1, SubTile.FLOATING_NORTH), w, h);
				} else {
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX(), tc.getY() - 1, SubTile.FLOATING_SOUTH), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX(), tc.getY() + 1, SubTile.FLOATING_NORTH), w, h);
				}
				break;
			case FLOATING_NORTH:
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.NORTH_WEST), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.NORTH_EAST), w, h);
				if(odd) {
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() + 1, tc.getY() - 1, SubTile.SOUTH_WEST), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() + 1, tc.getY() - 1, SubTile.WEST), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX(), tc.getY() - 1, SubTile.SOUTH_EAST), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX(), tc.getY() - 1, SubTile.EAST), w, h);
				} else {
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX(), tc.getY() - 1, SubTile.SOUTH_WEST), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX(), tc.getY() - 1, SubTile.WEST), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() - 1, tc.getY() - 1, SubTile.SOUTH_EAST), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() - 1, tc.getY() - 1, SubTile.EAST), w, h);
				}
				break;
			case FLOATING_SOUTH:
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.SOUTH_WEST), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.SOUTH_EAST), w, h);
				if(odd) {
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() + 1, tc.getY() + 1, SubTile.NORTH_WEST), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() + 1, tc.getY() + 1, SubTile.WEST), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX(), tc.getY() + 1, SubTile.NORTH_EAST), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX(), tc.getY() + 1, SubTile.EAST), w, h);
				} else {
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX(), tc.getY() + 1, SubTile.NORTH_WEST), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX(), tc.getY() + 1, SubTile.WEST), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() - 1, tc.getY() + 1, SubTile.NORTH_EAST), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() - 1, tc.getY() + 1, SubTile.EAST), w, h);
				}
				break;
			case NORTH_WEST:
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.CENTER), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.WEST), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.NORTH_EAST), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.FLOATING_NORTH), w, h);
				if(odd) {
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX(), tc.getY() - 1, SubTile.SOUTH_EAST), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX(), tc.getY() - 1, SubTile.FLOATING_SOUTH), w, h);
				} else {
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() - 1, tc.getY() - 1, SubTile.SOUTH_EAST), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() - 1, tc.getY() - 1, SubTile.FLOATING_SOUTH), w, h);
				}
				break;
			case NORTH_EAST:
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.CENTER), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.EAST), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.NORTH_WEST), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.FLOATING_NORTH), w, h);
				if(odd) {
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() + 1, tc.getY() - 1, SubTile.SOUTH_WEST), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() + 1, tc.getY() - 1, SubTile.FLOATING_SOUTH), w, h);
				} else {
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX(), tc.getY() - 1, SubTile.SOUTH_WEST), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX(), tc.getY() - 1, SubTile.FLOATING_SOUTH), w, h);
				}
				break;
			case SOUTH_EAST:
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.CENTER), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.EAST), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.SOUTH_WEST), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.FLOATING_SOUTH), w, h);
				if(odd) {
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() + 1, tc.getY() + 1, SubTile.NORTH_WEST), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() + 1, tc.getY() + 1, SubTile.FLOATING_NORTH), w, h);
				} else {
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX(), tc.getY() + 1, SubTile.NORTH_WEST), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX(), tc.getY() + 1, SubTile.FLOATING_NORTH), w, h);
				}
				break;
			case SOUTH_WEST:
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.CENTER), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.WEST), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.SOUTH_EAST), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.FLOATING_SOUTH), w, h);
				if(odd) {
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX(), tc.getY() + 1, SubTile.NORTH_EAST), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX(), tc.getY() + 1, SubTile.FLOATING_NORTH), w, h);
				} else {
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() - 1, tc.getY() + 1, SubTile.NORTH_EAST), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() - 1, tc.getY() + 1, SubTile.FLOATING_NORTH), w, h);
				}
				break;
			case WEST:
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.CENTER), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.NORTH_WEST), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc, SubTile.SOUTH_WEST), w, h);
				r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() - 1, tc.getY(), SubTile.EAST), w, h);
				if(odd) {
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX(), tc.getY() - 1, SubTile.FLOATING_SOUTH), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX(), tc.getY() + 1, SubTile.FLOATING_NORTH), w, h);
				} else {
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() - 1, tc.getY() - 1, SubTile.FLOATING_SOUTH), w, h);
					r = addIfNewSubTile(r, new SubTileCoordinate(tc.getX() - 1, tc.getY() + 1, SubTile.FLOATING_NORTH), w, h);
				}
				break;
			}
		}
		return r;
	}
	
	public static SubTileCoordinate byDirection(SubTileCoordinate base, Direction dir) {
		boolean odd = base.getY() % 2 == 1;
		switch(base.getSubTile()) {
		case CENTER:
			if(dir == Direction.NORTH_WEST) return new SubTileCoordinate(base, SubTile.NORTH_WEST);
			if(dir == Direction.NORTH_EAST) return new SubTileCoordinate(base, SubTile.NORTH_EAST);
			if(dir == Direction.EAST) return new SubTileCoordinate(base, SubTile.EAST);
			if(dir == Direction.SOUTH_EAST) return new SubTileCoordinate(base, SubTile.SOUTH_EAST);
			if(dir == Direction.SOUTH_WEST) return new SubTileCoordinate(base, SubTile.SOUTH_WEST);
			if(dir == Direction.WEST) return new SubTileCoordinate(base, SubTile.WEST);
			break;
		case EAST:
			if(dir == Direction.WEST) return new SubTileCoordinate(base, SubTile.CENTER);
			if(dir == Direction.NORTH_WEST) return new SubTileCoordinate(base, SubTile.NORTH_EAST);
			if(dir == Direction.SOUTH_WEST) return new SubTileCoordinate(base, SubTile.SOUTH_EAST);
			if(dir == Direction.EAST) return new SubTileCoordinate(base.getX() + 1, base.getY(), SubTile.WEST);
			if(odd) {
				if(dir == Direction.NORTH_EAST) return new SubTileCoordinate(base.getX() + 1, base.getY() - 1, SubTile.FLOATING_SOUTH);
				if(dir == Direction.SOUTH_EAST) return new SubTileCoordinate(base.getX() + 1, base.getY() + 1, SubTile.FLOATING_NORTH);
			} else {
				if(dir == Direction.NORTH_EAST) return new SubTileCoordinate(base.getX(), base.getY() - 1, SubTile.FLOATING_SOUTH);
				if(dir == Direction.SOUTH_EAST) return new SubTileCoordinate(base.getX(), base.getY() + 1, SubTile.FLOATING_NORTH);
			}
			break;
		case FLOATING_NORTH:
			if(dir == Direction.SOUTH_WEST) return new SubTileCoordinate(base, SubTile.NORTH_WEST);
			if(dir == Direction.SOUTH_EAST) return new SubTileCoordinate(base, SubTile.NORTH_EAST);
			if(odd) {
				if(dir == Direction.EAST) return new SubTileCoordinate(base.getX(), base.getY() - 1, SubTile.SOUTH_WEST);
				if(dir == Direction.NORTH_EAST) return new SubTileCoordinate(base.getX(), base.getY() - 1, SubTile.WEST);
				if(dir == Direction.WEST) return new SubTileCoordinate(base.getX() + 1, base.getY() - 1, SubTile.SOUTH_EAST);
				if(dir == Direction.NORTH_WEST) return new SubTileCoordinate(base.getX() + 1, base.getY() - 1, SubTile.EAST);
			} else {
				if(dir == Direction.EAST) return new SubTileCoordinate(base.getX() - 1, base.getY() - 1, SubTile.SOUTH_WEST);
				if(dir == Direction.NORTH_EAST) return new SubTileCoordinate(base.getX() - 1, base.getY() - 1, SubTile.WEST);
				if(dir == Direction.WEST) return new SubTileCoordinate(base.getX(), base.getY() - 1, SubTile.SOUTH_EAST);
				if(dir == Direction.NORTH_WEST) return new SubTileCoordinate(base.getX(), base.getY() - 1, SubTile.EAST);
			}
			break;
		case FLOATING_SOUTH:
			if(dir == Direction.NORTH_WEST) return new SubTileCoordinate(base, SubTile.SOUTH_WEST);
			if(dir == Direction.NORTH_EAST) return new SubTileCoordinate(base, SubTile.SOUTH_EAST);
			if(odd) {
				if(dir == Direction.NORTH_WEST) return new SubTileCoordinate(base.getX(), base.getY() + 1, SubTile.NORTH_WEST);
				if(dir == Direction.NORTH_WEST) return new SubTileCoordinate(base.getX(), base.getY() + 1, SubTile.WEST);
				if(dir == Direction.NORTH_WEST) return new SubTileCoordinate(base.getX() + 1, base.getY() + 1, SubTile.NORTH_EAST);
				if(dir == Direction.NORTH_WEST) return new SubTileCoordinate(base.getX() + 1, base.getY() + 1, SubTile.EAST);
			} else {
				if(dir == Direction.NORTH_WEST) return new SubTileCoordinate(base.getX() - 1, base.getY() + 1, SubTile.NORTH_WEST);
				if(dir == Direction.NORTH_WEST) return new SubTileCoordinate(base.getX() - 1, base.getY() + 1, SubTile.WEST);
				if(dir == Direction.NORTH_WEST) return new SubTileCoordinate(base.getX(), base.getY() + 1, SubTile.NORTH_EAST);
				if(dir == Direction.NORTH_WEST) return new SubTileCoordinate(base.getX(), base.getY() + 1, SubTile.EAST);
			}
			break;
		case NORTH_WEST:
			if(dir == Direction.SOUTH_EAST) return new SubTileCoordinate(base, SubTile.CENTER);
			if(dir == Direction.SOUTH_WEST) return new SubTileCoordinate(base, SubTile.WEST);
			if(dir == Direction.EAST) return new SubTileCoordinate(base, SubTile.NORTH_EAST);
			if(dir == Direction.NORTH_EAST) return new SubTileCoordinate(base, SubTile.FLOATING_NORTH);
			if(odd) {
				if(dir == Direction.NORTH_WEST) return new SubTileCoordinate(base.getX(), base.getY() - 1, SubTile.SOUTH_EAST);
				if(dir == Direction.WEST) return new SubTileCoordinate(base.getX(), base.getY() - 1, SubTile.FLOATING_SOUTH);
			} else {
				if(dir == Direction.NORTH_WEST) return new SubTileCoordinate(base.getX() - 1, base.getY() - 1, SubTile.SOUTH_EAST);
				if(dir == Direction.WEST) return new SubTileCoordinate(base.getX() - 1, base.getY() - 1, SubTile.FLOATING_SOUTH);
			}
			break;
		case NORTH_EAST:
			if(dir == Direction.SOUTH_WEST) return new SubTileCoordinate(base, SubTile.CENTER);
			if(dir == Direction.SOUTH_EAST) return new SubTileCoordinate(base, SubTile.EAST);
			if(dir == Direction.WEST) return new SubTileCoordinate(base, SubTile.NORTH_WEST);
			if(dir == Direction.NORTH_WEST) return new SubTileCoordinate(base, SubTile.FLOATING_NORTH);
			if(odd) {
				if(dir == Direction.NORTH_EAST) return new SubTileCoordinate(base.getX() + 1, base.getY() - 1, SubTile.SOUTH_WEST);
				if(dir == Direction.EAST) return new SubTileCoordinate(base.getX() + 1, base.getY() - 1, SubTile.FLOATING_SOUTH);
			} else {
				if(dir == Direction.NORTH_EAST) return new SubTileCoordinate(base.getX(), base.getY() - 1, SubTile.SOUTH_EAST);
				if(dir == Direction.EAST) return new SubTileCoordinate(base.getX(), base.getY() - 1, SubTile.FLOATING_SOUTH);
			}
			break;
		case SOUTH_EAST:
			if(dir == Direction.NORTH_WEST) return new SubTileCoordinate(base, SubTile.CENTER);
			if(dir == Direction.NORTH_EAST) return new SubTileCoordinate(base, SubTile.EAST);
			if(dir == Direction.WEST) return new SubTileCoordinate(base, SubTile.SOUTH_WEST);
			if(dir == Direction.SOUTH_WEST) return new SubTileCoordinate(base, SubTile.FLOATING_SOUTH);
			if(odd) {
				if(dir == Direction.SOUTH_EAST) return new SubTileCoordinate(base.getX() + 1, base.getY() + 1, SubTile.NORTH_WEST);
				if(dir == Direction.EAST) return new SubTileCoordinate(base.getX() + 1, base.getY() + 1, SubTile.FLOATING_NORTH);
			} else {
				if(dir == Direction.SOUTH_EAST) return new SubTileCoordinate(base.getX(), base.getY() + 1, SubTile.NORTH_WEST);
				if(dir == Direction.EAST) return new SubTileCoordinate(base.getX(), base.getY() + 1, SubTile.FLOATING_NORTH);
			}
			break;
		case SOUTH_WEST:
			if(dir == Direction.NORTH_EAST) return new SubTileCoordinate(base, SubTile.CENTER);
			if(dir == Direction.NORTH_WEST) return new SubTileCoordinate(base, SubTile.WEST);
			if(dir == Direction.EAST) return new SubTileCoordinate(base, SubTile.SOUTH_EAST);
			if(dir == Direction.SOUTH_EAST) return new SubTileCoordinate(base, SubTile.FLOATING_SOUTH);
			if(odd) {
				if(dir == Direction.SOUTH_WEST) return new SubTileCoordinate(base.getX(), base.getY() + 1, SubTile.NORTH_EAST);
				if(dir == Direction.WEST) return new SubTileCoordinate(base.getX(), base.getY() + 1, SubTile.FLOATING_NORTH);
			} else {
				if(dir == Direction.SOUTH_WEST) return new SubTileCoordinate(base.getX() - 1, base.getY() + 1, SubTile.NORTH_EAST);
				if(dir == Direction.WEST) return new SubTileCoordinate(base.getX() - 1, base.getY() + 1, SubTile.FLOATING_NORTH);
			}
			break;
		case WEST:
			if(dir == Direction.EAST) return new SubTileCoordinate(base, SubTile.CENTER);
			if(dir == Direction.NORTH_EAST) return new SubTileCoordinate(base, SubTile.NORTH_WEST);
			if(dir == Direction.SOUTH_EAST) return new SubTileCoordinate(base, SubTile.SOUTH_WEST);
			if(dir == Direction.WEST) return new SubTileCoordinate(base.getX() - 1, base.getY(), SubTile.EAST);
			if(odd) {
				if(dir == Direction.NORTH_WEST) return new SubTileCoordinate(base.getX(), base.getY() - 1, SubTile.FLOATING_SOUTH);
				if(dir == Direction.SOUTH_WEST) return new SubTileCoordinate(base.getX(), base.getY() + 1, SubTile.FLOATING_NORTH);
			} else {
				if(dir == Direction.NORTH_WEST) return new SubTileCoordinate(base.getX() - 1, base.getY() - 1, SubTile.FLOATING_SOUTH);
				if(dir == Direction.SOUTH_WEST) return new SubTileCoordinate(base.getX() - 1, base.getY() + 1, SubTile.FLOATING_NORTH);
			}
			break;
		}
		return base;
	}
	
	public static ArrayList<SubTileCoordinate> adjacentSubTile(SubTileCoordinate adj, int w, int h){
		ArrayList<SubTileCoordinate> a = new ArrayList<>();
		a.add(adj);
		return adjacentSubTile(a, w, h);
	}
	
	public static ArrayList<SubTileCoordinate> addIfNewSubTile(ArrayList<SubTileCoordinate> a, SubTileCoordinate c, int w, int h){
		for(int i = 0;i < a.size();i ++) {
			if(a.get(i).sameAs(c)) return a;
		}
		if(c.getX() < 0) {
			c.setX(c.getX()+w);
		}
		if(c.getX() >= w) {
			c.setX(c.getX()-w);
		}
		if(c.getY() < 0) {
			return a;
		}
		if(c.getY() >= h) {
			return a;
		}
		a.add(c);
		return a;
	}

	public static double distance(SubTileCoordinate a, SubTileCoordinate b, int w) {
		double x1, x2, y1, y2;
		x1 = a.getX() * HexagonData.HEXAGON_RADIUS;
		x2 = b.getX() * HexagonData.HEXAGON_RADIUS;
		y1 = a.getY() * HexagonData.HEXAGON_RADIUS * 0.75;
		y2 = b.getY() * HexagonData.HEXAGON_RADIUS * 0.75;
		
		if(a.getY() % 2 == 1) {
			x1 += HexagonData.HEXAGON_RADIUS/2;
		}
		
		if(b.getY() % 2 == 1) {
			x2 += HexagonData.HEXAGON_RADIUS/2;
		}
		
		x1 += HexagonData.getXBySubTile(a.getSubTile());
		x2 += HexagonData.getXBySubTile(b.getSubTile());
		y1 += HexagonData.getYBySubTile(a.getSubTile());
		y2 += HexagonData.getYBySubTile(b.getSubTile());
		
		x1 /= (double) HexagonData.HEXAGON_RADIUS;
		x2 /= (double) HexagonData.HEXAGON_RADIUS;
		y1 /= (double) HexagonData.HEXAGON_RADIUS;
		y2 /= (double) HexagonData.HEXAGON_RADIUS;
		
		double scalingFactor = 15;
		
		x1 *= scalingFactor;
		x2 *= scalingFactor;
		y1 *= scalingFactor;
		y2 *= scalingFactor;
		
		double result1 = Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
		
		x1 -= w*5;
		
		double result2 = Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
		
		x1 += w*10;
		
		double result3 = Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));

		double result = Math.min(result1, Math.min(result2, result3));
		
		result = Math.round(result*10)/10.0;
		
		return result;
	}
}
