/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import junit.framework.TestCase;

/**
 *
 * @author user
 */
public class UtilTest extends TestCase {
    
    public UtilTest(String testName) {
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
     * Test of getListFromFile method, of class Util.
     */
    public void testGetListFromFile() {
        System.out.println("getListFromFile");
        String sFileName = "";
        boolean isOrdered = false;
        List expResult = null;
        List result = Util.getListFromFile(sFileName, isOrdered);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTimeStamp method, of class Util.
     */
    public void testGetTimeStamp() {
        System.out.println("getTimeStamp");

        DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        String expResult =  df.format(new Date());
        String result = Util.getTimeStamp();

        /* check for small variation in two strings */
        assert(expResult.startsWith(result.substring(0,11)));
        
    }

}
