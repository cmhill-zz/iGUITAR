package edu.umd.cs.guitar.guitestrunner;

import java.awt.Component;


/**
 * Info about a testcase step event.
 * 
 * @author Scott McMaster
 * 
 */
public class TestCaseStepEventArgs {
    private TestCaseStep step;

    private Component component;

    public TestCaseStepEventArgs(TestCaseStep step, Component component) {
        this.step = step;
        this.component = component;
    }

    public void setStep(TestCaseStep step) {
        this.step = step;
    }

    public TestCaseStep getStep() {
        return step;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public Component getComponent() {
        return component;
    }
}
