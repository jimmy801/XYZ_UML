package Control.Base;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;

import Model.Base.BasicObject;
import Model.Base.Port;

public class LineMode extends Mode {
	protected Point press;
	protected Point release;
	protected Port pressP;
	protected Port releaseP;
	protected BasicObject pressObj;
	protected BasicObject releaseObj;

	public LineMode() {
		press = new Point();
		release = new Point();
		initPtr();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		press.setLocation(e.getPoint());
		for (Port p : canvas.ports) {
			if (p.contains(press)) {
				pressObj = p.getParent();
				pressP = p;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		release.setLocation(e.getPoint());
		for (Port p : canvas.ports) {
			p.setVisible(p.getParent().isSelected());
		}
	}
	
	public void initPtr() {
		pressObj = null;
		pressP = null;
		releaseObj = null;
		releaseP = null;
	}
	
	public void changePortColor(Point pt) {
		changePortColor(pt, false);
	}

	public void changePortColor(Point pt, boolean drag) {
		for (Port p : canvas.ports) {
			if (p.contains(pt)) {
				p.setColor(p.getParent() != pressObj ? Color.GREEN : Color.RED);
				p.setVisible(true);
			} else {
				p.setColor(Color.BLACK);
				p.setVisible(p.getParent().isSelected() || (drag && pressObj != null));
			}
		}
		canvas.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		changePortColor(e.getPoint());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		changePortColor(e.getPoint(), true);
	}

	protected boolean connectLine() {
		initPtr();
		for (Port p : canvas.ports) {
			if (p.contains(press)) {
				pressObj = p.getParent();
				pressP = p;
			} else if (p.contains(release)) {
				releaseObj = p.getParent();
				releaseP = p;
			}
		}

		/*if (pressObj != releaseObj && pressObj != null && releaseObj != null) {
			System.out.println("Connect");
			return true;
		}*/
		return pressObj != releaseObj && pressObj != null && releaseObj != null;
	}
}
