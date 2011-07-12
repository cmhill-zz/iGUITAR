package edu.umd.cs.guitar.replayer;

import java.util.List;
import java.util.LinkedList;

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
 * The GWindowStub class is a stub class for the abstract base
 * class GWindow.  It is designed to be used in testing wherever
 * a GWindow instance is required.
 *
 * This class is fully customizable at the point of use.  It contains a
 * public member variable for each abstract function with a return value.
 * When such a function is invoked, the corresponding variable is returned.
 * This allows the user to set the expected return value explicitly prior to
 * use.
 *
 */
public class GWindowStub extends GWindow {
    
    /** Return value for calls to member function getFullID */
    String  m_getFullID;

    /** Return value for calls to member function getName */
    String  m_getName;

    /** Return value for calls to member function isModal */
    boolean m_isModal;

    /** Return value for calls to member function equals */
    boolean m_equals;

    /** Return value for calls to member function isValid */
    boolean m_isValid;

    /** Return value for calls to member function extractGUIProperties */
    GUIType m_extractGUIProperties;

    /** Return value for calls to member function extractWindow */
    GUIType m_extractWindow;

    /** Return value for calls to member function getFirstChildByID */
    GComponent m_getFirstChildByID;

    /** Return value for calls to member function getContainer */
    GComponent m_getContainer;

    /** Return value for calls to member function getGUIProperties */
    LinkedList<PropertyType> m_getGUIProperties;

    /** Creates a GWindowStub instance and initializes it */
    public GWindowStub( ) {
	initialize( );

	m_getName   = null;
	m_getFullID = null;
    }

    /**
     * Creates a GWindowStub instance and initializes it.
     *
     * @param p_getName The name and full ID of the created GWindow instance.
     */
    public GWindowStub( String p_getName ) {
	initialize( );
	
	m_getName   = p_getName;
	m_getFullID = p_getName;
	m_getContainer = new GComponentMock();
    }

    /**
     * This function initializes the member variables to their default values -
     * false for all boolean members and null for all non-boolean members.
     */
    public void initialize( ) {
	// m_getFullID = null;
	// m_getName   = null;
	m_isModal   = false;
	m_equals    = false;
	m_isValid   = false;

	m_extractWindow        = null;
	m_extractGUIProperties = null;
	m_getFirstChildByID    = null;
	m_getContainer         = null;
	m_getGUIProperties     = null;
    }

    /**
     * This function simulates a call to the abstract base class function
     * getFullID.
     *
     * @return m_getFullID
     */
    @Override
    public String getFullID( ) {
	return m_getFullID;
    }

    /**
     * This function simulates a call to the abstract base class function
     * getName.
     *
     * @return m_getName
     */
    @Override
    public String getName( ) {
	return m_getName;
    }

    /**
     * This function simulates a call to the abstract base class function
     * getFirstChildByID.
     *
     * @return m_getFirstChildByID
     */
    @Override
    public GComponent getFirstChildByID( String sID ) {
	return m_getFirstChildByID;
    }

    /**
     * This function simulates a call to the abstract base class function
     * getContainer.
     *
     * @return m_getContainer
     */
    @Override
    public GComponent getContainer( ) {
	return m_getContainer;
    }

    /**
     * This function simulates a call to the abstract base class function
     * getGUIProperties.
     *
     * @return m_getGUIProperties
     */
    @Override
    public List<PropertyType> getGUIProperties( ) {
	return m_getGUIProperties;
    }

    /**
     * This function simulates a call to the abstract base class function
     * extractGUIProperties.
     *
     * @return m_extractGUIProperties
     */
    @Override
    public GUIType extractGUIProperties( ) {
	return m_extractGUIProperties;
    }

    /**
     * This function simulates a call to the abstract base class function
     * extractWindow.
     *
     * @return m_extractWindow
     */
    @Override
    public GUIType extractWindow( ) {
	return m_extractWindow;
    }

    /**
     * This function simulates a call to the abstract base class function
     * isModal.
     *
     * @return m_isModal
     */
    @Override
    public boolean isModal( ) {
	return m_isModal;
    }

    /**
     * This function simulates a call to the abstract base class function
     * equals.
     *
     * @param window The Object instance in question.
     *
     * @return m_equals
     */
    @Override
    public boolean equals(Object window) {
	return m_equals;
    }

    /**
     * This function simulates a call to the abstract base class function
     * isValid.
     *
     * @return m_isValid
     */
    @Override
    public boolean isValid( ) {
	return m_isValid;
    }
}