/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.model;

import java.util.HashSet;
import java.util.Set;
import junit.framework.TestCase;

/**
 *jUnit test for JFCApplication
 */
public class JFCApplicationTest extends TestCase {
    
    public JFCApplicationTest(String testName) {
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
     * Test of connect method, of class JFCApplication.
     */
    public void testConnect_0args() throws Exception {
        System.out.println("connect");
        String[] surls = {};
        JFCApplication instance = new JFCApplication("edu.umd.cs.guitar.model.TestMain",surls);
        assertEquals(instance.sClassName, "edu.umd.cs.guitar.model.TestMain");
        instance.iInitialDelay = 1;
        TestMain.mainwasrun = false;
        instance.connect();
        assertEquals(true,TestMain.mainwasrun);
        
    }

    /**
     * Test of connect method, of class JFCApplication.
     */
    public void testConnect_StringArr() throws Exception {
        System.out.println("connect");
        String[] surls = {"file:test", "test:file"};
        String[] surlsEmpty = {};
        String[] args = {"arg1", "arg2"};
        Exception expException =null;

        JFCApplication instance;

        /* Test 1 - no main method */
        expException = null;
        instance = new JFCApplication("edu.umd.cs.guitar.model.TestNoMain",surls);
        assertEquals(instance.sClassName, "edu.umd.cs.guitar.model.TestNoMain");
        instance.iInitialDelay = 1;
        instance.connect(args);


        /* Test 2 - class not fond */
        expException = null;
        try {
            instance = new JFCApplication("edu.umd.cs.guitar.model.TestNotFound",surls);
        } catch (ClassNotFoundException e) {
            expException = e;
        }
        assertNotNull(expException);

        /* Test 3 - class with main + args */
        instance = new JFCApplication("edu.umd.cs.guitar.model.TestMain",surls);
        assertEquals(instance.sClassName, "edu.umd.cs.guitar.model.TestMain");
        instance.iInitialDelay = 1;
        TestMain.mainwasrun = false;
        instance.connect(args);
        assertEquals(true,TestMain.mainwasrun);

    }

    /**
     * Test of getAllWindow method, of class JFCApplication.
     */
    public void testGetAllWindow() throws Exception {
        System.out.println("getAllWindow");
        JFCApplication instance = null;
        Set expResult = null;

        /* setup */
        String[] surls = {"file:test"};
        instance = new JFCApplication("edu.umd.cs.guitar.model.TestMain",surls);
        assertEquals(instance.sClassName, "edu.umd.cs.guitar.model.TestMain");
        instance.iInitialDelay = 1;
        TestMain.mainwasrun = false;
        instance.connect();
        assertEquals(true,TestMain.mainwasrun);
        
        
        /* Test 1 - no frames */
        Set result = instance.getAllWindow();
        assertEquals(new HashSet(), result);


    }

}
