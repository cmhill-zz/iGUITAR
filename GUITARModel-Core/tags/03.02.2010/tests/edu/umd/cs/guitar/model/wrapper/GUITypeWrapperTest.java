/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.model.wrapper;

import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.GUIType;
import java.util.Set;
import junit.framework.TestCase;

/**
 *
 * @author user
 */
public class GUITypeWrapperTest extends TestCase {
    
    public GUITypeWrapperTest(String testName) {
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
     * Test of toString method, of class GUITypeWrapper.
     */
    public void testToString() {
        System.out.println("toString");
        GUITypeWrapper instance = new GUITypeWrapper(new GUIType());
        String ID = "0";
        String expResult = "GUITypeWrapper [ID=" + ID + "]";
        instance.ID = ID;
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of parseData method, of class GUITypeWrapper.
     */
    public void testParseData() {
        System.out.println("parseData");
        GUITypeWrapper instance = new GUITypeWrapper(new GUIType());

    }

    /**
     * Test of getInvoker method, of class GUITypeWrapper.
     */
    public void testGetInvoker() {
        System.out.println("getInvoker");
        GUITypeWrapper instance = new GUITypeWrapper(new GUIType());
        ComponentTypeWrapper expResult = new ComponentTypeWrapper(new ComponentType());
        instance.invoker = expResult;
        ComponentTypeWrapper result = instance.getInvoker();
        assertEquals(expResult, result);
    }

    /**
     * Test of setInvoker method, of class GUITypeWrapper.
     */
    public void testSetInvoker() {
        System.out.println("setInvoker");
        GUITypeWrapper instance = new GUITypeWrapper(new GUIType());
        ComponentTypeWrapper expResult = new ComponentTypeWrapper(new ComponentType());
        instance.setInvoker(expResult);
        ComponentTypeWrapper result =  instance.invoker;
        assertEquals(expResult, result);
    }

    /**
     * Test of getContainer method, of class GUITypeWrapper.
     */
    public void testGetContainer() {
        System.out.println("getContainer");
        GUITypeWrapper instance = new GUITypeWrapper(new GUIType());
        ComponentTypeWrapper expResult = new ComponentTypeWrapper(new ComponentType());
        instance.container = expResult;
        ComponentTypeWrapper result = instance.getContainer();
        assertEquals(expResult, result);
    }

    /**
     * Test of getData method, of class GUITypeWrapper.
     */
    public void testGetData() {
        System.out.println("getData");
        GUITypeWrapper instance = new GUITypeWrapper(new GUIType());
        GUIType expResult = new GUIType();
        instance.dGUIType = expResult;
        GUIType result = instance.getData();
        assertEquals(expResult, result);
    }

    /**
     * Test of getChildByID method, of class GUITypeWrapper.
     */
    public void testGetChildByID() {
        System.out.println("getChildByID");
        String ID = "";
        GUITypeWrapper instance = null;
        ComponentTypeWrapper expResult = null;
        ComponentTypeWrapper result = instance.getChildByID(ID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setValueByName method, of class GUITypeWrapper.
     */
    public void testSetValueByName() {
        System.out.println("setValueByName");
        String sTitle = "";
        String sName = "";
        String sValue = "";
        GUITypeWrapper instance = null;
        instance.setValueByName(sTitle, sName, sValue);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addValueByName method, of class GUITypeWrapper.
     */
    public void testAddValueByName() {
        System.out.println("addValueByName");
        String sTitle = "";
        String sName = "";
        String sValue = "";
        GUITypeWrapper instance = null;
        instance.addValueByName(sTitle, sName, sValue);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class GUITypeWrapper.
     */
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        GUITypeWrapper instance = null;
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTitle method, of class GUITypeWrapper.
     */
    public void testGetTitle() {
        System.out.println("getTitle");
        GUITypeWrapper instance = null;
        String expResult = "";
        String result = instance.getTitle();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTitle method, of class GUITypeWrapper.
     */
    public void testSetTitle() {
        System.out.println("setTitle");
        String sTitle = "";
        GUITypeWrapper instance = null;
        instance.setTitle(sTitle);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isRoot method, of class GUITypeWrapper.
     */
    public void testIsRoot() {
        System.out.println("isRoot");
        GUITypeWrapper instance = null;
        boolean expResult = false;
        boolean result = instance.isRoot();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isModal method, of class GUITypeWrapper.
     */
    public void testIsModal() {
        System.out.println("isModal");
        GUITypeWrapper instance = null;
        boolean expResult = false;
        boolean result = instance.isModal();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAvailableWindowList method, of class GUITypeWrapper.
     */
    public void testGetAvailableWindowList() {
        System.out.println("getAvailableWindowList");
        GUITypeWrapper instance = null;
        Set expResult = null;
        Set result = instance.getAvailableWindowList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
