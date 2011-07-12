/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.event;

import edu.umd.cs.guitar.model.JFCXComponent;
import java.awt.Button;
import java.awt.Component;
import java.lang.reflect.Method;
import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.swing.JTabbedPane;
import junit.framework.TestCase;
import org.easymock.classextension.EasyMock;

/**
 *jUnit test for JFCSelectFromParent
 */
public class JFCSelectFromParentTest extends TestCase {
    
    public JFCSelectFromParentTest(String testName) {
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
     * Test of performImpl method, of class JFCSelectFromParent.
     */
    public void testPerformImpl_GComponent(){
        System.out.println("performImpl");
        JFCXComponent mockJFCXComponent;
        Component mockAccessibleChild;
        Component rmockAccessibleChild;
        Accessible mockAccessibleParent;
        AccessibleContext mockAContext;
        AccessibleContext rmockAContext;
        Method mockMethod;
        JFCSelectFromParent instance = new JFCSelectFromParent();

        /* test 1 - aParent == null and aCompmonent=null in getSelectableParent */
        mockJFCXComponent = EasyMock.createMock(JFCXComponent.class);
        EasyMock.expect(mockJFCXComponent.getAComponent()).andReturn(null);
        EasyMock.replay(mockJFCXComponent);
        instance.performImpl(mockJFCXComponent);

        /* test 2 - aParent == null and aContext=null in getSelectableParent */
        mockJFCXComponent = EasyMock.createMock(JFCXComponent.class);
        mockAccessibleChild = EasyMock.createMock(Button.class);
        EasyMock.expect(mockJFCXComponent.getAComponent()).andReturn((Accessible) mockAccessibleChild);
        EasyMock.expect(mockAccessibleChild.getAccessibleContext()).andReturn(null);
        EasyMock.replay(mockJFCXComponent,mockAccessibleChild);
        instance.performImpl(mockJFCXComponent);

        /* test 3 - aParent == null and aParent=null in getSelectableParent */
        mockJFCXComponent = EasyMock.createMock(JFCXComponent.class);
        mockAccessibleChild = EasyMock.createMock(Button.class);
        mockAContext = EasyMock.createMock(AccessibleContext.class);
        EasyMock.expect(mockJFCXComponent.getAComponent()).andReturn((Accessible) mockAccessibleChild);
        EasyMock.expect(mockAccessibleChild.getAccessibleContext()).andReturn(mockAContext);
        EasyMock.expect(mockAContext.getAccessibleParent()).andReturn(null);
        EasyMock.replay(mockJFCXComponent,mockAccessibleChild,mockAContext);
        instance.performImpl(mockJFCXComponent);

        /* test 4 - aParent == null and method.getName = setSelectedComponent */
        mockJFCXComponent = EasyMock.createMock(JFCXComponent.class);
        mockAccessibleChild = EasyMock.createMock(Button.class);
        mockAContext = EasyMock.createMock(AccessibleContext.class);
        mockAccessibleParent = EasyMock.createMock(JTabbedPane.class);

        EasyMock.expect(mockJFCXComponent.getAComponent()).andReturn((Accessible) mockAccessibleChild);
        EasyMock.expect(mockAccessibleChild.getAccessibleContext()).andReturn(mockAContext);
        EasyMock.expect(mockAContext.getAccessibleParent()).andReturn(mockAccessibleParent);
        EasyMock.replay(mockJFCXComponent,mockAccessibleChild,mockAContext,mockAccessibleParent);
        instance.performImpl(mockJFCXComponent);

        /* test 5 - aParent == null and method.getName! = setSelectedComponent */
        mockJFCXComponent = EasyMock.createMock(JFCXComponent.class);

        rmockAccessibleChild = EasyMock.createMock(Button.class);
        rmockAContext = EasyMock.createMock(AccessibleContext.class);

        mockAccessibleChild = EasyMock.createMock(Button.class);
        mockAContext = EasyMock.createMock(AccessibleContext.class);
        mockAccessibleParent = EasyMock.createMock(JTabbedPane.class);
        EasyMock.expect(mockJFCXComponent.getAComponent()).andReturn((Accessible) rmockAccessibleChild);
        EasyMock.expect(rmockAccessibleChild.getAccessibleContext()).andReturn(rmockAContext);
        EasyMock.expect(rmockAContext.getAccessibleParent()).andReturn((Accessible) mockAccessibleChild);
        EasyMock.expect(mockAccessibleChild.getAccessibleContext()).andReturn(mockAContext);
        EasyMock.expect(mockAContext.getAccessibleParent()).andReturn(mockAccessibleParent);
        EasyMock.replay(mockJFCXComponent,mockAccessibleChild,mockAContext,mockAccessibleParent,rmockAccessibleChild, rmockAContext);
        instance.performImpl(mockJFCXComponent);



    }

    /**
     * Test of performImpl method, of class JFCSelectFromParent.
     */
    public void testPerformImpl_GComponent_Object() {
        System.out.println("performImpl");
        JFCXComponent mockgComponent;
        Object parameters = null;
        mockgComponent = EasyMock.createMock(JFCXComponent.class);
        EasyMock.expect(mockgComponent.getAComponent()).andReturn(null);
        JFCSelectFromParent instance = new JFCSelectFromParent();
        instance.performImpl(mockgComponent, parameters);
        
    }

}
