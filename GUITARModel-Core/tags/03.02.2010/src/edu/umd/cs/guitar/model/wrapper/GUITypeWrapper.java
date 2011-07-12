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
import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * Adapter class to process GUIType.
 * 
 * <p>
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 * 
 */
public class GUITypeWrapper {
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GUITypeWrapper [ID=" + ID + "]";
	}

	GUIType dGUIType;
	String ID;

	// -------------------------------
	// Parsed structured data
	// -------------------------------

	/**
	 * Root container of the window
	 */
	ComponentTypeWrapper container;

	/**
     * 
     */
	ComponentTypeWrapper invoker;

	/**
	 * 
	 * A lazy method to parse data
	 * 
	 * <p>
	 * 
	 * @param dGUIStructure
	 * @param wGUIStructure
	 */
	public void parseData(GUIStructure dGUIStructure,
			GUIStructureWrapper wGUIStructure) {

		GUITARLog.log.debug("Parsing window: " + this.getTitle());

		this.container = new ComponentTypeWrapper(this.dGUIType.getContainer());
		this.container.setWindow(this);
		this.container.parseData(dGUIStructure, wGUIStructure);

	}

	/**
	 * @return the invoker
	 */
	public ComponentTypeWrapper getInvoker() {
		return invoker;
	}

	/**
	 * @param invoker
	 *            the invoker to set
	 */
	public void setInvoker(ComponentTypeWrapper invoker) {
		this.invoker = invoker;
	}

	/**
	 * @return the container
	 */
	public ComponentTypeWrapper getContainer() {
		return this.container;
	}

	/**
	 * @param data
	 */
	public GUITypeWrapper(GUIType data) {
		super();
		this.dGUIType = data;
		this.ID = getTitle();
	}

	/**
	 * @return the data
	 */
	public GUIType getData() {
		return dGUIType;
	}

	public ComponentTypeWrapper getChildByID(String ID) {
		ComponentTypeWrapper container = new ComponentTypeWrapper(dGUIType
				.getContainer());
		return container.getChildByID(ID);
	}

	public void setValueByName(String sTitle, String sName, String sValue) {
		ComponentType window = dGUIType.getWindow();
		ComponentTypeWrapper windowA = new ComponentTypeWrapper(window);
		windowA.setValueByName(sTitle, sName, sValue);

		ComponentType container = dGUIType.getContainer();
		ComponentTypeWrapper containerA = new ComponentTypeWrapper(container);
		containerA.setValueByName(sTitle, sName, sValue);
	}

	public void addValueByName(String sTitle, String sName, String sValue) {
		ComponentType window = dGUIType.getWindow();
		ComponentTypeWrapper windowA = new ComponentTypeWrapper(window);
		windowA.addValueByName(sTitle, sName, sValue);

		ComponentType container = dGUIType.getContainer();
		ComponentTypeWrapper containerA = new ComponentTypeWrapper(container);
		containerA.addValueByName(sTitle, sName, sValue);
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof GUITypeWrapper))
			return false;

		GUITypeWrapper other = (GUITypeWrapper) obj;

		String sMyTitle = getTitle();
		String sOtherTitle = other.getTitle();

		return sMyTitle.equals(sOtherTitle);
	}

	/**
	 * Get title of the window data
	 * 
	 * @return
	 */
	public String getTitle() {
		ComponentType window = dGUIType.getWindow();
		ComponentTypeWrapper winAdapter = new ComponentTypeWrapper(window);
		String sGUITitle = winAdapter
				.getFirstValueByName(GUITARConstants.TITLE_TAG_NAME);
		return sGUITitle;
	}

	public void setTitle(String sTitle) {
		ObjectFactory factory = new ObjectFactory();

		ComponentType window = dGUIType.getWindow();
		AttributesType attributes = window.getAttributes();

		PropertyType newProperty = factory.createPropertyType();
		newProperty.setName(GUITARConstants.TITLE_TAG_NAME);
		List<String> value = new ArrayList<String>();
		value.add(sTitle);

		List<PropertyType> lProperty = attributes.getProperty();
		PropertyType p;
		for (int i = 0; i < lProperty.size(); i++) {
			p = lProperty.get(i);

			if (p.getName().equals(GUITARConstants.TITLE_TAG_NAME)) {
				lProperty.add(i, newProperty);
				lProperty.remove(p);
			}
		}

	}

	public boolean isRoot() {
		ComponentType window = dGUIType.getWindow();
		ComponentTypeWrapper windowA = new ComponentTypeWrapper(window);
		String isRoot = windowA
				.getFirstValueByName(GUITARConstants.ROOTWINDOW_TAG_NAME);
		return (isRoot.equalsIgnoreCase("true"));

	}

	/**
	 * Check if the window is modal
	 * 
	 * <p>
	 * 
	 * @return
	 */
	public boolean isModal() {
		ComponentType window = dGUIType.getWindow();
		ComponentTypeWrapper windowA = new ComponentTypeWrapper(window);
		String isRoot = windowA
				.getFirstValueByName(GUITARConstants.MODAL_TAG_NAME);
		return (isRoot.equalsIgnoreCase("true"));

	}

	/**
	 * Get a list of windows must be available when this window is opened
	 * 
	 * <p>
	 * 
	 * @return
	 */
	public Set<GUITypeWrapper> getAvailableWindowList() {
		Set<GUITypeWrapper> retWins = new HashSet<GUITypeWrapper>();

		GUITypeWrapper availWindow = this;
		ComponentTypeWrapper invoker;

		while (!availWindow.isModal()) {
			retWins.add(availWindow);
			invoker = availWindow.invoker;
			if (invoker == null)
				break;
			availWindow = invoker.getWindow();
		}

		retWins.add(availWindow);
		// System.out.println("=====================");
		// System.out.println(this);
		// System.out.println("Avail window list" + retWins);
		return retWins;

	}

	/**
	 * @param signature
	 * @param name
	 * @param values
	 */
	public void addValueBySignature(AttributesType signature, String name,
			Set<String> values) {
		ComponentType container = dGUIType.getContainer();
		ComponentTypeWrapper containerA = new ComponentTypeWrapper(container);
		containerA.addValueBySignature(signature, name, values);

	}
}
