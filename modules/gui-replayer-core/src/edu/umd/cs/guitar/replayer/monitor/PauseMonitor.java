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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.umd.cs.guitar.exception.GException;

/**
 * Add a pause after each step. This monitor is used to examine individual test
 * case in detail.
 * 
 * <p>
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao N. Nguyen </a>
 * 
 */
public class PauseMonitor extends GTestMonitor {

	/**
	 * 
	 */
	public PauseMonitor() {
		// TODO Auto-generated constructor stub
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
		pause("Step is done. Press ENTER to continue ...  ");
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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.replayer.monitor.GTestMonitor#term()
	 */
	@Override
	public void term() {
		pause("Test case is done. Press ENTER to continue ...  ");
	}

	/**
	 * Pause for a while
	 */
	public void pause(Object msg) {
		System.err.println(this.getClass().getName() + ": " + msg);
		// Pause
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			stdin.readLine();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
