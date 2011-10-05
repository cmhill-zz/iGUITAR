package edu.umd.cs.guitar.replayer.monitor;

import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.replayer.StepTypeMock;

/**
 * This is a mock object for TestStepStartEventArgs
 * 
 */
public class TSStartEvtArgsMock extends TestStepStartEventArgs {
	/**
	 * Constructor
	 */
	public TSStartEvtArgsMock(StepType step) {
		super(step);
		// TODO Auto-generated constructor stub
		step = new StepTypeMock();
	}

}
