package Model.Base;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

import javax.swing.JComponent;

import Model.Objects.Group;

/**
 * Base of all object components
 * 
 * @author Jimmy801
 *
 * @see {@link JComponent}
 */
public class BasicObject extends JComponent {
	public Vector<Port> ports;
	protected Group parentGroup;
	protected boolean selected;
	private int PORT_NUM = 4;

	public BasicObject() {
	}

	public BasicObject(Point pt, int w, int h) {
		this(pt, w, h, "");
	}

	public BasicObject(int x, int y, Dimension dim) {
		this(x, y, dim.width, dim.height, "");
	}

	public BasicObject(Point pt, Dimension dim) {
		this(pt, dim, "");
	}

	public BasicObject(int x, int y, int w, int h) {
		this(x, y, w, h, "");
	}

	public BasicObject(Point pt, int w, int h, String name) {
		this(pt.x, pt.y, w, h, name);
	}

	public BasicObject(int x, int y, Dimension dim, String name) {
		this(x, y, dim.width, dim.height, name);
	}

	public BasicObject(Point pt, Dimension dim, String name) {
		this(pt.x, pt.y, dim.width, dim.height, name);
	}

	public BasicObject(int x, int y, int width, int height, String name) {
		this.ports = new Vector<>();
		for (int i = 0; i < PORT_NUM; ++i) {
			this.ports.add(new Port(this.getLocation(), this));
		}
		parentGroup = null;
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
		return (p.x > this.getX()) && (p.y > this.getY()) && 
				(p.x < this.getX() + this.getWidth()) && (p.y < this.getY() + this.getHeight());
	}

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
		}
	}

	public void setPortsVisible(boolean isVisible) {
		for (Port port : ports) {
			port.setVisible(isVisible);
		}
	}

	public void setPortNumber(int PORT_NUM) {
		this.PORT_NUM = PORT_NUM;
		ports.clear();
		for (int i = 0; i < PORT_NUM; ++i) {
			this.ports.add(new Port(this.getLocation(), this));
		}
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		setPortsVisible(selected);
	}

	public boolean isSelected() {
		return selected;
	}

	public Group getParentGroup() {
		return parentGroup;
	}

	public void setParentGroup(Group parentGroup) {
		this.parentGroup = parentGroup;
	}

	/**
	 * Calculate the dimension of text.
	 * 
	 * @param text - The string of text
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
