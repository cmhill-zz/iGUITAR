package edu.umd.cs.guitar.replayer;



import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.umd.cs.guitar.model.GApplication;
import edu.umd.cs.guitar.replayer.Replayer;

import junit.framework.TestCase;



/**
 * This is the tests for GReplayerMonitor
 * using GReplayerMonitorMock2
 * @author Ran Liu
 *
 */
public class GReplayerMonitorTest extends TestCase {
	
    private GReplayerMonitor m_stub   ;
  
	
    /**
     * This function is part of the JUnit framework.  It performs any
     * initialization necessary to perform the tests.  In this case, it
     * initialized the member variables.
     */
    protected void setUp( ) {
    	
	
	m_stub    = new GReplayerMonitorMock2( );

	//m_window2 = new GWindowStub( "window2" );
    }
    public void test0( ) {
    	assertEquals( m_stub.application, null );
    	 
        }
    
    /**
     * This function tests the GReplayerMonitor::getReplayer function.
     * It addresses the following cases:
     *
     * 1 null
     * 
     */
    public void test1( ) {
	GApplication l_app;

	l_app = m_stub.getApplication();

	assertEquals( l_app== null,true );

	
    }
   

	
}
