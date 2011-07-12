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
public class EventsTypeTest extends TestCase {
    
    public EventsTypeTest(String testName) {
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
     * Test of getEvent method, of class EventsType.
     */
    public void testGetEvent() {
        System.out.println("getEvent");
        EventsType instance = new EventsType();
        List expResult = new ArrayList();

        /* test 1 - event = null */
        List result = instance.getEvent();
        assertEquals(expResult, result);

        /* test 2 - event != null */
        instance.event=expResult;
        result = instance.getEvent();
        assertEquals(expResult, result);

    }

    /**
     * Test of setEvent method, of class EventsType.
     */
    public void testSetEvent() {
        System.out.println("setEvent");
        List<EventType> event = new ArrayList();
        EventsType instance = new EventsType();
        instance.setEvent(event);
        assertEquals(instance.event, event);

    }

}
