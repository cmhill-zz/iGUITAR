/*	
 *  Copyright (c) 2009-@year@. The GUITAR group at the University of Maryland. Names of owners of this group may
 *  be obtained by sending an e-mail to atif@cs.umd.edu
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 *  documentation files (the "Software"), to deal in the Software without restriction, including without 
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *	the Software, and to permit persons to whom the Software is furnished to do so, subject to the following 
 *	conditions:
 * 
 *	The above copyright notice and this permission notice shall be included in all copies or substantial 
 *	portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 *	LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 *	EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 *	IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 *	THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package edu.umd.cs.guitar.ripper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.data.ComponentListType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.Configuration;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.FullComponentType;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.util.Debugger;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * 
 * The core ripping algorithm class.
 * 
 * <p>
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 * 
 */
public class Ripper {

    /**
     * 
     * List of windows ripped
     * 
     */
    volatile Set<GWindow> lRippedWindow = new HashSet<GWindow>();

    GRipperMonitor monitor = null;
    LinkedList<GComponentFilter> lComponentFilter = new LinkedList<GComponentFilter>();
    LinkedList<GWindowFilter> lWindowFilter = new LinkedList<GWindowFilter>();;

    static ObjectFactory factory = new ObjectFactory();

    // Data
    GUIStructure dGUIStructure = new GUIStructure();

    Configuration configuration = new Configuration();

    // LOG
    ComponentListType lOpenWindowComps;
    ComponentListType lCloseWindowComp;

    /**
     * @return the log
     */
    public Logger getLog() {
        return log;
    }

    /**
     * @return the lOpenWindowComps
     */
    public ComponentListType getlOpenWindowComps() {
        return lOpenWindowComps;
    }

    Logger log;

    /**
     * @param log
     *            the log to set
     */
    public void setLog(Logger log) {
        this.log = log;
    }

    /**
     * Constructor with logger
     * <p>
     * 
     * @param logger
     *            External logger
     */
    public Ripper(Logger logger) {
        super();
        this.log = logger;
        lOpenWindowComps = factory.createComponentListType();
        lCloseWindowComp = factory.createComponentListType();
    }

    /**
     * Constructor without logger
     */
    public Ripper() {
        this(Logger.getLogger("Ripper"));
    }

    /**
     * 
     * Start ripping
     * 
     */

    public void execute() {

        if (monitor == null) {
            GUITARLog.log.error("Monitor hasn't been assigned");
            return;
        }
        // 1. Set Up the environment
        monitor.setUp();

        // 2. Get the list of root window
        List<GWindow> gRootWindows = monitor.getRootWindows();

        if (gRootWindows == null) {
            GUITARLog.log.warn("No root window");
            return;
        }

        GUITARLog.log.info("Number of root windows: " + gRootWindows.size());

        // 3. Main step: ripping starting from
        // each root window in the list
        for (GWindow xRootWindow : gRootWindows) {
            xRootWindow.setRoot(true);
            // monitor.addRippedList(xRootWindow );
            this.lRippedWindow.add(xRootWindow);
            GUIType gRoot = ripWindow(xRootWindow);
            this.dGUIStructure.getGUI().add(gRoot);
        }

        // 4. Clean up
        monitor.cleanUp();
    }

