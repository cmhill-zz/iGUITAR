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

public class AmazonSystemTest extends TestCase {
	static String url = "http://www.amazon.com/s/ref=IPTV_allIPTVs?ie=UTF8&rh=n%3A979929011%2Cp_n_feature_five_browse-bin%3A2443316011&pf_rd_m=ATVPDKIKX0DER&pf_rd_s=left-1&pf_rd_r=1QCBK39TW1CRXZPDWKDB&pf_rd_t=101&pf_rd_p=1294528342&pf_rd_i=2343832011";
	static String width = "2";
	static String depth = "2";
	static String gui = "amazonTestGui.GUI";
	static String efg = "amazonTestEFG";
	static String testCase_dir = "amazontestCases";
	static int testCaseLength = 3;
	static int numTestCases = 1;
	
	public void test_amazonRipper() throws Exception {
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
	
	public void test_amazonEFG() throws Exception {
		//Create the EFG
		String[] args = new String[]{"EFGConverter", gui, efg};
		GUIStructure2GraphConverter guiStructure = new GUIStructure2GraphConverter();
		guiStructure.main(args);
	}
	
	public void test_amazonTestCase() throws Exception {
		//Create a test case
		SequenceLengthCoverage testGenerator = new SequenceLengthCoverage();
		SequenceLengthCoverageConfiguration config = ((SequenceLengthCoverageConfiguration)testGenerator.getConfiguration());
		config.OUTPUT_DIR = testCase_dir;
		String efgDir = "../../GUIReplayer-Plugin-Web/tests/" + efg;
		EFG efg = (EFG) IO.readObjFromFile(efgDir, EFG.class);
		config.LENGTH = testCaseLength;
		testGenerator.generate(efg,testCase_dir,numTestCases);
	}
	
	public void test_amazonReplayer() throws Exception {
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
