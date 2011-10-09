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
import java.awt.Dialog;
import java.awt.Window;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleState;
import javax.accessibility.AccessibleStateSet;

import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.ContentsType;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;

/**
 * Implementation for {@link GWindow} for Java Swing
 * 
 * @see GWindow
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class IphWindow extends GWindow {

	public GUIType guiType = null;
	
	private String accessibleComponentSize = null;
	
	private String className = null;
	
	String title;
	int x;
	int y;
	int width;
	int height;
	boolean isVisible;
	boolean isModal;
	boolean isEnabled;
	
	public IphWindow(GUIType guiType) {
		this.guiType = guiType;
		this.isRoot = true;
		
		System.out.println("Creating IphWindow, with attributes: ");
		for (PropertyType propertyType : guiType.getWindow().getAttributes().getProperty()) {
			System.out.print(propertyType.getName() + "\t:\t");
			for (String value : propertyType.getValue()) {
				System.out.print(value + " ");
			}
			System.out.println();
		}
	}
	
	@Override
	public String getTitle() {
		return guiType.getWindow().getAttributes().getProperty().get(4).getValue().get(0);
	}

	@Override
	public int getX() {
		return new Integer(guiType.getWindow().getAttributes().getProperty().get(0).getValue().get(0));
	}

	@Override
	public int getY() {
		return new Integer(guiType.getWindow().getAttributes().getProperty().get(1).getValue().get(0));
	}
	
	@Override
	public List<PropertyType> getGUIProperties() {	
		return guiType.getWindow().getAttributes().getProperty();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.title == "") ? 0 : getTitle().hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object window) {
		if (window != null) {
			return this.hashCode() == ((IphWindow) window).hashCode();
		}
		return false;
	}

	@Override
	public GUIType extractGUIProperties() {
		return guiType;
	}

	@Override
	public boolean isValid() {
		// TODO(cmhill): Add isVisible to the objc iphone xml document.
		/*if (isVisible == false) {
			return false;
		}*/
		if (this.getTitle() == null || this.getTitle() == "") {
			return false;
		}
		return true;
	}

	// Need to talk to Hua He
	@Override
	public GComponent getContainer() {
		return new IphComponent(this);
	}

	@Override
	public boolean isModal() {
		return isModal;
	}

}