    /**
     * Rip a window
     * 
     * <p>
     * 
     * @param gWindow
     * @return
     */
    public GUIType ripWindow(GWindow gWindow) {

        GUITARLog.log.info("----------------------------");
        GUITARLog.log.info("Ripping window: *" + gWindow.getFullID() + "*");

        // 1. Rip special/customized components
        for (GWindowFilter wf : lWindowFilter) {
            if (wf.isProcess(gWindow)) {
                GUITARLog.log.info("Window filter "
                        + wf.getClass().getSimpleName() + " is applied");
                return wf.ripWindow(gWindow);
            }
        }

        try {

            GUIType retGUI = gWindow.extractWindow();
            GComponent gWinContainer = gWindow.getContainer();

            ComponentType container = null;

            if (gWinContainer != null)
                container = ripComponent(gWinContainer, gWindow);

            if (container != null)
                retGUI.getContainer().getContents().getWidgetOrContainer().add(
                        container);
            return retGUI;

        } catch (Exception e) {
            GUITARLog.log.error("Exception during ripping window ");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * Rip a component
     * 
     * <p>
     * 
     * @param component
     * @return
     */
    public ComponentType ripComponent(GComponent component, GWindow window) {

        GUITARLog.log.debug("Ripping component: *" + component.getFullID()
                + "* in window *" + window.getFullID() + "*");

        // 1. Rip special/customized components
        for (GComponentFilter cm : lComponentFilter) {
            if (cm.isProcess(component, window)) {
                GUITARLog.log.info("Filter " + cm.getClass().getSimpleName()
                        + " is applied");

                return cm.ripComponent(component, window);
            }
        }

        // GUITARLog.log.debug("No filter applied");

        // 2. Rip regular components
        ComponentType retComp = null;
        try {

            retComp = component.extractProperties();
            // 2.1 Try to perform action on the component
            // to reveal more windows/components

            // clear window opened cache before performing actions
            monitor.resetWindowCache();

            if (monitor.isExpandable(component, window))
                monitor.expandGUI(component);

            // // Trigger terminal widget
            // if (monitor.isWindowClosed()) {
            // GUITARLog.log.debug("!!!!! Window closed");
            //
            // LinkedList<GWindow> lClosedWindows = monitor
            // .getClosedWindowCache();
            //
            // String sWinID = window.getFullID();
            // for (GWindow closedWin : lClosedWindows) {
            //
            //
            // if (sWinID.equals(closedWin.getFullID())) {
            //
            // List<FullComponentType> lCloseComp = lCloseWindowComp
            // .getFullComponent();
            //
            // FullComponentType cCloseComp = factory
            // .createFullComponentType();
            // cCloseComp.setWindow(closedWin.extractWindow()
            // .getWindow());
            // cCloseComp.setComponent(retComp);
            // lCloseComp.add(cCloseComp);
            // lCloseWindowComp.setFullComponent(lCloseComp);
            // }
            //
            // }
            //
            // }

            if (monitor.isNewWindowOpened()) {

                List<FullComponentType> lOpenComp = lOpenWindowComps
                        .getFullComponent();
                FullComponentType cOpenComp = factory.createFullComponentType();
                cOpenComp.setWindow(window.extractWindow().getWindow());
                cOpenComp.setComponent(retComp);
                lOpenComp.add(cOpenComp);
                lOpenWindowComps.setFullComponent(lOpenComp);

                LinkedList<GWindow> lNewWindows = monitor
                        .getOpenedWindowCache();
                monitor.resetWindowCache();
                GUITARLog.log.info(lNewWindows.size()
                        + " new window(s) opened!");
                for (GWindow newWins : lNewWindows) {
                    GUITARLog.log.info("\t" + newWins.getFullID());
                }

                // Process the opened windows in a FIFO order
                while (!lNewWindows.isEmpty()) {

                    GWindow gNewWin = lNewWindows.pollLast();
                    GComponent gWinComp = gNewWin.getContainer();

                    if (gWinComp != null) {

                        // Add invokelist property for the component
                        String sWindowTitle = gNewWin.getFullID();
                        ComponentTypeWrapper compA = new ComponentTypeWrapper(
                                retComp);
                        compA.addValueByName(
                                GUITARConstants.INVOKELIST_TAG_NAME,
                                sWindowTitle);

                        GUITARLog.log.debug(sWindowTitle + " recorded");

                        retComp = compA.getDComponentType();

                        // Check ignore window
                        if (!monitor.isIgnoredWindow(gNewWin)) {

                            if (!monitor.isRippedWindow(gNewWin)) {
                                // if (!this.lRippedWindow.contains(gNewWin)) {
                                gNewWin.setRoot(false);
                                // lRippedWindow.add(gNewWin);
                                monitor.addRippedList(gNewWin);

                                GUIType dWindow = ripWindow(gNewWin);

                                if (dWindow != null)
                                    dGUIStructure.getGUI().add(dWindow);
                            } else {
                                GUITARLog.log.info("Window is ripped!!!");
                            }

                        } else {
                            GUITARLog.log.info("Window is ignored!!!");
                        }
                    }
                    monitor.closeWindow(gNewWin);

                    // // Check if there is a new window open
                    // lNewWindows = monitor.getOpenedWindowCache();
                    // monitor.resetWindowCache();
                }
            }

            // TODO: check if the component is still available after ripping
            // its child window
            List<GComponent> gChildrenList = component.getChildren();
            int nChildren = gChildrenList.size();
            int i = 0;

            while (i < nChildren) {

                GComponent gChild = gChildrenList.get(i++);
                ComponentType guiChild = ripComponent(gChild, window);

                if (guiChild != null)
                    ((ContainerType) retComp).getContents()
                            .getWidgetOrContainer().add(guiChild);

                gChildrenList = component.getChildren();

                nChildren = gChildrenList.size();
            }

        } catch (Exception e) {
            // logOld.println("ripComponent exception");
            // e.printStackTrace();
            GUITARLog.log.error("ripComponent exception", e);

            return null;
        }
        return retComp;
    }

    /**
     * @return the lCloseWindowComp
     */
    public ComponentListType getlCloseWindowComp() {
        return lCloseWindowComp;
    }

    /**
     * @param monitor
     *            the monitor to set
     */
    public void setMonitor(GRipperMonitor monitor) {
        this.monitor = monitor;
    }

    /**
     * 
     * Add a window filter
     * 
     * @param filter
     */
    public void addWindowFilter(GWindowFilter filter) {
        if (this.lWindowFilter == null) {
            lWindowFilter = new LinkedList<GWindowFilter>();
        }
        lWindowFilter.addLast(filter);
        filter.setRipper(this);
    }

    /**
     * 
     * Remove a window filter
     * 
     * @param filter
     */
    public void removeWindowFilter(GWindowFilter filter) {
        lWindowFilter.remove(filter);
        filter.setRipper(null);
    }

    /**
     * 
     * Add a component filter
     * 
     * @param filter
     */
    public void addComponentFilter(GComponentFilter filter) {
        if (this.lComponentFilter == null) {
            lComponentFilter = new LinkedList<GComponentFilter>();
        }
        lComponentFilter.addLast(filter);
        filter.setRipper(this);
    }

    /**
     * 
     * Remove a component filter
     * 
     * @param filter
     */
    public void removeComponentFilter(GComponentFilter filter) {
        lComponentFilter.remove(filter);
        filter.setRipper(null);
    }

    /**
     * @return the dGUIStructure
     */
    public GUIStructure getResult() {
        return dGUIStructure;
    }

}
