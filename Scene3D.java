package display3D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;

import javax.swing.JComponent;
import javax.swing.JFrame;



/**
 * class for displaying object3D's
 * @author Luke
 *
 */
public class Scene3D extends JComponent {
	
	private Camera3D camera = new Camera3D();
	private List<Object3D> objects = new ArrayList<Object3D>();
	private InputMonitor mdm;
	
	private Color background = Color.black;
	
	public Scene3D(){
		mdm = new InputMonitor( camera ,this);
	}
	
	public Scene3D( Color bg ){
		this();
		this.background = bg;
	}
	
	public Scene3D( Object3D ... objects ){
		this();
		for( Object3D o : objects )
			addObject3D(o);
	}
	
	public void addObject3D( Object3D c3d ){
		objects.add(c3d);
	}
	
	public void addObject3D( Object3D ... objects ){
		for( Object3D o : objects ){
			addObject3D(o);
		}
	}
	
	public void removeObject3D( Object3D c3d ){
		objects.remove(c3d);
	}
	
	public void clearObject3D(  ){
		objects.clear();
	}
	
	public Camera3D getCamera3D(){
		return camera;
	}
	
	public void paintComponent( Graphics g ){
		g = ( Graphics2D )g;
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor( background );
		g.fillRect(0, 0, getWidth(), getHeight());
		
		Graphics3D g3D = new Graphics3D( camera ,g, this );
		
		
		
		g.setColor(Color.red);
		for( Object3D c3D : objects ){
			synchronized( c3D ){
				c3D.draw(g3D);
			}
		}
		
	}
	
	
}
