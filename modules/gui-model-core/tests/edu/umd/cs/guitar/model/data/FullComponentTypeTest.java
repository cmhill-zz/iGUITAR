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
public class FullComponentTypeTest extends TestCase {
    
    public FullComponentTypeTest(String testName) {
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
     * Test of getWindow method, of class FullComponentType.
     */
    public void testGetWindow() {
        System.out.println("getWindow");
        FullComponentType instance = new FullComponentType();
        ComponentType expResult = new ComponentType();
        instance.window = expResult;
        ComponentType result = instance.getWindow();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setWindow method, of class FullComponentType.
     */
    public void testSetWindow() {
        System.out.println("setWindow");
        ComponentType value = new ComponentType();
        FullComponentType instance = new FullComponentType();
        instance.setWindow(value);
        assertEquals(instance.window,value);
    }

    /**
     * Test of getComponent method, of class FullComponentType.
     */
    public void testGetComponent() {
        System.out.println("getComponent");
        FullComponentType instance = new FullComponentType();
        ComponentType expResult = new ComponentType();
        instance.component=expResult;
        ComponentType result = instance.getComponent();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setComponent method, of class FullComponentType.
     */
    public void testSetComponent() {
        System.out.println("setComponent");
        ComponentType value = new ComponentType();
        FullComponentType instance = new FullComponentType();
        instance.setComponent(value);
        assertEquals(instance.component,value);
    }

}
