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
public class ContainerTypeTest extends TestCase {
    
    public ContainerTypeTest(String testName) {
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
     * Test of getContents method, of class ContainerType.
     */
    public void testGetContents() {
        System.out.println("[getContents]");
        ContainerType instance = new ContainerType();
        ContentsType expResult = new ContentsType();
        instance.contents=expResult;
        ContentsType result = instance.getContents();
        assertEquals(expResult, result);

    }

    /**
     * Test of setContents method, of class ContainerType.
     */
    public void testSetContents() {
        System.out.println("setContents");
        ContentsType value = new ContentsType();
        ContainerType instance = new ContainerType();
        instance.setContents(value);
        assertEquals(instance.contents, value);
    }

}
