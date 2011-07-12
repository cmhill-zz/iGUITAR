package edu.umd.cs.guitar.ripper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.data.ComponentListType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.Configuration;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.ContentsType;
import edu.umd.cs.guitar.model.data.FullComponentType;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.util.Debugger;
import edu.umd.cs.guitar.util.GUITARLog;

import edu.umd.cs.guitar.ripper.GRipperMonitorStub;
import edu.umd.cs.guitar.ripper.GWindowFilterStub;
import edu.umd.cs.guitar.ripper.GComponentFilterStub;
import edu.umd.cs.guitar.ripper.GComponentStub;

import junit.framework.TestCase;

/**
 * The RipperTest class implements unit tests for the member
 * functions defined in the class Ripper.
 */
public class RipperTest extends TestCase {

    /** The Ripper instance used in the unit testing */
    private Ripper m_ripper;

    /** A Logger instance for use in testing */
    private Logger m_logger1;

    /** A Logger instance for use in testing */
    private Logger m_logger2;

    /** A GRipperMonitorStub instance for use in testing */
    private GRipperMonitorStub m_monitor1;

    /** A GRipperMonitorStub instance for use in testing */
    private GRipperMonitorStub m_monitor2;

    /** A GWindowFilterStub instance for use in testing */
    private GWindowFilterStub  m_wfilter1;

    /** A GWindowFilterStub instance for use in testing */
    private GWindowFilterStub  m_wfilter2;

    /** A GComponentFilterStub instance for use in testing */
    private GComponentFilterStub  m_cfilter1;

    /** A GComponentFilterStub instance for use in testing */
    private GComponentFilterStub  m_cfilter2;

    /** A GWindowStub instance for use in testing */
    private GWindowStub m_window1;

    /** A GWindowStub instance for use in testing */
    private GWindowStub m_window2;

    /** A GWindowExceptionStub instance for use in testing */
    private GWindowExceptionStub m_except;

    /**
     * This function is part of the JUnit framework.  It performs any
     * initialization necessary to perform the tests.  In this case, it
     * initializes the member variables.
     */
    @Override
    public void setUp( ) {
	m_ripper   = new Ripper( );

	m_logger1  = Logger.getLogger( "Logger1" );
	m_logger2  = Logger.getLogger( "Logger2" );

	m_window1  = new GWindowStub( "Window1" );
	m_window2  = new GWindowStub( "Window2" );

	m_except   = new GWindowExceptionStub( );

	m_monitor1 = new GRipperMonitorStub( );
	m_monitor2 = new GRipperMonitorStub( );

	m_wfilter1 = new GWindowFilterStub( );
	m_wfilter2 = new GWindowFilterStub( );

	m_cfilter1 = new GComponentFilterStub( );
	m_cfilter2 = new GComponentFilterStub( );
    }

    /**
     * This function checks that a Ripper instance has the expected state
     * immediately after construction using the default constructor.
     */
    public void test00( ) {
	assertEquals((m_ripper != null), true );

	Logger l_logger = m_ripper.log;

	ComponentListType l_openw  = m_ripper.lOpenWindowComps;
	ComponentListType l_closew = m_ripper.lCloseWindowComp;

	Set<GWindow> l_ripped = m_ripper.lRippedWindow;

	GRipperMonitor l_monitor = m_ripper.monitor;
	ObjectFactory  l_factory = m_ripper.factory;

	GUIStructure  l_struct = m_ripper.dGUIStructure;
	Configuration l_config = m_ripper.configuration;

	LinkedList<GComponentFilter> l_cfilter = m_ripper.lComponentFilter;
	LinkedList<GWindowFilter   > l_wfilter = m_ripper.lWindowFilter;

	assertEquals( l_monitor, null );

	assertEquals((l_logger  != null), true );
	assertEquals((l_openw   != null), true );
	assertEquals((l_closew  != null), true );
	assertEquals((l_ripped  != null), true );
	assertEquals((l_factory != null), true );
	assertEquals((l_struct  != null), true );
	assertEquals((l_config  != null), true );
	assertEquals((l_cfilter != null), true );
	assertEquals((l_wfilter != null), true );
    }

    /**
     * This function tests that a Ripper instance has the expected state
     * immediately after construction using the Ripper::Ripper(Logger)
     * constructor.
     */
    public void test01( ) {
	Ripper l_ripper = new Ripper( m_logger1 );

	assertEquals((l_ripper != null), true );

	Logger l_logger = l_ripper.log;

	ComponentListType l_openw  = l_ripper.lOpenWindowComps;
	ComponentListType l_closew = l_ripper.lCloseWindowComp;

	Set<GWindow> l_ripped = l_ripper.lRippedWindow;

	GRipperMonitor l_monitor = l_ripper.monitor;
	ObjectFactory  l_factory = l_ripper.factory;

	GUIStructure  l_struct = l_ripper.dGUIStructure;
	Configuration l_config = l_ripper.configuration;

	LinkedList<GComponentFilter> l_cfilter = l_ripper.lComponentFilter;
	LinkedList<GWindowFilter   > l_wfilter = l_ripper.lWindowFilter;

	assertEquals( l_monitor, null      );
	assertSame  ( l_logger , m_logger1 );

	assertEquals((l_openw   != null), true );
	assertEquals((l_closew  != null), true );
	assertEquals((l_ripped  != null), true );
	assertEquals((l_factory != null), true );
	assertEquals((l_struct  != null), true );
	assertEquals((l_config  != null), true );
	assertEquals((l_cfilter != null), true );
	assertEquals((l_wfilter != null), true );
    }

    /**
     * This function tests the Ripper::setMonitor function.
     * It addresses the following cases:
     *
     * 1. The Monitor member is null and set to null.
     * 2. The Monitor member is null and set to non-null.
     * 3. The Monitor member is non-null and set to a different non-null.
     * 4. The Monitor member is non-null and set to null.
     */
    public void test02( ) {
	assertEquals( m_ripper.monitor, null );

	m_ripper.setMonitor( null );

	assertEquals( m_ripper.monitor, null );

	m_ripper.setMonitor( m_monitor1 );

	assertSame( m_ripper.monitor, m_monitor1 );

	m_ripper.setMonitor( m_monitor2 );

	assertSame( m_ripper.monitor, m_monitor2 );

	m_ripper.setMonitor( null );

	assertEquals( m_ripper.monitor, null );
    }

