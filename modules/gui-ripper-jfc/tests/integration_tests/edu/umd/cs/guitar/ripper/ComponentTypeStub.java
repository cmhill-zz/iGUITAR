package edu.umd.cs.guitar.ripper;

import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentType;

/**
 * ComponentTypeStub is a Mock Object Class for ComponentClass. This class
 * Maintains the determines the type of components i.e. (Button or window)
 */
public class ComponentTypeStub extends ComponentType{
	
	/**
     * Attributes associated with this componenttype
     */
	AttributesTypeStub at = new AttributesTypeStub();

	/**
     * Blank/Dummy Constructor
     */
	public ComponentTypeStub(){
		
	}
	/**
     * Returns Attributes associated with the  componenttype
     * 
     * @return AttributesType
     */
	public AttributesType getAttributes(){
		return at;
	}
}
