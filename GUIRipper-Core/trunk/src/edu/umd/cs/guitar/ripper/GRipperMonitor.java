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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GWindow;

/**
 * Monitor the Ripper's behavior. This component handles platform-specific
 * issues with the Ripper.
 * 
 * <p>
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 * 
 */
public abstract class GRipperMonitor {

    /**
     * The ripper
     */
    Ripper ripper;

    /**
     * 
     * List of windows ripped
     * 
     */
    volatile Set<String> lRippedWindow = new HashSet<String>();

    /**
     * 
     * Get a list of all root window. The list will be retrieved based on
     * individual platform
     * 
     * <p>
     * 
         * @return GWindow List
     */
    public abstract List<GWindow> getRootWindows();

    /**
     * All actions needed before ripping They can be starting application,
     * adding window opened listener, and setting the application's context
     * 
     */
    public abstract void setUp();

    /**
     * 
     * Do all steps after finishing ripping
     * 
     */
    public abstract void cleanUp();

    /**
     * Detect if there is a window opened
     * 
     * <p>
     * 
         * @return true/false
     */
    public abstract boolean isNewWindowOpened();

    /**
     * Detect if there is a window closing
     * 
     * <p>
     * 
        * @return true/false
     */
    public abstract boolean isWindowClosed();

    /**
     * Get a stack of window opened after an event. The window order of is the
     * order they are opened
     * 
     * <p>
     * 
     * @return the list of opened windows stored in the cache
     */
    public abstract LinkedList<GWindow> getOpenedWindowCache();

    /**
     * Reset cache to store opened windows to empty
     */
    public abstract void resetWindowCache();

    /**
     * Close a window
     * 
     * <p>
     * 
     * @param window
     */
    public abstract void closeWindow(GWindow window);

    /**
     * 
     * Define the criteria for ignoring windows we don't want to rip. For
     * example the file browser window which is a system window
     * 
     * <p>
     * 
     * @param window
        * @return true/false
     */
    public abstract boolean isIgnoredWindow(GWindow window);

    /**
     * Try to do some actions on the component to reveal more GUI components.
     * For example, click on a button to open a new window or click on a menu
     * item to expand a new sub menu.
     * 
     * <p>
     * 
     * @param component
     */
    public abstract void expandGUI(GComponent component);

    /**
     * Define the criteria for expanding a GUI component
     * 
     * <p>
     * 
     * @param component
     * @param window
        * @return true/false
     */
    abstract boolean isExpandable(GComponent component, GWindow window);

    /**
     * @return the ripper
     */
    public Ripper getRipper() {
        return ripper;
    }

    /**
     * @param ripper
     *            the ripper to set
     */
    public void setRipper(Ripper ripper) {
        this.ripper = ripper;
    }

    /**
     * Check if a window is ripped. The criteria is based on the
     * <code> equals </code> method <i> Depreciated </i> this method is moved to
     * the core ripper algorithm.
     * <p>
     * 
     * @param window
     * @return
     */
    // @Deprecated
    // public boolean isRippedWindow(GWindow window) {
    // if (this.lRippedWindow.contains(window))
    // return true;
    // return false;
    // }
    //
    // /**
    // * Add a new window to ripped list
    // *
    // * <p>
    // *
    // * @param window
    // */
    // @Deprecated
    // public void addRippedList(GWindow window) {
    // this.lRippedWindow.add(window);
    // }

/**
 * checks for the rippedWindow
 * @param window
 * @return ripped windowName
 */
   
    public boolean isRippedWindow(GWindow window) {
        String sWindowName = window.getTitle();
        return (lRippedWindow.contains(sWindowName));
    }

/**
 * Adds the ripped winodws to ripped window list
 * @param window
 */
   
    public void addRippedList(GWindow window) {
        String windowTitle = window.getTitle();
        this.lRippedWindow.add(windowTitle);
    }

    /**
        * @return list of closedwindows
     */
    abstract LinkedList<GWindow> getClosedWindowCache();

}
