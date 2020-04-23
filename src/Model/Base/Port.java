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
	
	private int whichPort() {
		return parent.ports.indexOf(this);
	}
	
	private boolean parentContain(Point p) {
		if(!parent.contains(p)) {
			return false;
		}
		Point tL = new Point(parent.getX(), parent.getY());
		Point tR = new Point(parent.getX() + parent.getWidth(), parent.getY());
		Point bL = new Point(parent.getX(), parent.getY() + parent.getHeight());
		Point bR = new Point(parent.getX() + parent.getWidth(), parent.getY() + parent.getHeight());
		
		Point dis1 = new Point(tL.x - bR.x, tL.y - bR.y);
		Point dis2 = new Point(tR.x - bL.x, tR.y - bL.y);
		
		switch(whichPort()) {
		case 0: // left
			return ((p.getX() - tL.getX()) / dis1.getX() > (p.getY() - tL.getY()) / dis1.getY()) && 
					((p.getX() - tR.getX()) / dis2.getX() < (p.getY() - tR.getY()) / dis2.getY());
		case 1: // top
			return ((p.getX() - tL.getX()) / dis1.getX() < (p.getY() - tL.getY()) / dis1.getY()) && 
					((p.getX() - tR.getX()) / dis2.getX() < (p.getY() - tR.getY()) / dis2.getY());
		case 2: // right
			return ((p.getX() - tL.getX()) / dis1.getX() < (p.getY() - tL.getY()) / dis1.getY()) && 
					((p.getX() - tR.getX()) / dis2.getX() > (p.getY() - tR.getY()) / dis2.getY());
		case 3: // bottom
			return ((p.getX() - tL.getX()) / dis1.getX() > (p.getY() - tL.getY()) / dis1.getY()) && 
					((p.getX() - tR.getX()) / dis2.getX() > (p.getY() - tR.getY()) / dis2.getY());
		}
		return false;
	}

	@Override
	public boolean contains(Point p) {
		return ((p.x > this.getX()) && (p.y > this.getY()) && (p.x < this.getX() + this.getWidth())
				&& (p.y < this.getY() + this.getHeight())) || parentContain(p);
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