    /**
     * This function tests the Ripper::setLog function.
     * It addresses the following cases:
     *
     * 1. The Logger member is null and set to null.
     * 2. The Logger member is null and set to non-null.
     * 3. The Logger member is non-null and set to a different non-null.
     * 4. The Logger member is non-null and set to null.
     */
    public void test03( ) {
	assertEquals((m_ripper.log != null), true );

	m_ripper.setLog( null );

	assertEquals( m_ripper.log, null );

	m_ripper.setLog( null );

	assertEquals( m_ripper.log, null );

	m_ripper.setLog( m_logger1 );

	assertSame( m_ripper.log, m_logger1 );

	m_ripper.setLog( m_logger2 );

	assertSame( m_ripper.log, m_logger2 );
    }

    /**
     * This function tests the Ripper::getLog function.
     * It addresses the following cases:
     *
     * 1. The Logger member is null.
     * 2. The Logger member is non-null.
     */
    public void test04( ) {
	Logger l_logger;

	m_ripper.log = null;
	l_logger = m_ripper.getLog( );

	assertEquals( l_logger, null );

	m_ripper.log = m_logger1;
	l_logger = m_ripper.getLog( );

	assertSame( l_logger, m_logger1 );
    }

    /**
     * This function tests the Ripper::addWindowFilter function in the case
     * when Ripper::lWindowFilter is initially non-null.  It addresses the
     * following cases:
     *
     * (1) Ripper::lWindowFilter is empty.
     * (2) Ripper::lWindowFilter is non-empty and does not contain the
     *     GWindowFilter instance being added.
     * (3) Ripper::lWindowFilter is non-empty and contains the GWindowFilter
     *     instance begin added.
     * (4) The GWindowFilter instance being added is null.
     */
    public void test05( ) {
	assertEquals((m_ripper.lWindowFilter != null), true );

	m_ripper.addWindowFilter( m_wfilter1 );

	assertSame( m_ripper.lWindowFilter.getLast( ), m_wfilter1 );

	m_ripper.addWindowFilter( m_wfilter1 );

	assertSame( m_ripper.lWindowFilter.getLast( ), m_wfilter1 );

	m_ripper.addWindowFilter( m_wfilter2 );

	assertSame( m_ripper.lWindowFilter.getLast( ), m_wfilter2 );

	try {
	    m_ripper.addWindowFilter( null );

	    fail( "Expected NullPointerException" );
	}
	catch( Exception e ) {
	}

	m_ripper.lWindowFilter.clear( );
    }

    /**
     * This function tests the Ripper::addWindowFilter function in the case
     * when Ripper::lWindowFilter is initially null.  It addresses the
     * following cases:
     *
     * (1) Ripper::lWindowFilter is empty.
     * (2) Ripper::lWindowFilter is non-empty and does not contain the
     *     GWindowFilter instance being added.
     * (3) Ripper::lWindowFilter is non-empty and contains the GWindowFilter
     *     instance begin added.
     * (4) The GWindowFilter instance being added is null.
     */
    public void test06( ) {
	m_ripper.lWindowFilter = null;

	assertEquals( m_ripper.lWindowFilter, null );

	m_ripper.addWindowFilter( m_wfilter1 );

	assertSame( m_ripper.lWindowFilter.getLast( ), m_wfilter1 );

	m_ripper.addWindowFilter( m_wfilter1 );

	assertSame( m_ripper.lWindowFilter.getLast( ), m_wfilter1 );

	m_ripper.addWindowFilter( m_wfilter2 );

	assertSame( m_ripper.lWindowFilter.getLast( ), m_wfilter2 );

	assertEquals( m_ripper.lWindowFilter.contains( m_wfilter1 ), true );
	assertEquals( m_ripper.lWindowFilter.contains( m_wfilter2 ), true );

	try {
	    m_ripper.addWindowFilter( null );

	    fail( "Expected NullPointerException" );
	}
	catch( Exception e ) {
	}

	m_ripper.lWindowFilter.clear( );	
    }

    /**
     * This function tests the Ripper::removeWindowFilter function in the
     * case when Ripper::lWindowFilter is non-null initially.  It addresses
     * the following cases:
     *
     * (1) Ripper::lWindowFilter is empty.
     * (2) Ripper::lWindowFilter is non-empty and does not contain the
     *     GWindowFilter instance in question.
     * (3) Ripper::lWindowFilter is non-empty and contains the
     *     GWindowFilter instance in question.
     * (4) The GWindowFilter instance in question is null.
     */
    public void test07( ) {
	assertEquals((m_ripper.lWindowFilter != null), true );
	
	/* Try removing a null element */

	try {
	    m_ripper.removeWindowFilter( null );
	    
	    fail( "Expected NullPointerException" );
	}
	catch( Exception e ) {
	}

	/* Try removing from an empty list */

	assertEquals( m_ripper.lWindowFilter.size( ), 0 );

	m_ripper.removeWindowFilter( m_wfilter1 );
	
	assertEquals( m_ripper.lWindowFilter.size( ), 0 );

	/* Add a couple elements to the list */

	m_ripper.lWindowFilter.addLast( m_wfilter1 );
	m_ripper.lWindowFilter.addLast( m_wfilter2 );

	assertEquals( m_ripper.lWindowFilter.contains( m_wfilter1 ), true );
	assertEquals( m_ripper.lWindowFilter.contains( m_wfilter2 ), true );

	m_ripper.removeWindowFilter( m_wfilter1 );

	assertEquals( m_ripper.lWindowFilter.contains( m_wfilter1 ), false );
	assertEquals( m_ripper.lWindowFilter.contains( m_wfilter2 ), true  );
	
	m_ripper.removeWindowFilter( m_wfilter2 );

	assertEquals( m_ripper.lWindowFilter.contains( m_wfilter1 ), false );
	assertEquals( m_ripper.lWindowFilter.contains( m_wfilter2 ), false );

	assertEquals( m_ripper.lWindowFilter.size( ), 0 );

	m_ripper.lWindowFilter.clear( );
    }

