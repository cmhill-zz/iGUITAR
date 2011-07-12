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
package edu.umd.cs.guitar.testcase;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.testcase.plugin.TCPlugin;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * 
 * Main class of the Test Case Generator. Run the specified converter, with the
 * specifier parameters
 * 
 * @author <a href="mailto:charlie.biger@gmail.com"> Charlie BIGER </a>
 * 
 */
public class TestcaseGenerator {

    public static void main(String[] args) {

        setupLog();

        TestCaseGeneratorConfiguration configuration = new TestCaseGeneratorConfiguration();
        CmdLineParser parser = new CmdLineParser(configuration);

        try {
            parser.parseArgument(args);

            if (!configuration.isValid()
                    || (TestCaseGeneratorConfiguration.HELP)) {
                throw new CmdLineException("");
            }

            // By default the plugin is put in the same package with TCPlugin
            if (!TestCaseGeneratorConfiguration.PLUGIN.contains(TCPlugin.class
                    .getPackage().getName()))
                TestCaseGeneratorConfiguration.PLUGIN = TCPlugin.class
                        .getPackage().getName()
                        + "." + TestCaseGeneratorConfiguration.PLUGIN;

            Class converterClass = Class
                    .forName(TestCaseGeneratorConfiguration.PLUGIN);
            TCPlugin generator = (TCPlugin) converterClass.newInstance();

            if (!generator.isValidArgs()) {
                throw new CmdLineException("Invalid plugin arguments ");
            }

            generator.generate();

        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println();
            System.err.println("Usage: java [JVM options] "
                    + TestcaseGenerator.class.getName()
                    + " [TC generator options] \n");
            System.err.println("where [TC generator options] include:");
            System.err.println();
            parser.printUsage(System.err);
            System.exit(0);
        } catch (ClassNotFoundException e) {
            System.out
                    .println("Plugin cannot be found. Please make sure that the plugin name is correct and the corresponding .jar file can be reached.");
            e.printStackTrace();
        } catch (InstantiationException e) {

            System.out
                    .println("Plugin is defined as an Abstract class, or an interface, or its constructor is not accessible without parameters.");
            System.out.println("Please Report this bug");
        } catch (IllegalAccessException e) {
            System.out.println("Plugin is not accessible");
        }
    }

    /**
     * 
     */
    private static void setupLog() {
        try {
            GUITARLog.log = Logger.getLogger(TestcaseGenerator.class
                    .getSimpleName());
            final File logFile = new File("TestcaseGenerator.log");
            final String LOG_PATTERN = "%m%n";
            final PatternLayout pl = new PatternLayout(LOG_PATTERN);

            final FileAppender rfp = new RollingFileAppender(pl, logFile
                    .getCanonicalPath(), true);

            final ConsoleAppender cp = new ConsoleAppender(pl);

            GUITARLog.log.addAppender(rfp);
            GUITARLog.log.addAppender(cp);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
