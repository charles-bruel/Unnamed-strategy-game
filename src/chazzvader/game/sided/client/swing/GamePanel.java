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
@Deprecated
@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
	/**
	 * The parent frame of this panel
	 */
	GameFrame frame;
	
	/**
	 * The client manager.
	 */
	ClientManager client;
	
	/**
	 * Creates the frame
	 * @param frame
	 */
	public GamePanel(GameFrame frame) {
		this.frame = frame;
		
		//client = new ClientManager(frame, this);
		//client.start();
		
		
	}

	/**
	 * Gets the parent frame for this panel.
	 * @return The frame
	 */
	public GameFrame getFrame() {
		return frame;
	}
	
	private long lastFrameEndTime = System.nanoTime();
	private long frameDelta = 0;
	
	/**
	 * Renders the frame (rendering tasks delegated to RenderManager and WorldRenderer).
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//client.getRender().render(g);
		long deltaEnd = System.nanoTime();
		frameDelta = deltaEnd - lastFrameEndTime;
		lastFrameEndTime = deltaEnd;
	}

	/**
	 * Gets the client associated with this object.
	 * @return The client
	 */
	public ClientManager getClient() {
		return client;
	}

	public int getFPS() {
		if(frameDelta == 0) return -1;
		return (int) (1000000000/frameDelta);
	}
	
}