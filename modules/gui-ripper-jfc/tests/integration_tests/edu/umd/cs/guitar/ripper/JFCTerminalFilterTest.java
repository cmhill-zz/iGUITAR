package edu.umd.cs.guitar.ripper;

import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.model.*;
import edu.umd.cs.guitar.model.data.*;
import edu.umd.cs.guitar.model.wrapper.*;

import edu.umd.cs.guitar.ripper.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.accessibility.Accessible;
import javax.swing.*;

import junit.framework.TestCase;
/**
 * The JFCTerminalFilterTest class implements unit tests for the member
 * functions defined in the JFCTerminalFilter.
 */
public class JFCTerminalFilterTest extends TestCase {	
	/**Mock object for test window*/
	GComponentStub c;
	/** Sets up test objects */
	public void setUp() {
		c= new GComponentStub();
	}
	/**JFCTerminalFilter was implemented as a singleton class for some crazy reason. testGetInstance
	 * ensures that a valid object is always returned. 
	 */
	public void testGetInstance() {
		JFCTerminalFilter test=(JFCTerminalFilter)JFCTerminalFilter.getInstance();
		assertNotNull(test);
	}
	
	/**testIsProcessGComponentGWindow ensures Terminal objects passed into isProcess
	 * equal true and all other components equal false
	 */
	public void testIsProcessGComponentGWindow() {
		JFCTerminalFilter test=(JFCTerminalFilter)JFCTerminalFilter.getInstance();
		assertFalse(test.isProcess(c,null));
		GComponentStub c2= new GComponentStub();
		c2.setTypeVal(GUITARConstants.TERMINAL);
		assertTrue(test.isProcess(c2,null));
	}
	/**This Test ensures that ripComponent always returns the component type for 
	 * the component passed in, which is null in this case */
	public void testRipComponentGComponentGWindow() {
		JFCTerminalFilter test=(JFCTerminalFilter)JFCTerminalFilter.getInstance();
		c.setComponentType(null);
		ComponentType a=test.ripComponent(c,null);
		assertEquals(null,a);	
	}

}
