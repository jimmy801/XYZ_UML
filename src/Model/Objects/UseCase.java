package Model.Objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import Model.Base.BasicObject;

/**
 * Use Case object component
 * 
 * @author Jimmy801
 *
 * @see {@link BasicObject}
 */
public class UseCase extends BasicObject {
	/**
	 * Width of this component
	 */
	private final static int width = 120;
	/**
	 * Height of this component
	 */
	private final static int height = 70;

	/**
	 * Initial by top-left point and name of component.
	 * 
	 * @param p    - top-left corner
	 * @param name - name of component
	 * 
	 * @see {@link BasicObject}
	 */
	public UseCase(Point p, String str) {
		super(p, new Dimension(width, height), str);
	}

	/**
	 * Initial by name of component.
	 * 
	 * @param name - name of component
	 * 
	 * @see {@link BasicObject}
	 */
	public UseCase(String str) {
		this(new Point(), str);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.LIGHT_GRAY);
		g.fillOval(0, 0, getWidth(), getHeight());

		Dimension textSize = getTextSize();
		int textW = textSize.width, textH = textSize.height;
		g.setColor(Color.BLACK);
		g.drawString(this.getName(), (getWidth() - textW) / 2, (getHeight() + textH) / 2);
	}

	@Override
	public void paintBorder(Graphics g) {
		super.paintBorder(g);
		g.setColor(selected? Color.MAGENTA : Color.BLACK);
		g.drawOval(0, 0, getWidth(), getHeight());
	}
}
