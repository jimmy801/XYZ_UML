package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import Model.Base.Shape;
import Model.Objects.Group;

/**
 * MenuBar of specified operation
 * 
 * @author Jimmy801
 *
 * @see {@link JMenuBar}
 */
public class MenuBar extends JMenuBar {
	/**
	 * Instance of MenuBar
	 */
	private static MenuBar menuBarInstance = null;
	/**
	 * Instance of UML canvas
	 */
	private Canvas canvas = Canvas.getInstance();
	/**
	 * UML file menu
	 */
	private static JMenu fileMenu;
	/**
	 * UML edit menu
	 */
	private static JMenu editMenu;
	/**
	 * Menu item of UML file menu
	 */
	private JMenuItem exit, rename;
	/**
	 * Menu item of UML edit file
	 */
	private JMenuItem group, ungroup, clear;

	private MenuBar() {
		init();
		this.add(fileMenu);
		this.add(editMenu);
	}

	public static MenuBar getInstance() {
		if (menuBarInstance == null) {
			menuBarInstance = new MenuBar();
		}
		return menuBarInstance;
	}

	private void initFileMenu() {
		fileMenu = new JMenu("File");

		exit = new JMenuItem("Exit");
		clear = new JMenuItem("Clear");

		// register menu item actions
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.objs.clear();
				canvas.groups.clear();
				canvas.ports.clear();
				canvas.removeAll();
				setMenuItemEnable();
				canvas.repaint();
			}
		});

		fileMenu.add(clear);
		fileMenu.add(exit);
	}

	private void initEditMenu() {
		editMenu = new JMenu("Edit");

		rename = new JMenuItem("Rename");
		group = new JMenuItem("Group");
		ungroup = new JMenuItem("Ungroup");

		// register menu item actions
		rename.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Shape selectedObj = canvas.getSelectedObjs()[0];
				Object result = JOptionPane.showInputDialog(new JFrame(), "Enter new name:", selectedObj.getName());
				if (result != null && !result.toString().isEmpty())
					selectedObj.setName(result.toString());
				setMenuItemEnable();
			}
		});

		group.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Group group = new Group();
				Iterator<Shape> it = canvas.objs.iterator();
				while (it.hasNext()) {
					Shape obj = it.next();
					if (obj.isSelected()) {
						group.addChild(obj);
						obj.setSelected(false);
						it.remove(); // remove obj from canvas.objs
					}
				}

				canvas.objs.add(0, group);
				canvas.groups.add(0, group);
				canvas.add(group, 0);
				canvas.resetDepth();
				canvas.repaint();
				setMenuItemEnable();
			}
		});

		ungroup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Iterator<Group> it = canvas.groups.iterator();
				while (it.hasNext()) {
					Group g = it.next();
					if (g.isSelected()) {
						canvas.objs.addAll(0, g.getChildren());
						g.getChildren().forEach((el) -> el.setSelected(false));
						g.getChildren().clear();
						canvas.objs.remove(g); // remove g from canvas.objs
						canvas.remove(g);
						it.remove(); // remove obj from canvas.groups
						break;
					}
				}
				canvas.repaint();
				setMenuItemEnable();
			}
		});

		editMenu.add(rename);
		editMenu.add(group);
		editMenu.add(ungroup);
	}

	private void init() {
		initFileMenu();
		initEditMenu();
		setMenuItemEnable();
	}

	/**
	 * Enable rename menu item
	 * 
	 * @param renamedable - enable rename menu item or not
	 */
	public void setRenamedable(boolean renamedable) {
		rename.setEnabled(renamedable);
	}

	/**
	 * Enable group menu item
	 * 
	 * @param groupable - enable group menu item or not
	 */
	public void setGroupable(boolean groupable) {
		group.setEnabled(groupable);
	}

	/**
	 * Enable ungroup menu item
	 * 
	 * @param ungroupable - enable ungroup menu item or not
	 */
	public void setUngroupable(boolean ungroupable) {
		ungroup.setEnabled(ungroupable);
	}

	/**
	 * Enable clear menu item
	 * 
	 * @param clearable - enable clear menu item or not
	 */
	public void setClearable(boolean clearable) {
		clear.setEnabled(clearable);
	}

	/**
	 * Set menu items enable by UML canvas
	 */
	public void setMenuItemEnable() {
		int selectedObjs = canvas.countObjSelected(); // all selected objs(contain groups)
		int selectedGroups = canvas.countGroupSelected(); // all selected groups
		setRenamedable(selectedObjs == 1 && selectedGroups != 1);
		setUngroupable(selectedGroups == 1 && selectedObjs == 1);
		setGroupable(selectedObjs > 1);
		setClearable(!canvas.objs.isEmpty());
	}
}
