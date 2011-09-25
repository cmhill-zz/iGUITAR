package edu.umd.cs.guitar;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.cli.ParseException;

import junit.framework.TestCase;
import edu.umd.cs.guitar.replayer.WebReplayerConfiguration;
import edu.umd.cs.guitar.replayer.WebReplayerMain;
import edu.umd.cs.guitar.ripper.WebRipperConfiguration;
import edu.umd.cs.guitar.ripper.WebRipperMain;
import edu.umd.cs.guitar.graph.GUIStructure2GraphConverter;
import edu.umd.cs.guitar.testcase.plugin.SequenceLengthCoverage;
import edu.umd.cs.guitar.testcase.plugin.SequenceLengthCoverageConfiguration;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;

public class SystemTest extends TestCase {
	static String url = "http://www.phand.net";
	static String width = "2";
	static String depth = "2";
	static String gui = "phandTestGui.GUI";
	static String efg = "pahndTestEFG";
	static String testCase_dir = "phandtestCases";
	static int testCaseLength = 3;
	static int numTestCases = 1;
	
	public void test_Ripper() throws Exception {
		//Executes the Ripper
		String[] args = new String[]{"--website-url", url, "-w", width, "-d", depth,"--gui-file",gui};
		WebRipperConfiguration wrc = new WebRipperConfiguration();
		try {
			wrc.parseArguments(args);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		WebRipperMain wrm = new WebRipperMain(wrc);
		wrm.execute();
	}
	
	public void test_EFG() throws Exception {
		//Create the EFG
		String[] args = new String[]{"EFGConverter", gui, efg};
		GUIStructure2GraphConverter guiStructure = new GUIStructure2GraphConverter();
		guiStructure.main(args);
	}
	
	public void test_TestCase() throws Exception {
		//Create a test case
		SequenceLengthCoverage testGenerator = new SequenceLengthCoverage();
		SequenceLengthCoverageConfiguration config = ((SequenceLengthCoverageConfiguration)testGenerator.getConfiguration());
		config.OUTPUT_DIR = testCase_dir;
		String efgDir = "../../GUIReplayer-Plugin-Web/tests/" + efg;
		EFG efg = (EFG) IO.readObjFromFile(efgDir, EFG.class);
		config.LENGTH = testCaseLength;
		testGenerator.generate(efg,testCase_dir,numTestCases);
	}
	
	public void test_Replayer() throws Exception {
		//Find testcase names
		File dir = new File("../../GUIReplayer-Plugin-Web/tests/" + testCase_dir + "/");
		FilenameFilter filter = new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return !name.startsWith(".");
		    }
		};
		String[] children = dir.list(filter);
		
		for(String tst : children) {
			System.out.println("Executing test " + tst);
			//Run the test case with the replayer
			WebReplayerConfiguration newConfig = new WebReplayerConfiguration();	
			newConfig.parseArguments(new String[]{});
			newConfig.WEBSITE_URL = url;
			newConfig.GUI_FILE = "../../GUIReplayer-Plugin-Web/tests/" + gui;
			newConfig.EFG_FILE = "../../GUIReplayer-Plugin-Web/tests/" + efg;
			newConfig.TESTCASE = "../../GUIReplayer-Plugin-Web/tests/"+testCase_dir+"/"+tst;
			
			WebReplayerMain replayerTest = new WebReplayerMain(newConfig);
			Exception exception = null;
			
			try{
				replayerTest.execute();
			}catch(Exception e){
				exception = e;
				System.out.println(exception.toString() + "\n" + exception.getMessage() + 
						"\n");
				e.printStackTrace();
			}
			
			assertNull(exception);
		}
	}
}
