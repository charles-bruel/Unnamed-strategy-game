package chazzvader.game.content;

import java.awt.image.BufferedImage;

import chazzvader.game.sided.client.render.GraphicsWrapper;
import chazzvader.game.sided.client.render.ImageManager;

public class Yields {

	public static final int BASE_SIZE = 40;
	public static final int BASE_SPACING = BASE_SIZE + 5;
	public static final int SMALL_SIZE = BASE_SIZE / 2;
	public static final int SMALL_SPACING = BASE_SPACING / 2;
	public static final int MAX_ARRAY_SIZE = 3;

	public static final BufferedImage FOOD_IMAGE = ImageManager.FOOD;
	public static final BufferedImage PROD_IMAGE = ImageManager.PRODUCTION;
	public static final BufferedImage GOLD_IMAGE = ImageManager.GOLD;
	public static final BufferedImage CULTURE_IMAGE = ImageManager.CULTURE;
	public static final BufferedImage FAITH_IMAGE = ImageManager.FAITH;
	public static final BufferedImage SCI_IMAGE = ImageManager.SCIENCE;

	public static final BufferedImage FOOD_IMAGE_SIMPLE = ImageManager.FOOD_SIMPLE;
	public static final BufferedImage PROD_IMAGE_SIMPLE = ImageManager.PRODUCTION_SIMPLE;
	public static final BufferedImage GOLD_IMAGE_SIMPLE = ImageManager.GOLD_SIMPLE;
	public static final BufferedImage CULTURE_IMAGE_SIMPLE = ImageManager.CULTURE_SIMPLE;
	public static final BufferedImage FAITH_IMAGE_SIMPLE = ImageManager.FAITH_SIMPLE;
	public static final BufferedImage SCI_IMAGE_SIMPLE = ImageManager.SCIENCE_SIMPLE;

	private int food, production, science, culture, faith, gold;

	private YieldType[][] toDraw;
	private BufferedImage[] complex;
	private BufferedImage[] simple;
	private int pages;
	
	public BufferedImage[] getComplex() {
		return complex;
	}

	public BufferedImage[] getSimple() {
		return simple;
	}

	private int arrayLength;

	@Override
	public String toString() {
		return super.toString() + " " + food + " " + production + " " + science + " " + culture + " " + faith + " "
				+ gold;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		updateImageAndPositions();
		this.food = food;
	}

	public int getProduction() {
		return production;
	}

	public void setProduction(int production) {
		updateImageAndPositions();
		this.production = production;
	}

	public int getScience() {
		return science;
	}

	public void setScience(int science) {
		updateImageAndPositions();
		this.science = science;
	}

	public int getCulture() {
		return culture;
	}

	public void setCulture(int culture) {
		updateImageAndPositions();
		this.culture = culture;
	}

	public int getFaith() {
		return faith;
	}

	public void setFaith(int faith) {
		updateImageAndPositions();
		this.faith = faith;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		updateImageAndPositions();
		this.gold = gold;
	}

	public int getTotalYields() {
		return food + production + science + culture + faith + gold;
	}

	public Yields(int food, int production, int science, int culture, int faith, int gold) {
		this.food = food;
		this.production = production;
		this.science = science;
		this.culture = culture;
		this.faith = faith;
		this.gold = gold;
		updateImageAndPositions();
	}

	public Yields() {
		this(0, 0, 0, 0, 0, 0);
	}

	public void renderYieldsSmall(GraphicsWrapper g, int fx, int fy, float z) {
		renderYields(g, fx, fy, z, SMALL_SIZE, SMALL_SPACING, false);
	}

	public void renderYieldsLarge(GraphicsWrapper g, int fx, int fy, float z) {
		renderYields(g, fx, fy, z, BASE_SIZE, BASE_SPACING, true);
	}

	public void updateImageAndPositions() {
		arrayLength = (int) Math.ceil(Math.sqrt(getTotalYields()));
		int totalYields = getTotalYields();
		if (totalYields == 0) {
			arrayLength = 0;
			pages = 0;
			return;
		}
		if (arrayLength > MAX_ARRAY_SIZE)
			arrayLength = MAX_ARRAY_SIZE;
		YieldType[] items = new YieldType[totalYields];
		pages = (int) Math.ceil(totalYields / 9f);
		int on = 0;
		for (int i = 0; i < food; i++) {
			items[on] = YieldType.FOOD;
			on++;
		}
		for (int i = 0; i < production; i++) {
			items[on] = YieldType.PRODUCTION;
			on++;
		}
		for (int i = 0; i < gold; i++) {
			items[on] = YieldType.GOLD;
			on++;
		}
		for (int i = 0; i < science; i++) {
			items[on] = YieldType.SCIENCE;
			on++;
		}
		for (int i = 0; i < faith; i++) {
			items[on] = YieldType.FAITH;
			on++;
		}
		for (int i = 0; i < culture; i++) {
			items[on] = YieldType.CULTURE;
			on++;
		}

		int arraySizeFull = arrayLength * arrayLength;
		toDraw = new YieldType[pages][arraySizeFull];
		int k = 0;
		for (int i = 0; i < pages; i++) {
			for (int j = 0; j < 9 && j < totalYields - (i * arraySizeFull); j++, k++) {
				toDraw[i][j] = items[k];
			}
		}
		
		drawToImages();
	}

