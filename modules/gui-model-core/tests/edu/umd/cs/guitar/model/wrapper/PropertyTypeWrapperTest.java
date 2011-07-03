/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.model.wrapper;

import junit.framework.TestCase;

/**
 *
 * @author user
 */
public class PropertyTypeWrapperTest extends TestCase {
    
    public PropertyTypeWrapperTest(String testName) {
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
     * Test of equals method, of class PropertyTypeWrapper.
     */
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        PropertyTypeWrapper instance = null;
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
