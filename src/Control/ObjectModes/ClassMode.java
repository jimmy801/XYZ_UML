package Control.ObjectModes;

import java.awt.event.MouseEvent;

import Control.Base.ObjectMode;
import Model.Base.BasicObject;
import Model.Base.Port;
import Model.Objects.Class;

/**
 * Class Object control
 * 
 * @author Jimmy801
 *
 * @see {@link ObjectMode}
 */
public class ClassMode extends ObjectMode {
	public ClassMode() {
		super();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		BasicObject obj = new Class(e.getPoint(), "Class");
		canvas.add(obj, 0);
		for (Port port : obj.ports) {
			canvas.add(port, 0);
			canvas.ports.add(0, port);
		}
		canvas.objs.add(0, obj);
		canvas.repaint();
		menuBar.setMenuItemEnable();
	}
}
