package Control.Base;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import Model.Base.SelectRectangle;
import Model.Base.Shape;

/**
 * Select Control, control select event
 * 
 * @author Jimmy801
 *
 * @see {@link Mode}
 */
public class SelectMode extends Mode {
	/**
	 * Mouse press point
	 */
	private Point press;
	/**
	 * Mouse release point
	 */
	private Point release;
	/**
	 * Display dragging selected rectangle
	 */
	private SelectRectangle sr;
	/**
	 * Last selected objects(For {@link #sr})
	 */
	private Map<Shape, Point> selectedObjs;
	/**
	 * Check mouse pressed on an object
	 */
	private boolean pressOnObj;

	public SelectMode() {
		press = new Point();
		release = new Point();
		sr = new SelectRectangle(new Point());
		selectedObjs = new HashMap<>();
		pressOnObj = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		press.setLocation(e.getPoint());
		boolean inSelect = false; // check mouse press in one of selected object
		// check last selected object
		for (Entry<Shape, Point> sel : selectedObjs.entrySet()) {
			Shape com = sel.getKey();
			if (!canvas.objs.contains(com) || !com.isSelected()) { // components are grouped or not selected than break
				break;
			}
			if (com.contains(press)) { // if one of last selected object contain press point
				inSelect = true; // DO NOT break because we have to set all relative position
			}
			selectedObjs.put(com, new Point(com.getX() - press.x, com.getY() - press.y)); // set all relative position
		}
		if (!inSelect) { // if not press on last selected object than clear
			selectedObjs.clear();
		}

		// set selected and save relative position
		for (Shape obj : canvas.objs) {
			if (obj.contains(press) && !inSelect && !pressOnObj) {
				pressOnObj = true;
				obj.setSelected(true);
				selectedObjs.put(obj, new Point(obj.getX() - press.x, obj.getY() - press.y));
			} else {
				obj.setSelected(selectedObjs.containsKey(obj));
			}
		}

		// after setting selected, we need to reset depth of components
		canvas.resetDepth();

		if (!pressOnObj && !inSelect) { // draw selected rectangle
			sr.setLocation(press);
			canvas.add(sr, 0);
		} else { // press on object, maybe dragging
			pressOnObj = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		release.setLocation(e.getPoint());
		if (!pressOnObj) { // set state of objects in selected rectangle to "selected"
			for (Shape obj : canvas.objs) {
				if (sr.contains(obj.getLocation())) {
					obj.setSelected(true);
					selectedObjs.put(obj, new Point());
				}
			}
			canvas.remove(sr); // remove selected rectangle from canvas
			sr.setSize(0, 0); // set selected rectangle to zero prevent next selected rectangle size error
		}
		menuBar.setMenuItemEnable(); // selected state changed, so we need to reset menu item enable
		pressOnObj = false;
		canvas.resetDepth();
		canvas.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		Point dragPoint = e.getPoint();
		if (pressOnObj) { // move components
			for (Entry<Shape, Point> sel : selectedObjs.entrySet()) {
				Shape com = sel.getKey();
				Point offset = sel.getValue();
				com.setLocation(dragPoint.x + offset.x, dragPoint.y + offset.y);
			}
		} else { // selected by selected rectangle
			sr.setBounds(Math.min(dragPoint.x, press.x), Math.min(dragPoint.y, press.y),
					Math.abs(dragPoint.x - press.x), Math.abs(dragPoint.y - press.y));
		}
		canvas.repaint();
	}

}
