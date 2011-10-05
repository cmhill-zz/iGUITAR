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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Math;

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
public class JFCDefaultHashcodeGenerator extends GHashcodeGenerator {

	static List<String> ID_PROPERTIES = new ArrayList<String>(
			JFCConstants.ID_PROPERTIES);

	Hashtable<List<String>, List<String>> h;

	private static List<String> SIZE_ID_CLASSES = Arrays.asList(
			"javax.swing.JRootPane", "javax.swing.JPanel",
			"javax.swing.JTextPane", "javax.swing.JViewport",
			"javax.swing.JScrollPane$ScrollBar",
			"javax.swing.table.JTableHeader");

	private static List<String> SIZE_ID_PROPERTIES = Arrays.asList("height",
			"width");

	private static List<String> POSITION_ID_CLASSES = Arrays.asList(
			// "javax.swing.plaf.metal.MetalScrollButton",
			"javax.swing.JScrollPane$ScrollBar", "javax.swing.JTextPane",
			"javax.swing.JTextField", "javax.swing.JViewport");

	private static List<String> POSITION_ID_PROPERTIES = Arrays.asList("X",
			"Y");

	// private static List<String> IGNORED_ID_CLASSES = Arrays.asList(
	// "javax.swing.plaf.metal.MetalScrollButton",
	// "javax.swing.JScrollPane$ScrollBar",
	// // Rachota
	// "javax.swing.JSpinner$NumberEditor",
	// "javax.swing.plaf.basic.BasicComboPopup$1",
	// "javax.swing.plaf.basic.BasicComboPopup");

	private static List<String> IGNORED_ID_CLASSES = Arrays.asList();

	private JFCDefaultHashcodeGenerator() {

		h = new Hashtable<List<String>, List<String>>();
		h.put(POSITION_ID_CLASSES, POSITION_ID_PROPERTIES);
		h.put(SIZE_ID_CLASSES, SIZE_ID_PROPERTIES);
	}

	static JFCDefaultHashcodeGenerator instance = null;

	public static JFCDefaultHashcodeGenerator getInstance() {
		if (instance == null)
			instance = new JFCDefaultHashcodeGenerator();
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.model.GHashcodeGenerator#getHashcodeFromData(edu.umd
	 * .cs.guitar.model.data.ComponentType,
	 * edu.umd.cs.guitar.model.data.GUIType)
	 */
	public long getHashcodeFromData(ComponentType dComponent, GUIType dWindow) {

		ComponentTypeWrapper wComponent = new ComponentTypeWrapper(dComponent);

		String sClass = wComponent
				.getFirstValueByName(GUITARConstants.CLASS_TAG_NAME);
		if (IGNORED_ID_CLASSES.contains(sClass)) {
			return 0;
		}

		preprocessID(dComponent);

		final int prime = 31;

		long result = 1;

		AttributesType attributes = dComponent.getAttributes();
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

		String sWindowTitle = getWindowTitle(dWindow);

		result = (prime * result + (sWindowTitle == null ? 0 : (sWindowTitle
				.hashCode())));

		result = (result * 2) & 0xffffffffL;

		return result;

	}

	/**
	 * @param dWindow
	 * @return
	 */
	private String getWindowTitle(GUIType dWindow) {
		GUITypeWrapper wWindow = new GUITypeWrapper(dWindow);
		String sTitle = wWindow.getTitle();
		sTitle = sTitle.replace("*", "");
		return sTitle;
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

	/**
	 * Check if a string is match by a regular expression temporarily used for
	 * matching window titles. Should move to some more general modules for
	 * future use.
	 * 
	 * <p>
	 * 
	 * @param input
	 * @param regExp
	 * @return
	 */
	private boolean isRegMatched(String input, String regExp) {

		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile(regExp);
		matcher = pattern.matcher(input);
		if (matcher.matches())
			return true;

		return false;
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
		ComponentType component = gComponent.extractProperties();
		GUIType window = gWindow.extractWindow();
		return getHashcodeFromData(component, window);
	}
}
