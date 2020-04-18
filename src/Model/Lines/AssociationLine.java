package Model.Lines;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import Model.Base.Line;
import Model.Base.Port;

/**
 * Association line component
 * 
 * @author Jimmy801
 *
 * @see {@link Line}
 */
public class AssociationLine extends Line {
	/**
	 * Initialize by source port and destination port
	 * 
	 * @param src - source port
	 * @param dst - destination port
	 * 
	 * @see {@link Line}
	 */
	public AssociationLine(Port src, Port dst) {
		super(src, dst);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		Point srcPt = src.getLocation();
		Point dstPt = dst.getLocation();

		int dis = (int) srcPt.distance(dstPt);
		if (dis <= 0) {
			return;
		}

		Point crossPoint = new Point(dstPt.x + Math.round(CROSS_LEN * (srcPt.x - dstPt.x) / dis),
				dstPt.y + Math.round(CROSS_LEN * (srcPt.y - dstPt.y) / dis));
		Point normalLen = new Point(-Math.round(NORMAL_LEN * (dstPt.y - srcPt.y) / dis),
				Math.round(NORMAL_LEN * (dstPt.x - srcPt.x) / dis));

		g.drawLine(srcPt.x, srcPt.y, dstPt.x, dstPt.y);

		g.drawLine(dstPt.x, dstPt.y, crossPoint.x + normalLen.x, crossPoint.y + normalLen.y);
		g.drawLine(dstPt.x, dstPt.y, crossPoint.x - normalLen.x, crossPoint.y - normalLen.y);
	}
}
