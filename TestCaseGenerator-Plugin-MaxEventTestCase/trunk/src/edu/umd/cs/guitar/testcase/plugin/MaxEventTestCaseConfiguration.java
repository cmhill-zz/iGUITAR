package edu.umd.cs.guitar.testcase.plugin;

import org.kohsuke.args4j.Option;
import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;

/*
	The configuration file for the MaxEventTestCase
*/
public class MaxEventTestCaseConfiguration extends TestCaseGeneratorConfiguration{

	@Option(name = "-r", usage = "max repeats", aliases = "--repeats")
    static public Integer REPEATS;

}