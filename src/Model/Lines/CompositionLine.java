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
		Point online = new Point(dstPt.x + ARROW_LEN * (srcPt.x - dstPt.x) / dis,
				dstPt.y + ARROW_LEN * (srcPt.y - dstPt.y) / dis);
		Point twoonline = new Point(dstPt.x + ARROW_LEN * 2 * (srcPt.x - dstPt.x) / dis,
				dstPt.y + ARROW_LEN * 2 * (srcPt.y - dstPt.y) / dis);
		Point normal = new Point(-ARROW_LEN * (dstPt.y - srcPt.y) / dis, ARROW_LEN * (dstPt.x - srcPt.x) / dis);

		int x[] = { dstPt.x, online.x + normal.x, twoonline.x, online.x - normal.x };
		int y[] = { dstPt.y, online.y + normal.y, twoonline.y, online.y - normal.y };
		g.drawLine(srcPt.x, srcPt.y, dstPt.x, dstPt.y);

		g.fillPolygon(x, y, 4);
	}
}
