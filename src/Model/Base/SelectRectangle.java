package Model.Base;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import javax.swing.JComponent;

public class SelectRectangle extends JComponent {
	public Point pt;
	public Dimension dim;

	public SelectRectangle() {
		this(new Point());
	}

	public SelectRectangle(Point pt) {
		this.pt = pt;
		dim = new Dimension();
	}

	@Override
	public boolean contains(Point p) {
		return (p.x > pt.x) && (p.y > pt.y) && (p.x < pt.x + dim.width) && (p.y < pt.y + dim.height);
	}

	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);

		g2.setPaint(Color.GRAY);
		g2.setStroke(dashed);
		g2.drawRect(0, 0, dim.width, dim.height);
	}

	@Override
	public void setLocation(Point p) {
		this.setLocation(p.x, p.y);
	}

	@Override
	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		this.pt.setLocation(x, y);
		setBounds(x, y, dim.width, dim.height);
		repaint();
	}
}
