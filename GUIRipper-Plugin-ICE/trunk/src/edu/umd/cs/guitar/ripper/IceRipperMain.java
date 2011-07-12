package edu.umd.cs.guitar.ripper;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public class IceRipperMain {
    public static void main(String[] args) {
        IceRipperConfiguration configuration = new IceRipperConfiguration();
        CmdLineParser parser = new CmdLineParser(configuration);
        final IceRipper ripper = new IceRipper(configuration);

        try {
            parser.parseArgument(args);
            ripper.main("GuitarRipper", args, configuration.ICE_CONFIG);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println();
            System.err.println("Usage: java [JVM options] "
                    + IceRipperMain.class.getName() + " [Ripper options] \n");
            System.err.println("where [Ripper options] include:");
            System.err.println();
            parser.printUsage(System.err);
        }
    }
}
