package edu.umd.cs.guitar.replayer.monitor;

import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.replayer.StepTypeMock;
import edu.umd.cs.guitar.replayer.monitor.TestStepEventArgs;
import junit.framework.TestCase;
/**
 * This checks TestStepEventArgs
 */
public class TestStepEventArgsTest extends TestCase {
	TestStepEventArgs m_testStepEventArgs1;
	TestStepEventArgs m_testStepEventArgs2;
	StepTypeMock m_st;
	
	/**
     * Set up tests
     */
	protected void setUp() throws Exception {
		m_st = new StepTypeMock();
		m_testStepEventArgs1 = new TestStepEventArgs(null);
		m_testStepEventArgs2 = new TestStepEventArgs(m_st);
		
	}
	
	/**
     * This checks the method getStep
     */
	public void testGet(){
		assertEquals( m_testStepEventArgs1.getStep(), null );
		assertEquals( m_testStepEventArgs2.getStep(), m_st );
	}


}
