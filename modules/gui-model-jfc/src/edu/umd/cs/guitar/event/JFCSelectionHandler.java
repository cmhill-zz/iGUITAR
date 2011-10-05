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
package edu.umd.cs.guitar.event;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.List;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleAction;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleSelection;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.JFCXComponent;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class JFCSelectionHandler extends JFCEventHandler {

	Integer selectedIndex;

	/**
     * 
     */
	public JFCSelectionHandler() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.event.JEventHandler#actionPerformImp(edu.umd.cs.guitar
	 * .model.GXComponent)
	 */
	@Override
	protected void performImpl(GComponent component,Hashtable<String, List<String>> optionalData) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.event.JFCEventHandler#actionPerformImp(edu.umd.cs.guitar
	 * .model.GXComponent, java.lang.Object)
	 */
	@Override
	protected void performImpl(GComponent gComponent, Object parameters,Hashtable<String, List<String>> optionalData) {

		if (parameters instanceof List<?>) {
			List<String> lParameter = (List<String>) parameters;
			if (lParameter == null) {
				selectedIndex = 0;
			} else {
				String sIndex = (lParameter.size() != 0) ? lParameter.get(0)
						: "0";
				try {
					selectedIndex = Integer.parseInt(sIndex);
				} catch (Exception e) {
					selectedIndex = 0;
				}
			}
		}

		if (gComponent == null) {
			return;
		}

		// Accessible aComponent = getAccessible(gComponent);
		//
		// if (aComponent == null)
		// return;
		Component component = getComponent(gComponent);

		AccessibleContext aContext = component.getAccessibleContext();

		if (aContext == null)
			return;
		final AccessibleSelection aSelection = aContext
				.getAccessibleSelection();

		if (aSelection == null)
			return;

		Method selectionMethod;

		try {
			selectionMethod = gComponent.getClass().getMethod(
					"setSelectedIndex", Component.class);

			// Component component = (Component) aComponent;
			selectionMethod.invoke(component, selectedIndex);

		} catch (SecurityException e) {
			GUITARLog.log.error(e);
		} catch (NoSuchMethodException e) {
			GUITARLog.log.error(e);
		} catch (IllegalArgumentException e) {
			GUITARLog.log.error(e);
		} catch (IllegalAccessException e) {
			GUITARLog.log.error(e);
		} catch (InvocationTargetException e) {
			GUITARLog.log.error(e);
		}

		// aSelection.
	}

	/* (non-Javadoc)
	 * @see edu.umd.cs.guitar.event.GEvent#isSupportedBy(edu.umd.cs.guitar.model.GComponent)
	 */
	@Override
	public boolean isSupportedBy(GComponent gComponent) {
		
		
		if (!(gComponent instanceof JFCXComponent))
			return false;
		GEvent gFilterEvent;
		gFilterEvent= new JFCActionHandler();
		if (gFilterEvent.isSupportedBy(gComponent))
			return false;
		
		JFCXComponent jComponent = (JFCXComponent) gComponent;
		Component component = jComponent.getComponent();
		AccessibleContext aContext = component.getAccessibleContext();
		if (aContext==null)
			return false;
		
		Object event = aContext.getAccessibleSelection();
		if (event != null)
			return true;
		
		return false;
	}
}
