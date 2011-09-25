package edu.umd.cs.guitar.ripper;


import org.kohsuke.args4j.Option;

import edu.umd.cs.guitar.util.Util;

;

/**
 * Class contains the runtime configurations of JFC GUITAR Ripper
 * 
 * <p>
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class IphRipperConfiguration extends GRipperConfiguration {
	// GUITAR server parameters
	@Option(name = "-p", usage = "destination server port", aliases = "--server-port")
	static public String PORT;
	@Option(name = "-sh", usage = "destination server host", aliases = "--server-host")
	static public String SERVER_HOST;
	
    // GUITAR runtime parameters
    @Option(name = "-g", usage = "destination GUI file path", aliases = "--gui-file")
    static public String GUI_FILE = "GUITAR-Default.GUI";

    @Option(name = "-l", usage = "log file name ", aliases = "--log-file")
    static public String LOG_FILE = Util.getTimeStamp() + ".log";


    @Option(name = "-i", usage = "initial waiting time for the application to get stablized before being ripped", aliases = "--initial-wait")
    static public Integer INITIAL_WAITING_TIME = 500;

    // Application Under Test
    //@Option(name = "-c", usage = "<REQUIRED> main class name for the Application Under Test ", aliases = "--main-class", required = true)
    @Option(name = "-c", usage = "<REQUIRED> main class name for the Application Under Test ", aliases = "--main-class")
    static public String MAIN_CLASS = null;

    @Option(name = "-a", usage = "arguments for the Application Under Test, separated by a colon (:) ", aliases = "--arguments")
    static public String ARGUMENT_LIST;

    @Option(name = "-u", usage = "URLs for the Application Under Test, separated by a colon (:) ", aliases = "--urls")
    static public String URL_LIST;

    @Option(name = "-j", usage = "Java Virtual Machine options for the Application Under Test", aliases = "--jvm-options")
    static public String JVM_OPTIONS;

    @Option(name = "-cf", usage = "configure file for the ripper defining terminal, ignored components and ignored windows", aliases = "--configure-file")
    public static String CONFIG_FILE = //"resources" + File.separator + "config"
           // + File.separator + 
            "configuration.xml";
    
    @Option(name = "-ce", usage = "customized event list (usually aut-specific events)", aliases = "--event-list")
    public static String CUSTOMIZED_EVENT_LIST = null;
    
    @Option(name = "-jar", usage = "Automatically looking for the main class name in jar file specified b-c")
    public static boolean USE_JAR = false;
    
}
