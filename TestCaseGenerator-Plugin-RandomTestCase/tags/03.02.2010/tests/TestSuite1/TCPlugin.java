package edu.umd.cs.guitar.testcase.plugin;

import java.util.ArrayList;
import java.util.LinkedList;

import edu.umd.cs.guitar.model.data.EventGraphType;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.model.data.EFG;

/**
* Mock object.  Returns constant values that are set by test cases.
* Provides a call back to capture output from the RandomTestCase class.
*/
public abstract class TCPlugin {

    final String TEST_NAME_PREFIX = "t";
    final String TEST_NAME_SUFIX = ".tst";

    ArrayList<EventType> initialEvents = null;

    /**
    * Explicitly overridden by RandomTestCase
    */
    abstract public boolean isValidArgs();

    /**
    * Explicitly overridden by RandomTestCase
    */
    abstract public void generate(EFG efg, String outputDir, int nMaxNumber);

    /**
    * Used to pass output back to test cases
    */
    public ArrayList<LinkedList<EventType>> results = null;

    /**
    * Used to pass output back to test cases
    */
    public ArrayList<String> names = null;

    /**
    * Captures the output of the RandomTestCase class in a place that is directly
    * accessable to the test cases.
    */
    void writeToFile(String tCName, LinkedList<EventType> path) {
        names.add(tCName);
        results.add(path);
    }
}
