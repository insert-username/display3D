package display3D;

/**
 * example for how to use 3d stuff. Note: i changed the Canvass3D class to Scene3D, so there's a
 * bunch of references to Canvass3D still here
 * @author Luke
 *
 */
public final class Demo {
	public static void main( String [] args ){
		
		//first, you'll need to make a 3D object
		String name = "name";//name
		int tailLength = 100;//max length of the tail
		int appendSkips = 5;//skip every n appends
		Object3DPath path = new Object3DPath(name , tailLength , appendSkips);
		
		//make some axes as well
		Object3D axes = new Object3DAxes("X", "Y", "Z", 1.0);
		
		//now create a canvass to hold these objects
		Scene3D myCanvass = new Scene3D();
		myCanvass.addObject3D( path , axes );
		
		//now create a frame to display the canvass, you dont
		//actually have to use Frame3D, canvass is a JComponent
		Frame3D f3D = new Frame3D( myCanvass );
		
		//you'll need to repaint manually ( I just found out you shouldn't use multithreading with Swing ) :
		
		int count = 0;
		while( true ){
			f3D.repaint();
			
			//append some coordinates to the path
			path.append(
					Math.cos( (double)count/50. ),
					Math.sin( (double)count/50. ),
					Math.sin( (double)count/20. ));
			
			try {
				Thread.currentThread().sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			count ++;
		}
	}
}