    /**
     * This function tests the Ripper::removeWindowFilter function in the
     * case when Ripper::lWindowFilter is null initially.  It expects
     * an Exception instance to be thrown.
     */
    public void test08( ) {
	LinkedList<GWindowFilter> l_wfilter = m_ripper.lWindowFilter;

	m_ripper.lWindowFilter = null;

	try {
	    m_ripper.removeWindowFilter( null );

	    fail( "Expected exception." );
	}
	catch( Exception e ) {
	}

	m_ripper.lWindowFilter = l_wfilter;

	assertEquals(( m_ripper.lWindowFilter != null), true );
    }

    /**
     * Test the Ripper::addWindowFilter and Ripper::removeWindowFilter
     *   functions in tandem.
     *
     * This test raises a question about the removeWindowFilter function.
     * Are we allowed to have multiple references to the same filter
     * instance in the window filter list?  If so, when we remove that
     * filter, should we remove all references to it or only one?
     *
     * The test assumes that only one reference to a given GWindowFilter
     * instance is desirable and that a request to remove a given
     * GWindowFilter instance should remove all references to that instance.
     */
    public void test08_5( ) {
	boolean l_bool;
	LinkedList<GWindowFilter> l_wfilter = m_ripper.lWindowFilter;

	/* Make sure neither filter is there */

	l_bool = l_wfilter.contains( m_wfilter1 );

	assertEquals( l_bool, false );

	l_bool = l_wfilter.contains( m_wfilter2 );

	assertEquals( l_bool, false );

	/* Add the first filter */

	m_ripper.addWindowFilter( m_wfilter1 );

	l_bool = l_wfilter.contains( m_wfilter1 );

	assertEquals( l_bool, true );

	l_bool = l_wfilter.contains( m_wfilter2 );

	assertEquals( l_bool, false );

	/* Add the second filter */

	m_ripper.addWindowFilter( m_wfilter2 );

	l_bool = l_wfilter.contains( m_wfilter1 );

	assertEquals( l_bool, true );

	l_bool = l_wfilter.contains( m_wfilter2 );

	assertEquals( l_bool, true );

	/* Add a copy of the first filter */

	m_ripper.addWindowFilter( m_wfilter1 );

	l_bool = l_wfilter.contains( m_wfilter1 );

	assertEquals( l_bool, true );

	l_bool = l_wfilter.contains( m_wfilter2 );

	assertEquals( l_bool, true );

	/* Remove the first filter */

	m_ripper.removeWindowFilter( m_wfilter1 );

	l_bool = l_wfilter.contains( m_wfilter1 );

	assertEquals( l_bool, false );

	l_bool = l_wfilter.contains( m_wfilter2 );

	assertEquals( l_bool, true );

	/* Remove the second filter */

	m_ripper.removeWindowFilter( m_wfilter2 );

	l_bool = l_wfilter.contains( m_wfilter1 );

	assertEquals( l_bool, false );

	l_bool = l_wfilter.contains( m_wfilter2 );

	assertEquals( l_bool, false );
    }

    /**
     * This function tests the Ripper::addComponentFilter function in the case
     * when Ripper::lComponentFilter is initially non-null.  It addresses the
     * following cases:
     *
     * (1) Ripper::lComponentFilter is empty.
     * (2) Ripper::lComponentFilter is non-empty and does not contain the
     *     GComponentFilter instance being added.
     * (3) Ripper::lComponentFilter is non-empty and contains the GComponentFilter
     *     instance begin added.
     * (4) The GComponentFilter instance being added is null.
     */
     public void test09( ) {
	assertEquals((m_ripper.lComponentFilter != null), true );

	m_ripper.addComponentFilter( m_cfilter1 );

	assertSame( m_ripper.lComponentFilter.getLast( ), m_cfilter1 );

	m_ripper.addComponentFilter( m_cfilter1 );

	assertSame( m_ripper.lComponentFilter.getLast( ), m_cfilter1 );

	m_ripper.addComponentFilter( m_cfilter2 );

	assertSame( m_ripper.lComponentFilter.getLast( ), m_cfilter2 );

	try {
	    m_ripper.addComponentFilter( null );

	    fail( "Expected NullPointerException" );
	}
	catch( Exception e ) {
	}

	m_ripper.lComponentFilter.clear( );
    }

    /**
     * This function tests the Ripper::addComponentFilter function in the case
     * when Ripper::lComponentFilter is initially null.  It addresses the
     * following cases:
     *
     * (1) Ripper::lComponentFilter is empty.
     * (2) Ripper::lComponentFilter is non-empty and does not contain the
     *     GComponentFilter instance being added.
     * (3) Ripper::lComponentFilter is non-empty and contains the
     *     GComponentFilter instance being added.
     * (4) The GComponentFilter instance being added is null.
     */
    public void test10( ) {
	m_ripper.lComponentFilter = null;

	assertEquals( m_ripper.lComponentFilter, null );

	m_ripper.addComponentFilter( m_cfilter1 );

	assertSame( m_ripper.lComponentFilter.getLast( ), m_cfilter1 );

	m_ripper.addComponentFilter( m_cfilter1 );

	assertSame( m_ripper.lComponentFilter.getLast( ), m_cfilter1 );

	m_ripper.addComponentFilter( m_cfilter2 );

	assertSame( m_ripper.lComponentFilter.getLast( ), m_cfilter2 );

	assertEquals( m_ripper.lComponentFilter.contains( m_cfilter1 ), true );
	assertEquals( m_ripper.lComponentFilter.contains( m_cfilter2 ), true );

	try {
	    m_ripper.addComponentFilter( null );

	    fail( "Expected NullPointerException" );
	}
	catch( Exception e ) {
	}

	m_ripper.lComponentFilter.clear( );	
    }

