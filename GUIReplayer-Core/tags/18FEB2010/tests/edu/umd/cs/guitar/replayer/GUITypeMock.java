package edu.umd.cs.guitar.replayer;

import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.GUIType;

/**
 * This is the mock object for GUIType
 */
public class GUITypeMock extends GUIType {
	
	ComponentType window;
	/**
	 * Constructor
	 */
	public GUITypeMock(){
		window = new ComponentTypeMock();
	}
}
