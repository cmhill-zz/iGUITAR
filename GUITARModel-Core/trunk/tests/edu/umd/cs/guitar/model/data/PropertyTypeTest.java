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
public class PropertyTypeTest extends TestCase {
    
    public PropertyTypeTest(String testName) {
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
     * Test of getName method, of class PropertyType.
     */
    public void testGetName() {
        System.out.println("getName");
        PropertyType instance = new PropertyType();
        String expResult = "";
        instance.name = expResult;
        String result = instance.getName();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setName method, of class PropertyType.
     */
    public void testSetName() {
        System.out.println("setName");
        String value = "";
        PropertyType instance = new PropertyType();
        instance.setName(value);
        assertEquals(instance.name,value);
    }

    /**
     * Test of getValue method, of class PropertyType.
     */
    public void testGetValue() {
        System.out.println("getValue");
        PropertyType instance = new PropertyType();
        List expResult = new ArrayList();

        /* Test 1 - value = null */
        List result = instance.getValue();
        assertEquals(expResult, result);

        /* Test 2 - value != null */
        instance.value = expResult;
        result = instance.getValue();
        assertEquals(expResult,result);
        
    }

    /**
     * Test of setValue method, of class PropertyType.
     */
    public void testSetValue() {
        System.out.println("setValue");
        List<String> value = new ArrayList();
        PropertyType instance = new PropertyType();
        instance.setValue(value);
        assertEquals(instance.value,value);
    }

}
