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

import edu.umd.cs.guitar.model.data.*;

/**
 * 
 * Utility class to interact with data in {@link edu.umd.cs.guitar.model.data.*}}
 * Under development 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 * 
 */
public class MExplorer {
	
	final static String TITLE_TAG_NAME = "Title";
	final static String INVOKELIST_TAG_NAME = "Invokelist";
	final static String EVENT_TAG_NAME = "ReplayableAction";

	/**
	 * Find GUIType by name 
	 * 
	 * <p>
	 * 
	 * @param sGUIName
	 * @param guiStructure
	 * @return GUIType
	 */
	public static GUIType getGUITypeByName(String sGUIName, GUIStructure guiStructure) {
		List<GUIType> lGUI = guiStructure.getGUI();
		
		for(GUIType gui: lGUI){
			String sName = getFirstPropertyByName(TITLE_TAG_NAME, gui.getWindow());
			if(sGUIName.equals(sName))
				return gui;
		}
		return null;

	}

	/**
	 * Find Property by name 
	 * 
	 * <p>
	 * 
	 * @param sName
	 * @param component
	 * @return String
	 */
	public static  String getFirstPropertyByName(String sName, ComponentType component) {
		AttributesType attributes = component.getAttributes();
		return getFirstPropertyByName(sName, attributes);
	}
	
	public static  String getFirstPropertyByName(String sName, AttributesType attributes) {
		List<PropertyType> lProperty = attributes.getProperty();

		for (PropertyType p : lProperty) {
			if (p.getName().equals(sName)) {
				if (p.getValue().size() > 0)
					return p.getValue().get(0);
				else 
					return null;
			}
		}
		return null;
	}
	
	public static  List<String> getPropertyListByName(String sName, ComponentType component) {
		AttributesType attributes = component.getAttributes();
		return getPropertyListByName(sName, attributes);
	}

	public static  List<String> getPropertyListByName(String sName, AttributesType attributes) {
		List<PropertyType> lProperty = attributes.getProperty();

		for (PropertyType p : lProperty) {
			if (p.getName().equals(sName)) {
				return p.getValue();
			}
		}
		
		return new ArrayList<String>();
	}
}
