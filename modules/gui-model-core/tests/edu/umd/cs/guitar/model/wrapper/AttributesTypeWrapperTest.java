/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.model.wrapper;

import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.PropertyType;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author user
 */
public class AttributesTypeWrapperTest extends TestCase {
    
    public AttributesTypeWrapperTest(String testName) {
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
     * Test of equals method, of class AttributesTypeWrapper.
     */
    public void testEquals() {
        System.out.println("equals");
        AttributesTypeWrapper instance;
        Object compareto;
        boolean expResult;
        boolean result;


        /* Test 1 - objects are the same object */
        expResult = true;
        instance = new AttributesTypeWrapper(new AttributesType());        
        result = instance.equals(instance);
        assertEquals(expResult,result);

        /* Test 2 - compare to is null */
        expResult = false;
        instance = new AttributesTypeWrapper(new AttributesType());
        result = instance.equals(null);
        assertEquals(expResult,result);

        /* Test 3 - compare to is of different class */
        expResult = false;
        instance = new AttributesTypeWrapper(new AttributesType());
        result = instance.equals(new AttributesType());
        assertEquals(expResult,result);

        /* Test 4 - wrapped is null and compare to wrapper is not null */
        expResult = false;
        instance = new AttributesTypeWrapper(null);
        compareto = new AttributesTypeWrapper(new AttributesType());
        result = instance.equals(compareto);
        assertEquals(expResult,result);

        /*  Test 5 - wrapped is null and compare to wrapped is null */
        expResult = true;
        instance = new AttributesTypeWrapper(null);
        compareto = new AttributesTypeWrapper(null);
        result = instance.equals(compareto);
        assertEquals(expResult,result);

        /* Test 6 - properties of both are null */
        expResult = true;
        instance = new AttributesTypeWrapper(new AttributesType());
        compareto = new AttributesTypeWrapper(new AttributesType());
        result = instance.equals(compareto);
        assertEquals(expResult,result);

        /* Test 7 - properties of one is null */
/*        expResult = false;
        instance = new AttributesTypeWrapper(new AttributesType());
        compareto = new AttributesTypeWrapper(new AttributesType());
        instance.dAtrributeType.setProperty(new ArrayList());
        result = instance.equals(compareto);
        assertEquals(expResult,result);
*/
        /* Test 8 - properties in each are different */
        expResult = false;
        List<PropertyType> proplist = new ArrayList();
        PropertyType prop = new PropertyType();
        prop.setName("Test");
        prop.setValue(new ArrayList());
        proplist.add(prop);
        proplist.add(new PropertyType());
        proplist.get(1).setName("test2");
        proplist.get(1).setValue(new ArrayList());
        instance = new AttributesTypeWrapper(new AttributesType());
        instance.dAtrributeType.setProperty(proplist);

        proplist = new ArrayList();
        proplist.add(new PropertyType());
        proplist.get(0).setName("test2");
        proplist.get(0).setValue(new ArrayList());
        compareto = new AttributesTypeWrapper(new AttributesType());
        ((AttributesTypeWrapper)compareto).dAtrributeType.setProperty(proplist);
        result = instance.equals(compareto);
        assertEquals(expResult,result);
    }

    /**
     * Test of containsAll method, of class AttributesTypeWrapper.
     */
    public void testContainsAll() {
        System.out.println("containsAll");
       AttributesTypeWrapper instance;
        AttributesTypeWrapper compareto;
        boolean expResult;
        boolean result;
        List<PropertyType> proplist;
        PropertyType prop;

        /* Test 1 - properties in first are empty list. other has 1 property */
        expResult = false;
        proplist = new ArrayList();
        prop = new PropertyType();
        prop.setName("Test");
        prop.setValue(new ArrayList());
        proplist.add(prop);
        instance = new AttributesTypeWrapper(new AttributesType());
        compareto = new AttributesTypeWrapper(new AttributesType());
        instance.dAtrributeType.setProperty(new ArrayList());
        ((AttributesTypeWrapper)compareto).dAtrributeType.setProperty(proplist);
        result = instance.containsAll(compareto);
        assertEquals(expResult,result);

        /* Test 2 - properties in each are same */
        expResult = true;
        proplist = new ArrayList();
        prop = new PropertyType();
        prop.setName("Test");
        prop.setValue(new ArrayList());
        proplist.add(prop);
        instance = new AttributesTypeWrapper(new AttributesType());
        compareto = new AttributesTypeWrapper(new AttributesType());
        instance.dAtrributeType.setProperty(proplist);
        ((AttributesTypeWrapper)compareto).dAtrributeType.setProperty(proplist);
        result = instance.containsAll(compareto);
        assertEquals(expResult,result);

    }

    /**
     * Test of contains method, of class AttributesTypeWrapper.
     */
    public void testContains() {
        System.out.println("contains");
        AttributesTypeWrapper instance;
        boolean expResult;
        boolean result;
        List<PropertyType> proplist;
        PropertyType tofind;

        /* Test 1 - current properties is an empty list */
        expResult = false;

        proplist = new ArrayList();
        instance = new AttributesTypeWrapper(new AttributesType());
        instance.dAtrributeType.setProperty(proplist);

        tofind = new PropertyType();
        tofind.setName("");
        tofind.setValue(new ArrayList());
        
        result = instance.contains(tofind);
        assertEquals(expResult, result);
        
        /* Test 2 - propert list not empty and property not in list of properties */
        expResult = false;

        proplist = new ArrayList();
        proplist.add(new PropertyType());
        proplist.get(0).setName("At 1");
        proplist.get(0).setValue(new ArrayList());
        instance = new AttributesTypeWrapper(new AttributesType());
        instance.dAtrributeType.setProperty(proplist);

        tofind = new PropertyType();
        tofind.setName("");
        tofind.setValue(new ArrayList());

        result = instance.contains(tofind);
        assertEquals(expResult, result);

        /* Test 3 - property list has two elements, tofind is in list of properties,
          tofind's value is null */
        expResult = true;

        tofind = new PropertyType();
        tofind.setName("At 2");
        tofind.setValue(null);

        proplist = new ArrayList();
        proplist.add(new PropertyType());
        proplist.get(0).setName("At 1");
        proplist.get(0).setValue(new ArrayList());
        proplist.add(tofind);
        instance = new AttributesTypeWrapper(new AttributesType());
        instance.dAtrributeType.setProperty(proplist);

        result = instance.contains(tofind);
        assertEquals(expResult, result);

        /* Test 4 - property list has two elements, tofind is in list of properties,
          tofind's value is not null but matching property's value is empty list */
        expResult = true;

        tofind = new PropertyType();
        tofind.setName("At 2");
        tofind.setValue(new ArrayList());

        proplist = new ArrayList();
        proplist.add(new PropertyType());
        proplist.get(0).setName("At 1");
        proplist.get(0).setValue(new ArrayList());
        proplist.add(tofind);
        instance = new AttributesTypeWrapper(new AttributesType());
        instance.dAtrributeType.setProperty(proplist);

        result = instance.contains(tofind);
        assertEquals(expResult, result);

       /* Test 5 - tofind has two strings in value.  matches on the first but fails to match on the second */
        expResult = false;

        tofind = new PropertyType();
        tofind.setName("At 1");
        tofind.setValue(new ArrayList());
        tofind.getValue().add("hello");
        tofind.getValue().add("world");
        
        proplist = new ArrayList();
        proplist.add(new PropertyType());
        proplist.get(0).setName("At 1");
        proplist.get(0).setValue(new ArrayList());
        proplist.get(0).getValue().add("hello");
            
        instance = new AttributesTypeWrapper(new AttributesType());
        instance.dAtrributeType.setProperty(proplist);

        result = instance.contains(tofind);
        assertEquals(expResult, result);

    }

    /**
     * Test of getPropertyByName method, of class AttributesTypeWrapper.
     */
    public void testGetPropertyByName() {
        System.out.println("getPropertyByName");
        AttributesTypeWrapper instance;
        PropertyType expResult;
        PropertyType result;
        List<PropertyType> proplist;

        /* Test 1 - property list is empty  */
        instance = new AttributesTypeWrapper(new AttributesType());
        expResult = null;
        proplist = new ArrayList();

        instance.dAtrributeType.setProperty(proplist);
        result = instance.getPropertyByName("world");
        assertEquals(expResult, result);

        /* Test 2 - property not in property list */
        instance = new AttributesTypeWrapper(new AttributesType());
        expResult = null;
        proplist = new ArrayList();
        proplist.add(new PropertyType());
        proplist.get(0).setName("At 1");
        proplist.get(0).setValue(new ArrayList());
        proplist.get(0).getValue().add("hello");

        instance.dAtrributeType.setProperty(proplist);
        result = instance.getPropertyByName("world");
        assertEquals(expResult, result);

        /* Test 3 - property in property list */
        instance = new AttributesTypeWrapper(new AttributesType());
        expResult = new PropertyType();
        expResult.setName("At 1");
        expResult.setValue(new ArrayList());
        proplist = new ArrayList();
        proplist.add(expResult);
        
        instance.dAtrributeType.setProperty(proplist);
        result = instance.getPropertyByName("At 1");
        assertEquals(expResult, result);


    }

    /**
     * Test of getFirstValByName method, of class AttributesTypeWrapper.
     */
    public void testGetFirstValByName() {
        System.out.println("getFirstValByName");
        AttributesTypeWrapper instance;
        String expResult;
        String result;
        List<PropertyType> proplist;

        /* Test 1 - property list is empty  */
        instance = new AttributesTypeWrapper(new AttributesType());
        expResult = null;
        proplist = new ArrayList();

        instance.dAtrributeType.setProperty(proplist);
        result = instance.getFirstValByName("world");
        assertEquals(expResult, result);

        /* Test 2 - property not in property list */
        instance = new AttributesTypeWrapper(new AttributesType());
        expResult = null;
        proplist = new ArrayList();
        proplist.add(new PropertyType());
        proplist.get(0).setName("At 1");
        proplist.get(0).setValue(new ArrayList());
        proplist.get(0).getValue().add("hello");

        instance.dAtrributeType.setProperty(proplist);
        result = instance.getFirstValByName("world");
        assertEquals(expResult, result);

        /* Test 3 - property in property list */
        expResult = null;
        instance = new AttributesTypeWrapper(new AttributesType());
        instance.dAtrributeType.setProperty(new ArrayList());
        instance.dAtrributeType.getProperty().add(new PropertyType());
        instance.dAtrributeType.getProperty().get(0).setName("test1");
        instance.dAtrributeType.getProperty().get(0).setValue(new ArrayList());
        result = instance.getFirstValByName("test1");
        assertEquals(expResult, result);

        /* Test 4 - property in property list */
        expResult = "B";
        instance = new AttributesTypeWrapper(new AttributesType());
        instance.dAtrributeType.setProperty(new ArrayList());
        instance.dAtrributeType.getProperty().add(new PropertyType());
        instance.dAtrributeType.getProperty().get(0).setName("test1");
        instance.dAtrributeType.getProperty().get(0).setValue(new ArrayList());
        instance.dAtrributeType.getProperty().get(0).getValue().add("B");
        result = instance.getFirstValByName("test1");
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class AttributesTypeWrapper.
     */
    public void testToString() {
        System.out.println("toString");
        AttributesTypeWrapper instance;
        String expResult;
        String result;

        /* Test 1 - null attributeType */
        expResult = "";
        instance = new AttributesTypeWrapper(null);
        result = instance.toString();
        assertEquals(expResult, result);

        /* Test 2 - non-empty properties list
         *           Return value isn't actually possible to assert due to
         *          how toString() is implemented.
         */
        instance = new AttributesTypeWrapper(new AttributesType());
        instance.dAtrributeType.setProperty(new ArrayList());
        instance.dAtrributeType.getProperty().add(new PropertyType());
        result = instance.toString();
        assertNotSame("", result);
    }

    /**
     * Test of printInfo method, of class AttributesTypeWrapper.
     * Test case only for code coverage.  Nothing to assert true as a result.
     */
    public void testPrintInfo() {
        System.out.println("printInfo");
        AttributesTypeWrapper instance = null;


        instance = new AttributesTypeWrapper(new AttributesType());
        instance.dAtrributeType.setProperty(new ArrayList());
        instance.dAtrributeType.getProperty().add(new PropertyType());
        instance.dAtrributeType.getProperty().add(new PropertyType());
        instance.dAtrributeType.getProperty().get(0).setName("test1");
        instance.dAtrributeType.getProperty().get(0).setValue(new ArrayList());
        instance.dAtrributeType.getProperty().get(0).getValue().add("A");
        instance.dAtrributeType.getProperty().get(1).setName("test2");
        instance.dAtrributeType.getProperty().get(1).setValue(new ArrayList());
        instance.dAtrributeType.getProperty().get(1).getValue().add("B");

        instance.printInfo();

    }

}
