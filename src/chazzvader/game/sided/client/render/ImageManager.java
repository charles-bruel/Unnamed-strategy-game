package chazzvader.game.sided.client.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import chazzvader.game.other.Console;

/**
 * Class containing all images the program uses
 * @author csbru
 * @since 1.0
 * @version 1.0
 */
public final class ImageManager {

	
	//Icons
	public static final BufferedImage WINDOW_ICON = read("assets/textures/icons/window_icon.png");

	public static final BufferedImage FOOD = read("assets/textures/icons/food.png");
	public static final BufferedImage GOLD = read("assets/textures/icons/gold.png");
	public static final BufferedImage CULTURE = read("assets/textures/icons/culture.png");
	public static final BufferedImage FAITH = read("assets/textures/icons/faith.png");
	public static final BufferedImage PRODUCTION = read("assets/textures/icons/production.png");
	public static final BufferedImage SCIENCE = read("assets/textures/icons/science.png");
	
	public static final BufferedImage FOOD_SIMPLE = read("assets/textures/icons/food_simple.png");
	public static final BufferedImage GOLD_SIMPLE = read("assets/textures/icons/gold_simple.png");
	public static final BufferedImage CULTURE_SIMPLE = read("assets/textures/icons/culture_simple.png");
	public static final BufferedImage FAITH_SIMPLE = read("assets/textures/icons/faith_simple.png");
	public static final BufferedImage PRODUCTION_SIMPLE = read("assets/textures/icons/production_simple.png");
	public static final BufferedImage SCIENCE_SIMPLE = read("assets/textures/icons/science_simple.png");

	//Tiles
	public static final Image TILE_OVERLAY = read("assets/textures/tiles/overlay.png");
	public static final Image SELECTED_OVERLAY = read("assets/textures/tiles/hover_icon.png");
	public static final Image FILLED_HEX = read("assets/textures/tiles/filled_hex.png");
	public static final Image SELECTED_HEX = read("assets/textures/tiles/selected.png");

	public static final BufferedImage COAST = read("assets/textures/tiles/coast.png");
	public static final BufferedImage OCEAN = read("assets/textures/tiles/ocean.png");
	
	public static final BufferedImage TUNDRA_HILLS = read("assets/textures/tiles/tundra_hills.png");
	public static final BufferedImage SNOW_HILLS = read("assets/textures/tiles/snow_hills.png");
	public static final BufferedImage GRASSLAND_HILLS = read("assets/textures/tiles/grassland_hills.png");
	public static final BufferedImage PLAINS_HILLS = read("assets/textures/tiles/plains_hills.png");
	public static final BufferedImage DESERT_HILLS = read("assets/textures/tiles/desert_hills.png");
	
	public static final BufferedImage TUNDRA_MOUNTAIN = read("assets/textures/tiles/tundra_mountain.png");
	public static final BufferedImage SNOW_MOUNTAIN = read("assets/textures/tiles/snow_mountain.png");
	public static final BufferedImage GRASSLAND_MOUNTAIN = read("assets/textures/tiles/grassland_mountain.png");
	public static final BufferedImage PLAINS_MOUNTAIN = read("assets/textures/tiles/plains_mountain.png");
	public static final BufferedImage DESERT_MOUNTAIN = read("assets/textures/tiles/desert_mountain.png");
	
	public static final BufferedImage TUNDRA = read("assets/textures/tiles/tundra.png");
	public static final BufferedImage SNOW = read("assets/textures/tiles/snow.png");
	public static final BufferedImage GRASSLAND = read("assets/textures/tiles/grassland.png");
	public static final BufferedImage PLAINS = read("assets/textures/tiles/plains.png");
	public static final BufferedImage DESERT = read("assets/textures/tiles/desert.png");

	public static final BufferedImage WOODS = read("assets/textures/tiles/woods.png");
	public static final BufferedImage PINE_WOODS = read("assets/textures/tiles/pine_woods.png");
	public static final BufferedImage RAINFOREST = read("assets/textures/tiles/rainforest.png");
	public static final BufferedImage OASIS = read("assets/textures/tiles/oasis.png");

	public static final BufferedImage RESOURCE_DIAMOND = read("assets/textures/tiles/resources/diamond.png");
	public static final BufferedImage RESOURCE_URANIUM = read("assets/textures/tiles/resources/uranium.png");
	public static final BufferedImage RESOURCE_IRON = read("assets/textures/tiles/resources/iron.png");
	public static final BufferedImage RESOURCE_DEER = read("assets/textures/tiles/resources/deer.png");
	public static final BufferedImage RESOURCE_OIL = read("assets/textures/tiles/resources/oil.png");

