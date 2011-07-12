package edu.umd.cs.guitar.replayer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.kohsuke.args4j.CmdLineException;
import org.xml.sax.SAXException;

import edu.umd.cs.guitar.exception.GException;
import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.GIDGenerator;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.IceDefaultIDGenerator;
import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentListType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.Configuration;
import edu.umd.cs.guitar.model.data.FullComponentType;
import edu.umd.cs.guitar.model.data.TestCase;
import edu.umd.cs.guitar.model.wrapper.AttributesTypeWrapper;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.replayer.monitor.GTestMonitor;
import edu.umd.cs.guitar.replayer.monitor.PauseMonitor;
import edu.umd.cs.guitar.replayer.monitor.StateMonitorFull;
import edu.umd.cs.guitar.replayer.monitor.TimeMonitor;
import edu.umd.cs.guitar.model.IceConstants;
import edu.umd.cs.guitar.util.GUITARLog;

public class IceReplayer extends Ice.Application {

    IceReplayerConfiguration CONFIG;

    private void printInfo() {
        GUITARLog.log.info("Testcase: " + IceReplayerConfiguration.TESTCASE);
        GUITARLog.log.info("Log file: " + IceReplayerConfiguration.LOG_FILE);
        GUITARLog.log.info("GUI state file: " + IceReplayerConfiguration.GUI_STATE_FILE);
    }

    private void checkArgs() throws CmdLineException {
        if (GReplayerConfiguration.HELP) {
            throw new CmdLineException("");
        }

        if (IceReplayerConfiguration.GUI_FILE == null) {
            throw new CmdLineException("Missing GUI_FILE argument (-g, --gui-file)");
        }

        if (IceReplayerConfiguration.EFG_FILE == null) {
            throw new CmdLineException("Missing EFG_FILE argument (-e, --efg-file)");
        }

        if (IceReplayerConfiguration.TESTCASE == null) {
            throw new CmdLineException("Missing TESTCASE argument (-t, --test-case)");
        }
    }

    public IceReplayer(IceReplayerConfiguration configuration) {
        super();
        this.CONFIG = configuration;
    }

    public int run(String[] args) {
        int status;
        try {
            status = execute();
        } catch (CmdLineException e) {
            status = 1;
        }
        return status;
    }

    public int execute() throws CmdLineException, GException {
        long startTime = System.currentTimeMillis();
        checkArgs();
        setupEnv();

        System.setProperty("file.name", IceReplayerConfiguration.LOG_FILE);
        PropertyConfigurator.configure(IceConstants.LOG4J_PROPERTIES_FILE);

        GUITARLog.log = Logger.getLogger(
                            IceReplayerMain.class.getSimpleName());

        printInfo();

        TestCase tc = (TestCase) IO.readObjFromFile(
                          IceReplayerConfiguration.TESTCASE, TestCase.class);

        if (tc == null) {
            GUITARLog.log.error("Test case not found");
            System.exit(1);
        }

        int status;
        Replayer replayer;
        try {
            replayer = new Replayer(tc, IceReplayerConfiguration.GUI_FILE,
                                    IceReplayerConfiguration.EFG_FILE);
            GReplayerMonitor monitor = new IceReplayerMonitor();

            // Adds a GUI state record monitor
            StateMonitorFull stateMonitor = new StateMonitorFull(
                    IceReplayerConfiguration.GUI_STATE_FILE,
                    IceReplayerConfiguration.DELAY);

            // Set the id generator for the state monitor
            GIDGenerator idGenerator = IceDefaultIDGenerator.getInstance();
            stateMonitor.setIdGenerator(idGenerator);

            replayer.addTestMonitor(stateMonitor);

            // Add pause or timeout monitor
            if (IceReplayerConfiguration.PAUSE) {
                // Pause monitor
                replayer.addTestMonitor(new PauseMonitor());
            } else {
                // Timeout monitor
                replayer.addTestMonitor(new TimeMonitor(
                        IceReplayerConfiguration.TESTSTEP_TIMEOUT,
                        IceReplayerConfiguration.TESTCASE_TIMEOUT));
            }

            replayer.setMonitor(monitor);
            replayer.setTimeOut(IceReplayerConfiguration.TESTCASE_TIMEOUT);

            replayer.execute();
            status = 0;
        } catch (GException e) {
            GUITARLog.log.error("GUITAR Exception thrown", e);
            status = 1;
        } catch (ParserConfigurationException e) {
            /* Checked exceptions make me want to go murder a puppy */
            GUITARLog.log.error("Parser Configuration Error", e);
            status = 1;
        } catch (SAXException e) {
            /* I like puppies... */
            GUITARLog.log.error("SAX Exception Error", e);
            status = 1;
        } catch (IOException e) {
            /* I doubt this made me write less error-prone code */
            GUITARLog.log.error("IO Exception thrown", e);
            status = 1;
        } finally {
            // Elapsed time
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            DateFormat df = new SimpleDateFormat("HH : mm : ss : SS");
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            GUITARLog.log.info("Time Elapsed: " + df.format(duration));

            printInfo();
        }

        return status;
    }

    private void setupEnv() {
        // Load configuration file
        Configuration conf = (Configuration) IO.readObjFromFile(
            IceReplayerConfiguration.CONFIG_FILE, Configuration.class);

        if (conf != null) {
            // Load the terminal/ignored windows from the configuration
            loadWidgetConfiguration(
                conf.getTerminalComponents().getFullComponent(),
                IceConstants.sTerminalWidgetSignature);
            loadWidgetConfiguration(
                conf.getIgnoredComponents().getFullComponent(),
                IceConstants.sIgnoredWidgetSignature);
        }
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