    /**
     * This function tests the Ripper::removeComponentFilter function in the
     * case when Ripper::lComponentFilter is non-null initially.  It addresses
     * the following cases:
     *
     * (1) Ripper::lComponentFilter is empty.
     * (2) Ripper::lComponentFilter is non-empty and does not contain the
     *     GComponentFilter instance in question.
     * (3) Ripper::lComponentFilter is non-empty and contains the
     *     GComponentFilter instance in question.
     * (4) The GComponentFilter instance in question is null.
     */
    public void test11( ) {
	assertEquals((m_ripper.lComponentFilter != null), true );
	
	/* Try removing a null element */

	try {
	    m_ripper.removeComponentFilter( null );
	    
	    fail( "Expected NullPointerException" );
	}
	catch( Exception e ) {
	}

	/* Try removing from an empty list */

	assertEquals( m_ripper.lComponentFilter.size( ), 0 );

	m_ripper.removeComponentFilter( m_cfilter1 );
	
	assertEquals( m_ripper.lComponentFilter.size( ), 0 );

	/* Add a couple elements to the list */

	m_ripper.lComponentFilter.addLast( m_cfilter1 );
	m_ripper.lComponentFilter.addLast( m_cfilter2 );

	assertEquals( m_ripper.lComponentFilter.contains( m_cfilter1 ), true );
	assertEquals( m_ripper.lComponentFilter.contains( m_cfilter2 ), true );

	m_ripper.removeComponentFilter( m_cfilter1 );

	assertEquals( m_ripper.lComponentFilter.contains( m_cfilter1 ), false );
	assertEquals( m_ripper.lComponentFilter.contains( m_cfilter2 ), true  );
	
	m_ripper.removeComponentFilter( m_cfilter2 );

	assertEquals( m_ripper.lComponentFilter.contains( m_cfilter1 ), false );
	assertEquals( m_ripper.lComponentFilter.contains( m_cfilter2 ), false );

	assertEquals( m_ripper.lComponentFilter.size( ), 0 );

	m_ripper.lComponentFilter.clear( );
    }

    /**
     * This function tests the Ripper::removeComponentFilter function in the
     * case when Ripper::lComponentFilter is null initially.  It expects
     * an Exception instance to be thrown.
     */
    public void test12( ) {
	LinkedList<GComponentFilter> l_cfilter = m_ripper.lComponentFilter;

	m_ripper.lComponentFilter = null;

	try {
	    m_ripper.removeComponentFilter( null );

	    fail( "Expected exception." );
	}
	catch( Exception e ) {
	}

	m_ripper.lComponentFilter = l_cfilter;

	assertEquals(( m_ripper.lComponentFilter != null), true );
    }

    /**
     * Test the Ripper::addComponentFilter and Ripper::removeComponentFilter
     *   functions in tandem.
     *
     * This test raises a question about the removeComponentFilter function.
     * Are we allowed to have multiple references to the same filter
     * instance in the window filter list?  If so, when we remove that
     * filter, should we remove all references to it or only one?
     *
     * The test assumes that only one reference to a given GComponentFilter
     * instance is desirable and that a request to remove a given
     * GComponentFilter instance should remove all references to that instance.
     */
    public void test12_5( ) {
	boolean l_bool;
	LinkedList<GComponentFilter> l_cfilter = m_ripper.lComponentFilter;

	/* Make sure neither filter is there */

	l_bool = l_cfilter.contains( m_cfilter1 );

	assertEquals( l_bool, false );

	l_bool = l_cfilter.contains( m_cfilter2 );

	assertEquals( l_bool, false );

	/* Add the first filter */

	m_ripper.addComponentFilter( m_cfilter1 );

	l_bool = l_cfilter.contains( m_cfilter1 );

	assertEquals( l_bool, true );

	l_bool = l_cfilter.contains( m_cfilter2 );

	assertEquals( l_bool, false );

	/* Add the second filter */

	m_ripper.addComponentFilter( m_cfilter2 );

	l_bool = l_cfilter.contains( m_cfilter1 );

	assertEquals( l_bool, true );

	l_bool = l_cfilter.contains( m_cfilter2 );

	assertEquals( l_bool, true );

	/* Add a copy of the first filter */

	m_ripper.addComponentFilter( m_cfilter1 );

	l_bool = l_cfilter.contains( m_cfilter1 );

	assertEquals( l_bool, true );

	l_bool = l_cfilter.contains( m_cfilter2 );

	assertEquals( l_bool, true );

	/* Remove the first filter */

	m_ripper.removeComponentFilter( m_cfilter1 );

	l_bool = l_cfilter.contains( m_cfilter1 );

	assertEquals( l_bool, false );

	l_bool = l_cfilter.contains( m_cfilter2 );

	assertEquals( l_bool, true );

	/* Remove the second filter */

	m_ripper.removeComponentFilter( m_cfilter2 );

	l_bool = l_cfilter.contains( m_cfilter1 );

	assertEquals( l_bool, false );

	l_bool = l_cfilter.contains( m_cfilter2 );

	assertEquals( l_bool, false );
    }

    /**
     * This function tests the Ripper::getlOpenWindowComps function.
     * It addresses the following cases:
     *
     * 1. The lOpenWindowComps member is null.
     * 2. The lOpenWindowComps member is non-null.
     */
    public void test13( ) {
	ComponentListType l_list = m_ripper.lOpenWindowComps;

	assertSame( m_ripper.getlOpenWindowComps( ), l_list );

	m_ripper.lOpenWindowComps = null;

	assertEquals( m_ripper.getlOpenWindowComps( ), null );

	m_ripper.lOpenWindowComps = l_list;
    }

