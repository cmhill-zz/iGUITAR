/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.model.data;

import junit.framework.TestCase;

/**
 *
 * @author user
 */
public class ComponentTypeTest extends TestCase {
    
    public ComponentTypeTest(String testName) {
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
     * Test of getAttributes method, of class ComponentType.
     */
    public void testGetAttributes() {
        System.out.println("[getAttributes]");
        ComponentType compType = new ComponentType();
        AttributesType expResult = new AttributesType();
        compType.attributes=expResult;
        AttributesType result = compType.getAttributes();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAttributes method, of class ComponentType.
     */
    public void testSetAttributes() {
        System.out.println("[setAttributes]");
        AttributesType value = new AttributesType();
        ComponentType instance = new ComponentType();
        instance.setAttributes(value);
        assertEquals(instance.attributes, value);

    }

}
