package Utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

/**
 * Component painting methods
 * 
 * @author Jimmy801
 *
 */
public class PaintMethods {

	/**
	 * Help function, help to calculate the cross point of an arrow line
	 * 
	 * @param srcPt     - start point of line
	 * @param dstPt     - end point of line
	 * @param dis       - distance of start point and end point
	 * @param CROSS_LEN - distance of line arrow vertex on line
	 * @return the cross point of an arrow line
	 * 
	 * @see {@link Model.Base.Line}
	 */
	private static Point calCrossPoint(Point srcPt, Point dstPt, int dis, int CROSS_LEN) {
		return new Point(dstPt.x + Math.round(CROSS_LEN * (srcPt.x - dstPt.x) / dis),
				dstPt.y + Math.round(CROSS_LEN * (srcPt.y - dstPt.y) / dis));
	}

	/**
	 * Help function, help to calculate the normal length of an arrow line
	 * 
	 * @param srcPt      - start point of line
	 * @param dstPt      - end point of line
	 * @param dis        - distance of start point and end point
	 * @param NORMAL_LEN - distance of line and arrow tail
	 * @return the normal length of an arrow line
	 * 
	 * @see {@link Model.Base.Line}
	 */
	private static Point calNormalLen(Point srcPt, Point dstPt, int dis, int NORMAL_LEN) {
		return new Point(-Math.round(NORMAL_LEN * (dstPt.y - srcPt.y) / dis),
				Math.round(NORMAL_LEN * (dstPt.x - srcPt.x) / dis));
	}

	/**
	 * Paint an association line
	 * 
	 * @param g          - painted Graphics
	 * @param srcPt      - start point of line
	 * @param dstPt      - end point of line
	 * @param CROSS_LEN  - distance of line arrow vertex on line
	 * @param NORMAL_LEN - distance of line and arrow tail
	 * 
	 * @see {@link Model.Lines.AssociationLine}
	 */
	public static void paintAssociationLine(Graphics g, Point srcPt, Point dstPt, int CROSS_LEN, int NORMAL_LEN) {
		g.setColor(Color.BLACK);

		int dis = (int) srcPt.distance(dstPt);
		if (dis <= 0) {
			return;
		}

		Point crossPoint = calCrossPoint(srcPt, dstPt, dis, CROSS_LEN);
		Point normalLen = calNormalLen(srcPt, dstPt, dis, NORMAL_LEN);

		g.drawLine(srcPt.x, srcPt.y, dstPt.x, dstPt.y);

		g.drawLine(dstPt.x, dstPt.y, crossPoint.x + normalLen.x, crossPoint.y + normalLen.y);
		g.drawLine(dstPt.x, dstPt.y, crossPoint.x - normalLen.x, crossPoint.y - normalLen.y);
	}

	/**
	 * Paint a composition line
	 * 
	 * @param g          - painted Graphics
	 * @param srcPt      - start point of line
	 * @param dstPt      - end point of line
	 * @param CROSS_LEN  - distance of line arrow vertex on line
	 * @param NORMAL_LEN - distance of line and arrow tail
	 * 
	 * @see {@link Model.Lines.CompositionLine}
	 */
	public static void paintCompositionLine(Graphics g, Point srcPt, Point dstPt, int CROSS_LEN, int NORMAL_LEN) {
		g.setColor(Color.BLACK);

		int dis = (int) srcPt.distance(dstPt);
		if (dis == 0) {
			return;
		}

		g.drawLine(srcPt.x, srcPt.y, dstPt.x, dstPt.y);

		// draw diamond
		Point diamondCrossPoint = calCrossPoint(dstPt, srcPt, dis, CROSS_LEN);
		Point diamondTail = calCrossPoint(dstPt, srcPt, dis, CROSS_LEN * 2);
		Point diamondNormalLen = calNormalLen(dstPt, srcPt, dis, NORMAL_LEN);

		int x[] = { srcPt.x, diamondCrossPoint.x + diamondNormalLen.x, diamondTail.x,
				diamondCrossPoint.x - diamondNormalLen.x };
		int y[] = { srcPt.y, diamondCrossPoint.y + diamondNormalLen.y, diamondTail.y,
				diamondCrossPoint.y - diamondNormalLen.y };
		g.fillPolygon(x, y, 4);

		// draw arrow
		Point arrowCrossPoint = calCrossPoint(srcPt, dstPt, dis, CROSS_LEN);
		Point arrowNormalLen = calNormalLen(srcPt, dstPt, dis, NORMAL_LEN);

		g.drawLine(dstPt.x, dstPt.y, arrowCrossPoint.x + arrowNormalLen.x, arrowCrossPoint.y + arrowNormalLen.y);
		g.drawLine(dstPt.x, dstPt.y, arrowCrossPoint.x - arrowNormalLen.x, arrowCrossPoint.y - arrowNormalLen.y);
	}

	/**
	 * Paint a generalization line
	 * 
	 * @param g          - painted Graphics
	 * @param srcPt      - start point of line
	 * @param dstPt      - end point of line
	 * @param CROSS_LEN  - distance of line arrow vertex on line
	 * @param NORMAL_LEN - distance of line and arrow tail
	 * 
	 * @see {@link Model.Lines.GeneralizationLine}
	 */
	public static void paintGeneralizationLine(Graphics g, Point srcPt, Point dstPt, int CROSS_LEN, int NORMAL_LEN) {
		g.setColor(Color.BLACK);

		int dis = (int) srcPt.distance(dstPt);
		if (dis <= 0) {
			return;
		}

		Point crossPoint = calCrossPoint(srcPt, dstPt, dis, CROSS_LEN);
		Point normalLen = calNormalLen(srcPt, dstPt, dis, NORMAL_LEN);

		g.drawLine(srcPt.x, srcPt.y, crossPoint.x, crossPoint.y);

		int x[] = { dstPt.x, crossPoint.x + normalLen.x, crossPoint.x - normalLen.x };
		int y[] = { dstPt.y, crossPoint.y + normalLen.y, crossPoint.y - normalLen.y };
		g.drawPolygon(x, y, 3);
	}

