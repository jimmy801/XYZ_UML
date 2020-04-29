package View;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;

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

	ObjectIcon(MODE mode) {
		this.mode = mode;
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
		int cW = c.getWidth(), cH = c.getHeight();
		int w, h;
		Dimension textSize;
		switch (mode) {
		case SELECT:
			Image image = null;
			w = 50;
			h = 50;
			try {
				File imgFile = new File("resources/cursor.png");
				image = ImageIO.read(imgFile);
			} catch (Exception ex) {
				System.out.println("No cursor.png!!");
			}
			int imgX = (cW - w) / 2, imgY = (cH - h) / 2;
			g.drawImage(image, imgX, imgY, w, h, null);
			break;
		case ASSOCIATION:
			PaintMethods.paintAssociationLine(g, new Point(x, cH / 2), new Point(cW - x, cH / 2), Line.getCrossLen(),
					Line.getNormalLen());
			break;
		case GENERALIZATION:
			PaintMethods.paintGeneralizationLine(g, new Point(x, cH / 2), new Point(cW - x, cH / 2), Line.getCrossLen(),
					Line.getNormalLen());
			break;
		case COMPOSITION:
			PaintMethods.paintCompositionLine(g, new Point(x, cH / 2), new Point(cW - x, cH / 2), Line.getCrossLen(),
					Line.getNormalLen());
			break;
		case CLASS:
			textSize = getTextSize(g, c.getFont(), mode.getTitle());
			x = (cW - Class.width + Class.border) / 2;
			y = (cH - Class.height + Class.border) / 2;
			w = Class.width - Class.border * 2;
			h = Class.height - Class.border * 2;
			PaintMethods.paintClass(g, x, y, w, h, mode.getTitle(), textSize.width, textSize.height);
			PaintMethods.paintClassBoard(g, x, y, w, h, Class.border);
			break;
		case USECASE:
			textSize = getTextSize(g, c.getFont(), mode.getTitle());
			x = (cW - UseCase.width + UseCase.border) / 2;
			y = (cH - UseCase.height + UseCase.border) / 2;
			w = UseCase.width - UseCase.border * 2;
			h = UseCase.height - UseCase.border * 2;
			PaintMethods.paintUseCase(g, x, y, w, h, mode.getTitle(), textSize.width, textSize.height);
			PaintMethods.paintUseCaseBoard(g, x, y, w, h, Class.border);
			break;
		default:
			break;
		}
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
