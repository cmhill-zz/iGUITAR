/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.model.wrapper;

import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.GUIType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import junit.framework.TestCase;

/**
 *
 * @author user
 */
public class GUIStructureWrapperTest extends TestCase {
    
    public GUIStructureWrapperTest(String testName) {
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
     * Test of parseData method, of class GUIStructureWrapper.
     */
    public void testParseData() {
        System.out.println("parseData");
        GUIStructureWrapper instance = null;
        instance.parseData();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addGUI method, of class GUIStructureWrapper.
     */
    public void testAddGUI_GUIType() {
        System.out.println("addGUI");
        GUIType dGUI = new GUIType();
        GUIStructureWrapper instance = new GUIStructureWrapper(new GUIStructure());
        instance.dGUIStructure.setGUI(new ArrayList());
        instance.addGUI(dGUI);
        assert(instance.dGUIStructure.getGUI().contains(dGUI));
    }

    /**
     * Test of addGUI method, of class GUIStructureWrapper.
     */
    public void testAddGUI_GUITypeWrapper() {
        System.out.println("addGUI");
        GUIType dGUI = new GUIType();
        GUITypeWrapper mGUI = new GUITypeWrapper(dGUI);
        GUIStructureWrapper instance = new GUIStructureWrapper(new GUIStructure());
        instance.dGUIStructure.setGUI(new ArrayList());
        instance.addGUI(mGUI);
        assert(instance.dGUIStructure.getGUI().contains(dGUI));
    }

    /**
     * Test of removeGUI method, of class GUIStructureWrapper.
     */
    public void testRemoveGUI() {
        System.out.println("removeGUI");
        GUITypeWrapper mGUI = null;
        GUIStructureWrapper instance = null;
        instance.removeGUI(mGUI);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGUIByTitle method, of class GUIStructureWrapper.
     */
    public void testGetGUIByTitle() {
        System.out.println("getGUIByTitle");
        String sTitle = "";
        GUIStructureWrapper instance = null;
        GUITypeWrapper expResult = null;
        GUITypeWrapper result = instance.getGUIByTitle(sTitle);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of contains method, of class GUIStructureWrapper.
     */
    public void testContains() {
        System.out.println("contains");
        GUITypeWrapper obj = null;
        GUIStructureWrapper instance = null;
        boolean expResult = false;
        boolean result = instance.contains(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getData method, of class GUIStructureWrapper.
     */
    public void testGetData() {
        System.out.println("getData");
        GUIStructureWrapper instance = null;
        GUIStructure expResult = null;
        GUIStructure result = instance.getData();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRoot method, of class GUIStructureWrapper.
     */
    public void testGetRoot() {
        System.out.println("getRoot");
        GUIStructureWrapper instance = null;
        GUITypeWrapper expResult = null;
        GUITypeWrapper result = instance.getRoot();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getComponentFromID method, of class GUIStructureWrapper.
     */
    public void testGetComponentFromID() {
        System.out.println("getComponentFromID");
        String ID = "";
        GUIStructureWrapper instance = null;
        ComponentTypeWrapper expResult = null;
        ComponentTypeWrapper result = instance.getComponentFromID(ID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addValueBySignature method, of class GUIStructureWrapper.
     */
    public void testAddValueBySignature() {
        System.out.println("addValueBySignature");
        AttributesType signature = null;
        String name = "";
        Set<String> values = null;
        GUIStructureWrapper instance = null;
        instance.addValueBySignature(signature, name, values);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGUIs method, of class GUIStructureWrapper.
     */
    public void testGetGUIs() {
        System.out.println("getGUIs");
        GUIStructureWrapper instance = null;
        List expResult = null;
        List result = instance.getGUIs();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
