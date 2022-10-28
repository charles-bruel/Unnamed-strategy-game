package chazzvader.game.sided.both.game.map;

import chazzvader.game.content.ContentBaseGame;
import chazzvader.game.content.Tile;
import chazzvader.game.other.Coordinate;
import chazzvader.game.sided.both.game.Player;

public class Map {
	
	private boolean wrap;
	
	private Tile[][] tiles;
	
	public Tile[][] getTiles(){
		return tiles;
	}
	
	public Map setTile(int x, int y, Tile t) {
		tiles[x][y] = t;
		refreshRValues();
		return this;
	}
	
	public Map() {
		this(45,25);
	}
	
	public Tile tileAt(Coordinate c) {
		return tiles[c.getX()][c.getY()];
	}
	
	public Map(int w, int h) {
		this(fill(w,h));
	}
	
	private static Tile[][] fill(int w, int h) {
		Tile[][] t = new Tile[w][h];
		for(int i = 0;i < w;i ++) {
			for(int j = 0;j < h;j ++) {
				t[i][j] = ContentBaseGame.TILE_OCEAN.get();
			}
		}
		return t;
	}

	public Map(Tile[][] tiles) {
		this.tiles = tiles;
		this.wrap = true;
		refreshRValues();
		finalize(null);
	}
	
	/**
	 * Returns x and y bounds of the map.
	 * @return A int[] representing the min and max of tiles in the map. {x-min, x-max, y-min, y-max}
	 */
	public int[] getBounds() {
		/*int[] a = new int[] {0, 0, 0, 0};
		
		int w = WorldRenderer.getTileWidth()*tiles.length+(WorldRenderer.getTileWidth()/2);
		int h = (int) (WorldRenderer.getTileHeight()*tiles[0].length*0.75f);

		a[0] = 0-w/4;
		a[1] = w/4;
		a[2] = 0-h/4;
		a[3] = h/2;
		
		return a;*/
		return null;
	}

	private void refreshRValues() {
		//rwidth = WorldRenderer.getTileWidth()*tiles.length/2;	
		//rheight = WorldRenderer.getTileHeight()*tiles[0].length/2;
	}
	
	/**
	 * @return If the map wraps horizontally
	 */
	public boolean isWrap() {
		return wrap;
	}

	public int getWidth() {
		return tiles.length;
	}
	
	public int getHeight() {
		return tiles[0].length;
	}
	
	private int rwidth = 0;
	
	public int getRWidth() {
		return rwidth;
	}
	
	private int rheight = 0;
	
	public int getRHeight() {
		return rheight;
	}

	/**
	 * @param wrap the wrap to set
	 */
	public void setWrap(boolean wrap) {
		this.wrap = wrap;
	}

	/**
	 * @param tiles the tiles to set
	 */
	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

	public void finalize(Player p) {
		for(int i = 0;i < tiles.length;i ++) {
			Tile[] ts = tiles[i];
			for(int j = 0;j < ts.length;j ++) {
				if(ts[j] == null) {
					continue;
				}
				ts[j].setCoordinate(new Coordinate(i, j));
				ts[j].setMap(this);
				ts[j].updateYields(p);
			}
		}
	}

}
