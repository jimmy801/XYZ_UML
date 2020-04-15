package Control.LineModes;

import java.awt.event.MouseEvent;

import Control.Base.LineMode;
import Model.Base.Line;
import Model.Base.Port;
import Model.Lines.AssociationLine;
import Model.Lines.CompositionLine;
import Model.Lines.GeneralizationLine;
import Utils.MODE;

public class GeneralizationMode extends LineMode {

	public GeneralizationMode() {
		super();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		
        if(connectLine()){
            Line line =  new CompositionLine(pressP, releaseP);
            line.setSize(canvas.getWidth(), canvas.getHeight());
            canvas.add(line, 0);
            pressP.addLine(line);
            releaseP.addLine(line);
        }
        canvas.repaint();
	}
}
