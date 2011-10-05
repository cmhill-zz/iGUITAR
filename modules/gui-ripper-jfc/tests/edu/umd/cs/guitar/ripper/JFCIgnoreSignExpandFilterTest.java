package edu.umd.cs.guitar.ripper;

import java.util.*;


import edu.umd.cs.guitar.model.*;
import edu.umd.cs.guitar.model.data.*;
import edu.umd.cs.guitar.model.wrapper.*;
import junit.framework.TestCase;
/**
 * The JFCIgnoreSignExpandFilterTest class implements unit tests for the member
 * functions defined in the JFCIgnoreSignExpandFilter.
 */
public class JFCIgnoreSignExpandFilterTest extends TestCase {
	/**List of components to ignore*/
	List<FullComponentType> lIgnoreFullComponent;
	/**Mock object for test window*/
	GWindowStub window;
	/**Mock object for test component*/
	GComponentStub component;
	/** Sets up test objects */
	public void setUp() {
		lIgnoreFullComponent = new ArrayList<FullComponentType>();
		window= new GWindowStub();
		component= new GComponentStub();
	}
	/** Tests that the JFCIgnoreExpandFilter constructor successfully creates a new object with
	 * lIgnoreFullComponent of size 0
	 */
	public void testJFCIgnoreSignExpandFilter() {
		JFCIgnoreSignExpandFilter test=new JFCIgnoreSignExpandFilter(lIgnoreFullComponent);
		assertEquals(0,test.lIgnoreFullComponent.size());
	}

	/**testIsProcessGComponentGWindow ensures that IsProcess returns false for 
	 * components that are not in its ignore list and true for those that are.
	 */
	public void testIsProcessGComponentGWindow() {
		FullComponentTypeStub fullcomp = new FullComponentTypeStub();
		fullcomp.setComponent(component.extractProperties());
		fullcomp.setWindow(window.extractWindow().getWindow());
		lIgnoreFullComponent.add(fullcomp);
		JFCIgnoreSignExpandFilter test=new JFCIgnoreSignExpandFilter(lIgnoreFullComponent);
		assertTrue(test.isProcess(component, window));
		
		JFCIgnoreSignExpandFilter test2=new JFCIgnoreSignExpandFilter(new ArrayList<FullComponentType>());
		assertFalse(test2.isProcess(component, window));
	}
	/** 
	 * testIsProcessGComponentGWindow2 tests a special branch in which the window in the 
	 * ignore list is null
	 */
	public void testIsProcessGComponentGWindow2() {
		FullComponentTypeStub fullcomp = new FullComponentTypeStub();
		fullcomp.setComponent(component.extractProperties());
		fullcomp.setWindow(null);
		lIgnoreFullComponent.add(fullcomp);
		JFCIgnoreSignExpandFilter test=new JFCIgnoreSignExpandFilter(lIgnoreFullComponent);
		assertTrue(test.isProcess(component, window));
	}
	/** testIsProcessGComponentGWindow3 tests a special branch where the passed inwindow contains all 
	 * the attributes of a window in the ignore list
	 */
	public void testIsProcessGComponentGWindow3() {
		FullComponentTypeStub fullcomp = new FullComponentTypeStub();
		List<String> pvalues =new ArrayList<String>();
		pvalues.add("blah");
		PropertyType p=new PropertyType();
		p.setName("blah");
		p.setValue(pvalues);
		ComponentTypeStub component2= new ComponentTypeStub();
		component2.at.friendProperites.add(p);
		fullcomp.setComponent(component.extractProperties());
		fullcomp.setWindow(component2);
		lIgnoreFullComponent.add(fullcomp);
		JFCIgnoreSignExpandFilter test=new JFCIgnoreSignExpandFilter(lIgnoreFullComponent);
		assertFalse(test.isProcess(component, window));
	}
	/**testIsProcessGComponentGWindow3 tests a special branch where the passed in component contains all 
	 * the attributes of a component in the ignore list
	 */
	public void testIsProcessGComponentGWindow4() {
		FullComponentTypeStub fullcomp = new FullComponentTypeStub();
		List<String> pvalues =new ArrayList<String>();
		pvalues.add("blah");
		PropertyType p=new PropertyType();
		p.setName("blah");
		p.setValue(pvalues);
		ComponentTypeStub component2= new ComponentTypeStub();
		component2.at.friendProperites.add(p);
		fullcomp.setComponent(component2);
		fullcomp.setWindow(window.extractWindow().getWindow());
		lIgnoreFullComponent.add(fullcomp);
		JFCIgnoreSignExpandFilter test=new JFCIgnoreSignExpandFilter(lIgnoreFullComponent);
		assertFalse(test.isProcess(component, window));
	}
	/**This Test ensures that ripComponent always returns the component type for 
	 * the component passed in, which is null in this case */
	public void testRipComponentGComponentGWindow() {
		JFCIgnoreSignExpandFilter test=new JFCIgnoreSignExpandFilter(lIgnoreFullComponent);
		component.setComponentType(null);
		ComponentTypeStub a=(ComponentTypeStub)test.ripComponent(component,window);
		assertEquals(null,a);
	}
	
}
