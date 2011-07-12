package edu.umd.cs.guitar.ripper;
import junit.framework.TestCase;

/**
 * The IgnoreWindowFilterTest class implements unit tests for the member
 * functions defined in the IgnoreWindowFilter.
 */
public class IgnoreWindowFilterTest extends TestCase {
	/**Mock object for test window*/
	GWindowStub window= new GWindowStub();
	
	/**This Test ensures that isProcess always returns false */
	public void testIsProcessGWindow() {
		IgnoreWindowFilter iwf = new IgnoreWindowFilter();
		assertFalse(iwf.isProcess(window));
	}
	/**This Test ensures that ripWindow always returns the component type for the component passed in */
	public void testRipWindowGWindow() {
		IgnoreWindowFilter iwf = new IgnoreWindowFilter();
		assertEquals(iwf.ripWindow(window),null);
	}

}
