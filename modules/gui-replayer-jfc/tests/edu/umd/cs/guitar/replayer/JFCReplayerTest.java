package edu.umd.cs.guitar.replayer;
import org.kohsuke.args4j.CmdLineException;
import java.io.File;
import junit.framework.*;

public class JFCReplayerTest extends TestCase{
	
	//Test #1: throwing CmdLineException - these are wrong. check test 2.
	
	public void test_execute1(){
		Boolean exceptionCaught = false;
		JFCReplayerConfiguration incompleteConfig = new JFCReplayerConfiguration();
		JFCReplayer testReplayer = new JFCReplayer(incompleteConfig);
		
		//catch exception
		try{
			testReplayer.execute();
		}catch (CmdLineException e) {
			exceptionCaught=true;
		}
		//assert
		assertTrue(exceptionCaught);
	}
	
	//Test #2: correct input
	public void test_execute2()
	{
		JFCReplayerConfiguration newConfig = new JFCReplayerConfiguration();
		
		//Need to set up:TESTCASE, GUI_FILE, EFG_FILE, MAIN_CLASS
		JFCReplayerConfiguration.MAIN_CLASS = "checkboxesDemo";
		JFCReplayerConfiguration.CONFIG_FILE = "configuration.xml";
		JFCReplayerConfiguration.GUI_FILE = "checkboxesDemo.GUI.xml";
		JFCReplayerConfiguration.EFG_FILE = "checkboxesDemo.EFG.xml";
		

		JFCReplayerConfiguration.TESTCASE = "t_e7_e7.tst";
		
		JFCReplayer replayerTest = new JFCReplayer(newConfig);
		Exception cmdLineExc = null;
		
		try{
			replayerTest.execute();;
		}catch(CmdLineException e){
			cmdLineExc = e;
      }
		assertNull(cmdLineExc);
	}

	//Test #2: correct input-pause
	public void test_execute2_pause()
	{
		JFCReplayerConfiguration newConfig = new JFCReplayerConfiguration();
		
		//Need to set up:TESTCASE, GUI_FILE, EFG_FILE, MAIN_CLASS
		JFCReplayerConfiguration.MAIN_CLASS = "checkboxesDemo";
		JFCReplayerConfiguration.CONFIG_FILE = "configuration.xml";
		JFCReplayerConfiguration.GUI_FILE = "checkboxesDemo.GUI.xml";
		JFCReplayerConfiguration.EFG_FILE = "checkboxesDemo.EFG.xml";
		
		JFCReplayerConfiguration.TESTCASE = "t_e7_e7.tst";
		
		JFCReplayerConfiguration.PAUSE = true;
		
		JFCReplayer replayerTest = new JFCReplayer(newConfig);
		Exception cmdLineExc = null;
		
		try{
			replayerTest.execute();;
		}catch(CmdLineException e){
			cmdLineExc = e;
      }
		assertNull(cmdLineExc);
	}

	
	public void test_execute3(){
		JFCReplayerConfiguration newConfig = new JFCReplayerConfiguration();
		
		//Need to set up:TESTCASE, GUI_FILE, EFG_FILE, MAIN_CLASS
		JFCReplayerConfiguration.MAIN_CLASS = "checkboxesDemo";
		JFCReplayerConfiguration.CONFIG_FILE = "configuration.xml";
		JFCReplayerConfiguration.GUI_FILE = "checkboxesDemo.GUI.xml";
		JFCReplayerConfiguration.EFG_FILE = "checkboxesDemo.EFG.xml";
		
				JFCReplayerConfiguration.TESTCASE=null;

		JFCReplayer replayerTest = new JFCReplayer(newConfig);
		Exception cmdLineExc = null;
		
		try{
			replayerTest.execute();;
		}catch(CmdLineException e){
			cmdLineExc = e;
     }
		assertNotNull(cmdLineExc);
	}
	
	public void test_execute4(){
		JFCReplayerConfiguration newConfig = new JFCReplayerConfiguration();
		
		
		
		//Need to set up:TESTCASE, GUI_FILE, EFG_FILE, MAIN_CLASS
		JFCReplayerConfiguration.MAIN_CLASS = "checkboxesDemo";
		JFCReplayerConfiguration.CONFIG_FILE = "configuration.xml";
		JFCReplayerConfiguration.GUI_FILE = "checkboxesDemo.GUI.xml";
		JFCReplayerConfiguration.EFG_FILE = "checkboxesDemo.EFG.xml";
		
		JFCReplayerConfiguration.HELP=true;
		
		JFCReplayer replayerTest = new JFCReplayer(newConfig);
		Exception cmdLineExc = null;
		
		try{
			replayerTest.execute();;
		}catch(CmdLineException e){
			cmdLineExc = e;
      }
		assertNotNull(cmdLineExc);
	}
	
}
