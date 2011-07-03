package edu.umd.cs.guitar.ripper;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.data.ComponentType;

/**
 * GComponentFilterStub is a Mock Object Class for GComponentFilter. This overrides the Abstract 
 * functions as well as functions needed for testing
 */
public class GComponentFilterStub extends GComponentFilter {
	
	/**
     * Returns whether a given filter can process these components
     * 
     * @param arg0 GComponent in GUI
     * @param arg1 GWindow in GUI
     * 
     * @return false
     */
	@Override
	public boolean isProcess(GComponent arg0, GWindow arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
     * Returns a componenttype object that contains attributes "ripped" from the component
     * 
     * @param arg0 GComponent in GUI
     * @param arg1 GWindow in GUI
     * 
     * @return null
     */
	@Override
	public ComponentType ripComponent(GComponent arg0, GWindow arg1) {
		// TODO Auto-generated method stub
		return null;
	}
}
