package edu.umd.cs.guitar.guitestrunner;

import java.awt.Component;

/**
 * Info about a testcase step event.
 * @author Scott McMaster
 *
 */
public class TestcaseStepEventArgs
{
	private TestcaseStep step;
	
	private Component component;
	
	public TestcaseStepEventArgs(TestcaseStep step, Component component)
	{
		this.step = step;
		this.component = component;
	}

	public void setStep(TestcaseStep step) {
		this.step = step;
	}

	public TestcaseStep getStep() {
		return step;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public Component getComponent() {
		return component;
	}
}
