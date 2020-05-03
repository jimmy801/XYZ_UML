package Model.Base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

import javax.swing.JComponent;

/**
 * Port component
 * 
 * @author Jimmy801
 *
 * @see {@link JComponent}
 */
public class Port extends JComponent {
	/**
	 * Connection lines of this port
	 */
	public Vector<Line> lines;
	/**
	 * Width of this component
	 */
	public static final int width = 8;
	/**
	 * Height of this component
	 */
	public static final int height = 8;
	/**
	 * Parent of this component
	 */
	private BasicObject parent;
	/**
	 * Color of this component
	 */
	private Color color;

	/**
	 * Initialize port by top-left corner and parent component
	 * 
	 * @param R      - top-left corner of this component
	 * @param parent - parent component of this component
	 */
	public Port(Point R, BasicObject parent) {
		this(R.x, R.y, parent);
	}

	/**
	 * Initialize port by left, top, and parent component
	 * 
	 * @param x      - left position
	 * @param y      - top position
	 * @param parent - parent component of this component
	 */
	public Port(int x, int y, BasicObject parent) {
		this.parent = parent;
		lines = new Vector<Line>();
		color = Color.BLACK;
		this.setBounds(x, y, width, height);
	}

	/**
	 * Find this port is which position of parent object
	 * 
	 * @return 0 for Left, 1 for Top, 2 for Right, 3 for Bottom
	 */
	private int whichPort() {
		return parent.ports.indexOf(this);
	}

	/**
	 * Point in the part(left, top, right, bottom) of parent is same as position of
	 * this port.
	 * 
	 * @param p - the point must be detected
	 * @return true if position of port is same as the part of parent.
	 * 
	 * @see {@link #whichPort()}
	 */
	public boolean parentContain(Point p) {
		if (!parent.contains(p)) {
			return false;
		}

		// top-left point of parent
		Point tL = new Point(parent.getX(), parent.getY());
		// top-right point of parent
		Point tR = new Point(parent.getX() + parent.getWidth(), parent.getY());
		// bottom-left point of parent
		Point bL = new Point(parent.getX(), parent.getY() + parent.getHeight());
		// bottom-right point of parent
		Point bR = new Point(parent.getX() + parent.getWidth(), parent.getY() + parent.getHeight());

		// delta of 2 points on lines
		Point lineDelta1 = new Point(tL.x - bR.x, tL.y - bR.y);
		Point lineDelta2 = new Point(tR.x - bL.x, tR.y - bL.y);

		// delta of check point and 1 of point on lines
		Point pointDelta1 = new Point(p.x - tL.x, p.y - tL.y);
		Point pointDelta2 = new Point(p.x - tR.x, p.y - tR.y);

		switch (whichPort()) {
		case 0: // left
			return (pointDelta1.x * lineDelta1.y > pointDelta1.y * lineDelta1.x)
					&& (pointDelta2.x * lineDelta2.y > pointDelta2.y * lineDelta2.x);
		case 1: // top
			return (pointDelta1.x * lineDelta1.y < pointDelta1.y * lineDelta1.x)
					&& (pointDelta2.x * lineDelta2.y > pointDelta2.y * lineDelta2.x);
		case 2: // right
			return (pointDelta1.x * lineDelta1.y < pointDelta1.y * lineDelta1.x)
					&& (pointDelta2.x * lineDelta2.y < pointDelta2.y * lineDelta2.x);
		case 3: // bottom
			return (pointDelta1.x * lineDelta1.y > pointDelta1.y * lineDelta1.x)
					&& (pointDelta2.x * lineDelta2.y < pointDelta2.y * lineDelta2.x);
		default:
			return false;
		}
	}
	
	/**
	 * Point is in port or not.
	 * 
	 * @param p - the point must be detected
	 * @return true if the point is in port.
	 */
	public boolean portContain(Point p) {
		return ((p.x > this.getX()) && (p.y > this.getY()) && (p.x < this.getX() + this.getWidth())
				&& (p.y < this.getY() + this.getHeight()));
	}

	@Override
	public boolean contains(Point p) {
		return portContain(p) || parentContain(p);
	}

	@Override
	public BasicObject getParent() {
		return parent;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}

	/**
	 * Add line to this port
	 * 
	 * @param line - added line
	 * 
	 * @see {@link Line}
	 */
	public void addLine(Line line) {
		lines.add(line);
	}

	/**
	 * Set color of this component
	 * 
	 * @param color - set color of this component
	 */
	public void setColor(Color color) {
		this.color = color;
	}
}
