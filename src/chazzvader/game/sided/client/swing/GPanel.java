package chazzvader.game.sided.client.swing;

import java.awt.Graphics;

import javax.swing.JPanel;

import chazzvader.game.sided.client.ClientManager;

/**
 * A panel that takes all of the space on the frame, used for rendering etc. Also loosely serves as an overall manager.
 * @author csbru
 * @since 1.0
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GPanel extends JPanel {
	
	/**
	 * The parent frame of this panel
	 */
	GFrame frame;
	
	/**
	 * The client manager.
	 */
	ClientManager client;
	
	/**
	 * Creates the frame
	 * @param frame
	 */
	public GPanel(GFrame frame) {
		this.frame = frame;
		
		client = new ClientManager(frame, this);
		client.start();
		
		
	}

	/**
	 * Gets the parent frame for this panel.
	 * @return The frame
	 */
	public GFrame getFrame() {
		return frame;
	}
	
	private long startTime = System.nanoTime();
	private int delta = 0;
	
	/**
	 * Renders the frame (rendering tasks delegated to RenderManager and WorldRenderer).
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		client.getRender().render(g);
		delta = (int) (System.nanoTime()-startTime);
		startTime = System.nanoTime();
	}

	/**
	 * Gets the client associated with this object.
	 * @return The client
	 */
	public ClientManager getClient() {
		return client;
	}

	public int getFPS() {
		if(delta == 0) return -1;
		return 1000000000/delta;
	}
	
}