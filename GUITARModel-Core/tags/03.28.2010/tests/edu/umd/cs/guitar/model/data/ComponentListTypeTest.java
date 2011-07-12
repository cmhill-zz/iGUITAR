/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.model.data;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author user
 */
public class ComponentListTypeTest extends TestCase {
    
    public ComponentListTypeTest(String testName) {
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
     * Test of getFullComponent method, of class ComponentListType.
     */
    public void testGetFullComponent() {
        System.out.println("[getFullComponent]");
        ComponentListType attrType = new ComponentListType();
        List<FullComponentType> proplist = new ArrayList();

        /* Test 1 - fullComponent is null */
        assertEquals(attrType.getFullComponent(), proplist);
        System.out.println("[getFullComponent|Test 1]- PASSED");

        /* Test 2 - fullComponent returned is the one set manaully */
        attrType.fullComponent = proplist;
        assertEquals(attrType.getFullComponent(),proplist);
        System.out.println("[getFullComponent|Test 2]- PASSED");
    }

    /**
     * Test of setFullComponent method, of class ComponentListType.
     */
    public void testSetFullComponent() {
        System.out.println("[setFullComponent]");
        ComponentListType attrType = new ComponentListType();
        List<FullComponentType> proplist = new ArrayList();
        /* Test 1 - setting empty array list */
        attrType.setFullComponent(proplist);
        assertEquals(attrType.fullComponent, proplist);
        System.out.println("[setFullComponent |Test 1]- PASSED");
    }

}
