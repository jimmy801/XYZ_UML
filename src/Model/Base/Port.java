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
	public Vector<Line> lines;
	public static int width = 5;
	public static int height = 5;
	private BasicObject parent;
	private Color color;

	public Port(Point R, BasicObject parent) {
		this(R.x, R.y, parent);
	}

	public Port(int x, int y, BasicObject parent) {
		this.parent = parent;
		lines = new Vector<Line>();
		color = Color.BLACK;
		this.setBounds(x, y, width, height);
	}

	@Override
	public boolean contains(Point p) {
		return (p.x > this.getX()) && (p.y > this.getY()) 
				&& (p.x < this.getX() + this.getWidth()) && (p.y < this.getY() + this.getHeight());
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

	public void addLine(Line line) {
		lines.add(line);
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
