package edu.umd.cs.guitar.ripper;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import org.netbeans.jemmy.EventTool;

import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.event.IphEvent;

import edu.umd.cs.guitar.exception.ApplicationConnectException;
import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.IphApplication;
import edu.umd.cs.guitar.model.IphCommServer;
import edu.umd.cs.guitar.model.IphCommServerConstants;
import edu.umd.cs.guitar.model.IphComponent;
import edu.umd.cs.guitar.model.IphConstants;

import edu.umd.cs.guitar.util.GUITARLog;

/**
 * 
 * Monitor for the ripper to handle Iphone specific features
 * 
 * @see GRipperMonitor
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Author </a>
 */
public class IphRipperMonitor extends GRipperMonitor {
	// Logger logger;
	IphRipperConfiguration configuration;

	List<String> sIgnoreWindowList = new ArrayList<String>();
	List<String> sRootWindows = new ArrayList<String>();
	
	
	public IphApplication application;

	
	/**
	 * Temporary list of windows opened during the expand event is being
	 * performed. Those windows are in a native form to prevent data loss.
	 * 
	 */
	volatile LinkedList<GWindow> tempOpenedWinStack = new LinkedList<GWindow>();

	volatile LinkedList<GWindow> tempClosedWinStack = new LinkedList<GWindow>();
	
	private static final int INITIAL_DELAY = 1000;

	
	
	/**
	 * Constructor
	 * 
	 * <p>
	 * 
	 * @param configuration
	 *            ripper configuration
	 */
	public IphRipperMonitor(IphRipperConfiguration configuration) {
		super();
		// this.logger = logger;
		this.configuration = configuration;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.ripper.RipperMonitor#isIgnoredWindow(edu.umd.cs.guitar
	 * .model.GXWindow)
	 */
	@Override
	public boolean isIgnoredWindow(GWindow window) {
		String sWindow = window.getTitle();
		// TODO: Ignore template
		return (this.sIgnoreWindowList.contains(sWindow));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.ripper.RipperMonitor#setUp()
	 */
	
	// Complete - Rongjian Lan
	@Override
	public void setUp() {

		// Set up parameters
		sIgnoreWindowList = IphConstants.sIgnoredWins;

		// Start the application
		
		
		try {
			
			application = new IphApplication();
			
			// Parsing arguments
			String[] args;
			if (IphRipperConfiguration.ARGUMENT_LIST != null)
				args = IphRipperConfiguration.ARGUMENT_LIST
						.split(GUITARConstants.CMD_ARGUMENT_SEPARATOR);
			else
				args = new String[0];
			
			GUITARLog.log.debug("Requesting server host:" + this.configuration.SERVER_HOST);
			GUITARLog.log.debug("Requesting server port:" + this.configuration.PORT);
			
			application.connect(args);

			// Delay
			try {
				GUITARLog.log
						.info("Initial waiting: "
								+ IphRipperConfiguration.INITIAL_WAITING_TIME
								+ "ms...");
				Thread.sleep(IphRipperConfiguration.INITIAL_WAITING_TIME);
			} catch (InterruptedException e) {
				GUITARLog.log.error(e);
			}

		}  catch (ApplicationConnectException e) {
			// TODO Auto-generated catch block
			GUITARLog.log.error(e);
		}

	}
	
	//Complete - Rongjian Lan
	@Override
	public List<GWindow> getRootWindows() {
		
		List<GWindow> retWindowList = new ArrayList<GWindow>();

		retWindowList.clear();
		if (application != null) {
			if (application.allWindows == null) {
				application.allWindows = application.getAllWindow();
			}
			for (GWindow window : application.allWindows) {
				System.out.println("Checking window : " + window.getTitle() + 
						" (isRoote : " + window.isRoot() + " | isValid : " + window.isValid() + ")");
				if (window.isRoot() && window.isValid()) {
					retWindowList.add(window);
				}
			}
		}
		

		// / Debugs:
		GUITARLog.log.debug("Root window size: " + retWindowList.size());
		for (GWindow window : retWindowList) {
			GUITARLog.log.debug("Window title: " + window.getTitle());
		}

		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			GUITARLog.log.error(e);
		}
		return retWindowList;
	}

	// Preliminary implementation - Rongjian Lan
	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		application = null;
		IphCommServer.close();
	}

	// Complete - Rongjian Lan
	@Override
	public boolean isNewWindowOpened() {
		return (tempOpenedWinStack.size() > 0);
	}

	// Complete - Rongjian Lan
	@Override
	public boolean isWindowClosed() {
		return (tempClosedWinStack.size() > 0);
	}

	// Complete - Rongjian Lan
	@Override
	public LinkedList<GWindow> getOpenedWindowCache() {
		LinkedList<GWindow> retWindows = new LinkedList<GWindow>();
		
		for (GWindow gWindow : tempOpenedWinStack) {
			if (gWindow.isValid())
				retWindows.addLast(gWindow);
		}
		
		return retWindows;
	}

	// Complete - Rongjian Lan
	@Override
	public void resetWindowCache() {
		tempOpenedWinStack.clear();
		tempClosedWinStack.clear();
	}

	// Complete - Rongjian lan
	@Override
	public void closeWindow(GWindow window) {
		if (window == null) 
			return;
		
		if (tempOpenedWinStack.contains(window.getTitle())) {
			IphCommServer.request(IphCommServerConstants.CLOSE_WINDOW + window.getTitle());
			tempClosedWinStack.add(window);
		}		
	}

	// Complete -Rongjian Lan
	@Override
	public void expandGUI(GComponent component) {
		if (component == null)
			return;

		GUITARLog.log.info("Expanding *" + component.getTitle() + "*...");
		
		GEvent action = new IphEvent();

		action.perform(component, null);
		GUITARLog.log.info("Waiting  " + configuration.DELAY
				+ "ms for a new window to open");

		new EventTool().waitNoEvent(configuration.DELAY);
	}

	// Preliminary implementation - Rongjian Lan
	@Override
	boolean isExpandable(GComponent gComponent, GWindow window) {
		IphComponent component = (IphComponent) gComponent;
		String ID = component.getTitle();
		
		System.out.println("Title : " + ID);
		if (ID == null)
			return false;

		if (ID.equals(""))
			return false;

		if (!component.isEnable()) {
			GUITARLog.log.debug("Component is disabled");
			return false;
		}

		if (!component.isClickable())
			return false;

		if (component.getTypeVal().equals(GUITARConstants.TERMINAL))
			return false;

		return true;
	}

	// Complete - Rongjian Lan
	@Override
	LinkedList<GWindow> getClosedWindowCache() {
		LinkedList<GWindow> retWindows = new LinkedList<GWindow>();
		
		for (GWindow gWindow : tempClosedWinStack) {
			if (gWindow.isValid())
				retWindows.addLast(gWindow);
		}
		
		return retWindows;
	}

}
