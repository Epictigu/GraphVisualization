package de.fhswf;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

public class Knoten extends JPanel{

	private static final long serialVersionUID = 2L;
	
	float degrees = 180;
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
        float innerDiameter = 80;

        Point p = getPointOnCircle(degrees, (getWidth() / 2f) - (innerDiameter / 2));
        g2d.fillOval(p.x - (int) (innerDiameter / 2), p.y - (int) (innerDiameter / 2), (int) innerDiameter, (int) innerDiameter);

        g2d.dispose();
	}
	
	protected Point getPointOnCircle(float degress, float radius) {

	    int x = Math.round(getWidth() / 2);
	    int y = x;

	    double rads = Math.toRadians(degress - 90);
	    
	    
	    int xPosy = Math.round((float) (x + Math.cos(rads) * radius));
	    int yPosy = Math.round((float) (y + Math.sin(rads) * radius));

	    return new Point(xPosy, yPosy);

	}
	
}
