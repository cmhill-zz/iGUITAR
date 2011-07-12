/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.model;

import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.model.wrapper.PropertyTypeWrapper;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.easymock.classextension.EasyMock;

/**
 *
 * @author user
 */
public class GWindowTest extends TestCase {
    
    public GWindowTest(String testName) {
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
     * Test of extractWindow method, of class GWindow.
     */
    public void testExtractWindow() {
        System.out.println("extractWindow");

        /* Test 1
         * guiproperties null */
        GWindowImpl instance = new GWindowImpl();
        instance.fullID = "test";
        instance.modal = true;
        instance.isRoot = true;
        instance.guiproplist = null;

        GUIType result = instance.extractWindow();
        List<PropertyType> resultProps = result.getWindow().getAttributes().getProperty();
        ComponentTypeWrapper expComp = new ComponentTypeWrapper(new ComponentType());
        expComp.addValueByName(GUITARConstants.ID_TAG_NAME,"test");
        expComp.addValueByName(GUITARConstants.MODAL_TAG_NAME,"" + true);
        expComp.addValueByName(GUITARConstants.ROOTWINDOW_TAG_NAME,"" +true);

        List<PropertyTypeWrapper> retProp = new ArrayList();
        for ( int i = 0; i < resultProps.size(); i++) {
            retProp.add(new PropertyTypeWrapper(resultProps.get(i)));
        }
        List<PropertyType> expP = expComp.getDComponentType().getAttributes().getProperty();
        List<PropertyTypeWrapper> expProp = new ArrayList();
        for ( int i = 0; i < expP.size(); i++) {
            expProp.add(new PropertyTypeWrapper(expP.get(i)));
        }
        
        assertNotNull(result.getContainer());
        assertNotNull(result.getContainer().getContents());
        assertEquals(expProp, retProp);

        /* Test 2
         * gui properties not null */
         instance = new GWindowImpl();
        instance.fullID = "test";
        instance.modal = true;
        instance.isRoot = true;
        instance.guiproplist = new ArrayList();

        result = instance.extractWindow();
        resultProps = result.getWindow().getAttributes().getProperty();
        expComp = new ComponentTypeWrapper(new ComponentType());
        expComp.addValueByName(GUITARConstants.ID_TAG_NAME,"test");
        expComp.addValueByName(GUITARConstants.MODAL_TAG_NAME,"" + true);
        expComp.addValueByName(GUITARConstants.ROOTWINDOW_TAG_NAME,"" +true);

        retProp = new ArrayList();
        for ( int i = 0; i < resultProps.size(); i++) {
            retProp.add(new PropertyTypeWrapper(resultProps.get(i)));
        }
        expP = expComp.getDComponentType().getAttributes().getProperty();
        expProp = new ArrayList();
        for ( int i = 0; i < expP.size(); i++) {
            expProp.add(new PropertyTypeWrapper(expP.get(i)));
        }

        assertNotNull(result.getContainer());
        assertNotNull(result.getContainer().getContents());
        assertEquals(expProp, retProp);
    }

    /**
     * Test of isRoot method, of class GWindow.
     */
    public void testIsRoot() {
        System.out.println("isRoot");
        /* sets root */
        GWindowImpl instance = new GWindowImpl();
        instance.isRoot = true;
        assertEquals(true,instance.isRoot());
        instance.isRoot = false;
        assertEquals(false,instance.isRoot());
    }

    /**
     * Test of setRoot method, of class GWindow.
     */
    public void testSetRoot() {
        System.out.println("setRoot");

        GWindowImpl instance = new GWindowImpl();
        instance.setRoot(true);
        assertEquals(true,instance.isRoot);
        instance.setRoot(false);
        assertEquals(false,instance.isRoot);
    }

    /**
     * Test of getFirstChildByID method, of class GWindow.
     */
    public void testGetFirstChildByID() {
        System.out.println("getFirstChildByID");
        GComponent container = EasyMock.createMock(GComponent.class);
        EasyMock.expect(container.getFirstChildByID("Test Case")).andReturn(container);
        EasyMock.replay(container);

        GWindowImpl instance = new GWindowImpl();
        instance.container = container;
        GComponent result = instance.getFirstChildByID("Test Case");

        assertEquals(container, result);
    }

    public class GWindowImpl extends GWindow {
        String fullID;
        boolean modal;
         List<PropertyType> guiproplist;
         GComponent container;

        public GComponent getContainer() {
            return container;
        }

        public List<PropertyType> getGUIProperties() {
            return guiproplist;
        }

        public GUIType extractGUIProperties() {
            return null;
        }

        public boolean isModal() {
            return modal;
        }

        public boolean equals(Object window) {
            return false;
        }

        public boolean isValid() {
            return false;
        }

        public String getFullID() {
            return fullID;
        }

        public String getName() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

}
