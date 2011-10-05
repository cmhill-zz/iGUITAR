/**
 * 
 */
package edu.umd.cs.guitar.replayer.monitor;

import edu.umd.cs.guitar.exception.GException;
import edu.umd.cs.guitar.replayer.monitor.GTestMonitor;
import edu.umd.cs.guitar.replayer.monitor.TestStepEndEventArgs;
import edu.umd.cs.guitar.replayer.monitor.TestStepStartEventArgs;

/**
 * @author Ran Liu
 * This is the Mock object for GTestMonitor
 */
public class GTestMonitorMock extends GTestMonitor {
	
	public boolean init;
	public boolean term;
	public GTestMonitorMock(){
		init=false;
		term=false;
	}
	/**
	 * @see edu.umd.cs.guitar.replayer.monitor.GTestMonitor#afterStep(edu.umd.cs.guitar.replayer.monitor.TestStepEndEventArgs)
	 */
	@Override
	public void afterStep(TestStepEndEventArgs arg0) {

	}

	/**
	 * @see edu.umd.cs.guitar.replayer.monitor.GTestMonitor#beforeStep(edu.umd.cs.guitar.replayer.monitor.TestStepStartEventArgs)
	 */
	@Override
	public void beforeStep(TestStepStartEventArgs arg0) {		

	}

	/**
	 * @see edu.umd.cs.guitar.replayer.monitor.GTestMonitor#init()
	 */
	@Override
	public void init() {
		init=true;
		
	}

	/**
	 * @see edu.umd.cs.guitar.replayer.monitor.GTestMonitor#term()
	 */
	@Override
	public void term() {
		init=false;
	}
	@Override
	public void exceptionHandler(GException e) {
		// TODO Auto-generated method stub
		
	}

}
