package edu.umd.cs.guitar.ripper;

import java.util.*;

import edu.umd.cs.guitar.model.data.*;

/**
 * The Ripper Stub class implements a Mock Object for the class Ripper. 
 * The ripper class is responsible for extracting attributes from given components
 * and windows
 *
 */
public class RipperStub extends Ripper{
	/** Component Attributes found*/
	ComponentTypeStub ct = new ComponentTypeStub(); 
	/**
	 * Dummy Constructor*/
	RipperStub(){
		
	}
	/**
     * Returns a componenttype object that contains attributes "ripped" from the component. THis is 
     * exclusively used for the tabbedfiler test
     * 
     * @param c GComponent in GUI
     * @param w GWindow in GUI
     * 
     * @return ComponentType
     */
	ComponentTypeStub ripComponent(GComponentStub c, GWindowStub w){
		AttributesTypeStub a = new AttributesTypeStub();
		PropertyType p = new PropertyType();
		p.setName("JTabbedPane");
		ArrayList<PropertyType> plist = new  ArrayList<PropertyType>();
		a.setProperty(plist);
		ct.setAttributes(a);
		return ct;
	}
}