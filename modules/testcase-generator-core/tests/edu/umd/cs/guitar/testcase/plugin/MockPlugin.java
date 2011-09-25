package edu.umd.cs.guitar.testcase.plugin;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.EventGraphType;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;
import edu.umd.cs.guitar.util.GUITARLog;

public class MockPlugin extends TCPlugin {

	public MockPlugin() {
	
	}

	@Override
	public boolean isValidArgs() {
		return true;
	}
	
	/**
	 * Simple generate method which outputs testcases consisting of initial events.
	 */
	@Override
	public void generate(EFG efg, String outputDir, int nMaxNumber) {
		new File(outputDir).mkdir();
		
		this.efg = efg;
		initialize();
		
		for(EventType initial : initialEvents) {
			LinkedList<EventType> path = new LinkedList<EventType>();
			path.add(initial);
			writeToFile(TestCaseGeneratorConfiguration.OUTPUT_DIR + File.separator + TEST_NAME_PREFIX
					+ "_" + initial.getEventId() + TEST_NAME_SUFIX, path);
		}
	}

}
