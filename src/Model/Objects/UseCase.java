package Model.Objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import Model.Base.BasicObject;

public class UseCase extends BasicObject {
	public UseCase(Point p, String str) {
        super(p, new Dimension(150, 75), str);
    }
	
	public UseCase(String str) {
		this(new Point(), str);
	}

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension textSize = getTextSize();
        int textW = textSize.width, textH = textSize.height;
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(0, 0, getWidth(), getHeight());
        g.setColor(Color.black);
        g.drawString(name, (getWidth() - textW) / 2, (getHeight() + textH) / 2);
    }

    @Override
    public void paintBorder(Graphics g){
        super.paintBorder(g);
        g.setColor(Color.blue);
        g.drawOval(0, 0, getWidth(), getHeight());
    }
}
