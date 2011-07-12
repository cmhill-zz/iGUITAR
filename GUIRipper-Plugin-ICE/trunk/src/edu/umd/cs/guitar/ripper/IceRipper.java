package edu.umd.cs.guitar.ripper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.lf5.util.ResourceUtils;
import org.kohsuke.args4j.CmdLineException;

import edu.umd.cs.guitar.model.GIDGenerator;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.IceDefaultIDGenerator;
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
import edu.umd.cs.guitar.model.IceConstants;
import edu.umd.cs.guitar.util.GUITARLog;

public class IceRipper extends Ice.Application {
    private IceRipperConfiguration config;

    public IceRipper(IceRipperConfiguration config) {
        this.config = config;
    }

    private Ripper ripper;

    public int run(String[] args) {
        try {
            execute();
        } catch (CmdLineException e) {
            return 1;
        }

        return 0;
    }

    public void execute() throws CmdLineException {
        if (config.help) {
            throw new CmdLineException("");
        }

        System.setProperty("file.name", IceRipperConfiguration.LOG_FILE);
        PropertyConfigurator.configure(IceConstants.LOG4J_PROPERTIES_FILE);

        GUITARLog.log = Logger.getLogger(
            IceRipperMain.class.getSimpleName());
        long nStartTime = System.currentTimeMillis();
        ripper = new Ripper(GUITARLog.log);

        try {
            setupEnv();
            ripper.execute();
        } catch (Exception e) {
            GUITARLog.log.error("IceRipper: ", e);
        }

        GUIStructure dGUIStructure = ripper.getResult();
        IO.writeObjToFile(dGUIStructure, config.GUI_FILE);

        GUITARLog.log.info("-----------------------------");
        GUITARLog.log.info("OUTPUT SUMARY: ");
        GUITARLog.log.info("Number of Windows: "
                + dGUIStructure.getGUI().size());
        GUITARLog.log.info("GUI file:" + config.GUI_FILE);
        GUITARLog.log.info("Open Component file:" + config.LOG_WIDGET_FILE);
        ComponentListType lOpenWins = ripper.getlOpenWindowComps();
        ComponentListType lCloseWins = ripper.getlCloseWindowComp();
        ObjectFactory factory = new ObjectFactory();

        LogWidget logWidget = factory.createLogWidget();
        logWidget.setOpenWindow(lOpenWins);
        logWidget.setCloseWindow(lCloseWins);

        IO.writeObjToFile(logWidget, config.LOG_WIDGET_FILE);

        // ------------------
        // Elapsed time:
        long nEndTime = System.currentTimeMillis();
        long nDuration = nEndTime - nStartTime;
        DateFormat df = new SimpleDateFormat("HH : mm : ss: SS");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        GUITARLog.log.info("Ripping Elapsed: " + df.format(nDuration));
        GUITARLog.log.info("Log file: " + IceRipperConfiguration.LOG_FILE);
    }

    private void setupEnv() {
        // Load configuration file
        Configuration conf = (Configuration) IO.readObjFromFile(
            IceRipperConfiguration.CONFIG_FILE, Configuration.class);

        if (conf != null) {
            // Load the terminal/ignored windows from the configuration
            loadWidgetConfiguration(
                conf.getTerminalComponents().getFullComponent(),
                IceConstants.sTerminalWidgetSignature);
            loadWidgetConfiguration(
                conf.getIgnoredComponents().getFullComponent(),
                IceConstants.sIgnoredWidgetSignature);
        }

        GRipperMonitor monitor = new IceRipperMonitor(config);
        ripper.setMonitor(monitor);
        
        GIDGenerator idGenerator = IceDefaultIDGenerator.getInstance();
        ripper.setIDGenerator(idGenerator);
    }

    private void loadWidgetConfiguration(
        List<FullComponentType> widgetList,
        List<AttributesTypeWrapper> widgetSignatures)
    {
        for (FullComponentType widget : widgetList) {
            ComponentType component = widget.getWindow();
            if (component == null) {
                component = widget.getComponent();
            }
            AttributesType attributes = component.getAttributes();
            widgetSignatures.add(
                new AttributesTypeWrapper(component.getAttributes()));
        }
    }
}
