package edu.umd.cs.guitar.testcase;

import junit.framework.TestCase;

import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;


/**
 * This class is to test the TestCaseGeneratorConfiguration class.
 * <br/> <br/>
 * 
 * There is only one method. It is tested through creating an object for 
 * TestCaseGeneratorConfiguration and then calling the method.
 * <br/> <br/>
 * 
 * The line coverage of this class reaches 100%.
 * 
 * @author Yuening Hu
 */	

public class TestCaseGeneratorConfigurationTest extends TestCase {
	
    /**
     * This variable is to get an instance of TestCaseGeneratorConfiguration,
     * so the methods in this class can be tested.
     */	
	TestCaseGeneratorConfiguration m_config;
	
    /**
     * This function is to check the isValid method in TestCaseGeneratorConfiguration 
     * class, and comparing the results with the expected results.
     */	
	public void testisValid(){
		m_config = new TestCaseGeneratorConfiguration();
		assertEquals(m_config.isValid(), true);
	}
}
