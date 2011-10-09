package edu.umd.cs.guitar.replayer;

import org.kohsuke.args4j.Option;

import edu.umd.cs.guitar.util.Util;

public class IphReplayerConfiguration extends GReplayerConfiguration {

	@Option(name = "-cf", usage = "Configure file for the gui recorder to recognize the terminal widgets", aliases = "--configure-file")
	public static String CONFIG_FILE = // "resources" + File.separator +
	// "config"
	// + File.separator +
	"configuration.xml";

	// GUITAR runtime parameters
	@Option(name = "-g", usage = "<REQUIRED> GUI file path", aliases = "--gui-file")
	static String GUI_FILE = null;

	@Option(name = "-e", usage = "<REQUIRED> EFG file path", aliases = "--efg-file")
	static String EFG_FILE = null;

	@Option(name = "-t", usage = "<REQUIRED> testcase file path", aliases = "--testcase-file")
	static String TESTCASE = null;

	@Option(name = "-gs", usage = "gui state file path", aliases = "--gui-state")
	static String GUI_STATE_FILE = "GUITAR-Default.STA";

	@Option(name = "-l", usage = "log file name ", aliases = "--log-file")
	static String LOG_FILE = Util.getTimeStamp() + ".log";;

	@Option(name = "-i", usage = "initial waiting time for the application to get stablized before being ripped", aliases = "--wait-time")
	static int INITIAL_WAITING_TIME = 500;

	@Option(name = "-d", usage = "step delay time", aliases = "--delay")
	static int DELAY = 0;

	@Option(name = "-to", usage = "testcase timeout", aliases = "--testcase-timeout")
	static int TESTCASE_TIMEOUT = 30000;

	@Option(name = "-so", usage = "test steptimeout", aliases = "--teststep-timeout")
	static int TESTSTEP_TIMEOUT = 4000;

	// Application Under Test
	//@Option(name = "-c", usage = "<REQUIRED> main class name for the Application Under Test ", aliases = "--main-class")
	//static String MAIN_CLASS = null;

	@Option(name = "-a", usage = "arguments for the Application Under Test, separated by ':' ", aliases = "--arguments")
	static String ARGUMENT_LIST;

	@Option(name = "-u", usage = "URLs for the Application Under Test, separated by ':' ", aliases = "--urls")
	static public String URL_LIST;

	@Option(name = "-j", usage = "Java Virtual Machine options for the Application Under Test", aliases = "--jvm-options")
	static String JVM_OPTIONS;

	@Option(name = "-p", usage = "Pause after each step", aliases = "--pause")
	static boolean PAUSE = false;

	@Option(name = "-r", usage = "Compare string using regular expression", aliases = "--regular-expression")
	static boolean USE_REX = false;
	
	// GUITAR server parameters
	@Option(name = "-po", usage = "destination server port", aliases = "--server-port")
	static public String PORT;
	@Option(name = "-sh", usage = "destination server host", aliases = "--server-host")
	static public String SERVER_HOST;
	
}
