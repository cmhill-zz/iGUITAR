package edu.umd.cs.guitar.ripper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GWindow;

import edu.umd.cs.guitar.ripper.GRipperMonitorStub;
import edu.umd.cs.guitar.ripper.GWindowStub;

import junit.framework.TestCase;

/**
 * The GRipperMonitorTest class implements unit tests for the member
 * functions defined in the abstract class GRipperMonitor.
 * The GRipperMonitorStub class is used in order to create GRipperMonitor
 * instances.
 */
public class GRipperMonitorTest extends TestCase {

    /** A Ripper instance for use in testing */
    private Ripper             m_ripper1;
 
    /** A Ripper instance for use in testing */
    private Ripper             m_ripper2;

    /** A GWindowStub instance for use in testing */
    private GWindowStub        m_window1;

    /** A GWindowStub instance for use in testing */
    private GWindowStub        m_window2;

    /** The GRipperMonitorStub instance on which tests are performed */
    private GRipperMonitorStub m_stub   ;

    /**
     * This function is part of the JUnit framework.  It performs any
     * initialization necessary to perform the tests.  In this case, it
     * initialized the member variables.
     */
    protected void setUp( ) {
	m_ripper1 = new Ripper            ( );
	m_ripper2 = new Ripper            ( );
	m_stub    = new GRipperMonitorStub( );

	m_window1 = new GWindowStub( "window1" );
	m_window2 = new GWindowStub( "window2" );
    }
    
    /**
     * This function checks that the GRipperMonitor instance has the
     * expected state immediately after construction.
     */
    public void test0( ) {
	assertEquals( m_stub.ripper, null );
	assertEquals( m_stub.lRippedWindow, new HashSet<String>( ));
    }

    /**
     * This function tests the GRipperMonitor::setRipper function.
     * It addresses the following cases:
     *
     * 1. The Ripper member is null and set to null.
     * 2. The Ripper member is null and set to non-null.
     * 3. The Ripper member is non-null and set to a different non-null.
     * 4. The Ripper member is non-null and set to null.
     */
    public void test1( ) {
	assertEquals( m_stub.ripper, null );

	m_stub.setRipper( null );
	
	assertEquals( m_stub.ripper, null );

	m_stub.setRipper( m_ripper1 );

	assertSame( m_stub.ripper, m_ripper1 );

	m_stub.setRipper( m_ripper2 );

	assertSame( m_stub.ripper, m_ripper2 );

	m_stub.setRipper( null );

	assertEquals( m_stub.ripper, null );
    }

    /**
     * This function tests the GRipperMonitor::getRipper function.
     * It addresses the following cases:
     *
     * 1. The Ripper member is null.
     * 2. The Ripper member is non-null.
     */
    public void test2( ) {
	Ripper l_ripper;

	l_ripper = m_stub.getRipper( );

	assertEquals( l_ripper, null );

	m_stub.ripper = m_ripper1;

	l_ripper = m_stub.getRipper( );

	assertSame( l_ripper, m_ripper1 );

	m_stub.ripper = null;

	l_ripper = m_stub.getRipper( );

	assertEquals( l_ripper, null );
    }

    /**
     * This function tests the GRipperMonitor::isRippedWindow function.
     * It addresses the following cases:
     *
     * 1. GRipperMonitor::lRippedWindow is empty.
     * 2. GRipperMonitor::lRippedWindow is non-empty and does not contain the
     *    GWindow instance in question.
     * 3. GRipperMonitor::lRippedWindow is non-empty and contains the
     *    GWindow instance in question.
     * 4. The GWindow instance in question is null.
     */
    public void test3( ) {
	boolean l_bool;

	l_bool = m_stub.isRippedWindow( m_window1 );

	assertEquals( l_bool, false );

	l_bool = m_stub.isRippedWindow( m_window2 );

	assertEquals( l_bool, false );

	m_stub.lRippedWindow.add( m_window1.getName( ));

	l_bool = m_stub.isRippedWindow( m_window1 );

	assertEquals( l_bool, true  );

	l_bool = m_stub.isRippedWindow( m_window2 );

	assertEquals( l_bool, false );

	m_stub.lRippedWindow.add( m_window2.getName( ));

	l_bool = m_stub.isRippedWindow( m_window1 );

	assertEquals( l_bool, true  );

	l_bool = m_stub.isRippedWindow( m_window2 );

	assertEquals( l_bool, true  );

	m_stub.lRippedWindow.clear( );

	try {
	    m_stub.isRippedWindow( null );

	    fail( "Should raise an exception." );
	}
	catch( Exception e ) {
	}
    }

