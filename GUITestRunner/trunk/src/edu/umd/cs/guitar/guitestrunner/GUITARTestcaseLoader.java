package edu.umd.cs.guitar.guitestrunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

/**
 * Load up a test case from a file as a set of steps.
 * @author Scott McMaster
 *
 */
public class GUITARTestcaseLoader
{
	/**
	 * Stash the name of the file that we loaded from.
	 */
	private String testFilename;
	
	/**
	 * The steps for this testcase.
	 */
	private List<TestcaseStep> steps;

	/**
	 * Build the list of test cases from the xml Step elements in the given file.
	 */
	private void buildTestcaseStepsFromXmlFile()
	{
		steps = new ArrayList<TestcaseStep>();
		
		Builder builder = new Builder();
		Document testcaseDoc = null;
		try {
			 testcaseDoc = builder.build(new File(testFilename));
		} catch (ValidityException e) {
			throw new TestRunnerException(e);
		} catch (ParsingException e) {
			throw new TestRunnerException(e);
		} catch (IOException e) {
			throw new TestRunnerException(e);
		}
		
		Elements stepElems = testcaseDoc.getRootElement().getChildElements("Step");
		for( int i = 0; i < stepElems.size(); ++i )
		{
			Element stepElem = stepElems.get(i);
			TestcaseStep step = TestcaseStep.fromStepElement(stepElem);
			steps.add(step);
		}
	}

	/**
	 * Create a loader for the given file.
	 * @param testFilename
	 */
	public GUITARTestcaseLoader(String testFilename)
	{
		this.testFilename = testFilename;
		buildTestcaseStepsFromXmlFile();		
	}

	/**
	 * Name of the file that we loaded from.
	 * @return
	 */
	public String getFilename() {
		return testFilename;
	}

	/**
	 * Get the steps for this testcase.
	 * @return
	 */
	public List<TestcaseStep> getSteps()
	{
		return steps;
	}

}
