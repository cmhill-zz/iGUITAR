package edu.umd.cs.guitar.ripper;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.ripper.GComponentFilter;

/**
 * The GComponentFilterStub class is a stub class for the abstract base
 * class GComponentFilter.  It is designed to be used in testing wherever
 * a GComponentFilter instance is required.
 *
 * This class is fully customizable at the point of use.  It contains a
 * public member variable for each abstract function with a return value.
 * When such a function is invoked, the corresponding variable is returned.
 * This allows the user to set the expected return value explicitly prior to
 * use.
 *
 * This class does not override those functions in the abstract base class
 * for which definitions are already present.
 */
public class GComponentFilterStub extends GComponentFilter {
    
    /** Return value for calls to member function isProcess */
    boolean       m_isProcess;

    /** Return value for calls to member function ripComponent */
    ComponentType m_ripComponent;

    /** Creates a GComponentFilterStub instance and initializes it */
    public GComponentFilterStub( ) {
	initialize( );
    }

    /**
     * This function initializes the member variables to their default values -
     * true for all boolean members and null for all non-boolean members.
     */
    public void initialize( ) {
	m_isProcess    = true;
	m_ripComponent = null;
    }

    /**
     * This function simulates a call to the abstract base class function
     * isProcess.
     *
     * @param component The GComponent instance in question.
     * @param window The GWindow instance in question.
     *
     * @return m_isProcess
     */
    public boolean isProcess( GComponent component, GWindow window ) {
	return m_isProcess;
    }

    /**
     * This function simulates a call to the abstract base class function
     * ripComponent.
     *
     * @param component The GComponent instance in question.
     * @param window The GWindow instance in question.
     *
     * @return m_ripComponent
     */
    public ComponentType ripComponent( GComponent component, GWindow window ) {
	return m_ripComponent;
    }

	public boolean isEnable(){
		return false;//needs to be changed to a real value
	}
}