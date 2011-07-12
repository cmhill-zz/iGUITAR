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
package edu.umd.cs.guitar.model;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;
import java.util.List;

import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleState;
import javax.accessibility.AccessibleStateSet;
import javax.swing.JDialog;
import javax.swing.JFrame;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.ContentsType;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.util.Debugger;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class JFCXWindow extends GWindow {

    Window window;

    /**
     * Get the JFC window object.
     * 
     * <p>
     * 
     * @return the window
     */
    public Window getWindow() {
        return window;
    }

    /**
     * Constructor
     * 
     * <p>
     * 
     * @param window
     */
    public JFCXWindow(Window window) {
        this.window = window;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.model.GXWindow#extractWindowInfo()
     */
    @Override
    public GUIType extractGUIProperties() {
        GUIType retGUI;

        ObjectFactory factory = new ObjectFactory();
        retGUI = factory.createGUIType();

        // Window

        AccessibleContext wContext = window.getAccessibleContext();
        ComponentType dWindow = factory.createComponentType();
        ComponentTypeWrapper gaWindow = new ComponentTypeWrapper(dWindow);
        dWindow = gaWindow.getDComponentType();

        gaWindow.addValueByName("Size", wContext.getAccessibleComponent()
                .getSize().toString());

        retGUI.setWindow(dWindow);

        // Container

        ComponentType dContainer = factory.createContainerType();
        ComponentTypeWrapper gaContainer = new ComponentTypeWrapper(dContainer);

        gaContainer.addValueByName("Size", wContext.getAccessibleComponent()
                .getSize().toString());
        dContainer = gaContainer.getDComponentType();

        ContentsType dContents = factory.createContentsType();
        ((ContainerType) dContainer).setContents(dContents);

        retGUI.setContainer((ContainerType) dContainer);

        return retGUI;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.model.GXWindow#getContainer()
     */
    @Override
    public GComponent getContainer() {
        return new JFCXComponent(window);
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.model.GXWindow#isModal()
     */
    @Override
    public boolean isModal() {
        AccessibleContext context = window.getAccessibleContext();
        if (context == null)
            return false;
        AccessibleStateSet states = context.getAccessibleStateSet();
        if (states.contains(AccessibleState.MODAL))
            return true;
        else
            return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.model.GXObject#getID()
     */
    @Override
    public String getFullID() {
        return getName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.model.GXObject#getName()
     */
    @Override
    public String getName() {

        String sName = null;
        // if (window == null)
        // return "";

        // Check for accessibility name
        AccessibleContext aContext = window.getAccessibleContext();
        if (aContext != null) {
            sName = aContext.getAccessibleName();
            if (sName != null)
                return sName;
        }

        sName = window.getClass().getName();
        return sName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.model.GXWindow#getGUIProperties()
     */
    @Override
    public List<PropertyType> getGUIProperties() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */

    public boolean equals1(Object window) {
        if (!window.getClass().equals(this.getClass()))
            return false;
        JFCXWindow jxWindow = (JFCXWindow) window;
        String myID = getFullID();
        String otherID = jxWindow.getFullID();
        if (myID.equals(otherID))
            return true;
        else
            return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((window == null) ? 0 : getFullID().hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JFCXWindow other = (JFCXWindow) obj;
        if (window == null) {
            if (other.window != null)
                return false;
        } else {
            String myID = getFullID();
            String otherID = other.getFullID();
            if (!myID.equals(otherID))
                return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.model.GWindow#isValidWindow()
     */
    @Override
    public boolean isValid() {
        // Check if window is visible
        if (!this.window.isVisible())
            return false;
        
        if ((getFullID()==null)||"".equals(getFullID()))
            return false;

        return true;
    }

}
