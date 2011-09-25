/*  
 *  Copyright (c) 2009-@year@. The GUITAR group at the University of Maryland. Names of owners of this group may
 *  be obtained by sending an e-mail to atif@cs.umd.edu
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 *  documentation files (the "Software"), to deal in the Software without restriction, including without 
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *  the Software, and to permit persons to whom the Software is furnished to do so, subject to the following 
 *  conditions:
 * 
 *  The above copyright notice and this permission notice shall be included in all copies or substantial 
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 *  LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 *  EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 *  THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package edu.umd.cs.guitar.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.spi.RootCategory;

import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.model.wrapper.GUITypeWrapper;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao N. Nguyen </a>
 * 
 */
public class WebContextHashcodeGenerator extends GHashcodeGenerator {

	static List<String> ID_PROPERTIES = new ArrayList<String>(
			WebConstants.ID_PROPERTIES);

	Hashtable<List<String>, List<String>> h;

	private static List<String> SIZE_ID_CLASSES = Arrays.asList(
			"javax.swing.JRootPane", "javax.swing.JPanel",
			"javax.swing.JTextPane", "javax.swing.JViewport",
			"javax.swing.JScrollPane$ScrollBar",
			"javax.swing.table.JTableHeader");

	private static List<String> SIZE_ID_PROPERTIES = Arrays.asList("height",
			"width");

	private static List<String> POSSITION_ID_CLASSES = Arrays.asList(
			// "javax.swing.plaf.metal.MetalScrollButton",
			"javax.swing.JScrollPane$ScrollBar", "javax.swing.JTextPane",
			"javax.swing.JTextField", "javax.swing.JViewport");

	private static List<String> IGNORED_ID_CLASSES = Arrays.asList(
			"javax.swing.plaf.metal.MetalScrollButton",
			"javax.swing.JScrollPane$ScrollBar",
			// Rachota
			"javax.swing.JSpinner$NumberEditor",
			"javax.swing.plaf.basic.BasicComboPopup$1",
			"javax.swing.plaf.basic.BasicComboPopup");

	private static List<String> POSSITION_ID_PROPERTIES = Arrays.asList("x",
			"y");

	static WebContextHashcodeGenerator instance = null;

	public static WebContextHashcodeGenerator getInstance() {
		if (instance == null)
			instance = new WebContextHashcodeGenerator();
		return instance;
	}

	/**
	 * 
	 */
	public WebContextHashcodeGenerator() {
		h = new Hashtable<List<String>, List<String>>();
		h.put(POSSITION_ID_CLASSES, POSSITION_ID_PROPERTIES);
		h.put(SIZE_ID_CLASSES, SIZE_ID_PROPERTIES);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.model.GHashcodeGenerator#getHashcodeFromData(edu.umd
	 * .cs.guitar.model.data.ComponentType,
	 * edu.umd.cs.guitar.model.data.GUIType)
	 */
	@Override
	public long getHashcodeFromData(ComponentType component, GUIType dWindow) {
		
		ComponentTypeWrapper wComponent = new ComponentTypeWrapper(component);

		String sClass = wComponent
				.getFirstValueByName(GUITARConstants.CLASS_TAG_NAME);
		if (IGNORED_ID_CLASSES.contains(sClass)) {
			return 0;
		}
		
		ComponentType root = dWindow.getContainer();

		final int prime = 31;

		long result = 1;

		// Window title

		result = prime * result + getWindowHashcode(dWindow);
		result = prime * result + getComponnentTreeHashcode(component, root);

		result = (result * 2) & 0xffffffffL;
		return result;
	}

	long getComponnentTreeHashcode(ComponentType component, ComponentType root) {

		ComponentTypeWrapper wRoot = new ComponentTypeWrapper(root);
		wRoot.parseData();

		ComponentTypeWrapper target = findNode(component, wRoot);
		
		System.out.println("Component:" + target.getFirstValueByName(GUITARConstants.TITLE_TAG_NAME));

		long result = 1;
		long prime = 31;

		List<ComponentTypeWrapper> path = new ArrayList<ComponentTypeWrapper>();
		
		while (!wRoot.equals(target)) {
			result = result * prime
					+ getComponnentHashcode(target.getDComponentType());
			path.add(target);
			target = target.getParent();
		}
		path.add(wRoot);
		
		// Testing
		for (ComponentTypeWrapper comp : path) {
			System.out.print(comp
					.getFirstValueByName(GUITARConstants.TITLE_TAG_NAME) +", ");
		}
		System.out.println();
		

		result = result * prime
				+ getComponnentHashcode(wRoot.getDComponentType());

		return result;
	}

	LinkedList<ComponentType> path = new LinkedList<ComponentType>();

	ComponentTypeWrapper findNode(ComponentType component,
			ComponentTypeWrapper wRoot) {

		ComponentTypeWrapper result = null;
		if (component.equals(wRoot.getDComponentType())) {
			return wRoot;
		}

		List<ComponentTypeWrapper> lChildren = wRoot.getChildren();

		if (lChildren == null)
			return result;

		for (ComponentTypeWrapper child : wRoot.getChildren()) {
			ComponentTypeWrapper find = findNode(component, child);
			if (find != null)
				return find;
		}

		return result;

	}

	long getComponnentHashcode(ComponentType component) {
		ComponentTypeWrapper wComponent = new ComponentTypeWrapper(component);

		String sClass = wComponent
				.getFirstValueByName(GUITARConstants.CLASS_TAG_NAME);
		if (IGNORED_ID_CLASSES.contains(sClass)) {
			return 0;
		}

		preprocessID(component);

		final int prime = 31;

		long result = 1;

		AttributesType attributes = component.getAttributes();
		if (attributes == null)
			return result;

		List<PropertyType> lProperty = attributes.getProperty();
		if (lProperty == null)
			return result;

		for (PropertyType property : lProperty) {
			if (ID_PROPERTIES.contains(property.getName())) {

				String name = property.getName();
				result = (prime * result + (name == null ? 0 : name.hashCode()));

				List<String> valueList = property.getValue();
				if (valueList != null)
					for (String value : valueList) {
						result = (prime * result + (value == null ? 0 : (value
								.hashCode())));

					}

			}
		}

		result = (result * 2) & 0xffffffffL;

		return result;

	}

	/**
	 * @param dComponent
	 * 
	 */
	private void preprocessID(ComponentType dComponent) {
		ComponentTypeWrapper wComponent = new ComponentTypeWrapper(dComponent);

		// Generate ID Properties list for widget
		String sClass = wComponent
				.getFirstValueByName(GUITARConstants.CLASS_TAG_NAME);

		for (List<String> lClassList : h.keySet()) {

			if (lClassList.contains(sClass)) {
				ID_PROPERTIES.addAll(h.get(lClassList));
			}
		}

	}

	long getWindowHashcode(GUIType dWindow) {
		GUITypeWrapper wWindow = new GUITypeWrapper(dWindow);
		return wWindow.getTitle().hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.model.GHashcodeGenerator#getHashcodeFromGUI(edu.umd
	 * .cs.guitar.model.GComponent, edu.umd.cs.guitar.model.GWindow)
	 */
	@Override
	public long getHashcodeFromGUI(GComponent gComponent, GWindow gWindow) {
		// TODO Auto-generated method stub
		return 0;
	}

}
