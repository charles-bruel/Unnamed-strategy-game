package chazzvader.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import chazzvader.game.Main;
import chazzvader.game.other.Debug;
import chazzvader.game.sided.client.ClientManager;
import chazzvader.game.sided.client.render.ui.UIManager.UIMode;
import chazzvader.game.sided.client.render.ui.UIManager.UIState;

/**
 * Input listener. Deals with all input. Implements MouseListener, KeyListener, MouseWheelListener, MouseMotionListener. <br>
 * (Note - no methods in this class have javadoc.)
 * @author csbru
 * @since 1.0
 * @version 1.0
 */
public class InputListener implements MouseListener, KeyListener, MouseWheelListener, MouseMotionListener {

	ClientManager client;
	
	/**
	 * Variables used to represent the position when a mouse drag begins. Used for panning around in the game.
	 */
	private int ox, oy;
	private float z;
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		//Zoom
		if(client.isGame() && client.getUi().getUIState() == UIState.GAME) {
			if (client.getUi().isMouseOnGame()) {
				float nz = z;
				nz += e.getWheelRotation();
				if(nz<1) nz = 1;
				if(nz>15) nz = 15;
				z = nz;
				client.setZoom(1/z);
			}
		} else {//Scrolling
			client.getUi().setScroll(client.getUi().getScroll()+e.getWheelRotation()*10);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		//Panning stuff
		ox = Main.getFrame().getMouseX();
		oy = Main.getFrame().getMouseY();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		{//More Panning Stuff
			float xm = ox-e.getX();
			float ym = oy-e.getY();
			xm*=1/client.getZoom();
			ym*=1/client.getZoom();
			float x = client.getX()+xm;
			float y = client.getY()+ym;
			
			if(client.getGame() != null && client.getGame().getMap() != null) {
				int[] b = client.getGame().getMap().getBounds();
				if(x<b[0] && !client.getGame().getMap().isWrap()) x = b[0];
				if(x>b[1] && !client.getGame().getMap().isWrap()) x = b[1];
				if(y<b[2]) y = b[2];
				if(y>b[3]) y = b[3];
				
				if(x<b[0] && client.getGame().getMap().isWrap()) {
					x = b[1];
				}
				if(x>b[1] && client.getGame().getMap().isWrap()) {
					x = b[0];
				}
			}
			
			client.setX(Math.round(x));
			client.setY(Math.round(y));
	
			ox = e.getX();
			oy = e.getY();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		//UI event
		Main.getFrame().getPanel().getClient().getUi().triggerClick(e);
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
		//Debug console
		if(e.getKeyChar() == '`') Debug.input();
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if(client.isGame() && client.getUi().getUIMode() != UIMode.NONE) {
				client.getUi().setUIMode(UIMode.NONE);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	public ClientManager getClient() {
		return client;
	}

	public InputListener(ClientManager client) {
		this.client = client;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

}
