package edu.umd.cs.guitar.ripper;

import edu.umd.cs.guitar.ripper.RipperTestSuite;

/**
 * The RipperTestRunner class is an executable class that runs the
 * entire suite of GUIRipper unit tests.
 */
public class RipperTestRunner {

    /**
     * Executes the test suite.
     *
     * @param args The command line arguments.
     */
    public static void main( String[] args )
    {
	junit.textui.TestRunner.run( RipperTestSuite.make( ));
    }
}