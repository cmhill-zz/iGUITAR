package edu.umd.cs.guitar.ripper;

import java.awt.*;
import java.awt.event.WindowEvent;

import javax.accessibility.*;
import javax.swing.*;

import edu.umd.cs.guitar.model.*;
import edu.umd.cs.guitar.ripper.JFCRipperConfiguration;
import junit.framework.TestCase;
/**
 * The JFCRipperMointorTest class implements unit tests for the member
 * functions defined in the JFCRipperMointor.
 */
public class JFCRipperMointorTest extends TestCase {
	
	/** Dummy Configuration*/
	JFCRipperConfiguration config;
	/** instance of class under testing*/
	JFCRipperMointor monitor;
	/**Mock object for test window*/
	GWindowStub window;
	/**Mock object for test component*/
	GComponentStub component;
	/** Sets up test objects */
	protected void setUp() {
		config = new JFCRipperConfiguration();
		monitor= new JFCRipperMointor(config);
		window= new GWindowStub();
		component= new GComponentStub();
	}
	/**
	 * testSetUp tests the SetUp function by checking to make sure the 
	 * Monitor successfully connects to the application under test. THis is done by
	 * checking how many windows are opened by the MAIN_CLASS. In this case it is 0
	 * because the class does not exist
	 * */
	public void testSetUp() {
		int len = Frame.getFrames().length;
		JFCRipperConfiguration.MAIN_CLASS="NoClass";
		monitor.setUp();
		assertEquals(len,Frame.getFrames().length);
	}
	/**
	 * testSetUp tests the SetUp function by checking to make sure the 
	 * Monitor successfully connects to the application under test. THis is done by
	 * checking how many windows are opened by the MAIN_CLASS. In this case it is 0
	 * because the class does not exist. URL_LIST and ARGUMENT_LIST are set to cover
	 * additional branches
	 * */
	public void testSetUp2() {
		int len = Frame.getFrames().length;
		JFCRipperConfiguration.MAIN_CLASS="NoClass";
		JFCRipperConfiguration.URL_LIST="URLS";
		JFCRipperConfiguration.ARGUMENT_LIST="Arguments";
		monitor.setUp();
		assertEquals(len,Frame.getFrames().length);
	}
	/**
	 * testSetUp tests the SetUp function by checking to make sure the 
	 * Monitor successfully connects to the application under test. THis is done by
	 * checking how many windows are opened by the MAIN_CLASS. In this case it is 1
	 * window opened 
	 * */
	public void testSetUp3() {
		int len = Frame.getFrames().length;
		JFCRipperConfiguration.MAIN_CLASS="edu.umd.cs.guitar.ripper.testcase.OneWindow";
		monitor.setUp();
		assertEquals(len+1,Frame.getFrames().length);
	}
	
/** no need to implement test for CleanUp...empty void function */
	public void testCleanUp() {		
  	}
	/** Tests IsNewWindowOpened by ensuring false is returned when tempOpenedWinStack.size=0 and 
	 * true when tempOpenedWinStack.size>0*/
	public void testIsNewWindowOpened() {
		assertFalse(monitor.isNewWindowOpened());
		monitor.tempOpenedWinStack.add(null);		
		assertTrue(monitor.isNewWindowOpened());
	}
	/** Tests testIsWindowClosed by ensuring false is returned when tempClosedWinStack=0 and 
	 * true when tempClosedWinStack>0*/
	public void testIsWindowClosed() {
		assertFalse(monitor.isWindowClosed());
		monitor.tempClosedWinStack.add(null);		
		assertTrue(monitor.isWindowClosed());
	}
	/** Tests ResetWindowCache by ensuring tempClosedWinStack and tempOpenedWinStack
	 * equal zero after it is called*/
	public void testResetWindowCache() {
		monitor.resetWindowCache();
		assertEquals(0,monitor.tempClosedWinStack.size());
		assertEquals(0,monitor.tempOpenedWinStack.size());
	}

	/** Tests Constructor by ensuring the config is set*/
	public void testJFCRipperMointorJFCRipperConfiguration() {
		JFCRipperMointor monitortest= new JFCRipperMointor(config);
		assertTrue(config.equals(monitortest.configuration));
	}

