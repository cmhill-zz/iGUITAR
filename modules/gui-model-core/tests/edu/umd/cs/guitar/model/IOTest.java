/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.umd.cs.guitar.model;

import edu.umd.cs.guitar.model.data.ComponentListType;
import edu.umd.cs.guitar.model.data.Configuration;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;

/**
 *
 * @author user
 */
public class IOTest extends TestCase {
    
    public IOTest(String testName) {
        super(testName);
    }

    Configuration obj;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        obj = new Configuration();
        obj.setIgnoredComponents(new ComponentListType());
        obj.getIgnoredComponents().setFullComponent(new ArrayList());
        obj.setTerminalComponents(new ComponentListType());
        obj.getTerminalComponents().setFullComponent(new ArrayList());
        
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    boolean compareConfigurations(Configuration a, Configuration b) {
        if ( a.getIgnoredComponents().getFullComponent().equals(b.getIgnoredComponents().getFullComponent())) {
            if ( a.getTerminalComponents().getFullComponent().equals(b.getTerminalComponents().getFullComponent())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Test of readObjFromFile method, of class IO.
     */
    public void testReadObjFromFile_FileInputStream_Class() {
        System.out.println("readObjFromFile");
        Configuration result;
        FileInputStream is = null;
        try {
            is = new FileInputStream("tests/inputs/IOTest-obj.oracle.xml");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IOTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        /* Test 1 - read obj from oracle file */
        result = (Configuration) IO.readObjFromFile(is, Configuration.class);

        assertEquals(true, compareConfigurations(obj,result));
        try {
            /* Test 2 - exception for file not found */
            is.close();
        } catch (IOException ex) {
            Logger.getLogger(IOTest.class.getName()).log(Level.SEVERE, null, ex);
        }

      result = (Configuration) IO.readObjFromFile(is, Configuration.class);
 


    }

    /**
     * Test of readObjFromFile method, of class IO.
     */
    public void testReadObjFromFile_String_Class() {
        System.out.println("readObjFromFile");
        Configuration result;
        /* Test 1 - read obj from oracle file */
        result = (Configuration) IO.readObjFromFile("tests/inputs/IOTest-obj.oracle.xml", Configuration.class);

        assertEquals(true, compareConfigurations(obj,result));

        /* Test 2 - exception for file not found */
        result = (Configuration) IO.readObjFromFile("notfound/IOTest-obj.oracle.xml", Configuration.class);

    }

    /**
     * Test of writeObjToFile method, of class IO.
     */
    public void testWriteObjToFile_Object_OutputStream() {
        try {
            System.out.println("writeObjToFile");
            /* Test 1 - write object to file in current dir */
            FileOutputStream os = new FileOutputStream("tests/IOTest-obj.xml");
            IO.writeObjToFile(obj, os);
            /* Test 2 - close file to cause error */
            os.close();
            IO.writeObjToFile(obj, os);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IOTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of writeObjToFile method, of class IO.
     */
    public void testWriteObjToFile_Object_String() {
        System.out.println("writeObjToFile");
        /* Test 1 - write object to file in current dir */
        IO.writeObjToFile(obj, "tests/IOTest-obj.xml");
       
        /* Test 2 - write object to file in directory that doesnt exist */
        IO.writeObjToFile(obj, "doesnotexist/IOTest-obj.xml");
    }

}
