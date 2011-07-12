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
package edu.umd.cs.guitar.model;

import java.awt.Frame;
import java.awt.Window;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.umd.cs.guitar.exception.ApplicationConnectException;
import edu.umd.cs.guitar.util.Debugger;
import edu.umd.cs.guitar.util.GUITARLog;
import edu.umd.cs.guitar.model.JFCXWindow;

/**
 * Implementation for {@link GApplication} for Java Swing
 * 
 * @see GApplication
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class JFCApplication extends GApplication {

	private Class<?> cClass;
	String sClassName;
	int iInitialDelay;

	/**
	 * @param sClassName
	 * @param iInitalDelay
	 * @throws ClassNotFoundException
	 */
	@Deprecated
	public JFCApplication(String sClassName, int iInitialDelay)
			throws ClassNotFoundException {
		super();
		this.cClass = Class.forName(sClassName);
		this.sClassName = sClassName;
		this.iInitialDelay = iInitialDelay;
	}

	final String[] URL_PREFIX = { "file:", "jar:", "http:" };

	/**
	 * @param sClassName
	 * @param sURLs
	 * @throws ClassNotFoundException
	 * @throws MalformedURLException
	 */
	public JFCApplication(String sClassName, String[] sURLs)
			throws ClassNotFoundException, MalformedURLException {
		super();

		Set<URL> lURLs = new HashSet<URL>();

		// System URLs
		URLClassLoader sysLoader = (URLClassLoader) ClassLoader
				.getSystemClassLoader();
		URL urls[] = sysLoader.getURLs();
		for (int i = 0; i < urls.length; i++) {
			lURLs.add(urls[i]);
		}

		// Additional URLs passed by arguments
		for (String sURL : sURLs) {
			for (String pref : URL_PREFIX) {
				if (sURL.startsWith(pref)) {

					URL appURL = new URL(sURL);
					lURLs.add(appURL);

					// GUITARLog.log.debug("GOT Application URL!!!!");
					// GUITARLog.log.debug("Original: " + sURL);
					// GUITARLog.log.debug("Converted: " + appURL.getPath());

					break;
				}
			}
		}

		URL[] arrayURLs = (URL[]) (lURLs.toArray(new URL[lURLs.size()]));
		// --------------
		GUITARLog.log.debug("=============================");
		GUITARLog.log.debug("Application URLs: ");
		GUITARLog.log.debug("-----------------------------");
		for (URL url : arrayURLs) {
			GUITARLog.log.debug("\t" + url.getPath());
		}
		GUITARLog.log.debug("");

		// ---------------

		URLClassLoader loader = new URLClassLoader(arrayURLs);
		this.cClass = Class.forName(sClassName, true, loader);
		// this.cClass = Class.forName(sClassName);
		this.sClassName = sClassName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.util.Application#start()
	 */
	@Override
	public void connect() throws ApplicationConnectException {
		String[] args = new String[0];
		connect(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.util.GApplication#start(java.lang.String[])
	 */
	@Override
	public void connect(String[] args) throws ApplicationConnectException {

		GUITARLog.log.debug("=============================");
		GUITARLog.log.debug("Application Parameters: ");
		GUITARLog.log.debug("-----------------------------");
		for (int i = 0; i < args.length; i++)
			GUITARLog.log.debug("\t" + args[i]);
		GUITARLog.log.debug("");

		Method method;

		
		try {
			method = cClass.getMethod("main", new Class[] { String[].class });
			
			GUITARLog.log.debug("Main method FOUND!");
			
			if (method != null) {
				method.invoke(null, new Object[] { args });
				GUITARLog.log.debug("Main method INVOKED!");
			}
			

			else
				throw new ApplicationConnectException();

			// } catch (SecurityException e) {
			// // TODO Auto-generated catch block
		} catch (NoSuchMethodException e) {
			GUITARLog.log
					.debug("Coundn't find main method for the application");
			GUITARLog.log.error(e);
		} catch (IllegalAccessException e) {
			GUITARLog.log.error(e);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			GUITARLog.log.error(e);
		}

		try {
			Thread.sleep(iInitialDelay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			GUITARLog.log.error(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.model.GApplication#getAllWindow()
	 */
	@Override
	public Set<GWindow> getAllWindow() {
		Frame[] windows = Frame.getFrames();

		Set<GWindow> retWindows = new HashSet<GWindow>();

		for (Frame aWindow : windows) {
			GWindow gWindow = new JFCXWindow(aWindow);
			if (gWindow.isValid())
				retWindows.add(gWindow);
			Set<GWindow> lOwnedWins = getAllOwnedWindow(aWindow);

			for (GWindow aOwnedWins : lOwnedWins) {

				if (aOwnedWins.isValid())
					retWindows.add(aOwnedWins);
			}

		}

		return retWindows;
	}

	private Set<GWindow> getAllOwnedWindow(Window parent) {
		Set<GWindow> retWindows = new HashSet<GWindow>();
		Window[] lOwnedWins = parent.getOwnedWindows();
		for (Window aOwnedWin : lOwnedWins) {
			retWindows.add(new JFCXWindow(aOwnedWin));
			Set<GWindow> lOwnedWinChildren = getAllOwnedWindow(aOwnedWin);

			retWindows.addAll(lOwnedWinChildren);
		}
		return retWindows;
	}

	// @Override
	// public GUIStructure getCurrentState() {
	//     
	// }

}
