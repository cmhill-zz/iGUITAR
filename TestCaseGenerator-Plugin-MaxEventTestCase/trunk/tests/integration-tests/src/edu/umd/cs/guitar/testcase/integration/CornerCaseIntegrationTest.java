package edu.umd.cs.guitar.testcase.tests.integration;

import edu.umd.cs.guitar.model.EFGConfiguration;
import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.model.data.TestCase;
import edu.umd.cs.guitar.testcase.TestcaseGenerator;

import java.util.HashSet;
import java.util.Set;

/**
 * An integration test case class for the TestCaseGenerator tool, checking
 * corner cases like error handling and poor combinations of inputs, etc,
 * partially for the purpose of getting good coverage.  This uses a mock
 * implementation of several classes, of special importance is the mock
 * implementation of IO, which has some added static methods that are called
 * here for ease of checking output.
 *
 * @author Sam Huang
 */
public class CornerCaseIntegrationTest extends junit.framework.TestCase {

  /* There's not much to the TestcaseGeneratorConfiguration class, it's all
   * static fields with one trivial boolean class function (which always
   * returns true).  We override the settings of this file in order to control
   * the desired behavior of the classes under test. */

  /**
   * Basic setup, clears/resets all global variables keeping track of things for us.
   */
  public void setUp() {
    // Reset the record that our mock IO object keeps of all objects requested
    // to be written to file.
    IO.clearOutputRecord();
  }

  /**
   * tearDown, does nothing currently.
   */
  public void tearDown() {
  }

  /**
   * Tests the base constructor for TestCaseGenerator.  This is for coverage
   * only, as all of the TestCaseGenerator methods are static; this test
   * *should* be defunct if a private constructor is added.
   */
  public void testBrokenConstructor() {
    new TestcaseGenerator();
  }

  /**
   * Tests TestcaseGenerator's ability to handle an unkown plugin (error handling).
   */
  public void testValidPlugin() {
    TestcaseGenerator.main(toArray("-e", EFGConfiguration.SINGLE_EVENT_EFG_FILE, "-p", "Non-existantPlugin", "-d", "."));
  }

  /**
   * Tests TestcaseGenerator's ability to handle a plugin with a private constructor (error handling).
   */
  public void testPrivateConstructorPlugin() {
    TestcaseGenerator.main(toArray("-e", EFGConfiguration.SINGLE_EVENT_EFG_FILE, "-p", "DummyPlugin", "-d", "."));
  }

  /**
   * Tests TestcaseGenerator's ability to handle a plugin which is an abstract class (error handling).
   */
  public void testAbstractPlugin() {
    TestcaseGenerator.main(toArray("-e", EFGConfiguration.SINGLE_EVENT_EFG_FILE, "-p", "TCPlugin", "-d", "."));
  }

  ///**
   //* Test to check behavior on SingleEvent toy example, length = 1.
   //*/
  //public void testSingeEvent1() {
    //singleEventTemplate(1);
  //}


  ///** Meta-test templates for a given configuration. */
  //protected void singleEventTemplate(Integer l) {
    //String[] eventIDs = new String[] {"e0"};
    //kCycleTemplate(EFGConfiguration.SINGLE_EVENT_EFG_FILE, eventIDs, l);
  //}

  ///**
   //* Template for testing an EFG that is a cycle with exactly one node marked 'initial.'
   //*
   //* @param eventIDs - the cycle of event IDs that a test case should iterate
   //*                   over (in order, and back to the beginning).
   //* @param l - the length of the test case to generate
   //*/
  //protected void kCycleTemplate(String fileName, String[] eventIDs, Integer l) {

    //TestcaseGenerator.main(toArray("-e", fileName, "-p", "SequenceLengthCoverage", "-d", ".", "-l", l.toString()));


    //// Create a set of proper names for the output test files
    //Set <String> targetFileNames = new HashSet <String> ();
    //targetFileNames.add("./t0.tst");

    //for(Object obj : IO.objs2fileNames.keySet()) {
      //if(obj.getClass() != TestCase.class) {
        //fail("failure: unexpected class type requested to be written to file: " + obj.getClass());
      //} else {
        //TestCase tc = (TestCase)obj;

        //// Check that the file name chosen by SequenceLengthCoverage matches
        //// what it should be.
        //String chosenFileName = IO.objs2fileNames.get(obj);
        //if(!targetFileNames.contains(chosenFileName))
          //fail();
        //targetFileNames.remove(chosenFileName);

        //// Check that the number of steps is proper.
        //assertEquals(l.intValue(), tc.getStep().size());

        //// First, prepare a nice output message in case of error.
        //String trace =  "";
        //trace += " expected:";
        //for(int q = 0; q < l; q++) {
          //trace += " " + eventIDs[q % 2];
        //}
        //trace += "\n observed:";
        //for(StepType st : tc.getStep()) {
          //trace += " " + st.getEventId();
        //}
        //trace += "\n";

        //// Check that each step of the test case matches the known truth, which
        //// is just a looping of e0 -> e1 -> e0 -> e1 -> ..., for the length l.

        //int counter = 0;
        //for(StepType st : tc.getStep()) {
          //String truth = eventIDs[counter % 2];
          //String msg = "Step " + counter + " of the test case does not match.  The entire sequences are:\n" + trace;
          //assertEquals(msg, truth, st.getEventId());
          //counter++;
        //}
      //}
    //}
    //// after all the  objects are gone through, we should have no more filenames left in targetFileNames
    //assertEquals(0, targetFileNames.size());
  //}


  /* Lazy... */
  protected String[] toArray(String... args) {
    return args;
  }
}
