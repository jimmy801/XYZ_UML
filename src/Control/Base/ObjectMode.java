package Control.Base;

import java.awt.event.MouseEvent;

import Model.Objects.*;
import Model.Objects.Class;
import Model.Base.BasicObject;
import Model.Base.Port;
import Utils.MODE;

public class ObjectMode extends Mode {
	private MODE mode;
	
	public ObjectMode() {}
	
	public ObjectMode(MODE mode) {
    	this.mode = mode;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        BasicObject obj = null;
        switch(mode) {
		case CLASS:
			obj = new Class(e.getPoint(), "Class");
			break;
		case USECASE:
			obj = new UseCase(e.getPoint(), "Use case");
			break;
		default:
			System.exit(1);
			break;
		}
    	for(BasicObject ob: canvas.objs)
    		ob.setSelected(false);
        canvas.add(obj, 0);
        for(Port port: obj.ports){
            canvas.add(port, 0);
            canvas.ports.add(0, port);
        }
        canvas.objs.add(0, obj);
        canvas.repaint();
        menuBar.setMenuItemEnable();
    }
}
