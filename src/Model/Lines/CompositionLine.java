package Model.Lines;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import Model.Base.Line;
import Model.Base.Port;

/**
 * Composition line component
 * 
 * @author Jimmy801
 *
 * @see {@link Line}
 */
public class CompositionLine extends Line {
	/**
	 * Initialize by source port and destination port
	 * 
	 * @param src - source port
	 * @param dst - destination port
	 * 
	 * @see {@link Line}
	 */
	public CompositionLine(Port src, Port dst) {
		super(src, dst);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);

		Point srcPt = src.getLocation();
		Point dstPt = dst.getLocation();

		int dis = (int) srcPt.distance(dstPt);
		if (dis == 0) {
			return;
		}

		g.drawLine(srcPt.x, srcPt.y, dstPt.x, dstPt.y);

		// draw diamond
		Point diamondCrossPoint = new Point(srcPt.x + Math.round(CROSS_LEN * (dstPt.x - srcPt.x) / dis),
				srcPt.y + Math.round(CROSS_LEN * (dstPt.y - srcPt.y) / dis));
		Point diamondTail = new Point(srcPt.x + Math.round(CROSS_LEN * 2 * (dstPt.x - srcPt.x) / dis),
				srcPt.y + Math.round(CROSS_LEN * 2 * (dstPt.y - srcPt.y) / dis));
		Point diamondNormalLen = new Point(-Math.round(NORMAL_LEN * (srcPt.y - dstPt.y) / dis),
				Math.round(NORMAL_LEN * (srcPt.x - dstPt.x) / dis));

		int x[] = { srcPt.x, diamondCrossPoint.x + diamondNormalLen.x, diamondTail.x, diamondCrossPoint.x - diamondNormalLen.x };
		int y[] = { srcPt.y, diamondCrossPoint.y + diamondNormalLen.y, diamondTail.y, diamondCrossPoint.y - diamondNormalLen.y };
		g.fillPolygon(x, y, 4);
		
		// draw arrow
		Point arrowCrossPoint = new Point(dstPt.x + Math.round(CROSS_LEN * (srcPt.x - dstPt.x) / dis),
				dstPt.y + Math.round(CROSS_LEN * (srcPt.y - dstPt.y) / dis));
		Point arrowNormalLen = new Point(-Math.round(NORMAL_LEN * (dstPt.y - srcPt.y) / dis),
				Math.round(NORMAL_LEN * (dstPt.x - srcPt.x) / dis));

		g.drawLine(dstPt.x, dstPt.y, arrowCrossPoint.x + arrowNormalLen.x, arrowCrossPoint.y + arrowNormalLen.y);
		g.drawLine(dstPt.x, dstPt.y, arrowCrossPoint.x - arrowNormalLen.x, arrowCrossPoint.y - arrowNormalLen.y);
	}
}
