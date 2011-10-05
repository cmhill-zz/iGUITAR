/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.model;

import edu.umd.cs.guitar.exception.ApplicationConnectException;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.ContentsType;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.GUIType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import junit.framework.TestCase;
import org.easymock.classextension.EasyMock;

/**
 *
 * @author user
 */
public class GApplicationTest extends TestCase {
    
    public GApplicationTest(String testName) {
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
     * Test of connect method, of class GApplication.
     */
    public void testConnect_0args() throws Exception {
        System.out.println("connect");
        GApplication instance = new GApplicationImpl();
        instance.connect();
        
    }

    /**
     * Test of connect method, of class GApplication.
     */
    public void testConnect_StringArr() throws Exception {
        System.out.println("connect");
        String[] args = {"a", "string", "test"};
        GApplication instance = new GApplicationImpl();
        instance.connect(args);
        
    }

    /**
     * Test of getAllWindow method, of class GApplication.
     */
    public void testGetAllWindow() {
        System.out.println("getAllWindow");
        GApplication instance = new GApplicationImpl();
        Set expResult = null;
        Set result = instance.getAllWindow();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getCurrentState method, of class GApplication.
     */
    public void testGetCurrentState() {
        System.out.println("getCurrentState");
        GApplicationImpl instance = new GApplicationImpl();
        instance.testNum = 21;
        GUIStructure result = instance.getCurrentState();
        GUIStructure expResult = (GUIStructure) instance.expResult;

        assertEquals(expResult.getGUI(), result.getGUI());
 
        instance = new GApplicationImpl();
        instance.testNum = 22;
        result = instance.getCurrentState();
        expResult = (GUIStructure) instance.expResult;

        assertEquals(expResult.getGUI(), result.getGUI());

    }

    /**
     * Test of getCurrentWinID method, of class GApplication.
     */
    public void testGetCurrentWinID() {
        System.out.println("getCurrentWinID");
        GApplicationImpl instance = new GApplicationImpl();
        instance.testNum = 3;
        Set<String> result = instance.getCurrentWinID();
        Set<String> expResult = (Set<String>) instance.expResult;

        assertEquals(expResult, result);
    }

    public class GApplicationImpl extends GApplication {
        
        public int testNum = 1;
        public Object expResult;

        public void connect() throws ApplicationConnectException {
        }

        public void connect(String[] args) throws ApplicationConnectException {
        }

        public Set<GWindow> getAllWindow() {
            if ( testNum == 1 ) {
                return null;
            }
            else if ( testNum == 21 ){
                GUIStructure expRet = new GUIStructure();
                List<GUIType> expSet = new ArrayList();
                Set<GWindow> set = new HashSet();

                GWindow mockWindow1 = EasyMock.createMock(GWindow.class);
                GUIType mockGUIType1 = EasyMock.createMock(GUIType.class);
                GComponent mockContainer1 = EasyMock.createMock(GComponent.class);
                ComponentType mockCompType1 = EasyMock.createMock(ComponentType.class);
                ContainerType mockContainerType1 = EasyMock.createMock(ContainerType.class);
                ContentsType mockContentsType1 = EasyMock.createMock(ContentsType.class);
                ArrayList<ComponentType> componentTypeList = new ArrayList();
                EasyMock.expect(mockWindow1.isValid()).andReturn(Boolean.FALSE);
                EasyMock.expect(mockWindow1.extractWindow()).andReturn(mockGUIType1);
                EasyMock.expect(mockWindow1.getContainer()).andReturn(mockContainer1);
                EasyMock.expect(mockContainer1.extractProperties()).andReturn(mockCompType1);
                EasyMock.expect(mockGUIType1.getContainer()).andReturn(mockContainerType1);
                EasyMock.expect(mockContainerType1.getContents()).andReturn(mockContentsType1);
                EasyMock.expect(mockContentsType1.getWidgetOrContainer()).andReturn(componentTypeList);
                /* then mocKCompType1 is added to the list */
                EasyMock.replay(mockWindow1, mockContainer1, mockGUIType1, mockContainerType1, mockContentsType1, mockCompType1);

                GWindow mockWindow2 = EasyMock.createMock(GWindow.class);
                GUIType mockGUIType2 = EasyMock.createMock(GUIType.class);
                GComponent mockContainer2 = EasyMock.createMock(GComponent.class);
                ComponentType mockCompType2 = EasyMock.createMock(ComponentType.class);
                ContainerType mockContainerType2 = EasyMock.createMock(ContainerType.class);
                ContentsType mockContentsType2 = EasyMock.createMock(ContentsType.class);
                ArrayList<ComponentType> componentTypeList2 = new ArrayList();
                EasyMock.expect(mockWindow2.isValid()).andReturn(Boolean.TRUE);
                EasyMock.expect(mockWindow2.extractWindow()).andReturn(mockGUIType2);
                EasyMock.expect(mockWindow2.getContainer()).andReturn(mockContainer2);
                EasyMock.expect(mockContainer2.extractProperties()).andReturn(mockCompType2);
                EasyMock.expect(mockGUIType2.getContainer()).andReturn(mockContainerType2);
                EasyMock.expect(mockContainerType2.getContents()).andReturn(mockContentsType2);
                EasyMock.expect(mockContentsType2.getWidgetOrContainer()).andReturn(componentTypeList2);
                /* then mocKCompType2 is added to the list */
                EasyMock.replay(mockWindow2, mockContainer2, mockGUIType2, mockContainerType2, mockContentsType2, mockCompType2);

                set.add(mockWindow1);
                set.add(mockWindow2);

                /* set expRet */
                expSet.add(mockGUIType2);
                expRet.setGUI(expSet);
                expResult = (Object) expRet;

                return set;
            }   else if ( testNum == 22 ){
                GUIStructure expRet = new GUIStructure();
                List<GUIType> expSet = new ArrayList();
                Set<GWindow> set = new HashSet();

                GWindow mockWindow3 = EasyMock.createMock(GWindow.class);
                GUIType mockGUIType3 = EasyMock.createMock(GUIType.class);
                GComponent mockContainer3 = EasyMock.createMock(GComponent.class);
                ContainerType mockCompType3 = EasyMock.createMock(ContainerType.class);
                ContainerType mockContainerType3 = EasyMock.createMock(ContainerType.class);
                ContentsType mockContentsType3 = EasyMock.createMock(ContentsType.class);
                ArrayList<ComponentType> componentTypeList3 = new ArrayList();
                List<GComponent> children = new ArrayList();
                GComponent mockChild = EasyMock.createMock(GComponent.class);
                children.add((GComponent) mockChild);
                ComponentType mockChildCompType = EasyMock.createMock(ComponentType.class);
                List<ComponentType> childrenComp = new ArrayList<ComponentType>();
                childrenComp.add(mockChildCompType);

                EasyMock.expect(mockWindow3.isValid()).andReturn(Boolean.TRUE);
                EasyMock.expect(mockWindow3.extractWindow()).andReturn(mockGUIType3);
                EasyMock.expect(mockWindow3.getContainer()).andReturn(mockContainer3);
                EasyMock.expect(mockContainer3.extractProperties()).andReturn(mockCompType3);
                mockCompType3.setContents((ContentsType) EasyMock.anyObject());
                EasyMock.expect(mockContainer3.getChildren()).andReturn(children);
                EasyMock.expect(mockChild.extractProperties()).andReturn(mockChildCompType);


                EasyMock.expect(mockGUIType3.getContainer()).andReturn(mockContainerType3);
                EasyMock.expect(mockContainerType3.getContents()).andReturn(mockContentsType3);
                EasyMock.expect(mockContentsType3.getWidgetOrContainer()).andReturn(componentTypeList3);
                /* then mockChildCompType is added to the list */
                EasyMock.replay(mockWindow3, mockContainer3, mockGUIType3, mockContainerType3, mockContentsType3, mockCompType3, mockChild,mockChildCompType);

                set.add(mockWindow3);

                /* set expRet */
                expSet.add(mockGUIType3);
                expRet.setGUI(expSet);
                expResult = (Object) expRet;

                return set;
            }
            else if ( testNum == 3 ) {
                Set<String> expSet = new HashSet<String>();
                expSet.add("Hello");
                expSet.add("World");
                expResult = (Object) expSet;

                GWindow mockWindow1 = EasyMock.createMock(GWindow.class);
                EasyMock.expect(mockWindow1.getTitle()).andReturn("Hello");//changed from FullID to title because only option for GWindow
                GWindow mockWindow2 = EasyMock.createMock(GWindow.class);
                EasyMock.expect(mockWindow2.getTitle()).andReturn("World");//changed from FullID to title because only option for GWindow
                EasyMock.replay(mockWindow1, mockWindow2);

                Set<GWindow> ret = new HashSet();
                ret.add(mockWindow1);
                ret.add(mockWindow2);
                return ret;
            }
            return null;
            
        }

    }

}
