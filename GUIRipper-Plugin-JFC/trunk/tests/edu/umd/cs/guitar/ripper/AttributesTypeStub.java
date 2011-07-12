package edu.umd.cs.guitar.ripper;
import edu.umd.cs.guitar.model.data.*;
import java.util.*;

/**
 * The AttributesTypeStub class is a mock object for the class AttributesType.  
 */
public class AttributesTypeStub extends AttributesType{
    
	/** Maintains a list of properties associated with each attribute */
	List<PropertyType> friendProperites = new ArrayList<PropertyType>();
	
	/**
     * Blank/Dummy Constructor
     */
	public AttributesTypeStub(){
		
	}
	/**
     * Returns List of properties associated with the  Attribute
     * 
     * @return List<PropertyType>
     */
	public List<PropertyType> getProperty(){
		return friendProperites;
	}
}
