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
import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import edu.umd.cs.guitar.event.EventManager;
import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.event.IphTouchEvent;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.ContentsType;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.wrapper.AttributesTypeWrapper;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * Implementation for {@link GWindow} for Java Swing
 * 
 * @see GWindow
 * 
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class IphComponent extends GComponent {

	ComponentType componentType = null;
	ContentsType contentsType = null;
	IphComponent parent = null;
	
	public IphComponent(GWindow window) {
		super(window);
		componentType = ((IphWindow) window).guiType.getContainer();
		contentsType = ((IphWindow) window).guiType.getContainer().getContents();
	}

	public IphComponent(ComponentType childComp, IphComponent parent) {
		super(parent.window);
		
		this.componentType = childComp;
		
		if (childComp instanceof ContainerType) {
			this.contentsType = ((ContainerType) childComp).getContents();
		}
		this.parent = parent;
	}
	
	@Override
	public String getTitle() {
		return componentType.getAttributes().getProperty().get(4).getValue().get(0);
	}

	@Override
	public int getX() {
		return new Integer(componentType.getAttributes().getProperty().get(0).getValue().get(0));
	}
	
	@Override
	public int getY() {
		return new Integer(componentType.getAttributes().getProperty().get(1).getValue().get(0));
	}
	
	private int getWidth() {
		return new Integer(componentType.getAttributes().getProperty().get(2).getValue().get(0));
	}
	
	private int getHeight() {
		return new Integer(componentType.getAttributes().getProperty().get(3).getValue().get(0));
	}
	
	private String getClassName() {
		return componentType.getAttributes().getProperty().get(4).getValue().get(0);
	}
	
	public String getAddress() {
		return componentType.getAttributes().getProperty().get(5).getValue().get(0);
	}

	@Override
	public String getClassVal() {
		// TODO Auto-generated method stub
		return componentType.getAttributes().getProperty().get(4).getValue().get(0);
	}

	@Override
	public List<PropertyType> getGUIProperties() {
		// TODO Auto-generated method stub
		return componentType.getAttributes().getProperty();
	}

	@Override
	public List<GEvent> getEventList() {
		List<GEvent> eventList = new ArrayList<GEvent>();
		for (PropertyType property : this.getGUIProperties()) {
			// Go through the properties and find if one contains
			// INVOKE, TOUCH.
			if (property.getName().equals("INVOKE") &&
					property.getValue().get(0).equals("TOUCH")) {
				eventList.add(new IphTouchEvent());
			}
		}
		
		return eventList;
	}

	@Override
	public List<GComponent> getChildren() {
		List<GComponent> iphChildren = new ArrayList<GComponent>();
		
		if (this.componentType instanceof ContainerType) {
			if (((ContainerType) componentType).getContents() == null) {
				
			} else {
				for (ComponentType child : ((ContainerType) componentType).getContents().getWidgetOrContainer()) {
					iphChildren.add(new IphComponent(child, this));
				}
			}
		} 
		
		return iphChildren;
	}

	@Override
	public GComponent getParent() {
		return parent;
	}

	@Override
	public String getTypeVal() {
		return this.getClassName();
	}

	@Override
	public boolean hasChildren() {
		if ( this.contentsType != null && this.contentsType.getWidgetOrContainer().size() > 0)
			return true;
		
		return false;
	}

	@Override
	public boolean isTerminal() {
		return hasChildren();
	}

	@Override
	public boolean isEnable() {
		return true;
	}
	
	public boolean isClickable() {
		// Rongjian Lan: I need this API, please implement it
		for (PropertyType property : this.getGUIProperties()) {
			// Go through the properties and find if one contains
			// INVOKE, TOUCH.
			if (property.getName().equals("INVOKE") &&
					property.getValue().get(0).equals("TOUCH")) {
				System.out.println("IphComponent : " + this.getClassVal()
						+ ", supports TOUCH (clickable)");
				return true;
			}
		}
		
		return false;
	}
}
