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
	
	@Override
	public void setUp() {

		// Init commClient
		commClient = new CommClient(IphRipperConfiguration.SERVER_HOST, IphRipperConfiguration.PORT);
		
		// Set up parameters
		sIgnoreWindowList = IphConstants.sIgnoredWins;

		// Start the application
		IphApplication application;
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

		}  catch (ApplicationConnectException e) {
			// TODO Auto-generated catch block
			GUITARLog.log.error(e);
		}

	}

	@Override
	public List<GWindow> getRootWindows() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isNewWindowOpened() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWindowClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LinkedList<GWindow> getOpenedWindowCache() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetWindowCache() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeWindow(GWindow window) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void expandGUI(GComponent component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	boolean isExpandable(GComponent component, GWindow window) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	LinkedList<GWindow> getClosedWindowCache() {
		// TODO Auto-generated method stub
		return null;
	}

}
