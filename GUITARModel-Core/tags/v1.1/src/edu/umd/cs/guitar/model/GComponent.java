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
import java.util.List;

import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.ContentsType;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.model.wrapper.PropertyTypeWrapper;

/**
 * Abstract class for accessible components (widget/container) in GUITAR
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 * 
 */
public abstract class GComponent implements GObject {

    private static int ID = 0;

    /**
     * 
     * Extract component properties and convert it to GUITAR data format
     * 
     * <p>
     * 
     * @return
     */
    public ComponentType extractProperties() {

        ComponentType retComp;

        if (!hasChildren()) {
            retComp = factory.createComponentType();
        } else {
            retComp = factory.createContainerType();
            ContentsType contents = factory.createContentsType();
            ((ContainerType) retComp).setContents(contents);
        }

        ComponentTypeWrapper retCompAdapter = new ComponentTypeWrapper(retComp);

        // Add ID
        retCompAdapter
                .addValueByName(GUITARConstants.ID_TAG_NAME, "w" + (ID++));

        // String sID = getFullID();
        // retCompAdapter.addValueByName(GUITARConstants.FULL_ID_TAG_NAME, sID);

        // Class
        String sClass = getClassVal();
        retCompAdapter.addValueByName(GUITARConstants.CLASS_TAG_NAME, sClass);

        // Type
        String sType = getTypeVal();
        retCompAdapter.addValueByName(GUITARConstants.TYPE_TAG_NAME, sType);

        // Events
        List<GEvent> lEvents = getEventList();
        for (GEvent event : lEvents)
            retCompAdapter.addValueByName(GUITARConstants.EVENT_TAG_NAME, event
                    .getClass().getName());

        // Other GUI Properties
        retComp = retCompAdapter.getDComponentType();

        AttributesType attributes = retComp.getAttributes();
        List<PropertyType> lProperties = attributes.getProperty();
        List<PropertyType> lGUIProperties = getGUIProperties();

        // Update list
        if (lGUIProperties != null)
            lProperties.addAll(lGUIProperties);

        attributes.setProperty(lProperties);
        retComp.setAttributes(attributes);

        return retComp;
    }

    /**
     * Get all children of the component.
     * 
     * <p>
     * 
     * @return
     */
    abstract public List<GComponent> getChildren();

    /**
     * Get the class of the component
     * 
     * @return
     */
    abstract public String getClassVal();

    /**
     * Get the list of events can be performed by the component
     * 
     * @return
     */
    abstract public List<GEvent> getEventList();

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.model.GXObject#getFirstChildByID(java.lang.String)
     */
    @Override
    public GComponent getFirstChildByID(String sID) {
        {
            if (sID.equals(this.getFullID()))
                return this;

            List<GComponent> gChildren = getChildren();
            GComponent result = null;

            for (GComponent gChild : gChildren) {
                result = gChild.getFirstChildByID(sID);
                if (result != null)
                    return result;
            }
            return null;

        }
    }

    /**
     * 
     * Get first the child by comparing its properties to a list of
     * {@link edu.umd.cs.guitar.model.data.PropertyType}. This list works as an
     * identifier for widgets on the GUI.
     * 
     * <p>
     * 
     * @param lIDProperties
     *            the list of
     *            {@link edu.PropertyTypeWrapper.umd.cs.guitar.model.wrapper.PropertyTypeAdapter}
     *            working as widget identifier.
     * @return
     * 
     */
    public GComponent getFirstChild(List<PropertyTypeWrapper> lIDProperties) {

        ComponentType comp = extractProperties();
        List<PropertyType> lProperties = comp.getAttributes().getProperty();

        List<PropertyTypeWrapper> lPropertyTypeAdapters = new ArrayList<PropertyTypeWrapper>();

        for (PropertyType p : lProperties)
            lPropertyTypeAdapters.add(new PropertyTypeWrapper(p));

        if (lPropertyTypeAdapters.containsAll(lIDProperties))
            return this;

        List<GComponent> gChildren = getChildren();
        GComponent result = null;

        for (GComponent gChild : gChildren) {
            result = gChild.getFirstChild(lIDProperties);
            if (result != null)
                return result;
        }
        return null;
    }

    /**
     * Get a child whose properties match a certain property set
     * 
     * @param properties
     * @return
     */
    public GComponent getChildByPropertySet(List<PropertyType> properties) {
        {
            List<GComponent> gChildren = getChildren();
            GComponent result = null;
            for (GComponent gChild : gChildren) {
                result = gChild.getChildByPropertySet(properties);
                if (result != null)
                    return result;
            }
            return null;
        }
    }

    /**
     * Get all GUI properties of widgets
     * 
     * <p>
     * 
     * @return
     */
    abstract public List<PropertyType> getGUIProperties();

    /**
     * Get the direct parent of the component.
     * 
     * <p>
     * 
     * @return
     */
    abstract public GComponent getParent();

    /**
     * 
     * Get the GUITAR type of event supported by the component (i.e. TERMINAL,
     * SYSTEM INTERACTION, etc)
     * 
     * <p>
     * 
     * @return
     */
    abstract public String getTypeVal();

    /**
     * Check if the component has children
     * 
     * <p>
     * 
     * @return
     */
    abstract public boolean hasChildren();

    /**
     * @return
     */
    abstract public boolean isTerminal();

}
