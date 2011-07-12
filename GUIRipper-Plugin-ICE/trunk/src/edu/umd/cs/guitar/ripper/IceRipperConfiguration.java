package edu.umd.cs.guitar.ripper;

import java.io.File;

import org.kohsuke.args4j.Option;

import edu.umd.cs.guitar.util.Util;


/**
 * Class contains the runtime configurations of WG GUITAR Ripper
 * 
 * <p>
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class IceRipperConfiguration extends GRipperConfiguration {

    // GUITAR runtime parameters
    @Option(name = "-g", usage = "destination GUI file path", aliases = "--gui-file")
    static public String GUI_FILE = "GUITAR-Default.GUI";

    @Option(name = "-l", usage = "log file name ", aliases = "--log-file")
    static public String LOG_FILE = Util.getTimeStamp() + ".log";

    @Option(name = "-ow", usage = "log file name ", aliases = "--open-win-file")
    static public String LOG_WIDGET_FILE = "log_widget.xml";

    @Option(name = "-i", usage = "initial waiting time for the application to get stablized before being ripped", aliases = "--initial-wait")
    static public Integer INITIAL_WAITING_TIME = 0;
    
    @Option(name = "-a", usage = "arguments for the Application Under Test, separated by ';' ", aliases = "--arguments")
    static public String ARGUMENT_LIST;

    @Option(name = "-u", usage = "URLs for the Application Under Test, separated by ';' ", aliases = "--urls")
    static public String URL_LIST;

    @Option(name = "-j", usage = "Java Virtual Machine options for the Application Under Test", aliases = "--jvm-options")
    static public String JVM_OPTIONS;

    @Option(name = "-cf", usage = "Configure file for the ripper defining terminal, ignored components and ignored windows", aliases = "--configure-file")
    public static String CONFIG_FILE = //"resources" + File.separator + "config"
           // + File.separator + 
            "configuration.xml";

    @Option(name = "-c", usage = "Ice Configuration File", aliases = "--config")
    public static String ICE_CONFIG = "ripper_ice.cfg";
}
