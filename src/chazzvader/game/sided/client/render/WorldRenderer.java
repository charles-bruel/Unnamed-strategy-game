package chazzvader.game.sided.client.render;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import chazzvader.game.Main;
import chazzvader.game.content.Yields;
import chazzvader.game.content.tiles.Tile;
import chazzvader.game.content.units.Unit;
import chazzvader.game.input.InputHelper;
import chazzvader.game.other.Debug;
import chazzvader.game.other.HexagonData;
import chazzvader.game.other.SubTileCoordinate;
import chazzvader.game.other.SubTileCoordinate.SubTile;
import chazzvader.game.sided.both.game.Game;
import chazzvader.game.sided.both.game.map.Map;
import chazzvader.game.sided.both.game.pathing.AreaPath;
import chazzvader.game.sided.client.ClientManager;

/**
 * Actually render world stuff like tiles
 * @author csbru
 * @since 1.0
 * @version 1.0
 */
public class WorldRenderer {
	
	private static final int w = ImageManager.COAST.getWidth(null);
	private static final int h = ImageManager.COAST.getHeight(null);
	private static final int tw = w/2;	
	private static final int th = h/2;
	private static int iwidth = tw;
	private static int iheight = th;
	
	private static int xma = 0;
	private static int xmi = 0;
 	private static int yma = 0;
 	private static int ymi = 0;
 	
 	private static AreaPath moveable = null;
 	
 	public static int getTileHeight() {
 		return h;
 	}
 	
 	public static int getTileWidth() {
 		return w;
 	}
 	 	
 	public static int getXPos(int xpos, int ypos, ClientManager client, Map m, float z, boolean offset) {
		int width = m.getTiles().length;
		if(xpos < xmi || xpos > xma) {
			if(!offset) {
				return -1;
			} else {
				if(xpos+width < xmi || xpos+width > xma) {
					return -1;
				}
			}
		}
 		float x;
		x = tw*xpos;
		x -= width*(w/2);
		x -= client.getX();
		x += offset?m.getRWidth():0;
		x *= z;
		x += client.getFrame().getToolkit().getScreenSize().width/2;
		if(ypos % 2 == 1) x += z*0.5*tw;
		int fx = Math.round(x);
		return fx;
 	}
 	
 	public static int getXPosCorner(int xpos, int ypos, ClientManager client, Map m, float z, boolean offset) {
 		float x;
		x = tw*xpos;
		x -= m.getTiles().length*(w/2);
		x -= client.getX();
		x += offset?m.getRWidth():0;
		x *= z;
		x += client.getFrame().getToolkit().getScreenSize().width/2;
		if(ypos % 2 == 1) x += z*0.5*tw;
		x -= tw*z;
		int fx = Math.round(x);
		return fx;
 	}
 	
 	public static int getXPosNoOffset(int x, ClientManager client, Map map, float z, boolean offset) {
		return getXPos(x, 0, client, map, z, offset);
	}
 	
 	public static int getXTilePos(int mouseX, int tileY, ClientManager client, Map m, float z) {
 		int width = m.getTiles().length;
 		int xpos = getXTilePosRaw(mouseX, tileY, client, m, z);
 		while (xpos < 0) {
 			xpos += width;
 		}
 		while (xpos >= width) {
 			xpos -= width;
 		}
 		return xpos;
 	}
 	
 	public static int getXTilePosRaw(int mouseX, int tileY, ClientManager client, Map m, float z) {
 		float x = mouseX;
 		int tw = w/2;
 		int width = m.getTiles().length;
 		if(tileY % 2 == 1) x -= z*0.5*tw;
 		x -= client.getFrame().getToolkit().getScreenSize().width/2;
 		x /= z;
 		x += client.getX();
 		x += width*(w/2);
 		int xpos = (int) (x/tw);
 		
		return xpos;
 	}
 	
 	public static int getYPos(int ypos, ClientManager client, Map m, float z) {
		if(ypos < ymi || ypos > yma) return -1;
		float y;
 		y = th*ypos*0.75f;
		y-=m.getTiles()[0].length;
		y-=client.getY();
		y *= z;
		y += client.getFrame().getToolkit().getScreenSize().height/2;
 		int fy = Math.round(y);
 		return fy;
 	}
 	
