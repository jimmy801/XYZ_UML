package View;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JPanel;

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

/**
 * UML canvas
 * 
 * @author Jimmy801
 *
 * @see {@link JPanel}
 */
public class Canvas extends JPanel {
	/**
	 * Instance of canvas
	 */
	private static Canvas canvasInstance = null;
	/**
	 * Current mode of canvas
	 */
	public static Mode currentMode = null;
	/**
	 * Current mode id
	 */
	private int mode;
	/**
	 * All modes of canvas
	 */
	private static Vector<Mode> modes;
	/**
	 * All components of canvas
	 */
	public Vector<BasicObject> objs;
	/**
	 * All ports of canvas
	 */
	public Vector<Port> ports;
	/**
	 * All group of canvas
	 */
	public Vector<Group> groups;

	/**
	 * Comparator of component by z-order
	 */
	private Comparator<JComponent> zOrderCmp = new Comparator<JComponent>() {
		@Override
		public int compare(JComponent arg0, JComponent arg1) {
			return getComponentZOrder(arg0) - getComponentZOrder(arg1);
		}
	};

	/**
	 * Initialize canvas
	 */
	public Canvas() {
		this.setLayout(null); // make objects wouldn't locate to wrong position.
		objs = new Vector<BasicObject>();
		ports = new Vector<Port>();
		groups = new Vector<Group>();
		modes = new Vector<Mode>();
		registerCanvasListener();
	}

	/**
	 * Register non-Modes listeners of canvas
	 */
	private void registerCanvasListener() {
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				// make sure lines of ports can be painted on full canvas
				for (Port port : canvasInstance.ports) {
					for (Line line : port.lines) {
						line.setSize(canvasInstance.getSize());
					}
				}
			}
		});
	}

	/**
	 * Get instance of Canvas
	 * 
	 * @return {@link Canvas} CanvasInstance
	 */
	public static Canvas getInstance() {
		if (canvasInstance == null) {
			canvasInstance = new Canvas();
			initModes();
		}
		return canvasInstance;
	}

	/**
	 * Initial all modes of canvas by {@link MODE}
	 */
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

	}

	/**
	 * Get id of current mode
	 * 
	 * @return current mode id
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * Change mode by mode id
	 * 
	 * @param mode - change mode id
	 */
	public void setMode(int mode) {
		this.removeMouseListener(currentMode);
		this.removeMouseMotionListener(currentMode);
		currentMode = modes.elementAt(mode);
		this.mode = mode;
		this.addMouseListener(currentMode);
		this.addMouseMotionListener(currentMode);
	}

	/**
	 * Get arrays of selected {@link BasicObject} components on canvas
	 * 
	 * @return array of selected components
	 */
	public BasicObject[] getSelectedObjs() {
		return objs.stream().filter(e -> e.isSelected()).toArray(BasicObject[]::new);
	}

	/**
	 * Get number of selected objects
	 * 
	 * @return number of selected objects
	 * 
	 * @see {@link #getSelectedObjs}
	 */
	public int countObjSelected() {
		return getSelectedObjs().length;
	}

	/**
	 * Get arrays of selected {@link Group} groups on canvas
	 * 
	 * @return array of selected groups
	 */
	public Group[] getSelectedGroups() {
		return groups.stream().filter(e -> e.isSelected()).toArray(Group[]::new);
	}

	/**
	 * Get number of selected groups
	 * 
	 * @return number of selected groups
	 * 
	 * @see {@link #getSelectedGroups}
	 */
	public int countGroupSelected() {
		return getSelectedGroups().length;
	}

	/**
	 * Set z-order of children(like ports and lines) of a {@link BasicObject}
	 * component on canvas
	 * 
	 * @param obj - the unset z-order object
	 */
	public void setObjZOrder(BasicObject obj) {
		int OZOrder = getComponentZOrder(obj);
		for (Port port : obj.ports) {
			setComponentZOrder(port, OZOrder);
			int PZOrder = getComponentZOrder(port);
			for (Line line : port.lines) {
				Port src = line.getSrc();
				Port dst = line.getDst();
				Port otherPort = (src == port) ? dst : src;
				setComponentZOrder(line, Math.min(getComponentZOrder(otherPort), PZOrder));
			}
		}
	}

	/**
	 * Set z-order of children(like groups and other objects) of a {@link Group}
	 * component on canvas.
	 * 
	 * Use recursive to check whether parent group is selected.
	 * 
	 * @param groupZ - start depth of group
	 * @param group  - the unset z-order group
	 */
	public void setGroupZOrder(int groupZ, Group group) {
		for (int i = group.getChildren().size() - 1; i >= 0; --i) { // reverse move to front, make sure z-order wouldn't
																	// be reversed
			BasicObject obj = group.getChildren().get(i);
			if (groups.contains(obj)) { // if obj is a Group
				setGroupZOrder(0, groups.get(groups.indexOf(obj)));
			}
			setComponentZOrder(obj, groupZ);
			setObjZOrder(obj);
		}
		setComponentZOrder(group, groupZ); // children must deeper than group
	}

	/**
	 * Change z-orders of all selected components on canvas
	 */
	public void resetDepth() {
		boolean unsort = false; // check order of objs need to be sorted.

		// move selected objs to front
		for (int i = objs.size() - 1; i >= 0; --i) { // reverse move to front, make sure z-order wouldn't be reversed
			BasicObject obj = objs.get(i);
			if (obj.isSelected()) {
				unsort = true;
				setComponentZOrder(obj, 0);
				setObjZOrder(obj); // move its ports and lines

				// if obj is a Group, all of its children also need to move to front
				if (groups.contains(obj)) {
					setGroupZOrder(0, groups.get(groups.indexOf(obj)));
				}
			}
		}

		if (unsort) { // sort objs, ports and groups vector by z-order
			objs.sort(zOrderCmp);
			ports.sort(zOrderCmp);
			groups.sort(zOrderCmp);
		}
		this.repaint();
	}
}
