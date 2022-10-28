package chazzvader.game.sided.client.render;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import chazzvader.game.Main;
import chazzvader.game.localization.LocalizationManager;
import chazzvader.game.other.Debug;
import chazzvader.game.sided.client.ClientManager;

/**
 * Overarching renderer class
 * @author csbru
 * @since 1.0
 * @version 1.0
 */
public final class RenderManager {
	
	/**
	 * Copy of the client object
	 */
	ClientManager client;
	
	/**
	 * Renders the entire game.
	 * This does: <br>
	 * 1. Calls {@link #renderGame(Graphics) renderGame()} if the state of the client manager is set to ingame. <br>
	 * 2. Calls {@link #renderMenu(Graphics) renderMenu()} if the state of the client manager is not set to ingame. <br>
	 * 3. Displays the framerate.
	 * @param g The graphics object, all rendering is done from this object
	 */
	public void render(Graphics g){
		GraphicsWrapper gw = new GraphicsWrapper(g);
		if(client.isGame()){
			renderGame(gw);
		}else{
			renderMenu(gw);
		}
		client.getUi().render(gw);
		if(Debug.showFPS()) {
			gw.setColor(255);//Blue
			gw.setFont(new Font("Courier", Font.BOLD, 30));
			gw.renderString(client.getPanel().getFPS()+"", 0, 30);
		}
	}

	/**
	 * Render the game when in the menu (being in a menu while the game is being rendered in the background doesn't count)
	 * @param g The graphics object, all rendering is done from this object
	 */
	private void renderMenu(GraphicsWrapper g) {
		Image image = ImageManager.BACKGROUND_IMAGES.get(0);
		float ratio = image.getWidth(null)/image.getHeight(null);
		int imgH = client.getPanel().getHeight();
		int imgW = (int) Math.round(ratio*imgH);
		g.renderImage(image, (int) client.getTime() % imgW, 0, imgW, imgH);
		g.renderImage(image, (int) client.getTime() % imgW - imgW, 0, imgW, imgH);
		
	}

	/**
	 * Render the game while inside a game, including any in-game menus
	 * @param g
	 */
	private void renderGame(GraphicsWrapper g) {
		if(client.getGame() != null) {
			WorldRenderer.render(g, client.getGame(), client.getZoom(), client);
		}else {
			g.setFont(new Font("Courier", Font.BOLD, 100));
			Dimension ss = Main.getTk().getScreenSize();
			g.renderStringCenter(LocalizationManager.get("LOC_LOADING"), (int) ss.getWidth()/2, (int) ss.getHeight()/2);
		}
	}

	/**
	 * Gets the client object stored by this class
	 * @return The client object
	 */
	public ClientManager getClient() {
		return client;
	}

	/**
	 * Constructor for the manager
	 * @param client A client object, to get required info for rendering
	 */
	public RenderManager(ClientManager client) {
		super();
		this.client = client;
	}
	
}
