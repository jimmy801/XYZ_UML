package View;

import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JLayeredPane;

import Control.Base.Mode;
import Control.Base.SelectMode;
import Control.LineModes.AssociationMode;
import Control.LineModes.CompositionMode;
import Control.LineModes.GeneralizationMode;
import Control.ObjectModes.ClassMode;
import Control.ObjectModes.UseCaseMode;
import Model.Base.BasicObject;
import Model.Base.Line;
import Model.Base.Port;
import Model.Objects.Group;
import Utils.MODE;

public class Canvas extends JLayeredPane {
	private static Canvas canvasInstance = null;
	public static Mode currentMode = null;
	private int mode;
	private static Vector<Mode> modes;
	public Vector<BasicObject> objs;
	public Vector<Port> ports;
	public Vector<Group> groups;

	public Canvas() {
		this.setLayout(null); // make objects wouldn't locate to wrong position.
		this.setBackground(Color.WHITE);
		objs = new Vector<BasicObject>();
		ports = new Vector<Port>();
		groups = new Vector<Group>();
		modes = new Vector<Mode>();
	}

	public static Canvas getInstance() {
		if (canvasInstance == null) {
			canvasInstance = new Canvas();
			initModes();
		}
		return canvasInstance;
	}

	private static void initModes() {
		for (MODE m : MODE.values()) {
			switch (m) {
			case SELECT:
				modes.add(new SelectMode());
				break;
			case ASSOCIATION:
				modes.add(new AssociationMode());
				break;
			case GENERALIZATION:
				modes.add(new GeneralizationMode());
				break;
			case COMPOSITION:
				modes.add(new CompositionMode());
				break;
			case CLASS:
				modes.add(new ClassMode());
				break;
			case USECASE:
				modes.add(new UseCaseMode());
				break;
			}
		}
		canvasInstance.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				for (Port port : canvasInstance.ports) {
					for (Line line : port.lines) {
						line.setSize(canvasInstance.getSize());
					}
				}
			}
		});
	}

	@Override
	public boolean isOptimizedDrawingEnabled() {
		return false;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.removeMouseListener(currentMode);
		this.removeMouseMotionListener(currentMode);
		currentMode = modes.elementAt(mode);
		this.mode = mode;
		this.addMouseListener(currentMode);
		this.addMouseMotionListener(currentMode);
	}

	public BasicObject[] getSelectedObjs() {
		return objs.stream().filter(e -> e.isSelected()).toArray(BasicObject[]::new);
	}

	public int countObjSelected() {
		return getSelectedObjs().length;
	}

	public Group[] getSelectedGroups() {
		return groups.stream().filter(e -> e.isSelected()).toArray(Group[]::new);
	}

	public int countGroupSelected() {
		return getSelectedGroups().length;
	}

	public void groupObjs() {
		Group group = new Group(/* objs.stream().filter(e->e.isSelected()).toArray(BasicObject[]::new) */);
		Iterator<BasicObject> it = objs.iterator();
		while (it.hasNext()) {
			BasicObject obj = it.next();
			if (obj.isSelected()) {
				group.addChild(getComponentZOrder(obj) <= getComponentZOrder(group) ? 0 : group.getChildren().size(),
						obj);
				obj.setSelected(false);
				it.remove();
			}
		}
		group.getChildren().forEach((el)->System.out.println(el.getName()));
		objs.add(0, group);
		groups.add(0, group);
		this.add(group, 0);
		moveToFront();
		this.repaint();
	}

	public void ungroupObjs() {
		Iterator<Group> it = groups.iterator();
		while (it.hasNext()) {
			Group g = it.next();
			if (g.isSelected()) {
				objs.addAll(0, g.getChildren());
				g.getChildren().forEach((el) -> {
					el.setSelected(false);
					el.setParentGroup(null);
				});
				g.getChildren().clear();
				objs.remove(g);
				this.remove(g);
				it.remove();
				break;
			}
		}
		this.repaint();
	}

	public void setObjZOrder(BasicObject obj) {
		for (Port port : obj.ports) {
			setComponentZOrder(port, getComponentZOrder(obj));
			for (Line line : port.lines)
				setComponentZOrder(line, getComponentZOrder(port));
		}
	}

	public boolean setGroupZOrder(Group group) {
		boolean selected = group.isSelected();
		if (group.getParentGroup() != null) {
			selected = setGroupZOrder(group.getParentGroup());
		}
		if (selected) {
			for (BasicObject obj : group.getChildren()) {
				setComponentZOrder(obj, getComponentZOrder(group));
				setObjZOrder(obj);
			}
			moveToFront(group);
		}
		return selected;
	}

	public void moveToFront() {
		boolean unsort = false;
		for (int i = objs.size() - 1; i >= 0; --i) {
			BasicObject obj = objs.get(i);
			if (obj.isSelected()) {
				unsort = true;
				moveToFront(obj);
				setObjZOrder(obj);
			}
		}
		for (int i = groups.size() - 1; i >= 0; i--)
			setGroupZOrder(groups.get(i));
		if (unsort) {
			objs.sort(new Comparator<BasicObject>() {
				@Override
				public int compare(BasicObject arg0, BasicObject arg1) {
					return getComponentZOrder(arg0) - getComponentZOrder(arg1);
				}
			});
		}
		this.repaint();
	}
}
