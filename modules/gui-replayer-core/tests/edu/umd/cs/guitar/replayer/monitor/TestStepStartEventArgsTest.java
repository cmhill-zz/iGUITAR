package edu.umd.cs.guitar.replayer.monitor;


import edu.umd.cs.guitar.replayer.StepTypeMock;
import edu.umd.cs.guitar.replayer.monitor.TestStepStartEventArgs;
import junit.framework.TestCase;
/**
 * This is the test of TestStepStartEventArgs
 */
public class TestStepStartEventArgsTest extends TestCase {
	StepTypeMock step;
	
	/**
     *Set up the tests
     */
	protected void setUp() throws Exception {
		step = new StepTypeMock();
	}
	
        
	/**
     * This checks constructor.
     */
	public void testConstructor(){
		TestStepStartEventArgs l_testStepStartEventArgs;
		
		l_testStepStartEventArgs = new TestStepStartEventArgs(step);
		
		assertEquals((l_testStepStartEventArgs != null), true );
	}
}
