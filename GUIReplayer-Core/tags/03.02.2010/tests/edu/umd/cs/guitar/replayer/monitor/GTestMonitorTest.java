
package edu.umd.cs.guitar.replayer.monitor;

import junit.framework.TestCase;
import edu.umd.cs.guitar.replayer.Replayer;
import edu.umd.cs.guitar.replayer.TestCaseMock;

/**
 * @author Ran
 * This is the test for GTestMonitor
 */
public class GTestMonitorTest extends TestCase {

	
	 private GTestMonitorMock m_gtmm;
	 private Replayer m_replayer;
	 private TestCaseMock m_tsm;
	 private String m_EFG;
	 private String m_GUI;
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		m_EFG = ".//tests//inputs//ButtonDemo.efg.xml";
		m_GUI = ".//tests//inputs//ButtonDemo.gui.xml";
		m_tsm = new TestCaseMock();
		m_gtmm = new GTestMonitorMock();
		m_replayer= new Replayer(m_tsm,m_GUI,m_EFG);
	}

	/**
	 * Test get
	 */

	public void testGet( ) {
		 assertEquals( m_gtmm.getReplayer(), null );
		 
	 }
	
	/**
	 * Test set
	 */
	 public void testSet( ){
		 Replayer lreplayer = new Replayer(null);
		 m_gtmm.setReplayer(lreplayer);
		 assertNotNull( m_gtmm.getReplayer());
		 m_gtmm.setReplayer(m_replayer);
		 assertEquals( m_gtmm.getReplayer(), m_replayer );
		 
	 }
}
