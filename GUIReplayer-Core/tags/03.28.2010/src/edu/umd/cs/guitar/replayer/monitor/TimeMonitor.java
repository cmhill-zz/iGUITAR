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

import edu.umd.cs.guitar.exception.GException;
import edu.umd.cs.guitar.exception.TimeoutException;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * Monitor timeout for testcase
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class TimeMonitor extends GTestMonitor {

	int nStepTimeout = 0;
	int nTestcaseTimeout = 0;
	private Timer tStepTimer;
	long nStartTime;
	long nEndTime;

	/**
	 * @param nStepTimeout
	 * @param nTestcaseTimeout
	 */
	public TimeMonitor(int nStepTimeout, int nTestcaseTimeout) {
		super();
		this.nStepTimeout = nStepTimeout;
		this.nTestcaseTimeout = nTestcaseTimeout;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.replayer.monitor.GTestMonitor#afterStep(edu.umd.cs.
	 * guitar.replayer.monitor.TestStepEventArgs)
	 */
	@Override
	public void afterStep(TestStepEndEventArgs arg0) {
		GUITARLog.log.info(tStepTimer.getTimerName() + " elapsed Time: "
				+ tStepTimer.getElapsedTime());
		tStepTimer.stopTimer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.replayer.monitor.GTestMonitor#beforeStep(edu.umd.cs
	 * .guitar.replayer.monitor.TestStepEventArgs)
	 */
	@Override
	public void beforeStep(TestStepStartEventArgs arg0) {
		tStepTimer.startTimer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.replayer.monitor.GTestMonitor#init()
	 */
	@Override
	public void init() {
		GUITARLog.log.info(this.getClass().getName()
				+ " test monitor is applied");
		tStepTimer = new Timer("Step Timer", nStepTimeout);
		tStepTimer.start();
		nStartTime = System.currentTimeMillis();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.replayer.monitor.GTestMonitor#term()
	 */
	@Override
	public void term() {
		// // ------------------
		// // Elapsed time:
		// long nEndTime = System.currentTimeMillis();
		// long nDuration = nEndTime - nStartTime;
		// DateFormat df = new SimpleDateFormat("HH : mm : ss: SS");
		// df.setTimeZone(TimeZone.getTimeZone("GMT"));
		// GUITARLog.log.info("Time Elapsed: " + df.format(nDuration));
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
		if (e instanceof TimeoutException)
			GUITARLog.log.error("TimeOut");
	}

}

/**
 * A timer to monitor test run
 * 
 * <p>
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 * 
 */
class Timer extends Thread {
	boolean isRunning = false;
	long nStartTime;
	String name;

	/**
	 * @return the name
	 */
	public String getTimerName() {
		return name;
	}

	/**
	 * @param name
	 * @param nTimeout
	 */
	public Timer(String name, int nTimeout) {
		super();
		this.name = name;
		this.nTimeout = nTimeout;
	}

	/** Length of timeout */
	int nTimeout = 0;

	/** Rate at which timer is checked */
	protected int m_rate = 100;

	/** Time elapsed */
	private int m_elapsed;

	public void startTimer() {
		m_elapsed = 0;
		nStartTime = System.currentTimeMillis();
		isRunning = true;
	}

	public void stopTimer() {
		isRunning = false;
	}

	private void timeout() {
		GUITARLog.log.info(name + ": TIMEOUT!!!");
		System.setSecurityManager(null);
		System.exit(1);
	}

	@Override
	public void run() {
		while (m_elapsed < nTimeout) {
			try {
				Thread.sleep(m_rate);
			} catch (InterruptedException e) {
				GUITARLog.log.error(e);
			}
			if (isRunning)
				m_elapsed += m_rate;

		}
		timeout();
	}

	/**
	 * @return the elapsed time
	 */
	public long getElapsedTime() {
		return (System.currentTimeMillis() - nStartTime);
	}

}
