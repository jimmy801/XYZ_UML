package Model.Objects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import Model.Base.BasicObject;
import Utils.PaintMethods;

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
	public final static int width = 100;
	/**
	 * Height of this component
	 */
	public final static int height = 90;
	/**
	 * Size of border
	 */
	public final static int border = 2;

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

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension textSize = getTextSize();
		PaintMethods.paintClass(g, border, border, this.getWidth() - 2 * border, this.getHeight() - 2 * border,
				this.getName(), textSize.width, textSize.height);
	}

	@Override
	public void paintBorder(Graphics g) {
		super.paintBorder(g);
		PaintMethods.paintClassBoard(g, selected, border, border, this.getWidth() - 2 * border,
				this.getHeight() - 2 * border, border);
	}
}
