package edu.umd.cs.guitar.replayer;
import org.kohsuke.args4j.CmdLineException;
import java.util.*;
import java.io.File;
import junit.framework.*;

public class JFCReplayerTest extends TestCase{
	
	//Test #1: throwing CmdLineException
	
	public void test_execute1(){
		Exception cmdLineExc = null;
		Boolean exceptionCaught = false;
		JFCReplayerConfiguration incompleteConfig = new JFCReplayerConfiguration();
		JFCReplayer testReplayer = new JFCReplayer(incompleteConfig);
		
		//catch exception
		try{
			testReplayer.execute();
		}catch (CmdLineException e) {
			cmdLineExc = e;
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
		JFCReplayerConfiguration.CONFIG_FILE = "JFCReplayer"+File.separator+"tests"+File.separator+"inputs"+File.separator
			+"resources"+File.separator+"config"+File.separator+"configuration.xml";
		JFCReplayerConfiguration.MAIN_CLASS = "checkboxesDemo";
		JFCReplayerConfiguration.GUI_FILE = "JFCReplayer"+File.separator
			+"tests"+File.separator+"inputs"+File.separator+"checkboxesDemo"+File.separator+"checkboxesDemo.GUI.xml";
		JFCReplayerConfiguration.EFG_FILE = "JFCReplayer"+File.separator
			+"tests"+File.separator+"inputs"+File.separator+"checkboxesDemo"+File.separator+"checkboxesDemo.EFG.xml";
		JFCReplayerConfiguration.TESTCASE = "JFCReplayer"+File.separator+"tests"+File.separator+"inputs"+File.separator
    		+"checkboxesDemo"+File.separator+"testcases"+File.separator+"t1.tst";

		JFCReplayer replayerTest = new JFCReplayer(newConfig);
		Exception cmdLineExc = null;
		
		try{
			replayerTest.execute();;
		}catch(CmdLineException e){
			cmdLineExc = e;
            System.err.println(e.getMessage());
            System.err.println();
            System.err.println("Usage: java [JVM options] "+ JFCReplayerMain.class.getName() + " [Relayer options] \n");
            System.err.println("where [Replayer options] include:");
            System.err.println();
        }
		assertNull(cmdLineExc);
	}
	
	public void test_execute3(){
		JFCReplayerConfiguration newConfig = new JFCReplayerConfiguration();
		
		
		//Need to set up:TESTCASE, GUI_FILE, EFG_FILE, MAIN_CLASS
		JFCReplayerConfiguration.CONFIG_FILE = "JFCReplayer"+File.separator+"tests"+File.separator+"inputs"+File.separator
			+"resources"+File.separator+"config"+File.separator+"configuration.xml";
		JFCReplayerConfiguration.MAIN_CLASS = "checkboxesDemo";
		JFCReplayerConfiguration.GUI_FILE = "JFCReplayer"+File.separator
			+"tests"+File.separator+"inputs"+File.separator+"checkboxesDemo"+File.separator+"checkboxesDemo.GUI.xml";
		JFCReplayerConfiguration.EFG_FILE = "JFCReplayer"+File.separator
			+"tests"+File.separator+"inputs"+File.separator+"checkboxesDemo"+File.separator+"checkboxesDemo.EFG.xml";
		JFCReplayerConfiguration.TESTCASE=null;
		
		JFCReplayer replayerTest = new JFCReplayer(newConfig);
		Exception cmdLineExc = null;
		
		try{
			replayerTest.execute();;
		}catch(CmdLineException e){
			cmdLineExc = e;
            System.err.println(e.getMessage());
            System.err.println();
            System.err.println("Usage: java [JVM options] "+ JFCReplayerMain.class.getName() + " [Relayer options] \n");
            System.err.println("where [Replayer options] include:");
            System.err.println();
        }
		assertNotNull(cmdLineExc);
	}
	
	public void test_execute4(){
		JFCReplayerConfiguration newConfig = new JFCReplayerConfiguration();
		
		
		//Need to set up:TESTCASE, GUI_FILE, EFG_FILE, MAIN_CLASS
		JFCReplayerConfiguration.CONFIG_FILE = "JFCReplayer"+File.separator+"tests"+File.separator+"inputs"+File.separator
			+"resources"+File.separator+"config"+File.separator+"configuration.xml";
		JFCReplayerConfiguration.MAIN_CLASS = "checkboxesDemo";
		JFCReplayerConfiguration.GUI_FILE = "JFCReplayer"+File.separator
			+"tests"+File.separator+"inputs"+File.separator+"checkboxesDemo"+File.separator+"checkboxesDemo.GUI.xml";
		JFCReplayerConfiguration.EFG_FILE = "JFCReplayer"+File.separator
			+"tests"+File.separator+"inputs"+File.separator+"checkboxesDemo"+File.separator+"checkboxesDemo.EFG.xml";
		JFCReplayerConfiguration.HELP=true;
		
		JFCReplayer replayerTest = new JFCReplayer(newConfig);
		Exception cmdLineExc = null;
		
		try{
			replayerTest.execute();;
		}catch(CmdLineException e){
			cmdLineExc = e;
            System.err.println(e.getMessage());
            System.err.println();
            System.err.println("Usage: java [JVM options] "+ JFCReplayerMain.class.getName() + " [Relayer options] \n");
            System.err.println("where [Replayer options] include:");
            System.err.println();
        }
		assertNotNull(cmdLineExc);
	}
	
}
