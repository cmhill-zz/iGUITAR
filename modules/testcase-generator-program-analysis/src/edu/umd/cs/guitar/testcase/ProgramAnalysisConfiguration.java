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
	
	final public static String SEPERATOR = ";";
	
	public static String[] getClassScope(){
		return SCOPE.split(SEPERATOR);
	}
}
