package Control.Base;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Model.Base.BasicObject;
import Model.Base.Line;
import Model.Base.Port;
import Model.Lines.*;
import Model.Objects.Class;
import Model.Objects.UseCase;
import Utils.MODE;

public class LineMode extends Mode {
	protected Point press;
	protected Point release;
	protected Port pressP;
	protected Port releaseP;
	protected BasicObject pressObj;
	protected BasicObject releaseObj;
	
	public LineMode(){
		press = new Point();
		release = new Point();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		press.setLocation(e.getPoint());
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		release.setLocation(e.getPoint());
		for(Port p: canvas.ports){
            p.setVisible(p.getParent().isSelected());
        }
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		super.mouseDragged(e);
		for(Port p: canvas.ports){
			if(p.contains(e.getPoint())) {
				p.setColor(Color.GREEN);
				p.setVisible(true);
			}
			else {
				p.setColor(Color.BLACK);
				p.setVisible(p.getParent().isSelected());
			}
        }
		canvas.repaint();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		for(Port p: canvas.ports){
            p.setVisible(true);
            if(p.contains(e.getPoint()))
				p.setColor(Color.GREEN);
            else
            	p.setColor(Color.BLACK);
        }
        canvas.repaint();
	}
	
	protected boolean connectLine() {
		pressObj = null;
		pressP = null;
		releaseObj = null;
		releaseP = null;
        for(Port p: canvas.ports){
            if(p.contains(press)){
                pressObj = p.getParent();
                pressP = p;
            }else if(p.contains(release)){
                releaseObj = p.getParent();
                releaseP = p;
            }
        }

        if(pressP != null && releaseP != null && pressP != releaseP) {
        	System.out.println("Connect");
        	return true;
        }
        return pressP != null && releaseP != null && pressP != releaseP;
    }
}
