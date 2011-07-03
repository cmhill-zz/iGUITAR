package edu.umd.cs.guitar.ripper;

import java.io.IOException;

import edu.umd.cs.guitar.graph.GUIStructure2GraphConverter;
import edu.umd.cs.guitar.testcase.plugin.SequenceLengthCoverage;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;

import junit.framework.TestCase;

/**
 * Test Cases for google
 * @author brian
 *
 */

public class testRipperIntegration extends TestCase {

	public void testRipperToEFG() throws Exception{
		String[] args = new String[]{"--website-url", "http://www.google.com", "-w", "1", "-d", "1","--gui-file","googleINTGUI.GUI"};
		WebRipperConfiguration wrc = new WebRipperConfiguration();
		wrc.parseArguments(args);
		WebRipperMain wrm = new WebRipperMain(wrc);
		wrm.execute();
		
		args = new String[] {"EFGConverter", "googleINTGUI.GUI", "googleEFG"};
		GUIStructure2GraphConverter.main(args);
		assertTrue(true);
	}
	
	public void testEFGToTestCases() throws Exception{
		SequenceLengthCoverage testGenerator = new SequenceLengthCoverage();
		TestCaseGeneratorConfiguration config = testGenerator.getConfiguration();
		config.OUTPUT_DIR = "testCases";
		EFG efg = (EFG) IO.readObjFromFile("../../GUIRipper-Plugin-Web/test/googleEFG", EFG.class);
		testGenerator.generate(efg,"testCases",1);
	}
}
