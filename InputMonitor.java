package display3D;

import java.awt.event.*;



/**
 * monitors mouse input on a canvass and adjusts the canvass' camera accordingly
 * @author Luke
 *
 */
public class InputMonitor implements MouseListener, MouseMotionListener,MouseWheelListener{
	
	private int mouseDragX , mouseDragY;
	private int mouseX , mouseY;
	
	private Camera3D camera;
	private Scene3D scene;
	
	public InputMonitor( Camera3D camera , Scene3D scene){
		this.camera =camera;
		this.scene = scene;
		
		this.scene.addMouseListener(this);
		this.scene.addMouseMotionListener(this);
		this.scene.addMouseWheelListener(this);
	}
	
	public int [] getDrag(){
		int [] result = new int[]{ mouseDragX , mouseDragY };
		mouseX -= mouseDragX;
		mouseY -= mouseDragY;
		mouseDragX = 0;
		mouseDragY = 0;
		
		return result;
	}
	
	
	public void mouseDragged(MouseEvent e) {
		mouseDragX = -(e.getX() - mouseX);
		mouseDragY = -(e.getY() - mouseY);
		
		//get the mouse drag amount
		int [] drag = getDrag();
		
		if( !e.isShiftDown() ){
			double deltaPhi = ((double)drag[0])/100.;
			double deltaTheta = ((double)drag[1])/100.;
			
			
			camera.rotatePhi(deltaPhi);
			camera.rotateTheta(deltaTheta);
		}else{
			
			Vect foc = camera.getFocus();
			
			Vect[] xy = camera.getCamXYVectors();
			
			foc = foc.add( xy[0].scale( 1./camera.getZoom() * ((double)drag[0])) );
			foc = foc.add( xy[1].scale( 1./camera.getZoom() * ((double)-drag[1]))  );
			
			camera.setFocus(foc);
		}
		
		scene.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if( e.isControlDown() ){
			camera.setLocation(1 , 0 , 0);
			
			//camera.setFocus(new Vect(0,0,0));
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void mousePressed(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	public void mouseReleased(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		mouseDragX = 0;
		mouseDragY = 0;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		double tics  = -e.getPreciseWheelRotation();
		
		if( tics > 0 )
			camera.setZoom(camera.getZoom()/0.9);
		else
			camera.setZoom(camera.getZoom()/1.1);
		
		scene.repaint();
	}

}
