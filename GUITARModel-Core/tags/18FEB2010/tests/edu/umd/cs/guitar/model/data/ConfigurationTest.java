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
public class ConfigurationTest extends TestCase {
    
    public ConfigurationTest(String testName) {
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
     * Test of getIgnoredComponents method, of class Configuration.
     */
    public void testGetIgnoredComponents() {
        System.out.println("[getIgnoredComponents]");
        Configuration instance = new Configuration();
        ComponentListType expResult = new ComponentListType();
        instance.ignoredComponents = expResult;
        ComponentListType result = instance.getIgnoredComponents();
        assertEquals(expResult, result);

    }

    /**
     * Test of setIgnoredComponents method, of class Configuration.
     */
    public void testSetIgnoredComponents() {
        System.out.println("setIgnoredComponents");
        ComponentListType value = new ComponentListType();
        Configuration instance = new Configuration();
        instance.setIgnoredComponents(value);
        assertEquals(instance.ignoredComponents, value);
    }

    /**
     * Test of getTerminalComponents method, of class Configuration.
     */
    public void testGetTerminalComponents() {
        System.out.println("getTerminalComponents");
        Configuration instance = new Configuration();
        ComponentListType expResult = new ComponentListType();
        instance.terminalComponents=expResult;
        ComponentListType result = instance.getTerminalComponents();
        assertEquals(expResult, result);

    }

    /**
     * Test of setTerminalComponents method, of class Configuration.
     */
    public void testSetTerminalComponents() {
        System.out.println("setTerminalComponents");
        ComponentListType value = new ComponentListType();
        Configuration instance = new Configuration();
        instance.setTerminalComponents(value);
        assertEquals(instance.terminalComponents,value);
    }

}
