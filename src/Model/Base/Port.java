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
	public static final int width = 5;
	/**
	 * Height of this component
	 */
	public static final int height = 5;
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

	@Override
	public boolean contains(Point p) {
		return (p.x > this.getX()) && (p.y > this.getY()) && (p.x < this.getX() + this.getWidth())
				&& (p.y < this.getY() + this.getHeight());
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
