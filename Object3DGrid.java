package display3D;

import java.awt.Color;

/**
 * draw a grid on the floor
 * @author Luke
 *
 */
public class Object3DGrid implements Object3D{
	
	private final double radius = 10;
	private final int divisions = 10;
	private final int segments = 20;
	
	public void draw(Graphics3D g) {
		double [] origin = new double[]{ 0,0,0 };
		double [] x = new double[]{ 1,0,0 };
		double [] y = new double[]{ 0,1,0 };
		double [] z = new double[]{ 0,0,1 };
		
		double radiusStep = radius/(double)divisions;
		
		g.setColor(new Color( 50 , 50 , 50 ));
		for( double r = radiusStep ; r <= radius ; r += radiusStep ){
			g.drawCircle3D(origin, r, segments);
		}
		
		for( int i = 0 ; i < segments ; i++){
			double theta = (double)i * (double)(2.*Math.PI/(double)segments);
			g.drawLine3D(origin, new double[]{ Math.cos(theta)*radius ,Math.sin(theta)*radius , 0 } );
		}
	}
	
}
