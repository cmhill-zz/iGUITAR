/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.model.data;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author user
 */
public class GUIStructureTest extends TestCase {
    
    public GUIStructureTest(String testName) {
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
     * Test of getGUI method, of class GUIStructure.
     */
    public void testGetGUI() {
        System.out.println("getGUI");
        GUIStructure instance = new GUIStructure();
        List expResult = new ArrayList();

        /* Test 1 - gui = null */
        List result = instance.getGUI();
        assertEquals(expResult, result);

        /* Test 2 - gui != null */
        instance.gui = expResult;
        result = instance.getGUI();
        assertEquals(expResult,result);
    }

    /**
     * Test of setGUI method, of class GUIStructure.
     */
    public void testSetGUI() {
        System.out.println("setGUI");
        List<GUIType> gui = new ArrayList();
        GUIStructure instance = new GUIStructure();
        instance.setGUI(gui);
        assertEquals(instance.gui,gui);
    }

}
