package display3D;

/**
 * example 3D display
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
		
		//now create a scene to hold these objects
		Scene3D myScene = new Scene3D();
		myScene.addObject3D( path , axes );
		
		//now create a frame to display the scene, you dont
		//actually have to use Frame3D, scene is a JComponent
		Frame3D f3D = new Frame3D( myScene );
		
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
