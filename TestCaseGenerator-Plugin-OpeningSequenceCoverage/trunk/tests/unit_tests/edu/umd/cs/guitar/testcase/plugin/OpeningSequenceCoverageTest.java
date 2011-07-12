package edu.umd.cs.guitar.testcase.plugin;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import junit.framework.Assert;

import junit.framework.Test;

import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.testcase.plugin.OpeningSequenceCoverage;
import edu.umd.cs.guitar.model.data.EventGraphType;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.model.data.EventsType;
import edu.umd.cs.guitar.model.data.RowType;
import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.model.data.TestCase;
import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;

/**
 * This test class tests the OpeningSequenceCoverage plugin. It is based on Tandeep Sidhu's test suite, modified to test
 * the most recent iteration of the plugin (revision 2192). Changes to the plugin include new lines, branches, and testcase
 * naming conventions.
 * <br/><br/>
 * The plugin contains a private method, printGraph(), which is never called within the plugin code. This method contains
 * 13 lines and 6 branches that are therefore unreachable.
 * <br/>
 * Additionally, there is a conditional at line 131 which tests if the getPathToRoot(EventType event) method of the TCPlugin class
 * returns a LinkedList of size 0. The behavior of this method is currently to return a populated LinkedList or null, so the
 * false branch of this line is impossible to achieve.
 * <br/><br/>
 * After discounting unreachable lines and branches, maximal code coverage is 59/72 lines and 25/32 branches.
 * 
 * @author Tandeep Sidhu, Mahmoud Abdelsalam, Thomas Egan
 *
 */
public class OpeningSequenceCoverageTest extends junit.framework.TestCase {

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

	/**
	 * Calling isValidArgs() without setting any configuration should return false
	 */
	
	public void testIsValidArgs1() {
		OpeningSequenceCoverage coverage = new OpeningSequenceCoverage();

		Assert.assertTrue(coverage.isValidArgs() == false);

	}

	/**
	 * Calling isValidArgs() after setting a positive length in TestCaseGeneratorConfiguration should return true
	 */
	
	public void testIsValidArgs2() {
		OpeningSequenceCoverage coverage = new OpeningSequenceCoverage();
		TestCaseGeneratorConfiguration.LENGTH = 3;


		Assert.assertTrue(coverage.isValidArgs() == true);

	}

	/**
	 * Generating test case with length 1. The EFG used has two events: e0 and e4. e4 is the only initial event.
	 * <br/>
	 * Because e4 is the only initial event, the only valid length 1 test is a single test with the single step (e4).
	 * 
	 * <br/> <br/>
	 * To confirm the output, the generated test case file is read from the output directory, and the test is
	 * compared against the correct event sequence.
	 */
	
