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

/**
 * Group object component
 * 
 * @author Jimmy801
 *
 * @see {@link BasicObject}
 */
public class Group extends BasicObject {
	/**
	 * Children of this group
	 */
	private Vector<BasicObject> children;
	/**
	 * Top-left corner of this component
	 */
	private Point p;
	/**
	 * Dimension of this component
	 */
	private Dimension dim;

	public Group() {
		this(new Point(), new Dimension());
	}

	/**
	 * Initial by top-left point, width, height, and name of component.
	 * 
	 * @param p   - top-left corner point of this component
	 * @param dim - dimension of this component
	 */
	public Group(Point p, Dimension dim) {
		this.p = p;
		this.dim = dim;
		children = new Vector<>();
		this.ports = new Vector<>();
		setPortNumber(0);
		this.setSelected(true);
	}

	/**
	 * Initial by children of this group
	 * 
	 * @param components - array of children
	 * 
	 * @see {@link BasicObject}
	 */
	public Group(BasicObject... components) {
		p = new Point(components[0].getLocation());
		dim = new Dimension(components[0].getSize());
		this.ports = new Vector<>();
		setPortNumber(0);
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
		int Xoffset = x - p.x;
		int Yoffset = y - p.y;
		for (BasicObject com : children)
			com.setLocation(com.getX() + Xoffset, com.getY() + Yoffset);
		this.p = new Point(x, y);
		setBounds(x, y, dim.width, dim.height);
		repaint();
	}

	@Override
	public void paintBorder(Graphics g) {
		super.paintBorder(g);
		Graphics2D g2d = (Graphics2D) g;
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
		g2d.setColor(selected ? new Color(72, 72, 145) : new Color(77, 255, 255));
		g2d.setStroke(dashed);
		g2d.drawRect(0, 0, this.getWidth(), this.getHeight());
	}

	/**
	 * Add children to this group
	 * 
	 * @param objs - array new children
	 * 
	 * @see {@link BasicObject}
	 */
	public void addAll(BasicObject... objs) {
		for (BasicObject obj : objs)
			addChild(obj);
	}

	/**
	 * Add children to this group
	 * 
	 * @param objs - {@link Vector} array of new children
	 * 
	 * @see {@link BasicObject}
	 */
	public void addAll(Vector<BasicObject> objs) {
		this.addAll(objs.toArray(new BasicObject[0]));
	}

	/**
	 * Get children of this group
	 * 
	 * @return children of this group
	 * 
	 * @see {@link BasicObject}
	 */
	public Vector<BasicObject> getChildren() {
		return this.children;
	}

	/**
	 * Add a child to this group
	 * 
	 * @param idx - add child position
	 * @param c   - child component
	 */
	public void addChild(int idx, BasicObject c) {
		int cLeft = c.getX(); // left position of added child
		int cTop = c.getY(); // top position of added child
		int cRight = cLeft + c.getWidth(); // right position of added child
		int cBottom = cTop + c.getHeight(); // bottom position of added child
		int right = p.x + dim.width; // group new right
		int bottom = children.isEmpty() ? 0 : p.y + dim.height; // group new bottom
		if (children.isEmpty()) {
			p.x = cLeft;
			p.y = cTop;
		} else {
			p.x = cLeft < p.x ? cLeft : p.x;
			p.y = cTop < p.y ? cTop : p.y;
		}
		dim.height = (cBottom > bottom ? cBottom : bottom) - p.y;
		dim.width = (cRight > right ? cRight : right) - p.x;
		children.add(idx, c);
		setLocation(p);
	}

	/**
	 * Add a child to this group<br>
	 * Default add to end.
	 * 
	 * @param c - child component
	 */
	public void addChild(BasicObject c) {
		this.addChild(children.size(), c);
	}
}
