/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.model.wrapper;

import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.GUIStructure;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author user
 */
public class EFGWrapperTest extends TestCase {
    
    public EFGWrapperTest(String testName) {
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
     * Test of parseEFG method, of class EFGWrapper.
     */
    public void testParseEFG() {
        System.out.println("parseEFG");
        GUIStructure dGUIStructure = null;
        EFGWrapper instance = new EFGWrapper();
        instance.parseEFG(dGUIStructure);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEfg method, of class EFGWrapper.
     */
    public void testGetEfg() {
        System.out.println("getEfg");
        EFGWrapper instance = new EFGWrapper();
        EFG expResult = null;
        EFG result = instance.getEfg();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getwEventList method, of class EFGWrapper.
     */
    public void testGetwEventList() {
        System.out.println("getwEventList");
        EFGWrapper instance = new EFGWrapper();
        List expResult = null;
        List result = instance.getwEventList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
