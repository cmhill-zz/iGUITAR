/*  
 *  Copyright (c) 2009-@year@. The GUITAR group at the University of Maryland. Names of owners of this group may
 *  be obtained by sending an e-mail to atif@cs.umd.edu
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 *  documentation files (the "Software"), to deal in the Software without restriction, including without 
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *  the Software, and to permit persons to whom the Software is furnished to do so, subject to the following 
 *  conditions:
 * 
 *  The above copyright notice and this permission notice shall be included in all copies or substantial 
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 *  LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 *  EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 *  THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package edu.umd.cs.guitar.model;

import java.awt.Component;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.model.wrapper.GUIStructureWrapper;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao N. Nguyen </a>
 * 
 */
public class JFCDefaultIDGeneratorSimpleTest {

	private static final String TEST_DATA_DIR = "test-data";
	GUIStructure gs1;
	GUIStructure gs2;
	JFCDefaultIDGeneratorSimple generator;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		gs1 = (GUIStructure) IO.readObjFromFile(TEST_DATA_DIR + "/"
				+ "radio1.GUI", GUIStructure.class);
		gs2 = (GUIStructure) IO.readObjFromFile(TEST_DATA_DIR + "/radio2.GUI",
				GUIStructure.class);
		generator = JFCDefaultIDGeneratorSimple.getInstance();
	}

	@Test
	public void testIDGen(){
		System.out.println("Test ID Gen: ");
		
		System.out.println("------------------");
		System.out.println("First struture: ");
		generator.generateID(gs1);
		IO.writeObjToFile(gs1, TEST_DATA_DIR +"/"+ "radio1.out.GUI");

		
		System.out.println("------------------");
		System.out.println("Second struture: ");
		GUIStructureWrapper wGS2 = new GUIStructureWrapper(gs2);
		generator.generateID(gs2);
		
		IO.writeObjToFile(gs2, TEST_DATA_DIR +"/"+ "radio2.out.GUI");
		
//		ComponentTypeWrapper comp2 = wGS2.getComponentFromID("w219836856");
//		System.out.println("File: "+comp2.getFirstValueByName(GUITARConstants.TITLE_TAG_NAME));
		
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.out.println("Done");
	}

}
