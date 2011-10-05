package edu.umd.cs.guitar.ripper;

import java.awt.Window;

import javax.accessibility.Accessible;

import edu.umd.cs.guitar.model.JFCXComponent;
import edu.umd.cs.guitar.model.JFCXWindow;
import edu.umd.cs.guitar.model.data.ComponentType;


/**
 * JFCWindowStub is a Mock Object Class for JFCXWindow. This class
 * holds the GUITAR representation of a JFC window. Member function used in testing
 * are implemented or otherwise default to the parent class 
 */
public class JFCWindowStub extends JFCXWindow{
	/** Is Window Valid*/
	boolean valid;
	
	/**
	 * 
	 * holds the GUITAR representation of a JFC window
	 */
	public JFCWindowStub(Window arg0) {
		super(arg0);
		valid = true;
		// TODO Auto-generated constructor stub
	}

}