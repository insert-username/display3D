package display3D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;


/**
 * projects a three dimensional point onto an orthographic 'billboard' surface
 * @author Luke
 *
 */
public class Camera3D {
	
	//where the camera is in space
	private Vect camLocation = new Vect(new double[]{ 1 , 1 , 1 });
	
	//the point in space the camera is looking at
	private Vect camFocus = new Vect(new double[]{ 0,0, 0});
	
	//multiplier
	private double multiplier = 100;
	
	public double[] getDisplayCoords( double [] r ){
		return this.getDisplayCoords(new Vect(r));
	}
	
	public double[] getDisplayCoords( Vect r ){
		
		//offset by focus
		r = r.sub( camFocus );
		
		//two vectors orthogonal to the camera location based on the roll angle
		//get cam X by z cross r
		Vect camXVector = new Vect(0,0,1).cross( camLocation  );
		Vect camYVector = camLocation.cross( camXVector   );
		
		//normalise the camera vectors
		camXVector = camXVector.normalise();
		camYVector = camYVector.normalise();
		
		double viewX = r.dot( camXVector );
		double viewY = r.dot( camYVector );
		
		return new double[]{ viewX*multiplier , viewY*multiplier };
		
	}
	
	public Vect[] getCamXYVectors(){
		Vect camX = new Vect(0,0,1).cross( camLocation  ).normalise();
		Vect camY = camLocation.cross( camX ).normalise();
		
		return new Vect[]{ camX , camY };
	}
	
	public Vect getLocation(){
		return new Vect( camLocation );
	}
	
	public Vect getFocus(){
		return new Vect( camFocus );
	}
	
	public double getZoom( ){
		return multiplier;
	}
	
	public void setZoom( double multiplier ){
		this.multiplier = multiplier;
	}
	
	public void setLocation( double ... location ){
		if( location.length != 3 )
			throw new IllegalArgumentException();
		this.camLocation = new Vect(location);
	}
	
	public void setLocation( Vect location ){
		this.camLocation = new Vect(location);
	}
	
	public void setFocus( double ... focus ){
		if( focus.length != 3 )
			throw new IllegalArgumentException();
		this.camFocus = new Vect(focus);
	}
	
	public void setFocus( Vect focus ){
		this.camFocus = new Vect(focus);
	}
	
	public void rotateTheta( double deltaTheta ){
		setLocation( camLocation.rotateTheta(deltaTheta) );
	}
	
	public void rotatePhi( double deltaPhi ){
		setLocation( camLocation.rotatePhi(deltaPhi) );
	}
	
	
	

}
