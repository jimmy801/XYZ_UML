package Model.Base;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

import javax.swing.JComponent;

/**
 * Base of all object components
 * 
 * @author Jimmy801
 *
 * @see {@link JComponent}
 */
public class BasicObject extends JComponent {
	/**
	 * Ports of connection lines
	 */
	public Vector<Port> ports;
	/**
	 * Check component is selected by user
	 */
	protected boolean selected;
	/**
	 * The number of ports
	 */
	private int PORT_NUM = 4;

	public BasicObject() {
	}

	/**
	 * Initial by top-left point, width, and height.<br>
	 * Default name is empty
	 * 
	 * @param p      - top-left corner point of this component
	 * @param width  - width of this component
	 * @param height - height of this component
	 */
	public BasicObject(Point p, int width, int height) {
		this(p, width, height, "");
	}

	/**
	 * Initial by left x, top y, and dimension.<br>
	 * Default name is empty
	 * 
	 * @param x   - left position of this component
	 * @param y   - top position of this component
	 * @param dim - dimension of this component
	 */
	public BasicObject(int x, int y, Dimension dim) {
		this(x, y, dim.width, dim.height, "");
	}

	/**
	 * Initial by top-left point, and dimension.<br>
	 * Default name is empty
	 * 
	 * @param p   - top-left corner point of this component
	 * @param dim - dimension of this component
	 */
	public BasicObject(Point p, Dimension dim) {
		this(p, dim, "");
	}

	/**
	 * Initial by left x, top y, width, and height.<br>
	 * Default name is empty
	 * 
	 * @param x      - left position of this component
	 * @param y      - top position of this component
	 * @param width  - width of this component
	 * @param height - height of this component
	 */
	public BasicObject(int x, int y, int width, int height) {
		this(x, y, width, height, "");
	}

	/**
	 * Initial by top-left point, width, height, and name of component.
	 * 
	 * @param p      - top-left corner point of this component
	 * @param width  - width of this component
	 * @param height - height of this component
	 * @param name   - name of this component
	 */
	public BasicObject(Point p, int width, int height, String name) {
		this(p.x, p.y, width, height, name);
	}

	/**
	 * Initial by top-left point, width, height, and name of component.
	 * 
	 * @param x    - left position of this component
	 * @param y    - top position of this component
	 * @param dim  - dimension of this component
	 * @param name - name of this component
	 */
	public BasicObject(int x, int y, Dimension dim, String name) {
		this(x, y, dim.width, dim.height, name);
	}

	/**
	 * Initial by top-left point, width, height, and name of component.
	 * 
	 * @param p    - top-left corner point of this component
	 * @param dim  - dimension of this component
	 * @param name - name of this component
	 */
	public BasicObject(Point p, Dimension dim, String name) {
		this(p.x, p.y, dim.width, dim.height, name);
	}

	/**
	 * Initial by top-left point, width, height, and name of component.
	 * 
	 * @param x      - left position of this component
	 * @param y      - top position of this component
	 * @param width  - width of this component
	 * @param height - height of this component
	 * @param name   - name of this component
	 */
	public BasicObject(int x, int y, int width, int height, String name) {
		this.ports = new Vector<>();
		for (int i = 0; i < PORT_NUM; ++i) {
			this.ports.add(new Port(this.getLocation(), this));
		}
		this.setName(name);
		this.setSelected(true);
		this.setBounds(x, y, width, height);
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
			ports.get(i).setLocation(pt.x + ((dim.width * (isLeft ^ 1)) >> (i & 1)) - (Port.width * isLeft),
					pt.y + ((dim.height * (isTop ^ 1)) >> (i & 1 ^ 1)) - (Port.height * isTop));

			// same as the following code:
//			switch (i) {
//			case 0: // top
//				ports.get(i).setLocation(new Point(pt.x + dim.width / 2, pt.y - Port.height));
//				break;
//			case 1: // left
//				ports.get(i).setLocation(new Point(pt.x - Port.width, pt.y + dim.height / 2));
//				break;
//			case 2: // right
//				ports.get(i).setLocation(new Point(pt.x + dim.width, pt.y + dim.height / 2));
//				break;
//			case 3: // bottom
//				ports.get(i).setLocation(new Point(pt.x + dim.width / 2, pt.y + dim.height));
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

	/**
	 * Clear old ports and set new number of ports
	 * 
	 * @param PORT_NUM - new number of ports
	 */
	public void setPortNumber(int PORT_NUM) {
		this.PORT_NUM = PORT_NUM;
		ports.clear();
		for (int i = 0; i < PORT_NUM; ++i) {
			this.ports.add(new Port(this.getLocation(), this));
		}
	}

	/**
	 * Set component is selected by user
	 * 
	 * @param selected - user is selected this component or not
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
		setPortsVisible(selected);
	}

	/**
	 * Get this component is selected by user or not
	 * 
	 * @return this component is selected by user or not
	 */
	public boolean isSelected() {
		return selected;
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
