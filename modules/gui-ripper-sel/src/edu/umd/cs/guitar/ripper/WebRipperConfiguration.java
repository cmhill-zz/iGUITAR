package edu.umd.cs.guitar.ripper;

import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.MissingOptionException;

/**
 * Class contains the runtime configurations of Web GUITAR Ripper
 * 
 * <p>
 * 
 * @author
 */
public class WebRipperConfiguration extends NewGRipperConfiguration{
	public static enum Browser {Firefox, IE, HTMLUnit, Chrome};
	public String WEBSITE_URL, DOT_FILE; // website to rip/crawl
	public int DEPTH = 3;   // depth = max depth of links to explore starting from the main URL
	public int WIDTH = 1;   // width = max number of links to scan on a single page
	public String PROFILE = null; // Firefox profile to use
	public Browser BROWSER = null; // Browser to use
	public String BROWSER_PATH = null; // Browser to use
	public boolean help = false;
	public boolean NO_NAVIGATE_HREFS = false;
	
    public WebRipperConfiguration() {
		opts.addOption( "u", "website-url", true, "root URL for the website under test" );
		opts.addOption( "w", "width", true, "max number of links to scan on a single page" );
		opts.addOption( "d", "depth", true, "max depth of links to explore starting from the main URL" );
        opts.addOption( "g", "graph-file", true, "desired file name for DOT file and JPEG graph of website structure");
        opts.addOption( "p", "profile", true, "desired Firefox profile to use");
        opts.addOption( "b", "browser", true, "desired browser to use. One of {Firefox, IE, HTMLUnit, Chrome}");
        opts.addOption( "n", "no-navigate-hrefs", false, "skip href navigation when ripping");
        opts.addOption( "bp", "browser-path", true, "path to the browser executable, if different from its default location");
    }

    public void parseArguments(String[] args) throws ParseException {
        super.parseArguments(args);
		WEBSITE_URL = cmd.getOptionValue("website-url");
		
		if(cmd.getOptionValue("width") != null)
			WIDTH = Integer.parseInt(cmd.getOptionValue("width"));

		if(cmd.getOptionValue("depth") != null)
			DEPTH = Integer.parseInt(cmd.getOptionValue("depth"));
		
		if(cmd.getOptionValue("browser") != null)
			BROWSER = Enum.valueOf(Browser.class, (cmd.getOptionValue("browser")));
		
		if(cmd.getOptionValue("browser-path") != null)
			BROWSER_PATH = (cmd.getOptionValue("browser-path"));
		
		if(cmd.hasOption("no-navigate-hrefs")) {
			NO_NAVIGATE_HREFS = true;
		}
		
		DOT_FILE = cmd.getOptionValue("graph-file");
		
		if(cmd.getOptionValue("profile") != null)
			PROFILE = cmd.getOptionValue("profile");
		
		//MAIN_CLASS = cmd.getOptionValue("main-class");
        //URL_LIST = cmd.getOptionValue("urls");
        //JVM_OPTIONS = cmd.getOptionValue("jvm-options");
        //CUSTOMIZED_EVENT_LIST = cmd.getOptionValue("event-list");

        if (WEBSITE_URL == null) {
            throw new MissingOptionException("Missing required option: website-url");
        }
    }

}
