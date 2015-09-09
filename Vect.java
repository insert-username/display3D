package display3D;


/**
 * three-dimensional vector class
 * @author Luke
 *
 */

public class Vect {

	private double [] vals;
	
	public Vect( double ... vals ){
		if( vals.length != 3 )
			throw new IllegalArgumentException();
		
		this.vals = clone( vals );
	}
	
	public Vect( Vect vect ){
		this( vect.vals );
	}
	
	public static double[] clone( double [] vals ){
		if( vals.length != 3 )
			throw new IllegalArgumentException();
		
		return new double[]{
				vals[0],
				vals[1],
				vals[2]
		};
	}
	
	public double[] getVals(){
		return clone( vals );
	}
	
	public Vect add( Vect vect ){
		return new Vect( Vect.add(vals, vect.vals) );
	}
	
	public static double[] add( double[] a , double[] b ){
		return new double[]{
				a[0] + b[0],
				a[1] + b[1],
				a[2] + b[2]
		};
	}
	
	public Vect sub( Vect vect ){
		return new Vect( Vect.sub(vals, vect.vals) );
	}
	
	public static double[] sub( double[] a , double[] b ){
		return new double[]{
				a[0] - b[0],
				a[1] - b[1],
				a[2] - b[2]
		};
	}
	
	public double dot( Vect vect ){
		return  Vect.dot(vals, vect.vals) ;
	}
	
	public static double dot( double[] a , double[] b){
		return ( a[0]*b[0] + a[1]*b[1] + a[2]*b[2] );
	}
	
	public Vect scale( double amount ){
		return new Vect( scale( this.vals , amount ) );
	}
	
	public static double modSq( double [] vals ){
		return vals[0]*vals[0] + vals[1]*vals[1] + vals[2]*vals[2];
	}
	
	public double modSq(){
		return Vect.modSq(getVals());
	}
	
	public static double[] scale( double [] vals , double amount ){
		return new double[]{
				vals[0]*amount,
				vals[1]*amount,
				vals[2]*amount
		};
	}
	
	public Vect cross( Vect vect ){
		return new Vect( Vect.cross(vals, vect.vals) );
	}
	
	public static double[] cross( double[] a , double[] b ){
		return new double[]{
				a[1]*b[2] - b[1]*a[2],
				a[2]*b[0] - a[0]*b[2],
				a[0]*b[1] - b[0]*a[1]
		};
	}
	
	public Vect normalise(){
		return new Vect( Vect.normalise(vals) );
	}
	
	public static double [] normalise( double ... r ){
		double x = r[0];
		double y = r[1];
		double z = r[2];
		
		double hyp = Math.sqrt(x*x +y*y +z*z);
		return new double[]{ x/hyp , y/hyp , z/hyp };
	}
	
	public static double angle( double x , double y ){
		if( x == 0 ){
			if( y > 0 ) return Math.PI/4.;
			if( y < 0 ) return Math.PI * ( 3./4. );
		}
		double theta = Math.atan2(y, x);
		
		//if( x < 0 && y > 0 ) theta = (Math.PI + theta);
		//if( x > 0 && y < 0 ) theta = 2.*Math.PI + theta;
		//if( x < 0 && y < 0 ) theta = 
			
		return theta;
	}
	
	public Vect rotateTheta( double deltaTheta ){
		
		double x = vals[0];
		double y = vals[1];
		double z = vals[2];
		
		double rFloor =  Math.hypot(x, y);
		double r = Math.sqrt( x*x + y*y + z*z );
		
		double theta = Vect.angle( z , rFloor );
		
		//if( theta + deltaTheta >= Math.PI/2. || theta + deltaTheta <= -0)
			//return this;
		
		theta += deltaTheta;
		
		double phi = Vect.angle( x , y );
		
		x = r * Math.cos(phi) * Math.sin(theta);
		y = r * Math.sin(phi) * Math.sin(theta);
		z = r * Math.cos(theta);
		
		return new Vect( x , y , z );
	}
	
	public double getTheta(){
		double theta = Vect.angle( getZ() , Math.hypot(getX(), getY()) );
		return theta;
	}
	
	public Vect rotatePhi( double deltaPhi ){
		double x = vals[0];
		double y = vals[1];
		double z = vals[2];
		
		//get current theta value
		if( vals[0] == 0 && vals[1] == 0 )
			return this;
		
		double r = Math.hypot(x, y);
		double theta = Vect.angle( x , y );
		theta += deltaPhi;
		
		x = Math.cos(theta) * r;
		y = Math.sin(theta) * r;
		
		return new Vect(x,y,z);
	}
	
	public double getX(){
		return vals[0];
	}
	public double getY(){
		return vals[1];
	}
	public double getZ(){
		return vals[2];
	}
	
	public String toString(){
		return getX() + "\t" + getY() + "\t" + getZ();
	}
}
