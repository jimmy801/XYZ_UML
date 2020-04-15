package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MenuBar extends JMenuBar {
    private static MenuBar menuBarInstance = null;
	private static JMenu fileMenu;
	private static JMenu editMenu;
    private JMenuItem exit, rename, group, ungroup, clear;
    private Canvas canvas = Canvas.getInstance();
	
	public MenuBar() {
        init();
        this.add(fileMenu);
        this.add(editMenu);
	}
	
    public static MenuBar getInstance() {
    	if(menuBarInstance == null) {
    		menuBarInstance = new MenuBar();
	    }
    	return menuBarInstance;
    }
	
	private void initFileMenu() {
		fileMenu = new JMenu("File");

        exit = new JMenuItem("Exit");
        clear = new JMenuItem("Clear");

        // perform action
        exit.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                }
        );

        clear.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        canvas.objs.clear();
                        canvas.groups.clear();
                        canvas.ports.clear();
                        canvas.removeAll();
                        setMenuItemEnable();
                        canvas.repaint();
                    }
                }
        );

        fileMenu.add(clear);
        fileMenu.add(exit);
	}
	
	private void initEditMenu(){
        editMenu = new JMenu("Edit");

        rename = new JMenuItem("Rename");
        group = new JMenuItem("Group");
        ungroup = new JMenuItem("Ungroup");
        
        rename.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Object result = JOptionPane.showInputDialog(new JFrame(), "Enter new name:");
            	if(result != null && !result.toString().isEmpty())
            		canvas.getSelectedObjs()[0].rename(result.toString());
                setMenuItemEnable();
            }
        });

        group.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.groupObjs();
                setMenuItemEnable();
                canvas.repaint();
            }
        });

        ungroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.ungroupObjs();
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
