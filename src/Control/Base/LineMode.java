package Control.Base;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;

import Model.Base.BasicObject;
import Model.Base.Line;
import Model.Base.Port;

/**
 * Base of all Line Controls
 * 
 * @author Jimmy801
 * 
 * @see {@link Mode}
 */
public class LineMode extends Mode {
	/**
	 * Press point
	 */
	protected Point press;
	/**
	 * Release point
	 */
	protected Point release;
	/**
	 * Press port
	 */
	protected Port pressP;
	/**
	 * Release port
	 */
	protected Port releaseP;
	/**
	 * Parent component of press port
	 */
	protected BasicObject pressObj;
	/**
	 * Parent component of release port
	 */
	protected BasicObject releaseObj;
	/**
	 * Showing when press on port and dragging mode line
	 */
	protected Line draggedLine;

	public LineMode() {
		press = new Point();
		release = new Point();
		initPtr();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		press.setLocation(e.getPoint());

		// set press port and its parent
		for (Port p : canvas.ports) {
			if (p.contains(press)) {
				pressObj = p.getParent();
				pressP = p;
				break;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		release.setLocation(e.getPoint());

		// make ports visible to origin state
		for (Port p : canvas.ports) {
			if (p.contains(release) && releaseP == null) {
				releaseObj = p.getParent();
				releaseP = p;
			}
			p.setVisible(p.getParent().isSelected());
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		changePortStyle(e.getPoint());
		canvas.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		changePortStyle(e.getPoint(), true);
		canvas.repaint();
	}

	/**
	 * Initialize press and release ports and their parents
	 */
	public void initPtr() {
		pressObj = null;
		pressP = null;
		releaseObj = null;
		releaseP = null;
		draggedLine = null;
	}

	/**
	 * Change port style by point
	 * 
	 * @param pt - the point which will effect port color
	 */
	public void changePortStyle(Point pt) {
		changePortStyle(pt, false);
	}

	/**
	 * Change port style by point
	 * 
	 * @param pt   - the point which will effect port color
	 * @param drag - if trigger by mouse drag event, all port must be visible
	 */
	public void changePortStyle(Point pt, boolean drag) {
		for (Port p : canvas.ports) {
			if (p.contains(pt)) {
				p.setColor(p.getParent() != pressObj ? Color.GREEN : Color.RED);
				p.setVisible(true);
			} else {
				p.setColor(Color.BLACK);
				p.setVisible(p.getParent().isSelected() || (drag && pressObj != null));
			}
		}
	}

	/**
	 * Check if line connected
	 * 
	 * @return line is connected or not
	 */
	protected boolean connectLine() {
		return pressObj != releaseObj && pressObj != null && releaseObj != null;
	}
}