 	public static int getYPosCorner(int ypos, ClientManager client, Map m, float z) {
		float y;
 		y = th*ypos*0.75f;
		y-=m.getTiles()[0].length;
		y-=client.getY();
		y *= z;
		y += client.getFrame().getToolkit().getScreenSize().height/2;
		y -= th*z;
 		int fy = Math.round(y);
 		return fy;
 	}
 	
 	public static int getYTilePos(int mouseY, ClientManager client, Map m, float z) {
		int height = m.getTiles()[0].length;
 		int ypos = getYTilePosRaw(mouseY, client, m, z);
 		while (ypos < 0) {
			ypos += height;
 		}
		while (ypos >= height) {
 			ypos -= height;
 		}
 		return ypos;
 	}
 	
 	public static int getYTilePosRaw(int mouseY, ClientManager client, Map m, float z) {
		float y = mouseY;
		int height = m.getTiles()[0].length;
 		int th = h/2;
		y -= client.getFrame().getToolkit().getScreenSize().height/2;
		y /= z;
		y += client.getY();
		y += height;
		y /= th;
		int ypos = (int) (y /= 0.75f);
		return ypos;
 	}
	/**
	 * Overarching rendering method.
	 * This calls the following methods in this order: <br>
	 * 1. {@link #renderTiles(Graphics, Tile[][], float, ClientManager) renderTiles()}
	 * @param g The graphics object, all rendering is done from this object
	 * @param map A tile[][] representing the map
	 * @param z The zoom
	 * @param client The client manager, used to get information
	 */
	public static void render(GraphicsWrapper g, Game game, float z, ClientManager client) {
		Map m = game.getMap();
		xmi = getXTilePosRaw(0, 0, client, m, z)-1;
 		xma = getXTilePosRaw(Main.getTk().getScreenSize().width, 0, client, m, z)+1;
 		ymi = getYTilePosRaw(0, client, m, z)-1;
 		yma = getYTilePosRaw(Main.getTk().getScreenSize().height, client, m, z)+1;
		renderTiles(g, game.getMap(), z, client);
		renderUnits(g, game.getUnits(), z, client, m);
		if(z != 1) return;
		SubTileCoordinate c = InputHelper.determineTilePositionAtMouse(client);
		if(c == null) return;
		int fx = getXPos(c.getX(), c.getY(), client, m, z, false);
		if(fx == -1) {
			fx = getXPos(c.getX(), c.getY(), client, m, z, true);
			if(fx == -1) return;
		}
		int fy = getYPos(c.getY(), client, m, z);
		if(fy == -1) return;
		g.renderImageCenter(ImageManager.SELECTED_OVERLAY, (int) (fx+HexagonData.getXBySubTile(c.getSubTile())*z), (int) (fy+HexagonData.getYBySubTile(c.getSubTile())*z), iwidth/3, iheight/3);//*/
	}
 	
 	/**
	 * Renders tiles with their associated features, buildings and resources.
	 * @param g The graphics object, all rendering is done from this object
	 * @param map A tile[][] representing the map
	 * @param z The zoom
	 * @param client The client manager, used to get information
	 */
 	private static void renderTiles(GraphicsWrapper g, Map map, float z, ClientManager client) {
 		if(map == null) return;
		renderTileSet(g, map, false, 0, z, client);
		renderTileSet(g, map, true, 0, z, client);
 		if(Debug.showXYcoordinate()) {
 			g.setColor(255);//Blue
 			g.setFont(new Font("Courier", Font.BOLD, 30));
 			g.renderString(client.getX()/(w/2)+", "+Math.round(client.getY()/(h/2)/0.75), 0, 100);
 		}
 				
	}
 	
