package chazzvader.game.sided.client.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import chazzvader.game.Main;
import chazzvader.game.other.IAction;

/**
 * Class to provide static methods to render basic shapes, images etc<br>
 * Replaced by GraphicsWrapper
 * @author csbru
 * @since 1.0
 */
@Deprecated
public final class RenderMethods {
	
	/**
	 * Renders a button with functionality. It is rendered as a blue box see {@link #renderBox(Graphics, int, int, int, int) renderBox()}. The height is assumed to be 40.
	 * @param g The graphics object, all rendering is done from this object
	 * @param x The x position of the button
	 * @param y The y position of the button
	 * @param w The width of the button
	 * @param text The text to display on the button
	 * @param click The action to perform when the button is clicked
	 */
	public static void renderButton(Graphics g, int x, int y, int w, String text, IAction click) {
		int mouseX = Main.getFrame().getMouseX(), mouseY = Main.getFrame().getMouseY();
		boolean mouse = false;
		
		if(within(mouseX, mouseY, x, y, x+w, y+40)) {
			mouse = true;
			
			if(Main.getFrame().getPanel().getClient().getUi().click()) {
				click.run();
			}
		}
		
		g.setColor(new Color(8974839));//Light Blue
		if(mouse) g.setColor(new Color(7181462));//Murky Light Blue
		renderBox(g, x, y, x+w, y+40);
		
		g.setColor(new Color(0));//Black
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString(text, x+5, y+30);
	}
	
	/**
	 * Renders a box shape. The rendered box is a octagon with the 4 sides at 45 degree angle as decoration, there length is 5 root 2. 
	 * @param g The graphics object, all rendering is done from this object
	 * @param x1 The 1st x position of the box
	 * @param y1 The 1st y position of the box
	 * @param x2 The 2nd x position of the box
	 * @param y2 The 2nd y position of the box
	 */
	public static void renderBox(Graphics g, int x1, int y1, int x2, int y2) {
		int[] xa = {x1+5, x2-5, x2  , x2  , x2-5, x1+5, x1  , x1  };
		int[] ya = {y1  , y1  , y1+5, y2-5, y2  , y2  , y2-5, y1+5};
		g.fillPolygon(xa, ya, 8);
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
	 * Draws a line with a circle on the end
	 * @param g The graphics object, all rendering is done from this object
	 * @param x1 The 1st x position of the line
	 * @param y1 The 1st y position of the line
	 * @param x2 The 2nd x position of the line
	 * @param y2 The 2nd y position of the line
	 */
	public static void renderLine(Graphics g, int x1, int y1, int x2, int y2) {
		g.drawLine(x1, y1, x2, y2);
		g.fillOval(x1-5, y1-5, 10, 10);
		g.fillOval(x2-5, y2-5, 10, 10);
	}
	
	/**
	 * Draws an image, but the x and y coordinates provided are in the center of the image, not the upper left corner
	 * @param g The graphics object, all rendering is done from this object
	 * @param i The image to draw
	 * @param x The x coordinate of the center of the image
	 * @param y The y coordinate of the center of the image
	 * @param w The width of the image (the image gets stretched/scakled)
	 * @param h The height of the image (the image gets stretched/scakled)
	 */
	public static void renderImageCenter(Graphics g, Image i, int x, int y, int w, int h) {
		g.drawImage(i, x-(w/2), y-(h/2), w, h, null);
	}
	
	
}