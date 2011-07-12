/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.model.wrapper;

import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.ContentsType;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.PropertyType;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.easymock.classextension.EasyMock;

/**
 *
 * @author user
 */
public class ComponentTypeWrapperTest extends TestCase {
    
    public ComponentTypeWrapperTest(String testName) {
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
     * Test of getInvokedWindows method, of class ComponentTypeWrapper.
     */
    public void testGetInvokedWindows() {
        System.out.println("getInvokedWindows");
        ComponentTypeWrapper instance = null;
        instance = new ComponentTypeWrapper(new ComponentType());
        List<GUITypeWrapper> expResult = new ArrayList();
        instance.invokedWindows = expResult;
        List<GUITypeWrapper> result = instance.getInvokedWindows();
        assertEquals(expResult,result);
    }

    /**
     * Test of parseData method, of class ComponentTypeWrapper.
     */
    public void testParseData() {
        System.out.println("parseData");
        GUIStructure mockdGUIStructure ;
        GUIStructureWrapper mockwGUIStructure ;
        ComponentType mockComponentType;
        ContainerType mockContainerType;
        AttributesType mockAttributesType;
        ContentsType mockContentType;
        GUITypeWrapper mockGTypeWrapper;
        PropertyType mockPropertyType;
        ComponentTypeWrapper instance = new ComponentTypeWrapper(new ComponentType());
        
        /* Test 1 - sInvokeList = null */
        mockComponentType = EasyMock.createMock(ComponentType.class);
        mockdGUIStructure = EasyMock.createMock(GUIStructure.class);
        mockwGUIStructure = EasyMock.createMock(GUIStructureWrapper.class);
        instance.dComponentType=mockComponentType;
        EasyMock.expect(mockComponentType.getAttributes()).andReturn(null);
        EasyMock.replay(mockdGUIStructure,mockwGUIStructure,mockComponentType);
        
        instance.parseData(mockdGUIStructure, mockwGUIStructure);

        /* Test 2 - invokedWin = null */
        String s1 = "Test1";
        mockComponentType = EasyMock.createMock(ComponentType.class);
        mockAttributesType = EasyMock.createMock(AttributesType.class);
        mockPropertyType = EasyMock.createMock(PropertyType.class);
        mockGTypeWrapper = EasyMock.createMock(GUITypeWrapper.class);
        mockdGUIStructure = EasyMock.createMock(GUIStructure.class);
        mockwGUIStructure = EasyMock.createMock(GUIStructureWrapper.class);
        instance.dComponentType=mockComponentType;
        EasyMock.expect(mockComponentType.getAttributes()).andReturn(mockAttributesType);
        List<PropertyType> arraylist = new ArrayList();
        arraylist.add(mockPropertyType);
        EasyMock.expect(mockAttributesType.getProperty()).andReturn(arraylist);
        EasyMock.expect(mockPropertyType.getName()).andReturn(GUITARConstants.INVOKELIST_TAG_NAME);
        List<String> retlist = new ArrayList();
        retlist.add(s1);
        EasyMock.expect(mockPropertyType.getValue()).andReturn(retlist);


        EasyMock.expect(mockwGUIStructure.getGUIByTitle(s1)).andReturn(null);
        EasyMock.replay(mockdGUIStructure,mockwGUIStructure,mockComponentType,mockAttributesType, mockPropertyType,mockGTypeWrapper);


        instance.parseData(mockdGUIStructure, mockwGUIStructure);


        /* Test 3 - invokedWin != null and !lGUIs.contains(invokedWin) */

        mockComponentType = EasyMock.createMock(ComponentType.class);
        mockAttributesType = EasyMock.createMock(AttributesType.class);
        mockPropertyType = EasyMock.createMock(PropertyType.class);
        mockGTypeWrapper = EasyMock.createMock(GUITypeWrapper.class);
        mockdGUIStructure = EasyMock.createMock(GUIStructure.class);
        mockwGUIStructure = EasyMock.createMock(GUIStructureWrapper.class);
        instance.dComponentType=mockComponentType;
        EasyMock.expect(mockComponentType.getAttributes()).andReturn(mockAttributesType);
        arraylist = new ArrayList();
        arraylist.add(mockPropertyType);
        EasyMock.expect(mockAttributesType.getProperty()).andReturn(arraylist);
        EasyMock.expect(mockPropertyType.getName()).andReturn(GUITARConstants.INVOKELIST_TAG_NAME);
        retlist = new ArrayList();
        retlist.add(s1);
        EasyMock.expect(mockPropertyType.getValue()).andReturn(retlist);


        EasyMock.expect(mockwGUIStructure.getGUIByTitle(s1)).andReturn(mockGTypeWrapper);
        mockGTypeWrapper.setInvoker(instance);
        List<GUITypeWrapper> lguis = new ArrayList();
        EasyMock.expect(mockwGUIStructure.getGUIs()).andReturn(lguis);
        EasyMock.expect(mockwGUIStructure.getGUIs()).andReturn(lguis);
        mockGTypeWrapper.parseData(mockdGUIStructure, mockwGUIStructure);
        EasyMock.replay(mockdGUIStructure,mockwGUIStructure,mockComponentType,mockAttributesType, mockPropertyType,mockGTypeWrapper);


        instance.parseData(mockdGUIStructure, mockwGUIStructure);

        /* Test 4 - invokedWin != null and lGUIs.contains(invokedWin) */

        mockComponentType = EasyMock.createMock(ComponentType.class);
        mockAttributesType = EasyMock.createMock(AttributesType.class);
        mockPropertyType = EasyMock.createMock(PropertyType.class);
        mockGTypeWrapper = EasyMock.createMock(GUITypeWrapper.class);
        mockdGUIStructure = EasyMock.createMock(GUIStructure.class);
        mockwGUIStructure = EasyMock.createMock(GUIStructureWrapper.class);
        instance.dComponentType=mockComponentType;
        EasyMock.expect(mockComponentType.getAttributes()).andReturn(mockAttributesType);
        arraylist = new ArrayList();
        arraylist.add(mockPropertyType);
        EasyMock.expect(mockAttributesType.getProperty()).andReturn(arraylist);
        EasyMock.expect(mockPropertyType.getName()).andReturn(GUITARConstants.INVOKELIST_TAG_NAME);
        retlist = new ArrayList();
        retlist.add(s1);
        EasyMock.expect(mockPropertyType.getValue()).andReturn(retlist);


        EasyMock.expect(mockwGUIStructure.getGUIByTitle(s1)).andReturn(mockGTypeWrapper);
        mockGTypeWrapper.setInvoker(instance);
        lguis = new ArrayList();
        lguis.add(mockGTypeWrapper);
        EasyMock.expect(mockwGUIStructure.getGUIs()).andReturn(lguis);
        mockGTypeWrapper.parseData(mockdGUIStructure, mockwGUIStructure);
        EasyMock.replay(mockdGUIStructure,mockwGUIStructure,mockComponentType,mockAttributesType, mockPropertyType,mockGTypeWrapper);


        instance.parseData(mockdGUIStructure, mockwGUIStructure);

        /* Test 5 - dComponentType instance of ContainerType and dChildrenList = null */

        mockContainerType = EasyMock.createMock(ContainerType.class);
        mockComponentType = EasyMock.createMock(ComponentType.class);
        mockContentType = EasyMock.createMock(ContentsType.class);
        mockAttributesType = EasyMock.createMock(AttributesType.class);
        mockPropertyType = EasyMock.createMock(PropertyType.class);
        mockGTypeWrapper = EasyMock.createMock(GUITypeWrapper.class);
        mockdGUIStructure = EasyMock.createMock(GUIStructure.class);
        mockwGUIStructure = EasyMock.createMock(GUIStructureWrapper.class);
        instance.dComponentType=mockContainerType;
        EasyMock.expect(mockContainerType.getAttributes()).andReturn(mockAttributesType);
        arraylist = new ArrayList();
        arraylist.add(mockPropertyType);
        EasyMock.expect(mockAttributesType.getProperty()).andReturn(arraylist);
        EasyMock.expect(mockPropertyType.getName()).andReturn(GUITARConstants.INVOKELIST_TAG_NAME);
        retlist = new ArrayList();
        retlist.add(s1);
        EasyMock.expect(mockPropertyType.getValue()).andReturn(retlist);


        EasyMock.expect(mockwGUIStructure.getGUIByTitle(s1)).andReturn(mockGTypeWrapper);
        mockGTypeWrapper.setInvoker(instance);
        lguis = new ArrayList();
        lguis.add(mockGTypeWrapper);
        EasyMock.expect(mockwGUIStructure.getGUIs()).andReturn(lguis);
        mockGTypeWrapper.parseData(mockdGUIStructure, mockwGUIStructure);

        EasyMock.expect(mockContainerType.getContents()).andReturn(mockContentType);
        EasyMock.expect(mockContentType.getWidgetOrContainer()).andReturn(null);
        EasyMock.replay(mockdGUIStructure,mockwGUIStructure,mockContentType,mockAttributesType, mockPropertyType,mockGTypeWrapper,mockContainerType);


        instance.parseData(mockdGUIStructure, mockwGUIStructure);

        /* Test 6 - dComponentType instance of ContainerType and dChildrenList != null
         Unable to cover with mocks.
         

        mockContainerType = EasyMock.createMock(ContainerType.class);
        mockComponentType = EasyMock.createMock(ComponentType.class);
        mockContentType = EasyMock.createMock(ContentsType.class);
        mockAttributesType = EasyMock.createMock(AttributesType.class);
        mockPropertyType = EasyMock.createMock(PropertyType.class);
        mockGTypeWrapper = EasyMock.createMock(GUITypeWrapper.class);
        mockdGUIStructure = EasyMock.createMock(GUIStructure.class);
        mockwGUIStructure = EasyMock.createMock(GUIStructureWrapper.class);
        instance.dComponentType=mockContainerType;
        EasyMock.expect(mockContainerType.getAttributes()).andReturn(mockAttributesType).anyTimes();
        arraylist = new ArrayList();
        arraylist.add(mockPropertyType);
        EasyMock.expect(mockAttributesType.getProperty()).andReturn(arraylist).atLeastOnce();
        EasyMock.expect(mockPropertyType.getName()).andReturn(GUITARConstants.INVOKELIST_TAG_NAME).atLeastOnce();
        retlist = new ArrayList();
        retlist.add(s1);
        EasyMock.expect(mockPropertyType.getValue()).andReturn(retlist).atLeastOnce();


        EasyMock.expect(mockwGUIStructure.getGUIByTitle(s1)).andReturn(mockGTypeWrapper).atLeastOnce();
        mockGTypeWrapper.setInvoker(instance);
        EasyMock.expectLastCall().atLeastOnce();
        lguis = new ArrayList();
        lguis.add(mockGTypeWrapper);

        EasyMock.expect(mockwGUIStructure.getGUIs()).andReturn(lguis).atLeastOnce();
        mockGTypeWrapper.parseData(mockdGUIStructure, mockwGUIStructure);
        EasyMock.expectLastCall().atLeastOnce();

        EasyMock.expect(mockContainerType.getContents()).andReturn(mockContentType).atLeastOnce();
        List<ComponentType> dchildren = new ArrayList();
        dchildren.add(mockComponentType);
        EasyMock.expect(mockContentType.getWidgetOrContainer()).andReturn(dchildren).atLeastOnce();
        EasyMock.replay(mockdGUIStructure,mockComponentType,mockwGUIStructure,mockContentType,mockAttributesType, mockPropertyType,mockGTypeWrapper,mockContainerType);


        instance.parseData(mockdGUIStructure, mockwGUIStructure);
         */
    }

    /**
     * Test of getParent method, of class ComponentTypeWrapper.
     */
    public void testGetParent() {
        System.out.println("getParent");
        ComponentTypeWrapper instance = new ComponentTypeWrapper(new ComponentType());
        ComponentTypeWrapper expResult =new ComponentTypeWrapper(new ComponentType());
        instance.parent = expResult;
        ComponentTypeWrapper result = instance.getParent();
        assertEquals(expResult, result);
    }

    /**
     * Test of setParent method, of class ComponentTypeWrapper.
     */
    public void testSetParent() {
        System.out.println("setParent");
        ComponentTypeWrapper instance = new ComponentTypeWrapper(new ComponentType());
        ComponentTypeWrapper expResult =new ComponentTypeWrapper(new ComponentType());
        instance.setParent(expResult);
        ComponentTypeWrapper result = instance.parent;
        assertEquals(expResult, result);
    }

    /**
     * Test of getInvokedWindowList method, of class ComponentTypeWrapper.
     */
    public void testGetInvokedWindowList() {
        System.out.println("getInvokedWindowList");
        ComponentTypeWrapper instance = null;
        instance = new ComponentTypeWrapper(new ComponentType());
        List<GUITypeWrapper> expResult = new ArrayList();
        instance.invokedWindows = expResult;
        List<GUITypeWrapper> result = instance.getInvokedWindowList();
        assertEquals(expResult,result);
    }

    /**
     * Test of getChildren method, of class ComponentTypeWrapper.
     */
    public void testGetChildren() {
        System.out.println("getChildren");
        ComponentTypeWrapper instance = null;
        instance = new ComponentTypeWrapper(new ComponentType());
        List<ComponentTypeWrapper> expResult = new ArrayList();
        instance.children = expResult;
        List<ComponentTypeWrapper> result = instance.getChildren();
        assertEquals(expResult,result);
    }

    /**
     * Test of setWindow method, of class ComponentTypeWrapper.
     */
    public void testSetWindow() {
        System.out.println("setWindow");
        ComponentTypeWrapper instance = null;
        instance = new ComponentTypeWrapper(new ComponentType());
        GUITypeWrapper expResult = EasyMock.createMock(GUITypeWrapper.class);
        EasyMock.replay(expResult);
        instance.setWindow(expResult);
        GUITypeWrapper result = instance.window;
        assertEquals(expResult,result);
    }

    /**
     * Test of getWindow method, of class ComponentTypeWrapper.
     */
    public void testGetWindow() {
        System.out.println("getWindow");
        ComponentTypeWrapper instance = null;
        instance = new ComponentTypeWrapper(new ComponentType());
        GUITypeWrapper expResult = EasyMock.createMock(GUITypeWrapper.class);
        EasyMock.replay(expResult);
        instance.window = expResult;
        GUITypeWrapper  result = instance.getWindow();
        assertEquals(expResult,result);
    }

    /**
     * Test of getDComponentType method, of class ComponentTypeWrapper.
     */
    public void testGetDComponentType() {
        System.out.println("getDComponentType");
        ComponentTypeWrapper instance = null;
        instance = new ComponentTypeWrapper(new ComponentType());
        ComponentType expResult = new ComponentType();
        instance.dComponentType = expResult;
        ComponentType  result = instance.getDComponentType();
        assertEquals(expResult,result);
    }

    /**
     * Test of getFirstPropertyByName method, of class ComponentTypeWrapper.
     */
    public void testGetFirstPropertyByName() {
        System.out.println("getFirstPropertyByName");
        String sName = "test";
        ComponentType mockComponentType;
        AttributesType mockAttributesType;
        PropertyType mockPropertyType;
        ComponentTypeWrapper instance = new ComponentTypeWrapper(new ComponentType());


        /* Test 1 - attributes = null */
        mockComponentType = EasyMock.createMock(ComponentType.class);
        EasyMock.expect(mockComponentType.getAttributes()).andReturn(null);
        EasyMock.replay(mockComponentType);
        PropertyType expResult = null;
        PropertyType result = instance.getFirstPropertyByName(sName);
        assertEquals(expResult, result);

        /* Test 2 - attributes != null and p.getName = sName */
        sName = "test";
        mockComponentType = EasyMock.createMock(ComponentType.class);
        mockAttributesType = EasyMock.createMock(AttributesType.class);
        mockPropertyType = EasyMock.createMock(PropertyType.class);

        List<PropertyType> arrayList = new ArrayList();
        arrayList.add(mockPropertyType);
        instance.dComponentType = mockComponentType;
        EasyMock.expect(mockComponentType.getAttributes()).andReturn(mockAttributesType);
        EasyMock.expect(mockAttributesType.getProperty()).andReturn(arrayList);
        EasyMock.expect(mockPropertyType.getName()).andReturn(sName);
        EasyMock.replay(mockComponentType,mockAttributesType,mockPropertyType);
        expResult = mockPropertyType;
        
        result = instance.getFirstPropertyByName(sName);
        assertEquals(expResult, result);

        /* Test 3 - attributes != null and p.getName != sName */
        sName = "test";
        mockComponentType = EasyMock.createMock(ComponentType.class);
        mockAttributesType = EasyMock.createMock(AttributesType.class);
        mockPropertyType = EasyMock.createMock(PropertyType.class);

        arrayList = new ArrayList();
        arrayList.add(mockPropertyType);
        instance.dComponentType = mockComponentType;
        EasyMock.expect(mockComponentType.getAttributes()).andReturn(mockAttributesType);
        EasyMock.expect(mockAttributesType.getProperty()).andReturn(arrayList);
        EasyMock.expect(mockPropertyType.getName()).andReturn("wrong");
        EasyMock.replay(mockComponentType,mockAttributesType,mockPropertyType);
        expResult = null;

        result = instance.getFirstPropertyByName(sName);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getFirstValueByName method, of class ComponentTypeWrapper.
     */
    public void testGetFirstValueByName() {
        System.out.println("getFirstValueByName");
        String sName = "";
        PropertyType mockPropertyType;
        ComponentType mockComponentType;
        AttributesType mockAttributesType;
        ComponentTypeWrapper instance = new ComponentTypeWrapper(new ComponentType());

        /* Test 1 - property = null */
        mockComponentType = EasyMock.createMock(ComponentType.class);
        instance.dComponentType = mockComponentType;
        EasyMock.expect(mockComponentType.getAttributes()).andReturn(null);
        EasyMock.replay(mockComponentType);
        String expResult = null;
        String result = instance.getFirstValueByName(sName);
        assertEquals(expResult, result);

        /* Test 2 - valid property and size */
        mockComponentType = EasyMock.createMock(ComponentType.class);
        mockAttributesType = EasyMock.createMock(AttributesType.class);
        mockPropertyType = EasyMock.createMock(PropertyType.class);


        List<PropertyType> arrayList = new ArrayList();
        arrayList.add(mockPropertyType);
        instance.dComponentType = mockComponentType;
        EasyMock.expect(mockComponentType.getAttributes()).andReturn(mockAttributesType);
        EasyMock.expect(mockAttributesType.getProperty()).andReturn(arrayList);
        EasyMock.expect(mockPropertyType.getName()).andReturn("");
        List<String> stringarray = new ArrayList();
        stringarray.add("Test");
        EasyMock.expect(mockPropertyType.getValue()).andReturn(stringarray);
        EasyMock.expect(mockPropertyType.getValue()).andReturn(stringarray);
        EasyMock.replay(mockComponentType, mockAttributesType,mockPropertyType);
        expResult = "Test" ;
        result = instance.getFirstValueByName(sName);
        assertEquals(expResult, result);

        /* Test 3 - valid property but not valid size */
        mockComponentType = EasyMock.createMock(ComponentType.class);
        mockAttributesType = EasyMock.createMock(AttributesType.class);
        mockPropertyType = EasyMock.createMock(PropertyType.class);


        arrayList = new ArrayList();
        arrayList.add(mockPropertyType);
        instance.dComponentType = mockComponentType;
        EasyMock.expect(mockComponentType.getAttributes()).andReturn(mockAttributesType);
        EasyMock.expect(mockAttributesType.getProperty()).andReturn(arrayList);
        EasyMock.expect(mockPropertyType.getName()).andReturn("");
        stringarray = new ArrayList();
        EasyMock.expect(mockPropertyType.getValue()).andReturn(stringarray);
        EasyMock.expect(mockPropertyType.getValue()).andReturn(stringarray);
        EasyMock.replay(mockComponentType, mockAttributesType,mockPropertyType);
        expResult = null ;
        result = instance.getFirstValueByName(sName);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getValueListByName method, of class ComponentTypeWrapper.
     */
    public void testGetValueListByName() {
        System.out.println("getValueListByName");
        String sName = "";
        ComponentTypeWrapper instance = new ComponentTypeWrapper(new ComponentType());
        ComponentType mockComponentType ;
        AttributesType mockAttributesType ;
        PropertyType mockPropertyType;

        /* test 1 - attributes = null */
        mockComponentType = EasyMock.createMock(ComponentType.class);
        instance.dComponentType=mockComponentType;
        EasyMock.expect(mockComponentType.getAttributes()).andReturn(null);
        EasyMock.replay(mockComponentType);
        List expResult = null;
        List result = instance.getValueListByName(sName);
        assertEquals(expResult, result);

        /* test 2 - attributes != null and empty List */
        mockComponentType = EasyMock.createMock(ComponentType.class);
        mockAttributesType = EasyMock.createMock(AttributesType.class);
        instance.dComponentType=mockComponentType;
        EasyMock.expect(mockComponentType.getAttributes()).andReturn(mockAttributesType);
        List<PropertyType> arraylist = new ArrayList();
        EasyMock.expect(mockAttributesType.getProperty()).andReturn(arraylist);
        EasyMock.replay(mockComponentType,mockAttributesType);
        expResult = arraylist;
        result = instance.getValueListByName(sName);
        assertEquals(expResult, result);

        /* test 3 - attributes != null and valid property not equal to sName */
        mockComponentType = EasyMock.createMock(ComponentType.class);
        mockAttributesType = EasyMock.createMock(AttributesType.class);
        mockPropertyType = EasyMock.createMock(PropertyType.class);
        instance.dComponentType=mockComponentType;
        EasyMock.expect(mockComponentType.getAttributes()).andReturn(mockAttributesType);
        arraylist = new ArrayList();
        arraylist.add(mockPropertyType);
        EasyMock.expect(mockAttributesType.getProperty()).andReturn(arraylist);
        EasyMock.expect(mockPropertyType.getName()).andReturn("wrong");
        EasyMock.replay(mockComponentType,mockAttributesType, mockPropertyType);
        expResult = new ArrayList();
        result = instance.getValueListByName(sName);
        assertEquals(expResult, result);

        /* test 4 - attributes != null and valid property equal to sName */
        mockComponentType = EasyMock.createMock(ComponentType.class);
        mockAttributesType = EasyMock.createMock(AttributesType.class);
        mockPropertyType = EasyMock.createMock(PropertyType.class);
        instance.dComponentType=mockComponentType;
        EasyMock.expect(mockComponentType.getAttributes()).andReturn(mockAttributesType);
        arraylist = new ArrayList();
        arraylist.add(mockPropertyType);
        EasyMock.expect(mockAttributesType.getProperty()).andReturn(arraylist);
        EasyMock.expect(mockPropertyType.getName()).andReturn(sName);
        List<String> retlist = new ArrayList();
        EasyMock.expect(mockPropertyType.getValue()).andReturn(retlist);
        EasyMock.replay(mockComponentType,mockAttributesType, mockPropertyType);
        expResult = retlist;
        result = instance.getValueListByName(sName);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setValueByName method, of class ComponentTypeWrapper.
     */
    public void testSetValueByName() {
  /*      System.out.println("setValueByName");
        String sTitle = "";
        String sName = "";
        String sValue = "";
        ComponentType mockComponentType;
        ContainerType mockContainerType;
        ContentsType mockContentsType;
        AttributesType mockAttributesType;
        PropertyType mockPropertyType;

        ComponentTypeWrapper instance = new ComponentTypeWrapper(new ContainerType());

        /* ContainerType */
        /*
        mockContainerType = EasyMock.createMock(ContainerType.class);
        mockAttributesType = EasyMock.createMock(AttributesType.class);
        mockPropertyType = EasyMock.createMock(PropertyType.class);
        mockComponentType = EasyMock.createMock(ComponentType.class);
        mockContentsType = EasyMock.createMock(ContentsType.class);
        String s1 = "string1";
        List<PropertyType> arrayList = new ArrayList();
        arrayList.add(mockPropertyType);
        instance.dComponentType = mockContainerType;
        EasyMock.expect(mockContainerType.getAttributes()).andReturn(mockAttributesType).atLeastOnce();
        EasyMock.expect(mockAttributesType.getProperty()).andReturn(arrayList).atLeastOnce();
        EasyMock.expect(mockPropertyType.getName()).andReturn(s1).atLeastOnce();
        List<String> stringarray = new ArrayList();
        stringarray.add(s1);
        EasyMock.expect(mockPropertyType.getValue()).andReturn(stringarray).atLeastOnce();
        EasyMock.expect(mockContainerType.getContents()).andReturn(mockContentsType).atLeastOnce();
        List<ComponentType> lchildren = new ArrayList();
        List<ComponentType> nchildren = new ArrayList();

        lchildren.add(mockComponentType);
        EasyMock.expect(mockContentsType.getWidgetOrContainer()).andReturn(lchildren).atLeastOnce();
        mockContentsType.setWidgetOrContainer(nchildren);
        EasyMock.expectLastCall().atLeastOnce();
        mockContainerType.setContents(mockContentsType);
        EasyMock.expectLastCall().atLeastOnce();

        EasyMock.replay(mockContainerType, mockAttributesType,mockPropertyType,mockContentsType,mockComponentType);
        instance.setValueByName(sTitle, sName, sValue);
*/
        
    }

    /**
     * Test of addValueByName method, of class ComponentTypeWrapper.
     */
    public void testAddValueByName_String_String() {
        System.out.println("addValueByName");
        String sName = "right";
        String sValue = "";
        AttributesType mockAttributesType;
        ComponentType mockComponentType;
        PropertyType mockPropertyType, mockPropertyType2;

        /* Test 1 - attributes = null */
        mockComponentType = EasyMock.createMock(ComponentType.class);

        ComponentTypeWrapper instance = new ComponentTypeWrapper(new ComponentType());
        instance.dComponentType = mockComponentType;
        EasyMock.expect(mockComponentType.getAttributes()).andReturn(null);
        mockComponentType.setAttributes((AttributesType) EasyMock.anyObject());
        EasyMock.replay(mockComponentType);
        instance.addValueByName(sName, sValue);

        /* Test 2 - attributes != null */
        mockComponentType = EasyMock.createMock(ComponentType.class);
        mockAttributesType = EasyMock.createMock(AttributesType.class);
        mockPropertyType = EasyMock.createMock(PropertyType.class);
        mockPropertyType2 = EasyMock.createMock(PropertyType.class);
        instance = new ComponentTypeWrapper(new ComponentType());
        instance.dComponentType = mockComponentType;
        EasyMock.expect(mockComponentType.getAttributes()).andReturn(mockAttributesType);
        List<PropertyType> lproperty = new ArrayList();
        lproperty.add(mockPropertyType);
        lproperty.add(mockPropertyType2);
        EasyMock.expect(mockAttributesType.getProperty()).andReturn(lproperty);
        EasyMock.expect(mockPropertyType.getName()).andReturn("wrong");
        EasyMock.expect(mockPropertyType2.getName()).andReturn(sName);
        EasyMock.expect(mockPropertyType2.getValue()).andReturn(new ArrayList());
        mockPropertyType2.setValue((List<String>) EasyMock.anyObject());
        EasyMock.expectLastCall().anyTimes();
        mockPropertyType.setValue((List<String>) EasyMock.anyObject());
        EasyMock.expectLastCall().anyTimes();
        mockAttributesType.setProperty(lproperty);
        mockComponentType.setAttributes(mockAttributesType);
        EasyMock.replay(mockComponentType,mockAttributesType,mockPropertyType,mockPropertyType2);
        instance.addValueByName(sName, sValue);
        
    }

    /**
     * Test of addValueByName method, of class ComponentTypeWrapper.
     */
    public void testAddValueByName_3args() {
        System.out.println("addValueByName");
        String sTitle = "";
        String sName = "";
        String sValue = "";
        ComponentTypeWrapper instance = null;
        instance.addValueByName(sTitle, sName, sValue);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getChildByID method, of class ComponentTypeWrapper.
     */
    public void testGetChildByID() {
        System.out.println("getChildByID");
        String ID = "";
        ComponentTypeWrapper instance = null;
        ComponentTypeWrapper expResult = null;
        ComponentTypeWrapper result = instance.getChildByID(ID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printInfo method, of class ComponentTypeWrapper.
     */
    public void testPrintInfo() {
        System.out.println("printInfo");
        ComponentTypeWrapper instance = null;
        instance.printInfo();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasChild method, of class ComponentTypeWrapper.
     */
    public void testHasChild() {
        System.out.println("hasChild");
        ComponentTypeWrapper instance;
        boolean result;
        boolean expResult;

        /* Test 1 - children = null */
        instance = new ComponentTypeWrapper(new ComponentType());
        instance.children = null;
        expResult = false;
        result = instance.hasChild();
        assertEquals(expResult, result);

        /* Test 2 - children.size = 0 */
        instance = new ComponentTypeWrapper(new ComponentType());
        instance.children = new ArrayList();
        expResult = false;
        result = instance.hasChild();
        assertEquals(expResult, result);

        /* Test 3 - children.size > 0 */
        instance = new ComponentTypeWrapper(new ComponentType());
        instance.children = new ArrayList();
        instance.children.add(new ComponentTypeWrapper(null));
        expResult = true;
        result = instance.hasChild();
        assertEquals(expResult, result);
    }

}
