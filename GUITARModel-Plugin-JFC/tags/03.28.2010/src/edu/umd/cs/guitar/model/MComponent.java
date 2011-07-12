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

import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.model.data.PropertyType;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 * 
 */
public class MComponent {

	ComponentType data;
	MComponent parent;
	MGUI window = null;

	List<MComponent> lChildrenList = new ArrayList<MComponent>();
	MGUI mInvokeWindow = null;

	/**
	 * @param dCom
	 * @param parent
	 * @param window
	 */
	public MComponent(MGUIStructure mGUIStructure, GUIStructure guiStructure,
			ComponentType dCom, MComponent parent, MGUI window) {
		super();
		this.data = dCom;
		this.parent = parent;
		this.window = window;

		String sInvokeList = MExplorer.getFirstPropertyByName(
				MExplorer.INVOKELIST_TAG_NAME, dCom);

		// Create invoke window
		if (sInvokeList != null) {
			GUIType invokeGUI = MExplorer.getGUITypeByName(sInvokeList,
					guiStructure);

			if (invokeGUI != null) {
//				System.err.println(sInvokeList);
//				System.err.println("System.err.println(sInvokeList);");
				mInvokeWindow = new MGUI(mGUIStructure, guiStructure,
						invokeGUI, this);
				mGUIStructure.getLGUI().add(mInvokeWindow);
			}
		} else {
			// System.out.println("!!!" + dCom.getAttributes().getProperty() +"
			// Not found");
		}

		// Create a children list
		if (dCom instanceof ContainerType) {
			List<ComponentType> dChildrenList = ((ContainerType) dCom)
					.getContents().getWidgetOrContainer();
			for (ComponentType dChild : dChildrenList) {
				lChildrenList.add(new MComponent(mGUIStructure, guiStructure,
						dChild, this, this.window));
			}
		}
	}

	public MComponent getFirstComponentByName(String sName) {

		MComponent retComp = null;
		String sCompName = MExplorer.getFirstPropertyByName(
				MExplorer.TITLE_TAG_NAME, data);

		if (sName.equals(sCompName)) {
			return this;
		}

		for (MComponent child : lChildrenList) {
			retComp = child.getFirstComponentByName(sName);
			if (retComp != null)
				return retComp;
		}

		return retComp;
	}

	public List<String> getEvent() {
		return MExplorer.getPropertyListByName(MExplorer.EVENT_TAG_NAME, data);
	}

	public String getID() {
		return MExplorer.getFirstPropertyByName(MExplorer.TITLE_TAG_NAME, data);
	}

	public String getName() {
		String sID = getID();
		String[] sName = sID.split(GUITARConstants.NAME_SEPARATOR);
		if (sName.length > 0)
			return sName[0];
		else
			return null;
	}

	/**
	 * @return the parent
	 */
	public MComponent getParent() {
		return parent;
	}

	/**
	 * @return the window
	 */
	public MGUI getWindow() {
		return window;
	}

	/**
	 * @return the lChildrenList
	 */
	public List<MComponent> getLChildrenList() {
		return lChildrenList;
	}

	/**
	 * @return the mInvokeWindow
	 */
	public MGUI getMInvokeWindow() {
		return mInvokeWindow;
	}

	public void printInfo() {

		List<PropertyType> lProperty = data.getAttributes().getProperty();
		for (PropertyType p : lProperty) {
			System.out.println(p.getName() + ": " + p.getValue().get(0));
		}

	}

	/**
	 * @return the data
	 */
	public ComponentType getData() {
		return data;
	}
	
	public void addProperty(String sName, String sValue) {
		ObjectFactory factory = new ObjectFactory();
		PropertyType property = factory.createPropertyType();
		property.setName(sName);
		property.getValue().add(sValue);
		this.data.getAttributes().getProperty().add(property);
	}
	
	public boolean hasProperty(String sName){
		String result = MExplorer.getFirstPropertyByName(sName, data);
		return (result !=null);
		
	}

}
