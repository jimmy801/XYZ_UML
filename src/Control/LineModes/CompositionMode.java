package Control.LineModes;

import java.awt.event.MouseEvent;

import Control.Base.LineMode;
import Model.Base.Line;
import Model.Lines.GeneralizationLine;

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
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);

		if (connectLine()) {
			Line line = new GeneralizationLine(pressP, releaseP);
			line.setSize(canvas.getWidth(), canvas.getHeight());
			canvas.add(line, 0);
			pressP.addLine(line);
			releaseP.addLine(line);
		}
		initPtr();
		changePortStyle(e.getPoint());
		canvas.repaint();
	}
}
