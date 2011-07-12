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

package edu.umd.cs.guitar.ripper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.kohsuke.args4j.CmdLineException;

import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.JFCConstants;
import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentListType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.Configuration;
import edu.umd.cs.guitar.model.data.FullComponentType;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.model.wrapper.AttributesTypeWrapper;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * 
 * Executing class for JFCRipper
 * 
 * <p>
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class JFCRipper {

    JFCRipperConfiguration CONFIG;

    /**
     * @param CONFIG
     */
    public JFCRipper(JFCRipperConfiguration CONFIG) {
        super();
        this.CONFIG = CONFIG;
    }

    // Logger logger;

    /**
     * Execute the jfc ripper
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
        System.setProperty("file.name", CONFIG.LOG_FILE);
        PropertyConfigurator.configure(JFCConstants.LOG4J_PROPERTIES_FILE);

        GUITARLog.log = Logger.getLogger(JFCRipperMain.class.getSimpleName());
        long nStartTime = System.currentTimeMillis();

        ripper = new Ripper(GUITARLog.log);

        // -------------------------
        // Setup configuration
        // -------------------------

        try {
            setupEnv();
            ripper.execute();
        } catch (Exception e) {
            GUITARLog.log.error("JFCRipper: ", e);
        }

        GUIStructure dGUIStructure = ripper.getResult();
        IO.writeObjToFile(dGUIStructure, CONFIG.GUI_FILE);

        GUITARLog.log.info("Number of Windows: "
                + dGUIStructure.getGUI().size());
        GUITARLog.log.info("GUI file:" + CONFIG.GUI_FILE);

        GUITARLog.log.info("Open Component file:" + CONFIG.LOG_WIDGET_FILE);
        ComponentListType lOpenWins = ripper.getlOpenWindowComps();
        ComponentListType lCloseWins = ripper.getlCloseWindowComp();
        ObjectFactory factory = new ObjectFactory();

        // LogWidget logWidget = factory.createLogWidget();
        // logWidget.setOpenWin(lOpenWins);
        // logWidget.setCloseWin(lCloseWins);
        //
        // IO.writeObjToFile(logWidget, CONFIG.LOG_WIDGET_FILE);

        // ------------------
        // Elapsed time:
        long nEndTime = System.currentTimeMillis();
        long nDuration = nEndTime - nStartTime;
        DateFormat df = new SimpleDateFormat("HH : mm : ss: SS");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        GUITARLog.log.info("Ripping Elapsed: " + df.format(nDuration));
        GUITARLog.log.info("Log file: " + JFCRipperConfiguration.LOG_FILE);
    }

    /**
     * 
     */
    private void setupEnv() {
        // --------------------------
        // Terminal list
        Configuration conf = (Configuration) IO.readObjFromFile(
                JFCRipperConfiguration.CONFIG_FILE, Configuration.class);

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

        GRipperMonitor jMonitor = new JFCRipperMointor(CONFIG);

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
                    String ID = winAdapter
                            .getFirstValueByName(GUITARConstants.ID_TAG_NAME);
                    if (ID != null)
                        JFCConstants.sIgnoredWins.add(ID);

                } else
                    lIgnoredComps.add(fullComp);
            }


        // --------------------------
        // Ignore components xml
        GComponentFilter jIgnoreExpand = new JFCIgnoreSignExpandFilter(
                lIgnoredComps);
        ripper.addComponentFilter(jIgnoreExpand);

        // --------------------------
        // Ignore components
        // List<String> sIgnoreWidgetList = Util.getListFromFile(
        // CONFIG.IGNORE_COMPONENT_FILE, false);

        // GComponentFilter jIgnoreExpand = new JFCIgnoreSignExpandFilter(conf
        // .getIgnoredComponents());
        // ripper.addComponentFilter(jIgnoreExpand);

        // // Setup terminal components ripper
        // GComponentFilter jTermial = JFCTerminalFilter.getInstance();
        // ripper.addComponentFilter(jTermial);

        // Setup tab components ripper
        GComponentFilter jTab = JFCTabFilter.getInstance();
        ripper.addComponentFilter(jTab);

        ripper.setMonitor(jMonitor);

    }
}
