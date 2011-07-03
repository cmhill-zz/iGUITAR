/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.event;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.JFCXComponent;
import java.util.ArrayList;
import java.util.List;
import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleEditableText;
import junit.framework.TestCase;
import org.easymock.classextension.EasyMock;

/**
 *jUnit test for JFCEditableTextHandler
 */
public class JFCEditableTextHandlerTest extends TestCase {
    
    public JFCEditableTextHandlerTest(String testName) {
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
     * Test of performImpl method, of class JFCEditableTextHandler.
     */
    public void testPerformImpl_GComponent() {
        System.out.println("performImpl");
        GComponent gComponent = null;
        JFCEditableTextHandler instance = new JFCEditableTextHandler();
        instance.performImpl(gComponent);
        
    }

    /**
     * Test of performImpl method, of class JFCEditableTextHandler.
     */
    public void testPerformImpl_GComponent_Object() {
        System.out.println("performImpl");
        JFCXComponent mockComponent;
        Accessible mockAccessible;
        AccessibleContext mockAContext;
        AccessibleEditableText mockAEText;
        List<String> parameters;
        JFCEditableTextHandler instance = new JFCEditableTextHandler();

        /* Test 1 - gComponent = null */
        instance.performImpl(null, null);

        /* Test 2 - parameters not instanceof List */
        mockComponent = EasyMock.createMock(JFCXComponent.class);
        instance.performImpl(mockComponent,null);
        EasyMock.replay(mockComponent);
        
        /* Test 3 - lParameter = null and aTextEvent = null */
        mockComponent = EasyMock.createMock(JFCXComponent.class);
        mockAccessible = EasyMock.createMock(Accessible.class);
        mockAContext = EasyMock.createMock(AccessibleContext.class);
        EasyMock.expect(((JFCXComponent)mockComponent).getAComponent()).andReturn(mockAccessible);
        EasyMock.expect(mockAccessible.getAccessibleContext()).andReturn(mockAContext);
        EasyMock.expect(mockAContext.getAccessibleEditableText()).andReturn(null);
        EasyMock.replay(mockComponent,mockAccessible,mockAContext);
        parameters = new ArrayList();
        instance.performImpl(mockComponent,parameters);

        /* Test 4 - lParameter != null and aTextEvent != null */
        mockComponent = EasyMock.createMock(JFCXComponent.class);
        mockAccessible = EasyMock.createMock(Accessible.class);
        mockAContext = EasyMock.createMock(AccessibleContext.class);
        mockAEText = EasyMock.createMock(AccessibleEditableText.class);
        EasyMock.expect(((JFCXComponent)mockComponent).getAComponent()).andReturn(mockAccessible);
        EasyMock.expect(mockAccessible.getAccessibleContext()).andReturn(mockAContext);
        EasyMock.expect(mockAContext.getAccessibleEditableText()).andReturn(mockAEText);
        mockAEText.setTextContents((String) EasyMock.anyObject());
        EasyMock.replay(mockComponent,mockAccessible,mockAContext,mockAEText);
        parameters = new ArrayList();
        instance.performImpl(mockComponent,parameters);

        /* Test 5 - lParameter size > 0 */
        mockComponent = EasyMock.createMock(JFCXComponent.class);
        mockAccessible = EasyMock.createMock(Accessible.class);
        mockAContext = EasyMock.createMock(AccessibleContext.class);
        mockAEText = EasyMock.createMock(AccessibleEditableText.class);
        EasyMock.expect(((JFCXComponent)mockComponent).getAComponent()).andReturn(mockAccessible);
        EasyMock.expect(mockAccessible.getAccessibleContext()).andReturn(mockAContext);
        EasyMock.expect(mockAContext.getAccessibleEditableText()).andReturn(mockAEText);
        mockAEText.setTextContents((String) EasyMock.anyObject());
        EasyMock.replay(mockComponent,mockAccessible,mockAContext,mockAEText);
        parameters = new ArrayList();
        parameters.add("test");
        instance.performImpl(mockComponent,parameters);
        

        
    }

}
