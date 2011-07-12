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

import java.util.List;

import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.ContentsType;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;

/**
 * Abstract class for accessible windows in GUITAR
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 * 
 */
public abstract class GWindow implements GObject {

    boolean isRoot = false;

    /**
     * Extract window GUI information and convert to GUITAR data format
     * 
     * <p>
     * 
     * @return
     */

    public GUIType extractWindow() {
        GUIType retGUI;

        retGUI = factory.createGUIType();

        // ---------------------
        // Window
        // ---------------------

        ComponentType window = factory.createComponentType();

        ComponentTypeWrapper windowAdapter = new ComponentTypeWrapper(window);

        // Add properties required by GUITAR
        windowAdapter.addValueByName(GUITARConstants.ID_TAG_NAME, ""
                + getFullID());

        // Modal
        windowAdapter.addValueByName(GUITARConstants.MODAL_TAG_NAME, ""
                + isModal());

        // Is Root window
        windowAdapter.addValueByName(GUITARConstants.ROOTWINDOW_TAG_NAME, ""
                + isRoot());

        window = windowAdapter.getDComponentType();

        AttributesType attributes = window.getAttributes();
        List<PropertyType> lProperties = attributes.getProperty();
        List<PropertyType> lGUIProperties = getGUIProperties();

        if (lGUIProperties != null)
            lProperties.addAll(lGUIProperties);

        attributes.setProperty(lProperties);
        window.setAttributes(attributes);

        retGUI.setWindow(window);

        // ---------------------
        // Container

        ContainerType container = factory.createContainerType();
        ContentsType contents = factory.createContentsType();
        container.setContents(contents);
        retGUI.setContainer(container);

        return retGUI;
    }

    /**
     * Get the component corresponding to the window
     * 
     * <p>
     * 
     * @return
     */
    abstract public GComponent getContainer();

    abstract public List<PropertyType> getGUIProperties();

    abstract public GUIType extractGUIProperties();

    // --------------------
    // Get window properties used for GUITAR
    // --------------------

    /**
     * Check if the window is modal or not
     * 
     * <p>
     * 
     * @return
     */
    abstract public boolean isModal();

    /**
     * Check if the window is a root window or not
     * 
     * @return the isRoot
     */

    public boolean isRoot() {
        return isRoot;
    }

    /**
     * Indicate that the window is a root window, from which we start ripping.
     * This flag is set depending on our ripping purposes.
     * 
     * <p>
     * 
     * @param isRoot
     *            the isRoot to set
     */
    public void setRoot(boolean isRoot) {
        this.isRoot = isRoot;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.model.GXObject#getFirstChildByID(java.lang.String)
     */
    public GComponent getFirstChildByID(String sID) {
        GComponent container = getContainer();
        return container.getFirstChildByID(sID);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public abstract boolean equals(Object window);

    /**
     * Check if a window is actually valid and worth considering.
     * 
     * <p>
     * 
     * @return
     */
    public abstract boolean isValid();

}