    /**
     * This function tests the GRipperMonitor::addRippedList function.
     * It addresses the following cases:
     *
     * 1. GRipperMonitor::lRippedWindow is empty.
     * 2. GRipperMonitor::lRippedWindow is non-empty and does not contain
     *    the GWindow instance in question.
     * 3. GRipperMonitor::lRippedWindow is non-empty and contains the
     *    the GWindow instance in question.
     * 4. The GWindow instance in question is null.
     */
    public void test5( ) {
	boolean l_bool;

	m_stub.addRippedList( m_window1 );

	l_bool = m_stub.lRippedWindow.contains( m_window1.getFullID( ));

	assertEquals( l_bool, true  );

	l_bool = m_stub.lRippedWindow.contains( m_window2.getFullID( ));

	assertEquals( l_bool, false );

	m_stub.addRippedList( m_window1 );

	l_bool = m_stub.lRippedWindow.contains( m_window1.getFullID( ));

	assertEquals( l_bool, true  );

	l_bool = m_stub.lRippedWindow.contains( m_window2.getFullID( ));

	assertEquals( l_bool, false );

	m_stub.addRippedList( m_window2 );

	l_bool = m_stub.lRippedWindow.contains( m_window1.getFullID( ));

	assertEquals( l_bool, true  );

	l_bool = m_stub.lRippedWindow.contains( m_window2.getFullID( ));

	assertEquals( l_bool, true  );

	m_stub.lRippedWindow.clear( );

	try {
	    m_stub.addRippedList( null );

	    fail( "Should raise an exception." );
	}
	catch( Exception e ) {
	}
    }

    /**
     * This function tests the GRipperMonitor::isRippedWindow and
     * GRipperMonitor::addRippedList in tandem.
     *
     * At the time of writing this test, a GWindow instance is added to the
     * list based on its name and checked for list membership based on
     * its full ID.  This seems like a potential bug.  This test addresses the
     * case when the name and full ID of the GWindow instance are the same.
     */
    public void test7( ) {
	boolean l_bool;
	GWindowStub l_window = new GWindowStub( );

	l_window.m_getName   = "Window";
	l_window.m_getFullID = "Window";

	l_bool = m_stub.isRippedWindow( l_window );

	assertEquals( l_bool, false );

	m_stub.addRippedList( l_window );

	l_bool = m_stub.isRippedWindow( l_window );

	assertEquals( l_bool, true );

	m_stub.lRippedWindow.clear( );
    }

    /**
     * This function tests the GRipperMonitor::isRippedWindow and
     * GRipperMonitor::addRippedList in tandem.
     *
     * At the time of writing this test, a GWindow instance is added to the
     * list based on its name and checked for list membership based on
     * its full ID.  This seems like a potential bug.  This test addresses the
     * case when the name and full ID of the GWindow instance differ.
     */
    public void test8( ) {
	boolean l_bool;
	GWindowStub l_window = new GWindowStub( );

	l_window.m_getName   = "Window1";
	l_window.m_getFullID = "Window2";

	l_bool = m_stub.isRippedWindow( l_window );

	assertEquals( l_bool, false );

	m_stub.addRippedList( l_window );

	l_bool = m_stub.isRippedWindow( l_window );

	assertEquals( l_bool, true );

	m_stub.lRippedWindow.clear( );
    }
}