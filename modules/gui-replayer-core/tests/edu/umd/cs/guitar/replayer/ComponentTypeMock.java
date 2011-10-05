package edu.umd.cs.guitar.replayer;

import edu.umd.cs.guitar.model.data.ComponentType;


/** 
 * This is the mock object of the ComponnetType
 * @author Ran Liu
 * @see edu.umd.cs.guitar.model.data.ComponentType
 *
 */
public class ComponentTypeMock extends ComponentType {
	public String attributes;
	/**
     * mock object for ComponentType
     */
	public ComponentTypeMock(){
		attributes = "component";
	}
}
