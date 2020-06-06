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
	
	private Graph graph = null;
	
	private float degreeC = 360f;
	private int iD = 80;
	
	Map<Integer, Point> circlePos = new HashMap<Integer, Point>();

	public Knoten() {}
	
	public Knoten(Graph g) {
		graph = g;
        if(g.getAmountKnots() > 4) 
        	iD-=(g.getAmountKnots()-4)*2;
        if(g.getAmountKnots() > 1)
        	degreeC = 360f / g.getAmountKnots();
	}
	
	public void setFile(Graph g) {
		graph = g;
		iD = 80;
		degreeC = 360;
        if(g.getAmountKnots() > 4) 
        	iD-=(g.getAmountKnots()-4)*2;
        if(g.getAmountKnots() > 1)
        	degreeC = 360f / g.getAmountKnots();
        repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(
		        RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(new Color(51,51,51));
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setColor(Color.WHITE);
		
		if(graph == null) return;
		
        for(int i = 0; i < graph.getAmountKnots(); i++) {
        	circlePos.put(i + 1, drawEcke(g2d, (i * degreeC)));
        }
        
        g2d.setStroke(new BasicStroke(5f - (graph.getAmountKnots() / 10)));
        
        boolean[][] adjM = graph.getAdjacencyMatrix();
        for(int i = 1; i <= graph.getAmountKnots(); i++) {
        	for(int j = 1; j <= graph.getAmountKnots(); j++) {
        		if(j != i) {
        			if(adjM[i][j])
        				drawKante(g2d, i, j);
        		}
        	}
        }
        
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
