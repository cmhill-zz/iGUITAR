package edu.umd.cs.guitar.ripper;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.GUIType;

import edu.umd.cs.guitar.ripper.GComponentFilterStub;

import junit.framework.TestCase;

/**
 * The GComponentFilterTest class implements unit tests for the member
 * functions defined in the abstract class GComponentFilter.
 * The GComponentFilterStub class is used in order to create GComponentFilter
 * instances.
 */
public class GComponentFilterTest extends TestCase {

    /** A Ripper instance for use in testing */
    private Ripper               m_ripper1;

    /** A Ripper instance for use in testing */
    private Ripper               m_ripper2;

    /** The GComponentFilterStub instance on which tests are performed */
    private GComponentFilterStub m_stub   ;

    /**
     * This function is part of the JUnit framework.  It performs any
     * initialization necessary to perform the tests.  In this case, it
     * initializes the member variables.
     */
    @Override
    protected void setUp( ) {
	m_ripper1 = new Ripper              ( );
	m_ripper2 = new Ripper              ( );
	m_stub    = new GComponentFilterStub( );
    }

    /**
     * This function checks that the GComponentFilter instance has the
     * expected state immediately after construction.
     */
    public void test0( ) {
	assertEquals( m_stub.ripper, null );
    }

    /**
     * This function tests the GComponentFilter::setRipper function.
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