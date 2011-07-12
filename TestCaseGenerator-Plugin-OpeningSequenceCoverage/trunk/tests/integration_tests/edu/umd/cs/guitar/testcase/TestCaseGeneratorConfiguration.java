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

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.Option;



;

/**
 * Class contains the runtime configurations of JFC GUITAR Ripper
 * 
 * <p>
 * 
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class TestCaseGeneratorConfiguration {

    // Common parameters
    @Option(name = "-p", usage = "plugin name", aliases = "--plugin")
    static public String PLUGIN = "LengthCoverage";

    @Option(name = "-e", usage = "input efg file", aliases = "--efg-file")
    static public String EFG_FILE = "Default.EFG";

    @Option(name = "-m", usage = "maximum number of testcases (0 for all)", aliases = "--max-number")
    static public int MAX_NUMBER = 100;

    @Option(name = "-d", usage = "out put dir", aliases = "--dir")
    static public String OUTPUT_DIR = "TC";

    @Option(name = "-?", usage = "print this help message", aliases = "--help")
    static protected boolean HELP;

    // ---------------------------------
    // Plugin specific parameters
    // ---------------------------------

    // Length coverage
    @Option(name = "-l", usage = "test case length", aliases = "--length")
    static public Integer LENGTH;

    // @Option(name = "-g", usage = "input gui file", aliases = "--gui-file")
    // static public String GUI_FILE = null;

    /**
     * Check arguments' configuration
     * 
     * @return true/false
     */
    public boolean isValid() {
        return true;
    }

}
