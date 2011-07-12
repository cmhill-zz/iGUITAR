package edu.umd.cs.guitar.guitestrunner;

/**
 * 
 * @author Scott McMaster, Si Huang
 * 
 */
public class TestRunnerException extends Exception {
    private static final long serialVersionUID = 1L;

    public TestRunnerException() {
        super();
    }

    public TestRunnerException(String message) {
        super(message);
    }

    public TestRunnerException(Throwable cause) {
        super(cause);
    }

    public TestRunnerException(String message, Throwable cause) {
        super(message, cause);
    }
}
