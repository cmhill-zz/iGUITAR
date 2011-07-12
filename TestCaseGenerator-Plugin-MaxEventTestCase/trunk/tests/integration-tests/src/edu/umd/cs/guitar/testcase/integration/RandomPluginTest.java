package edu.umd.cs.guitar.testcase.tests.integration;

import edu.umd.cs.guitar.model.EFGConfiguration;
import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.model.data.TestCase;
import edu.umd.cs.guitar.testcase.TestcaseGenerator;

import java.util.HashSet;
import java.util.Set;
import java.io.File;

/**
 * The integration test case class for the TestCaseGenerator tool, testing the
 * usage of <code>RandomTestCase</code>.  This uses a mock implementation of
 * several classes, of special importance is the mock implementation of IO,
 * which has some added static methods that are called here for ease of
 * checking output.  Because we are generating randomly, we use some simplified
 * EFGs to actually make the generation deterministic and therefore checkable.
 * We could in the future lift this restriction by performing a weaker
 * validation of "is it a valid path?" rather than "is it THE valid path" or
 * "does it generate valid test cases according to some expected distribution?"
 *
 * @author Sam Huang
 */
public class RandomPluginTest extends junit.framework.TestCase {

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
   * Test to check behavior on SingleEvent toy example, length = 1.
   */
  public void testSingeEvent1() {
    singleEventTemplate(1,1);
  }

  /**
   * Test to check behavior on SingleEvent toy example, length = 2.
   */
  public void testSingeEvent2() {
    singleEventTemplate(2, 1);
  }

  /**
   * Test to check behavior on SingleEvent toy example, length = 3.
   */
  public void testSingeEvent3() {
    singleEventTemplate(3, 1);
  }

  /**
   * Test to check behavior on SingleEvent toy example, length = 4.
   */
  public void testSingeEvent4() {
    singleEventTemplate(4, 1);
  }

  /**
   * Test to check behavior on SingleEvent toy example, length = 5.
   */
  public void testSingeEvent5() {
    singleEventTemplate(5, 1);
  }

  /**
   * Test to check behavior on TwoCycle toy example, length = 1.
   */
  public void testTwoCycle1() {
    twoCycleTemplate(1, 1);
  }

  /**
   * Test to check behavior on TwoCycle toy example, length = 2.
   */
  public void testTwoCycle2() {
    twoCycleTemplate(2, 1);
  }

  /**
   * Test to check behavior on TwoCycle toy example, length = 3.
   */
  public void testTwoCycle3() {
    twoCycleTemplate(3, 1);
  }

  /**
   * Test to check behavior on TwoCycle toy example, length = 4.
   */
  public void testTwoCycle4() {
    twoCycleTemplate(4, 1);
  }

  /**
   * Test to check behavior on TwoCycle toy example, length = 4.
   */
  public void testTwoCycle5() {
    twoCycleTemplate(5, 1);
  }

  /** Meta-test templates for a given configuration. */
  protected void singleEventTemplate(Integer l, Integer m) {
    String[] eventIDs = new String[] {"e0"};
    kCycleTemplate(EFGConfiguration.SINGLE_EVENT_EFG_FILE, eventIDs, l, m);
  }

  protected void twoCycleTemplate(Integer l, Integer m) {
    String[] eventIDs = new String[] {"e0", "e1"};
    kCycleTemplate(EFGConfiguration.TWO_CYCLE_EFG_FILE, eventIDs, l, m);
  }

  /**
   * Template for testing an EFG that is a cycle with exactly one node marked 'initial.'
   *
   * @param eventIDs - the cycle of event IDs that a test case should iterate
   *                   over (in order, and back to the beginning).
   * @param l - the length of the test case to generate
   */
  protected void kCycleTemplate(String fileName, String[] eventIDs, Integer l, Integer m) {

    TestcaseGenerator.main(toArray("-e", fileName, "-p", "RandomTestCase", "-d", ".", "-l", l.toString(), "-m", m.toString()));


    // Create a set of proper names for the output test files
    Set <String> targetFileNames = new HashSet <String> ();
    for(int q = 0; q < m; q++) {
      targetFileNames.add("." + File.separator + "t" + q + ".tst");
	  
    }
	
	//Iterator iter = targetFileNames.iterator();
	//while(iter.hasNext()){
	//System.out.println("IteratedElement="+iter.next());
	//}
	

    for(Object obj : IO.objs2fileNames.keySet()) {
      if(obj.getClass() != TestCase.class) {
        fail("failure: unexpected class type requested to be written to file: " + obj.getClass());
      } else {
        TestCase tc = (TestCase)obj;
		

        // Check that the file name chosen by SequenceLengthCoverage matches
        // what it should be.
        String chosenFileName = IO.objs2fileNames.get(obj);
		//System.out.println("ChoosenFileName="+chosenFileName);
        if(!targetFileNames.contains(chosenFileName))
          fail("Could not locate test name " + chosenFileName);
        targetFileNames.remove(chosenFileName);

        // Check that the number of steps is proper.
        assertEquals(l.intValue(), tc.getStep().size());

        // First, prepare a nice output message in case of error.
        String trace =  "";
        trace += " expected:";
        for(int q = 0; q < l; q++) {
          trace += " " + eventIDs[q % 2];
        }
        trace += "\n observed:";
        for(StepType st : tc.getStep()) {
          trace += " " + st.getEventId();
        }
        trace += "\n";

        // Check that each step of the test case matches the known truth, which
        // is just a looping of e0 -> e1 -> e0 -> e1 -> ..., for the length l.

        int counter = 0;
        for(StepType st : tc.getStep()) {
          String truth = eventIDs[counter % 2];
          String msg = "Step " + counter + " of the test case does not match.  The entire sequences are:\n" + trace;
          assertEquals(msg, truth, st.getEventId());
          counter++;
        }
      }
    }
    // after all the  objects are gone through, we should have no more filenames left in targetFileNames
    assertEquals(0, targetFileNames.size());
  }


  /* Lazy... */
  private String[] toArray(String... args) {
    return args;
  }
}
