package Model.Objects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import Model.Base.BasicObject;
import Utils.PaintMethods;

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
	public final static int width = 120;
	/**
	 * Height of this component
	 */
	public final static int height = 70;
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
		Dimension textSize = getTextSize();
		PaintMethods.paintUseCase(g, border, border, this.getWidth() - border * 2, this.getHeight() - border * 2,
				this.getName(), textSize.width, textSize.height);
	}

	@Override
	public void paintBorder(Graphics g) {
		super.paintBorder(g);
		PaintMethods.paintUseCaseBoard(g, selected, border, border, this.getWidth() - border * 2,
				this.getHeight() - border * 2, border);
	}
}
