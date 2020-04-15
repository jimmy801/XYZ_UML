package Control.Base;

import java.awt.event.MouseEvent;

import Model.Base.BasicObject;

public class ObjectMode extends Mode {

	public ObjectMode() {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		for (BasicObject obj : canvas.objs)
			obj.setSelected(false);
	}
}
