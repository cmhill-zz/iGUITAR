/**
 * 
 */
package edu.umd.cs.guitar.model;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.umd.cs.guitar.util.Debugger;

/**
 * @author baonn
 * 
 */
public class JFCApplicationTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	String testdataDir="/home/baonn/Desktop/tmp/guitar/modules/gui-model-jfc/test-data";
	String sJarFile = testdataDir + "/radio.jar";
	String[] URLs =new String[0];

	@Test
	public void testJarLoad(){
		
		try {
			JFCApplication app = new JFCApplication(sJarFile, true, URLs);
			app.connect();
			Debugger.pause("Press ENTER to continue...");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
