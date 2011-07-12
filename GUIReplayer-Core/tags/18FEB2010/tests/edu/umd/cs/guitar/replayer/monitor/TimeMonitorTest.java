package edu.umd.cs.guitar.replayer.monitor;

import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.replayer.StepTypeMock;
import edu.umd.cs.guitar.replayer.monitor.TestStepEndEventArgs;
import junit.framework.TestCase;
/**
 * The TimeMonitorTest class implements unit tests for the member
 * functions defined in the class TimeMonitor.
 */
public class TimeMonitorTest extends TestCase {
	/** an instance of TimeMonitor*/
	TimeMonitor tm;
	/** an instance of TestStepStartEventArgs*/
	TestStepStartEventArgs arg ;
	/** an instance of StepType*/
	StepType step;
	
	/**
     * Set up tests
     */
	protected void setUp(){
		tm=new TimeMonitor(20, 20);
	//	step = new StepTypeMock();
	//	arg = new TSStartEvtArgsMock(step);
	}
	/**
     * This checks constructor.
     */
	public void testConstructor(){
		assertEquals(tm.nStepTimeout,20);
		assertEquals(tm.nTestcaseTimeout,20);
	//	TimeMonitor$Time t;
	}
	
	/**
     * This checks init.
     */
	public void testInit(){
		
		long a =tm.nStartTime ;
		tm.init();
		
		assertFalse(a==tm.nStartTime);
	}
	/**
     * This checks beforeStep
     * includes the following test cases
     * 1.null
     * 
     */
	public void testBeforeStep(){
		long a = tm.nStartTime ;
		tm.init();
		tm.beforeStep(null);
		assertFalse(a==tm.nStartTime);
		
		
	}
	/**
     * This checks afterStep
     * includes the following test cases
     * 1.null
     * 
     */
	public void testAfterStep(){
		long a = tm.nEndTime ;
		tm.init();
		tm.beforeStep(null);
		tm.afterStep(null);
		assertTrue(a==tm.nEndTime);
		
		
	}

}
