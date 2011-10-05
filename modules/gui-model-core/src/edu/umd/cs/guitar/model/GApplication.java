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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.umd.cs.guitar.exception.ApplicationConnectException;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.ContentsType;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.ObjectFactory;

/**
 * 
 * The abstract representing a subject under test (SUT) application in GUITAR.
 * 
 * <p>
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public abstract class GApplication {

    private static ObjectFactory factory = new ObjectFactory();

    /**
     * Start the application
     * 
     * @throws ApplicationConnectException
     */
    public abstract void connect() throws ApplicationConnectException;

    /**
     * 
     * Start the application with arguments
     * 
     * <p>
     * 
     * @param args
     * @throws ApplicationConnectException
     */
    public abstract void connect(String[] args)
            throws ApplicationConnectException;

    /**
     * Get all windows available at current state
     * 
     * <p>
     * 
     * @return
     */
    public abstract Set<GWindow> getAllWindow();

    /**
     * 
     * Get current GUI state of the application.
     * 
     * <p>
     * 
     * @return
     * @see GUIStructure
     */
    public GUIStructure getCurrentState() {
    	
        GUIStructure guiStructure = factory.createGUIStructure();
        Set<GWindow> lWindows = getAllWindow();
        List<GUIType> lGUIs = new ArrayList<GUIType>();
        for (GWindow gWindow : lWindows) {
            if (gWindow.isValid()) {
                GUIType dGUI = extractDeepGUI(gWindow);
                lGUIs.add(dGUI);
            }
        }
        guiStructure.setGUI(lGUIs);
        return guiStructure;
    }

    /**
     * Get all widgets info in  a window
     * 
     * <p>
     * 
     * @param gWindow
     * @return
     */
    private GUIType extractDeepGUI(GWindow gWindow) {
        GUIType retGUI = gWindow.extractWindow();
        GComponent gWinContainer = gWindow.getContainer();
        ComponentType container = null;
        container = extractDeepGUI(gWinContainer);
        if (container != null)
            retGUI.getContainer().getContents().getWidgetOrContainer().add(
                    container);
        return retGUI;
    }

    /**
     * Helper method for extractDeepGUI
     * 
     * <p>
     * 
     * @param component
     * @return
     */
    private ComponentType extractDeepGUI(GComponent component) {
        ComponentType retComp = component.extractProperties();
        if (retComp instanceof ContainerType) {
            ContainerType container = (ContainerType) retComp;
            ContentsType contents = factory.createContentsType();
            container.setContents(contents);

            List<GComponent> children = component.getChildren();
            List<ComponentType> childrenComp = new ArrayList<ComponentType>();

            for (GComponent aChild : children) {
                ComponentType childComp = extractDeepGUI(aChild);
                childrenComp.add(childComp);
            }
            contents.setWidgetOrContainer(childrenComp);
        }
        return retComp;
    }

    /**
     * @return
     */
    public Set<String> getCurrentWinID() {
        Set<GWindow> allWins = getAllWindow();
        Set<String> allWinIDs = new HashSet<String>();
        for (GWindow win : allWins)
            allWinIDs.add(win.getTitle());

        return allWinIDs;

    }

}
