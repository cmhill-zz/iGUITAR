package edu.umd.cs.guitar.util;

import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.QueueTool;
import org.netbeans.jemmy.TestOut;
import org.netbeans.jemmy.TimeoutExpiredException;

/**
 * Utilities useful to pretty much any type of test case replayer.
 * 
 * @author Scott McMaster, Si Huang
 * 
 */
public class Utils {
    public static final boolean DEBUG = false;
    public static final Logger LOG = new Logger(false);

    static {
        JemmyProperties.setCurrentOutput(TestOut.getNullOutput());
        LOG.setLogging(true);
    }

    public static void waitEmpty(long ms) {
        try {
            new QueueTool().waitEmpty(ms);
        } catch (TimeoutExpiredException e) {
            LOG.logln("Top event in the queue:");
            LOG.logln(QueueTool.getQueue().peekEvent());
        }
    }

    /**
     * Sleep and eat the useless checked exception.
     * 
     * @param ms
     */
    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // Ignore.
        }
    }
}
