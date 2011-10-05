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
public class StepTypeTest extends TestCase {
    
    public StepTypeTest(String testName) {
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
     * Test of getEventId method, of class StepType.
     */
    public void testGetEventId() {
        System.out.println("getEventId");
        StepType instance = new StepType();
        String expResult = "";
        instance.eventId=expResult;
        String result = instance.getEventId();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setEventId method, of class StepType.
     */
    public void testSetEventId() {
        System.out.println("setEventId");
        String value = "";
        StepType instance = new StepType();
        instance.setEventId(value);
        assertEquals(instance.eventId,value);
    }

    /**
     * Test of isReachingStep method, of class StepType.
     */
    public void testIsReachingStep() {
        System.out.println("isReachingStep");
        StepType instance = new StepType();
        boolean expResult = false;
        instance.reachingStep = expResult;
        boolean result = instance.isReachingStep();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setReachingStep method, of class StepType.
     */
    public void testSetReachingStep() {
        System.out.println("setReachingStep");
        boolean value = false;
        StepType instance = new StepType();
        instance.setReachingStep(value);
        assertEquals(instance.reachingStep,value);
    }

    /**
     * Test of getParameter method, of class StepType.
     */
    public void testGetParameter() {
        System.out.println("getParameter");
        StepType instance = new StepType();
        List expResult = new ArrayList();

        /* Test 1 - parameter = null */
        List result = instance.getParameter();
        assertEquals(expResult, result);

        /* Test 2 - parameter != null */
        instance.parameter = expResult;
        result = instance.getParameter();
        assertEquals(expResult,result);
    }

    /**
     * Test of getOptional method, of class StepType.
     */
    public void testGetOptional() {
        System.out.println("getOptional");
        StepType instance = new StepType();
        AttributesType expResult = new AttributesType();
        instance.optional = expResult;
        AttributesType result = instance.getOptional();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setOptional method, of class StepType.
     */
    public void testSetOptional() {
        System.out.println("setOptional");
        AttributesType value = new AttributesType();
        StepType instance = new StepType();
        instance.setOptional(value);
        assertEquals(instance.optional,value);
    }

    /**
     * Test of getGUIStructure method, of class StepType.
     */
    public void testGetGUIStructure() {
        System.out.println("getGUIStructure");
        StepType instance = new StepType();
        GUIStructure expResult = new GUIStructure();
        instance.guiStructure = expResult;
        GUIStructure result = instance.getGUIStructure();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setGUIStructure method, of class StepType.
     */
    public void testSetGUIStructure() {
        System.out.println("setGUIStructure");
        GUIStructure value = new GUIStructure();
        StepType instance = new StepType();
        instance.setGUIStructure(value);
        assertEquals(instance.guiStructure, value);
    }

    /**
     * Test of setParameter method, of class StepType.
     */
    public void testSetParameter() {
        System.out.println("setParameter");
        List<String> parameter = new ArrayList();
        StepType instance = new StepType();
        instance.setParameter(parameter);
        assertEquals(instance.parameter,parameter);
    }

}
