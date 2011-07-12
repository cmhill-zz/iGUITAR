package edu.umd.cs.guitar.ripper;

import javax.accessibility.Accessible;

import edu.umd.cs.guitar.model.JFCXComponent;
import edu.umd.cs.guitar.model.data.ComponentType;

/**
 * JFCComponentStub is a Mock Object Class for JFCXComponent. This class
 * holds the GUITAR representation of a JFC window. Member function used in testing
 * are implemented or otherwise default to the parent class 
 */
public class JFCComponentStub extends JFCXComponent{

	/** Component Name*/
	String Name;
	/** Comonent ComponentType using the mock object ComponentTypeStub*/
	ComponentTypeStub ct= new ComponentTypeStub();
	/** Dummy Constructor...initializes variables*/
	public JFCComponentStub(Accessible arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	/**
     * Returns component name
     * 
     * @return "Name"
     */
	public String getName(){
		return "Name";
	}
	/**
     * Returns properties associated with the component in the form of a ComponentType
     * 
     * @return ComponentType
     */
	public ComponentType extractProperties(){
		return ct;
	}
	/**
     * Sets component type
     * @param c ComponentType value to set in component
     * 
     */
	public void setComponentType(ComponentType c){
		ct=(ComponentTypeStub)c;
	}
}