package edu.umd.cs.guitar.replayer.monitor;
import edu.umd.cs.guitar.util.Debugger;
import junit.framework.*;


public class JFCDebugMonitorTest extends TestCase{
    /*
     *
     * 
     * 
     */
	public void test_afterStep(){
		JFCDebugMonitor monitor = new JFCDebugMonitor();
		monitor.afterStep(null);
		assertTrue(true);
	}
    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.replayer.AbsReplayerMonitor#connectToApplication()
     */
	public void test_beforeStep(){
		JFCDebugMonitor monitor = new JFCDebugMonitor();
		monitor.beforeStep(null);
		assertTrue(true);
	}
	
    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.replayer.AbsReplayerMonitor#connectToApplication()
     */
	public void test_init(){
		JFCDebugMonitor monitor = new JFCDebugMonitor();
		monitor.init();
		assertTrue(true);
	}
	
    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.replayer.AbsReplayerMonitor#connectToApplication()
     */
	public void test_term(){
		JFCDebugMonitor monitor = new JFCDebugMonitor();
		monitor.term();
		assertTrue(true);
	}

}


