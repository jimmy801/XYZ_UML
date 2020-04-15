package Control.Base;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JComponent;

import Model.Base.BasicObject;
import Model.Base.Line;
import Model.Base.Port;
import Model.Base.SelectRectangle;
import Model.Objects.Group;

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
		for(Entry<BasicObject, Point> sel: selectedObjs.entrySet()) {
			BasicObject com = sel.getKey();
			if(!canvas.objs.contains(com) || !com.isSelected()) 
				break;
			if(com.contains(e.getPoint()))
				inSelect = true;
			selectedObjs.put(com, new Point(com.getX() - e.getX(), com.getY() - e.getY()));
		}
		if(!inSelect) selectedObjs.clear();
		for(BasicObject obj: canvas.objs) {
			if(obj.contains(press) && !inSelect && !pressOnObj) {
				pressOnObj = true;
				obj.setSelected(true);
				selectedObjs.put(obj, new Point(obj.getX() - e.getX(), obj.getY() - e.getY()));
			}
			else obj.setSelected(selectedObjs.containsKey(obj));
		}
		moveToFront();
		if(!pressOnObj && !inSelect) {
			sr.setLocation(e.getPoint());
			canvas.add(sr, 0);
		}
		else pressOnObj = true;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		release.setLocation(e.getPoint());
		if(!pressOnObj) {
			for(BasicObject obj: canvas.objs) {
				if(sr.contains(obj.getLocation())) {
					obj.setSelected(true);
					selectedObjs.put(obj, new Point());
				}
			}
			canvas.remove(sr);
		}
		menuBar.setMenuItemEnable();
		pressOnObj = false;
		sr.dim.setSize(0, 0);
		canvas.repaint();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		if(pressOnObj) {
			for(Entry<BasicObject, Point>sel: selectedObjs.entrySet()) {
				BasicObject com = sel.getKey();
				Point offset = sel.getValue();
				com.setLocation(e.getX() + offset.x, e.getY() + offset.y);
			}
		}
		else {
			sr.dim.setSize(Math.abs(e.getPoint().x - press.x), Math.abs(e.getPoint().y - press.y));
			sr.setLocation(Math.min(e.getPoint().x, press.x), Math.min(e.getPoint().y, press.y));
		}
		canvas.repaint();
	}
	
	private void setObjZOrder(BasicObject obj) {
		for(Port port: obj.ports) {
			canvas.setComponentZOrder(port, canvas.getComponentZOrder(obj));
			for(Line line: port.lines)
				canvas.setComponentZOrder(line, canvas.getComponentZOrder(port));
		}
	}
	
	private boolean setGroupZOrder(Group group) {
		boolean selected = group.isSelected();
		if(group.getParentGroup() != null) {
			selected = setGroupZOrder(group.getParentGroup());
		}
		if(selected) {
			for(BasicObject obj: group.getChildren()) {
				canvas.setComponentZOrder(obj, canvas.getComponentZOrder(group));
				setObjZOrder(obj);
			}
			canvas.moveToFront(group);
		}
		return selected;
	}
	
	private void moveToFront() {
		boolean unsort = false;
		for(int i = canvas.objs.size() - 1; i >= 0; --i) {
			BasicObject obj = canvas.objs.get(i);
			if(obj.isSelected()) {
				unsort = true;
				canvas.moveToFront(obj);
				setObjZOrder(obj);
			}
		}
		for(int i = canvas.groups.size() - 1; i >= 0; i--)
			setGroupZOrder(canvas.groups.get(i));
		if(unsort) {
			canvas.objs.sort(new Comparator<BasicObject>() {
				@Override
				public int compare(BasicObject arg0, BasicObject arg1) {
					return canvas.getComponentZOrder(arg0) - canvas.getComponentZOrder(arg1);
				}
			});
		}
	}

}
