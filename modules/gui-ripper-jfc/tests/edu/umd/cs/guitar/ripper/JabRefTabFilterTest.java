package edu.umd.cs.guitar.ripper;

import junit.framework.TestCase;

import edu.umd.cs.guitar.model.*;
import edu.umd.cs.guitar.model.data.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The JabRefTabFilterTest class implements unit tests for the member
 * functions defined in the JabRefTabFilter. This class appears to be
 * a work in progress so only fully operational functions are tested.
 */
public class JabRefTabFilterTest extends TestCase {
	/**Mock object for test window*/
	GWindowStub window;
	/**Mock object for test component*/
	JFCComponentStub component;
	/** Sets up test objects */
	public void setUp() {
		window= new GWindowStub();
		component= new JFCComponentStub(new AccessibleStub());
	}
	/**JabRefTabFilter was implemented as a singleton class for some reason. testGetInstance
	 * ensures that a valid object is always returned. 
	 */
	public void testGetInstance() {
		/*GComponentFilter testJabRefTabFilter;
		testJabRefTabFilter=JabRefTabFilter.getInstance();
		assertNotNull(testJabRefTabFilter);*/
		//JabRefTabFilter no longer a class
	}
	/**testIsProcessGComponentGWindow ensures that IsProcess always returns false
	 */
	public void testIsProcessGComponentGWindow() {
		/*GComponentFilter testJabRefTabFilter;
		testJabRefTabFilter=JabRefTabFilter.getInstance();		
		assertFalse(testJabRefTabFilter.isProcess(component,window));*/
		//JabRefTabFilter no longer a class
	}
	/**RipComponentGComponentGWindow has a debug call in it that pauses execution
	 *and is therefore unsuitable for testing 
	 */
	public void testRipComponentGComponentGWindow() {
		
	}

}