    /**
     * This function tests the Ripper::getlCloseWindowComp function.
     * It addresses the following cases:
     *
     * 1. The lCloseWindowComp member is null.
     * 2. The lCloseWindowComp member is non-null.
     */
    public void test14( ) {
	ComponentListType l_list = m_ripper.lCloseWindowComp;

	assertSame( m_ripper.getlCloseWindowComp( ), l_list );

	m_ripper.lCloseWindowComp = null;

	assertEquals( m_ripper.getlCloseWindowComp( ), null );

	m_ripper.lCloseWindowComp = l_list;
    }

    /**
     * This function tests the Ripper::getResult function.
     * It addresses the following cases:
     *
     * 1. The dGUIStructure member is null.
     * 2. The dGUIStructure member is non-null.
     */
    public void test15( ) {
	GUIStructure l_guis = m_ripper.dGUIStructure;

	assertSame( m_ripper.getResult( ), l_guis );

	m_ripper.dGUIStructure = null;

	assertEquals( m_ripper.getResult( ), null );

	m_ripper.dGUIStructure = l_guis;
    }

    /**
     * Test the Ripper::ripWindow function.
     *
     * This test tests the case when Ripper::lWindowFilter contains a single
     * GWindowFilter that returns true for the GWindow argument.
     * Ripper::ripWindow should never exit the initial for loop.
     */
    public void test16( ) {
	/* Set up the test */

	GUIType l_guit = new GUIType( );

	m_window1.initialize( );
	
	m_wfilter1.initialize( );
	m_wfilter1.m_isProcess = true;
	m_wfilter1.m_ripWindow = l_guit;

	m_ripper.lWindowFilter.addLast( m_wfilter1 );

	/* Execute the test */

	assertSame( m_ripper.ripWindow( m_window1 ), l_guit );

	/* Restore Ripper state */

	m_ripper.lWindowFilter.clear( );
    }

    /**
     * Test the Ripper::ripWindow function.
     *
     * This test tests the case when Ripper::lWindowFilter contains one
     * GWindowFilter that returns false for the GWindow argument followed by
     * one that returns true.  Ripper::ripWindow should never exit the
     * initial for loop.
     */
    public void test17( ) {
	/* Set up the test */

	GUIType l_guit = new GUIType( );

	m_window1.initialize( );

	m_wfilter1.initialize( );
	m_wfilter2.initialize( );

	m_wfilter1.m_isProcess = false;
	m_wfilter2.m_isProcess = true;
	m_wfilter2.m_ripWindow = l_guit;

	m_ripper.lWindowFilter.addLast( m_wfilter1 );
	m_ripper.lWindowFilter.addLast( m_wfilter2 );

	/* Execute the test */

	assertSame( m_ripper.ripWindow( m_window1 ), l_guit );

	/* Restore Ripper state */

	m_ripper.lWindowFilter.clear( );
    }

    /**
     * Test the Ripper::ripWindow function.
     *
     * This test tests the case when Ripper::lWindowFilter contains one
     * GWindowFilter that returns false for the GWindow argument.
     * Ripper::ripWindow should pass through to the try block.  There it
     * should traverse the false branch of each predicate.
     */
    public void test18( ) {
	/* Set up the test */

	GUIType       l_guit    = new GUIType      ( );
	ContainerType l_contain = new ContainerType( );
	ContentsType  l_content = new ContentsType ( );

	List<ComponentType> l_list = new LinkedList<ComponentType>( );

	l_content.setWidgetOrContainer( l_list );
	l_contain.setContents( l_content );
	l_guit.setContainer  ( l_contain );

	m_wfilter1.initialize( );
	m_wfilter1.m_isProcess = false;
	
	m_window1.initialize( );
	m_window1.m_extractWindow = l_guit;
	m_window1.m_getContainer  = null;

	m_ripper.lWindowFilter.addLast( m_wfilter1 );

	/* Execute the test */

	assertSame( m_ripper.ripWindow( m_window1 ), l_guit );
	assertEquals( l_list.size( ), 0 );

	/* Restore Ripper state */

	m_ripper.lWindowFilter.clear( );
    }

    /**
     * Test the Ripper::ripWindow function.
     *
     * This test assumes Ripper::lWindowFilter is empty.  It is designed
     * to test the true path for each of the branches in the try block.
     */
    public void test20( ) {
	/* Set up the test */

	GUIType       l_guit    = new GUIType       ( );
	GComponent    l_comp    = new GComponentStub( );
	ComponentType l_cont    = new ComponentType ( );
	ContainerType l_contain = new ContainerType ( );
	ContentsType  l_content = new ContentsType  ( );

	List<ComponentType> l_list = new LinkedList<ComponentType>( );

	l_content.setWidgetOrContainer( l_list );
	l_contain.setContents( l_content );
	l_guit.setContainer  ( l_contain );

	m_window1.initialize( );
	m_window1.m_extractWindow = l_guit;
	m_window1.m_getContainer  = l_comp;

	m_cfilter1.initialize( );
	m_cfilter1.m_isProcess    = true;
	m_cfilter1.m_ripComponent = l_cont;

	m_ripper.lComponentFilter.addLast( m_cfilter1 );

	/* Execute the test */

	GUIType l_result = m_ripper.ripWindow( m_window1 );

	/* Verify the behavior */

	assertSame( l_result, l_guit );

	assertEquals( l_list.size( ), 1 );
	assertEquals( l_list.contains( l_cont ), true );

	/* Reset the state */

	m_ripper.lComponentFilter.clear( );
    }

    /**
     * Test the Ripper::ripWindow function.
     *
     * This test is designed to exercise the exception code in the
     * Ripper::ripWindow function.  It uses a GWindow stub that throws an
     * exception on every member function call.
     */

    public void test21( ) {
	assertEquals( m_ripper.ripWindow( m_except ), null );
    }

    /**
     * Test the Ripper::execute function.
     *
     * This test assumes Ripper::monitor is null.  It is designed to
     * test the true path of the first conditional.
     */

