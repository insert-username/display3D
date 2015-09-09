package display3D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

import javax.swing.JComponent;

/**
 * graphics class used for creating 3d drawings
 * 
 * TODO: the canvass class needs to encorporate a Z-buffer for correct Z depth drawing.
 * Implementation of this using Swing may be too slow to run however.
 * @author Luke
 *
 */
public class Graphics3D {
	
	private final Camera3D camera;
	private final Graphics g;
	private final JComponent canvass;
	
	
	public Graphics3D( Camera3D camera, Graphics g, JComponent canvass){
		this.camera = camera;
		this.canvass = canvass;
		this.g = g;
	}
	
	public void setColor( Color color ){
		g.setColor(color);
	}
	
	public Graphics getGraphics2D(){
		return g;
	}
	
	private int getWidth(){
		return canvass.getWidth();
	}
	
	private int getHeight(){
		return canvass.getHeight();
	}
	
	public Camera3D getCamera(){
		return camera;
	}
	
	public void drawPoint3D( double [] coord ,int radius){
		coord = camera.getDisplayCoords(coord);
		
		int x = (int)(coord[0] ) + getWidth()/2;
		int y = getHeight() - ((int)(coord[1] ) + getHeight()/2);
		g.fillOval(x-radius, y-radius, 2*radius, 2*radius);
	}
	
	public void drawString3D( double [] coord , String msg){
		coord = camera.getDisplayCoords(coord);
		
		int x = (int)(coord[0] ) + getWidth()/2;
		int y = getHeight() - ((int)(coord[1] ) + getHeight()/2);
		g.drawString(msg, x, y);
	}
	
	public void drawString2D( int x , int y , String msg){
		g.drawString(msg, x, y);
	}
	
	public void drawLine3D( double [] coordA , double [] coordB ){
		this.drawLine3D(coordA, coordB, 1);
	}
	
	public void drawLine3D( double [] coordA , double [] coordB , int thickness ){
		coordA = camera.getDisplayCoords(coordA);
		coordB = camera.getDisplayCoords(coordB);
		
		double x1 = (coordA[0]) + getWidth()/2;
		double y1 = getHeight() - ((coordA[1] ) + getHeight()/2);
		
		double x2 = (coordB[0]) + getWidth()/2;
		double y2 = getHeight() - ((coordB[1] ) + getHeight()/2);
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(thickness));
		g2.draw(new Line2D.Double(x1,y1,x2,y2));
	}
	
	public void drawArrow3D( double [] coordA , double [] coordB , int thickness , double headSize){
		
		coordA = camera.getDisplayCoords(coordA);
		coordB = camera.getDisplayCoords(coordB);
		
		double x1 = (coordA[0]) + getWidth()/2;
		double y1 = getHeight() - ((coordA[1] ) + getHeight()/2);
		
		double x2 = (coordB[0]) + getWidth()/2;
		double y2 = getHeight() - ((coordB[1] ) + getHeight()/2);
		
		drawArrow2D( x1,y1 , x2 , y2 , thickness ,headSize);
		
	}
	
	public void drawArrow2D( double x0 , double y0 , double x1 , double y1 , int thickness , double headSize){
		
		double arrX = x1-x0;
		double arrY = y1-y0;
		
		double headOffsX1 = x0 + arrX*(1.-headSize) -arrY*headSize;
		double headOffsY1 = y0 + arrY*(1.-headSize) +arrX*headSize;
		
		double headOffsX2 = x0 + arrX*(1.-headSize) +arrY*headSize;
		double headOffsY2 = y0 + arrY*(1.-headSize) -arrX*headSize;
		
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(thickness));
		g2.draw(new Line2D.Double(x0,y0,x1-arrX*headSize,y1-arrY*headSize));
		//g2.draw(new Line2D.Double(x1,y1,headOffsX1,headOffsY1));
		//g2.draw(new Line2D.Double(x1,y1,headOffsX2,headOffsY2));
		Path2D p = new Path2D.Double();
		
		p.moveTo(x1, y1);
		p.lineTo(headOffsX1,headOffsY1);
		p.lineTo(headOffsX2,headOffsY2);
		p.lineTo(x1, y1);
		g2.fill(p);
	}
	
	public void drawCircle3D( double [] origin , double radius , int steps ){
		double theta = 0;
		double deltaTheta = ( 2. * Math.PI ) / (double)steps;
		
		double[] r0 = new double[]{
				origin[0] + Math.cos(theta)*radius,
				origin[1] + Math.sin(theta)*radius,
				0
		};
		
		
		for( theta = 0 ; theta <= 2.*Math.PI - deltaTheta ; theta += deltaTheta ){
			double[] r1 = new double[]{
					origin[0] + Math.cos(theta+deltaTheta)*radius,
					origin[1] + Math.sin(theta+deltaTheta)*radius,
					0
			};
			
			this.drawLine3D(r0, r1);
			
			r0 = r1;
		}
	}
}
