package edu.umd.cs.guitar.ripper;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Window;
import java.util.ArrayList;

import javax.accessibility.Accessible;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import edu.umd.cs.guitar.model.JFCXComponent;
import edu.umd.cs.guitar.model.JFCXWindow;
import edu.umd.cs.guitar.model.data.ComponentType;

import junit.framework.TestCase;
/**
 * The JFCTabFilterTest class implements unit tests for the member
 * functions defined in the JFCTabFilter.
 */
public class JFCTabFilterTest extends TestCase {

	
	/**Mock object for test window*/
	GWindowStub window;
	/** Sets up test objects */
	public void setUp() {
		window= new GWindowStub();
	}
	/**JFCTabFilter was implemented as a singleton class for some reason. testGetInstance
	 * ensures that a valid object is always returned by GetInstance. 
	 */
	public void testGetInstance() {
		GComponentFilter test=JFCTabFilter.getInstance();
		assertNotNull(test);
	}
	
	/**testIsProcessGComponentGWindow ensures  JTabbedPane objects passed into isProcess
	 * equal true and all other components equal false
	 */
	public void testIsProcessGComponentGWindow() {
		JTabbedPane tab = new JTabbedPane();
		JButton button = new JButton();
		JFCComponentStub component= new JFCComponentStub(tab);
		JFCComponentStub component2= new JFCComponentStub(button);
		
		GComponentFilter test=JFCTabFilter.getInstance();
		assertFalse(test.isProcess(component2,window));
		assertTrue(test.isProcess(component,window));
	}

	/**
	 * This Test ensures valid component properties are returned by RipComponent with tabbed panes. 
	 * It is important that the pain contain components because those properties are included as well.
	 * A mock ripper is used to simulate the ripper's expected behavior.
	 */
	public void testRipComponentGComponentGWindow() {
		JTabbedPane tab = new JTabbedPane();
		JFCComponentStub component= new JFCComponentStub(tab);
		
		GComponentFilter test=JFCTabFilter.getInstance();
		ComponentType a=test.ripComponent(component,window);
		assertEquals(a.getAttributes().getProperty().size(),0);

		
		OneWindow w = new OneWindow();
		JFCWindowStub f = new JFCWindowStub(w);
		JFCXComponent c2 = new JFCXComponent(w.tabbedPane);
		test.ripper=new RipperStub ();
		a=test.ripComponent(c2,f);
		assertEquals(a.getAttributes().getProperty().size(),16);
		if(a.getAttributes().getProperty().size()>0){
			assertEquals("[javax.swing.JTabbedPane]",a.getAttributes().getProperty().get(1).getValue().toString());
		}
	}
	/**
	 * The OneWindow class is a Mock GUI that is used to create a JTabbedPane object in a valid window
	 * with sub components. This is used to rest that ripped components gives valid output
	 */
	private class OneWindow extends JFrame {
		public Container content;
		public JButton button1;
		public JTabbedPane tabbedPane;
	    public OneWindow() {
	        this("OneWindow testcase");
	    }
	    
	    public OneWindow(String title ) {
	        super(title);
	        content = getContentPane();
	        content.setBackground(Color.white);
	        content.setLayout(new FlowLayout());
	        button1 = new JButton("1st Button");
	        tabbedPane = new JTabbedPane();
	        content.add(tabbedPane);	        
	        tabbedPane.add(button1);
	        pack();
	        setVisible(true);
	    }
	}
}