    public void test22( ) {
	m_ripper.monitor = null;

	int l_win_size = m_ripper.lRippedWindow.size( );
	int l_gui_size = m_ripper.dGUIStructure.getGUI( ).size( );

	m_ripper.execute( );

	assertEquals( m_ripper.lRippedWindow.size( ), l_win_size );
	assertEquals( m_ripper.dGUIStructure.getGUI( ).size( ), l_gui_size );
    }

    /**
     * Test the Ripper::execute( ) function.
     *
     * This test assumes Ripper::monitor.getRootWindows( ) returns a null
     * value.  It is designed to test the true path of the second
     * conditional.
     */

    public void test23( ) {
	/* Set up the test */

	m_monitor1.initialize( );
	m_monitor1.m_getRootWindows = null;

	m_ripper.monitor = m_monitor1;

	/* Grab some data for verification */

	int l_win_size = m_ripper.lRippedWindow.size( );
	int l_gui_size = m_ripper.dGUIStructure.getGUI( ).size( );

	/* Execute the test */

	m_ripper.execute( );

	/* Check for correct behavior */

	assertEquals( m_ripper.lRippedWindow.size( ), l_win_size );
	assertEquals( m_ripper.dGUIStructure.getGUI( ).size( ), l_gui_size );
    }

    /**
     * Test the Ripper::execute( ) function.
     *
     * This test assumes Ripper::monitor.getRootWindows( ) returns a non-null
     * empty list.  It is designed to test the false path of the for loop
     * (i.e., the for loop should not execute).
     */
    public void test24( ) {
	/* Set up the test */

	m_monitor1.initialize( );
	m_monitor1.m_getRootWindows = new LinkedList<GWindow>( );

	m_ripper.monitor = m_monitor1;

	/* Grab some data for verification */

	int l_win_size = m_ripper.lRippedWindow.size( );
	int l_gui_size = m_ripper.dGUIStructure.getGUI( ).size( );

	/* Execute the test */

	m_ripper.execute( );

	/* Check for correct behavior */

	assertEquals( m_ripper.lRippedWindow.size( ), l_win_size );
	assertEquals( m_ripper.dGUIStructure.getGUI( ).size( ), l_gui_size );
    }

    /**
     * Test the Ripper::execute( ) function.
     *
     * This test assumes Ripper::monitor.getRootWindows( ) returns a non-null
     * non-empty list.  It is designed to test the true path of the for loop
     * (i.e., the for loop should execute once).
     */
    public void test25( ) {
	/* Set up the test */

	LinkedList<GWindow> l_list = new LinkedList<GWindow>( );
	l_list.addLast( m_window1 );

	GUIType l_guit = new GUIType( );

	m_window1.initialize( );

	m_monitor1.initialize( );
	m_monitor1.m_getRootWindows = l_list;

	m_wfilter1.initialize( );
	m_wfilter1.m_isProcess = true;
	m_wfilter1.m_ripWindow = l_guit;
	
	m_ripper.monitor = m_monitor1;
	m_ripper.lWindowFilter.addLast( m_wfilter1 );

	/* Grab some data for comparison */

	int l_win_size = m_ripper.lRippedWindow.size( );
	int l_gui_size = m_ripper.dGUIStructure.getGUI( ).size( );

	/* Execute the test */

	m_ripper.execute( );

	/* Check the results */

	assertEquals( m_ripper.lRippedWindow.size( ), l_win_size + 1 );
	assertEquals( m_ripper.lRippedWindow.contains( m_window1 ), true );

	assertEquals( m_ripper.dGUIStructure.getGUI( ).size( ), l_gui_size + 1 );
	assertEquals( m_ripper.dGUIStructure.getGUI( ).contains( l_guit ), true );

	/* Restore Ripper state */

	m_ripper.lWindowFilter.clear( );
	m_ripper.lRippedWindow.clear( );
	m_ripper.dGUIStructure.getGUI( ).clear( );
    }

    /**
     * Test the Ripper::ripComponent function.
     *
     * In this case, we test the component filtering.  We add two filters
     * to Ripper::lComponentFilters.  The first filter returns false from
     * the isProcess function; the second returns true.  This should execute
     * every branch in the for loop except the case where the for loop
     * conditional fails.  That case is addressed in subsequent tests.
     */
    public void test26( ) {
	/* Set up the test */

	ComponentType l_compt = new ComponentType ( );
	GComponent    l_gcomp = new GComponentStub( );

	m_window1 .initialize( );
	m_cfilter1.initialize( );
	m_cfilter2.initialize( );

	m_cfilter1.m_isProcess = false;
	m_cfilter2.m_isProcess = true;

	m_cfilter1.m_ripComponent = null;
	m_cfilter2.m_ripComponent = l_compt;

	m_ripper.lComponentFilter.addLast( m_cfilter1 );
	m_ripper.lComponentFilter.addLast( m_cfilter2 );

	/* Execute the test */

	ComponentType l_result = m_ripper.ripComponent( l_gcomp, m_window1 );

	/* Validate the result */

	assertSame( l_result, l_compt );

	/* Restore Ripper state */

	m_ripper.lComponentFilter.clear( );
    }

    /**
     * Test the Ripper::ripComponent function.
     *
     * This test is designed to test the false branch of the outermost
     * predicates in the try block.  Additionally, it tests the case
     * where the component has children.  As it happens, this test also
     * covers the functions exception handling.
     *
     * Note that the cast from a ComponentType to a ContainerType in the
     * child processing loop always throws an exception - the case is
     * not allowed.  Accordingly, there is no state change due to this
     * test; it always returns null from the error handling.  It is unlikely
     * this was the intended behavior, so this is considered an error.
     */
    public void test27( ) {
	/* Set up the test */

	ComponentType  l_compt  = new ComponentType ( );
	GComponentStub l_gcomp  = new GComponentStub( );
	GComponentStub l_child1 = new GComponentStub( );
	GComponentStub l_child2 = new GComponentStub( );

	LinkedList<GComponent> l_list1 = new LinkedList<GComponent>( );
	LinkedList<GComponent> l_list2 = new LinkedList<GComponent>( );

	l_list1.addLast( l_child1 );
	l_list1.addLast( l_child2 );

	l_child2.m_extractProperties = l_compt;
	l_gcomp .m_extractProperties = l_compt;
	l_child2.m_getChildren       = l_list2;
	l_gcomp .m_getChildren       = l_list1;

	m_window1 .initialize( );
	m_monitor1.initialize( );

	m_monitor1.m_isExpandable      = false;
	m_monitor1.m_isNewWindowOpened = false;

	m_ripper.monitor = m_monitor1;

	/* Execute the test */

	ComponentType l_result = m_ripper.ripComponent( l_gcomp, m_window1 );

	/* Validate the results */
	/* This always fails and needs to be modified */

	assertEquals( l_result != null, true );

	/* Restore Ripper state */

	m_ripper.monitor = null;
    }

