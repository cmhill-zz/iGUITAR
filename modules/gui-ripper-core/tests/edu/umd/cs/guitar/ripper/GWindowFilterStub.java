package edu.umd.cs.guitar.ripper;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.GUIType;

import edu.umd.cs.guitar.ripper.GWindowFilter;

/**
 * The GWindowFilterStub class is a stub class for the abstract base
 * class GWindowFilter.  It is designed to be used in testing wherever
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
public class GWindowFilterStub extends GWindowFilter {

    /** Return value for calls to member function isProcess */
    boolean m_isProcess;

    /** Return value for calls to member function ripWindow */
    GUIType m_ripWindow;

    /** Creates a GWindowFilterStub instance and initializes it */
    public GWindowFilterStub( ) {
	initialize( );
    }

    /**
     * This function initializes the member variables to their default values -
     * false for all boolean members and null for all non-boolean members.
     */
    public void initialize( ) {
	m_isProcess = false;
	m_ripWindow = null;
    }

    /**
     * This function simulates a call to the abstract base class function
     * isProcess.
     *
     * @param window The GWindow instance in question.
     *
     * @return m_isProcess
     */
    @Override
    public boolean isProcess( GWindow window ) {
	return m_isProcess;
    }

    /**
     * This function simulates a call to the abstract base class function
     * ripWindow.
     *
     * @param window The GWindow instance in question.
     *
     * @return m_ripWindow
     */
    @Override
    public GUIType ripWindow( GWindow window ) {
	return m_ripWindow;
    }
}