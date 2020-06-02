package de.fhswf;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class Knoten extends JPanel{

	private static final long serialVersionUID = 2L;
	
	float degrees = 0f;
	int knoten = 30;
	private int iD = 80;
	
	Map<Integer, Point> circlePos = new HashMap<Integer, Point>();
	
	@Override
	public void paint(Graphics g) {
		iD = 80;
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(
		        RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setColor(Color.WHITE);
        if(knoten > 4) {
        	iD-=(knoten-4)*2;
        }
        
        float degreeC = 360f;
        if(knoten > 0)degreeC = 360f / knoten;
        for(int i = 0; i < knoten; i++) {
        	circlePos.put(i + 1, drawEcke(g2d, degrees + (i * degreeC)));
        }
        
        g2d.setStroke(new BasicStroke(5f - (knoten / 10)));
        
        drawKante(g2d, 2, 7);
        drawKante(g2d, 9, 15);
        drawKante(g2d, 3, 7);
        drawKante(g2d, 11, 22);
        drawKante(g2d, 7, 23);
        drawKante(g2d, 1, 23);
        
        g2d.dispose();
	}
	
	Point drawEcke(Graphics2D g2d, float degree) {
        Point p = getPointOnCircle(degree, (getWidth() / 2) - (iD / 2));
        p.setLocation(p.x - (iD / 2), p.y - (iD / 2));
        g2d.fillOval(p.x, p.y, iD, iD);
        return p;
	}
	
	void drawKante(Graphics2D g2d, int k1, int k2) {
        int aL = iD / 2;
        
        Point t1 = circlePos.get(k1);
        Point t2 = circlePos.get(k2);
        g2d.drawLine(t1.x + aL, t1.y + aL, t2.x + aL, t2.y + aL);
	}
	
	protected Point getPointOnCircle(float degress, int radius) {

	    int x = Math.round(getWidth() / 2);
	    int y = x;

	    double rad = Math.toRadians(degress - 90);
	    
	    
	    int xPosy = Math.round((float) (x + Math.cos(rad) * radius));
	    int yPosy = Math.round((float) (y + Math.sin(rad) * radius));

	    return new Point(xPosy, yPosy);

	}
	
}