    /**
     * This test is designed to test the true branches of the outermost
     * predicates in the try block.  Then it should exercise the for loop,
     * the while loop, and the true branch of each predicate in the while
     * loop.
     *
     * Note:  There is a likely bug in the code.  There is no check for the
     * case when the list of component children is null.  Thus, an exception
     * is almost always thrown.  This is considered an error.
     */
    public void test28( ) {
	/* Set up the test */

	GUIType        l_guit  = new GUIType       ( );
	ComponentType  l_compt = new ComponentType ( );
	GComponentStub l_gcomp = new GComponentStub( );

	// l_gcomp.m_getChildren = new LinkedList<GComponent>( );

	LinkedList<GWindow> l_list1 = new LinkedList<GWindow>( );

	l_list1.addLast( m_window1 );
	l_list1.addLast( m_window2 );

	l_gcomp.m_extractProperties = l_compt;

	m_window2 .initialize( );
	m_window1 .initialize( );
	m_monitor1.initialize( );
	m_wfilter1.initialize( );

	m_window1.m_extractWindow = l_guit;
	m_window1.m_getContainer  = l_gcomp;

	m_monitor1.m_isExpandable         = true;
	m_monitor1.m_isNewWindowOpened    = true;
	m_monitor1.m_isIgnoredWindow      = false;
	m_monitor1.m_getOpenedWindowCache = l_list1;

	m_wfilter1.m_isProcess = true;
	m_wfilter1.m_ripWindow = l_guit;

	m_ripper.monitor = m_monitor1;
	m_ripper.lWindowFilter.addLast( m_wfilter1 );

	/* Execute the test */

	ComponentType l_result = m_ripper.ripComponent( l_gcomp, m_window1 );

	/* Check Test Sanity */

	assertEquals( l_result != null, true );

	try {
	    FullComponentType l_fcomp =
		m_ripper.lOpenWindowComps.getFullComponent( ).get( 0 );

	    assertSame( l_fcomp.getWindow( ), m_window1.extractWindow( ).getWindow( ));
	    assertSame( l_fcomp.getComponent( ), l_compt );

	    PropertyType l_propt = l_result.getAttributes( ).getProperty( ).get( 0 );

	    assertSame( l_propt.getName( ), GUITARConstants.INVOKELIST_TAG_NAME );
	    assertEquals( l_propt.getValue( ).contains( m_window1.getFullID( )), true );

	    assertEquals( m_ripper.dGUIStructure.getGUI( ).contains( l_guit ), true );
	}
	catch( Exception e ) {
	    fail( "Expected non-empty lists." );
	}

	/* Restore Ripper state */

	m_ripper.monitor = null;
	m_ripper.lOpenWindowComps = m_ripper.factory.createComponentListType( );
	m_ripper.lWindowFilter.clear( );
	m_ripper.dGUIStructure.getGUI( ).clear( );
    }

    /**
     * This test is designed to test the true branches of the outermost
     * predicates in the try block.  Then it should exercise the for loop,
     * the while loop, and the true branch of each predicate in the while
     * loop except the innermost predicate; it should test the false branch
     * there.
     *
     * Note:  There is a likely bug in the code.  There is no check for the
     * case when the list of component children is null.  Thus, an exception
     * is almost always thrown.  This is considered an error.
     */
    public void test29( ) {
	/* Set up the test */

	GUIType        l_guit  = new GUIType       ( );
	ComponentType  l_compt = new ComponentType ( );
	GComponentStub l_gcomp = new GComponentStub( );

	// l_gcomp.m_getChildren = new LinkedList<GComponent>( );

	LinkedList<GWindow> l_list1 = new LinkedList<GWindow>( );

	l_list1.addLast( m_window1 );
	l_list1.addLast( m_window2 );

	l_gcomp.m_extractProperties = l_compt;

	m_window2 .initialize( );
	m_window1 .initialize( );
	m_monitor1.initialize( );
	m_wfilter1.initialize( );

	m_window1.m_extractWindow = l_guit;
	m_window1.m_getContainer  = l_gcomp;

	m_monitor1.m_isExpandable         = true;
	m_monitor1.m_isNewWindowOpened    = true;
	m_monitor1.m_isIgnoredWindow      = false;
	m_monitor1.m_getOpenedWindowCache = l_list1;

	m_wfilter1.m_isProcess = true;
	m_wfilter1.m_ripWindow = null;

	m_ripper.monitor = m_monitor1;
	m_ripper.lWindowFilter.addLast( m_wfilter1 );

	/* Execute the test */

	ComponentType l_result = m_ripper.ripComponent( l_gcomp, m_window1 );

	/* Check Test Sanity */

	assertEquals( l_result != null, true );

	try {
	    FullComponentType l_fcomp =
		m_ripper.lOpenWindowComps.getFullComponent( ).get( 0 );

	    assertSame( l_fcomp.getWindow( ), m_window1.extractWindow( ).getWindow( ));
	    assertSame( l_fcomp.getComponent( ), l_compt );

	    PropertyType l_propt =
		l_result.getAttributes( ).getProperty( ).get( 0 );

	    assertSame( l_propt.getName( ), GUITARConstants.INVOKELIST_TAG_NAME );
	    assertEquals( l_propt.getValue( ).contains( m_window1.getFullID( )), true );
	}
	catch( Exception e ) {
	    fail( "Expected non-empty lists." );
	}

	/* Restore Ripper state */

	m_ripper.monitor = null;
	m_ripper.lOpenWindowComps = m_ripper.factory.createComponentListType( );
	m_ripper.lWindowFilter.clear( );
	m_ripper.dGUIStructure.getGUI( ).clear( );
    }

