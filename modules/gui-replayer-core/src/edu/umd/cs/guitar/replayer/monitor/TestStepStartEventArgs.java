package edu.umd.cs.guitar.replayer.monitor;

import edu.umd.cs.guitar.model.data.StepType;

/**
 * A wrapper of test step data Info about a testcase step event.
 * 
 * TODO: Should add more application data to collect more results (Bao)
 * 
 * @author Scott McMaster (modified by Bao Nguyen)
 * 
 */

public class TestStepStartEventArgs extends TestStepEventArgs  {

    /**
     * @param step
     */
    public TestStepStartEventArgs(StepType step) {
        super(step);
    }


}
