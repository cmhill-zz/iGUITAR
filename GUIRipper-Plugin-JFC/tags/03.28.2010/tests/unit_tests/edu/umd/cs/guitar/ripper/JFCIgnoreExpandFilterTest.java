package edu.umd.cs.guitar.ripper;

import java.util.*;


import edu.umd.cs.guitar.model.*;
import edu.umd.cs.guitar.model.data.*;

import junit.framework.TestCase;

/**
 * The JFCIgnoreExpandFilterTest class implements unit tests for the member
 * functions defined in the JFCIgnoreExpandFilter.
 */
public class JFCIgnoreExpandFilterTest extends TestCase {
	
	/**List of widgets to ignore*/
	List<String> sIgnoreWidgetList;
	/**Mock object for test window*/
	GWindowStub window;
	/**Mock object for test component*/
	GComponentStub component;
	/** Sets up test objects */
	public void setUp() {
		sIgnoreWidgetList = new ArrayList<String>();
		window= new GWindowStub();
		component= new GComponentStub();
	}
	/** Tests that the JFCIgnoreExpandFilter constructor successfully creates a new object with
	 * sIgnoreWidgetList of size 0
	 */
	public void testJFCIgnoreExpandFilter() {
		JFCIgnoreExpandFilter test=new JFCIgnoreExpandFilter(sIgnoreWidgetList);
		assertEquals(0,test.sIgnoreWidgetList.size());
	}
	/**testIsProcessGComponentGWindow ensures that IsProcess returns false for 
	 * components that are not in its ignore list and true for those that are.
	 */
	public void testIsProcessGComponentGWindow() {
		List<String> sIgnoreWidgetList2 = new ArrayList<String>();
		sIgnoreWidgetList2.add("Name");
		JFCIgnoreExpandFilter test=new JFCIgnoreExpandFilter(sIgnoreWidgetList);
		JFCIgnoreExpandFilter test2=new JFCIgnoreExpandFilter(sIgnoreWidgetList2);
		assertFalse(test.isProcess(component, window));
		assertTrue(test2.isProcess(component, window));
		
	}
	/**This Test ensures that ripComponent always returns the component type for 
	 * the component passed in, which is null in this case */
	public void testRipComponentGComponentGWindow() {
		JFCIgnoreExpandFilter test=new JFCIgnoreExpandFilter(sIgnoreWidgetList);
		component.setComponentType(null);
		ComponentTypeStub a= (ComponentTypeStub) test.ripComponent(component,window);
		assertEquals(null,a);
	}
	
	public void CoverageIsProcessGComponentGWindow() {
		List<String> sIgnoreWidgetList2 = new ArrayList<String>();
		sIgnoreWidgetList2.add("Name");
		JFCIgnoreExpandFilter test=new JFCIgnoreExpandFilter(sIgnoreWidgetList);
		JFCIgnoreExpandFilter test2=new JFCIgnoreExpandFilter(sIgnoreWidgetList2);
		assertFalse(test.isProcess(component, window));
		assertTrue(test2.isProcess(component, window));
		
	}
}