    /**
     * This test is designed to test the true branches of the outermost
     * predicates in the try block.  Then it should exercise the for loop,
     * the while loop, and the true branch of the outer predicate, and the
     * false branch of the inner predicate.
     *
     * Note:  There is a likely bug in the code.  There is no check for the
     * case when the list of component children is null.  Thus, an exception
     * is almost always thrown.  This is considered an error.
     */
    public void test30( ) {
	/* Set up the test */

	GUIType        l_guit  = new GUIType       ( );
	ComponentType  l_compt = new ComponentType ( );
	GComponentStub l_gcomp = new GComponentStub( );

	// l_gcomp.m_getChildren = new LinkedList<GComponent>( );

	LinkedList<GWindow> l_list1 = new LinkedList<GWindow>( );

	l_list1.addLast( m_window1 );
	l_list1.addLast( m_window2 );

	l_gcomp.m_extractProperties = l_compt;

	m_window2 .initialize( );
	m_window1 .initialize( );
	m_monitor1.initialize( );
	m_wfilter1.initialize( );

	m_window1.m_extractWindow = l_guit;
	m_window1.m_getContainer  = l_gcomp;

	m_monitor1.m_isExpandable         = true;
	m_monitor1.m_isNewWindowOpened    = true;
	m_monitor1.m_isIgnoredWindow      = true;
	m_monitor1.m_getOpenedWindowCache = l_list1;

	m_wfilter1.m_isProcess = true;
	m_wfilter1.m_ripWindow = null;

	m_ripper.monitor = m_monitor1;
	m_ripper.lWindowFilter.addLast( m_wfilter1 );

	/* Execute the test */

	ComponentType l_result = m_ripper.ripComponent( l_gcomp, m_window1 );

	/* Check Test Sanity */

	assertEquals( l_result != null, true );

	try {
	    FullComponentType l_fcomp =
		m_ripper.lOpenWindowComps.getFullComponent( ).get( 0 );

	    assertSame( l_fcomp.getWindow( ), m_window1.extractWindow( ).getWindow( ));
	    assertSame( l_fcomp.getComponent( ), l_compt );

	    PropertyType l_propt =
		l_result.getAttributes( ).getProperty( ).get( 0 );

	    assertSame( l_propt.getName( ), GUITARConstants.INVOKELIST_TAG_NAME );
	    assertEquals( l_propt.getValue( ).contains( m_window1.getFullID( )), true );
	}
	catch( Exception e ) {
	    fail( "Expected non-empty lists." );
	}

	/* Restore Ripper state */

	m_ripper.monitor = null;
	m_ripper.lOpenWindowComps = m_ripper.factory.createComponentListType( );
	m_ripper.lWindowFilter.clear( );
	m_ripper.dGUIStructure.getGUI( ).clear( );
    }

    /**
     * This test is designed to test the true branches of the outermost
     * predicates in the try block.  Then it should exercise the for loop,
     * the while loop, and the true branch of the outer predicate, and the
     * false branch of the inner predicate.
     *
     * Note:  There is a likely bug in the code.  There is no check for the
     * case when the list of component children is null.  Thus, an exception
     * is almost always thrown.  This is considered an error.
     */
    public void test31( ) {
	/* Set up the test */

	GUIType        l_guit  = new GUIType       ( );
	ComponentType  l_compt = new ComponentType ( );
	GComponentStub l_gcomp = new GComponentStub( );

	// l_gcomp.m_getChildren = new LinkedList<GComponent>( );

	LinkedList<GWindow> l_list1 = new LinkedList<GWindow>( );

	l_list1.addLast( m_window1 );
	l_list1.addLast( m_window2 );

	l_gcomp.m_extractProperties = l_compt;

	m_window2 .initialize( );
	m_window1 .initialize( );
	m_monitor1.initialize( );
	m_wfilter1.initialize( );

	m_window1.m_extractWindow = l_guit;
	m_window1.m_getContainer  = l_gcomp;

	m_monitor1.m_isExpandable         = true;
	m_monitor1.m_isNewWindowOpened    = true;
	m_monitor1.m_isIgnoredWindow      = false;
	m_monitor1.m_getOpenedWindowCache = l_list1;
	m_monitor1.addRippedList( m_window1 );

	m_wfilter1.m_isProcess = true;
	m_wfilter1.m_ripWindow = null;

	m_ripper.monitor = m_monitor1;
	m_ripper.lWindowFilter.addLast( m_wfilter1 );

	/* Execute the test */

	ComponentType l_result = m_ripper.ripComponent( l_gcomp, m_window1 );

	/* Check Test Sanity */

	assertEquals( l_result != null, true );

	try {
	    FullComponentType l_fcomp =
		m_ripper.lOpenWindowComps.getFullComponent( ).get( 0 );

	    assertSame( l_fcomp.getWindow( ), m_window1.extractWindow( ).getWindow( ));
	    assertSame( l_fcomp.getComponent( ), l_compt );

	    PropertyType l_propt =
		l_result.getAttributes( ).getProperty( ).get( 0 );

	    assertSame( l_propt.getName( ), GUITARConstants.INVOKELIST_TAG_NAME );
	    assertEquals( l_propt.getValue( ).contains( m_window1.getFullID( )), true );
	}
	catch( Exception e ) {
	    fail( "Expected non-empty lists." );
	}

	/* Restore Ripper state */

	m_ripper.monitor = null;
	m_ripper.lOpenWindowComps = m_ripper.factory.createComponentListType( );
	m_ripper.lWindowFilter.clear( );
	m_ripper.dGUIStructure.getGUI( ).clear( );
    }
}
