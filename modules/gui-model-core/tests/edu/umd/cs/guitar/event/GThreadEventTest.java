/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.event;

import edu.umd.cs.guitar.model.GComponent;
import junit.framework.TestCase;
import org.easymock.classextension.EasyMock;

/**
 *
 * @author user
 */
public class GThreadEventTest extends TestCase {
    
    public GThreadEventTest(String testName) {
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
     * Test of perform method, of class GThreadEvent.
     */
    public void testPerform_GComponent_Object() {
        System.out.println("perform");
        GComponent mockComp = EasyMock.createMock(GComponent.class);
        Object mockObj = EasyMock.createMock(Object.class);
        GThreadEvent threadEvent = new GThreadEventImpl();
        threadEvent.perform(mockComp, mockObj);
    }


    /**
     * Test of perform method, of class GThreadEvent.
     */
    public void testPerform_GComponent() {
        System.out.println("perform");
        System.out.println("perform");
        GComponent mockComp = EasyMock.createMock(GComponent.class);
        GThreadEvent threadEvent = new GThreadEventImpl();
        threadEvent.perform(mockComp);
    }


    public class GThreadEventImpl extends GThreadEvent {

        public void performImpl(GComponent gComponent) {
        }

        public void performImpl(GComponent gComponent, Object parameters) {
        }
    }

}
