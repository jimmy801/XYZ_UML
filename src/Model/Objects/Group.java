package Model.Objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.Vector;

import Model.Base.BasicObject;

public class Group extends BasicObject {
	private Vector<BasicObject> children;

	public Group() {
		this(new Point(), new Dimension());
	}

	public Group(Point pt, Dimension dim) {
		this.pt = pt;
		this.dim = dim;
		this.parentGroup = null;
		children = new Vector<>();
		this.ports = new Vector<>();
		setPortNumber(0);
		this.setSelected(true);
	}

	public Group(BasicObject... components) {
		pt = new Point(components[0].getLocation());
		dim = new Dimension(components[0].getSize());
		this.ports = new Vector<>();
		setPortNumber(0);
		this.parentGroup = null;
		children = new Vector<>();
		addAll(components);
		this.setSelected(true);
	}

	@Override
	public void setLocation(Point p) {
		this.setLocation(p.x, p.y);
	}

	@Override
	public void setLocation(int x, int y) {
		int Xoffset = x - pt.x;
		int Yoffset = y - pt.y;
		for (BasicObject com : children)
			com.setLocation(com.getX() + Xoffset, com.getY() + Yoffset);
		this.pt = new Point(x, y);
		setBounds(x, y, dim.width, dim.height);
		repaint();
	}

	@Override
	public void paintBorder(Graphics g) {
		super.paintBorder(g);
		Graphics2D g2d = (Graphics2D) g;
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
		g2d.setColor(selected ? Color.RED : Color.BLUE);
		g2d.setStroke(dashed);
		g2d.drawRect(0, 0, this.getWidth(), this.getHeight());
	}

	public void addAll(BasicObject... objs) {
		for (BasicObject obj : objs)
			addChild(obj);
	}

	public void addAll(Vector<BasicObject> objs) {
		this.addAll(objs.toArray(new BasicObject[0]));
	}

	public Vector<BasicObject> getChildren() {
		return this.children;
	}

	public void addChild(int idx, BasicObject c) {
		int cLeft = c.getX();
		int cTop = c.getY();
		int cRight = cLeft + c.getWidth();
		int cBottom = cTop + c.getHeight();
		int right = pt.x + dim.width;
		int bottom = children.isEmpty() ? 0 : pt.y + dim.height;
		if (children.isEmpty()) {
			pt.x = cLeft;
			pt.y = cTop;
		} else {
			pt.x = cLeft < pt.x ? cLeft : pt.x;
			pt.y = cTop < pt.y ? cTop : pt.y;
		}
		dim.height = (cBottom > bottom ? cBottom : bottom) - pt.y;
		dim.width = (cRight > right ? cRight : right) - pt.x;
		children.add(idx, c);
		c.setParentGroup(this);
		setLocation(pt);
	}

	public void addChild(BasicObject c) {
		this.addChild(children.size(), c);
	}
}
