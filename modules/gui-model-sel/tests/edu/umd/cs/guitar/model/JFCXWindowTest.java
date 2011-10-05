/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.model;

import edu.umd.cs.guitar.model.data.GUIType;
import java.awt.Window;
import java.util.List;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleState;
import javax.accessibility.AccessibleStateSet;
import junit.framework.TestCase;
import org.easymock.classextension.EasyMock;

/**
 * jUnit test for JFCXWindow
 */
public class JFCXWindowTest extends TestCase {
    
    public JFCXWindowTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getWindow method, of class JFCXWindow.
     */
    public void testGetWindow() {
        System.out.println("getWindow");
        JFCXWindow instance = new JFCXWindow(null);
        Window expResult = null;
        Window result = instance.getWindow();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of extractGUIProperties method, of class JFCXWindow.
     */
    public void testExtractGUIProperties() {
        System.out.println("extractGUIProperties");
        JFCXWindow instance = null;
        GUIType expResult = null;
        GUIType result = instance.extractGUIProperties();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContainer method, of class JFCXWindow.
     */
    public void testGetContainer() {
        System.out.println("getContainer");
        Window mockWindow;
        mockWindow = EasyMock.createMock(Window.class);
        JFCXWindow instance = new JFCXWindow(mockWindow);
        EasyMock.replay(mockWindow);
        GComponent result = instance.getContainer();
        assertEquals(((JFCXComponent)result).aComponent , mockWindow);
        
    }

    /**
     * Test of isModal method, of class JFCXWindow.
     */
    public void testIsModal() {
        System.out.println("isModal");
        Window mockWindow;
        AccessibleContext mockAccessibleContext;
        AccessibleStateSet mockStates;

        /* test 1 - context = null */
        mockWindow = EasyMock.createMock(Window.class);
        mockAccessibleContext = EasyMock.createMock(AccessibleContext.class);
        JFCXWindow instance = new JFCXWindow(mockWindow);
        instance.window = mockWindow;
        EasyMock.expect(mockWindow.getAccessibleContext()).andReturn(null);
        EasyMock.replay(mockWindow,mockAccessibleContext);
        boolean expResult = false;
        boolean result = instance.isModal();
        assertEquals(expResult, result);

        /* test 2 - not states.contains(AccessibleState.MODAL) */
        mockWindow = EasyMock.createMock(Window.class);
        mockAccessibleContext = EasyMock.createMock(AccessibleContext.class);
        mockStates = EasyMock.createMock(AccessibleStateSet.class);
        instance = new JFCXWindow(mockWindow);
        instance.window = mockWindow;
        EasyMock.expect(mockWindow.getAccessibleContext()).andReturn(mockAccessibleContext);
        EasyMock.expect(mockAccessibleContext.getAccessibleStateSet()).andReturn(mockStates);
        EasyMock.expect(mockStates.contains(AccessibleState.MODAL)).andReturn(false);
        EasyMock.replay(mockWindow,mockAccessibleContext,mockStates);
        expResult = false;
        result = instance.isModal();
        assertEquals(expResult, result);

        /* test 3 - states.contains(AccessibleState.MODAL) */
        mockWindow = EasyMock.createMock(Window.class);
        mockAccessibleContext = EasyMock.createMock(AccessibleContext.class);
        mockStates = EasyMock.createMock(AccessibleStateSet.class);
        instance = new JFCXWindow(mockWindow);
        instance.window = mockWindow;
        EasyMock.expect(mockWindow.getAccessibleContext()).andReturn(mockAccessibleContext);
        EasyMock.expect(mockAccessibleContext.getAccessibleStateSet()).andReturn(mockStates);
        EasyMock.expect(mockStates.contains(AccessibleState.MODAL)).andReturn(true);
        EasyMock.replay(mockWindow,mockAccessibleContext,mockStates);
        expResult = true;
        result = instance.isModal();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getFullID method, of class JFCXWindow.
     */
    public void testGetFullID() {
        System.out.println("getFullID");
        JFCXWindow instance = null;
        String expResult = "";
        String result = instance.getTitle();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class JFCXWindow.
     */
    public void testGetName() {
        System.out.println("getName");
        AccessibleContext mockAccessibleContext;
        Window mockWindow;
        
        /* Test 1 - aContext = null */
        mockWindow = EasyMock.createMock(Window.class);
        mockAccessibleContext = EasyMock.createMock(AccessibleContext.class);
        
        JFCXWindow instance = new JFCXWindow(mockWindow);
        EasyMock.expect(mockWindow.getAccessibleContext()).andReturn(null);
        EasyMock.replay(mockWindow);

        String expResult = mockWindow.getClass().getName();
        String result = instance.getTitle();
        assertEquals(expResult, result);

        /* Test 2 - aContext != null and sName = null */
        mockWindow = EasyMock.createMock(Window.class);
        mockAccessibleContext = EasyMock.createMock(AccessibleContext.class);

        instance = new JFCXWindow(mockWindow);
        EasyMock.expect(mockWindow.getAccessibleContext()).andReturn(mockAccessibleContext);
        EasyMock.expect(mockAccessibleContext.getAccessibleName()).andReturn(null);
        EasyMock.replay(mockWindow,mockAccessibleContext);

        expResult =mockWindow.getClass().getName();
        result = instance.getTitle();
        assertEquals(expResult, result);

        /* Test 3 - aContext != null and sName != null */
        mockWindow = EasyMock.createMock(Window.class);
        mockAccessibleContext = EasyMock.createMock(AccessibleContext.class);

        instance = new JFCXWindow(mockWindow);
        EasyMock.expect(mockWindow.getAccessibleContext()).andReturn(mockAccessibleContext);
        EasyMock.expect(mockAccessibleContext.getAccessibleName()).andReturn("test");
        EasyMock.replay(mockWindow,mockAccessibleContext);

        expResult = "test";
        result = instance.getTitle();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getGUIProperties method, of class JFCXWindow.
     */
    public void testGetGUIProperties() {
        System.out.println("getGUIProperties");
        JFCXWindow instance = null;
        List expResult = null;
        List result = instance.getGUIProperties();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals1 method, of class JFCXWindow.
     */
    public void testEquals1() {
        System.out.println("equals1");
        Object window = null;
        JFCXWindow instance = null;
        boolean expResult = false;
        boolean result = instance.equals(window);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class JFCXWindow.
     */
    public void testHashCode() {
        System.out.println("hashCode");
        JFCXWindow instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class JFCXWindow.
     */
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        JFCXWindow instance = null;
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isValid method, of class JFCXWindow.
     */
    public void testIsValid() {
        System.out.println("isValid");
        JFCXWindow instance = null;
        boolean expResult = false;
        boolean result = instance.isValid();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
