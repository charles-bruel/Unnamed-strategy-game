package chazzvader.game.content.mapgen;

import java.util.ArrayList;
import java.util.Random;

import chazzvader.game.content.features.Features;
import chazzvader.game.content.manager.ContentBaseGame;
import chazzvader.game.content.resources.Resource;
import chazzvader.game.content.resources.Resources;
import chazzvader.game.content.tiles.Tile;
import chazzvader.game.other.Console;
import chazzvader.game.other.Coordinate;
import chazzvader.game.sided.both.game.map.Map;
import chazzvader.game.sided.client.render.ui.Parameter;

public class MapGenStandard extends MapGenerator {

	private class TileEntry {
		public TileEntry(int id, int t, int w) {
			this.id = id;
			this.t = t;
			this.w = w;
		}

		int id, t, w;
		
	}

	public enum MapLayout {
		ISLANDS,CONTINENTS,PANGEA;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public Map generate(int w, int h, ArrayList<Parameter> parameters) {
		Map m = new Map(w, h);
		m.setWrap(true);
		Random r = new Random();
		
		MapLayout ml = MapLayout.PANGEA;
		
		int[][] om = noise(w, h, 1, seedPointCalculation(ml, w, h), subSeedPointCalculation(ml, w, h), maxDistanceCalculation(ml, w, h), r, true);
		int[][] tm = noise(w, h, 5, seedPointCalculation(35, w, h), 3, r);
		int[][] wm = noise(w, h, 10, seedPointCalculation(35, w, h), 3, r);
		int[][] gm = noise(w, h, 1, seedPointCalculation(6, w, h), 1, r);
		int[][] hm = noise(w, h, 5, seedPointCalculation(35, w, h), 3, r);
		for(int i = 0;i < w;i ++) {
			for(int j = 0;j < h;j ++) {
				m.setTile(i, j, getTile(i, j, om, tm, wm, gm, hm, w, h, r));
			}
		}
		for(int i = 0;i < w;i ++) {
			for(int j = 0;j < h;j ++) {
				if(m.getTiles()[i][j].getInName().equalsIgnoreCase("TileUselessOcean")) {
					if(validateCoast(m.getTiles(), i, j)) {
						m.setTile(i, j, ContentBaseGame.COAST.get(m, new Coordinate(i, j)));
					}
				}
			}
		}
		
		m = generateRivers(m, r, w, h);
		
		for(int i = 0;i < w;i ++) {
			for(int j = 0;j < h;j ++) {
				m.setTile(i, j, addResource(m.getTiles()[i][j], r, 90));
			}
		}
		m.mapWideUpdateYields(null);
		Console.print("(Mapgen) Map Generation Complete!", 0);
		return m;
	}
	
	private int seedPointCalculation(MapLayout mapLayout, int width, int height) {
		switch (mapLayout) {
		case CONTINENTS:
			return seedPointCalculation(3, width, height);
		case ISLANDS:
			return seedPointCalculation(4, width, height);
		case PANGEA:
			return 1;
		default:
			return seedPointCalculation(3, width, height);
		}
	}
	
	private int subSeedPointCalculation(MapLayout mapLayout, int width, int height) {
		switch (mapLayout) {
		case CONTINENTS:
			return 5;
		case ISLANDS:
			return 1;
		case PANGEA:
			return seedPointCalculation(50, width, height);
		default:
			return 5;
		}
	}
	
	private int maxDistanceCalculation(MapLayout mapLayout, int width, int height) {
		switch (mapLayout) {
		case CONTINENTS:
			return 30;
		case ISLANDS:
			return 5;
		case PANGEA:
			return width*2/3;
		default:
			return 30;
		}
	}

