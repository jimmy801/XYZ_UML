package Control.LineModes;

import java.awt.event.MouseEvent;

import Control.Base.LineMode;
import Model.Base.Line;
import Model.Base.Port;
import Model.Lines.CompositionLine;

/**
 * Composition line control
 * 
 * @author Jimmy801
 *
 * @see {@link LineMode}
 */
public class CompositionMode extends LineMode {

	public CompositionMode() {
		super();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);

		if (pressP != null) {
			if (draggedLine != null) {
				canvas.remove(draggedLine);
			}
			draggedLine = new CompositionLine(pressP, new Port(e.getPoint(), null));
			draggedLine.setSize(canvas.getWidth(), canvas.getHeight());
			canvas.add(draggedLine, 0);
			canvas.repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);

		if (connectLine()) {
			Line line = new CompositionLine(pressP, releaseP);
			line.setSize(canvas.getWidth(), canvas.getHeight());
			canvas.add(line, 0);
			pressP.addLine(line);
			releaseP.addLine(line);
		}
		if (draggedLine != null) {
			canvas.remove(draggedLine);
		}
		initPtr();
		changePortStyle(e.getPoint());
		canvas.repaint();
	}
}
