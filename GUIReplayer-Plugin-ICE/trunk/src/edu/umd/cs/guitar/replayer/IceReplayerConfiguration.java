package edu.umd.cs.guitar.replayer;

import org.kohsuke.args4j.Option;

import edu.umd.cs.guitar.util.Util;

public class IceReplayerConfiguration extends GReplayerConfiguration {

    @Option(name = "-cf", usage = "Configure file for the gui recorder to recognize the terminal widgets", aliases = "--configure-file")
    public static String CONFIG_FILE = "configuration.xml";

    @Option(name = "-g", usage = "<REQUIRED> GUI file path", aliases = "--gui-file")
    public static String GUI_FILE = null;

    @Option(name = "-e", usage = "<REQUIRED> EFG file path", aliases = "--efg-file")
    public static String EFG_FILE = null;

    @Option(name = "-t", usage = "<REQUIRED> testcase file path", aliases = "--test-case-file")
    public static String TESTCASE = null;

    @Option(name = "-gs", usage = "gui state file path", aliases = "--gui-state")
    public static String GUI_STATE_FILE = "GUITAR-Default.STA";

    @Option(name = "-l", usage = "log file name ", aliases = "--log-file")
    public static String LOG_FILE = Util.getTimeStamp() + ".log";;

    @Option(name = "-i", usage = "initial waiting time for the application to get stablized before being ripped", aliases = "--wait-time")
    public static int INITIAL_WAITING_TIME = 0;

    @Option(name = "-d", usage = "step delay time", aliases = "--delay")
    public static int DELAY = 0;

    @Option(name = "-to", usage = "testcase timeout", aliases = "--testcase-timeout")
    public static int TESTCASE_TIMEOUT = 30000;

    @Option(name = "-so", usage = "test steptimeout", aliases = "--teststep-timeout")
    public static int TESTSTEP_TIMEOUT = 4000;

    @Option(name = "-a", usage = "arguments for the Application Under Test, separated by ';' ", aliases = "--arguments")
    public static String ARGUMENT_LIST;

    @Option(name = "-p", usage = "Pause after each step", aliases = "--pause")
    public static boolean PAUSE = false;

    @Option(name = "-m", usage = "Monitor plugins to use (comma delimited). Include full package", aliases = "--monitors")
    public static String PLUGINS_LIST = "";

    @Option(name = "-c", usage = "Ice Configuration File", aliases = "--config")
    public static String ICE_CONFIG = "replayer_ice.cfg";
}
