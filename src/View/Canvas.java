package View;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.JComponent;
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

/**
 * UML canvas
 * 
 * @author Jimmy801
 *
 * @see {@link JLayeredPane}
 */
public class Canvas extends JLayeredPane {
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
	 * Initial all modes of canvas by {@link Utils.MODE}
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
	 * Get arrays of selected {@link Model.Base.BasicObject} components on canvas
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
	 * Get arrays of selected {@link Model.Objects.Group} groups on canvas
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
	 * Set z-order of children(like ports and lines) of a
	 * {@link Model.Base.BasicObject} component on canvas
	 * 
	 * @param obj - the unset z-order object
	 */
	public void setObjZOrder(BasicObject obj) {
		int OZOrder = getComponentZOrder(obj);
		for (Port port : obj.ports) {
			setComponentZOrder(port, OZOrder);
			int PZOrder = getComponentZOrder(port);
			for (Line line : port.lines) {
				setComponentZOrder(line, PZOrder);
			}
		}
	}

	/**
	 * Set z-order of children(like groups and other objects) of a
	 * {@link Model.Objects.Group} component on canvas
	 * 
	 * Use recursive to check whether parent group is selected.
	 * 
	 * @param group - the unset z-order group
	 * @return group is selected or not
	 */
	public int setGroupZOrder(int groupZ, Group group) {
		for(BasicObject obj: group.getChildren()) {
			setComponentZOrder(obj, groupZ + 1);
			setObjZOrder(obj);
			for(Group g: groups) {
				if(obj == g) {
					setGroupZOrder(groupZ + 1, g);
					break;
				}
			}
			
			groupZ += getChildrenTotal(obj);
		}
		return groupZ;
	}
	
	public int getChildrenTotal(BasicObject obj) {
		int total = 0;
		for(Group g: groups) {
			if(obj == g) {
				for(BasicObject child: g.getChildren()) {
					total += getChildrenTotal(child);
				}
				break;
			}
		}
		for(Port port: obj.ports) {
			total += port.lines.size() + 1; // lines and ports
		}
		total += 1; // self object
		return total;
	}

	/**
	 * Change z-orders of all selected components on canvas
	 */
	public void moveToFront() {
		boolean unsort = false; // check order of objs need to be sorted.

		// move selected objs to front
		for (int i = objs.size() - 1; i >= 0; --i) { // reverse move to front, make sure z-order wouldn't be reversed
			BasicObject obj = objs.get(i);
			if (obj.isSelected()) {
				unsort = true;
				moveToFront(obj);
				setObjZOrder(obj);
				for (Group group : groups) {
					if(obj == group) {
						setGroupZOrder(0, group);
						break;
					}
				}
			}
		}

		if (unsort) {
			objs.sort(zOrderCmp);
			groups.sort(zOrderCmp);
		}
		this.repaint();
	}
}
