package Model.Base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

import javax.swing.JComponent;

public class Port extends JComponent {
	public Point R;
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
		setVisible(false);
		setLocation(x, y);
		color = Color.BLACK;
	}

	@Override
	public boolean contains(Point p) {
		return (p.x > R.x) && (p.y > R.y) && (p.x < R.x + width) && (p.y < R.y + height);
	}

	@Override
	public void setLocation(Point p) {
		setLocation(p.x, p.y);
	}

	@Override
	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		R = new Point(x, y);
		setBounds(R.x, R.y, width, height);
		repaint();
	}

	@Override
	public BasicObject getParent() {
		return parent;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color);
		g.fillRect(0, 0, width, height);
	}

	public void addLine(Line line) {
		lines.add(line);
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
