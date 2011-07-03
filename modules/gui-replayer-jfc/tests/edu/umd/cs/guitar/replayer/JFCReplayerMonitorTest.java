package edu.umd.cs.guitar.replayer;
import edu.umd.cs.guitar.model.GWindow;
import java.awt.Frame;
import junit.framework.*;
import edu.umd.cs.guitar.model.JFCXWindow;


public class JFCReplayerMonitorTest extends TestCase{
	//Test#1
	public void test_getAction(){
		JFCReplayerMonitor testMonitor = new JFCReplayerMonitor("nothing");
		assertNull(testMonitor.getAction(""));
	}
	
	//Test#2
	public void test_getArguments(){
		JFCReplayerMonitor testMonitor = new JFCReplayerMonitor("nothing");
		assertNull(testMonitor.getArguments("nothing"));
	}
	
/*	//Test#3
	public void test_getComponent(){
		String text = "text";
		Frame aframe = new Frame();
		GWindow thewindow = new JFCXWindow(aframe);
		JFCReplayerMonitor testMonitor = new JFCReplayerMonitor("nothing");
		assertNull(testMonitor.getComponent(text,thewindow));
	}
	
//Test#4
	public void test_getWindow(){
		JFCReplayerMonitor testMonitor = new JFCReplayerMonitor("nothing");
		assertNull(testMonitor.getWindow("nothing"));
	}
	
	//Test#5
	public void test_selectIDProperties(){
		JFCReplayerMonitor testMonitor = new JFCReplayerMonitor("nothing");
		assertNull(testMonitor.selectIDProperties(null));
	}

	//Test
	public void test_connectToApplication(){
		JFCReplayerMonitor testMonitor = new JFCReplayerMonitor("nothing");
		testMonitor.connectToApplication();
		assertTrue(true);
	}*/
}
