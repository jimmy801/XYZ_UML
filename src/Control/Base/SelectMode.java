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
	private Point press;
	private Point release;
	private Map<BasicObject, Point> selectedObjs;
	private SelectRectangle sr;
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
		boolean inSelect = false;
		for (Entry<BasicObject, Point> sel : selectedObjs.entrySet()) {
			BasicObject com = sel.getKey();
			if (!canvas.objs.contains(com) || !com.isSelected()) {
				break;
			}
			if (com.contains(e.getPoint())) {
				inSelect = true;
			}
			selectedObjs.put(com, new Point(com.getX() - e.getX(), com.getY() - e.getY()));
		}
		if (!inSelect) {
			selectedObjs.clear();
		}
		for (BasicObject obj : canvas.objs) {
			if (obj.contains(press) && !inSelect && !pressOnObj) {
				pressOnObj = true;
				obj.setSelected(true);
				selectedObjs.put(obj, new Point(obj.getX() - e.getX(), obj.getY() - e.getY()));
			} else {
				obj.setSelected(selectedObjs.containsKey(obj));
			}
		}
		canvas.moveToFront();
		if (!pressOnObj && !inSelect) {
			sr.setLocation(e.getPoint());
			canvas.add(sr, 0);
		} else {
			pressOnObj = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		release.setLocation(e.getPoint());
		if (!pressOnObj) {
			for (BasicObject obj : canvas.objs) {
				if (sr.contains(obj.getLocation())) {
					obj.setSelected(true);
					selectedObjs.put(obj, new Point());
				}
			}
			canvas.remove(sr);
		}
		menuBar.setMenuItemEnable();
		pressOnObj = false;
		sr.setSize(0, 0);
		canvas.moveToFront();
		canvas.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		if (pressOnObj) {
			for (Entry<BasicObject, Point> sel : selectedObjs.entrySet()) {
				BasicObject com = sel.getKey();
				Point offset = sel.getValue();
				com.setLocation(e.getX() + offset.x, e.getY() + offset.y);
			}
		} else {
			sr.setSize(Math.abs(e.getPoint().x - press.x), Math.abs(e.getPoint().y - press.y));
			sr.setLocation(Math.min(e.getPoint().x, press.x), Math.min(e.getPoint().y, press.y));
		}
		canvas.repaint();
	}

}
