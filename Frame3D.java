package display3D;

import javax.swing.*;

/**
 * simple frame for displaying a canvass3D
 * @author Luke
 *
 */
public class Frame3D extends JFrame{
	
	private Scene3D canvass;
	
	public Frame3D( Scene3D c ){
		if( c == null )
			throw new NullPointerException();
		
		this.canvass = c;
		
		this.getContentPane().add(canvass);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500,500);
		this.setVisible(true);
		
	}

	
}
