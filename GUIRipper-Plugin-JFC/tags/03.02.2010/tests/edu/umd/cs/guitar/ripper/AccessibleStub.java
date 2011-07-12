package edu.umd.cs.guitar.ripper;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;


/**
 * The AccessibleStub class is a mock object for the class Accessible.  
 * All GUI components inherit from Accessible so this is the 
 * lowest level class that can be used to test GUI components
 *
 */
public class AccessibleStub implements Accessible{


    /**
     * This function implements getAccessibleContext, which returns the 
     * part of the component that is accessible. In this case there is really no 
     * comonent drawn so this is null
     *
     * @return null
     */
	@Override
	public AccessibleContext getAccessibleContext() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
     * This function returns the name of the component
     *
     * @return "Name"
     */
	String getName(){
		return "Name";
	}

}