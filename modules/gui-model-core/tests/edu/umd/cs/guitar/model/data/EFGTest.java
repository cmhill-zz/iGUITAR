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
public class EFGTest extends TestCase {
    
    public EFGTest(String testName) {
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
     * Test of getEvents method, of class EFG.
     */
    public void testGetEvents() {
        System.out.println("getEvents");
        EFG instance = new EFG();
        EventsType expResult = new EventsType();
        instance.events=expResult;
        EventsType result = instance.getEvents();
        assertEquals(expResult, result);

    }

    /**
     * Test of setEvents method, of class EFG.
     */
    public void testSetEvents() {
        System.out.println("setEvents");
        EventsType value = new EventsType();
        EFG instance = new EFG();
        instance.setEvents(value);
        assertEquals(instance.events, value);
    }

    /**
     * Test of getEventGraph method, of class EFG.
     */
    public void testGetEventGraph() {
        System.out.println("getEventGraph");
        EFG instance = new EFG();
        EventGraphType expResult = new EventGraphType();
        instance.eventGraph=expResult;
        EventGraphType result = instance.getEventGraph();
        assertEquals(expResult, result);

    }

    /**
     * Test of setEventGraph method, of class EFG.
     */
    public void testSetEventGraph() {
        System.out.println("setEventGraph");
        EventGraphType value = new EventGraphType();
        EFG instance = new EFG();
        instance.setEventGraph(value);
        assertEquals(instance.eventGraph,value);
    }

}
