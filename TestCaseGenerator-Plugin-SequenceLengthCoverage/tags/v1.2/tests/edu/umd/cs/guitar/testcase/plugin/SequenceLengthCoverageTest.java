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
import edu.umd.cs.guitar.testcase.plugin.SequenceLengthCoverage;
import edu.umd.cs.guitar.model.data.EventGraphType;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.model.data.EventsType;
import edu.umd.cs.guitar.model.data.RowType;
import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.model.data.TestCase;
import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;

/**
 * This test class tests the SequenceLengthCoverage plugin. Click on each test's name to see more details about the test.
 * <br/> <br/>
 * Overall, I was able to achieve 57% (26/46) line coverage and 45% (9/20) branch coverage. The line and branch coverage do not reach close
 * to 100% because the SequenceLengthCoverage class has 2 private methods:debug (9 lines, 4 branches) and printGraph (11 lines, 6 branches)
 *  that are never called from anywhere in the class (dead close) thus
 * these set the upper bound on the maximum achievable line and branch coverage. Other than these 2 dead methods, 
 * one branch in SequenceLengthCoverage at line number
 * 166 also affects the results. There is no feasible way to design a test case that would make the program take that false branch. All attempts have lead the program
 * to halt before reaching this piece of code thus making it impossible to design a test case to take that false branch. 
 * 
 * <br/> <br/>
 * The above mentioned exceptions have set an upper bound on the code coverage results.
 * 
 * @author Tandeep Sidhu
 *
 */
public class SequenceLengthCoverageTest {

	private static final String outputDir = "./SequenceLengthCoverage/tests/output";

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
		SequenceLengthCoverage coverage = new SequenceLengthCoverage();

		Assert.assertTrue(coverage.isValidArgs() == false);

	}

	/**
	 * Calling isValidArgs() after setting the length configuration in TestCaseGeneratorConfiguration should return true
	 */
	
	public void testIsValidArgs2() {
		SequenceLengthCoverage coverage = new SequenceLengthCoverage();
		TestCaseGeneratorConfiguration.LENGTH = 3;


		Assert.assertTrue(coverage.isValidArgs() == true);

	}



	/**
	 * Generating test case with length 1. Since there are only two events in the input EFG,
	 * there should be just 2 test cases generated, each with just one event.
	 * 
	 * <br/> <br/>
	 * To confirm the output, I read the generated test cases files from the output directory
	 * and confirm that the generated test case files have the correct values.
	 */
	
	public void testGenerate() {
		TestCaseGeneratorConfiguration.LENGTH = 1;
		
		//run test
		runTestWithTwoEvents();

		//check output
		File dir = new File(outputDir);

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".tst");
			}
		}; 

		File[] listFiles = dir.listFiles(filter);

		Assert.assertTrue(listFiles.length == 2);

		TestCase tc = (TestCase) readObjFromFile(outputDir + "/t0.tst",
				TestCase.class);

		Assert.assertTrue(tc.getStep().size() == 1);

		for (StepType type : tc.getStep()) {
			Assert.assertTrue(type.getEventId().equals("e0"));
		}

		tc = (TestCase) readObjFromFile(outputDir + "/t1.tst",
				TestCase.class);

		Assert.assertTrue(tc.getStep().size() == 1);

		for (StepType type : tc.getStep()) {
			Assert.assertTrue(type.getEventId().equals("e4"));
		}

		 
	}

	/**
	 * Generating test case with length 2. Since there are only two events in the input EFG,
	 * and they are linked like this (e4 -> e4, e4 -> e0). Therefore, there should be just 2 test cases 
	 * generated each with just 2 steps. First
	 * test case should have 2 steps (e4, e0) and and second should have (e4, e4)
	 * 
	 * <br/> <br/>
	 * To confirm the output, I read the generated test cases files from the output directory
	 * and confirm that the generated test case files have the correct values.
	 */
	
	public void testGenerate1() {
		TestCaseGeneratorConfiguration.LENGTH = 2;

		runTestWithTwoEvents();

		File dir = new File(outputDir);

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".tst");
			}
		}; 

		File[] listFiles = dir.listFiles(filter);


		Assert.assertTrue(listFiles.length == 2);


		TestCase tc = (TestCase) readObjFromFile(outputDir + "/t0.tst",
				TestCase.class);

		Assert.assertTrue(tc.getStep().size() == 2);

		Assert.assertTrue(tc.getStep().get(0).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(1).getEventId().equals("e0"));


		tc = (TestCase) readObjFromFile(outputDir + "/t1.tst",
				TestCase.class);

		Assert.assertTrue(tc.getStep().size() == 2);

		Assert.assertTrue(tc.getStep().get(0).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(1).getEventId().equals("e4"));



	}
	
	
	/**
	 * Generating test case with length 4. Since there are only two events in the input EFG,
	 * and they are linked like this (e4 -> e4, e4 -> e0). Therefore, there should be just 2 test cases 
	 * generated each with just 4 steps. First
	 * test case should have 4 steps (e4, e0, e4, e4) and second should have 4 steps as well (e4, e4, e4, e4)
	 * 
	 * <br/> <br/>
	 * To confirm the output, I read the generated test cases files from the output directory
	 * and confirm that the generated test case files have the correct values.
	 */
	
	public void testGenerate2() {
		TestCaseGeneratorConfiguration.LENGTH = 4;

		runTestWithTwoEvents();
		
		File dir = new File(outputDir);

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".tst");
			}
		}; 

		File[] listFiles = dir.listFiles(filter);
		
		Assert.assertTrue(listFiles.length == 2);


		TestCase tc = (TestCase) readObjFromFile(outputDir + "/t0.tst",
				TestCase.class);

		Assert.assertTrue(tc.getStep().size() == 4);

		Assert.assertTrue(tc.getStep().get(0).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(1).getEventId().equals("e0"));
		Assert.assertTrue(tc.getStep().get(2).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(3).getEventId().equals("e4"));


		tc = (TestCase) readObjFromFile(outputDir + "/t1.tst",
				TestCase.class);

		Assert.assertTrue(tc.getStep().size() == 4);

		Assert.assertTrue(tc.getStep().get(0).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(1).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(2).getEventId().equals("e4"));
		Assert.assertTrue(tc.getStep().get(3).getEventId().equals("e4"));


	}

	private void runTestWithTwoEvents() {
		TestCaseGeneratorConfiguration.OUTPUT_DIR = outputDir;

		SequenceLengthCoverage coverage = new SequenceLengthCoverage();

		EFG efg = new EFG();

		EventsType value = new EventsType();
		List<EventType> list = new ArrayList<EventType>();

		EventType type = new EventType();
		type.setAction("edu.umd.cs.guitar.event.JFCSelectionHandler");
		type.setEventId("e0");
		type.setInitial(true);
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
		rows.add(2);
		rows.add(2);
		rows.add(2);
		rows.add(2);

		row.setE(rows);

		List<RowType> tmp = new ArrayList<RowType>();
		tmp.add(row);



		row = new RowType();
		rows = new ArrayList<Integer>();
		rows.add(0);
		rows.add(1);
		rows.add(2);
		rows.add(2);
		rows.add(2);
		rows.add(2);

		row.setE(rows);
		tmp.add(row);

		value1.setRow(tmp);

		efg.setEventGraph(value1);



		String outputDir = "test";
		int nMaxNumber = 100;

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
