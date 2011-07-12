/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.model;

import edu.umd.cs.guitar.event.JFCActionHandler;
import edu.umd.cs.guitar.event.JFCEditableTextHandler;
import edu.umd.cs.guitar.event.JFCSelectionHandler;
import edu.umd.cs.guitar.event.JFCValueHandler;
import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.wrapper.AttributesTypeWrapper;
import java.awt.Button;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.accessibility.Accessible;
import javax.accessibility.AccessibleAction;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleEditableText;
import javax.accessibility.AccessibleSelection;
import javax.accessibility.AccessibleValue;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import junit.framework.TestCase;
import org.easymock.classextension.EasyMock;

/**
 * jUnit test for JFCXComponent
 */
public class JFCXComponentTest extends TestCase {
    
    public JFCXComponentTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        AttributesType one = new AttributesType();
        one.setProperty(new ArrayList());
        one.getProperty().add(new PropertyType());
        one.getProperty().get(0).setName(JFCConstants.TITLE_TAG);
        one.getProperty().get(0).setValue(new ArrayList());

        AttributesType two = new AttributesType();
        two.setProperty(new ArrayList());
        two.getProperty().add(new PropertyType());
        two.getProperty().get(0).setName(JFCConstants.TITLE_TAG);
        two.getProperty().get(0).setValue(new ArrayList());
        two.getProperty().get(0).getValue().add("Not this one");

        AttributesType three = new AttributesType();
        three.setProperty(new ArrayList());
        three.getProperty().add(new PropertyType());
        three.getProperty().get(0).setName(JFCConstants.TITLE_TAG);
        three.getProperty().get(0).setValue(new ArrayList());
        three.getProperty().get(0).getValue().add("This one");

        JFCConstants.sTerminalWidgetSignature.add(new AttributesTypeWrapper(one));
        JFCConstants.sTerminalWidgetSignature.add(new AttributesTypeWrapper(two));
        JFCConstants.sTerminalWidgetSignature.add(new AttributesTypeWrapper(three));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getAComponent method, of class JFCXComponent.
     */
    public void testGetAComponent() {
        System.out.println("getAComponent");
        JFCXComponent instance = new JFCXComponent(null);
        Accessible expResult = EasyMock.createMock(Accessible.class);
        instance.aComponent = expResult;
        Accessible result = instance.getAComponent();
        assertEquals(expResult, result);
    }

    /**
     * Test of getGUIProperties method, of class JFCXComponent.
     */
    public void testGetGUIProperties() {
        System.out.println("getGUIProperties");
        JFCXComponent instance = null;
        List<PropertyType> expResult = null;
        List<PropertyType> result = null ;

        String expName = "Test Name";
        String expIcon = "Test Icon";

        /* Tets 1 - name is "", aComponent is a mock Acessible */
        Accessible mockAccessible = EasyMock.createMock(Accessible.class);
        AccessibleContext mockContext = EasyMock.createMock(AccessibleContext.class);

        EasyMock.expect(mockAccessible.getAccessibleContext()).andReturn(mockContext);
        EasyMock.expect(mockContext.getAccessibleName()).andReturn(null);

        EasyMock.replay(mockAccessible,mockContext);

        instance = new JFCXComponent(null);
        instance.aComponent = mockAccessible;
        result = instance.getGUIProperties();
        if ( result.size() > 0 )
        {
            assert( !result.get(0).getName().equals(JFCConstants.TITLE_TAG));
            assert( !result.get(0).getName().equals(JFCConstants.ICON_TAG));
        }

        /* Tets 2 - name is "Test name", iconname is "Test icon" aComponent is a mock JFileChooser */
        JFileChooser mockFC = EasyMock.createMock(JFileChooser.class);
        mockContext = EasyMock.createMock(AccessibleContext.class);
        Icon mockIcon = EasyMock.createMock(Icon.class);

        EasyMock.expect(mockFC.getAccessibleContext()).andReturn(mockContext);
        EasyMock.expect(mockContext.getAccessibleName()).andReturn(expName);
        EasyMock.expect(mockFC.getIcon((File) EasyMock.anyObject())).andReturn(mockIcon);
        EasyMock.replay(mockFC,mockContext,mockIcon);

        instance = new JFCXComponent(null);
        instance.aComponent = mockFC;
        result = instance.getGUIProperties();
        assert(result.size()>1);
        assert(result.get(0).getName().equals(JFCConstants.TITLE_TAG));
        assert(result.get(1).getName().equals(JFCConstants.ICON_TAG));

        /* Test 3 - aComponent is a button */
        instance = new JFCXComponent(null);
        instance.aComponent = new JButton();
        result = instance.getGUIProperties();
      
    }

