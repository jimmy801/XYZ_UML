package Model.Objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import Model.Base.BasicObject;

/**
 * Class object component
 * 
 * @author Jimmy801
 *
 * @see {@link BasicObject}
 */
public class Class extends BasicObject {
	/**
	 * Width of this component
	 */
	private final static int width = 100;
	/**
	 * Height of this component
	 */
	private final static int height = 90;

	/**
	 * Initial by top-left point and name of component.
	 * 
	 * @param p    - top-left corner
	 * @param name - name of component
	 * 
	 * @see {@link BasicObject}
	 */
	public Class(Point p, String name) {
		super(p, new Dimension(width, height), name);
	}

	/**
	 * Initial by name of component.
	 * 
	 * @param name - name of component
	 * 
	 * @see {@link BasicObject}
	 */
	public Class(String name) {
		this(new Point(), name);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());

		int offset = getHeight() / 3;
		g.setColor(Color.BLACK);
		g.drawLine(0, offset, getWidth(), offset);
		g.drawLine(0, offset * 2, getWidth(), offset * 2);

		Dimension textSize = getTextSize();
		int textW = textSize.width, textH = textSize.height;
		g.setColor(Color.BLACK);
		g.drawString(this.getName(), getWidth() / 2 - textW / 2, getHeight() / 3 - textH / 2);
	}

	@Override
	public void paintBorder(Graphics g) {
		super.paintBorder(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.MAGENTA);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawRect(0, 0, getWidth(), getHeight());
	}
}
