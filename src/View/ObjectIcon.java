package View;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Icon;

import Model.Base.Line;
import Model.Objects.Class;
import Model.Objects.UseCase;
import Utils.MODE;
import Utils.PaintMethods;

/**
 * Icons of buttons
 * 
 * @author Jimmy801
 *
 * @see {@link Icon}
 */
public class ObjectIcon implements Icon {
	/**
	 * MODE of this icon
	 */
	private MODE mode;
	
	/**
	 * Use Adapter pattern (or a variation) to create pointer to function
	 */
	private Map<MODE, PaintMethod> paintMethod = new HashMap<>();

	ObjectIcon(MODE mode) {
		this.mode = mode;
		
		// paint function pointers
		PaintMethod selesctPaint = new PaintMethod() {
			public void paint(Component c, Graphics g, int x, int y) {
				Image image = null;
				int cW = c.getWidth(), cH = c.getHeight();
				int w = 50;
				int h = 50;
				try {
					File imgFile = new File("resources/cursor.png");
					image = ImageIO.read(imgFile);
				} catch (Exception ex) {
					System.out.println("No cursor.png!!");
				}
				int imgX = (cW - w) / 2, imgY = (cH - h) / 2;
				g.drawImage(image, imgX, imgY, w, h, null);
			}
		};
		PaintMethod associationLinePaint = new PaintMethod() {
			public void paint(Component c, Graphics g, int x, int y) {
				int cW = c.getWidth(), cH = c.getHeight();
				PaintMethods.paintAssociationLine(g, 
						new Point(x, cH / 2), new Point(cW - x, cH / 2), 
						Line.getCrossLen(),
						Line.getNormalLen());
			}
		};
		PaintMethod generalizationLinePaint = new PaintMethod() {
			public void paint(Component c, Graphics g, int x, int y) {
				int cW = c.getWidth(), cH = c.getHeight();
				PaintMethods.paintGeneralizationLine(g, 
						new Point(x, cH / 2), new Point(cW - x, cH / 2), 
						Line.getCrossLen(),
						Line.getNormalLen());
			}
		};
		PaintMethod compositionLinePaint = new PaintMethod() {
			public void paint(Component c, Graphics g, int x, int y) {
				int cW = c.getWidth(), cH = c.getHeight();
				PaintMethods.paintCompositionLine(g, 
						new Point(x, cH / 2), new Point(cW - x, cH / 2), 
						Line.getCrossLen(),
						Line.getNormalLen());
			}
		};
		PaintMethod classPaint = new PaintMethod() {
			public void paint(Component c, Graphics g, int x, int y) {
				Dimension textSize = getTextSize(g, c.getFont(), mode.getTitle());
				int cW = c.getWidth(), cH = c.getHeight();
				int w = Class.width - Class.border * 2;
				int h = Class.height - Class.border * 2;
				x = (cW - Class.width + Class.border) / 2;
				y = (cH - Class.height + Class.border) / 2;
				PaintMethods.paintClass(g, x, y, w, h, mode.getTitle(), textSize.width, textSize.height);
				PaintMethods.paintClassBoard(g, x, y, w, h, Class.border);
			}
		};
		PaintMethod usecasePaint = new PaintMethod() {
			public void paint(Component c, Graphics g, int x, int y) {
				Dimension textSize = getTextSize(g, c.getFont(), mode.getTitle());
				int cW = c.getWidth(), cH = c.getHeight();
				int w = UseCase.width - UseCase.border * 2;
				int h = UseCase.height - UseCase.border * 2;
				x = (cW - UseCase.width + UseCase.border) / 2;
				y = (cH - UseCase.height + UseCase.border) / 2;
				PaintMethods.paintUseCase(g, x, y, w, h, mode.getTitle(), textSize.width, textSize.height);
				PaintMethods.paintUseCaseBoard(g, x, y, w, h, Class.border);

			}
		};

		paintMethod.put(MODE.SELECT, selesctPaint);
		paintMethod.put(MODE.ASSOCIATION, associationLinePaint);
		paintMethod.put(MODE.GENERALIZATION, generalizationLinePaint);
		paintMethod.put(MODE.COMPOSITION, compositionLinePaint);
		paintMethod.put(MODE.CLASS, classPaint);
		paintMethod.put(MODE.USECASE, usecasePaint);
	}
	
	interface PaintMethod {
		void paint(Component c, Graphics g, int x, int y);
	}

	@Override
	public int getIconWidth() {
		return 180;
	}

	@Override
	public int getIconHeight() {
		return 90;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		paintMethod.get(mode).paint(c, g, x, y);
	}

	/**
	 * Calculate the dimension of name.
	 * 
	 * @param g    - painted Graphics
	 * @param font - font of text
	 * @param str  - text
	 * @return {@link Dimension} dimension of text
	 */
	private Dimension getTextSize(Graphics g, Font font, String str) {
		Dimension size = new Dimension();
		FontMetrics fm = g.getFontMetrics(font);
		size.width = fm.stringWidth(str);
		size.height = fm.getHeight();

		return size;
	}
}
