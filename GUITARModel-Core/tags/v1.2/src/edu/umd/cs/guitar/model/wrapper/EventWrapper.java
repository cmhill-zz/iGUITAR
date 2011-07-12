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
package edu.umd.cs.guitar.model.wrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.EventType;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class EventWrapper {

    String action;
    ComponentTypeWrapper component;

    /**
     * The edge used to reveal a widget; including window opener and menu opener
     * those edge will be used to reach a widget from the initial state
     */
    // private static final int REACHING_EDGE = 2;

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action
     *            the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return the component
     */
    public ComponentTypeWrapper getComponent() {
        return component;
    }

    /**
     * @param component
     *            the component to set
     */
    public void setComponent(ComponentTypeWrapper component) {
        this.component = component;
    }

    /**
     * Check if an event can be followed after this event
     * 
     * @param other
     *            the event to check
     * @return
     */
    public int isFollowedBy(EventWrapper other) {

        // Component associated with the first event
        ComponentTypeWrapper firstComponent = this.component;
        GUITypeWrapper firstWindow = firstComponent.getWindow();

        // Component associated with the second event
        ComponentTypeWrapper secondComponent = other.component;
        GUITypeWrapper secondWindow = secondComponent.getWindow();

        String ID = firstComponent
                .getFirstValueByName(GUITARConstants.ID_TAG_NAME);
        String otherID = secondComponent
                .getFirstValueByName(GUITARConstants.ID_TAG_NAME);

//        System.out.println("-----------------------------");
//        System.out.println("Analyzing " + ID + "->" + otherID);
//        System.out.println("Analyzing " + firstWindow + "->" + secondWindow);

        // ***********************************
        // Check if e2 can be followed by e1
        // ***********************************

        // -----------------------------------
        // Case 0: e2 is e1 (identical)
        if (ID.equals(otherID))
            return GUITARConstants.FOLLOW_EDGE;

        // -----------------------------------
        // Case 1: e2 is a hidden event and e2 is a direct child of e1

        // Get direct parent of event 2

        ComponentTypeWrapper directParent = other.component.getParent();

        while (directParent != null) {
            String action = directParent
                    .getFirstValueByName(GUITARConstants.EVENT_TAG_NAME);
            if (action != null)
                break;
            directParent = directParent.getParent();
        }

        // e2 have parent then it is a hidden event
        if (directParent != null) {
            String parentID = directParent
                    .getFirstValueByName(GUITARConstants.ID_TAG_NAME);

            if (ID.equals(parentID)) {
                // return GUITARConstants.FOLLOW_EDGE;
                // System.out.println(ID + "is a direct parent of " + otherID);
                return GUITARConstants.REACHING_EDGE;
            } else
                return GUITARConstants.NO_EDGE;
        }

        // -----------------------------------------------
        // Case 2: e2 is not a hidden event and in the windows available
        // after e1 is performed

        // first event type
        String firstEventType = firstComponent
                .getFirstValueByName(GUITARConstants.TYPE_TAG_NAME);

        // -------------------------------------
        // Get the top window list available after the first event (note that
        // it is a list since one event may open several window at the same time

        Set<GUITypeWrapper> windowsTopAfterFirstEvent = new HashSet<GUITypeWrapper>();

        // ------------------------
        // Check invoked windows
        List<GUITypeWrapper> windowsInvoked = firstComponent
                .getInvokedWindows();

        // Terminal event, current window is closed
        if (GUITARConstants.TERMINAL.equals(firstEventType)) {
            // System.out.println(ID + " is a terminal event");

            ComponentTypeWrapper invoker = firstWindow.getInvoker();
            if (invoker != null) {
                windowsTopAfterFirstEvent.add(invoker.getWindow());
            }

            // Non terminal
        } else {

            // New window opened
            if (windowsInvoked.size() > 0) {
//                System.out.println(ID + " is an invoker event");
                windowsTopAfterFirstEvent.addAll(windowsInvoked);
            }
            // No window opened/closed
            else {
                // System.out.println(ID + " is a system interaction event");
                windowsTopAfterFirstEvent.add(firstWindow);
            }
        }

        // -----------------------------------------
        // Check if the 2nd event is in the opened windows
        if (windowsInvoked.size() > 0)
            for (GUITypeWrapper window : windowsInvoked) {
                if (window.equals(secondWindow))
                    return GUITARConstants.REACHING_EDGE;

            }

        // -----------------------------------------
        // Check if the 2nd event is reachable after the 1st one

        Set<GUITypeWrapper> windowsAvailableAfterFirstEvent = new HashSet<GUITypeWrapper>();
        // get all windows available after event 1
        for (GUITypeWrapper window : windowsTopAfterFirstEvent) {
            Set<GUITypeWrapper> avaiableWindows = window
                    .getAvailableWindowList();
            if (avaiableWindows != null)
                windowsAvailableAfterFirstEvent.addAll(avaiableWindows);
        }

        // Check if the 2nd event is in the list of available window after the
        // 1st event

        for (GUITypeWrapper window : windowsAvailableAfterFirstEvent) {
            if (window.equals(secondWindow))
                // if (windowsInvoked.size() > 0)
                // return GUITARConstants.REACHING_EDGE;
                // else
                return GUITARConstants.FOLLOW_EDGE;

        }
        return GUITARConstants.NO_EDGE;
    }

    /**
     * Check if this event is hidden (e.g. menu item under menu)
     * 
     * @return
     */
    public boolean isHidden() {
        ComponentTypeWrapper parent = this.component.getParent();
        while (parent != null) {
            String action = parent
                    .getFirstValueByName(GUITARConstants.EVENT_TAG_NAME);

            parent = parent.getParent();
        }
        return false;
    }

    /**
     * Get event type
     * 
     * <p>
     * 
     * @return
     * @see EventType
     */
    public String getType() {

        // 1. Expand event
        if (component.hasChild())
            return GUITARConstants.EXPAND;

        // 2. Terminal event
        String widgetType = component
                .getFirstValueByName(GUITARConstants.TYPE_TAG_NAME);

        if (GUITARConstants.TERMINAL.equals(widgetType))
            return GUITARConstants.TERMINAL;

        // 3. Window open event
        List<GUITypeWrapper> invokedWindows = component.getInvokedWindows();
        if (invokedWindows != null)
            if (invokedWindows.size() > 0) {
                for (GUITypeWrapper win : invokedWindows) {
                    if (win.isModal())
                        return GUITARConstants.RESTRICED_FOCUS;
                }
                return GUITARConstants.UNRESTRICED_FOCUS;
            }

        // 4. System interaction event
        return GUITARConstants.SYSTEM_INTERACTION;

    }
}
