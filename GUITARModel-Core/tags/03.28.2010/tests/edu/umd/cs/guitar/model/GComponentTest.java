/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.model;

import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.wrapper.PropertyTypeWrapper;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.easymock.EasyMock;

/**
 *
 * @author user
 */
public class GComponentTest extends TestCase {

    private int ID = 0;

    public GComponentTest(String testName) {
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

    private class ComparablePropertyType extends PropertyType {

        protected ComparablePropertyType(String name, String value) {
            List<String> valList = new ArrayList<String>();
            valList.add(value);
            setValue(valList);
            setName(name);
        }

        protected ComparablePropertyType(PropertyType obj) {
            setValue(obj.getValue());
            setName(obj.getName());
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ComparablePropertyType ) {
                ComparablePropertyType cobj = (ComparablePropertyType) obj;
                if ( this.name.equals(cobj.name) && this.value.equals(cobj.value)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            return hash;
        }

    }

    private boolean compareComponentType(ComponentType obj1, ComponentType obj2) {
        List<PropertyType> list1 = obj1.getAttributes().getProperty();
        List<PropertyType> list2 = obj2.getAttributes().getProperty();
        
        List<ComparablePropertyType> clist1 =new ArrayList();
        List<ComparablePropertyType> clist2 =new ArrayList();

        for ( int i = 0; i <  list1.size(); i++ ) {
            clist1.add(new ComparablePropertyType( list1.get(0)));
        }
        for ( int i = 0; i <  list2.size(); i++ ) {
            clist2.add(new ComparablePropertyType( list2.get(0)));
        }
        return clist1.equals(clist2);
    }

    /**
     * Test of extractProperties method, of class GComponent.
     */
    public void testExtractProperties() {
        System.out.println("extractProperties");
        GComponentImpl instance;
        List<GEvent> eventList = new ArrayList();
        GEvent event = EasyMock.createMock(GEvent.class);
        eventList.add(event);
        EasyMock.replay(event);
        List<PropertyType> guiProperties = new ArrayList();

        ComponentType result;
        List<PropertyType> expProplist1 = new ArrayList();
        List<PropertyType> expProplist2 = new ArrayList();
        PropertyType tagName0 = new ComparablePropertyType(GUITARConstants.ID_TAG_NAME, "w" + ID++);
        PropertyType tagName1 = new ComparablePropertyType(GUITARConstants.ID_TAG_NAME, "w" + ID++);
        PropertyType className = new ComparablePropertyType(GUITARConstants.CLASS_TAG_NAME,  "class");
        PropertyType typeName = new ComparablePropertyType(GUITARConstants.TYPE_TAG_NAME,  "type");
        PropertyType eventName = new ComparablePropertyType(GUITARConstants.EVENT_TAG_NAME, event.getClass().getName());

        expProplist1.add(tagName0);
        expProplist1.add(className);
        expProplist1.add(typeName);
        expProplist1.add(eventName);

        expProplist2.add(tagName1);
        expProplist2.add(className);
        expProplist2.add(typeName);
        expProplist2.add(eventName);

        ComponentType expResult1 = new ComponentType();
        ComponentType expResult2 = new ComponentType();
        AttributesType value1 = new AttributesType();
        AttributesType value2 = new AttributesType();
        value1.setProperty(expProplist1);
        value2.setProperty(expProplist2);

        expResult1.setAttributes(value1);
        expResult2.setAttributes(value2);
        /* Test case 1
         * hasChildren = false;
         * getGUIProperties returns null
         */
         instance = new GComponentImpl();
         instance.hasChildren = false;
         instance.classVal = "class";
         instance.typeVal = "type";
         instance.eventList = eventList;
         instance.guiProperties = null;

         result = instance.extractProperties();
         
         assert(compareComponentType(result, expResult1));
         
        /* Test case 2
         * hasChildren = true;
         * getGUIProperties returns non-null
         */
         instance = new GComponentImpl();
         instance.hasChildren = false;
         instance.classVal = "class";
         instance.typeVal = "type";
         instance.eventList = eventList;
         instance.guiProperties = guiProperties;

         result = instance.extractProperties();
         assert(compareComponentType(result, expResult2));

    }



    /**
     * Test of getFirstChildByID method, of class GComponent.
     */
    public void testGetFirstChildByID() {
        System.out.println("getFirstChildByID");
        GComponentImpl instance;

        /* Test 1 - sID equals fullID */
        instance = new GComponentImpl();
        instance.fullID = "This";
        assertEquals(instance, instance.getFirstChildByID("This"));

        /* Test 2 - sID does not equal fullID
         * getChildren returns empty list so result is null
         */
        instance = new GComponentImpl();
        instance.fullID = "Not This";
        instance.children = new ArrayList();
        assertEquals(null, instance.getFirstChildByID("This"));

        /* Test 3 - sID does not equal fullID
         * getChildren has 2 elements, second one  that matches
         */
        instance = new GComponentImpl();
        instance.fullID = "Not This";
        instance.children = new ArrayList();
        GComponentImpl child1 = new GComponentImpl();
        GComponentImpl child2 = new GComponentImpl();
        child1.fullID = "Not This Either";
        child2.fullID = "This";
        child1.children= new ArrayList();
        child2.children= new ArrayList();
        instance.children.add(child1);
        instance.children.add(child2);
        assertEquals(child2, instance.getFirstChildByID("This"));
    }

    /**
     * Test of getFirstChild method, of class GComponent.
     */
    public void testGetFirstChild() {
        System.out.println("getFirstChild");
        GComponentImpl instance;
        GComponentImpl child1;
        GComponentImpl child2;
        List<GEvent> eventList = new ArrayList();
        GEvent event = EasyMock.createMock(GEvent.class);
        eventList.add(event);
        EasyMock.replay(event);

        List<PropertyTypeWrapper> expProplist1 = new ArrayList();
        PropertyType classprop = new PropertyType();
        PropertyType typeprop = new PropertyType();
        classprop.setName(GUITARConstants.CLASS_TAG_NAME);
        typeprop.setName(GUITARConstants.TYPE_TAG_NAME);
        List<String> classlist = new ArrayList();
        classlist.add("this");
        List<String> typelist = new ArrayList();
        typelist.add("one");
        classprop.setValue(classlist);
        typeprop.setValue(typelist);
        PropertyTypeWrapper className = new PropertyTypeWrapper(classprop);
        PropertyTypeWrapper typeName = new PropertyTypeWrapper(typeprop);
        
        expProplist1.add(className);
        expProplist1.add(typeName);
        
        /* Test case 1
         */
         instance = new GComponentImpl();
         instance.hasChildren = true;
         instance.classVal = "class";
         instance.typeVal = "type";
         instance.eventList = eventList;
         instance.guiProperties = null;
         instance.children = new ArrayList();

         child1 = new GComponentImpl();
         child1.hasChildren = false;
         child1.classVal = "class2";
         child1.typeVal = "type2";
         child1.eventList = eventList;
         child1.guiProperties = null;
         child1.children= new ArrayList();

         child2 = new GComponentImpl();
         child2.hasChildren = false;
         child2.classVal = "this";
         child2.typeVal = "one";
         child2.eventList = eventList;
         child2.guiProperties = null;
         child2.children= new ArrayList();
        instance.children.add(child1);
        instance.children.add(child2);

        assertEquals(child2, instance.getFirstChild(expProplist1));
    }

    public class GComponentImpl extends GComponent {

        boolean hasChildren;
        String classVal;
        String typeVal;
        List<GEvent> eventList;
        List<PropertyType> guiProperties;
        String fullID;
        List<GComponent> children;

        public List<GComponent> getChildren() {
            return children;
        }

        public String getClassVal() {
            return classVal;
        }

        public List<GEvent> getEventList() {
            return eventList;
        }

        public List<PropertyType> getGUIProperties() {
            return guiProperties;
        }

        public GComponent getParent() {
            return null;
        }

        public String getTypeVal() {
            return typeVal;
        }

        public boolean hasChildren() {
            return hasChildren;
        }

        public boolean isTerminal() {
            return false;
        }

        public String getFullID() {
            return fullID;
        }

        public String getName() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
		
		public boolean isEnable(){
		return false;//real value needs to be put here
		}
		
		public String getTitle(){
		return null;//real value needs to be put here
		}
    }

}
