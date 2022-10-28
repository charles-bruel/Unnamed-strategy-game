package chazzvader.game.sided.client.swing;

import javax.swing.JFrame;

import chazzvader.game.input.InputListener;
import chazzvader.game.localization.LocalizationManager;
import chazzvader.game.other.Console;
import chazzvader.game.sided.client.render.ImageManager;

/**
 * Frame to render everything on
 * @author csbru
 * @since 1.0
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GameFrame extends JFrame {

	private InputListener l;
	
	/**
	 * The panel in the frame
	 */
	GamePanel panel;
	
	/**
	 * Creates a frame and calls the {@link #init() init()} method.
	 */
	public GameFrame() {
		init();
	}

	/**
	 * Does stuff to set up the frame, such as setting the title and window size.
	 */
	private void init() {
		this.setTitle(LocalizationManager.get("LOC_TITLE"));
		this.setUndecorated(true);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setIconImage(ImageManager.WINDOW_ICON);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
				
		panel = new GamePanel(this);
		this.add(panel);
		l = new InputListener(panel.getClient());
		
		this.addMouseListener(l);
		this.addMouseWheelListener(l);
		this.addKeyListener(l);
		this.addMouseMotionListener(l);				
		this.setVisible(true);
		
		Console.print("(Client) Window started!", 0);
		
	}

	/**
	 * Gets the x position of the mouse on this frame.
	 * @return The x position of the mouse
	 */
	public int getMouseX() {
		return this.getMousePosition() == null ? 0 : this.getMousePosition().x;
	}
	
	/**
	 * Gets the y position of the mouse on this frame.
	 * @return The y position of the mouse
	 */
	public int getMouseY() {
		return this.getMousePosition() == null ? 0 : this.getMousePosition().y;
	}

	/**
	 * Gets the panel on this frame
	 * @return The panel on the frame
	 */
	public GamePanel getPanel() {
		return panel;
	}
	
}
