package edu.umd.cs.guitar.replayer;
import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.model.GApplication;
import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.PropertyType;
import  edu.umd.cs.guitar.replayer.GReplayerMonitor;
/**
 * @author Ran Liu
 * This is the mock object for GReplayerMonitor
 * 
 */
public class GReplayerMonitorMock extends GReplayerMonitor{
	GEvent m_gEvent;
	Object m_object;
	List<PropertyType> m_list;
	GWindow m_gWindow;
	GComponent m_gComp;
	public boolean setupcall=false;
	public boolean conncall=false;
	public boolean clean=false;
	public boolean init=false;
	public boolean term=false;
	/** Creates a GReplayerMonitorMock instance and initializes it */
	public GReplayerMonitorMock( ) {
		initialize( );
	    }
	/**
     * create a instance with a null gWindow
     */
	public GReplayerMonitorMock(String s) {
		initialize();
		m_gWindow = new GWindowStub();

	    }
	/**
     * initialize instance
     */
    public void initialize( ) {
    	m_gEvent = new GEventMock();
    	m_object = new Object();
    	m_list = new ArrayList<PropertyType>();
    
    	m_gWindow = new GWindowStub("window1");
    	m_gComp = new GComponentMock();
    	init=true;

    }
    
    /**
     * Set connection = true;
     */
	 public void connectToApplication(){
		 conncall=true;
	 }
	 
	 /**
	     * If this function is called, set setupcall=true
	     */
	 public void setUp(){
		 setupcall=true;
	 }
	 

	 /**
	     * If this function is called, set setupcall=true
	     */
	    public void cleanUp(){
	    	clean=true;
	    }


		 /**
		     * @return m_gWindow
		     */
	    public GWindow getWindow(String sWindowID){
	    	return m_gWindow;
	    }

	  
	    /**
	     * @return m_gEvent
	     */
	    public GEvent getAction(String sActionName){
	    	return m_gEvent;
	    }

	    /**
	     * @return m_gobject
	     */
	    public Object getArguments(String action){
	    	return m_object;
	    }

	    /**
	     * @return m_gCompt
	     */
		@Override
		public GComponent getComponent(String arg0, GWindow arg1) {
			// TODO Auto-generated method stub
			return m_gComp;
		}
		/**
	     * @return m_list
	     */
		@Override
		public List<PropertyType> selectIDProperties(ComponentType arg0) {
			// TODO Auto-generated method stub
			return m_list;
		}
		
		/**
	     * @return new GAppMock instance
	     */
		@Override
		public GApplication getApplication(){
			return new GAppMock();
			
		}

	  
 
}
