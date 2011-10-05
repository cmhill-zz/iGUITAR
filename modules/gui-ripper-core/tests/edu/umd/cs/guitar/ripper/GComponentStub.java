package edu.umd.cs.guitar.ripper;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.ContentsType;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.model.wrapper.PropertyTypeWrapper;

import edu.umd.cs.guitar.model.GComponent;

/**
 * The GComponentStub class is a stub class for the abstract base
 * class GComponent.  It is designed to be used in testing wherever
 * a GComponent instance is required.
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
public class GComponentStub extends GComponent {

    /** Return value for calls to member function getName */
    String  m_getName;

    /** Return value for calls to member function getFullID */
    String  m_getFullID;

    /** Return value for calls to member function getClassVal */
    String  m_getClassVal;

    /** Return value for calls to member function getTypeVal */
    String  m_getTypeVal;

    /** Return value for calls to member function hasChildren */
    boolean m_hasChildren;

    /** Return value for calls to member function isTerminal */
    boolean m_isTerminal;

    /** Return value for calls to member function getChildren */
    LinkedList<GComponent  > m_getChildren;

    /** Return value for calls to member function getEventList */
    LinkedList<GEvent      > m_getEventList;

    /** Return value for calls to member function getGUIProperties */
    LinkedList<PropertyType> m_getGUIProperties;

    /** Return value for calls to member function getParent */
    GComponent m_getParent;

    /** Return value for calls to member function extractProperties */
    ComponentType m_extractProperties;

    /** Creates a GComponentStub instance and initializes it */
    public GComponentStub( ) {
	initialize( );
    }

    /**
     * This function initializes the member variables to their default values -
     * null for all non-boolean members and variable for boolean members.
     */
    public void initialize( ) {
	m_getName     = null;
	m_getFullID   = null;
	m_getClassVal = null;
	m_getTypeVal  = null;

	m_hasChildren = false;
	m_isTerminal  = true;

	m_getChildren       = null;
	m_getEventList      = null;
	m_getGUIProperties  = null;
	m_extractProperties = null;

	m_getParent = null;
    }
    
    /**
     * This function simulates a call to the abstract base class function
     * getName.
     *
     * @return m_getName
     */
    public String getName( ) {
	return m_getName;
    }
	
	public boolean isEnable(){
	return false;//needs to be changed to a real value
	}

    /**
     * This function simulates a call to the abstract base class function
     * getFullID
     *
     * @return m_getFullID
     */
    public String getFullID( ) {
	return m_getFullID;
    }

    /**
     * This function simulates a call to the abstract base class function
     * getChildren.
     *
     * @return m_getChildren
     */
    @Override
    public List<GComponent> getChildren( ) {
	return m_getChildren;
    }

    /**
     * This function simulates a call to the abstract base class function
     * getClassVal.
     *
     * @return m_getClassVal
     */
    @Override
    public String getClassVal( ) {
	return m_getClassVal;
    }

    /**
     * This function simulates a call to the abstract base class function
     * getEventList
     *
     * @return m_getEventList
     */
    @Override
    public List<GEvent> getEventList( ) {
	return m_getEventList;
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
     * getParent.
     *
     * @return m_getParent
     */
    @Override
    public GComponent getParent( ) {
	return m_getParent;
    }

    /**
     * This function simulates a call to the abstract base class function
     * getTypeVal.
     *
     * @return m_getTypeVal
     */
    @Override
    public String getTypeVal( ) {
	return m_getTypeVal;
    }

    /**
     * This function simulates a call to the abstract base class function
     * hasChildren.
     *
     * @return m_hasChildren
     */
    @Override
    public boolean hasChildren( ) {
	return m_hasChildren;
    }

    /**
     * This function simulates a call to the abstract base class function
     * isTerminal.
     *
     * @return m_isTerminal
     */
    @Override
    public boolean isTerminal( ) {
	return m_isTerminal;
    }

    /**
     * This function simulates a call to the abstract base class function
     * extractProperties.
     *
     * @return m_extractProperties
     */
    @Override
    public ComponentType extractProperties( ) {
	return m_extractProperties;
    }
	
	public String getTitle(){
	return null;//needs to be changed to a real value
	}
}