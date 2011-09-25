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

	
	//To be modified to Iphone version
	/**
	 * This class loader dynamically load jar file to the current classpath
	 * 
	 */
	private class RuntimeJarFileLoader extends URLClassLoader {
		public RuntimeJarFileLoader(URL[] urls) {
			super(urls);
		}

		/**
		 * add the Jar file to the classpath
		 * 
		 * @param path
		 * @throws MalformedURLException
		 */
		public void addFile(String path) throws MalformedURLException {
			// construct the jar url path
			String urlPath = "jar:file:" + path + "!/";

			// invoke the base method
			addURL(new URL(urlPath));
		}

		/**
		 * add the Jar file to the classpath
		 * 
		 * @param path
		 * @throws MalformedURLException
		 */
		public void addFile(String paths[]) throws MalformedURLException {
			if (paths != null)
				for (int i = 0; i < paths.length; i++)
					addFile(paths[i]);
		}
	}

	/**
	 * Application with jar file
	 * 
	 * @param entrance
	 *            either main jar file path or main class name
	 * @param useJar
	 *            true if <code> entrance </code> is a jar file
	 * @param URLs
	 *            application URLs
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public IphApplication(String entrance, boolean useJar, String[] URLs)
			throws ClassNotFoundException, IOException {
		super();

		// main class name to start the application
		String mainClass;

		// check if main class is loaded from a jar file

		if (useJar) {

			// TODO: is method is not stable yet and required the jar file
			// already exist in the class path. Need to dynamically add the
			// current jar to the classpath
			// initialize with empty path

			// get jar file name

			// These code tries to load jar file dynamically but not successful
			// yet
			/*
			 * String jarFilePath = entrance; File jarFile = new
			 * File(jarFilePath); String jarFileAbsPath =
			 * jarFile.getAbsolutePath(); String jarFileURLPath = "jar:file:/" +
			 * jarFileAbsPath + "!/"; URL jarFileURL= new URL(jarFileURLPath);
			 * // load current classloader URLClassLoader currentClassLoader =
			 * (URLClassLoader) URLClassLoader .getSystemClassLoader();
			 * 
			 * URL[] curURLs = currentClassLoader.getURLs(); URL[] newURLs = new
			 * URL[curURLs.length+1];
			 * 
			 * for (int i = 0;i <curURLs.length;i++){ newURLs[i] = curURLs[i]; }
			 * newURLs[newURLs.length-1] = jarFileURL;
			 * 
			 * currentClassLoader = new URLClassLoader(newURLs,
			 * URLClassLoader.getSystemClassLoader());
			 * 
			 * Thread.currentThread().setContextClassLoader(currentClassLoader);
			 * 
			 * for (URL url: ((URLClassLoader)
			 * Thread.currentThread().getContextClassLoader()).getURLs()){
			 * System.out.println(url.getPath()); }
			 */
			// TODO: do a more comprehensive manifest parsing rather than just
			// getting the main class name
			InputStream is;
			is = new FileInputStream(entrance);
			JarInputStream jarStream = new JarInputStream(is);
			Manifest mf = (Manifest) jarStream.getManifest();

			Attributes attributes = mf.getMainAttributes();
			mainClass = (attributes.getValue("Main-Class"));

		} else {
			mainClass = entrance;
		}

		//this.cClass = initilizeMainClass(mainClass, URLs);

	}

	/**
	 * Initialize the main class
	 * <p>
	 * 
	 * @param sClassName
	 * @param sURLs
	 * @return
	 * @throws MalformedURLException
	 * @throws ClassNotFoundException
	 */
	private Class<?> initilizeMainClass(String sClassName, String[] sURLs)
			throws MalformedURLException, ClassNotFoundException {

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
					break;
				}
			}
		}

		URL[] arrayURLs = (lURLs.toArray(new URL[lURLs.size()]));
		// ---------------
		URLClassLoader loader = new URLClassLoader(arrayURLs);

		return Class.forName(sClassName, true, loader);
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
	public void connect(String[] args, CommClient cc) throws ApplicationConnectException {

		GUITARLog.log.debug("=============================");
		GUITARLog.log.debug("Application Parameters: ");
		GUITARLog.log.debug("-----------------------------");
		for (int i = 0; i < args.length; i++)
			GUITARLog.log.debug("\t" + args[i]);
		GUITARLog.log.debug("");

		Method method;
		
		//CommClient cc = new CommClient();
		cc.connect();
		try {
			while (System.in.read() != 0)
			System.out.println(cc.request(CommConstants.INVOKE_MAIN_METHOD));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Requesting Main Method!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Rongjian Lan");
		
//		try {
//			System.out.println("Requesting Main Method!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Rongjian Lan");
//			
//			method = cClass.getMethod("main", new Class[] { String[].class });
//
//			GUITARLog.log.debug("Main method FOUND!");
//
//			if (method != null) {
//				method.invoke(null, new Object[] { args });
//				GUITARLog.log.debug("Main method INVOKED!");
//			}
//
//			else
//				throw new ApplicationConnectException();
//
//		} catch (NoSuchMethodException e) {
//			GUITARLog.log
//					.debug("Coundn't find main method for the application");
//			GUITARLog.log.error(e);
//		} catch (IllegalAccessException e) {
//			GUITARLog.log.error(e);
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			GUITARLog.log.error(e);
//		}

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

	@Override
	public void connect(String[] args) throws ApplicationConnectException {
		// TODO Auto-generated method stub
		
	}
}
