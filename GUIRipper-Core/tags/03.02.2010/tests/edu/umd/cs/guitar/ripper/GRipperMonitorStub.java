package edu.umd.cs.guitar.ripper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GWindow;

import edu.umd.cs.guitar.ripper.GRipperMonitor;

/**
 * The GRipperMonitorStub class is a stub class for the abstract base
 * class GRipperMonitor.  It is designed to be used in testing wherever
 * a GRipperMonitor instance is required.
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
public class GRipperMonitorStub extends GRipperMonitor {

    /** Return value for calls to member function isNewWindowOpened */
    boolean m_isNewWindowOpened;

    /** Return value for calls to member function isWindowClosed */
    boolean m_isWindowClosed;

    /** Return value for calls to member function isIgnoredWindow */
    boolean m_isIgnoredWindow;

    /** Return value for calls to member function isExpandable */
    boolean m_isExpandable;

    /** Return value for calls to member function getRootWindows */
    LinkedList<GWindow> m_getRootWindows;

    /** Return value for calls to member function getOpenedWindowCache */
    LinkedList<GWindow> m_getOpenedWindowCache;

    /** Return value for calls to member function getClosedWindowCache */
    LinkedList<GWindow> m_getClosedWindowCache;

    /** Creates a GRipperMonitorStub instance and initializes it */
    public GRipperMonitorStub( ) {
	initialize( );
    }

    /**
     * This function initializes the member variables to their default values -
     * false for all boolean members and null for all non-boolean members.
     * Also, it clears the list lRippedWindow in the base class.
     */
    public void initialize( ) {
	m_isNewWindowOpened = false;
	m_isWindowClosed    = false;
	m_isIgnoredWindow   = false;
	m_isExpandable      = false;

	m_getRootWindows       = null;
	m_getOpenedWindowCache = null;
	m_getClosedWindowCache = null;

	lRippedWindow.clear( );
    }
    
    /**
     * This function simulates a call to the abstract base class function
     * getRootWindows.
     *
     * @return m_getRootWindows
     */
    @Override
    public List<GWindow> getRootWindows( ) {
	return m_getRootWindows;
    }

    /**
     * This function simulates a call to the abstract base class function
     * setUp.
     */
    @Override
    public void   setUp( ) { }

    /**
     * This function simulates a call to the abstract base class function
     * cleanUp.
     */
    @Override
    public void cleanUp( ) { }

    /**
     * This function simulates a call to the abstract base class function
     * isNewWindowOpened.
     *
     * @return m_isNewWindowOpened
     */
    @Override
    public boolean isNewWindowOpened( ) {
	return m_isNewWindowOpened;
    }

    /**
     * This function simulates a call to the abstract base class function
     * isWindowClosed.
     *
     * @return m_isWindowClosed
     */
    @Override
    public boolean isWindowClosed( ) {
	return m_isWindowClosed;
    }

    /**
     * This function simulates a call to the abstract base class function
     * getOpenedWindowCache.
     *
     * @return m_getOpenedWindowCache
     */
    @Override
    public LinkedList<GWindow> getOpenedWindowCache( ) {
	return m_getOpenedWindowCache;
    }

    /**
     * This function simulates a call to the abstract base class function
     * resetWindowCache.
     */
    @Override
    public void resetWindowCache( ) { }

    /**
     * This function simulates a call to the abstract base class function
     * closeWindow.
     *
     * @param window The GWindow instance in question.
     */
    @Override
    public void closeWindow( GWindow window ) { }

    /**
     * This function simulates a call to the abstract base class function
     * isIgnoredWindow.
     *
     * @param window The GWindow instance in question.
     *
     * @return m_isIgnoredWindow
     */
    @Override
    public boolean isIgnoredWindow( GWindow window ) {
	return m_isIgnoredWindow;
    }
    
    /**
     * This function simulates a call to the abstract base class function
     * expandUI.
     *
     * @param component The GComponent instance in question.
     */
    @Override
    public void expandGUI( GComponent component ) { }

    /**
     * This function simulates a call to the abstract base class function
     * isExpandable.
     *
     * @param component The GComponent instance in question.
     * @param window The GWindow instance in question.
     *
     * @return m_isExpandable
     */
    @Override
    public boolean isExpandable( GComponent component, GWindow window ) {
	return m_isExpandable;
    }

    /**
     * This function simulates a call to the abstract base class function
     * getClosedWindowCache.
     *
     * @return m_getClosedWindowCache
     */
    @Override
    LinkedList<GWindow> getClosedWindowCache( ) {
	return m_getClosedWindowCache;
    }
}