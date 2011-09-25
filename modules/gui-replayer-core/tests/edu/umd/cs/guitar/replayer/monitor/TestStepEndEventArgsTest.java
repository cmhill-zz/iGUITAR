package edu.umd.cs.guitar.replayer.monitor;


import junit.framework.TestCase;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.replayer.StepTypeMock;
import edu.umd.cs.guitar.replayer.monitor.TestStepEndEventArgs;
/**
 * This checks TestStepEndEventArgs.
 */
public class TestStepEndEventArgsTest extends TestCase {
	ComponentType component;
    GUIType window;
    StepTypeMock step;
    /**
     * set up tests
     */
    protected void setUp() throws Exception {
    	component = new ComponentType();
    	window = new GUIType();
		step = new StepTypeMock();
		
	}
    /**
     * This checks constructor.
     */
    public void testConstructor( ) {
    	
    	TestStepEndEventArgs l_endEventArgs;
    	l_endEventArgs = new TestStepEndEventArgs(step, component, window);
		assertEquals((l_endEventArgs != null), true );
	//	System.out.println(l_endEventArgs.getStep());
		assertEquals(l_endEventArgs.getStep(),step);
    }
}
