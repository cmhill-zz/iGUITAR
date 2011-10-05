package edu.umd.cs.guitar.ripper;


import org.kohsuke.args4j.Option;

import edu.umd.cs.guitar.util.Util;

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
 
    @Option(name = "-a", usage = "arguments for the Application Under Test, separated by a colon (:) ", aliases = "--arguments")
    static public String ARGUMENT_LIST;

    @Option(name = "-cf", usage = "configure file for the ripper defining terminal, ignored components and ignored windows", aliases = "--configure-file")
    public static String CONFIG_FILE = //"resources" + File.separator + "config"
           // + File.separator + 
            "configuration.xml";
    
    @Option(name = "-ce", usage = "customized event list (usually aut-specific events)", aliases = "--event-list")
    public static String CUSTOMIZED_EVENT_LIST = null;
    
}