 	private static void renderTileSet(GraphicsWrapper g, Map m, boolean offset, int oy, float z, ClientManager client) {
 		Tile[][] t = m.getTiles();
 		iwidth = (int) (z*(tw+2));
 		iheight = (int) (z*(th+2));
 		for(int i = 0;i < t.length;i ++) {
			Tile[] mr = t[i];
			for(int j = 0;j < mr.length;j ++) {
				int fx = getXPos(i, j, client, m, z, offset);
				if(fx == -1) continue;
				int fy = getYPos(j, client, m, z);
				if(fy == -1) continue;
				
				if(mr[j] == null || mr[j].getTex() == null) continue;
				
				g.renderImageCenter(mr[j].getTex(), fx, fy, iwidth, iheight);//Tile
				
				boolean[] r = mr[j].getRivers();
				for(int k = 0;k < r.length;k ++) {
					if(r[k]) {
						g.renderImageCenter(ImageManager.RIVER_IMAGES[k], fx, fy, (int) (z*(tw+2)), (int) (z*(th+2)));//Rivers
					}
				}
				if(mr[j].getFeature() != null) g.renderImageCenter(mr[j].getFeature().getImage(), fx, fy, (int) (iwidth*0.75), (int) (iheight*0.75));//Feature
				
				if(z > 0.8f) {
					g.renderImageCenter(ImageManager.TILE_OVERLAY, fx, fy, iwidth, iheight);//Tile
				}
				
				if(mr[j].getResource() != null) g.renderImageCenter(mr[j].getResource().getImage(), fx, fy+(int) (100*z), (int) (50*z), (int) (50*z));//Resource			
				
				
				if(z<0.7f) continue;
				if(Debug.showTileNumbers()) {
					g.setFont(new Font("Courier", Font.BOLD, 40));
					g.setColor(0xFF0000);
					g.renderStringCenter(i+", "+j, fx, fy);
				}
			}
		}
 		
 		for(int i = 0;i < t.length;i ++) {
			Tile[] mr = t[i];
			for(int j = 0;j < mr.length;j ++) {
				
				int fx = getXPos(i, j, client, m, z, offset);
				if(fx == -1) continue;
				int fy = getYPos(j, client, m, z);
				if(fy == -1) continue;
				
				if(mr[j] == null) continue;

				if(z >= 0.7f) {
					Yields[] ya = mr[j].getYields();
					for(int k = 0;k < 9;k ++) {
						//if(ya[k] == null) continue;
						SubTile cst = SubTile.fromID(k);
						int x = fx + HexagonData.getXBySubTile(cst);
						int y = fy + HexagonData.getYBySubTile(cst);
						if(ya[k] != null) {
							ya[k].renderYieldsSmall(g, x, y, z);
						}
					}
				}
			}
		}
 		
 	}
 	
 	private static void renderUnits(GraphicsWrapper g, ArrayList<Unit> units, float z, ClientManager client, Map m) {
		renderUnitSet(g, units, z, client, false, m);
		renderUnitSet(g, units, z, client, true, m);
	}

 	public static int getXPos(SubTileCoordinate c, ClientManager client, Map m, float z, boolean offset) {
 		int bx = getXPos(c.getX(), c.getY(), client, m, z, offset);
 		if(bx == -1) {
 			return -1;
 		}
 		return (int) (bx + HexagonData.getXBySubTile(c.getSubTile())*z);	
 	}
 	
 	public static int getYPos(SubTileCoordinate c, ClientManager client, Map m, float z) {
 		int by = getYPos(c.getY(), client, m, z);
 		if(by == -1) {
 			return -1;
 		}
 		return (int) (by + HexagonData.getYBySubTile(c.getSubTile())*z);	
 	}
 	
	private static void renderUnitSet(GraphicsWrapper g, ArrayList<Unit> units, float z, ClientManager client, boolean offset, Map m) {
		if(units == null) return;
		for(int i = 0;i < units.size();i ++) {
			Unit u = units.get(i);
			
			int fx = getXPos(u.getPos(), client, m, z, offset);
			if(fx == -1) continue;
			int fy = getYPos(u.getPos(), client, m, z);
			if(fx == -1) continue;
			
			if(client.getSelected() instanceof Unit) {
				Unit sel = (Unit) client.getSelected();
				if(u.getUID() == sel.getUID()) {
					g.setColor(0xAAAAFF);
					g.circleCentered(fx, fy, (int) (h*z)/9, (int) (h*z)/9);
					if(moveable == null || !moveable.getSTART().sameAs(sel.getPos())) {
						moveable = new AreaPath(sel.getPos(), m, false, sel.getMovement());
					} else {
						ArrayList<SubTileCoordinate> movableTiles = moveable.getMovableTiles();
						for(int k = 0;k < movableTiles.size();k ++) {
							int lfx = getXPos(movableTiles.get(k), client, m, z, offset);
							if(lfx == -1) continue;
							int lfy = getYPos(movableTiles.get(k), client, m, z);
							if(lfx == -1) continue;
							g.renderImageCenter(ImageManager.SELECTED_HEX, lfx, lfy, (int) (w*z*0.17), (int) (h*z*0.17));
						}
					}
				}
			}
			
			u.draw(g, fx, fy, z);
		}
	}
 	
	public static void callUpdate() {
		moveable = null;
	}
	
}
