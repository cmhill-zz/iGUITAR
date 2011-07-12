package edu.umd.cs.guitar.replayer;

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

import java.awt.Frame;
import java.awt.Window;
import java.net.MalformedURLException;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.exception.ApplicationConnectException;
import edu.umd.cs.guitar.exception.ApplicationTerminatedException;
import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.JFCApplication;
import edu.umd.cs.guitar.model.JFCConstants;
import edu.umd.cs.guitar.model.JFCXWindow;
import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.util.GUITARLog;
import edu.umd.cs.guitar.replayer.GReplayerMonitor;

/**
 * Replayer monitor for Java Swing (JFC) application
 * 
 * <p>
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class JFCReplayerMonitor extends GReplayerMonitor {

	/**
     * 
     */
	private static final int INITIAL_DELAY = 1000;
	String MAIN_CLASS;

	/**
	 * Delay for widget searching loop
	 */
	private static final int DELAY_STEP = 50;

	/**
	 * @param main_class
	 */
	public JFCReplayerMonitor(String main_class) {
		super();
		MAIN_CLASS = main_class;
	}

	/**
	 * Class used to disable System.exit()
	 * 
	 * @author Bao Nguyen
	 * 
	 */
	private static class ExitTrappedException extends SecurityException {
	}

	SecurityManager oldSecurityManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.replayer.AbsReplayerMonitor#setUp()
	 */
	@Override
	public void setUp() {
		GUITARLog.log.info("Setting up JFCReplayer...");
		// -------------------------------------
		// Add handler for all uncaught exceptions
		Thread
				.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
					public void uncaughtException(Thread t, Throwable e) {
						GUITARLog.log.error("Uncaught exception", e);
					}
				});

		// -------------------------------------
		// Disable System.exit() call by changing SecurityManager

		oldSecurityManager = System.getSecurityManager();
		final SecurityManager securityManager = new SecurityManager() {
			// public void checkExit(int status) {
			// //throw new ApplicationTerminatedException(status);
			// }

			public void checkPermission(Permission permission, Object context) {
				if ("exitVM".equals(permission.getName())) {
					throw new ExitTrappedException();
				}
			}

			public void checkPermission(Permission permission) {
				if ("exitVM".equals(permission.getName())) {
					throw new ExitTrappedException();
				}
			}
		};
		System.setSecurityManager(securityManager);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.replayer.AbsReplayerMonitor#cleanUp()
	 */
	@Override
	public void cleanUp() {
		System.setSecurityManager(oldSecurityManager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.replayer.AbsReplayerMonitor#getAction(java.lang.String)
	 */
	@Override
	public GEvent getAction(String actionName) {
		GEvent retAction = null;
		try {
			Class<?> c = Class.forName(actionName);
			Object action = c.newInstance();

			retAction = (GEvent) action;

		} catch (Exception e) {
			GUITARLog.log.error("Error in getting action", e);
		}

		return retAction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.replayer.AbsReplayerMonitor#getArguments(java.lang.
	 * String)
	 */
	@Override
	public Object getArguments(String action) {
		// EventData event = new EventData(action);
		// return event.getParameters();
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.replayer.AbsReplayerMonitor#getComponent(java.lang.
	 * String, edu.umd.cs.guitar.model.GXWindow)
	 */
	@Override
	public GComponent getComponent(String sComponentID, GWindow gWindow) {
		GComponent retGXComponent = null;

		while (retGXComponent == null) {

			retGXComponent = gWindow.getFirstChildByID(sComponentID);

			try {
				Thread.sleep(DELAY_STEP);
			} catch (InterruptedException e) {
				GUITARLog.log.error(e);
			}

		}
		return retGXComponent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.replayer.AbsReplayerMonitor#getWindow(java.lang.String)
	 */
	@Override
	public GWindow getWindow(String sWindowID) {

		GWindow retGXWindow = null;
		while (retGXWindow == null) {

			Frame[] windows = Frame.getFrames();

			if (windows == null)
				continue;

			for (Frame aWindow : windows) {
				Window window = getOwnedWindowByID(aWindow, sWindowID);
				if (window != null) {
					retGXWindow = new JFCXWindow(window);
					break;
				}
			}
			try {
				Thread.sleep(DELAY_STEP);
			} catch (InterruptedException e) {
				GUITARLog.log.error(e);
			}
		}
		return retGXWindow;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.replayer.GReplayerMonitor#selectIDProperties(edu.umd
	 * .cs.guitar.model.data.ComponentType)
	 */
	@Override
	public List<PropertyType> selectIDProperties(ComponentType comp) {
		if (comp == null)
			return new ArrayList<PropertyType>();

		List<PropertyType> retIDProperties = new ArrayList<PropertyType>();

		AttributesType attributes = comp.getAttributes();
		List<PropertyType> lProperties = attributes.getProperty();
		for (PropertyType p : lProperties) {
			if (JFCConstants.ID_PROPERTIES.contains(p.getName()))
				retIDProperties.add(p);
		}
		return retIDProperties;
	}

	/**
	 * 
	 * Recursively search a window
	 * 
	 * @param parent
	 * @param sWindowID
	 * @return Window
	 */
	private Window getOwnedWindowByID(Window parent, String sWindowID) {

		if (parent == null)
			return null;

		GWindow gWindow = new JFCXWindow(parent);

		if (sWindowID.equals(gWindow.getTitle())) {
			return parent;
		}

		Window retWin = null;
		Window[] wOwnedWins = parent.getOwnedWindows();
		for (Window aOwnedWin : wOwnedWins) {
			retWin = getOwnedWindowByID(aOwnedWin, sWindowID);
			if (retWin != null)
				return retWin;
		}

		return retWin;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.replayer.AbsReplayerMonitor#connectToApplication()
	 */
	@Override
	public void connectToApplication() {
		try {
			// application = new JFCApplication(MAIN_CLASS, INITIAL_DELAY);
			
			GUITARLog.log.info("Loading URL....");
			
			String[] URLs;
			if (JFCReplayerConfiguration.URL_LIST != null)
				URLs = JFCReplayerConfiguration.URL_LIST
						.split(GUITARConstants.CMD_ARGUMENT_SEPARATOR);
			else
				URLs = new String[0];

			application = new JFCApplication(
					JFCReplayerConfiguration.MAIN_CLASS, URLs);

			String[] args;

			if (JFCReplayerConfiguration.ARGUMENT_LIST != null)
				args = JFCReplayerConfiguration.ARGUMENT_LIST
						.split(GUITARConstants.CMD_ARGUMENT_SEPARATOR);
			else
				args = new String[0];

			GUITARLog.log.info("Loading URL.... DONE");
			
			application.connect(args);

			GUITARLog.log.info("Initial waiting for "
					+ JFCReplayerConfiguration.INITIAL_WAITING_TIME + "ms");

			try {
				Thread.sleep(JFCReplayerConfiguration.INITIAL_WAITING_TIME);
			} catch (InterruptedException e) {
				GUITARLog.log.error(e);
				throw new ApplicationConnectException();
			}
		} catch (MalformedURLException e) {
			GUITARLog.log.error(e);
			throw new ApplicationConnectException();
		} catch (ClassNotFoundException e) {
			GUITARLog.log.error(e);
			throw new ApplicationConnectException();
		}

	}

}
