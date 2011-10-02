package edu.umd.cs.guitar.model;
import edu.umd.cs.guitar.model.GWindow;

import java.awt.Frame;
import java.awt.Window;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import edu.umd.cs.guitar.exception.ApplicationConnectException;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * Implementation for {@link GApplication} for Java Swing
 * 
 * @see GApplication
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class IphApplication extends GApplication {

	int iInitialDelay;

	public Set<GWindow> allWindows;
	
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

	@Override
	public void connect(String[] args) throws ApplicationConnectException {
		GUITARLog.log.debug("=============================");
		GUITARLog.log.debug("Application Parameters: ");
		GUITARLog.log.debug("-----------------------------");
		for (int i = 0; i < args.length; i++)
			GUITARLog.log.debug("\t" + args[i]);
		GUITARLog.log.debug("");
		
		IphCommServer.setUpIServerSocket();
		if (IphCommServer.waitForConnection()) {
			IphCommServer.request(IphCommServerConstants.INVOKE_MAIN_METHOD);
			if (IphCommServer.hear() == IphCommServerConstants.APP_LAUNCHED) {
				System.out.println("The application has been launched!");
			}
		} else {
			System.out.println("Connection failed! System is exiting!");
			System.exit(1);
		}
		
		try {
			Thread.sleep(iInitialDelay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			GUITARLog.log.error(e);
		}
	}

	// Complete - Rongjian Lan
	@Override
	public Set<GWindow> getAllWindow() {
		Set<GWindow> retWindows = new HashSet<GWindow>();
		
		ArrayList<IphWindow> windows = new ArrayList<IphWindow>();
		IphCommServer.requestMainView(windows);
		for (IphWindow iWindow : windows) {
			retWindows.addAll(getAllOwnedWindow(iWindow.getTitle()));
		}
		
		return retWindows;
	}
	
	// Complete - Rongjian Lan
	public Set<GWindow> getAllOwnedWindow(String viewTitle) {
		Set<GWindow> retWindows = new HashSet<GWindow>();
		ArrayList<IphWindow> windows = new ArrayList<IphWindow>();
		IphCommServer.requestAllOwnedView(windows, viewTitle);
		retWindows.addAll(windows);
		for (IphWindow iWindow : windows) {
			retWindows.addAll(getAllOwnedWindow(iWindow.getTitle()));
		}
		return retWindows;
	}

}
