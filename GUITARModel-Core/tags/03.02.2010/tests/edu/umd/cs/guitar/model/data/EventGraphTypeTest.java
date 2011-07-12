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
public class EventGraphTypeTest extends TestCase {
    
    public EventGraphTypeTest(String testName) {
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
     * Test of getRow method, of class EventGraphType.
     */
    public void testGetRow() {
        System.out.println("getRow");
        EventGraphType instance = new EventGraphType();
        List expResult = new ArrayList();

        /* Test 1 - row = null */
        List result = instance.getRow();
        assertEquals(expResult, result);

        /* Test 2 - row != null */
        instance.row=expResult;
        result = instance.getRow();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setRow method, of class EventGraphType.
     */
    public void testSetRow() {
        System.out.println("setRow");
        List<RowType> row = new ArrayList();
        EventGraphType instance = new EventGraphType();
        instance.setRow(row);
        assertEquals(instance.row,row);

    }

}
