package edu.umd.cs.guitar.testcase.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.EventGraphType;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * Dummy plugin that has a private constructor, will cause errors if attempted to be used.
 * 
 * @author Sam Huang
 */
public class DummyPlugin extends TCPlugin {

  /**
   * 
   */
  private DummyPlugin() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.umd.cs.guitar.testcase.plugin.TCPlugin#isValidArgs()
   */
  @Override
    public boolean isValidArgs() {
      return true;
    }

  /*
   * (non-Javadoc)
   * 
   * @see edu.umd.cs.guitar.testcase.plugin.TCPlugin#generate()
   */
  public void generate(EFG efg, String outputDir, int nMaxNumber) {
  }
}
