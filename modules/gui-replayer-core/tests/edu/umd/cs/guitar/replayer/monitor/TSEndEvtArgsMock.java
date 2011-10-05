
package edu.umd.cs.guitar.replayer.monitor;

import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.replayer.ComponentTypeMock;
import edu.umd.cs.guitar.replayer.GUITypeMock;
import edu.umd.cs.guitar.replayer.StepTypeMock;

/**
 * @author Ran
 * This is the mock object of TestStepEndEventArgs
 *
 */
public class TSEndEvtArgsMock extends TestStepEndEventArgs {
	/**
	 * Default constructor
	 * @param step
	 * @param component
	 * @param window
	 */
	public TSEndEvtArgsMock(StepType step, ComponentType component,
			GUIType window) {

		super(step, component, window);
		// TODO Auto-generated constructor stub
		component = new ComponentTypeMock();
    	window = new GUITypeMock();
		step = new StepTypeMock();
	}
	
}
