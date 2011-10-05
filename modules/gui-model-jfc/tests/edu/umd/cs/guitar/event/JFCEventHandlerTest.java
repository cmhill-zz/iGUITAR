/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.event;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.JFCXComponent;
import javax.accessibility.Accessible;
import junit.framework.TestCase;
import org.easymock.classextension.EasyMock;

/**
 *jUnit test for JFCEventHandler
 */
public class JFCEventHandlerTest extends TestCase {
    
    public JFCEventHandlerTest(String testName) {
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
     * Test of getAccessible method, of class JFCEventHandler.
     */
    public void testGetAccessible() {
        System.out.println("getAccessible");
        JFCXComponent mockComponent;
        Accessible mockAccessible;
        JFCEventHandler instance = new JFCEventHandlerImpl();

        mockComponent = EasyMock.createMock(JFCXComponent.class);
        mockAccessible = EasyMock.createMock(Accessible.class);
        EasyMock.expect(mockComponent.getAComponent()).andReturn(mockAccessible);
        EasyMock.replay(mockComponent,mockAccessible);
        Accessible result = instance.getAccessible(mockComponent);
        assertEquals(mockAccessible, result);

    }

    public class JFCEventHandlerImpl extends JFCEventHandler {

        @Override
        protected void performImpl(GComponent arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected void performImpl(GComponent arg0, Object arg1) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

}
