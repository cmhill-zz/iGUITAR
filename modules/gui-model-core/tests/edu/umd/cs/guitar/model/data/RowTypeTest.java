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
public class RowTypeTest extends TestCase {
    
    public RowTypeTest(String testName) {
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
     * Test of getE method, of class RowType.
     */
    public void testGetE() {
        System.out.println("getE");
        RowType instance = new RowType();
        List expResult = new ArrayList();

        /* Test 1 - e = null */
        List result = instance.getE();
        assertEquals(expResult, result);

        /* Test 2 - e != null */
        instance.e = expResult;
        result = instance.getE();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setE method, of class RowType.
     */
    public void testSetE() {
        System.out.println("setE");
        List<Integer> e = new ArrayList();
        RowType instance = new RowType();
        instance.setE(e);
        assertEquals(instance.e,e);
    }

}
