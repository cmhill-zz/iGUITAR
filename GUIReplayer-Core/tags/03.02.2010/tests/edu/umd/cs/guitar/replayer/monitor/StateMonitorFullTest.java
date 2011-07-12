package edu.umd.cs.guitar.replayer.monitor;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.umd.cs.guitar.model.GApplication;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.replayer.GAppMock;
import edu.umd.cs.guitar.replayer.GReplayerMonitor;
import edu.umd.cs.guitar.replayer.GReplayerMonitorMock;
import edu.umd.cs.guitar.replayer.Replayer;
import edu.umd.cs.guitar.replayer.StepTypeMock;
import edu.umd.cs.guitar.replayer.TestCaseMock;
import junit.framework.TestCase;

public class StateMonitorFullTest extends TestCase {
	StateMonitorFull sMonitor;
	TestStepEndEventArgs tc;
	TestStepStartEventArgs ts;
	ComponentType component;
    GUIType window;
    StepTypeMock step;
    GReplayerMonitor gmonitor;
    Replayer rep;
 
    GApplication gApp1,gApp2;
		    /**
		     * set up the tests
		     */
	protected void setUp(){
		sMonitor= new StateMonitorFull(".//tests//inputs//ButtonDemo2.sta.xml");
		
		component = new ComponentType();
    	window = new GUIType();
		step = new StepTypeMock();
		tc =  new TSEndEvtArgsMock(step, component, window);
		ts = new TSStartEvtArgsMock(step);
		gmonitor=new GReplayerMonitorMock();
		String m_EFG = ".//tests//inputs//ButtonDemo.efg.xml";
		String m_GUI = ".//tests//inputs//ButtonDemo.gui.xml";
		TestCaseMock m_tsm = new TestCaseMock();
		try {
			rep = new Replayer(m_tsm,m_GUI,m_EFG);
			rep.setMonitor(gmonitor);
			sMonitor.replayer = rep ;
			gApp1= new GAppMock();
			gApp2 = new GAppMock("after");
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
     * This checks constructor.
     */
	public void testConstructor(){
		assertNotNull(sMonitor);
		
		assertNotNull(ts);
	}
	/**
     * This checks method init().
     */
	public void testInit(){
		sMonitor.init();
		assertNotNull(sMonitor.outTestCase);
		
		assertNotNull(sMonitor.monitor);
		assertNotNull(sMonitor.gApplication);
		File f1 = new File(sMonitor.sStateFile);
		assertTrue(f1.exists());
    
	}
	
	/**
     * This checks method AfterSetp.
     */
	public void testAfterStep(){
		sMonitor.outTestCase = new TestCaseMock();
		sMonitor.gApplication=gApp1;
		sMonitor.beforeStep(ts);
		sMonitor.gApplication=gApp2;
		sMonitor.monitor=gmonitor;
		sMonitor.afterStep(tc);
		
		assertFalse(sMonitor.outTestCase.getStep()==null);
		assertEquals(sMonitor.windowsAfterStep,gApp2.getCurrentWinID());
		File f1= new File(sMonitor.sStateFile);
		assertTrue(f1.exists());
		
	}
	/**
     * This checks method AfterSetp.
     * 
     * gApplication is not null
     */
	public void testBeforeStep(){
		sMonitor.gApplication=gApp1;
		sMonitor.beforeStep(ts);
		assertEquals(sMonitor.windowsBeforeStep,gApp1.getCurrentWinID());
	}
	
	
	
}
