package Control.Base;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import View.Canvas;
import View.MenuBar;

/**
 * Base of all controls, listen mouse events
 * 
 * @author Jimmy801
 *
 * @see {@link MouseListener}, {@link MouseMotionListener}
 */
public class Mode implements MouseListener, MouseMotionListener {

	protected Canvas canvas = Canvas.getInstance();
	protected MenuBar menuBar = MenuBar.getInstance();

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
