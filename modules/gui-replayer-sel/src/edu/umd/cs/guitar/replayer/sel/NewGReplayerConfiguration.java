/*  
 *  Copyright (c) 2009-@year@. The GUITAR group at the University of Maryland. Names of owners of this group may
 *  be obtained by sending an e-mail to atif@cs.umd.edu
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 *  documentation files (the "Software"), to deal in the Software without restriction, including without 
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *  the Software, and to permit persons to whom the Software is furnished to do so, subject to the following 
 *  conditions:
 * 
 *  The above copyright notice and this permission notice shall be included in all copies or substantial 
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 *  LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 *  EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 *  THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package edu.umd.cs.guitar.replayer.sel;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.HelpFormatter;

import edu.umd.cs.guitar.util.Util;

public class NewGReplayerConfiguration {

    // Subclasses should add their own options to this list
    protected Options opts;

    // Useful for subclasses to get command line options
    protected CommandLine cmd;

    public NewGReplayerConfiguration() {
        opts = new Options();
        opts.addOption( "g", "gui-file", true, "GUI file path" );
        opts.addOption( "e", "efg-file", true, "EFG file path" );
        opts.addOption( "t", "testcase", true, "testcase file path" );
        opts.addOption( null, "gui-state", true, "GUI state file path" );
        opts.addOption( "l", "log-file", true, "log file name" );
        opts.addOption( null, "initial-wait", true, "initial waiting time for the application to get stabilized before being replayed" );
        opts.addOption( "d", "delay", true, "step delay time" );
        opts.addOption( "c", "config-file", true, "configuration file for the ripper defining terminal and ignored components/windows" );
        opts.addOption( "a", "args", true, "arguments for the application under test, separated by ';'" );
        opts.addOption( "h", "help", false, "Print this message" );
    }

    public void parseArguments(String[] args) throws ParseException {
        PosixParser parser = new PosixParser();
        cmd = parser.parse( opts, args );

        if ( cmd.hasOption("help") ) {
            // Print help and exit with non-zero status code
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "ReplayerMain", opts );
            System.exit(1);
        }
        
        GUI_FILE = cmd.getOptionValue("gui-file", "GUITAR-Default.GUI");
        EFG_FILE = cmd.getOptionValue("efg-file", "GUITAR-Default.EFG");
        TESTCASE = cmd.getOptionValue("testcase");
        GUI_STATE_FILE = cmd.getOptionValue("gui-state", "GUITAR-Default.STA");
        LOG_FILE = cmd.getOptionValue("log-file", Util.getTimeStamp()+".log");
        CONFIG_FILE = cmd.getOptionValue("config-file", "configuration.xml");
        DELAY = Integer.parseInt(cmd.getOptionValue("delay", "5000"));
        ARGUMENT_LIST = cmd.getOptionValue("args", "");
        INITIAL_WAITING_TIME = Integer.parseInt(
            cmd.getOptionValue("initial-wait", "500"));
    }

    public String GUI_FILE;
    public String EFG_FILE;
    public String TESTCASE;
    public String GUI_STATE_FILE;
    public String LOG_FILE;
    public String CONFIG_FILE;
    public int DELAY;
    public String ARGUMENT_LIST;
    public int INITIAL_WAITING_TIME;
}