	//This is deprecated
	/** Tests deprecated Constructor by ensuring the config is set*/
	 public void testJFCRipperMointorString() { 		
		JFCRipperMointor monitortest= new JFCRipperMointor("blah");
		assertEquals("blah",JFCRipperConfiguration.MAIN_CLASS);
	}
	 /** Tests deprecated Constructor by ensuring the config is set*/
	 public void testJFCRipperMointorStringLog() { 		
		JFCRipperMointor monitortest= new JFCRipperMointor("blah2",null);
		assertEquals("blah2",JFCRipperConfiguration.MAIN_CLASS);
	}
	 /** Tests closeWindow by ensuring a mock window is set to invisible after closeWindow is called on it*/
	public void testCloseWindowGWindow() {
		OneWindow w = new OneWindow();
		JFCWindowStub f = new JFCWindowStub(w);
		assertTrue(f.getWindow().isVisible());
		monitor.closeWindow(f);
		assertFalse(f.getWindow().isVisible());
	}

	/** Tests expandGUI by ensuring 
	 * getOpenedWindowCache=0
	 * getClosedWindowCache=0
	 * lRippedWindow=0
	 * for a null object*/
	public void testExpandGUIGComponent() {
		monitor.expandGUI(null);
		assertEquals(monitor.getOpenedWindowCache().size(),0);
		assertEquals(monitor.getClosedWindowCache().size(),0);
		assertEquals(monitor.lRippedWindow.size(),0);
	}
	
	/** Tests expandGUI by ensuring 
	 * getOpenedWindowCache=0
	 * getClosedWindowCache=0
	 * lRippedWindow=0
	 * for a GComponentStub component*/
	public void testExpandGUIGComponent2() {
		monitor.expandGUI(new GComponentStub());
		assertEquals(monitor.getOpenedWindowCache().size(),0);
		assertEquals(monitor.getClosedWindowCache().size(),0);
		assertEquals(monitor.lRippedWindow.size(),0);
		
		//OneWindow onewin = new OneWindow();
		//MyEventQueue e = new MyEventQueue();
		//WindowEvent w1= new WindowEvent(onewin,WindowEvent.WINDOW_OPENED);
		//WindowEvent w2= new WindowEvent(onewin,WindowEvent.WINDOW_CLOSING);
		//WindowEvent w3= new WindowEvent(onewin,WindowEvent.WINDOW_ACTIVATED);
		//WindowEvent w4= new WindowEvent(onewin,WindowEvent.WINDOW_DEACTIVATED);
		//WindowEvent w5= new WindowEvent(onewin,WindowEvent.WINDOW_STATE_CHANGED);

		//e.dispatchEvent(w1);
		//e.dispatchEvent(w2);
		//e.dispatchEvent(w3);
		//e.dispatchEvent(w4);
		//e.dispatchEvent(w5);
		//onewin.dispose();
	}
	/** Tests getOpenedWindowCache by ensuring 0 is returned when tempOpenedWinStack.size=0 and 
	 * 1 when tempOpenedWinStack.size>0*/
	public void testGetOpenedWindowCache() {
		assertEquals(monitor.getOpenedWindowCache().size(),0);
		monitor.tempOpenedWinStack.add(null);
		assertEquals(monitor.getOpenedWindowCache().size(),1);
	}
	/** Tests getClosedWindowCache by ensuring 0 is returned when tempClosedWinStack.size=0 and 
	 * 1 when tempClosedWinStack.size>0*/
	public void testGetClosedWindowCache() {
		assertEquals(monitor.getClosedWindowCache().size(),0);
		monitor.tempClosedWinStack.add(null);		
		assertEquals(monitor.getClosedWindowCache().size(),1);
	}
	/** Tests the number of root windows found by getRootWindows by running
	 * it on a null GUI an Invisible GUI and GUI and opens One Window
	 * */
	public void testGetRootWindows() {
		int len = monitor.getRootWindows().size();
		assertEquals(1,monitor.getRootWindows().size());
		OneWindow newW = new OneWindow();
		assertEquals(2,monitor.getRootWindows().size());
		NoWindow invisibleW = new NoWindow();
		assertEquals(2,monitor.getRootWindows().size());
		newW.dispose();
		invisibleW.dispose();
	}
	/** Tests the number of root windows found by getRootWindows by running
	 * it on a GUI and opens One Window. sRootWindows is set to test a special branch
	 * */
	public void testGetRootWindows2() {
		monitor.sRootWindows.add("OneWindow testcase");
		OneWindow newW1 = new OneWindow();
		assertEquals(1,monitor.getRootWindows().size());
		newW1.dispose();
	}
	
