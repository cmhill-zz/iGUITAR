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
 * Monitor the Replayer's behavior. This component handles platform-specific issues with the Replayer
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 * 
 */
public abstract class GReplayerMonitor {

    public GApplication application;
    boolean isUseReg = false;

    /**
	 * @return the isUseReg
	 */
	public boolean isUseReg() {
		return isUseReg;
	}

	/**
	 * @param isUseReg the isUseReg to set
	 */
	public void setUseReg(boolean isUseReg) {
		this.isUseReg = isUseReg;
	}

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
     * @param sWindowTitle
     * @return Gwindow
     */
    public abstract GWindow getWindow(String sWindowTitle);

    /**
     * Get an action from its name
     * 
     * <p>
     * 
     * @param sActionName
     * @return GEvent
     */
    public abstract GEvent getAction(String sActionName);

    /**
     * Get parameters from from action name
     * 
     * <p>
     * 
     * @param action
     * @return java.lang.Object
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
     * @return  PropertyType 
     */
    public abstract List<PropertyType> selectIDProperties(ComponentType comp);

    /**
     * The abstract representing a subject under test (SUT) application in GUITAR.
     * @return the application
     */
    public GApplication getApplication() {
        return application;
    }

    /**
     * Delay for a <code> delay </code> milliseconds
     * 
     * @param delay
     */
    abstract public void delay(int delay);
    
    

}
