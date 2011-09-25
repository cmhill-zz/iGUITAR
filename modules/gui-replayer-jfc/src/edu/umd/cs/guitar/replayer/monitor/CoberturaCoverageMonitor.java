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
package edu.umd.cs.guitar.replayer.monitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.List;

import net.sourceforge.cobertura.coveragedata.CoverageDataFileHandler;
import net.sourceforge.cobertura.coveragedata.ProjectData;

import edu.umd.cs.guitar.exception.GException;
import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao N. Nguyen </a>
 * 
 */
public class CoberturaCoverageMonitor extends GTestMonitor {

	/**
	 * 
	 */
	private static final String COVERAGE_FILE_EXT = "ser";
	private static final String INIT_SER = "init" + "." + COVERAGE_FILE_EXT;
	String sCoverageCleanFile;
	String sCoverageMainFile;
	String sCoverageOutputDir;
	int counter = 0;

	/**
	 * @param sCoverageOutputDir
	 */
	public CoberturaCoverageMonitor(String sCoverageCleanFile,
			String sCoverageOutputDir) {
		super();

		this.sCoverageMainFile = System
				.getProperty("net.sourceforge.cobertura.datafile");

		this.sCoverageCleanFile = sCoverageCleanFile;

		this.sCoverageOutputDir = sCoverageOutputDir;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.replayer.monitor.GTestMonitor#afterStep(edu.umd.cs.
	 * guitar.replayer.monitor.TestStepEndEventArgs)
	 */
	@Override
	public void afterStep(TestStepEndEventArgs eStep) {

		StepType step = eStep.getStep();
		String sEventID = step.getEventId();

		String type = step.isReachingStep() ? "r" : "m";

		String sStepCoverageFile = sCoverageOutputDir + File.separator
				+ (counter++) + "." + type + "." + sEventID;

		sStepCoverageFile += ("." + COVERAGE_FILE_EXT);

		try {
			ProjectData.saveGlobalProjectData();
			copy(sCoverageMainFile, sStepCoverageFile);
			copy(sCoverageCleanFile, sCoverageMainFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.replayer.monitor.GTestMonitor#beforeStep(edu.umd.cs
	 * .guitar.replayer.monitor.TestStepStartEventArgs)
	 */
	@Override
	public void beforeStep(TestStepStartEventArgs step) {

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.replayer.monitor.GTestMonitor#init()
	 */
	@Override
	public void init() {

		GUITARLog.log.debug("Creating coverage dir: " + sCoverageOutputDir);
		new File(sCoverageOutputDir).mkdir();

		// File fCoverageMainFile = new File(sCoverageMainFile);

		try {
			// Force Cobertura to dump out data
			GUITARLog.log.debug("Forcing Cobertura to dump out data.... ");
			ProjectData.saveGlobalProjectData();

			GUITARLog.log.debug("Copying initial coverage file .... ");
			copy(sCoverageMainFile, sCoverageOutputDir + File.separator
					+ INIT_SER);
			copy(sCoverageCleanFile, sCoverageMainFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.replayer.monitor.GTestMonitor#term()
	 */
	@Override
	public void term() {
	}

	/**
	 * Copy and overwrite if needed
	 * 
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	private void copy(String source, String dest) throws IOException {
		// FileInputStream in = null;
		// FileOutputStream out = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			File fSrc = new File(source);
			File fDest = new File(dest);

			// if (fDest.exists()) {
			// fDest.delete();
			// }

			// in = new FileInputStream(fSrc);
			// out = new FileOutputStream(fDest);
			// // in.transferTo(0, in.size(), out);
			//
			// // Transfer bytes from in to out
			// byte[] buf = new byte[1024];
			// int len;
			// while ((len = in.read(buf)) > 0) {
			// out.write(buf, 0, len);
			// }

			in = new FileInputStream(fSrc).getChannel();
			out = new FileOutputStream(fDest).getChannel();
			in.transferTo(0, in.size(), out);

		} finally {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
		}
	}
}
