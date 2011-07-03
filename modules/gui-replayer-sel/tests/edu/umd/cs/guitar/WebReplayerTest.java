package edu.umd.cs.guitar;

import java.io.File;
import junit.framework.*;
import edu.umd.cs.guitar.replayer.*;
import edu.umd.cs.guitar.ripper.WebRipperConfiguration;
import edu.umd.cs.guitar.ripper.WebRipperMain;
import edu.umd.cs.guitar.graph.GUIStructure2GraphConverter;
import edu.umd.cs.guitar.testcase.plugin.SequenceLengthCoverage;
import edu.umd.cs.guitar.testcase.plugin.SequenceLengthCoverageConfiguration;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;
import java.io.File;
import java.io.FilenameFilter;
import org.apache.commons.cli.ParseException;

public class WebReplayerTest extends TestCase {
	
	public void test_execute1(){
		Boolean exceptionCaught = false;
		GReplayerConfiguration config = new GReplayerConfiguration();
		WebReplayerMain testReplayer = new WebReplayerMain(config);
		
		//catch exception
		try{
			testReplayer.execute();
		}catch (Exception e) {
			exceptionCaught=true;
		}
		//assert
		assertTrue(exceptionCaught);
	}
	
	public void test_w3school() throws Exception{
		//Executes the Ripper
		String[] args = new String[]{"--website-url","http://www.w3schools.com/html/tryit.asp?filename=tryhtml_form_radio", "-w", "1", "-d", "1","--gui-file","w3.GUI"};
		WebRipperConfiguration wrc = new WebRipperConfiguration();
		try {
			wrc.parseArguments(args);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		WebRipperMain wrm = new WebRipperMain(wrc);
		wrm.execute();
		
		args = new String[]{"EFGConverter", "w3.GUI", "w3EFG"};
		GUIStructure2GraphConverter guiStructure = new GUIStructure2GraphConverter();
		guiStructure.main(args);
		
		SequenceLengthCoverage testGenerator = new SequenceLengthCoverage();
		SequenceLengthCoverageConfiguration config = ((SequenceLengthCoverageConfiguration)testGenerator.getConfiguration());
		config.OUTPUT_DIR = "w3TestCases";
		EFG efg = (EFG) IO.readObjFromFile("../../GUIReplayer-Plugin-Web/tests/w3EFG", EFG.class);
		config.LENGTH = 3;
		testGenerator.generate(efg,"w3TestCases",3);
		
		//Find testcase names
		File dir = new File("../../GUIReplayer-Plugin-Web/tests/w3TestCases/");
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
			newConfig.WEBSITE_URL = "http://www.w3schools.com/html/tryit.asp?filename=tryhtml_form_radio";
			newConfig.GUI_FILE = "../../GUIReplayer-Plugin-Web/tests/w3.GUI";
			newConfig.EFG_FILE = "../../GUIReplayer-Plugin-Web/tests/w3EFG";
			newConfig.TESTCASE = "../../GUIReplayer-Plugin-Web/tests/w3TestCases/"+tst;
			
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

