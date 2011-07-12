package edu.umd.cs.guitar.replayer.monitor;

import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.model.GApplication;
import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.replayer.Replayer;

/**
 * A wrapper of test step data Info about a testcase step event.
 * 
 * TODO: Should add more application data to collect more results (Bao)
 * 
 * @author Scott McMaster (modified by Bao Nguyen)
 * 
 */

public class TestStepEndEventArgs extends TestStepEventArgs  {

    ComponentType component;
    GUIType window;
    
    /**
     * @param step
     * @param component
     * @param window
     */
    public TestStepEndEventArgs(StepType step, ComponentType component,
            GUIType window) {
        super(step);
        this.component = component;
        this.window = window;
    }
    

    

}
