package edu.umd.cs.guitar.ripper;

import java.util.List;
import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.PropertyType;

/**
 * GWindowStub is a Mock Object Class for GWindow. This class
 * hold the GUITAR represenation of a window
 */
public class GWindowStub extends GWindow{

	/** Name of Window */
	String Name;
	/** Window ComponentType using the mock object ComponentTypeStub*/
	ComponentTypeStub ct= new ComponentTypeStub();
	
	/**
     * Returns properties associated with the window in the form of a ComponentType
     * 
     * @return ComponentType
     */
	public ComponentType extractProperties(){
		return ct;
	}
	
	/**
     * Returns if two GWindows are equal. This is hard coded to false
     * 
     * @return false
     */
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
     * Returns properties associated with the GUI in the form of a GUIType
     * 
     * @return null
     */
	@Override
	public GUIType extractGUIProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
     * Returns components associated with the window 
     * 
     * @return GComponent
     */
	@Override
	public GComponent getContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
     * Returns Properties associated with the window 
     * 
     * @return null
     */
	@Override
	public List<PropertyType> getGUIProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
     * Returns if Window is Modal
     * 
     * @return false
     */
	@Override
	public boolean isModal() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
     * Returns if Window is Valid
     * 
     * @return false
     */
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
     * Returns window ID
     * 
     * @return null
     */
	public String getFullID() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
     * Returns window name
     * 
     * @return "Name"
     */
	public String getName() {
		// TODO Auto-generated method stub
		return "Name";
	}

	public String getTitle(){
		return this.getName(); //added to fix testIgnoredWindowGWindow test in JFCRipperMonitorTest.java
	}
}