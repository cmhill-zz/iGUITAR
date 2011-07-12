package edu.umd.cs.guitar.testcase.plugin;

import org.kohsuke.args4j.Option;

import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;

public class NodeToAllOtherNodeConfiguration extends TestCaseGeneratorConfiguration {
	
	@Option(name = "-t", usage = "types of events the generated test cases can contain (comma seperated)", aliases = "--eventType")
    static public String TYPES = "";
	
	@Option(name = "-w", usage = "event widgets the generated test cases can contain (comma seperated)", aliases = "--eventWidget")
    static public String WIDGETS = "";
	
}
