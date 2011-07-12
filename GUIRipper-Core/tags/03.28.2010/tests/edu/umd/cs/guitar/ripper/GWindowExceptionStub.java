package edu.umd.cs.guitar.ripper;

import java.util.List;

import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.ContentsType;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GWindow;

/**
 * The GWindowExceptionStub class is a stub class for the abstract base class
 * GWindow.  It can be used wherever a GWindow instance is required.
 *
 * This class is designed to throw a RuntimeException on the call to
 * any overridden member function.  This makes it useful for getting into
 * the exception handling code of various functions.  The only exception
 * to this rule is the getFullID function, which returns a constant
 * String value.
 */
public class GWindowExceptionStub extends GWindow {
    
    /**
     * This function simulates a call to the abstract base class function
     * getFullID.
     *
     * @return "GWindowExceptionStub"
     */
    public String getFullID( ) {
	return "GWindowExceptionStub";
    }

    /**
     * This function simulates a call to the abstract base class function
     * getName.
     *
     * @throws RuntimeException
     */
    public String getName( ) {
	throw new RuntimeException( );
    }

    /**
     * This function simulates a call to the abstract base class function
     * getFirstChildByID.
     *
     * @throws RuntimeException
     */
    @Override
    public GComponent getFirstChildByID( String sID ) {
	throw new RuntimeException( );
    }

    /**
     * This function simulates a call to the abstract base class function
     * getContainer.
      *
     * @throws RuntimeException
    */
    @Override
    public GComponent getContainer( ) {
	throw new RuntimeException( );
    }

    /**
     * This function simulates a call to the abstract base class function
     * getGUIProperties.
     *
     * @throws RuntimeException
     */
    @Override
    public List<PropertyType> getGUIProperties( ) {
	throw new RuntimeException( );
    }

    /**
     * This function simulates a call to the abstract base class function
     * extractGUIProperties.
     *
     * @throws RuntimeException
     */
    @Override
    public GUIType extractGUIProperties( ) {
	throw new RuntimeException( );
    }

    /**
     * This function simulates a call to the abstract base class function
     * isModal.
     *
     * @throws RuntimeException
     */
    @Override
    public boolean isModal( ) {
	throw new RuntimeException( );
    }

    /**
     * This function simulates a call to the abstract base class function
     * equals.
     *
     * @throws RuntimeException
     */
    @Override
    public boolean equals(Object window) {
	throw new RuntimeException( );
    }

    /**
     * This function simulates a call to the abstract base class function
     * isValid.
     *
     * @throws RuntimeException
     */
    @Override
    public boolean isValid( ) {
	throw new RuntimeException( );
    }
	
	public String getTitle(){
	return null;//needs to be changed to real value
	}
}