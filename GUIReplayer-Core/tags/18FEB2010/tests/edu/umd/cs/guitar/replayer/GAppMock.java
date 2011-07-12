package edu.umd.cs.guitar.replayer;

import java.util.HashSet;
import java.util.Set;

import edu.umd.cs.guitar.exception.ApplicationConnectException;
import edu.umd.cs.guitar.model.GApplication;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.data.GUIStructure;

/**
 * This is the mock object for GApplication
 * 
 * @see edu.umd.cs.guitar.model.GApplication
 */
public class GAppMock extends GApplication {
	/** a new HashSet<String> that stores window IDs*/
	Set<String> allWinIDs = new HashSet<String>();
	
	/**
	 * Default constructor
	 * constructs a GApplication with one window
	 */
	public GAppMock(){
		allWinIDs.add("window1");
	}
	/**
	 * constructor
	 * constructs a GApplication with three window
	 */
	public GAppMock(String str){
		allWinIDs.add("window1");
		allWinIDs.add("window2");
		allWinIDs.add("window3");
	}
	
	/**
	 * created for getCurrentState
	 * @return new GUIStructure 
	 */
	@Override
	public GUIStructure getCurrentState(){
		return new GUIStructure();
		
	}
	@Override
	public void connect() throws ApplicationConnectException {
		// TODO Auto-generated method stub

	}

	@Override
	public void connect(String[] arg0) throws ApplicationConnectException {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<GWindow> getAllWindow() {
		// TODO Auto-generated method stub
		return new HashSet<GWindow>();
	}
	/**
	 * created for getCurrentWinID
	 * 
	 * @return allWinIDs
	 */
	@Override
	public Set<String> getCurrentWinID(){
		
		
	//	allWinIDs.add("window1");
		return allWinIDs;
	
		
	}
	

}
