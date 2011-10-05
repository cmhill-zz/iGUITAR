package edu.umd.cs.guitar.replayer.monitor;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.umd.cs.guitar.model.data.ComponentType;

import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.replayer.GReplayerMonitor;
import edu.umd.cs.guitar.replayer.Replayer;
import edu.umd.cs.guitar.replayer.GReplayerMonitorMock;
import edu.umd.cs.guitar.replayer.StepTypeMock;
import edu.umd.cs.guitar.replayer.TestCaseMock;
import edu.umd.cs.guitar.replayer.monitor.StateMonitor;
import junit.framework.TestCase;
/**
 * This checks StateMonitor
 */
public class StateMonitorTest extends TestCase {
	StateMonitor sMonitor;
	TestStepEndEventArgs tc;
	ComponentType component;
    GUIType window;
    StepTypeMock step;
    
    /**
     * Set up the tests
     */
	protected void setUp() throws Exception {
	//	super.setUp();
		//sMonitor= new StateMonitor(".//tests//inputs//ButtonDemo1.sta.xml");
		sMonitor= new StateMonitor(".//inputs//ButtonDemo1.sta.xml");
		component = new ComponentType();
    	window = new GUIType();
		step = new StepTypeMock();
		tc =  new TSEndEvtArgsMock(step, component, window);
		
	}
	
	/**
     * This checks constructor.
     */
	public void testConstructor(){
		assertNotNull(sMonitor);
		assertNotNull(tc);
	}
	/**
     * This checks method init.
     */
	public void testInit(){
		sMonitor.init();
		assertNotNull(sMonitor.outTestCase);
	}
	
	/**
     * This checks method testAfterStep.
     */
	public void testAfterStep(){
		TestStepEndEventArgs ltc =  new TestStepEndEventArgs(step, component, window);
		sMonitor.outTestCase = new TestCaseMock();
		GReplayerMonitor monitor=new GReplayerMonitorMock();
		//String m_EFG = ".//tests//inputs//ButtonDemo.efg.xml";
		//String m_GUI = ".//tests//inputs//ButtonDemo.gui.xml";
		String m_EFG = ".//inputs//Project.EFG.xml";
		String m_GUI = ".//inputs//Project.GUI.xml";
		
		TestCaseMock m_tsm = new TestCaseMock();
		Replayer rep;
		try {
			rep = new Replayer(m_tsm,m_GUI,m_EFG);
			rep.setMonitor(monitor);
			sMonitor.replayer = rep ;
			
			sMonitor.afterStep(ltc);
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
		
		assertFalse(sMonitor.outTestCase.getStep()==null);
		File f1= new File(sMonitor.sStateFile);
		assertTrue(f1.exists());
		
	}

}
