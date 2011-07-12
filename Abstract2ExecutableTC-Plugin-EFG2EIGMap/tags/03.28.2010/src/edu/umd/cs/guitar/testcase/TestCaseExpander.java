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
package edu.umd.cs.guitar.testcase;

import java.io.File;
import java.util.List;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.EdgeMappingListType;
import edu.umd.cs.guitar.model.data.EdgeMappingType;
import edu.umd.cs.guitar.model.data.InitialMappingListType;
import edu.umd.cs.guitar.model.data.InitialMappingType;
import edu.umd.cs.guitar.model.data.Mapping;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.model.data.TestCase;

/**
 * Expand an abstract test suite to an executable one
 * 
 * <p>
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao N. Nguyen </a>
 * 
 */
public class TestCaseExpander {

	Mapping map;
	static ObjectFactory factory = new ObjectFactory();

	/**
	 * @param map
	 */
	public TestCaseExpander(Mapping map) {
		super();
		this.map = map;
	}

	public static void main(String args[]) {

		TestCaseExpanderConfiguration configuration = new TestCaseExpanderConfiguration();
		CmdLineParser parser = new CmdLineParser(configuration);
		try {
			parser.parseArgument(args);
			checkArgs();

			Mapping map = (Mapping) IO.readObjFromFile(
					TestCaseExpanderConfiguration.MAPPING_FILE, Mapping.class);
			TestCaseExpander expander = new TestCaseExpander(map);

			String sInputName = TestCaseExpanderConfiguration.INPUT_FILE;
			String sOutputName = TestCaseExpanderConfiguration.OUTPUT_FILE;
			boolean isExpandDir = TestCaseExpanderConfiguration.IS_DIRECTORY;
			if (isExpandDir) {

				expander.expandTestSuite(sInputName, sOutputName);

			} else {
				expander.expandTestCase(sInputName, sOutputName);
			}
			System.out.println("DONE");

		} catch (CmdLineException e) {
			System.err.println(e.getMessage());
			System.err.println();
			System.err.println("Usage: java [JVM options] "
					+ TestCaseExpander.class.getName()
					+ " [TestCaseExpander  converter options] \n");
			System.err
					.println("where [TestCaseExpander converter options] include:");
			System.err.println();
			parser.printUsage(System.err);

			System.exit(0);
		}
	}

	/**
	 * @param sInputName
	 * @param sOutputName
	 */
	private void expandTestSuite(String sInputName, String sOutputName) {

		File iDir = new File(sInputName);
		if (!iDir.isDirectory()) {
			System.out.println(sInputName + " is not a directory");
			return;
		}

		new File(sOutputName).mkdir();
		File[] files = iDir.listFiles();

		if (files != null) {
			for (int i = 0; i < files.length; i++) {

				String inFilePath = files[i].getPath();
				String outFilePath = sOutputName + File.separator
						+ files[i].getName();

				try {
					System.out
							.println("--------------------------------------");
					System.out.println("#: " + i);
					System.out.println("Expanding: " + inFilePath);
					System.out.println("To: \t" + outFilePath);
					expandTestCase(inFilePath, outFilePath);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * @param sInputFile
	 * @param sOutputFile
	 */
	private void expandTestCase(String sInputFile, String sOutputFile) {

		TestCase iTestCase = (TestCase) IO.readObjFromFile(sInputFile,
				TestCase.class);

		TestCase oTestCase = factory.createTestCase();

		List<StepType> iStepList = iTestCase.getStep();

		if (iStepList == null)
			return;
		if (iStepList.size() == 0)
			return;

		// Expand path to root
		StepType initStep = iStepList.get(0);
		List<String> pathToRoot = getPathToRoot(initStep.getEventId());

		if (pathToRoot != null) {
			for (int i = 0; i < pathToRoot.size(); i++) {
				StepType step = factory.createStepType();
				step.setEventId(pathToRoot.get(i));
				step.setReachingStep(true);
				oTestCase.getStep().add(step);
			}
		}

		initStep.setReachingStep(false);
		oTestCase.getStep().add(initStep);

		// Expand path between each event

		for (int i = 0; i < iStepList.size() - 1; i++) {
			StepType sourceStep = iStepList.get(i);
			StepType targetStep = iStepList.get(i + 1);
			List<String> path = getPath(sourceStep.getEventId(), targetStep
					.getEventId());
			if (path != null) {
				for (int j = 0; j < path.size(); j++) {
					StepType step = factory.createStepType();
					step.setEventId(path.get(j));
					step.setReachingStep(true);
					oTestCase.getStep().add(step);
				}
			}
			targetStep.setReachingStep(false);
			oTestCase.getStep().add(targetStep);
		}

		IO.writeObjToFile(oTestCase, sOutputFile);

	}

	/**
	 * @param sourceId
	 * @param targetId
	 * @return
	 */
	private List<String> getPath(String sourceId, String targetId) {
		EdgeMappingListType lEdgeMapping = map.getEdgeMappingList();
		for (EdgeMappingType edgeMapping : lEdgeMapping.getEdgeMapping()) {
			if (sourceId.equals(edgeMapping.getSourceId())
					&& targetId.equals(edgeMapping.getTargetId()))
				return edgeMapping.getPath().getEventId();
		}

		return null;
	}

	/**
	 * @param eventId
	 * @return
	 */
	private List<String> getPathToRoot(String eventId) {
		InitialMappingListType lInitialMapping = map.getInitialMappingList();
		for (InitialMappingType initialMapping : lInitialMapping
				.getIntialMapping()) {
			if (initialMapping.getEventId().equals(eventId)) {
				return initialMapping.getPath().getEventId();
			}
		}

		return null;

	}

	/**
	 * 
	 * Check for command-line arguments
	 * 
	 * @throws CmdLineException
	 * 
	 */
	private static void checkArgs() throws CmdLineException {
		// Check argument
		if (TestCaseExpanderConfiguration.HELP) {
			throw new CmdLineException("");
		}

		boolean isPrintUsage = false;

		if (TestCaseExpanderConfiguration.INPUT_FILE == null) {
			System.err.println("missing '-i' argument");
			isPrintUsage = true;
		}

		if (TestCaseExpanderConfiguration.OUTPUT_FILE == null) {
			System.err.println("missing '-o' argument");
			isPrintUsage = true;
		}

		if (TestCaseExpanderConfiguration.MAPPING_FILE == null) {
			System.err.println("missing '-m' argument");
			isPrintUsage = true;
		}

		if (isPrintUsage)
			throw new CmdLineException("");

	}

	/**
	 * 
	 */
	private static void printInfo() {
		System.out.println("================================");
		System.out.println("Intput File: "
				+ TestCaseExpanderConfiguration.INPUT_FILE);

		System.out.println("Output File: "
				+ TestCaseExpanderConfiguration.OUTPUT_FILE);

		System.out.println("Mapping File: "
				+ TestCaseExpanderConfiguration.MAPPING_FILE);

		System.out.println("================================");

	}

}