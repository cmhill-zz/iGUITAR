/*	
 *  Copyright (c) 2011. The GREYBOX group at the University of Freiburg, Chair of Software Engineering.
 *  Names of owners of this group may be obtained by sending an e-mail to arlt@informatik.uni-freiburg.de
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

import org.kohsuke.args4j.Option;

public class ProgramAnalysisConfiguration extends TestCaseGeneratorConfiguration {
	@Option(name = "-s",
            usage = "input for the static analysis, as list of jars or directories seperated by ;",
            aliases = "--scope")
    static public String SCOPE = null;
	
	@Option(name = "-me",
            usage = "defines the method, that is used to generate testcases",
            aliases = "--method")
    static public String METHOD = null;
	
	@Option(name = "-sh",
            usage = "consider shared events (=1) or not (=0, default)",
            aliases = "--shared")
    static public int SH = 0;
	
	final public static String SEPERATOR = ";";
	
	public static String[] getClassScope(){
		return SCOPE.split(SEPERATOR);
	}
}
