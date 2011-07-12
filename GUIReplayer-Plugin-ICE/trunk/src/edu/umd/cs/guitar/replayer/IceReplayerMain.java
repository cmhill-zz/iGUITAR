package edu.umd.cs.guitar.replayer;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public class IceReplayerMain {
    public static void main(String[] args) {
        IceReplayerConfiguration configuration = new IceReplayerConfiguration();
        CmdLineParser parser = new CmdLineParser(configuration);
        final IceReplayer replayer = new IceReplayer(configuration);

        try {
            parser.parseArgument(args);
            int status = replayer.main(
                "GuitarReplayer", args, configuration.ICE_CONFIG);
            System.gc();
            System.exit(status);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println();
            System.err.println("Usage: java [JVM options] "
                    + IceReplayerMain.class.getName() + " [Replayer options] \n");
            System.err.println("where [Replayer options] include:");
            System.err.println();
            parser.printUsage(System.err);
        }
    }
}