	private void drawToImages() {
		complex = new BufferedImage[pages];
		simple = new BufferedImage[pages];
		for(int i = 0;i < pages;i ++) {
			int baseImageSize = (arrayLength - 1) * BASE_SPACING + BASE_SIZE;
			int simpleImageSize = (arrayLength - 1) * SMALL_SPACING + SMALL_SIZE;
			BufferedImage complex = new BufferedImage(baseImageSize, baseImageSize, BufferedImage.TYPE_INT_ARGB);
			BufferedImage simple = new BufferedImage(simpleImageSize, simpleImageSize, BufferedImage.TYPE_INT_ARGB);
			GraphicsWrapper complexGraphics = new GraphicsWrapper(complex.getGraphics());
			GraphicsWrapper simpleGraphics = new GraphicsWrapper(simple.getGraphics());
			for(int j = 0;j < toDraw[0].length;j ++) {
				YieldType yieldToDraw = toDraw[i][j];
				
				if(yieldToDraw == null) {
					break;
				}
				
				BufferedImage imageToDrawSimple;
				BufferedImage imageToDrawComplex;

				imageToDrawComplex = yieldToDraw.getComplex();
				imageToDrawSimple = yieldToDraw.getSimple();
				
				complexGraphics.renderImage(imageToDrawComplex, (j % arrayLength) * BASE_SPACING, (j / arrayLength) * BASE_SPACING, BASE_SIZE, BASE_SIZE);
				simpleGraphics.renderImage(imageToDrawSimple, (j % arrayLength) * SMALL_SPACING, (j / arrayLength) * SMALL_SPACING, SMALL_SIZE, SMALL_SIZE);
			}
			complexGraphics.raw().dispose();
			simpleGraphics.raw().dispose();
			this.complex[i] = complex;
			this.simple[i] = simple;
		}
	}

	private void renderYields(GraphicsWrapper g, int fx, int fy, float z, int size, int spacing, boolean complex) {
		if (z < 0.3f || toDraw == null)
			return;
		/*int isz = (int) (size * z);
		int isp = (int) (spacing * z);
		int bx = fx - (isp * (arrayLength - 1)) / 2;
		int by = fy - (isp * (arrayLength - 1)) / 2;
		for (int i = 0; i < toDraw[0].length; i++) {
			YieldType yieldToDraw = toDraw[pageID][i];
			if (yieldToDraw == null) {
				break;
			}
			BufferedImage imageToDraw;
			if (complex) {
				imageToDraw = yieldToDraw.getComplex();
			} else {
				imageToDraw = yieldToDraw.getSimple();
			}
			g.renderImageCenter(imageToDraw, bx + (i % arrayLength) * isp, by + (i / arrayLength) * isp, isz, isz);
		}*/
		long time = System.currentTimeMillis() / 1000;
		int pageID = (int) (time % pages);
		BufferedImage image = complex ? this.complex[pageID] : this.simple[pageID];
		int finalSize = (int) (image.getWidth() * z);
		g.renderImageCenter(image, fx, fy, finalSize, finalSize);
	}

	public Yields add(int food, int production, int science, int culture, int faith, int gold) {
		this.food += food;
		this.production += production;
		this.science += science;
		this.culture += culture;
		this.faith += faith;
		this.gold += gold;
		updateImageAndPositions();
		return this;
	}

	public static Yields combine(Yields yieldsA, Yields yieldsB, Yields yieldsC) {
		float food, prod, gold, sci, faith, culture;

		food = yieldsA.getFood() + yieldsB.getFood() + yieldsC.getFood();
		prod = yieldsA.getProduction() + yieldsB.getProduction() + yieldsC.getProduction();
		gold = yieldsA.getGold() + yieldsB.getGold() + yieldsC.getGold();
		sci = yieldsA.getScience() + yieldsB.getScience() + yieldsC.getScience();
		faith = yieldsA.getFaith() + yieldsB.getFaith() + yieldsC.getFaith();
		culture = yieldsA.getCulture() + yieldsB.getCulture() + yieldsC.getCulture();

		food /= 3;
		prod /= 3;
		gold /= 3;
		sci /= 3;
		faith /= 3;
		culture /= 3;

		int ffood, fprod, fgold, fsci, ffaith, fculture;

		ffood = Math.round(food);
		fprod = Math.round(prod);
		fgold = Math.round(gold);
		fsci = Math.round(sci);
		ffaith = Math.round(faith);
		fculture = Math.round(culture);

		return new Yields(ffood, fprod, fsci, fculture, ffaith, fgold);
	}

	public static enum YieldType {
		FOOD(Yields.FOOD_IMAGE, Yields.FOOD_IMAGE_SIMPLE), PRODUCTION(Yields.PROD_IMAGE, Yields.PROD_IMAGE_SIMPLE),
		GOLD(Yields.GOLD_IMAGE, Yields.GOLD_IMAGE_SIMPLE), SCIENCE(Yields.SCI_IMAGE, Yields.SCI_IMAGE_SIMPLE),
		FAITH(Yields.FAITH_IMAGE, Yields.FAITH_IMAGE_SIMPLE),
		CULTURE(Yields.CULTURE_IMAGE, Yields.CULTURE_IMAGE_SIMPLE);

		private YieldType(BufferedImage complex, BufferedImage simple) {
			this.complex = complex;
			this.simple = simple;
		}

		private BufferedImage simple;
		private BufferedImage complex;

		public BufferedImage getSimple() {
			return simple;
		}

		public BufferedImage getComplex() {
			return complex;
		}
	}

}