	public void testGenerate() {
		TestCaseGeneratorConfiguration.LENGTH = 1;
		
		//run test
		runTestWithTwoEvents(0);

		//check output
		File dir = new File(outputDir);

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".tst");
			}
		}; 

		File[] listFiles = dir.listFiles(filter);

		Assert.assertTrue(listFiles.length == 1);

		TestCase tc = (TestCase) readObjFromFile(outputDir + "/t_e4.tst",
				TestCase.class);

		Assert.assertTrue(tc.getStep().size() == 1);

		for (StepType type : tc.getStep()) {
			Assert.assertTrue(type.getEventId().equals("e4"));
		}

		 
	}

	/**
	 * Generating test case with length 2. The EFG used has two events: e0 and e4. e4 is the only initial event.
	 * <br/>
	 * The graph has the following edges: (e0 -> e4), (e4 -> e0), (e4 -> e4).
	 * <br/>
	 * The unique length 2 test cases are: (e4 -> e0), (e4 -> e4).
	 * 
	 * <br/> <br/>
	 * To confirm the output, the generated test case files are read from the output directory, and the tests are
	 * compared against the correct event sequences.
	 */
	
	public void testGenerate1() {
		TestCaseGeneratorConfiguration.LENGTH = 2;

		runTestWithTwoEvents(0);

		File dir = new File(outputDir);

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".tst");
			}
		}; 

		File[] listFiles = dir.listFiles(filter);


		Assert.assertTrue(listFiles.length == 2);


		TestCase tc = (TestCase) readObjFromFile(outputDir + "/t_e4_e0.tst",
				TestCase.class);

		Assert.assertTrue(tc.getStep().size() == 2);

		Assert.assertTrue(tc.getStep().get(0).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(1).getEventId().equals("e0"));


		tc = (TestCase) readObjFromFile(outputDir + "/t_e4_e4.tst",
				TestCase.class);

		Assert.assertTrue(tc.getStep().size() == 2);

		Assert.assertTrue(tc.getStep().get(0).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(1).getEventId().equals("e4"));



	}

	/**
	 * Generating test case with length 2, max number of 1. The EFG used has two events: e0 and e4. e4 is the only initial event.
	 * <br/>
	 * The graph has the following edges: (e0 -> e4), (e4 -> e0), (e4 -> e4).
	 * <br/>
	 * Running generate() should create 1 test: (e4 -> e0).
	 * 
	 * <br/> <br/>
	 * To confirm the output, the generated test case files are read from the output directory, and the tests are
	 * compared against the correct event sequences.
	 */
	
	public void testGenerate2() {
		TestCaseGeneratorConfiguration.LENGTH = 2;

		runTestWithTwoEvents(1);

		File dir = new File(outputDir);

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".tst");
			}
		}; 

		File[] listFiles = dir.listFiles(filter);


		Assert.assertTrue(listFiles.length == 1);


		TestCase tc = (TestCase) readObjFromFile(outputDir + "/t_e4_e0.tst",
				TestCase.class);

		Assert.assertTrue(tc.getStep().size() == 2);

		Assert.assertTrue(tc.getStep().get(0).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(1).getEventId().equals("e0"));
	}
	
	
	/**
	 * Generating test case with length 2. The EFG used has two events: e0 and e4. e4 is the only initial event.
	 * <br/>
	 * The graph has the following edges: (e0 -> e4), (e4 -> e0), (e4 -> e4).
	 * <br/>
	 * The unique length 4 test cases are: <br/>
	 * (e4 -> e0 -> e4 -> e0) <br/>
	 * (e4 -> e0 -> e4 -> e4) <br/>
	 * (e4 -> e4 -> e0 -> e4) <br/>
	 * (e4 -> e4 -> e4 -> e0) <br/>
	 * (e4 -> e4 -> e4 -> e4)
	 * <br/> <br/>
	 * To confirm the output, the generated test case files are read from the output directory, and the tests are
	 * compared against the correct event sequences.
	 */
	
	public void testGenerate3() {
		TestCaseGeneratorConfiguration.LENGTH = 4;

		runTestWithTwoEvents(0);
		
		File dir = new File(outputDir);

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".tst");
			}
		}; 

		File[] listFiles = dir.listFiles(filter);
		
		Assert.assertTrue(listFiles.length == 5);


		TestCase tc = (TestCase) readObjFromFile(outputDir + "/t_e4_e0_e4_e0.tst",
				TestCase.class);

		Assert.assertTrue(tc.getStep().size() == 4);

		Assert.assertTrue(tc.getStep().get(0).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(1).getEventId().equals("e0"));
		Assert.assertTrue(tc.getStep().get(2).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(3).getEventId().equals("e0"));


		tc = (TestCase) readObjFromFile(outputDir + "/t_e4_e0_e4_e4.tst",
				TestCase.class);

		Assert.assertTrue(tc.getStep().size() == 4);

		Assert.assertTrue(tc.getStep().get(0).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(1).getEventId().equals("e0"));
		Assert.assertTrue(tc.getStep().get(2).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(3).getEventId().equals("e4"));


		tc = (TestCase) readObjFromFile(outputDir + "/t_e4_e4_e0_e4.tst",
				TestCase.class);

		Assert.assertTrue(tc.getStep().size() == 4);

		Assert.assertTrue(tc.getStep().get(0).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(1).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(2).getEventId().equals("e0"));
		Assert.assertTrue(tc.getStep().get(3).getEventId().equals("e4"));


		tc = (TestCase) readObjFromFile(outputDir + "/t_e4_e4_e4_e0.tst",
				TestCase.class);

		Assert.assertTrue(tc.getStep().size() == 4);

		Assert.assertTrue(tc.getStep().get(0).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(1).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(2).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(3).getEventId().equals("e0"));


		tc = (TestCase) readObjFromFile(outputDir + "/t_e4_e4_e4_e4.tst",
				TestCase.class);

		Assert.assertTrue(tc.getStep().size() == 4);

		Assert.assertTrue(tc.getStep().get(0).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(1).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(2).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(3).getEventId().equals("e4"));


	}

	/**
	 * Generating test case with length 30 and max number 1. The EFG used has two events: e0 and e4. e4 is the only initial event.
	 * This test checks for the special case of file naming in the plugin (tests with length over 30 should be named by index,
	 * not by the sequence of events in the test).
	 * <br/>
	 * The graph has the following edges: (e0 -> e4), (e4 -> e0). The graph is kept simple to avoid timeout errors.
	 * <br/>
	 * Running generate() should create 1 test with 30 events alternating between e4 and e0: <br/>
	 * (e4 -> e0 -> e4 -> e0 -> ... -> e4 -> e0).
	 * <br/> <br/>
	 * To confirm the output, the generated test case files are read from the output directory, and the tests are
	 * compared against the correct event sequences.
	 */
	public void testGenerate4() {
		TestCaseGeneratorConfiguration.LENGTH = 30;

		runTestWithTwoCycle(1);
		
		File dir = new File(outputDir);

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".tst");
			}
		}; 

		File[] listFiles = dir.listFiles(filter);
		
		Assert.assertTrue(listFiles.length == 1);

		TestCase tc = (TestCase) readObjFromFile(outputDir + "/t_0.tst",
				TestCase.class);

		Assert.assertTrue(tc.getStep().size() == 30);

		for (int i = 0; i < 30; i++) {
			if (i % 2 == 0) {
				Assert.assertTrue(tc.getStep().get(i).getEventId().equals("e4"));
			} else {
				Assert.assertTrue(tc.getStep().get(i).getEventId().equals("e0"));
			}
		}
	}

	private void runTestWithTwoCycle(int nMaxNumber) {
		TestCaseGeneratorConfiguration.OUTPUT_DIR = outputDir;
		setup();

		OpeningSequenceCoverage coverage = new OpeningSequenceCoverage();

		EFG efg = new EFG();

		EventsType value = new EventsType();
		List<EventType> list = new ArrayList<EventType>();

		EventType type = new EventType();
		type.setAction("edu.umd.cs.guitar.event.JFCSelectionHandler");
		type.setEventId("e0");
		type.setInitial(false);
		type.setType("EXPAND");
		type.setWidgetId("w11");

		list.add(type);

		type = new EventType();
		type.setAction("edu.umd.cs.guitar.event.JFCSelectionHandler");
		type.setEventId("e4");
		type.setInitial(true);
		type.setType("EXPAND");
		type.setWidgetId("w13");

		list.add(type);


		value.setEvent(list );

		efg.setEvents(value );


		EventGraphType value1 = new EventGraphType();

		RowType row = new RowType();
		List<Integer> rows = new ArrayList<Integer>();
		rows.add(0);
		rows.add(1);

		row.setE(rows);

		List<RowType> tmp = new ArrayList<RowType>();
		tmp.add(row);



		row = new RowType();
		rows = new ArrayList<Integer>();
		rows.add(2);
		rows.add(0);

		row.setE(rows);
		tmp.add(row);

		value1.setRow(tmp);

		efg.setEventGraph(value1);



		String outputDir = "test";

		coverage.generate(efg, outputDir, nMaxNumber);
	}

	private void runTestWithTwoEvents(int nMaxNumber) {
		TestCaseGeneratorConfiguration.OUTPUT_DIR = outputDir;
		setup();

		OpeningSequenceCoverage coverage = new OpeningSequenceCoverage();

		EFG efg = new EFG();

		EventsType value = new EventsType();
		List<EventType> list = new ArrayList<EventType>();

		EventType type = new EventType();
		type.setAction("edu.umd.cs.guitar.event.JFCSelectionHandler");
		type.setEventId("e0");
		type.setInitial(false);
		type.setType("EXPAND");
		type.setWidgetId("w11");

		list.add(type);

		type = new EventType();
		type.setAction("edu.umd.cs.guitar.event.JFCSelectionHandler");
		type.setEventId("e4");
		type.setInitial(true);
		type.setType("EXPAND");
		type.setWidgetId("w13");

		list.add(type);


		value.setEvent(list );

		efg.setEvents(value );


		EventGraphType value1 = new EventGraphType();

		RowType row = new RowType();
		List<Integer> rows = new ArrayList<Integer>();
		rows.add(0);
		rows.add(1);

		row.setE(rows);

		List<RowType> tmp = new ArrayList<RowType>();
		tmp.add(row);



		row = new RowType();
		rows = new ArrayList<Integer>();
		rows.add(1);
		rows.add(1);

		row.setE(rows);
		tmp.add(row);

		value1.setRow(tmp);

		efg.setEventGraph(value1);



		String outputDir = "test";

		coverage.generate(efg, outputDir, nMaxNumber);
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
