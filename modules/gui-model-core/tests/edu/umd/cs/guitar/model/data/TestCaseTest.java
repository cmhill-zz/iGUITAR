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
public class TestCaseTest extends TestCase {
    
    public TestCaseTest(String testName) {
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
     * Test of getStep method, of class TestCase.
     */
    public void testGetStep() {
        System.out.println("getStep");
        edu.umd.cs.guitar.model.data.TestCase instance = new edu.umd.cs.guitar.model.data.TestCase();
        List expResult = new ArrayList();

        /* Test 1 - step = null */
        List result = instance.getStep();
        assertEquals(expResult, result);


        /* Test 2 - step != null */
        instance.step = expResult;
        result = instance.getStep();
        assertEquals(expResult,result);
    }

    /**
     * Test of setStep method, of class TestCase.
     */
    public void testSetStep() {
        System.out.println("setStep");
        List<StepType> step = new ArrayList();
        edu.umd.cs.guitar.model.data.TestCase instance = new edu.umd.cs.guitar.model.data.TestCase();
        instance.setStep(step);
        assertEquals(instance.step,step);
    }

}
