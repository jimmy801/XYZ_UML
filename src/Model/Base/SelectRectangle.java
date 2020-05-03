package Model.Base;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import javax.swing.JComponent;

/**
 * The rectangle of select mode
 * 
 * @author Jimmy801
 *
 * @see {@link JComponet}
 */
public class SelectRectangle extends JComponent {
	public SelectRectangle() {
		this(new Point());
	}

	/**
	 * Initialize component by top-left corner
	 * 
	 * @param p - top-left corner
	 */
	public SelectRectangle(Point p) {
		this.setLocation(p);
	}

	@Override
	public boolean contains(Point p) {
		return (p.x > this.getX()) && (p.y > this.getY()) && (p.x < this.getX() + this.getWidth())
				&& (p.y < this.getY() + this.getHeight());
	}

	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);

		g2d.setPaint(Color.GRAY);
		g2d.setStroke(dashed);
		g2d.drawRect(0, 0, this.getWidth(), this.getHeight());
		g2d.dispose();
	}
}
