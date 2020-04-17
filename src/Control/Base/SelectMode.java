package Control.Base;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import Model.Base.BasicObject;
import Model.Base.SelectRectangle;

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
	private Map<BasicObject, Point> selectedObjs;
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
		for (Entry<BasicObject, Point> sel : selectedObjs.entrySet()) {
			BasicObject com = sel.getKey();
			if (!canvas.objs.contains(com) || !com.isSelected()) { // components are grouped or not selected than break
				break;
			}
			if (com.contains(e.getPoint())) { // if one of last selected object contain press point
				inSelect = true; // DO NOT break because we have to set all relative position
			}
			selectedObjs.put(com, new Point(com.getX() - e.getX(), com.getY() - e.getY())); // set all relative position
		}
		if (!inSelect) { // if not press on last selected object than clear
			selectedObjs.clear();
		}

		// set selected and save relative position
		for (BasicObject obj : canvas.objs) {
			if (obj.contains(press) && !inSelect && !pressOnObj) {
				pressOnObj = true;
				obj.setSelected(true);
				selectedObjs.put(obj, new Point(obj.getX() - e.getX(), obj.getY() - e.getY()));
			} else {
				obj.setSelected(selectedObjs.containsKey(obj));
			}
		}

		// after setting selected, we need to reset depth of components
		canvas.resetDepth();

		if (!pressOnObj && !inSelect) { // draw selected rectangle
			sr.setLocation(e.getPoint());
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
			for (BasicObject obj : canvas.objs) {
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
		if (pressOnObj) { // move components
			for (Entry<BasicObject, Point> sel : selectedObjs.entrySet()) {
				BasicObject com = sel.getKey();
				Point offset = sel.getValue();
				com.setLocation(e.getX() + offset.x, e.getY() + offset.y);
			}
		} else { // selected by selected rectangle
			sr.setSize(Math.abs(e.getPoint().x - press.x), Math.abs(e.getPoint().y - press.y));
			sr.setLocation(Math.min(e.getPoint().x, press.x), Math.min(e.getPoint().y, press.y));
		}
		canvas.repaint();
	}

}
