package Model.Base;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

import javax.swing.JComponent;

import Model.Objects.Group;

public class BasicObject extends JComponent {
	protected Point pt;
	protected Dimension dim;
	protected String name;
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

	public BasicObject(int x, int y, int w, int h, String name) {
		this.pt = new Point(x, y);
		this.dim = new Dimension(w, h);
		this.name = name;
		this.ports = new Vector<>();
		for (int i = 0; i < PORT_NUM; ++i)
			this.ports.add(new Port(this.pt, this));
		setLocation(x, y);
		parentGroup = null;
		this.setSelected(true);
	}

	@Override
	public void setLocation(Point p) {
		this.setLocation(p.x, p.y);
	}

	@Override
	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		this.pt.setLocation(x, y);
		setBounds(pt.x, pt.y, dim.width, dim.height);
		setPortLocation();
		repaint();
	}

	@Override
	public boolean contains(Point p) {
		return (p.x > pt.x) && (p.y > pt.y) && (p.x < pt.x + dim.width) && (p.y < pt.y + dim.height);
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	private void setPortLocation() {
		// left, top, right, bottom
		int isLeft, isTop;
		for (int i = 0; i < PORT_NUM; ++i) {
			isLeft = i == 0 ? 1 : 0;
			isTop = i == 1 ? 1 : 0;
			ports.get(i).setLocation(pt.x + ((dim.width * (isLeft ^ 1)) >> (i & 1)) - (Port.width * isLeft),
					pt.y + ((dim.height * (isTop ^ 1)) >> (i & 1 ^ 1)) - (Port.height * isTop));
		}
	}

	public void setPortsVisible(boolean isVisible) {
		for (Port port : ports)
			port.setVisible(isVisible);
	}

	public void setPortNumber(int PORT_NUM) {
		this.PORT_NUM = PORT_NUM;
		ports.clear();
		for (int i = 0; i < PORT_NUM; ++i)
			this.ports.add(new Port(this.pt, this));
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		setPortsVisible(selected);
		repaint();
	}

	public boolean isSelected() {
		return selected;
	}

	public void rename(String name) {
		this.name = name;
		repaint();
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
		size.width = fm.stringWidth(name);
		size.height = fm.getHeight();

		return size;
	}
}
