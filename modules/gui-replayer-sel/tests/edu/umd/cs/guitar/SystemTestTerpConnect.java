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

public class SystemTestTerpConnect extends TestCase {
	public void test_Terp_Ripper() throws Exception {
		//Executes the Ripper
		String[] args = new String[]{"--website-url", "http://terpconnect.umd.edu/~leylan/links.html", "-w", "3", "-d", "3","--gui-file","terpConnect.GUI"};
		WebRipperConfiguration wrc = new WebRipperConfiguration();
		try {
			wrc.parseArguments(args);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		WebRipperMain wrm = new WebRipperMain(wrc);
		wrm.execute();
	}
	
	public void test_Terp_EFG() throws Exception {
		//Create the EFG
		String[] args = new String[]{"EFGConverter", "terpConnect.GUI", "terpConnectEFG"};
		GUIStructure2GraphConverter guiStructure = new GUIStructure2GraphConverter();
		guiStructure.main(args);
	}
	
	public void test_Terp_TestCase() throws Exception {
		//Create a test case
		SequenceLengthCoverage testGenerator = new SequenceLengthCoverage();
		SequenceLengthCoverageConfiguration config = ((SequenceLengthCoverageConfiguration)testGenerator.getConfiguration());
		config.OUTPUT_DIR = "terpConnecttestCases";
		EFG efg = (EFG) IO.readObjFromFile("../../GUIReplayer-Plugin-Web/tests/terpConnectEFG", EFG.class);
		config.LENGTH = 3;
		testGenerator.generate(efg,"terpConnecttestCases",1);
	}
	
	public void test_Terp_Replayer() throws Exception {
		//Find testcase names
		File dir = new File("../../GUIReplayer-Plugin-Web/tests/terpConnecttestCases/");
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
			newConfig.WEBSITE_URL = "http://terpconnect.umd.edu/~leylan/links.html";
			newConfig.GUI_FILE = "../../GUIReplayer-Plugin-Web/tests/terpConnect.GUI";
			newConfig.EFG_FILE = "../../GUIReplayer-Plugin-Web/tests/terpConnectEFG";
			newConfig.TESTCASE = "../../GUIReplayer-Plugin-Web/tests/terpConnecttestCases/"+tst;
			
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
