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

	/**
     * 
     */
	private static final String COMPONENT_ID_PREFIX = "w";
	private static int ID_COUNTER = 0;

	private int ID;

	/**
     * 
     */
	public GComponent() {
		super();
		this.ID = ID_COUNTER++;
	}

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
		String ID = getID();
		retCompAdapter.addValueByName(GUITARConstants.ID_TAG_NAME, ID);

		// String sID = getFullID();
		// retCompAdapter.addValueByName(GUITARConstants.FULL_ID_TAG_NAME, sID);

		// Class
		String sClass = getClassVal();
		retCompAdapter.addValueByName(GUITARConstants.CLASS_TAG_NAME, sClass);

		// Type
		String sType = getTypeVal();
		retCompAdapter.addValueByName(GUITARConstants.TYPE_TAG_NAME, sType);

		// Hash code
		String sHashcode = Integer.toString(this.hashCode());
		retCompAdapter.addValueByName(GUITARConstants.HASHCODE_TAG_NAME,
				sHashcode);

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
	 * Generate ID for the component.
	 * 
	 * <p>
	 * 
	 * GUITAR uses Java default hashing algorithm on property names and values
	 * to generate ID for the component.
	 * 
	 * @return
	 */
	public String getID() {
		// return (COMPONENT_ID_PREFIX + (hashCode()));
		// return (COMPONENT_ID_PREFIX + (ID_COUNTER++));
		return (COMPONENT_ID_PREFIX + this.ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		List<PropertyType> guiProperties = getGUIProperties();

		final int prime = 31;
		int result = 1;
		if (guiProperties == null)
			return 0;

		for (PropertyType property : guiProperties) {
			String name = property.getName();
			result = prime * result + (name == null ? 0 : name.hashCode());
			result = Math.abs(result);

			List<String> valueList = property.getValue();
			result = prime * result
					+ (valueList == null ? 0 : valueList.hashCode());
			result = Math.abs(result);

		}

		return result;
	}

	/**
	 * Get the class of the component
	 * 
	 * @return
	 */
	public abstract String getClassVal();

	/**
	 * Get the list of events can be performed by the component
	 * 
	 * @return
	 */
	public abstract List<GEvent> getEventList();

	/**
	 * Get all children of the component.
	 * 
	 * <p>
	 * 
	 * @return
	 */
	public abstract List<GComponent> getChildren();

	/**
	 * Get the direct parent of the component.
	 * 
	 * <p>
	 * 
	 * @return
	 */
	public abstract GComponent getParent();

	/**
	 * 
	 * Get the GUITAR type of event supported by the component (i.e. TERMINAL,
	 * SYSTEM INTERACTION, etc)
	 * 
	 * <p>
	 * 
	 * @return
	 */
	public abstract String getTypeVal();

	/**
	 * Check if the component has children
	 * 
	 * <p>
	 * 
	 * @return
	 */
	public abstract boolean hasChildren();

	/**
	 * @return
	 */
	public abstract boolean isTerminal();

	/**
	 * Check if the component is enable
	 * 
	 * @return
	 */
	public abstract boolean isEnable();

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.model.GXObject#getFirstChildByID(java.lang.String)
	 */
	@Override
	public GComponent getFirstChildByID(String sID) {
		{
			if (sID.equals(this.getID()))
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
}