	/**
	 * Checks if IsExpandable can handle a null object
	 */
	public void testIsExpandableGComponentGWindow() {
		JFCComponentStub a = new JFCComponentStub(null);
		assertFalse(monitor.isExpandable(a, null));
	}
	/**
	 * Checks if IsExpandable can handle a components with null or empty names
	 */
	public void testIsExpandableGComponentGWindow2() {
		dummyComponent b =  new dummyComponent(new JButton());
		b.setName("");
		assertFalse(monitor.isExpandable(b, null));
		b.setName(null);
		assertFalse(monitor.isExpandable(b, null));
	}
	/**
	 * Checks if IsExpandable can non-clickable components
	 */
	public void testIsExpandableGComponentGWindow3() {
		OneWindow w = new OneWindow();
		w.setName("blah");
		dummyComponent c = new dummyComponent(w);
		c.setName("blah");
		assertFalse(monitor.isExpandable(c, null));
		
		AccessibleStub astub = new AccessibleStub();
		JFCComponentStub d = new JFCComponentStub ( new AccessibleStub());
		assertFalse(monitor.isExpandable(d, null));
	}
	/**
	 * Checks if IsExpandable can handle a terminal object
	 */
	public void testIsExpandableGComponentGWindow4() {
		dummyComponent b =  new dummyComponent(new JButton());
		b.setName("title");
		assertFalse(monitor.isExpandable(b, null));
		b.Type="non-terminal";
		assertTrue(monitor.isExpandable(b, null));
	}
	/**
	 * Checks if IsExpandable can handle a valid object
	 */
	public void testIsExpandableGComponentGWindow5() {
		dummyComponent b =  new dummyComponent(new JButton());
		b.setName("title");
		assertFalse(monitor.isExpandable(b, null));
		b.Type="non-terminal";
		assertTrue(monitor.isExpandable(b, null));
	}
		
	/**
	 * Checks if isIgnoredWindow returns false if window is not is sIgnoreWindowList and true if it is
	 */
	public void testIsIgnoredWindowGWindow() {	
		assertFalse(monitor.isIgnoredWindow(window));
		monitor.sIgnoreWindowList.add("Name");
		assertTrue(monitor.isIgnoredWindow(window));

	}
	/**
	 * Checks if addRootWindow increases the size of sRootWindows when it is called
	 */
	public void testAddRootWindow() {		
		int size=monitor.sRootWindows.size();
		
		monitor.addRootWindow(window.getName());
		assertEquals(monitor.sRootWindows.size(),size+1);
	}
	/**
	 * The OneWindow class is a Mock GUI that is used to create a visible GUI
	 */
	class OneWindow extends Frame{
		OneWindow(){
			this.setVisible(true);
		}
	}
	/**
	 * The OneWindow class is a Mock GUI that is used to create an invisible GUI
	 */
	class NoWindow extends Frame{
		NoWindow(){
			this.setVisible(false);
		}
	}
	/**
	 * The dummyComponent class is a Mock Component that is used to create a terminal JFC Component
	 */
	private class dummyComponent extends JFCXComponent{
		public dummyComponent(Accessible arg0) {
			super(arg0);
			Type=GUITARConstants.TERMINAL;
			// TODO Auto-generated constructor stub
		}
		String name="";
		String Type;
		public String getName() {
			return name;
		}
		public void setName(String n) {
			name=n;
		}
		public AccessibleContext getAccessibleContext(){
			return null;
		}
		public String getTypeVal() {
				return Type;
		}
	}
	/*public class MyEventQueue extends EventQueue {
		  protected void dispatchEvent(AWTEvent event) {
		    // the only functionality I add is that I print out all the events
		    //System.out.println(event);
		    super.dispatchEvent(event);
		  }
		}*/
}
