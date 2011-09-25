package edu.umd.cs.guitar.ripper;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.GUIType;

import edu.umd.cs.guitar.ripper.GWindowFilterStub;

import junit.framework.TestCase;

/**
 * The GWindowFilterTest class implements unit tests for the member
 * functions defined in the abstract class GWindowFilter.
 * The GWindowFilterStub class is used in order to create GWindowFilter
 * instances.
 */
public class GWindowFilterTest extends TestCase {

    /** A Ripper instance for use in testing */
    private Ripper            m_ripper1;

    /** A Ripper instance for use in testing */
    private Ripper            m_ripper2;

    /** The GWindowFilterStub instance on which tests are performed */
    private GWindowFilterStub m_stub   ;

    /**
     * This function is part of the JUnit framework.  It performs any
     * initialization necessary to perform the tests.  In this case, it
     * initialized the member variables.
     */
    @Override
    protected void setUp( ) {
	m_ripper1 = new Ripper           ( );
	m_ripper2 = new Ripper           ( );
	m_stub    = new GWindowFilterStub( );
    }

    /**
     * This function checks that the GWindowFilter instance has the
     * expected state immediately after construction.
     */
    public void test0( ) {
	assertEquals( m_stub.ripper, null );
    }

    /**
     * This function tests the GWindowFilter::setRipper function.
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
}