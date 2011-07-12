/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.model;

import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.GUIType;
import java.util.List;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * jUnit test for MExplorer
 */
public class MExplorerTest extends TestCase {
    
    public MExplorerTest(String testName) {
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
     * Test of getGUITypeByName method, of class MExplorer.
     */
    public void testGetGUITypeByName() {
        System.out.println("getGUITypeByName");
        String sGUIName = "";
        GUIStructure guiStructure = null;
        GUIType expResult = null;
        GUIType result = MExplorer.getGUITypeByName(sGUIName, guiStructure);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFirstPropertyByName method, of class MExplorer.
     */
    public void testGetFirstPropertyByName_String_ComponentType() {
        System.out.println("getFirstPropertyByName");
        String sName = "";
        ComponentType component = null;
        String expResult = "";
        String result = MExplorer.getFirstPropertyByName(sName, component);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFirstPropertyByName method, of class MExplorer.
     */
    public void testGetFirstPropertyByName_String_AttributesType() {
        System.out.println("getFirstPropertyByName");
        String sName = "";
        AttributesType attributes = null;
        String expResult = "";
        String result = MExplorer.getFirstPropertyByName(sName, attributes);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPropertyListByName method, of class MExplorer.
     */
    public void testGetPropertyListByName_String_ComponentType() {
        System.out.println("getPropertyListByName");
        String sName = "";
        ComponentType component = null;
        List expResult = null;
        List result = MExplorer.getPropertyListByName(sName, component);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPropertyListByName method, of class MExplorer.
     */
    public void testGetPropertyListByName_String_AttributesType() {
        System.out.println("getPropertyListByName");
        String sName = "";
        AttributesType attributes = null;
        List expResult = null;
        List result = MExplorer.getPropertyListByName(sName, attributes);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
