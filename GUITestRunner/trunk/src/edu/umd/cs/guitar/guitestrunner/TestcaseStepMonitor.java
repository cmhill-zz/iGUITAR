package edu.umd.cs.guitar.guitestrunner;

/**
 * Interface to be implemented by classes that wish to be notified
 * when test case steps run.
 */
public interface TestcaseStepMonitor
{
	/**
	 * Called on application startup, before the first step is run.
	 */
	void init();
	
	/**
	 * Called after the last step is through executing.
	 */
	void term();
	
	/**
	 * Notification right before a step runs.
	 * @param args
	 */
	void beforeStep(TestcaseStepEventArgs args);
}
