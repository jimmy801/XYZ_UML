package Model.Base;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Base of all object components
 * 
 * @author Jimmy801
 *
 * @see {@link Shape}
 */
public class BasicObject extends Shape {
	/**
	 * The number of ports
	 */
	private int PORT_NUM = 4;
	/**
	 * Ports of connection lines
	 */
	public Port ports[];

	/**
	 * Initial by top-left point, width, height, and name of component.
	 * 
	 * @param p    - top-left corner point of this component
	 * @param dim  - dimension of this component
	 * @param name - name of this component
	 */
	public BasicObject(Point p, Dimension dim, String name) {
		this.ports = new Port[PORT_NUM];
		for (int i = 0; i < PORT_NUM; ++i) {
			ports[i] = new Port(this.getLocation(), this);
		}
		this.setName(name);
		this.setSelected(true);
		this.setBounds(p.x, p.y, dim.width, dim.height);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		setPortLocation();
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		repaint();
	}

	@Override
	public boolean contains(Point p) {
		return (p.x > this.getX()) && (p.y > this.getY()) && (p.x < this.getX() + this.getWidth())
				&& (p.y < this.getY() + this.getHeight());
	}

	/**
	 * Set location of ports by this object
	 */
	private void setPortLocation() {
		// left, top, right, bottom
		int isLeft, isTop;
		Point pt = this.getLocation();
		Dimension dim = this.getSize();
		for (int i = 0; i < PORT_NUM; ++i) {
			isLeft = i == 0 ? 1 : 0;
			isTop = i == 1 ? 1 : 0;
			ports[i].setLocation(pt.x + ((dim.width * (isLeft ^ 1)) >> (i & 1)) - (Port.width * isLeft),
					pt.y + ((dim.height * (isTop ^ 1)) >> (i & 1 ^ 1)) - (Port.height * isTop));

			// same as the following code:
//			switch (i) {
//			case 0: // top
//				ports[i].setLocation(new Point(pt.x + dim.width / 2, pt.y - Port.height));
//				break;
//			case 1: // left
//				ports[i].setLocation(new Point(pt.x - Port.width, pt.y + dim.height / 2));
//				break;
//			case 2: // right
//				ports[i].setLocation(new Point(pt.x + dim.width, pt.y + dim.height / 2));
//				break;
//			case 3: // bottom
//				ports[i].setLocation(new Point(pt.x + dim.width / 2, pt.y + dim.height));
//				break;
//			}
		}
	}

	/**
	 * Set ports are visible or not
	 * 
	 * @param isVisible - port set to visible or not
	 */
	public void setPortsVisible(boolean isVisible) {
		for (Port port : ports) {
			port.setVisible(isVisible);
		}
	}

	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
		setPortsVisible(selected);
	}

	/**
	 * Calculate the dimension of name.
	 * 
	 * @return {@link Dimension} dimension of text
	 */
	public Dimension getTextSize() {
		Dimension size = new Dimension();
		Graphics g = this.getGraphics();
		FontMetrics fm = g.getFontMetrics(g.getFont());
		size.width = fm.stringWidth(this.getName());
		size.height = fm.getHeight();

		return size;
	}
}
