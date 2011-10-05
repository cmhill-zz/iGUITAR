/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.model;

import edu.umd.cs.guitar.model.data.ComponentType;
import java.util.List;
import junit.framework.TestCase;

/**
 * jUnit test for MComponent
 */
public class MComponentTest extends TestCase {
    
    public MComponentTest(String testName) {
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
     * Test of getFirstComponentByName method, of class MComponent.
     */
    public void testGetFirstComponentByName() {
        System.out.println("getFirstComponentByName");
        String sName = "";
         MComponent instance = null;
        MComponent expResult = null;
        MComponent result = instance.getFirstComponentByName(sName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEvent method, of class MComponent.
     */
    public void testGetEvent() {
        System.out.println("getEvent");
        MComponent instance = null;
        List expResult = null;
        List result = instance.getEvent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class MComponent.
     */
    public void testGetID() {
        System.out.println("getID");
        MComponent instance = null;
        String expResult = "";
        String result = instance.getID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class MComponent.
     */
    public void testGetName() {
        System.out.println("getName");
        MComponent instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getParent method, of class MComponent.
     */
    public void testGetParent() {
        System.out.println("getParent");
        MComponent instance = null;
        MComponent expResult = null;
        MComponent result = instance.getParent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWindow method, of class MComponent.
     */
    public void testGetWindow() {
        System.out.println("getWindow");
        MComponent instance = null;
        MGUI expResult = null;
        MGUI result = instance.getWindow();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLChildrenList method, of class MComponent.
     */
    public void testGetLChildrenList() {
        System.out.println("getLChildrenList");
        MComponent instance = null;
        List expResult = null;
        List result = instance.getLChildrenList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMInvokeWindow method, of class MComponent.
     */
    public void testGetMInvokeWindow() {
        System.out.println("getMInvokeWindow");
        MComponent instance = null;
        MGUI expResult = null;
        MGUI result = instance.getMInvokeWindow();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printInfo method, of class MComponent.
     */
    public void testPrintInfo() {
        System.out.println("printInfo");
        MComponent instance = null;
        instance.printInfo();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getData method, of class MComponent.
     */
    public void testGetData() {
        System.out.println("getData");
        MComponent instance = null;
        ComponentType expResult = null;
        ComponentType result = instance.getData();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addProperty method, of class MComponent.
     */
    public void testAddProperty() {
        System.out.println("addProperty");
        String sName = "";
        String sValue = "";
        MComponent instance = null;
        instance.addProperty(sName, sValue);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasProperty method, of class MComponent.
     */
    public void testHasProperty() {
        System.out.println("hasProperty");
        String sName = "";
        MComponent instance = null;
        boolean expResult = false;
        boolean result = instance.hasProperty(sName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
