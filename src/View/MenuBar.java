package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import Model.Base.BasicObject;
import Model.Objects.Group;

/**
 * MenuBar of specified operation
 *  
 * @author Jimmy801
 *
 * @see {@link JMenuBar}
 */
public class MenuBar extends JMenuBar {
	private static MenuBar menuBarInstance = null;
	private static JMenu fileMenu;
	private static JMenu editMenu;
	private JMenuItem exit, rename;
	private JMenuItem group, ungroup, clear;
	private Canvas canvas = Canvas.getInstance();

	public MenuBar() {
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

		// perform action
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

		rename.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object result = JOptionPane.showInputDialog(new JFrame(), "Enter new name:");
				if (result != null && !result.toString().isEmpty())
					canvas.getSelectedObjs()[0].setName(result.toString());
				setMenuItemEnable();
			}
		});

		group.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Group group = new Group();
				Iterator<BasicObject> it = canvas.objs.iterator();
				while (it.hasNext()) {
					BasicObject obj = it.next();
					if (obj.isSelected()) {
						group.addChild(obj);
						obj.setSelected(false);
						it.remove();
					}
				}
				group.getChildren().sort(new Comparator<BasicObject>() {
					@Override
					public int compare(BasicObject arg0, BasicObject arg1) {
						return canvas.getComponentZOrder(arg0) - canvas.getComponentZOrder(arg1);
					}
				});
				canvas.objs.add(0, group);
				canvas.groups.add(0, group);
				canvas.add(group, 0);
				canvas.moveToFront();
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
						g.getChildren().forEach((el) -> {
							el.setSelected(false);
							el.setParentGroup(null);
						});
						g.getChildren().clear();
						canvas.objs.remove(g);
						canvas.remove(g);
						it.remove();
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

	public void setRenamedable(boolean renamedable) {
		rename.setEnabled(renamedable);
	}

	public void setGroupable(boolean groupable) {
		group.setEnabled(groupable);
	}

	public void setUngroupable(boolean ungroupable) {
		ungroup.setEnabled(ungroupable);
	}

	public void setClearable(boolean clearable) {
		clear.setEnabled(clearable);
	}

	public void setMenuItemEnable() {
		int selectedObjs = canvas.countObjSelected();
		int selectedGroups = canvas.countGroupSelected();
		setRenamedable(selectedObjs == 1 && selectedGroups != 1);
		setUngroupable(selectedGroups == 1 && selectedObjs == 1);
		setGroupable(selectedObjs > 1);
		setClearable(!canvas.objs.isEmpty());
	}
}
