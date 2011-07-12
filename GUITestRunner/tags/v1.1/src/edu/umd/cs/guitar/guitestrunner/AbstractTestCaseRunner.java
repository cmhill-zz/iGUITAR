package edu.umd.cs.guitar.guitestrunner;


/**
 * Base class for various types of test case runners.
 * 
 * @author Scott McMaster
 * 
 */
public abstract class AbstractTestCaseRunner {
    // Why not run with steps? And ask other class to perform I/O.
    //
    // /**
    // * Name of the test file.
    // */
    // protected String testFilename;
    //
    // public AbstractTestCaseRunner(String testFilename) {
    // this.testFilename = testFilename;
    // }

    /**
     * Template method.
     */
    public abstract void runAll(TestCaseStep[] steps) throws TestRunnerException;
    public abstract void run(TestCaseStep step) throws TestRunnerException;
}
