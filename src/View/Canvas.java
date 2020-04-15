package View;

import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import Control.Base.*;
import Control.LineModes.*;
import Control.ObjectModes.*;
import Model.Base.*;
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
    	if(canvasInstance == null) {
    		canvasInstance = new Canvas();
    		initModes();
    	}
    	return canvasInstance;
    }
    
    private static void initModes() {
    	for(MODE m: MODE.values()) {
    		switch(m) {
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
			 	for(Port port: canvasInstance.ports) {
			 		for(Line line: port.lines) {
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
    	return objs.stream().filter(e->e.isSelected()).toArray(BasicObject[]::new);
    }
    
    public int countObjSelected() {
    	return getSelectedObjs().length;
    }
    
    public void groupObjs() {
    	Group group = new Group(/*objs.stream().filter(e->e.isSelected()).toArray(BasicObject[]::new)*/);
    	for(BasicObject obj: objs) {
    		if(obj.isSelected()) {
    			group.addChild(obj);
    		}
    	}
    	group.getChildren().forEach((el)->{ el.setSelected(false); objs.remove(el); });
    	objs.add(0, group);
    	groups.add(0, group);
    	canvasInstance.add(group, 0);
    	canvasInstance.repaint();
    }
    
    public Group[] getSelectedGroups() {
    	return groups.stream().filter(e->e.isSelected()).toArray(Group[]::new);
    }
    
    public int countGroupSelected() {
    	return getSelectedGroups().length;
    }
    
    public void ungroupObjs() {
    	Iterator<Group> it = groups.iterator();
    	while(it.hasNext()){
    		Group g = it.next();
	    	if(g.isSelected()){
	    		objs.addAll(0, g.getChildren()); 
	    		g.getChildren().forEach((el)->{ el.setSelected(false); el.setParentGroup(null); });
	    		g.getChildren().clear();
	    		objs.remove(g);
	    		canvasInstance.remove(g);
	    		it.remove();
	    		break;
	    	}
    	}
    	canvasInstance.repaint();
    }
}

