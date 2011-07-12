package edu.umd.cs.guitar.testcase.plugin;

import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;
import org.kohsuke.args4j.Option;

public class WeightedRandomConfiguration extends TestCaseGeneratorConfiguration {

	/*
 	 * Use this class to define any plugin-specific
	 * 	parameters using the org.kohsuke.args4j
	 * 	'Option' object:
 	 *
 	 * example:
 	 *
 	 * @Option(name = "-l", usage = "test case length", aliases = "--length")
 	 *	static public Integer LENGTH;
 	 *
 	 * "name" : the option annotation (e.g. "-f", "-v")
 	 * "usage" : the definition of the parameter
 	 * "aliases" : any additional option
	 * 	annotations (e.g. "--file", "-verbose")
 	 *
 	 */
		// Insert options(parameters) here
	@Option(name = "-w", usage = "weighted graph file", aliases = "--weights")
	static public String WEIGHTS = "";

}
