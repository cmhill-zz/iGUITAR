/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.event;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.JFCXComponent;
import javax.accessibility.Accessible;
import javax.accessibility.AccessibleAction;
import javax.accessibility.AccessibleContext;
import junit.framework.TestCase;
import org.easymock.classextension.EasyMock;

/**
 *jUnit test for JFCActionHandler
 */
public class JFCActionHandlerTest extends TestCase {
    
    public JFCActionHandlerTest(String testName) {
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
     * Test of performImpl method, of class JFCActionHandler.
     * The method under tests always succeeds.  It's a void function.
     * Exception cannot be thrown
     */
    public void testPerformImpl_GComponent() {
        System.out.println("performImpl");
        JFCActionHandler instance = new JFCActionHandler();
        JFCXComponent mockComponent;
        Accessible mockAccessible;
        AccessibleContext mockAContext;
        AccessibleAction mockAAction;


        /* Test 1 - parameter is null */
        instance.performImpl(null);

        /* Test 2 - accessible is null */
        mockComponent = EasyMock.createMock(JFCXComponent.class);
        EasyMock.expect(((JFCXComponent)mockComponent).getAComponent()).andReturn(null);
        EasyMock.replay(mockComponent);
        instance.performImpl(mockComponent);

        /* Test 3 - context is null */
        mockComponent = EasyMock.createMock(JFCXComponent.class);
        mockAccessible = EasyMock.createMock(Accessible.class);
        EasyMock.expect(((JFCXComponent)mockComponent).getAComponent()).andReturn(mockAccessible);
        EasyMock.expect(mockAccessible.getAccessibleContext()).andReturn(null);
        EasyMock.replay(mockComponent,mockAccessible);
        instance.performImpl(mockComponent);

        /* Test 4 - action is null */
        mockComponent = EasyMock.createMock(JFCXComponent.class);
        mockAccessible = EasyMock.createMock(Accessible.class);
        mockAContext = EasyMock.createMock(AccessibleContext.class);
        EasyMock.expect(((JFCXComponent)mockComponent).getAComponent()).andReturn(mockAccessible);
        EasyMock.expect(mockAccessible.getAccessibleContext()).andReturn(mockAContext);
        EasyMock.expect(mockAContext.getAccessibleAction()).andReturn(null);
        EasyMock.replay(mockComponent,mockAccessible,mockAContext);
        instance.performImpl(mockComponent);

        /* Test 5 - nActions > 0 */
        mockComponent = EasyMock.createMock(JFCXComponent.class);
        mockAccessible = EasyMock.createMock(Accessible.class);
        mockAContext = EasyMock.createMock(AccessibleContext.class);
        mockAAction = EasyMock.createMock(AccessibleAction.class);
        EasyMock.expect(((JFCXComponent)mockComponent).getAComponent()).andReturn(mockAccessible);
        EasyMock.expect(mockAccessible.getAccessibleContext()).andReturn(mockAContext);
        EasyMock.expect(mockAContext.getAccessibleAction()).andReturn(mockAAction);
        EasyMock.expect(mockAAction.getAccessibleActionCount()).andReturn(1);
        EasyMock.expect(mockAAction.doAccessibleAction(0)).andReturn(Boolean.TRUE);
        EasyMock.replay(mockComponent,mockAccessible,mockAContext,mockAAction);
        instance.performImpl(mockComponent);

        /* Test 6 - nActions <= 0 */
        mockComponent = EasyMock.createMock(JFCXComponent.class);
        mockAccessible = EasyMock.createMock(Accessible.class);
        mockAContext = EasyMock.createMock(AccessibleContext.class);
        mockAAction = EasyMock.createMock(AccessibleAction.class);
        EasyMock.expect(((JFCXComponent)mockComponent).getAComponent()).andReturn(mockAccessible);
        EasyMock.expect(mockAccessible.getAccessibleContext()).andReturn(mockAContext);
        EasyMock.expect(mockAContext.getAccessibleAction()).andReturn(mockAAction);
        EasyMock.expect(mockAAction.getAccessibleActionCount()).andReturn(0);
        EasyMock.replay(mockComponent,mockAccessible,mockAContext,mockAAction);
        instance.performImpl(mockComponent);

        

    }

    /**
     * Test of performImpl method, of class JFCActionHandler.
     */
    public void testPerformImpl_GComponent_Object() {
        System.out.println("performImpl");
        GComponent gComponent = null;
        Object parameters = null;
        JFCActionHandler instance = new JFCActionHandler();
        instance.performImpl(gComponent, parameters);
        
    }

}
