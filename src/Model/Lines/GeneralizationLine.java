package Model.Lines;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import Model.Base.Line;
import Model.Base.Port;

/**
 * Generalization line component
 * 
 * @author Jimmy801
 *
 * @see {@link Model.Base.Line}
 */
public class GeneralizationLine extends Line {
	/**
	 * Initialize by source port and destination port
	 * 
	 * @param src - source port
	 * @param dst - destination port
	 * 
	 * @see {@link Line}
	 */
	public GeneralizationLine(Port src, Port dst) {
		super(src, dst);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(lineColor);
		Point srcPt = src.getLocation();
		Point dstPt = dst.getLocation();
		int dis = (int) srcPt.distance(dstPt);
		Point online = new Point(dstPt.x + ARROW_LEN * (srcPt.x - dstPt.x) / dis,
				dstPt.y + ARROW_LEN * (srcPt.y - dstPt.y) / dis);
		Point normal = new Point(-ARROW_LEN * (dstPt.y - srcPt.y) / dis, ARROW_LEN * (dstPt.x - srcPt.x) / dis);

		int x[] = { dstPt.x, online.x + normal.x, online.x - normal.x };
		int y[] = { dstPt.y, online.y + normal.y, online.y - normal.y };
		g.drawLine(srcPt.x, srcPt.y, online.x, online.y);
		g.drawPolygon(x, y, 3);
	}
}
