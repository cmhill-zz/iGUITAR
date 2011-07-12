package edu.umd.cs.guitar.ripper;

import org.kohsuke.args4j.Option;

/**
 * 
 * General configuration for GUITAR Ripper. 
 * 
 * <p>
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class GRipperConfiguration {

    @Option(name = "-d", usage = "delay time after each GUI expanding action ", aliases = "--delay")
    Integer DELAY=500;
    @Option(name = "-?", usage = "print this help message", aliases = "--help")
    protected boolean help;

    /**
     * 
     */
    public GRipperConfiguration() {
        super();
    }

}