	private ArrayList<Coordinate> addIfNew(ArrayList<Coordinate> a, Coordinate c, int w, int h){
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
	
	private ArrayList<Coordinate> addIfNewRiverable(ArrayList<Coordinate> a, Coordinate c, int w, int h, Tile[][] map){
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
		if(!map[c.getX()][c.getY()].isLand() || !map[c.getX()][c.getY()].isPassable() || map[c.getX()][c.getY()].hasRiver()) return a;
		if(map[c.getX()][c.getY()].getFeature() != null && !map[c.getX()][c.getY()].getFeature().canHaveRiver()) return a;
		if(map[c.getX()][c.getY()].getInName().contains("Hill")) return a;
		a.add(c);
		return a;
	}
	
	private Tile addResource(Tile t, Random r, int threshold) {
		if(r.nextInt(100) > threshold) {
			Resource rs = Resources.fromIndex(r.nextInt(Resources.resourceCount())+1);
			t.addResource(rs, null);
		}
		return t;
	}
	
	private ArrayList<Coordinate> adjacent(ArrayList<Coordinate> adj, int w, int h){
		ArrayList<Coordinate> r = new ArrayList<Coordinate>();
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
	
	private ArrayList<Coordinate> adjacent(Coordinate adj, int w, int h){
		ArrayList<Coordinate> a = new ArrayList<>();
		a.add(adj);
		return adjacent(a, w, h);
	}
	
	private ArrayList<Coordinate> adjacentRiverable(ArrayList<Coordinate> adj, int w, int h, Tile[][] map){
		ArrayList<Coordinate> r = new ArrayList<Coordinate>();
		for(int i = 0;i < adj.size();i ++) {//For all values, get adjacents
			Coordinate tc = adj.get(i);
			int m = tc.getY() % 2 == 1 ? 1 : -1;
			r = addIfNewRiverable(r, new Coordinate(tc.getX()-1, tc.getY()), w, h, map);
			r = addIfNewRiverable(r, new Coordinate(tc.getX()+1, tc.getY()), w, h, map);
			r = addIfNewRiverable(r, new Coordinate(tc.getX(), tc.getY()+1), w, h, map);
			r = addIfNewRiverable(r, new Coordinate(tc.getX(), tc.getY()-1), w, h, map);
			r = addIfNewRiverable(r, new Coordinate(tc.getX()+m, tc.getY()+1), w, h, map);
			r = addIfNewRiverable(r, new Coordinate(tc.getX()+m, tc.getY()-1), w, h, map);
		}
		return r;
	}
	
	private ArrayList<Coordinate> adjacentRiverable(Coordinate adj, int w, int h, Tile[][] map){
		ArrayList<Coordinate> a = new ArrayList<>();
		a.add(adj);
		return adjacentRiverable(a, w, h, map);
	}
	
	private float dist(float mt, float y1, float x2, float y2){
		if(mt == x2 && y1 == y2) return 0;
		float dx = x2-mt;
		float dy = y2-y1;
		return (float) Math.sqrt(dx*dx+dy*dy);
	}
	
	private Map generateRivers(Map map, Random r, int w, int h) {
		Tile[][] tiles = map.getTiles();
		for(int i = 0;i < tiles.length;i ++) {
			for(int j = 0;j < tiles[i].length; j++) {
				if(tiles[i][j].getInName().contains("Coast") && r.nextInt(30) == 1) {
					ArrayList<Coordinate> possible = adjacentRiverable(new Coordinate(i, j), w, h, tiles);
					if(possible.size() == 0) {
						break;
					}
					Coordinate chosen = possible.get(r.nextInt(possible.size()));
					tiles = riverSnake(tiles, r, w, h, i, j, chosen.getX(), chosen.getY());
				}
			}
		}
		
		map.setTiles(tiles);
		return map;
	}
	
	private int getDirectionIndexRelative(Coordinate source, Coordinate target, int w, int h) {
		return getDirectionIndexRelative(source.getX(), source.getY(), target.getX(), target.getY(), w, h);
	}
	
	/**
	 * Gets the index in the river array of tiles to connect the tiles with a river. X1 and y1 are the source tile and x2 and y2 are the target tile.
	 */
	private int getDirectionIndexRelative(int x1, int y1, int x2, int y2, int w, int h){
		if(x1 >= w) x1 -= w;
		if(x1 < 0) x1 += w;
		if(x2 >= w) x2 -= w;
		if(x2 < 0) x2 += w;//Change the x values to their effective values.

		int xd = x2-x1;
		int yd = y2-y1;
		
		if(xd == 1 && yd == 0) return 2;
		if(xd == -1 && yd == 0) return 5;
		
		if(y1 % 2 ==1) { //Determine Mode
			//Odd
			if(xd == 0 && yd == -1) {
				return 0;
			}
			if(xd == 1 && yd == -1) {
				return 1;
			}
			if(xd == 0 && yd == 1) {
				return 4;
			}
			if(xd == 1 && yd == 1) {
				return 3;
			}
		} else {
			//Even
			if(xd == -1 && yd == -1) {
				return 0;
			}
			if(xd == 0 && yd == -1) {
				return 1;
			}
			if(xd == -1 && yd == 1) {
				return 4;
			}
			if(xd == 0 && yd == 1) {
				return 3;
			}
		}
		return -1;
		
	}
	
	private Tile getTile(int h, int t, Coordinate c) {
		Tile r = ContentBaseGame.OCEAN.get(null, c);
		
		if(h == 1 && t == 1) r = ContentBaseGame.DESERT.get(null, c);
		if(h == 2 && t == 1) r = ContentBaseGame.DESERT_HILLS.get(null, c);
		if(h == 3 && t == 1) r = ContentBaseGame.DESERT_MOUNTAIN.get(null, c);
		if(h == 1 && t == 2) r = ContentBaseGame.PLAINS.get(null, c);
		if(h == 2 && t == 2) r = ContentBaseGame.PLAINS_HILLS.get(null, c);
		if(h == 3 && t == 2) r = ContentBaseGame.PLAINS_MOUNTAIN.get(null, c);
		if(h == 1 && t == 3) r = ContentBaseGame.GRASSLAND.get(null, c);
		if(h == 2 && t == 3) r = ContentBaseGame.GRASSLAND_HILLS.get(null, c);
		if(h == 3 && t == 3) r = ContentBaseGame.GRASSLAND_MOUNTAIN.get(null, c);
		if(h == 1 && t == 4) r = ContentBaseGame.TUNDRA.get(null, c);
		if(h == 2 && t == 4) r = ContentBaseGame.TUNDRA_HILLS.get(null, c);
		if(h == 3 && t == 4) r = ContentBaseGame.TUNDRA_MOUNTAIN.get(null, c);
		if(h == 1 && t == 5) r = ContentBaseGame.SNOW.get(null, c);
		if(h == 2 && t == 5) r = ContentBaseGame.SNOW_HILLS.get(null, c);
		if(h == 3 && t == 5) r = ContentBaseGame.SNOW_MOUNTAIN.get(null, c);
		
		return r;
	}
	
	private Tile getTile(int x, int y, int[][] om, int[][] tm, int[][] wm, int[][] gm, int[][] hm, int w, int h, Random ra) {
		//Tile
		Tile r = ContentBaseGame.OCEAN.get(null, new Coordinate(x, y));
		float di = h/2-Math.abs(y-h/2);
		float mod = (di/h*2)*15;
		float mt = tm[x][y]+mod-5;
		if(om[x][y] == 1) {
			TileEntry[] ta = new TileEntry[8];
			ta[0] = new TileEntry(1, 20, 1);
			ta[1] = new TileEntry(2, 10, 3);
			ta[2] = new TileEntry(2, 8, 4);
			ta[3] = new TileEntry(3, 5, 5);
			ta[4] = new TileEntry(3, 5, 10);
			ta[5] = new TileEntry(4, 1, 1);
			ta[6] = new TileEntry(5, 0, 1);
			ta[7] = new TileEntry(1, 13, 1);
			int ft = 0;
			float ht = 200;
			for(int i = 0;i < ta.length;i ++) {
				float d = dist(mt, wm[x][y], ta[i].t, ta[i].w);
				if(d < ht) {
					ht = d;
					ft = ta[i].id;
				}
			}
			
			int height = hm[x][y];
			
			height += ra.nextInt(5);
			
			int fm = 1;
			if(height > 5) fm = 2;
			if(height > 7) fm = 3;
			r = getTile(fm, ft, new Coordinate(x, y));
		}
		//Feature
		if(ra.nextInt(10) == 1) {
			r.addFeature(Features.oasis(), null);
		}
		if(gm[x][y] == 1 && wm[x][y] > 2) {
			if(tm[x][y] * wm[x][y] > 30) {
				r.addFeature(Features.rainForest(), null);
			} else {
				r.addFeature(Features.woods(), null);
				r.addFeature(Features.pineWoods(), null);
			}
		}
		return r;
	}
	
	private int seedPointCalculation(float seedPointModifer, int width, int height) {
		return (int) Math.ceil(width*height/5000.0f*seedPointModifer);
	}
	
	private int[][] noise(int width, int height, int maxValue, int seedPoints, int subSeedPoints, Random random) {
		return noise(width, height, maxValue, seedPoints, subSeedPoints, 30, random, false);
	}
	
	private int[][] noise(int width, int height, int maxValue, int seedPoints, int subSeedPoints, int maxDistance, Random random, boolean isMask) {
		float[][] ret = new float[width][height];
		//int seedPoints = (int) Math.ceil(width*height/5000.0f*seedPointModifer);
		
		if(!isMask) {
			for(int i = 0;i < ret.length;i ++) {
				for(int j = 0;j < ret[i].length;j ++) {
					ret[i][j] = 0.5f;
				}
			}
		}
		
		for(int largeID = 0;largeID < seedPoints;largeID ++) {
			Coordinate currentCoordinate = new Coordinate(random.nextInt(width), random.nextInt(height));
			for(int supPointID = 0;supPointID < subSeedPoints;supPointID ++) {
				Coordinate seedCordinate = new Coordinate(currentCoordinate.getX()+maxDistance/2-random.nextInt(maxDistance), currentCoordinate.getY()+maxDistance/2-random.nextInt(maxDistance));
				ArrayList<Coordinate> coordinatesInMass = new ArrayList<Coordinate>();
				coordinatesInMass.add(seedCordinate);
				for(int iterationID = 0;iterationID < 2000;iterationID ++) {
					Coordinate randomCoordinate = coordinatesInMass.get(random.nextInt(coordinatesInMass.size()));
					ArrayList<Coordinate> randomCoordinateAdjacents = adjacent(randomCoordinate, width, height);
					if(randomCoordinateAdjacents.size() > 1) {
						int target = random.nextInt(randomCoordinateAdjacents.size()-1);
						coordinatesInMass = addIfNew(coordinatesInMass, randomCoordinateAdjacents.get(target), width, height);
						randomCoordinateAdjacents.remove(target);
					}
				}
				float ts;
				if(isMask) {
					ts = 1;
				} else {
					ts = random.nextFloat();
				}
				for(int j = 0;j < coordinatesInMass.size();j ++) {
					Coordinate currentCoordinate2 = coordinatesInMass.get(j);
					if(currentCoordinate2.getX() >= width || currentCoordinate2.getX() < 0 || currentCoordinate2.getY() >= height || currentCoordinate2.getY() < 0) {} else {
						ret[currentCoordinate2.getX()][currentCoordinate2.getY()] = ts;
					}
				}
			}
		}
		
		int[][] rm = new int[width][height];
		for(int i = 0;i < width;i ++) {
			for(int j = 0;j < height;j ++) {
				rm[i][j] = Math.round(ret[i][j]*maxValue);
			}
		}
		return rm;
	}
	
	private Tile[][] riverSnake(Tile[][] tiles, Random r, int w, int h, int bx, int by, int x, int y) {
		tiles[x][y].setRiver(true, getDirectionIndexRelative(x, y, bx, by, w, h));
		int initialDir = getDirectionIndexRelative(new Coordinate(x, y), new Coordinate(bx, by), w, h);
		boolean alive = true;
		Coordinate prev = new Coordinate(x, y);
		while(alive) {
			//Obtain next point
			ArrayList<Coordinate> possible = adjacentRiverable(prev, w, h, tiles);
			
			for(int i = 0;i < possible.size();i ++) {
				int dir = getDirectionIndexRelative(prev, possible.get(i), w, h);
				int a1 = dir-1;
				int a2 = dir+1;
				if(a1 < 0) {
					a1 += 6;
				}
				if(a2 > 5) {
					a2 -= 6;
				}
				if(a1 == initialDir || a2 == initialDir) {
					possible.remove(i);
					i--;
				}
			}
			
			if(r.nextInt(55) == 5) {//River branch
				if(possible.size() > 2) {
					Coordinate newTile = possible.get(r.nextInt(possible.size()));
					tiles[prev.getX()][prev.getY()].setRiver(true, getDirectionIndexRelative(prev, newTile, w, h));
					tiles = riverSnake(tiles, r, w, h, prev.getX(), prev.getY(), newTile.getX(), newTile.getY());
					possible = adjacentRiverable(prev, w, h, tiles);
				}
			}
			if(possible.size() == 0) {
				break;
			}
			Coordinate next = possible.get(r.nextInt(possible.size()));

			
			//Do river stuff™
			tiles[prev.getX()][prev.getY()].setRiver(true, getDirectionIndexRelative(prev, next, w, h));
			tiles[next.getX()][next.getY()].setRiver(true, getDirectionIndexRelative(next, prev, w, h));
			
			//Reset
			if(r.nextInt(20) == 5) alive = false;
			initialDir = getDirectionIndexRelative(next, prev, w, h);
			prev = next;
		}
		return tiles;
	}

	private boolean validateCoast(Tile[][] tiles, int i, int j) {
		ArrayList<Coordinate> adj = adjacent(new Coordinate(i, j), tiles.length, tiles[0].length);
		boolean coast = false;
		
		for(int k = 0;k < adj.size();k ++) {
			Coordinate a = adj.get(k);
			if(tiles[a.getX()][a.getY()].isLand()) {
				coast = true;
				break;
			}
		}
		
		return coast;
	}
	
}
