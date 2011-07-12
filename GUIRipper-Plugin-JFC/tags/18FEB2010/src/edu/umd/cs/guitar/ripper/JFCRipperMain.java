/*	
 *  Copyright (c) 2009-@year@. The GUITAR group at the University of Maryland. Names of owners of this group may
 *  be obtained by sending an e-mail to atif@cs.umd.edu
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 *  documentation files (the "Software"), to deal in the Software without restriction, including without 
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *	the Software, and to permit persons to whom the Software is furnished to do so, subject to the following 
 *	conditions:
 * 
 *	The above copyright notice and this permission notice shall be included in all copies or substantial 
 *	portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 *	LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 *	EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 *	IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 *	THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package edu.umd.cs.guitar.ripper;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * 
 * Entry class for JFCRipper
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class JFCRipperMain {

//    // ---------------------------------
//    // Parameters
//    // ---------------------------------
//
//    static String IGNORED_DIR = "data" + File.separator + "ignore"
//            + File.separator + "jabref" + File.separator;
//
//    static int DELAY;
//
//    static String MAIN_CLASS = "net.sf.jabref.JabRefMain";
//    // static String MAIN_CLASS = "org.gjt.sp.jedit.jEdit";
//    // static String MAIN_CLASS = "TerpWord";
//    // static String MAIN_CLASS = "TerpPaint";
//    // static String MAIN_CLASS = "org.cesilko.rachota.gui.MainWindow";
//
//    static String GUI_FILE_NAME = MAIN_CLASS + ".GUI";
//    static String LOG_FILE_NAME;
//
//    static Logger logger;

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        JFCRipperConfiguration configuration = new JFCRipperConfiguration();
        CmdLineParser parser = new CmdLineParser(configuration);
        JFCRipper jfcRipper = new JFCRipper(configuration );
        
        try {
//            parser.setUsageWidth(Integer.MAX_VALUE);
            parser.parseArgument(args);
            jfcRipper.execute();
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println();
            System.err.println("Usage: java [JVM options] "+ JFCRipperMain.class.getName() + " [Ripper options] \n");
            System.err.println("where [Ripper options] include:");
            System.err.println();
            parser.printUsage(System.err);
        }
        System.exit(0);
    }

    /**
     * Parse command the line parameters
     * 
     * <p>
     * 
     * @param args
     */
    @Deprecated
    private static void parseCmd(String[] args) {
//        // Initiate the arguments engine.
//        ArgsEngine engine = new ArgsEngine();
//
//        // Configure the switches/options. Use true for valued options.
//        engine.add("-d", "--delay", true);
//        engine.add("-i", "--ignore-dir", true);
//        engine.add("-c", "--main-class", true);
//        engine.add("-g", "--gui-file", true);
//        engine.add("-l", "--log-file", true);
//
//        // Perform the parsing. The 'args' is the String[] received by main
//        // method.
//
//        engine.parse(args);
//
//        if (engine.getBoolean("-d")) {
//            String sDELAY = engine.getString("-d");
//            DELAY = Integer.parseInt(sDELAY);
//        }
//
//        if (engine.getBoolean("-i")) {
//            IGNORED_DIR = engine.getString("-i");
//        }
//
//        if (engine.getBoolean("-c")) {
//            MAIN_CLASS = engine.getString("-c");
//        }
//
//        if (engine.getBoolean("-g")) {
//            GUI_FILE_NAME = engine.getString("-g");
//        }
    }

}
