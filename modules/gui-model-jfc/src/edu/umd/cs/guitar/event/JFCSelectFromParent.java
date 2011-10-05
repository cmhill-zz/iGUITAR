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
import javax.accessibility.AccessibleContext;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.JFCXComponent;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * Select a sub-item by calling a selection function form its parent
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class JFCSelectFromParent extends JFCEventHandler {

	/**
     * 
     */
	public JFCSelectFromParent() {
		// TODO Auto-generated constructor stub
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// * edu.umd.cs.guitar.event.ThreadEventHandler#actionPerformImp(edu.umd.cs
	// * .guitar.model.GXComponent)
	// */
	// @Override
	// protected void performImpl(GComponent gComponent) {
	// Accessible aChild = ((JFCXComponent) gComponent).getAComponent();
	// Component cChild = (Component) aChild;
	//
	// // Find the closet parent which is support selection
	// Accessible aParent = getSelectableParent(aChild);
	//
	// if (aParent != null) {
	// Method selectionMethod;
	//
	// try {
	// selectionMethod = aParent.getClass().getMethod(
	// "setSelectedComponent", Component.class);
	// selectionMethod.invoke(aParent, cChild);
	// } catch (SecurityException e) {
	// GUITARLog.log.error(e);
	// } catch (NoSuchMethodException e) {
	// GUITARLog.log.error(e);
	// } catch (IllegalArgumentException e) {
	// GUITARLog.log.error(e);
	// } catch (IllegalAccessException e) {
	// GUITARLog.log.error(e);
	// } catch (InvocationTargetException e) {
	// GUITARLog.log.error(e);
	// }
	// }
	//
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.event.ThreadEventHandler#actionPerformImp(edu.umd.cs
	 * .guitar.model.GXComponent)
	 */
	@Override
	protected void performImpl(GComponent gComponent,Hashtable<String, List<String>> optionalData) {
//		Accessible aChild = ((JFCXComponent) gComponent).getComponent();
		Component cChild = ((JFCXComponent) gComponent).getComponent();

		// Find the closet parent which is support selection
		Component aParent = getSelectableParent(cChild);

		if (aParent != null) {

			Method selectionMethod;

			try {
				selectionMethod = aParent.getClass().getMethod(
						"setSelectedComponent", Component.class);
				selectionMethod.invoke(aParent, cChild);
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
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.event.ThreadEventHandler#actionPerformImp(edu.umd.cs
	 * .guitar.model.GXComponent, java.lang.Object)
	 */
	@Override
	protected void performImpl(GComponent gComponent, Object parameters,Hashtable<String, List<String>> optionalData) {

		Integer index = 0;

		try {
			List<String> lParameter = (List<String>) parameters;

			if (lParameter == null) {
				index = 0;
			} else
				index = (lParameter.size() != 0) ? Integer.parseInt(lParameter
						.get(0)) : 0;
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Accessible aChild = ((JFCXComponent) gComponent).getAComponent();

		Component component = ((JFCXComponent) gComponent).getComponent();
		// Find the closet parent which is support selection

		// Accessible aParent = getSelectableParent(aChild);

		Component parent = getSelectableParent(component);

		GUITARLog.log.debug("!!!!!Parent:"+ parent);
		
		if (parent != null) {
			
			System.out.println("GOT HERE");
			
			Method selectionMethod;

			try {

				selectionMethod = parent.getClass().getMethod(
						"setSelectedIndex", int.class);

				selectionMethod.invoke(parent, index);

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
		}

	}

	/**
	 * 
	 * A helper method to find the closest ancestor having setSelectedComponent
	 * method Presumably this method will select the current element.
	 * 
	 * <p>
	 * 
	 * @param aComponent
	 * @return Accessible
	 */
	// private Accessible getSelectableParent(Accessible aComponent) {
	private Component getSelectableParent(Component component) {
		// if (aComponent == null)
		if (component == null)
			return null;

		Component parent = component.getParent();
		Method[] methods = parent .getClass().getMethods();
		for (Method m : methods) {
			if ("setSelectedComponent".equals(m.getName()))
				return parent;
		}

		return getSelectableParent(parent);
		
		
//		AccessibleContext aContext = component.getAccessibleContext();
//
//		if (aContext == null)
//			return null;
//		
//		
//		
//
//		Accessible aParent = aContext.getAccessibleParent();
//		if (aParent == null)
//			return null;
//
//		if (!(aParent instanceof Component))
//			return null;
//
//		


	}

	/* (non-Javadoc)
	 * @see edu.umd.cs.guitar.event.GEvent#isSupportedBy(edu.umd.cs.guitar.model.GComponent)
	 */
	@Override
	public boolean isSupportedBy(GComponent gComponent) {
		// TODO Auto-generated method stub
		return false;
	}
}
