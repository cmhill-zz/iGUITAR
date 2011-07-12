package edu.umd.cs.guitar.testcase.plugin;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import junit.framework.Assert;
import junit.framework.Test;

import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.testcase.plugin.MaxEventTestCase.*;
import edu.umd.cs.guitar.model.data.EventGraphType;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.model.data.EventsType;
import edu.umd.cs.guitar.model.data.RowType;
import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.model.data.TestCase;
import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;

/** Test class to test the MaxEventTestCase plugin for the TestCaseGenerator */
public class MaxEventTestCaseTest extends junit.framework.TestCase {

	private static final String outputDir = "./test";

	/**
	 * Setup the test by deleting the output directory
	 */
	public void setup() {
		try {
			File dir = new File(outputDir);

			File[] listFiles = dir.listFiles();

			for (File file : listFiles) {
				file.delete();
			}
		}catch(Exception e) {

		}
	}
	
	public void testGetConfig() {
		MaxEventTestCase maxEvent = new MaxEventTestCase();
		TestCaseGeneratorConfiguration tcgConfig = maxEvent.getConfiguration();
		
		Assert.assertTrue(tcgConfig != null);
	}
	
	/**
	 * Calling isValidArgs() without setting any configuration should return false
	 */
	public void testIsValidArgs1() {
		MaxEventTestCase maxEvent = new MaxEventTestCase();
		
		Assert.assertTrue(maxEvent.isValidArgs() == false);
	}

	/**
	 * Calling isValidArgs() after setting a REPEATS in MaxEventTestCaseConfiguration should return true
	 */
	public void testIsValidArgs2() {
		MaxEventTestCase maxEvent = new MaxEventTestCase();
		MaxEventTestCaseConfiguration.REPEATS = 0;

		Assert.assertTrue(maxEvent.isValidArgs() == true);
	}
	
	/**
	 * Generating test case with length 2. The EFG used has two events: e11 and e12.
	 */
	public void testGenerate1() {
		TestCaseGeneratorConfiguration.LENGTH = 2;

		runTestWithTwoEvents(2);

		File dir = new File(outputDir);

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".tst");
			}
		}; 

		File[] listFiles = dir.listFiles(filter);
		
		TestCase tc = (TestCase) readObjFromFile(outputDir + "/t01.tst", TestCase.class);

		Assert.assertTrue(tc.getStep().size() == 2);

		Assert.assertTrue(tc.getStep().get(0).getEventId().equals("e11"));
		Assert.assertTrue(tc.getStep().get(1).getEventId().equals("e12"));

		tc = (TestCase) readObjFromFile(outputDir + "/t01.tst", TestCase.class);

		Assert.assertTrue(tc.getStep().size() == 2);

		Assert.assertTrue(tc.getStep().get(0).getEventId().equals("e11"));
		Assert.assertTrue(tc.getStep().get(1).getEventId().equals("e12"));
	}
	
	private void runTestWithTwoEvents(int nMaxNumber) {
		TestCaseGeneratorConfiguration.OUTPUT_DIR = outputDir;
		MaxEventTestCaseConfiguration.REPEATS = 0;
		setup();

		MaxEventTestCase maxEvent = new MaxEventTestCase();

		EFG efg = new EFG();

		EventsType value = new EventsType();
		List<EventType> list = new ArrayList<EventType>();

		EventType type = new EventType();
		type.setAction("edu.umd.cs.guitar.event.JFCSelectionHandler");
		type.setEventId("e11");
		type.setInitial(true);
		type.setType("SYSTEM INTERACTION");
		type.setWidgetId("w11");

		list.add(type);

		type = new EventType();
		type.setAction("edu.umd.cs.guitar.event.JFCSelectionHandler");
		type.setEventId("e12");
		type.setInitial(false);
		type.setType("SYSTEM INTERACTION");
		type.setWidgetId("w12");

		list.add(type);

		value.setEvent(list);

		efg.setEvents(value);

		EventGraphType value1 = new EventGraphType();

		RowType row = new RowType();
		List<Integer> rows = new ArrayList<Integer>();
		rows.add(0);
		rows.add(1);

		row.setE(rows);

		List<RowType> tmp = new ArrayList<RowType>();
		tmp.add(row);

		rows = new ArrayList<Integer>();
		rows.add(1);
		rows.add(1);

		row.setE(rows);
		tmp.add(row);

		value1.setRow(tmp);

		efg.setEventGraph(value1);

		String outputDir = "test";

		maxEvent.generate(efg, outputDir, nMaxNumber);
	}
	
	private static Object readObjFromFile(String sFileName, Class<?> cls) {
        Object retObj = null;
        try {
            retObj = readObjFromFile(new FileInputStream(sFileName), cls);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return retObj;
    }
	
	 private static Object readObjFromFile(FileInputStream is, Class<?> cls) {
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

}