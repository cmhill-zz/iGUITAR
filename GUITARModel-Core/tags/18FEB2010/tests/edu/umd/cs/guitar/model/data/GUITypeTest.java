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
public class GUITypeTest extends TestCase {
    
    public GUITypeTest(String testName) {
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
     * Test of getWindow method, of class GUIType.
     */
    public void testGetWindow() {
        System.out.println("getWindow");
        GUIType instance = new GUIType();
        ComponentType expResult = new ComponentType();
        instance.window = expResult;
        ComponentType result = instance.getWindow();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setWindow method, of class GUIType.
     */
    public void testSetWindow() {
        System.out.println("setWindow");
        ComponentType value = new ComponentType();
        GUIType instance = new GUIType();
        instance.setWindow(value);
        assertEquals(instance.window,value);
    }

    /**
     * Test of getContainer method, of class GUIType.
     */
    public void testGetContainer() {
        System.out.println("getContainer");
        GUIType instance = new GUIType();
        ContainerType expResult = new ContainerType();
        instance.container=expResult;
        ContainerType result = instance.getContainer();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setContainer method, of class GUIType.
     */
    public void testSetContainer() {
        System.out.println("setContainer");
        ContainerType value = new ContainerType();
        GUIType instance = new GUIType();
        instance.setContainer(value);
        assertEquals(instance.container,value);
    }

}
