package edu.umd.cs.guitar.replayer;

import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.model.GApplication;
import edu.umd.cs.guitar.model.GComponent;
/**
 * This is a mock object for GEvent
 */
public class GEventMock implements GEvent {
	public GApplication app;
	
	/**
	 * Default constructor
	 */
	public GEventMock(){
		app = new GAppMock();
	}
	@Override
	public void perform(GComponent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void perform(GComponent arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
