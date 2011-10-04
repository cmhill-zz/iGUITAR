
package edu.umd.cs.guitar.ripper;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.kohsuke.args4j.CmdLineException;

import edu.umd.cs.guitar.model.GIDGenerator;
import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.IphConstants;
import edu.umd.cs.guitar.model.IphIDGenerator;
import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentListType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.Configuration;
import edu.umd.cs.guitar.model.data.FullComponentType;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.LogWidget;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.model.wrapper.AttributesTypeWrapper;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.ripper.filter.GComponentFilter;
import edu.umd.cs.guitar.ripper.filter.IphIgnoreSignExpandFilter;
import edu.umd.cs.guitar.ripper.filter.IphTabFilter;
import edu.umd.cs.guitar.util.DefaultFactory;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * 
 * Executing class for IphRipper
 * 
 * <p>
 * 
 * @author <a href="mailto:rjlan@cs.umd.edu"> Rongjian Lan </a>
 */
public class IphRipper {

	IphRipperConfiguration CONFIG;

	/**
	 * @param CONFIG
	 */
	public IphRipper(IphRipperConfiguration CONFIG) {
		super();
		this.CONFIG = CONFIG;
	}

	// Logger logger;

	/**
	 * Execute the iph ripper
	 * 
	 * <p>
	 * 
	 * @throws CmdLineException
	 * 
	 */
	Ripper ripper;

	public void execute() throws CmdLineException {

		if (CONFIG.help) {
			throw new CmdLineException("");
		}

		System.setProperty(GUITARLog.LOGFILE_NAME_SYSTEM_PROPERTY,
				IphRipperConfiguration.LOG_FILE);

		long nStartTime = System.currentTimeMillis();
		ripper = new Ripper(GUITARLog.log);

		// -------------------------
		// Setup configuration
		// -------------------------

		try {
			setupEnv();
			ripper.execute();
		} catch (Exception e) {
			GUITARLog.log.error("IphRipper: ", e);
			System.exit(1);
		}

		GUIStructure dGUIStructure = ripper.getResult();
		IO.writeObjToFile(dGUIStructure, IphRipperConfiguration.GUI_FILE);

		GUITARLog.log.info("-----------------------------");
		GUITARLog.log.info("OUTPUT SUMARY: ");
		GUITARLog.log.info("Number of Windows: "
				+ dGUIStructure.getGUI().size());
		GUITARLog.log.info("GUI file:" + IphRipperConfiguration.GUI_FILE);
		ComponentListType lOpenWins = ripper.getlOpenWindowComps();
		ComponentListType lCloseWins = ripper.getlCloseWindowComp();
		ObjectFactory factory = new ObjectFactory();

		LogWidget logWidget = factory.createLogWidget();
		logWidget.setOpenWindow(lOpenWins);
		logWidget.setCloseWindow(lCloseWins);

		//IO.writeObjToFile(logWidget, IphRipperConfiguration.LOG_WIDGET_FILE);

		// ------------------
		// Elapsed time:
		long nEndTime = System.currentTimeMillis();
		long nDuration = nEndTime - nStartTime;
		DateFormat df = new SimpleDateFormat("HH : mm : ss: SS");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		GUITARLog.log.info("Ripping Elapsed: " + df.format(nDuration));
		GUITARLog.log.info("Log file: " + IphRipperConfiguration.LOG_FILE);
	}

	/**
     * 
     */
	/**
	 * 
	 */
	private void setupEnv() {
		// --------------------------
		// Terminal list

		// Try to find absolute path first then relative path

		Configuration conf = null;

		try {
			conf = (Configuration) IO.readObjFromFile(
					IphRipperConfiguration.CONFIG_FILE, Configuration.class);

			if (conf == null) {
				InputStream in = getClass()
						.getClassLoader()
						.getResourceAsStream(IphRipperConfiguration.CONFIG_FILE);
				conf = (Configuration) IO.readObjFromFile(in,
						Configuration.class);
			}

		} catch (Exception e) {
			GUITARLog.log.error("No configuration file. Using an empty one...");
			// return;
		}

		if (conf == null) {
			DefaultFactory df = new DefaultFactory();
			conf = df.createDefaultConfiguration();
		}

		List<FullComponentType> cTerminalList = conf.getTerminalComponents()
				.getFullComponent();

		for (FullComponentType cTermWidget : cTerminalList) {
			ComponentType component = cTermWidget.getComponent();
			AttributesType attributes = component.getAttributes();
			if (attributes != null)
				IphConstants.sTerminalWidgetSignature
						.add(new AttributesTypeWrapper(component
								.getAttributes()));
		}

		GRipperMonitor iMonitor = new IphRipperMonitor(CONFIG);

		List<FullComponentType> lIgnoredComps = new ArrayList<FullComponentType>();
		// List<String> ignoredWindow = new ArrayList<String>();

		ComponentListType ignoredAll = conf.getIgnoredComponents();

		if (ignoredAll != null)
			for (FullComponentType fullComp : ignoredAll.getFullComponent()) {
				ComponentType comp = fullComp.getComponent();

				// TODO: Shortcut here
				if (comp == null) {
					ComponentType win = fullComp.getWindow();
					ComponentTypeWrapper winAdapter = new ComponentTypeWrapper(
							win);
					String sWindowTitle = winAdapter
							.getFirstValueByName(GUITARConstants.TITLE_TAG_NAME);
					if (sWindowTitle != null)
						IphConstants.sIgnoredWins.add(sWindowTitle);

				} else
					lIgnoredComps.add(fullComp);
			}

		// --------------------------
		// Ignore components xml
		GComponentFilter iIgnoreExpand = new IphIgnoreSignExpandFilter(
				lIgnoredComps);
		ripper.addComponentFilter(iIgnoreExpand);

		// Setup tab components ripper filter
		GComponentFilter iTab = IphTabFilter.getInstance();
		ripper.addComponentFilter(iTab);

		// Set up Monitor
		ripper.setMonitor(iMonitor);

		// Set up IDGenerator

		GIDGenerator iIDGenerator = IphIDGenerator.getInstance();
		ripper.setIDGenerator(iIDGenerator);

	}

}