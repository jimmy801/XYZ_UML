package Control.Base;

import java.awt.event.MouseEvent;

import Model.Base.BasicObject;

/**
 * Base of all object controls
 * 
 * @author Jimmy801
 *
 * @see {@link Mode}
 */
public class ObjectMode extends Mode {

	public ObjectMode() {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		for (BasicObject obj : canvas.objs) {
			obj.setSelected(false);
		}
	}
}
