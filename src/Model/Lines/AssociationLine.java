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
 * @see {@link Model.Base.Line}
 */
public class AssociationLine extends Line {
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
		Point online = new Point(dstPt.x + ARROW_LEN * (srcPt.x - dstPt.x) / dis,
				dstPt.y + ARROW_LEN * (srcPt.y - dstPt.y) / dis);
		Point normal = new Point(-ARROW_LEN * (dstPt.y - srcPt.y) / dis, ARROW_LEN * (dstPt.x - srcPt.x) / dis);
		g.drawLine(srcPt.x, srcPt.y, dstPt.x, dstPt.y);
		g.drawLine(dstPt.x, dstPt.y, online.x + normal.x, online.y + normal.y);
		g.drawLine(dstPt.x, dstPt.y, online.x - normal.x, online.y - normal.y);
	}
}
