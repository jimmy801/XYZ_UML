package Control.LineModes;

import java.awt.event.MouseEvent;

import Control.Base.LineMode;
import Model.Base.Line;
import Model.Lines.CompositionLine;

public class GeneralizationMode extends LineMode {

	public GeneralizationMode() {
		super();
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
		initPtr();
		changePortColor(e.getPoint());
//		canvas.repaint();
	}
}
