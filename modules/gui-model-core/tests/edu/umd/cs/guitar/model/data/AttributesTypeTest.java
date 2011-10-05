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
public class AttributesTypeTest extends TestCase {

    public AttributesTypeTest(String testName) {
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
     * Test of getProperty method, of class AttributesType.
     * Test Case 1 - property is null
     * Test 2 - property returned is the one set manaully
     *
     */
    public void testGetProperty() {
        System.out.println("[getProperty]");
        AttributesType attrType = new AttributesType();
        List<PropertyType> proplist = new ArrayList();

        /* Test 1 - property is null */
        assertEquals(attrType.getProperty(), proplist);
        System.out.println("[getProperty|Test 1]- PASSED");

        /* Test 2 - property returned is the one set manaully */
        attrType.property = proplist;
        assertEquals(attrType.getProperty(),proplist);
        System.out.println("[getProperty|Test 2]- PASSED");
    }

    /**
     * Test of setProperty method, of class AttributesType.
     */
    public void testSetProperty() {
        System.out.println("[setProperty]");
        AttributesType attrType = new AttributesType();
        List<PropertyType> proplist = new ArrayList();
        /* Test 1 - setting empty array list */
        attrType.setProperty(proplist);
        assertEquals(attrType.property, proplist);
        System.out.println("[setProperty |Test 1]- PASSED");
    }

}
