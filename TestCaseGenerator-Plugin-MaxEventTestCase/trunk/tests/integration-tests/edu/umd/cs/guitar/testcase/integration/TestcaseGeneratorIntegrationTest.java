package edu.umd.cs.guitar.testcase.tests.integration;

import junit.framework.TestCase;

/**
 * Test.
 *
 * @author Sam Huang
 */
public class TestcaseGeneratorIntegrationTest extends TestCase {

  public void setUp() {
  }

  public void tearDown() {
  }

  public void testDummy() {
    assertEquals(2,2);
  }

  /** There's not much to the TestcaseGeneratorConfiguration class, it's all
   * static fields with one trivial boolean class function (which always
   * returns true).  We override the settings of this file in order to control
   * the desired behavior of the classes under test. */
}
