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
package edu.umd.cs.guitar.replayer;

import java.util.List;

import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.model.GApplication;
import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.PropertyType;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 * 
 */
public abstract class GReplayerMonitor {

    public GApplication application;

    /**
     * Connect to application. This task includes start up the application if
     * needed
     * 
     */
    public abstract void connectToApplication();

    /**
     * Set up the replayer environment before the execution.
     */
    public abstract void setUp();

    /**
     * Clean up the replayer environment after the execution.
     */
    public abstract void cleanUp();

    /**
     * 
     * Find a window from its ID. This method should wait until a window is
     * retrieved or time out (i.e. not found).
     * 
     * <p>
     * 
     * @param windowID
     * @return
     */
    public abstract GWindow getWindow(String sWindowID);

    /**
     * 
     * Get component from its ID and the host window
     * 
     * <p>
     * 
     * @param sComponentID
     * @param gWindow
     * @return
     */
    public abstract GComponent getComponent(String sComponentID, GWindow gWindow);

    /**
     * Get an action from its ID
     * 
     * <p>
     * 
     * @param sActionID
     * @return
     */
    public abstract GEvent getAction(String sActionName);

    /**
     * Get parameters from from action name
     * 
     * <p>
     * 
     * @param action
     * @return
     */
    public abstract Object getArguments(String action);

    /**
     * 
     * Select a subset of properties for component GUI information to work as
     * its identifier.
     * 
     * <p>
     * 
     * @param comp
     * 
     * @return
     */
    public abstract List<PropertyType> selectIDProperties(ComponentType comp);

    /**
     * @return the application
     */
    public GApplication getApplication() {
        return application;
    }

}
