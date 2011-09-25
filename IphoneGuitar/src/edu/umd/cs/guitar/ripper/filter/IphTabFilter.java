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
package edu.umd.cs.guitar.ripper.filter;

import java.awt.Component;
import javax.accessibility.Accessible;
import javax.swing.JTabbedPane;
import edu.umd.cs.guitar.event.GEvent;
//import edu.umd.cs.guitar.event.IphSelectFromParent;
import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.IphComponent;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 * 
 */
public class IphTabFilter extends GComponentFilter {

	static GComponentFilter cmIgnoreMonitor = null;

	public synchronized static GComponentFilter getInstance() {
		if (cmIgnoreMonitor == null) {
			cmIgnoreMonitor = new IphTabFilter();
		}
		return cmIgnoreMonitor;
	}

	private IphTabFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.ripper.ComponentFilter#isProcess(edu.umd.cs.guitar.
	 * model.GXComponent)
	 */
	@Override
	public boolean isProcess(GComponent component, GWindow window) {

		if (!(component instanceof IphComponent))
			return false;

		IphComponent jComponent = (IphComponent) component;
		boolean isProcess = (jComponent.getComponent() instanceof JTabbedPane);
		return isProcess;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.ripper.ComponentFilter#ripComponent(edu.umd.cs.guitar
	 * .model.GXComponent)
	 */
	@Override
	public ComponentType ripComponent(GComponent component, GWindow window) {

		ComponentType retComp = component.extractProperties();
		ComponentTypeWrapper wRetComp = new ComponentTypeWrapper(retComp);

		// Remove the event in Tab
		wRetComp.removeProperty(GUITARConstants.EVENT_TAG_NAME);

		IphComponent jComponent = (IphComponent) component;

		// Accessible aTabPanel = jComponent.getAComponent();
		// AccessibleContext aContext = aTabPanel.getAccessibleContext();
		// AccessibleSelection eSelection = aContext.getAccessibleSelection();
		//        
		// List<GXComponent> jTabItems = component.getChildren();
		//        
		// for(GXComponent jTabItem: jTabItems){
		// eSelection.
		// }

		JTabbedPane jTab = (JTabbedPane) jComponent.getComponent();

		// Debug
		// debbug(jTab);

		int nChild = jTab.getComponentCount();

		for (int i = 0; i < nChild; i++) {
/*
			Component child = jTab.getComponent(i);
			// GComponent gChild = new IphComponent((Accessible) child);
			GComponent gChild = new IphComponent(child,window);
			// Select tab
			// Debugger.pause("About to select");
			GEvent eTabSelect = new IphSelectFromParent();
			eTabSelect.perform(gChild, null);
			// jTab.setSelectedComponent(child);
			// End Select tab

			ComponentType guiChild = ripper.ripComponent(gChild, window);
			ComponentTypeWrapper wGuiChild = new ComponentTypeWrapper(guiChild);

			wGuiChild.addValueByName(GUITARConstants.EVENT_TAG_NAME,
					IphSelectFromParent.class.getName());

			// Debug
			// Debugger.pause("Child index: "+jTab.get);

			// End of Debug

			((ContainerType) retComp).getContents().getWidgetOrContainer().add(
					guiChild);
					*/
		}

		// for (GXComponent gTabItem : gTabItems) {
		// Debugger.pause("Tab");
		//            
		// Component jTabIem = (Component) ((IphComponent) gTabItem)
		// .getAComponent();
		// jTab.setSelectedComponent(jTabIem);
		//            
		// }

		return retComp;
	}
}
