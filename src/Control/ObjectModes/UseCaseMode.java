package Control.ObjectModes;

import java.awt.event.MouseEvent;

import Control.Base.ObjectMode;
import Model.Base.BasicObject;
import Model.Base.Port;
import Model.Objects.UseCase;

public class UseCaseMode extends ObjectMode {

	public UseCaseMode() {
		super();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		BasicObject obj = new UseCase(e.getPoint(), "Use Case");
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
