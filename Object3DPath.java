package display3D;

import java.awt.Color;
import java.util.*;

import uk.ac.ed.ph.advcompsim.part2.point.Point;

/**
 * implementation of object3d for displaying a path
 * @author Luke
 *
 */
public class Object3DPath implements Object3D{
	
	private static int col = 0;
	private static Color[] colors = new Color[]{
		Color.red,
		Color.green,
		Color.yellow,
		Color.CYAN,
		Color.magenta,
		Color.orange,
		Color.blue
	};
	
	private static Color[] tailColors = new Color[]{
		new Color( 155 , 150 , 0 , 0 ),
		new Color( 0 , 255 , 0 , 0 ),
		new Color( 155 , 0 , 0 , 0 ),
		new Color( 255 , 0 , 255 , 0 ),
		new Color( 255 , 0 , 55 , 0 ),
		new Color( 255 , 255 , 15 , 0 ),
		new Color( 200 , 100 , 200 , 0 )
	};
	
	
	//name
	private String name;
	
	//display trail?
	boolean trail = true;
	
	//a list of the coordinates to draw
	List<double[]> coordinates = new LinkedList<double[]>();
	
	//the current head location
	private double [] headCoordinate = null;
	
	//the length of the path
	private int maxLength = 1000;
	
	//the coordinate of the last added element
	private int currentElement = 0;
	
	int count = 0;
	
	//number of appends to skip
	int elementskips = 10;
	
	//head color
	private Color headColor;
	private Color tailColor;
	
	public Object3DPath( String name ){
		this.name = name;
		
		headColor = colors[col];
		tailColor = tailColors[col];
		
		col = (col+1)%colors.length;
	}
	
	public Object3DPath( String name , int maxLength , int elementskips ){
		this(name);
		this.maxLength = maxLength;
		this.elementskips = elementskips;
	}
	
	public void setTrail( boolean trail ){
		this.trail = trail;
	}
	
	
	//append another coordinate to the path
	public void append( double ... coordinate ){
		if( coordinate.length != 3 )
			throw new IllegalArgumentException();
		
		headCoordinate = coordinate;
		count = (count+1)%elementskips;
		//dont append trails if they are too close together
		if( coordinates.size()>0 ){
			
			if( count != 0 )
				return;
			
		}else{
			coordinates.add(coordinate);
			currentElement = (currentElement+1) % maxLength;
			return;
		}
		
		currentElement = (currentElement+1) % maxLength;
		
		if( coordinates.size() <= currentElement )
			coordinates.add(coordinate);
		else
			coordinates.set(currentElement, coordinate);
	}
	
	public void append( Point coordinate ){
		append( new double[]{ 
				coordinate.coordinate(0),
				coordinate.coordinate(1),
				coordinate.coordinate(2)
		} );
	}
	
	public void draw(Graphics3D graphics) {
		
		for( int i = 1 ; i < coordinates.size() ; i++ ){
			int j0 = (i+currentElement-1) % coordinates.size();
			int j = (i+currentElement) % coordinates.size();
			
			double [] previousCoord = coordinates.get(j0);
			double [] coord = coordinates.get(j);
			
			//blend from head to tail color
			int red =
					blend( headColor.getRed() , tailColor.getRed() , coordinates.size()-1 , i );
			
			int blue =
					blend( headColor.getBlue() , tailColor.getBlue() , coordinates.size()-1 , i );
			
			int green =
					blend( headColor.getGreen() , tailColor.getGreen() , coordinates.size()-1 , i );
			
			int alpha =
					blend( headColor.getAlpha() , tailColor.getAlpha() , coordinates.size()-1 , i );
			
			graphics.setColor(new Color( (int)red , (int)green , (int)blue , (int)alpha ));
			
			if(trail)
				graphics.drawLine3D(coord, previousCoord);
			
			if( i == coordinates.size()-1 ){
				graphics.setColor( headColor );
				graphics.drawLine3D(coord, headCoordinate);
			}
		}
		
		if( headCoordinate != null ){
			graphics.setColor( headColor );
			graphics.drawPoint3D( headCoordinate , 3);
			graphics.drawString3D( headCoordinate , name);
		}
		
	}
	
	private int blend( int a , int b , int max , int i){
		
		double diff = a-b;
		diff *= (double)i/(double)max;
		diff+= b;
		
		return (int)diff;
		
	}
	
}
