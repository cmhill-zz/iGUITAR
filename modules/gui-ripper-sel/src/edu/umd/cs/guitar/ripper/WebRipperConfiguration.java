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
public class WebRipperConfiguration extends NewGRipperConfiguration {

	public String WEBSITE_URL, DOT_FILE; // website to rip/crawl
	public int DEPTH = 3;   // depth = max depth of links to explore starting from the main URL
	public int WIDTH = 1;   // width = max number of links to scan on a single page
	public String PROFILE = null; // Firefox profile to use
	public boolean help = false;

    public WebRipperConfiguration() {
		opts.addOption( "u", "website-url", true, "root URL for the website under test" );
		opts.addOption( "w", "width", true, "max number of links to scan on a single page" );
		opts.addOption( "d", "depth", true, "max depth of links to explore starting from the main URL" );
        opts.addOption( "g", "graph-file", true, "desired file name for DOT file and JPEG graph of website structure");
        opts.addOption( "p", "profile", true, "desired Firefox profile to use");
    }

    public void parseArguments(String[] args) throws ParseException {
        super.parseArguments(args);
		WEBSITE_URL = cmd.getOptionValue("website-url");
		
		if(cmd.getOptionValue("width") != null)
			WIDTH = Integer.parseInt(cmd.getOptionValue("width"));

		if(cmd.getOptionValue("depth") != null)
			DEPTH = Integer.parseInt(cmd.getOptionValue("depth"));
		
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
