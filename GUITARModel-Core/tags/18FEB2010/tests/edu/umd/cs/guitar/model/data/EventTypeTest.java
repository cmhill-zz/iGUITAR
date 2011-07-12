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
public class EventTypeTest extends TestCase {
    
    public EventTypeTest(String testName) {
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
     * Test of getEventId method, of class EventType.
     */
    public void testGetEventId() {
        System.out.println("getEventId");
        EventType instance = new EventType();
        String expResult = "";
        instance.eventId=expResult;
        String result = instance.getEventId();
        assertEquals(expResult, result);

    }

    /**
     * Test of setEventId method, of class EventType.
     */
    public void testSetEventId() {
        System.out.println("setEventId");
        String value = "";
        EventType instance = new EventType();
        instance.setEventId(value);
        assertEquals(instance.eventId,value);
    }

    /**
     * Test of getWidgetId method, of class EventType.
     */
    public void testGetWidgetId() {
        System.out.println("getWidgetId");
        EventType instance = new EventType();
        String expResult = "";
        instance.widgetId=expResult;
        String result = instance.getWidgetId();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setWidgetId method, of class EventType.
     */
    public void testSetWidgetId() {
        System.out.println("setWidgetId");
        String value = "";
        EventType instance = new EventType();
        instance.setWidgetId(value);
        assertEquals(instance.widgetId,value);
    }

    /**
     * Test of getType method, of class EventType.
     */
    public void testGetType() {
        System.out.println("getType");
        EventType instance = new EventType();
        String expResult = "";
        instance.type=expResult;
        String result = instance.getType();
        assertEquals(expResult, result);

    }

    /**
     * Test of setType method, of class EventType.
     */
    public void testSetType() {
        System.out.println("setType");
        String value = "";
        EventType instance = new EventType();
        instance.setType(value);
        assertEquals(instance.type,value);
    }

    /**
     * Test of isInitial method, of class EventType.
     */
    public void testIsInitial() {
        System.out.println("isInitial");
        EventType instance = new EventType();
        boolean expResult = false;
        instance.initial=expResult;
        boolean result = instance.isInitial();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setInitial method, of class EventType.
     */
    public void testSetInitial() {
        System.out.println("setInitial");
        boolean value = false;
        EventType instance = new EventType();
        instance.setInitial(value);
        assertEquals(instance.initial,value);
    }

    /**
     * Test of getAction method, of class EventType.
     */
    public void testGetAction() {
        System.out.println("getAction");
        EventType instance = new EventType();
        String expResult = "";
        instance.action=expResult;
        String result = instance.getAction();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setAction method, of class EventType.
     */
    public void testSetAction() {
        System.out.println("setAction");
        String value = "";
        EventType instance = new EventType();
        instance.setAction(value);
        assertEquals(instance.action,value);
    }

    /**
     * Test of getOptional method, of class EventType.
     */
    public void testGetOptional() {
        System.out.println("getOptional");
        EventType instance = new EventType();
        AttributesType expResult = new AttributesType();
        instance.optional = expResult;
        AttributesType result = instance.getOptional();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setOptional method, of class EventType.
     */
    public void testSetOptional() {
        System.out.println("setOptional");
        AttributesType value = new AttributesType();
        EventType instance = new EventType();
        instance.setOptional(value);
        assertEquals(instance.optional,value);
    }

}
