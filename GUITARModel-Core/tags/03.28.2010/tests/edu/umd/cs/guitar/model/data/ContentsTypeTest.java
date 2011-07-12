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
public class ContentsTypeTest extends TestCase {
    
    public ContentsTypeTest(String testName) {
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
     * Test of getWidgetOrContainer method, of class ContentsType.
     */
    public void testGetWidgetOrContainer() {
        System.out.println("getWidgetOrContainer");
        ContentsType instance = new ContentsType();
        List expResult = new ArrayList();

        /* Test 1 - widgetOrContainer is null */
        List result = instance.getWidgetOrContainer();
        assertEquals(expResult, result);

        /* Test 2 - widgetOrContainer is not null */
        instance.widgetOrContainer=expResult;
        result = instance.getWidgetOrContainer();
        assertEquals(result,expResult);

    }

    /**
     * Test of setWidgetOrContainer method, of class ContentsType.
     */
    public void testSetWidgetOrContainer() {
        System.out.println("setWidgetOrContainer");
        List<ComponentType> widgetOrContainer = new ArrayList();
        ContentsType instance = new ContentsType();
        instance.setWidgetOrContainer(widgetOrContainer);
        assertEquals(instance.widgetOrContainer,widgetOrContainer);
    }

}
