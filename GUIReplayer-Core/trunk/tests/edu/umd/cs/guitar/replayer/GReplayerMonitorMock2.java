package edu.umd.cs.guitar.replayer;


/**
 * @author Ran Liu
 * This is another mock object for GReplayerMonitor
 * used to test GReplayerMonitor::getApplication()
 * 
 */
import java.util.List;

import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.PropertyType;

public class GReplayerMonitorMock2 extends GReplayerMonitor {
	
	public GReplayerMonitorMock2() {
		initialize( );
	}
	public void initialize(){
		
	}
	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectToApplication() {
		// TODO Auto-generated method stub

	}

	@Override
	public GEvent getAction(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getArguments(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GComponent getComponent(String arg0, GWindow arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GWindow getWindow(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PropertyType> selectIDProperties(ComponentType arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUp() {
		// TODO Auto-generated method stub

	}

}
