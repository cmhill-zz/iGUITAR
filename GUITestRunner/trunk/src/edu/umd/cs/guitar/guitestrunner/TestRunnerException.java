package edu.umd.cs.guitar.guitestrunner;

public class TestRunnerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5505222631454584215L;
	
	public TestRunnerException()
	{
		super();
	}

	public TestRunnerException(String message)
	{
		super(message);
	}

	public TestRunnerException(Throwable cause)
	{
		super(cause);
	}

	public TestRunnerException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
