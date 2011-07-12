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
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import edu.umd.cs.guitar.util.GUITARLog;

/**
 * 
 * Provide GUITAR data XML reader and writer. This is a replacement of
 * {@link IO} to deal with dynamic resource allocation.
 * 
 * <p>
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class XMLHandler {

    /**
     * Read an object from a XML file
     * 
     * @param is
     *            input stream
     * @param cls
     *            class of object to be read
     * @return
     */
    public Object readObjFromFile(InputStream is, Class<?> cls) {

        Object retObj = null;
        try {

            String packageName = cls.getPackage().getName();
            JAXBContext jc = JAXBContext.newInstance(packageName);

            Unmarshaller u = jc.createUnmarshaller();
            retObj = u.unmarshal(is);

        } catch (JAXBException e) {
			GUITARLog.log.error(e);
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
    public Object readObjFromFile(String sFileName, Class<?> cls) {
        Object retObj = null;
        try {

            InputStream in = getClass().getResourceAsStream(sFileName);
            retObj = readObjFromFile(in, cls);
        } catch (Exception e) {
            System.out.println("File " + sFileName + " not found");
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
    public void writeObjToFile(Object object, OutputStream os) {
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
			GUITARLog.log.error(e);
        } catch (IOException e) {
			GUITARLog.log.error(e);
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
    public void writeObjToFile(Object object, String sFileName) {
        try {
            writeObjToFile(object, new FileOutputStream(sFileName));
        } catch (FileNotFoundException e) {
			GUITARLog.log.error(e);
        }
    }
}
