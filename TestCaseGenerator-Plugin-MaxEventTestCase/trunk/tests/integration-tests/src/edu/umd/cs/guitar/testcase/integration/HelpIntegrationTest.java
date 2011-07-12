package edu.umd.cs.guitar.testcase.tests.integration;

import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.model.data.TestCase;
import edu.umd.cs.guitar.testcase.TestcaseGenerator;

import java.util.HashSet;
import java.util.Set;

/**
 * An integration test case class for the TestCaseGenerator tool, checking
 * explicitly for the help dialog.  This must be done separately because
 * currently the help dialog issues a System.exit() call, which is uncatchable.
 * This uses a mock implementation of several classes, of special importance is
 * the mock implementation of IO, which has some added static methods that are
 * called here for ease of checking output.
 *
 * @author Sam Huang
 */
public class HelpIntegrationTest extends junit.framework.TestCase {

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
   * Tests the help method of TestCaseGenerator.
   */
  public void testHelp() {
    TestcaseGenerator.main(toArray("--help"));
  }

  protected String[] toArray(String... args) {
    return args;
  }
}