	public static final BufferedImage MILITARY_UNIT_BACKGROUND = readForceCompat("assets/textures/units/military/mil_unit_background.png");
	public static final BufferedImage MILITARY_UNIT_AXEMAN = read("assets/textures/units/military/axeman.png");
	
	public static final BufferedImage[] RIVER_IMAGES = new BufferedImage[6];
	
	/**
	 * A list contain all images possible for the background. This is obtained by searching assets/textures/background for .png files
	 */
	public static final ArrayList<BufferedImage> BACKGROUND_IMAGES;

	static {
		BACKGROUND_IMAGES = new ArrayList<>();
		File[] fa = new File("assets/textures/background").listFiles();
		if(fa != null){
			for(int i = 0;i < fa.length;i ++){
				if(fa[i].getName().matches(".*.jpg")){
					Console.print("(Client) Adding "+fa[i].getAbsolutePath()+" to the list of background images.", -1);
					BACKGROUND_IMAGES.add(read(fa[i].getAbsolutePath()));
				}
			}
		}
		RIVER_IMAGES[0] = read("assets/textures/tiles/river/river_nw.png");
		RIVER_IMAGES[1] = read("assets/textures/tiles/river/river_ne.png");
		RIVER_IMAGES[2] = read("assets/textures/tiles/river/river_e.png");
		RIVER_IMAGES[3] = read("assets/textures/tiles/river/river_se.png");
		RIVER_IMAGES[4] = read("assets/textures/tiles/river/river_sw.png");
		RIVER_IMAGES[5] = read("assets/textures/tiles/river/river_w.png");
		
	}
	
	public static BufferedImage read(String filepath) {
		try {
			return ImageIO.read(new File(filepath));
		} catch (IOException e) {
			Console.error(e, false);
		}
		return null;
	}
	
	public static BufferedImage readForceCompat(String filepath) {
		try {
			BufferedImage bi1 = ImageIO.read(new File(filepath));
			BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(), BufferedImage.TYPE_BYTE_INDEXED, createColorModel(new Color(0xFFFFFF)));
			Graphics2D g2d = bi2.createGraphics();
			g2d.drawImage(bi1, 0, 0, null);
			g2d.dispose();
			return bi2;
		} catch (IOException e) {
			Console.error(e, false);
		}
		return null;
	}
	
	public static IndexColorModel createColorModel(Color c) {
		byte tr = (byte) c.getRed();
		byte tg = (byte) c.getGreen();
		byte tb = (byte) c.getBlue();
		
		int cms = 4;
		
		byte[] r = new byte[cms];
	    byte[] g = new byte[cms];
	    byte[] b = new byte[cms];
	    byte[] a = new byte[cms];
	    for (int j = 0; j < r.length; j++) {
	    	if(j == 0) {
	    		r[j] = 0;
	    		g[j] = 0;
	    		b[j] = 0;
	    		a[j] = 0;
	    	} else {
	    		r[j] = tr;
	        	g[j] = tg;
	        	b[j] = tb;
	        	a[j] = (byte) 255;
	    	}
	    }
	    IndexColorModel cm = new IndexColorModel(4, cms, r, g, b, a);
	    return cm;
	}
	
	public static BufferedImage getColoredImage(BufferedImage i, Color c) {
		IndexColorModel cm = createColorModel(c);
	    WritableRaster wr = i.getRaster();
	    BufferedImage bi = new BufferedImage(cm, wr, false, new Hashtable<>());
		return bi;
	}
	
	/**
	 * Converts a given Image into a BufferedImage
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img){
	    if (img instanceof BufferedImage) {
	        return (BufferedImage) img;
	    }
	    
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();
	    return bimage;
	}
	
	/**
	 * Converts a given Image into a BufferedImage
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img, IndexColorModel cm){
		long bt = System.nanoTime();
	    if (img instanceof BufferedImage) {
	        return (BufferedImage) img;
	    }

	    
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_BYTE_INDEXED, cm);

	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();
	    bt = System.nanoTime()-bt;
	    return bimage;
	}
	
	/**
	 * Static method so static fields get initialized
	 */
	public static void init(){
		Console.print("(Render) Loaded Images", 0);
	}
	
}
