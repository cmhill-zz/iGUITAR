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
package edu.umd.cs.guitar.replayer.monitor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import edu.umd.cs.guitar.exception.GException;
import edu.umd.cs.guitar.model.GApplication;
import edu.umd.cs.guitar.model.GHashcodeGenerator;
import edu.umd.cs.guitar.model.GIDGenerator;
import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.model.data.TestCase;
import edu.umd.cs.guitar.model.wrapper.GUIStructureWrapper;
import edu.umd.cs.guitar.replayer.GReplayerMonitor;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * Collect current GUI states 
 * 
 * <p>
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class StateMonitorFull extends GTestMonitor {

	protected static ObjectFactory factory = new ObjectFactory();

	GHashcodeGenerator hashcodeGenerator;

	GIDGenerator idGenerator;

	/**
	 * @return the idGenerator
	 */
	public GIDGenerator getIdGenerator() {
		return idGenerator;
	}

	/**
	 * @param idGenerator
	 *            the idGenerator to set
	 */
	public void setIdGenerator(GIDGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	/**
	 * @return the hashcodeGenerator
	 */
	public GHashcodeGenerator getHashcodeGenerator() {
		return hashcodeGenerator;
	}

	/**
	 * @param hashcodeGenerator
	 * @param sStateFile
	 * @param delay
	 */
	public StateMonitorFull(GHashcodeGenerator hashcodeGenerator,
			String sStateFile, int delay) {
		super();
		this.hashcodeGenerator = hashcodeGenerator;
		this.sStateFile = sStateFile;
		this.delay = delay;
	}

	/**
	 * @param hashcodeGenerator
	 *            the hashcodeGenerator to set
	 */
	public void setHashcodeGenerator(GHashcodeGenerator hashcodeGenerator) {
		this.hashcodeGenerator = hashcodeGenerator;
	}

	/**
	 * Output GUIState file
	 */
	protected String sStateFile;

	/**
	 * Output GUI state object
	 */
	protected TestCase outTestCase;

	/**
	 * @param sStateFile
	 *            ouput file name
	 * @param delay
	 *            delay after each step
	 */
	public StateMonitorFull(String sStateFile, int delay) {
		super();
		this.sStateFile = sStateFile;
		this.delay = delay;
	}

	/**
	 * Delay time for GUI to get stable before recording
	 */
	protected int delay;

	GApplication gApplication;
	GReplayerMonitor monitor;


	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.replayer.GTestMonitor#init()
	 */
	@Override
	public void init() {
		// Record the initial state
		outTestCase = factory.createTestCase();
		monitor = replayer.getMonitor();
		gApplication = monitor.getApplication();
		
		monitor.delay(delay);

	

		GUIStructure initialState = getCurrentState();

		outTestCase.setGUIStructure(initialState);

		IO.writeObjToFile(outTestCase, sStateFile);
	}

	/**
	 * @return
	 */
	protected GUIStructure getCurrentState() {

		if (gApplication == null)
			return null;

		GUIStructure currentState = gApplication.getCurrentState();
		if (idGenerator != null)
			idGenerator.generateID(currentState);
		else
			GUITARLog.log.warn("No ID Generator assigned");
		return currentState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.replayer.GTestMonitor#afterStep(edu.umd.cs.guitar.replayer
	 * .TestStepEventArgs)
	 */
	@Override
	public void afterStep(TestStepEndEventArgs eStep) {

		GUITARLog.log.info("Delaying for " + delay
				+ " ms to get a stable GUI state....");

		monitor.delay(delay);

		GUITARLog.log.info("Recording GUI state....");

		List<StepType> lSteps = outTestCase.getStep();
		StepType step = eStep.getStep();

		GUIStructure guiState = getCurrentState();

		// Check opened window
		windowsAfterStep = gApplication.getCurrentWinID();

		Set<String> windowsNew = new HashSet<String>(windowsAfterStep);

		windowsNew.removeAll(windowsBeforeStep);

		GUIStructureWrapper guiStateAdapter = new GUIStructureWrapper(guiState);
		// guiStateAdapter.generateID(hashcodeGenerator);
		if (windowsNew.size() > 0) {
			GUITARLog.log.info("New window(s) open");
			for (String sID : windowsNew)
				GUITARLog.log.info(sID);
			GUITARLog.log.debug("By component: ");

			List<PropertyType> ID = monitor.selectIDProperties(eStep.component);
			AttributesType signature = factory.createAttributesType();
			signature.setProperty(ID);

			guiStateAdapter.addValueBySignature(signature,
					GUITARConstants.INVOKELIST_TAG_NAME, windowsNew);

		}

		// if (idGenerator != null)
		// idGenerator.generateID(guiState);
		// else
		// GUITARLog.log.warn("No ID Generator assigned");

		step.setGUIStructure(guiState);

		lSteps.add(step);
		outTestCase.setStep(lSteps);
		GUITARLog.log.info("DONE");
		GUITARLog.log.info("Dumping out state ... ");
		IO.writeObjToFile(outTestCase, sStateFile);
		GUITARLog.log.info("DONE");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.replayer.GTestMonitor#beforeStep(edu.umd.cs.guitar.
	 * replayer.TestStepEventArgs)
	 */
	@Override
	public void beforeStep(TestStepStartEventArgs eStep) {
		windowsBeforeStep = gApplication.getCurrentWinID();
	}

	Set<String> windowsBeforeStep;
	Set<String> windowsAfterStep;

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.replayer.GTestMonitor#term()
	 */
	@Override
	public void term() {
		// GUITARLog.log.info("Dumping GUI states");
		// IO.writeObjToFile(outTestCase, sStateFile);
		// GUITARLog.log.info("DONE");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.replayer.monitor.GTestMonitor#exceptionHandler(edu.
	 * umd.cs.guitar.exception.GException)
	 */
	@Override
	public void exceptionHandler(GException e) {
		// TODO Auto-generated method stub

	}

}
