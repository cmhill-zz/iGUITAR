package edu.umd.cs.guitar.ripper;

import edu.umd.cs.guitar.ripper.GComponentFilterTest;
import edu.umd.cs.guitar.ripper.GWindowFilterTest;
import edu.umd.cs.guitar.ripper.GRipperMonitorTest;
import edu.umd.cs.guitar.ripper.RipperTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * The RipperTestSuite class gathers together all of the GUIRipper unit tests
 * into a single test suite.  All of the unit tests can be executed by
 * making one execution call on the suite.
 */
public class RipperTestSuite {
    
    /**
     * This function aggregates the unit test classes into a single
     * TestSuite instance.
     *
     * @return A TestSuite instance containing all of the GUIRipper
     *   unit tests.
     */
    public static Test make( ) {
	TestSuite l_suite = new TestSuite( );

	l_suite.addTestSuite( GComponentFilterTest.class );
	l_suite.addTestSuite(    GWindowFilterTest.class );
	l_suite.addTestSuite(   GRipperMonitorTest.class );
	l_suite.addTestSuite(           RipperTest.class );

	return l_suite;
    }
}