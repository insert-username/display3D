package display3D;

import java.awt.Color;

/**
 * draw some 3D axes
 * @author Luke
 *
 */
public class Object3DAxes implements Object3D{
	
	private double scale = 1;
	private double [] origin = new double[]{0,0,0};
	private double [] x = new double[]{ 1,0,0 };
	private double [] y = new double[]{ 0,1,0 };
	private double [] z = new double[]{ 0,0,1 };
	
	//axes labels, eg xyz or r theta phi
	private String lx,ly,lz;
	
	public Object3DAxes( String lx , String ly , String lz , double scale){
		this.lx = lx;
		this.ly = ly;
		this.lz = lz;
		x[0] *= scale;
		y[1] *= scale;
		z[2] *= scale;
	}
	
	public void draw(Graphics3D g3D) {
		
		g3D.setColor(Color.white);
		g3D.drawArrow3D(origin, x,2,.1);
		g3D.drawArrow3D(origin, y,2,.1);
		g3D.drawArrow3D(origin, z,2,.1);
		
		g3D.drawString3D(Vect.add(x, new double[]{.1,0,0}), lx );
		g3D.drawString3D(Vect.add(y, new double[]{0,0.1,0}), ly);
		g3D.drawString3D(Vect.add(z, new double[]{0,0,0.1}), lz);
		
	}
	
}
