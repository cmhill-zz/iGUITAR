/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.event;

import edu.umd.cs.guitar.model.GComponent;
import junit.framework.TestCase;
import org.easymock.classextension.EasyMock;

/**
 *jUnit test for  JFCValueHandler
 */
public class JFCValueHandlerTest extends TestCase {
    
    public JFCValueHandlerTest(String testName) {
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
     * Test of performImpl method, of class JFCValueHandler.
     */
    public void testPerformImpl_GComponent() {
        System.out.println("performImpl");
        GComponent component = EasyMock.createMock(GComponent.class);
        JFCValueHandler instance = new JFCValueHandler();
        instance.performImpl(component);
        
    }

    /**
     * Test of performImpl method, of class JFCValueHandler.
     */
    public void testPerformImpl_GComponent_Object() {
        System.out.println("performImpl");
        GComponent gComponent = EasyMock.createMock(GComponent.class);
        Object parameters = null;
        JFCValueHandler instance = new JFCValueHandler();
        instance.performImpl(gComponent, parameters);
        
    }

}
