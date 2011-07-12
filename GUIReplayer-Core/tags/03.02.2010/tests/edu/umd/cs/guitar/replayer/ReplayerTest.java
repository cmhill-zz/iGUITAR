/**
 * 
 */
package edu.umd.cs.guitar.replayer;




import edu.umd.cs.guitar.model.data.StepType;


import java.io.IOException;
import java.lang.reflect.Field; 
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import edu.umd.cs.guitar.exception.ComponentNotFound;
import edu.umd.cs.guitar.replayer.Replayer;
import edu.umd.cs.guitar.replayer.monitor.GTestMonitor;
import edu.umd.cs.guitar.replayer.monitor.GTestMonitorMock;

import edu.umd.cs.guitar.util.GUITARLog;

import junit.framework.TestCase;

/**
 *this function is part of the JUnit framework.  It performs any 
 * initialization necessary to perform the tests.  In this case, it 
 *  initialized the member variables.
 *  Tests class Replayer
 *  @see edu.umd.cs.guitar.replayer.Replayer;
 *  @author Ran Liu  
 */
public class ReplayerTest extends TestCase {

	 private Replayer replayer;
//	 ReplayerMock replayer2;
	 private TestCaseMock m_tsm;
	 private String m_EFG;
	 private String m_GUI;

	 Logger log = GUITARLog.log;
	 Field fields[];

	// GReplayerMonitor monitor;
	 /**
	  * set up the tests
	  */
	protected void setUp(){
		m_EFG = ".//tests//inputs//ButtonDemo.efg.xml";
		m_GUI = ".//tests//inputs//ButtonDemo.gui.xml";
		m_tsm = new TestCaseMock();
		try {
			replayer = new Replayer(m_tsm,m_GUI,m_EFG);
		//	replayer2 = new ReplayerMock(m_tsm,m_GUI,m_EFG);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	/**
     * This function checks that a Replayer instance has the expected state
     * immediately after construction using the default constructor.
     */
	 public void test00( ) {
		 assertEquals((replayer != null), true );
		 
		 Logger l_logger = replayer .log;
		 assertEquals((l_logger  != null), true );
		 
		 
		 
	 }
	 
	 /**
	     * This function checks that a Replayer using the default constructor.
	     */
	 public void testConstructor( ) {
	
		
		
			
				
				assertEquals((replayer != null), true );
				//TestCaseMock l_tsm = new TestCaseMock("e02");
				//assertEquals((l_tsm  != null), true );
			
		
	 
	 }
	 
/*	 public void TestSetTimeOut() {
	        timeout= (int)Math.random();
	        m_replayer.setTimeOut(timeout);
	       
	    }
	 
	*/
	 /**
	     * This function checks execute when it works fine.
	     */
	 public void testExecuteOK() {
		
		try {
			GReplayerMonitorMock l_monitor = new GReplayerMonitorMock();
			replayer.setMonitor(l_monitor);
			GTestMonitorMock t_monitor = new GTestMonitorMock();
			replayer.addTestMonitor(t_monitor);
			replayer.execute();
			List<StepType> lSteps = m_tsm.getStep();
			int nStep = lSteps.size();
			
			//see if the setup() been called
			assertEquals(l_monitor.setupcall,true);
			//connection
			assertEquals(l_monitor.conncall,true);
		//	System.out.println(nStep+"");
			//
			//assertEquals(t_monitor.init,true);
			//assertEquals(t_monitor.term,true);
			assertEquals(nStep,2);
			assertEquals(l_monitor.clean,true);
		} catch (ComponentNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("test failed");
		}
			
			
	
		
	 }
	
	 /*	 public void testForLoop(){
		 try {
			 	
				GReplayerMonitorMock l_monitor = new GReplayerMonitorMock();
				replayer2.setMonitor(l_monitor);
			
				
				assertEquals( replayer2.count,0);
				replayer2.execute();
				
			} catch (ComponentNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("test failed");
			}
		 
	 }*/
	 /**
	     * This function checks execute when it throws exception.
	     * If the exception isn't throw exception, the test fails.
	     */
	 public void testExecuteFail1(){
		
		 try {
			 GReplayerMonitorMock l_monitor = new GReplayerMonitorMock("fail");
				replayer.setMonitor(l_monitor);
				replayer.execute();
			
				fail("exception failed");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			assertTrue(true);
		}
	 }
	 /**
	     * This function checks execute when it throws exception.
	     * If the exception isn't throw exception, the test fails.
	     * 
	     * Tests ComponentNotFound
	     */
	 public void testExecuteFail2(){
		
		try {
			 try {
				Replayer fail_rep= new ReplayerErrorMock(m_tsm,m_GUI,m_EFG);
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				fail("exception failed");
		} catch (ComponentNotFound e) {
			// TODO Auto-generated catch block
			
			assertTrue(true);
		}
		 
	 }
	
	 /**
	     * This function checks setMonitor function
	     */
	 public void testSetMonitor() {
		 GReplayerMonitorMock l_monitor = new GReplayerMonitorMock();
		 
		
		 replayer.setMonitor(l_monitor);
		 assertSame(replayer.getMonitor(),l_monitor);
		
	   }
	/* public void testAddTestMonitor() {
		 GTestMonitorMock aTestMonitor = new GTestMonitorMock();
		 replayer.addTestMonitor(aTestMonitor);
		 
		 assertSame(replayer.getMonitor(),aTestMonitor);
	    }
	    */
}
