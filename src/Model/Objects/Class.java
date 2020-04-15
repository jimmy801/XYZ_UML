package Model.Objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import Model.Base.BasicObject;

public class Class extends BasicObject {
	public Class(Point p, String str){
        super(p, new Dimension(100,90), str);
    }
	
	public Class(String str) {
		this(new Point(), str);
	}

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int offset = getHeight() / 3;
        Dimension textSize = getTextSize();
        int textW = textSize.width, textH = textSize.height;
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.BLACK);
        g.drawLine(0, offset, getWidth(), offset);
        g.drawLine(0, offset * 2, getWidth(), offset * 2);

        g.setColor(Color.BLACK);
        g.drawString(name, getWidth() / 2 - textW / 2, getHeight() / 3 - textH / 2);
    }

    @Override
    public void paintBorder(Graphics g){
        super.paintBorder(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.blue);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(0, 0, getWidth(), getHeight());
    }
}
