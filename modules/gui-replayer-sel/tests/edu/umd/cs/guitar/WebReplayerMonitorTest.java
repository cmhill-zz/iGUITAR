package edu.umd.cs.guitar;

import edu.umd.cs.guitar.replayer.WebReplayerMonitor;
import junit.framework.TestCase;
import edu.umd.cs.guitar.replayer.GReplayerConfiguration;

/**
 * test WebReplayerMonitor
 * @author brian
 *
 */
public class WebReplayerMonitorTest extends TestCase {
	
	public void test_getAction(){
		GReplayerConfiguration config = new GReplayerConfiguration();
		WebReplayerMonitor testMonitor = new WebReplayerMonitor(config);
		assertNull(testMonitor.getAction(""));
	}
	
	public void test_getArguments(){
		GReplayerConfiguration config = new GReplayerConfiguration();
		WebReplayerMonitor testMonitor = new WebReplayerMonitor(config);
		assertNull(testMonitor.getArguments("nothing"));
	}
	
	
	public void test_connectToApplication(){
		GReplayerConfiguration config = new GReplayerConfiguration();
		WebReplayerMonitor testMonitor = new WebReplayerMonitor(config);
		testMonitor.connectToApplication();
		assertTrue(true);
	}
}
