package edu.umd.cs.guitar.model;
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

	private Class<?> cClass;
	int iInitialDelay;

	final String[] URL_PREFIX = { "file:", "jar:", "http:" };

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
	public void connect(String[] args, CommClient commClient) throws ApplicationConnectException {

		GUITARLog.log.debug("=============================");
		GUITARLog.log.debug("Application Parameters: ");
		GUITARLog.log.debug("-----------------------------");
		for (int i = 0; i < args.length; i++)
			GUITARLog.log.debug("\t" + args[i]);
		GUITARLog.log.debug("");

		Method method;
		
		commClient.connect();
		
		try {
			while (System.in.read() != 0)
			System.out.println(commClient.request(CommConstants.INVOKE_MAIN_METHOD));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Requesting Main Method!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Rongjian Lan");

		try {
			Thread.sleep(iInitialDelay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			GUITARLog.log.error(e);
		}
	}

	@Override
	public void connect(String[] args) throws ApplicationConnectException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<GWindow> getAllWindow() {
		// TODO Auto-generated method stub
		return null;
	}

}
