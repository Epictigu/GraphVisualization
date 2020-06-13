package de.fhswf;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.ToolTipManager;

public class Knoten extends JPanel{

	private static final long serialVersionUID = 2L;
	
	public Graph graph = null;
	
	private float degreeC = 360f;
	private int iD = 80;
	private int currentCircle = 0;
	
	Map<Integer, Point> circlePos = new HashMap<Integer, Point>();
	
	public Knoten() {
		addMouseListener(getMouseAdapter());
		addMouseMotionListener(getMouseAdapter());
	}
	
	public Knoten(Graph g) {
		graph = g;
        if(g.getAmountKnots() > 4) 
        	iD-=(g.getAmountKnots()-4)*2;
        if(g.getAmountKnots() > 1)
        	degreeC = 360f / g.getAmountKnots();
        
        ToolTipManager.sharedInstance().registerComponent(this);
        addMouseListener(getMouseAdapter());
        addMouseMotionListener(getMouseAdapter());
        
        calcPos();
	}
	
	private void calcPos() {
        for(int i = 0; i < graph.getAmountKnots(); i++) {
        	circlePos.put(i + 1, addEcke(i * degreeC));
        }
	}
	
	@Override
	public String getToolTipText(MouseEvent event) {
		int i = getCurrentCircle(event);
		if(i > 0) return "" + graph.getKnotNames()[i];
		return null;
	}
	
    @Override
    public Point getToolTipLocation(MouseEvent event) {
        return new Point(event.getX() + 10, event.getY() + 10);
    }
    
    public MouseAdapter getMouseAdapter() {
    	return new MouseAdapter() {
    		public void mousePressed(MouseEvent e) {
    			int i = getCurrentCircle(e);
    			if(i > 0) {
    				currentCircle = i;
    			}
    		}
    		public void mouseReleased(MouseEvent e) {
    			currentCircle = 0;
    		}
    		public void mouseDragged(MouseEvent e) {
    			if(currentCircle > 0) {
    				int x = e.getX();
    				int y = e.getY();
    				
    				if(x < iD / 2) x = iD / 2;
    				if(y < iD / 2) y = iD / 2;
    				if(x > getWidth() - iD / 2) x = getWidth() - iD / 2;
    				if(y > getHeight() - iD / 2) y = getHeight() - iD / 2;
    				
    				circlePos.get(currentCircle).setLocation(x - (iD / 2), y - (iD / 2));
        			repaint();
    			}
    		}
    		public void mouseMoved(MouseEvent e) {
    			int i = getCurrentCircle(e);
        		if(i > 0) setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        		else setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    		}
		};
    }
    
    public int getCurrentCircle(MouseEvent event) {
		for(int i = 1; i <= circlePos.size(); i++) {
			Point oP = circlePos.get(i);
			double d = Math.hypot(event.getX()-(oP.getX() + iD / 2), event.getY()-(oP.getY() + iD / 2));
			if(d < iD / 2) return i;
		}
		return 0;
    }
    
	public void setFile(Graph g) {
		graph = g;
		iD = 80;
		degreeC = 360;
        if(g.getAmountKnots() > 4) 
        	iD-=(g.getAmountKnots()-4)*2;
        if(g.getAmountKnots() > 1)
        	degreeC = 360f / g.getAmountKnots();
        
        circlePos.clear();
        
        calcPos();
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
		
		
		if(graph == null) {
			g2d.setFont(new Font("Serif", Font.BOLD, 20));
			int width = g2d.getFontMetrics().stringWidth("Kein Graph ausgewählt!");
			g2d.drawString("Kein Graph ausgewählt!", getWidth() / 2 - (width / 2), getHeight() / 2 - 50);
			return;
		}
        
        g2d.setStroke(new BasicStroke(2.5f - (graph.getAmountKnots() / 20f)));
        
        boolean[][] adjM = graph.getAdjacencyMatrix();
        for(int i = 1; i <= graph.getAmountKnots(); i++) {
        	for(int j = 1; j <= graph.getAmountKnots(); j++) {
        		if(j != i) {
        			if(adjM[i][j])
        				drawKante(g2d, i, j);
        		}
        	}
        }
		
        int fontSize = (int) (30.0 - graph.getAmountKnots() * 0.5);
        g2d.setFont(new Font("Serif", Font.BOLD, fontSize));
		int cP = 0;
		for(Point p : circlePos.values()) {
			g2d.setColor(Color.WHITE);			cP++;
			g2d.fillOval(p.x, p.y, iD, iD);
			String knotName = graph.getKnotNames()[cP];
			if(knotName.length() < 4) {
				g2d.setColor(Color.BLACK);
				Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(knotName, g2d);
				g2d.drawString(knotName, (int) (p.x + iD / 2 - bounds.getWidth() / 2), (int) (p.y + iD / 2 + bounds.getHeight() / 4));
			}
		}
		
        g2d.dispose();
	}
	
	Point addEcke(float degree) {
        Point p = getPointOnCircle(degree, ((getWidth() - 50) / 2) - (iD / 2));
        p.setLocation(p.x - (iD / 2), p.y - (iD / 2));
        
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
