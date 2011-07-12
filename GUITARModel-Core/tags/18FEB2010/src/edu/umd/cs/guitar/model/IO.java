/*	
 *  Copyright (c) 2009-@year@. The GUITAR group at the University of Maryland. Names of owners of this group may
 *  be obtained by sending an e-mail to atif@cs.umd.edu
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 *  documentation files (the "Software"), to deal in the Software without restriction, including without 
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *	the Software, and to permit persons to whom the Software is furnished to do so, subject to the following 
 *	conditions:
 * 
 *	The above copyright notice and this permission notice shall be included in all copies or substantial 
 *	portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 *	LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 *	EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 *	IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 *	THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package edu.umd.cs.guitar.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * 
 * Provide GUITAR data reader and writer.
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class IO {

    /**
     * Read an object from a XML file
     * @param is
     *      input stream 
     * @param cls
     *      class of object to be read 
     * @return object
     */
    public static Object readObjFromFile(FileInputStream is, Class<?> cls) {

        Object retObj = null;
        try {

            String packageName = cls.getPackage().getName();
            JAXBContext jc = JAXBContext.newInstance(packageName);

            Unmarshaller u = jc.createUnmarshaller();
            retObj = u.unmarshal(is);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return retObj;
    }

    /**
     * Read an object from a XML file
     * 
     * @param sFileName
     *            file name
     * @param cls
     *            class of object
     * @return the object to be read
     */
    public static Object readObjFromFile(String sFileName, Class<?> cls) {
        Object retObj = null;
        try {
            retObj = readObjFromFile(new FileInputStream(sFileName), cls);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return retObj;
    }

    /**
     * Write an object to XML file
     * 
     * @param object
     *            object to write
     * @param os
     *            output stream
     */
    public static void writeObjToFile(Object object, OutputStream os) {
        String packageName = object.getClass().getPackage().getName();
        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance(packageName);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            marshaller.marshal(object, os);
            os.close();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Write an object to XML file
     * 
     * <p>
     * 
     * @param object
     *            Object to write
     * @param sFileName
     *            file name
     */
    public static void writeObjToFile(Object object, String sFileName) {
        try {
            writeObjToFile(object, new FileOutputStream(sFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
