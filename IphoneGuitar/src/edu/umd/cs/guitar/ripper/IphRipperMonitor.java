package edu.umd.cs.guitar.ripper;

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.accessibility.AccessibleAction;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleText;
import javax.imageio.ImageIO;

import org.netbeans.jemmy.EventTool;

import edu.umd.cs.guitar.event.EventManager;
import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.event.JFCActionEDT;
import edu.umd.cs.guitar.event.JFCEventHandler;
import edu.umd.cs.guitar.exception.ApplicationConnectException;
import edu.umd.cs.guitar.model.CommClient;
import edu.umd.cs.guitar.model.GApplication;
import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.IphApplication;
import edu.umd.cs.guitar.model.IphComponent;
import edu.umd.cs.guitar.model.IphConstants;
import edu.umd.cs.guitar.model.IphWindow;
import edu.umd.cs.guitar.model.JFCApplication;
import edu.umd.cs.guitar.model.JFCConstants;
import edu.umd.cs.guitar.model.JFCXComponent;
import edu.umd.cs.guitar.model.JFCXWindow;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * 
 * Monitor for the ripper to handle Java Swing specific features
 * 
 * @see GRipperMonitor
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class IphRipperMonitor extends GRipperMonitor {

	// --------------------------
	// Configuartion Parameters
	// --------------------------

	/**
     * 
     */
	private static final int INITIAL_DELAY = 1000;

	// Logger logger;
	IphRipperConfiguration configuration;

	List<String> sIgnoreWindowList = new ArrayList<String>();
	
	// Communication Client
	CommClient commClient;
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

	List<String> sRootWindows = new ArrayList<String>();

	/**
	 * Temporary list of windows opened during the expand event is being
	 * performed. Those windows are in a native form to prevent data loss.
	 * 
	 */
	volatile LinkedList<Window> tempOpenedWinStack = new LinkedList<Window>();

	volatile LinkedList<Window> tempClosedWinStack = new LinkedList<Window>();

	// volatile LinkedList<GWindow> tempGWinStack = new LinkedList<GWindow>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.ripper.RipperMonitor#cleanUp()
	 */
	@Override
	public void cleanUp() {
		// Debugger.pause("Clean up pause....");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.ripper.RipperMonitor#closeWindow(edu.umd.cs.guitar.
	 * model.GXWindow)
	 */
	@Override
	public void closeWindow(GWindow gWindow) {

		IphWindow jWindow = (IphWindow) gWindow;
		Window window = jWindow.getWindow();

		// TODO: A bug might happen here, will fix later
		// window.setVisible(false);
		window.dispose();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.ripper.RipperMonitor#expand(edu.umd.cs.guitar.model
	 * .GXComponent)
	 */
	@Override
	public void expandGUI(GComponent component) {
/*
		if (component == null)
			return;

		GUITARLog.log.info("Expanding *" + component.getTitle() + "*...");

		// GThreadEvent action = new IphActionHandler();
		GEvent action = new IphActionEDT();

		action.perform(component, null);
		GUITARLog.log.info("Waiting  " + configuration.DELAY
				+ "ms for a new window to open");

		new EventTool().waitNoEvent(configuration.DELAY);
		*/
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.ripper.RipperMonitor#getOpenedWindowCache()
	 */
	@Override
	public LinkedList<GWindow> getOpenedWindowCache() {

		LinkedList<GWindow> retWindows = new LinkedList<GWindow>();

		for (Window window : tempOpenedWinStack) {
			GWindow gWindow = new IphWindow(window);
			if (gWindow.isValid())
				retWindows.addLast(gWindow);
		}
		return retWindows;
	}

	@Override
	public LinkedList<GWindow> getClosedWindowCache() {

		LinkedList<GWindow> retWindows = new LinkedList<GWindow>();

		for (Window window : tempClosedWinStack) {
			GWindow gWindow = new IphWindow(window);
			if (gWindow.isValid())
				retWindows.addLast(gWindow);
		}
		return retWindows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.ripper.RipperMonitor#getRootWindows()
	 */
	@Override
	public List<GWindow> getRootWindows() {

		List<GWindow> retWindowList = new ArrayList<GWindow>();

		retWindowList.clear();

		Frame[] lFrames = Frame.getFrames();

		for (Frame frame : lFrames) {

			if (!isValidRootWindow(frame))
				continue;

			AccessibleContext xContext = frame.getAccessibleContext();
			String sWindowName = xContext.getAccessibleName();

			if (sRootWindows.size() == 0
					|| (sRootWindows.contains(sWindowName))) {

				GWindow gWindow = new IphWindow(frame);
				retWindowList.add(gWindow);
				// frame.requestFocus();
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

	/**
	 * Check if a root window is worth ripping
	 * 
	 * <p>
	 * 
	 * @param window
	 *            the window to consider
	 * @return true/false
	 */
	private boolean isValidRootWindow(Frame window) {

		// Check if window is valid
		// if (!window.isValid())
		// return false;

		// Check if window is visible
		if (!window.isVisible())
			return false;

		// Check if window is on screen
		// double nHeight = window.getSize().getHeight();
		// double nWidth = window.getSize().getWidth();
		// if (nHeight <= 0 || nWidth <= 0)
		// return false;

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.ripper.RipperMonitor#isExpandable(edu.umd.cs.guitar
	 * .model.GXComponent, edu.umd.cs.guitar.model.GXWindow)
	 */
	@Override
	boolean isExpandable(GComponent gComponent, GWindow window) {

		IphComponent jComponent = (IphComponent) gComponent;
		// Accessible aComponent = jComponent.getAComponent();
		//
		// if (aComponent == null)
		// return false;

		Component component = jComponent.getComponent();
		AccessibleContext aContext = component.getAccessibleContext();

		String ID = gComponent.getTitle();
		if (ID == null)
			return false;

		if ("".equals(ID))
			return false;

		if (!gComponent.isEnable()) {
			GUITARLog.log.debug("Component is disabled");
			return false;
		}

		if (!isClickable(component)) {
			return false;
		}

		if (gComponent.getTypeVal().equals(GUITARConstants.TERMINAL))
			return false;

		// // Check for more details
		// AccessibleContext aContext = component.getAccessibleContext();

		if (aContext == null)
			return false;

		AccessibleText aText = aContext.getAccessibleText();

		if (aText != null)
			return false;

		return true;
	}

	/**
	 * Check if a component is click-able.
	 * 
	 * @param component
	 * @return true/false
	 */
	private boolean isClickable(Component component) {

		AccessibleContext aContext = component.getAccessibleContext();

		if (aContext == null)
			return false;

		AccessibleAction action = aContext.getAccessibleAction();

		if (action == null)
			return false;

		return true;
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
	 * @see edu.umd.cs.guitar.ripper.RipperMonitor#isNewWindowOpened()
	 */
	@Override
	public boolean isNewWindowOpened() {
		return (tempOpenedWinStack.size() > 0);
		// return (tempGWinStack.size() > 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.ripper.RipperMonitor#resetWindowCache()
	 */
	@Override
	public void resetWindowCache() {
		this.tempOpenedWinStack.clear();
		this.tempClosedWinStack.clear();
	}

	public class WindowOpenListener implements AWTEventListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.AWTEventListener#eventDispatched(java.awt.AWTEvent)
		 */
		@Override
		public void eventDispatched(AWTEvent event) {

			switch (event.getID()) {
			case WindowEvent.WINDOW_OPENED:
				processWindowOpened((WindowEvent) event);
				break;
			case WindowEvent.WINDOW_ACTIVATED:
			case WindowEvent.WINDOW_DEACTIVATED:
			case WindowEvent.WINDOW_CLOSING:
				processWindowClosed((WindowEvent) event);
				break;

			default:
				break;
			}

		}

		/**
		 * @param event
		 */
		private void processWindowClosed(WindowEvent wEvent) {
			Window window = wEvent.getWindow();
			tempClosedWinStack.add(window);
		}

		/**
		 * @param wEvent
		 */
		private void processWindowOpened(WindowEvent wEvent) {
			Window window = wEvent.getWindow();
			tempOpenedWinStack.add(window);
		}
	}

	Toolkit toolkit;

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.ripper.RipperMonitor#setUp()
	 */
	
	@Override
	public void setUp() {

		// Registering default supported events
/*
		EventManager em = EventManager.getInstance();

		for (Class<? extends IphEventHandler> event : IphConstants.DEFAULT_SUPPORTED_EVENTS) {
			try {
				em.registerEvent(event.newInstance());
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Registering customized supported event
		Class<? extends GEvent> gCustomizedEvents;

		String[] sCustomizedEventList;
		if (IphRipperConfiguration.CUSTOMIZED_EVENT_LIST != null)
			sCustomizedEventList = IphRipperConfiguration.CUSTOMIZED_EVENT_LIST
					.split(GUITARConstants.CMD_ARGUMENT_SEPARATOR);
		else
			sCustomizedEventList = new String[0];

		for (String sEvent : sCustomizedEventList) {
			try {
				Class<? extends GEvent> cEvent = (Class<? extends GEvent>) Class
						.forName(sEvent);
				em.registerEvent(cEvent.newInstance());
			} catch (ClassNotFoundException e) {
				GUITARLog.log.error(e);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
*/
		// Init commClient
		commClient = new CommClient(IphRipperConfiguration.SERVER_HOST, IphRipperConfiguration.PORT);
		
		// Set up parameters
		sIgnoreWindowList = IphConstants.sIgnoredWins;

		// Start the application
		IphApplication application;
		try {
			String[] URLs;
			if (IphRipperConfiguration.URL_LIST != null)
				URLs = IphRipperConfiguration.URL_LIST
						.split(GUITARConstants.CMD_ARGUMENT_SEPARATOR);
			else
				URLs = new String[0];

			application = new IphApplication(IphRipperConfiguration.MAIN_CLASS, IphRipperConfiguration.USE_JAR,
					URLs);

			// Parsing arguments
			String[] args;
			if (IphRipperConfiguration.ARGUMENT_LIST != null)
				args = IphRipperConfiguration.ARGUMENT_LIST
						.split(GUITARConstants.CMD_ARGUMENT_SEPARATOR);
			else
				args = new String[0];
			GUITARLog.log.debug("Requesting server host:" + this.configuration.SERVER_HOST);
			GUITARLog.log.debug("Requesting server port:" + this.configuration.PORT);
			application.connect(args, commClient);

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

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			GUITARLog.log.error(e);
		} catch (ApplicationConnectException e) {
			// TODO Auto-generated catch block
			GUITARLog.log.error(e);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			GUITARLog.log.error(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// -----------------------------
		// Assign listener
		toolkit = java.awt.Toolkit.getDefaultToolkit();

		WindowOpenListener listener = new WindowOpenListener();
		toolkit.addAWTEventListener(listener, AWTEvent.WINDOW_EVENT_MASK);

	}

	/**
	 * 
	 * Add a root window to be ripped
	 * 
	 * <p>
	 * 
	 * @param sWindowName
	 *            the window name
	 */
	public void addRootWindow(String sWindowName) {
		this.sRootWindows.add(sWindowName);
	}

	boolean flagWindowClosed;

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.ripper.GRipperMonitor#isWindowClose()
	 */
	@Override
	public boolean isWindowClosed() {
		return (tempClosedWinStack.size() > 0);
	}

	
	 // ---------------------------------------
	 // Capture images
	 private void captureImage(GComponent component) {
	 // Toolkit.getDefaultToolkit().get
	 Robot robot;
	
	 try {
	 robot = new Robot();
	 IphComponent gComp = (IphComponent ) component;
	 Component comp = gComp.getComponent();
	
	 Point pos = comp.getLocationOnScreen();
	 Dimension dim = comp.getSize();
	 Rectangle bounder = new Rectangle(pos, dim);
	
	 BufferedImage screenshot = robot.createScreenCapture(bounder);
	 File outputfile = new File("images/" + gComp.getTitle() + ".png");
	 ImageIO.write(screenshot, "png", outputfile);
	
	 } catch (IOException e) {
	
	 } catch (AWTException e) {
	 // TODO Auto-generated catch block
	 GUITARLog.log.error(e);
	 } catch (Exception e) {
	 GUITARLog.log.error(e);
	 }
	
	 }
}
