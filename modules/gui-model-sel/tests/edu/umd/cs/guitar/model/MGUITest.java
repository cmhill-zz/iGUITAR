/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.model;

import edu.umd.cs.guitar.model.data.GUIType;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * jUnit tests for MGUI.
 * 
 */
public class MGUITest extends TestCase {
    
    public MGUITest(String testName) {
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
     * Test of getFirstComponentByName method, of class MGUI.
     */
    public void testGetFirstComponentByName() {
        System.out.println("getFirstComponentByName");
        String sName = "";
        MGUI instance = null;
        MComponent expResult = null;
        MComponent result = instance.getFirstComponentByName(sName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getData method, of class MGUI.
     */
    public void testGetData() {
        System.out.println("getData");
        MGUI instance = null;
        GUIType expResult = null;
        GUIType result = instance.getData();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTitle method, of class MGUI.
     */
    public void testGetTitle() {
        System.out.println("getTitle");
        MGUI instance = null;
        String expResult = "";
        String result = instance.getTitle();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setData method, of class MGUI.
     */
    public void testSetData() {
        System.out.println("setData");
        GUIType data = null;
        MGUI instance = null;
        instance.setData(data);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class MGUI.
     */
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        MGUI instance = null;
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
