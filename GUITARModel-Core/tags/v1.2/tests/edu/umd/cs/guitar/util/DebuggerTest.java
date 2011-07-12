/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.util;

import edu.umd.cs.guitar.model.data.ComponentType;
import java.io.IOException;
import junit.framework.TestCase;

/**
 *
 * @author user
 */
public class DebuggerTest extends TestCase {
    
    public DebuggerTest(String testName) {
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
     * Test of main method, of class Debugger.
     * main is empty. testing for completeness
     */
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Debugger.main(args);
    }

    /**
     * Test of pause method, of class Debugger.
     */
    public void testPause_0args() {
        System.out.println("pause");
        Debugger.pause();
        // nothing to check, always succeeds
    }

    /**
     * Test of pause method, of class Debugger.
     *  Always succeeds
     */
    public void testPause_Object()  {
        System.out.println("pause");
        Object msg = "Hello";
        try {
            System.in.close();
            Debugger.pause(msg); }
        catch (IOException e) {

        }

    }

    /**
     * Test of println method, of class Debugger.
     * Always succeeds
     */
    public void testPrintln() {
        System.out.println("println");
        Debugger.println("Hello world");


    }

    /**
     * Test of printlnMethodSupported method, of class Debugger.
     *  Always succeeds
     */
    public void testPrintlnMethodSupported() {
        System.out.println("printlnMethodSupported");
        String test = new String("test");
        Debugger.printlnMethodSupported(test);
    }

    /**
     * Test of printlnBeanMethodSupported method, of class Debugger.
     *  Always succeeds
     */
    public void testPrintlnBeanMethodSupported() {
        System.out.println("printlnBeanMethodSupported");
        Object obj = new ComponentType();
        Debugger.printlnBeanMethodSupported(obj);

    }

}