	/**
	 * Paint a class component.<br>
	 * Default top-left corner is (0, 0)
	 * 
	 * @param g     - painted Graphics
	 * @param w     - width of class component
	 * @param h     - height of class component
	 * @param name  - name of class component
	 * @param textW - width of text
	 * @param textH - height of text
	 * 
	 * @see {@link Model.Objects.Class}
	 */
	public static void paintClass(Graphics g, int w, int h, String name, int textW, int textH) {
		paintClass(g, 0, 0, w, h, name, textW, textH);
	}

	/**
	 * Paint a class component
	 * 
	 * @param g     - painted Graphics
	 * @param x     - left position of class component
	 * @param y     - top position of class component
	 * @param w     - width of class component
	 * @param h     - height of class component
	 * @param name  - name of class component
	 * @param textW - width of text
	 * @param textH - height of text
	 * 
	 * @see {@link Model.Objects.Class}
	 */
	public static void paintClass(Graphics g, int x, int y, int w, int h, String name, int textW, int textH) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x, y, w, h);

		int offset = h / 3;
		g.setColor(Color.BLACK);
		g.drawLine(x, y + offset, x + w, y + offset);
		g.drawLine(x, y + offset * 2, x + w, y + offset * 2);

		g.setColor(Color.BLACK);
		g.drawString(name, x + w / 2 - textW / 2, y + h / 3 - textH / 2);
	}

	/**
	 * Paint border of a class component
	 * 
	 * @param g        - painted Graphics
	 * @param selected - is class component selected or not
	 * @param x        - left position of class component
	 * @param y        - top position of class component
	 * @param w        - width of class component
	 * @param h        - height of class component
	 * @param border   - size of border
	 * 
	 * @see {@link Model.Objects.Class}
	 */
	public static void paintClassBoard(Graphics g, boolean selected, int x, int y, int w, int h, int border) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(selected ? Color.MAGENTA : Color.BLACK);
		g2d.setStroke(new BasicStroke(border));
		g2d.drawRect(x, y, w, h);
		g2d.dispose();
	}

	/**
	 * Paint border of a class component.<br>
	 * Default selected of component is false.
	 * 
	 * @param g      - painted Graphics
	 * @param x      - left position of class component
	 * @param y      - top position of class component
	 * @param w      - width of class component
	 * @param h      - height of class component
	 * @param border - size of border
	 * 
	 * @see {@link Model.Objects.Class}
	 */
	public static void paintClassBoard(Graphics g, int x, int y, int w, int h, int border) {
		paintClassBoard(g, false, x, y, w, h, border);
	}

	/**
	 * Paint a use case component.<br>
	 * Default top-left corner is (0, 0)
	 * 
	 * @param g     - painted Graphics
	 * @param w     - width of use case component
	 * @param h     - height of use case component
	 * @param name  - name of use case component
	 * @param textW - width of text
	 * @param textH - height of text
	 * 
	 * @see {@link Model.Objects.UseCase}
	 */
	public static void paintUseCase(Graphics g, int w, int h, String name, int textW, int textH) {
		paintUseCase(g, 0, 0, w, h, name, textW, textH);
	}

	/**
	 * Paint a use case component
	 * 
	 * @param g     - painted Graphics
	 * @param x     - left position of use case component
	 * @param y     - top position of use case component
	 * @param w     - width of use case component
	 * @param h     - height of use case component
	 * @param name  - name of use case component
	 * @param textW - width of text
	 * @param textH - height of text
	 * 
	 * @see {@link Model.Objects.UseCase}
	 */
	public static void paintUseCase(Graphics g, int x, int y, int w, int h, String name, int textW, int textH) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillOval(x, y, w, h);

		g.setColor(Color.BLACK);
		g.drawString(name, x + (w - textW) / 2, y + (h + textH) / 2);
	}

	/**
	 * Paint border of a use case component
	 * 
	 * @param g        - painted Graphics
	 * @param selected - is use case component selected or not
	 * @param x        - left position of use case component
	 * @param y        - top position of use case component
	 * @param w        - width of use case component
	 * @param h        - height of use case component
	 * @param border   - size of border
	 * 
	 * @see {@link Model.Objects.UseCase}
	 */
	public static void paintUseCaseBoard(Graphics g, boolean selected, int x, int y, int w, int h, int border) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(selected ? Color.MAGENTA : Color.BLACK);
		g2d.setStroke(new BasicStroke(border));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // make border smooth
		g2d.drawOval(x, y, w, h);
		g2d.dispose();
	}

	/**
	 * Paint border of a use case component.<br>
	 * Default selected of component is false.
	 * 
	 * @param g      - painted Graphics
	 * @param x      - left position of use case component
	 * @param y      - top position of use case component
	 * @param w      - width of use case component
	 * @param h      - height of use case component
	 * @param border - size of border
	 * 
	 * @see {@link Model.Objects.UseCase}
	 */
	public static void paintUseCaseBoard(Graphics g, int x, int y, int w, int h, int border) {
		paintUseCaseBoard(g, false, x, y, w, h, border);
	}
}