    /**
     * Test of getChildren method, of class JFCXComponent.
     */
    public void testGetChildren() {
        System.out.println("getChildren");
        JFCXComponent instance = null;
        List<GComponent> expResult = null;
        List<GComponent> result = null;
        Accessible mockComponent;
        AccessibleContext mockContext;

        /* Test 1 - context is null */
        expResult = new ArrayList<GComponent>();
        mockComponent = EasyMock.createMock(Accessible.class);
        mockContext = null;
        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext);
        EasyMock.replay(mockComponent);
        instance = new JFCXComponent(mockComponent);
        result = instance.getChildren();
        assertEquals(expResult, result);

        /* Test 2 - context has one child */
        expResult = new ArrayList<GComponent>();

        mockComponent = EasyMock.createMock(Accessible.class);
        mockContext = EasyMock.createMock(AccessibleContext.class);

        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext);
        EasyMock.expect(mockContext.getAccessibleChildrenCount()).andReturn(1);
        EasyMock.expect(mockContext.getAccessibleChild(0)).andReturn(null);
        EasyMock.replay(mockComponent,mockContext);
        instance = new JFCXComponent(mockComponent);
        result = instance.getChildren();
        assertEquals(result.size(),1);
        

    }

    /**
     * Test of getParent method, of class JFCXComponent.
     */
    public void testGetParent() {
        System.out.println("getParent");
        JFCXComponent instance = null;
        GComponent expResult = null;
        GComponent result = null;
        Accessible mockComponent;
        AccessibleContext mockContext;
        Accessible mockParent;

        /* Test 1 - context is null */
        expResult = null;
        mockComponent = EasyMock.createMock(Accessible.class);
        mockContext = null;
        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext);
        EasyMock.replay(mockComponent);
        instance = new JFCXComponent(mockComponent);
        result = instance.getParent();
        assertEquals(expResult, result);
        
                /* Test 2 - parent is null */
        expResult = null;
        mockComponent = EasyMock.createMock(Accessible.class);
        mockContext = EasyMock.createMock(AccessibleContext.class);
        mockParent = null;
        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext);
        EasyMock.expect(mockContext.getAccessibleParent()).andReturn(mockParent);
        EasyMock.replay(mockComponent, mockContext);
        instance = new JFCXComponent(mockComponent);
        result = instance.getParent();
        assertEquals(expResult, result);

                /* Test 3 - parent is non-null */
        mockComponent = EasyMock.createMock(Accessible.class);
        mockContext = EasyMock.createMock(AccessibleContext.class);
        mockParent = EasyMock.createMock(Accessible.class);
        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext);
        EasyMock.expect(mockContext.getAccessibleParent()).andReturn(mockParent);
        EasyMock.replay(mockComponent, mockContext, mockParent);
        instance = new JFCXComponent(mockComponent);
        result = instance.getParent();
        assertEquals(mockParent, ((JFCXComponent)result).aComponent);


    }

    /**
     * Test of hasChildren method, of class JFCXComponent.
     */
    public void testHasChildren() {
        System.out.println("hasChildren");
        JFCXComponent instance = null;
        boolean expResult = false;
        boolean result = false;
        Accessible mockComponent;
        AccessibleContext mockContext;

        /* Test 1 - context is null */
        expResult = false;
        mockComponent = EasyMock.createMock(Accessible.class);
        mockContext = null;
        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext);
        EasyMock.replay(mockComponent);
        instance = new JFCXComponent(mockComponent);
        result = instance.hasChildren();
        assertEquals(expResult, result);

        /* Test 2 - context has no child */
        expResult = false;

        mockComponent = EasyMock.createMock(Accessible.class);
        mockContext = EasyMock.createMock(AccessibleContext.class);

        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext);
        EasyMock.expect(mockContext.getAccessibleChildrenCount()).andReturn(0);
        EasyMock.replay(mockComponent,mockContext);
        instance = new JFCXComponent(mockComponent);
        result = instance.hasChildren();
        assertEquals(expResult, result);

        /* Test 3 - context has one child */
        expResult = true;

        mockComponent = EasyMock.createMock(Accessible.class);
        mockContext = EasyMock.createMock(AccessibleContext.class);

        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext);
        EasyMock.expect(mockContext.getAccessibleChildrenCount()).andReturn(1);
        EasyMock.replay(mockComponent,mockContext);
        instance = new JFCXComponent(mockComponent);
        result = instance.hasChildren();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class JFCXComponent.
     */
    public void testGetName() {
        System.out.println("getName");
        JFCXComponent instance = null;
        String expResult = "";
        String result = "";
        Accessible mockComponent;
        AccessibleContext mockContext;
        String sName = "";

        /* Test 1 - component is null */
        expResult = "";
        mockComponent = null;
        instance = new JFCXComponent(mockComponent);
        result = instance.getName();
        assertEquals(expResult, result);

        /* Test 2 - context is null */
        expResult = "";
        mockComponent = EasyMock.createMock(Accessible.class);
        mockContext = null;
        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext);
        EasyMock.replay(mockComponent);
        instance = new JFCXComponent(mockComponent);
        result = instance.getName();
        assertEquals(expResult, result);

        /* Test 3 - sName is not null */
        expResult = "";
        mockComponent = EasyMock.createMock(Accessible.class);
        mockContext = EasyMock.createMock(AccessibleContext.class);
        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext);
        EasyMock.expect(mockContext.getAccessibleName()).andReturn(sName);
        EasyMock.replay(mockComponent,mockContext);
        instance = new JFCXComponent(mockComponent);
        result = instance.getName();
        assertEquals(expResult, result);

        /* Test 4 - sName is null, icon should return null */
        expResult = null;
        mockComponent = EasyMock.createMock(Accessible.class);
        mockContext = EasyMock.createMock(AccessibleContext.class);
        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext);
        EasyMock.expect(mockContext.getAccessibleName()).andReturn(null);
        EasyMock.replay(mockComponent,mockContext);
        instance = new JFCXComponent(mockComponent);
        result = instance.getName();
        assertEquals(expResult, result);

        /* Test 5 - mockComponent is a button */
        expResult = "";
        mockComponent = EasyMock.createMock(Button.class);
        mockContext = EasyMock.createMock(AccessibleContext.class);
        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext);
        EasyMock.expect(mockContext.getAccessibleName()).andReturn(null);
        EasyMock.expect(((Button)mockComponent).getName()).andReturn(expResult);
        EasyMock.replay(mockComponent,mockContext);
        instance = new JFCXComponent(mockComponent);
        result = instance.getName();
        assertEquals(expResult, result);

                /* Test 6 - mockComponent is a button */
        expResult = "Pos(1,1)";
        mockComponent = EasyMock.createMock(Button.class);
        mockContext = EasyMock.createMock(AccessibleContext.class);
        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext);
        EasyMock.expect(mockContext.getAccessibleName()).andReturn(null);
        EasyMock.expect(((Button)mockComponent).getName()).andReturn(null);
        EasyMock.expect(((Button)mockComponent).getX()).andReturn(1);
        EasyMock.expect(((Button)mockComponent).getY()).andReturn(1);
        EasyMock.replay(mockComponent,mockContext);
        instance = new JFCXComponent(mockComponent);
        result = instance.getName();
        assertEquals(expResult, result);


    }

    /**
     * Test of getEventList method, of class JFCXComponent.
     */
    public void testGetEventList() {
        System.out.println("getEventList");
        JFCXComponent instance = null;
        List expResult = null;
        List result = null;
        Accessible mockComponent;
        AccessibleContext mockContext;


        /* Test 1 - context is null */
        expResult = new ArrayList();
        mockComponent = EasyMock.createMock(Accessible.class);
        mockContext = null;
        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext);
        EasyMock.replay(mockComponent);
        instance = new JFCXComponent(mockComponent);
        result = instance.getEventList();
        assertEquals(expResult, result);

        /* Test 2 - event is text, event is selection */
        expResult = new ArrayList();
        AccessibleEditableText mockEvent1 = EasyMock.createMock(AccessibleEditableText.class);
        AccessibleAction mockEvent2 = EasyMock.createMock(AccessibleAction.class);
        AccessibleSelection mockEvent3 = EasyMock.createMock(AccessibleSelection.class);
        AccessibleValue mockEvent4 = EasyMock.createMock(AccessibleValue.class);

        mockComponent = EasyMock.createMock(Accessible.class);
        mockContext = EasyMock.createMock(AccessibleContext.class);
        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext);
        EasyMock.expect(mockContext.getAccessibleEditableText()).andReturn(mockEvent1);
        EasyMock.expect(mockContext.getAccessibleAction()).andReturn(null);
        EasyMock.expect(mockContext.getAccessibleSelection()).andReturn(mockEvent3);
        EasyMock.expect(mockContext.getAccessibleValue()).andReturn(null);

        EasyMock.replay(mockComponent, mockContext);
        instance = new JFCXComponent(mockComponent);
        result = instance.getEventList();
        assert(result.size() == 2);
        assert(result.get(0) instanceof JFCEditableTextHandler);
        assert(result.get(1) instanceof JFCSelectionHandler);

        /* Test 3 - event is action, event is value */
        expResult = new ArrayList();

        mockComponent = EasyMock.createMock(Accessible.class);
        mockContext = EasyMock.createMock(AccessibleContext.class);
        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext);
        EasyMock.expect(mockContext.getAccessibleEditableText()).andReturn(null);
        EasyMock.expect(mockContext.getAccessibleAction()).andReturn(mockEvent2);
        EasyMock.expect(mockContext.getAccessibleSelection()).andReturn(null);
        EasyMock.expect(mockContext.getAccessibleValue()).andReturn(mockEvent4);

        EasyMock.replay(mockComponent, mockContext);
        instance = new JFCXComponent(mockComponent);
        result = instance.getEventList();
                assert(result.size() == 2);
        assert(result.get(0) instanceof JFCActionHandler);
        assert(result.get(1) instanceof JFCValueHandler);


    }

    /**
     * Test of getClassVal method, of class JFCXComponent.
     */
    public void testGetClassVal() {
        System.out.println("getClassVal");
        Accessible mockComponent;
        mockComponent = EasyMock.createMock(Accessible.class);
        JFCXComponent instance = new JFCXComponent(mockComponent);
        String expResult = mockComponent.getClass().getName();
        String result = instance.getClassVal();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTypeVal method, of class JFCXComponent.
     */
    public void testGetTypeVal() {
        System.out.println("getTypeVal");
        JFCXComponent instance = null;
        String expResult = "";
        String result = "";
        Accessible mockComponent;
        AccessibleContext mockContext;
        AccessibleAction mockAction;

        /* Test 1 - context is null */
        expResult = GUITARConstants.SYSTEM_INTERACTION;
        mockComponent = EasyMock.createMock(Accessible.class);
        mockContext = null;
        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext);
        EasyMock.replay(mockComponent);
        instance = new JFCXComponent(mockComponent);
        result = instance.getTypeVal();
        assertEquals(expResult, result);

        /* Test 2 - action is null */
        expResult = GUITARConstants.SYSTEM_INTERACTION;
        mockComponent = EasyMock.createMock(Accessible.class);
        mockContext = EasyMock.createMock(AccessibleContext.class);
        mockAction = null;
        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext);
        EasyMock.expect(mockContext.getAccessibleAction()).andReturn(mockAction);
        EasyMock.replay(mockComponent,mockContext);
        instance = new JFCXComponent(mockComponent);
        result = instance.getTypeVal();
        assertEquals(expResult, result);

        /* Test 3 - name is null */
        expResult = GUITARConstants.SYSTEM_INTERACTION;
        mockComponent = EasyMock.createMock(Accessible.class);
        mockContext = EasyMock.createMock(AccessibleContext.class);
        mockAction = EasyMock.createMock(AccessibleAction.class);
        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext).atLeastOnce();
        EasyMock.expect(mockContext.getAccessibleAction()).andReturn(mockAction);
        EasyMock.expect(mockContext.getAccessibleName()).andReturn(null);
        EasyMock.replay(mockComponent,mockContext, mockAction);
        instance = new JFCXComponent(mockComponent);
        result = instance.getTypeVal();
        assertEquals(expResult, result);

        /* Test 4 - name is in titleVals */
        if ( JFCConstants.sTerminalWidgetSignature.size() > 0)
        {
            int size = JFCConstants.sTerminalWidgetSignature.size();
            expResult = GUITARConstants.TERMINAL;
            mockComponent = EasyMock.createMock(Accessible.class);
            mockContext = EasyMock.createMock(AccessibleContext.class);
            mockAction = EasyMock.createMock(AccessibleAction.class);
            EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext).atLeastOnce();
            EasyMock.expect(mockContext.getAccessibleAction()).andReturn(mockAction);
            EasyMock.expect(mockContext.getAccessibleName()).andReturn(JFCConstants.sTerminalWidgetSignature.get(size-1).getFirstValByName(JFCConstants.TITLE_TAG));
            EasyMock.replay(mockComponent,mockContext, mockAction);
            instance = new JFCXComponent(mockComponent);
              result = instance.getTypeVal();
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of isTerminal method, of class JFCXComponent.
     */
    public void testIsTerminal() {
        System.out.println("isTerminal");
        JFCXComponent instance = null;
        boolean expResult = false;
        boolean result = false;
        Accessible mockComponent;
        AccessibleContext mockContext;
        AccessibleAction mockAction;

        /* Test 1 - context is null */
        expResult = false;
        mockComponent = EasyMock.createMock(Accessible.class);
        mockContext = null;
        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext);
        EasyMock.replay(mockComponent);
        instance = new JFCXComponent(mockComponent);
        result = instance.isTerminal();
        assertEquals(expResult, result);

        /* Test 2 - action is null */
        expResult = false;
        mockComponent = EasyMock.createMock(Accessible.class);
        mockContext = EasyMock.createMock(AccessibleContext.class);
        mockAction = null;
        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext);
        EasyMock.expect(mockContext.getAccessibleAction()).andReturn(mockAction);
        EasyMock.replay(mockComponent,mockContext);
        instance = new JFCXComponent(mockComponent);
        result = instance.isTerminal();
        assertEquals(expResult, result);

        /* Test 3 - name is null */
        expResult = false;
        mockComponent = EasyMock.createMock(Accessible.class);
        mockContext = EasyMock.createMock(AccessibleContext.class);
        mockAction = EasyMock.createMock(AccessibleAction.class);
        EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext).atLeastOnce();
        EasyMock.expect(mockContext.getAccessibleAction()).andReturn(mockAction);
        EasyMock.expect(mockContext.getAccessibleName()).andReturn(null);
        EasyMock.replay(mockComponent,mockContext, mockAction);
        instance = new JFCXComponent(mockComponent);
        result = instance.isTerminal();
        assertEquals(expResult, result);

        /* Test 4 - name is in titleVals */
        if ( JFCConstants.sTerminalWidgetSignature.size() > 0)
        {
            int size = JFCConstants.sTerminalWidgetSignature.size();
            expResult = true;
            mockComponent = EasyMock.createMock(Accessible.class);
            mockContext = EasyMock.createMock(AccessibleContext.class);
            mockAction = EasyMock.createMock(AccessibleAction.class);
            EasyMock.expect(mockComponent.getAccessibleContext()).andReturn(mockContext).atLeastOnce();
            EasyMock.expect(mockContext.getAccessibleAction()).andReturn(mockAction);
            EasyMock.expect(mockContext.getAccessibleName()).andReturn(JFCConstants.sTerminalWidgetSignature.get(size-1).getFirstValByName(JFCConstants.TITLE_TAG));
            EasyMock.replay(mockComponent,mockContext, mockAction);
            instance = new JFCXComponent(mockComponent);
            result = instance.isTerminal();

            assertEquals(expResult, result);
        }


        
    }

}
