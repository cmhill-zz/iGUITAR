/*  
 *  Copyright (c) 2009-@year@. The GUITAR group at the University of Maryland. Names of owners of this group may
 *  be obtained by sending an e-mail to atif@cs.umd.edu
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 *  documentation files (the "Software"), to deal in the Software without restriction, including without 
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *  the Software, and to permit persons to whom the Software is furnished to do so, subject to the following 
 *  conditions:
 * 
 *  The above copyright notice and this permission notice shall be included in all copies or substantial 
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 *  LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 *  EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 *  THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package edu.umd.cs.guitar.replayer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.kohsuke.args4j.CmdLineException;
import org.xml.sax.SAXException;

//import com.sun.net.ssl.internal.ssl.Debug;

import edu.umd.cs.guitar.exception.GException;
import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.JFCConstants;
import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentListType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.Configuration;
import edu.umd.cs.guitar.model.data.FullComponentType;
import edu.umd.cs.guitar.model.data.TestCase;
import edu.umd.cs.guitar.model.wrapper.AttributesTypeWrapper;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.replayer.monitor.GTestMonitor;
import edu.umd.cs.guitar.replayer.monitor.StateMonitor;
import edu.umd.cs.guitar.replayer.monitor.StateMonitorFull;
import edu.umd.cs.guitar.replayer.monitor.TimeMonitor;
import edu.umd.cs.guitar.util.Debugger;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class JFCReplayer {

    JFCReplayerConfiguration CONFIG;

    public void execute() throws CmdLineException {
        long nStartTime = System.currentTimeMillis();
        checkArgs();
        setupEnv();

        System.setProperty("file.name", JFCReplayerConfiguration.LOG_FILE);

        PropertyConfigurator.configure(JFCConstants.LOG4J_PROPERTIES_FILE);
        GUITARLog.log = Logger.getLogger(JFCReplayerMain.class.getSimpleName());

        printInfo();

        TestCase tc = (TestCase) IO.readObjFromFile(JFCReplayerConfiguration.TESTCASE,
                TestCase.class);
        
        Replayer replayer;
        try {
            
            if(tc ==null){
                GUITARLog.log.error("Test case not found");
                throw new FileNotFoundException();
            }
            
            replayer = new Replayer(tc, JFCReplayerConfiguration.GUI_FILE,
                    JFCReplayerConfiguration.EFG_FILE);
            GReplayerMonitor jMonitor = new JFCReplayerMonitor(
                    JFCReplayerConfiguration.MAIN_CLASS);

            // GTestMonitor tempMonitor = new JFCDebugMonitor();
            // replayer.addTestMonitor(tempMonitor);
            
            
            GTestMonitor timeoutMonitor = new TimeMonitor(
                    JFCReplayerConfiguration.TESTSTEP_TIMEOUT,
                    JFCReplayerConfiguration.TESTCASE_TIMEOUT);
            replayer.addTestMonitor(timeoutMonitor);

            GTestMonitor stateMonitor = new StateMonitorFull(
                    JFCReplayerConfiguration.GUI_STATE_FILE);
            replayer.addTestMonitor(stateMonitor);

            replayer.setMonitor(jMonitor);
            replayer.setTimeOut(JFCReplayerConfiguration.TESTCASE_TIMEOUT);

            try {
                replayer.execute();

            } catch (GException e) {
                GUITARLog.log.error("GUITAR Exception", e);
            }

        } catch (ParserConfigurationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (SAXException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        GUITARLog.log.info("GUI state file: "
                + JFCReplayerConfiguration.GUI_STATE_FILE);

        // ------------------
        // Elapsed time:
        long nEndTime = System.currentTimeMillis();
        long nDuration = nEndTime - nStartTime;
        DateFormat df = new SimpleDateFormat("HH : mm : ss: SS");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        GUITARLog.log.info("Time Elapsed: " + df.format(nDuration));
    }

    /**
     * 
     */
    private void printInfo() {
        GUITARLog.log.info("Testcase: " + JFCReplayerConfiguration.TESTCASE);
    }

    /**
     * 
     * Check for command-line arguments
     * 
     * @throws CmdLineException
     * 
     */
    private void checkArgs() throws CmdLineException {
        // Check argument
        if (JFCReplayerConfiguration.HELP) {
            throw new CmdLineException("");
        }

        boolean isPrintUsage = false;

        if (JFCReplayerConfiguration.MAIN_CLASS == null) {
            System.err.println("missing '-c' argument");
            isPrintUsage = true;
        }

        if (JFCReplayerConfiguration.GUI_FILE == null) {
            System.err.println("missing '-g' argument");
            isPrintUsage = true;
        }

        if (JFCReplayerConfiguration.EFG_FILE == null) {
            System.err.println("missing '-e' argument");
            isPrintUsage = true;
        }

        if (JFCReplayerConfiguration.TESTCASE == null) {
            System.err.println("missing '-t' argument");
            isPrintUsage = true;
        }

        if (isPrintUsage)
            throw new CmdLineException("");

    }

    /**
     * @param configuration
     */
    public JFCReplayer(JFCReplayerConfiguration configuration) {
        super();
        this.CONFIG = configuration;
    }


    /**
     * 
     */
    private void setupEnv() {
        // --------------------------
        // Terminal list
        Configuration conf = (Configuration) IO.readObjFromFile(
                JFCReplayerConfiguration.CONFIG_FILE, Configuration.class);

        List<FullComponentType> cTerminalList = conf.getTerminalComponents()
                .getFullComponent();

        for (FullComponentType cTermWidget : cTerminalList) {
            ComponentType component = cTermWidget.getComponent();
            AttributesType attributes = component.getAttributes();
            if (attributes != null)
                JFCConstants.sTerminalWidgetSignature
                        .add(new AttributesTypeWrapper(component
                                .getAttributes()));
        }

        List<FullComponentType> lIgnoredComps = new ArrayList<FullComponentType>();
        List<String> ignoredWindow = new ArrayList<String>();

        ComponentListType ignoredAll = conf.getIgnoredComponents();

        if (ignoredAll != null)
            for (FullComponentType fullComp : ignoredAll.getFullComponent()) {
                ComponentType comp = fullComp.getComponent();

                // TODO: Shortcut here
                if (comp == null) {
                    ComponentType win = fullComp.getWindow();
                    ComponentTypeWrapper winAdapter = new ComponentTypeWrapper(
                            win);
                    String ID = winAdapter
                            .getFirstValueByName(GUITARConstants.ID_TAG_NAME);
                    if (ID != null)
                        JFCConstants.sIgnoredWins.add(ID);
                    
                } else
                    lIgnoredComps.add(fullComp);
            }
    }
    
}
