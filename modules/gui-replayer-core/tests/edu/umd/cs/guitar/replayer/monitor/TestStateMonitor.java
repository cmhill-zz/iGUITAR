package edu.umd.cs.guitar.replayer.monitor;

import edu.umd.cs.guitar.replayer.monitor.StateMonitor;
import junit.framework.TestCase;

public class TestStateMonitor extends TestCase {
	StateMonitor sMonitor;
	protected void setUp() throws Exception {
		super.setUp();
		sMonitor= new StateMonitor("inputs//");
	}
	

}
