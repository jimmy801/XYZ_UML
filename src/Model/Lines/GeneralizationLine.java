package Model.Lines;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import Model.Base.Line;
import Model.Base.Port;

public class GeneralizationLine extends Line {
    public GeneralizationLine(Port src, Port dst){
        super(src, dst);
    }
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        g.setColor(Color.BLACK);
		int dis = (int) src.R.distance(dst.R);
        Point online = new Point(dst.R.x + ARROW_LEN * (src.R.x - dst.R.x) / dis, dst.R.y + ARROW_LEN * (src.R.y - dst.R.y) / dis);
        Point normal = new Point(-ARROW_LEN * (dst.R.y - src.R.y) / dis, ARROW_LEN * (dst.R.x - src.R.x) / dis);

        int x[] = { dst.R.x, online.x + normal.x, online.x - normal.x };
        int y[] = { dst.R.y, online.y + normal.y, online.y - normal.y };
        g.drawLine(src.R.x, src.R.y, online.x, online.y);
        g.drawPolygon(x, y, 3);
	}
}
