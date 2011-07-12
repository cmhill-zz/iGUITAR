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
public class EventWrapperTest extends TestCase {
    
    public EventWrapperTest(String testName) {
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
     * Test of getAction method, of class EventWrapper.
     */
    public void testGetAction() {
        System.out.println("getAction");
        EventWrapper instance = new EventWrapper();
        String expResult = "";
        String result = instance.getAction();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAction method, of class EventWrapper.
     */
    public void testSetAction() {
        System.out.println("setAction");
        String action = "";
        EventWrapper instance = new EventWrapper();
        instance.setAction(action);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getComponent method, of class EventWrapper.
     */
    public void testGetComponent() {
        System.out.println("getComponent");
        EventWrapper instance = new EventWrapper();
        ComponentTypeWrapper expResult = null;
        ComponentTypeWrapper result = instance.getComponent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setComponent method, of class EventWrapper.
     */
    public void testSetComponent() {
        System.out.println("setComponent");
        ComponentTypeWrapper component = null;
        EventWrapper instance = new EventWrapper();
        instance.setComponent(component);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isFollowedBy method, of class EventWrapper.
     */
    public void testIsFollowedBy() {
        System.out.println("isFollowedBy");
        EventWrapper other = null;
        EventWrapper instance = new EventWrapper();
        int expResult = 0;
        int result = instance.isFollowedBy(other);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isHidden method, of class EventWrapper.
     */
    public void testIsHidden() {
        System.out.println("isHidden");
        EventWrapper instance = new EventWrapper();
        boolean expResult = false;
        boolean result = instance.isHidden();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getType method, of class EventWrapper.
     */
    public void testGetType() {
        System.out.println("getType");
        EventWrapper instance = new EventWrapper();
        String expResult = "";
        String result = instance.getType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
