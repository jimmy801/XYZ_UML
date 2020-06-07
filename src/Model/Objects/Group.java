package Model.Objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.Vector;

import Model.Base.Shape;

/**
 * Group object component
 * 
 * @author Jimmy801
 *
 * @see {@link Shape}
 */
public class Group extends Shape {
	/**
	 * Children of this group
	 */
	private Vector<Shape> children;
	/**
	 * Top-left corner of this component
	 */
	private Point p;
	/**
	 * Dimension of this component
	 */
	private Dimension dim;

	public Group() {
		this.p = new Point();
		this.dim = new Dimension();
		children = new Vector<>();
		this.setSelected(true);
	}

	@Override
	public void setLocation(int x, int y) {
		int Xoffset = x - p.x;
		int Yoffset = y - p.y;
		for (Shape com : children) {
			com.setLocation(com.getX() + Xoffset, com.getY() + Yoffset);
		}
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
		g2d.dispose();
	}

	@Override
	public boolean contains(Point p) {
		return (p.x > this.getX()) && (p.y > this.getY()) && (p.x < this.getX() + this.getWidth())
				&& (p.y < this.getY() + this.getHeight());
	}

	/**
	 * Add children to this group
	 * 
	 * @param objs - array new children
	 * 
	 * @see {@link Shape}
	 */
	public void addAll(Shape... objs) {
		for (Shape obj : objs)
			addChild(obj);
	}

	/**
	 * Get children of this group
	 * 
	 * @return children of this group
	 * 
	 * @see {@link Shape}
	 */
	public Vector<Shape> getChildren() {
		return this.children;
	}

	/**
	 * Add a child to this group
	 * 
	 * @param idx - add child position
	 * @param c   - child component
	 */
	public void addChild(int idx, Shape c) {
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
	public void addChild(Shape c) {
		this.addChild(children.size(), c);
	}
}
