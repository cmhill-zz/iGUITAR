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

import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.GUIType;

/**
 * A wrapper for {@link GUI} to handle the relation between GUI components
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 * 
 */
public class MGUI {

	GUIType data;
	
	MComponent invoker;
	AttributesType attributes;
	List<MComponent> lComponent;

	/**
	 * @param data
	 * @param invoker
	 */
	public MGUI(MGUIStructure mGUIStructure, GUIStructure guiStructure,
			GUIType data, MComponent invoker) {

		this.data = data;
		if (invoker != null)
			this.invoker = invoker;

		attributes = data.getWindow().getAttributes();

		List<ComponentType> dataComponent = data.getContainer().getContents()
				.getWidgetOrContainer();

		MComponent component;
		lComponent = new ArrayList<MComponent>();
		for (ComponentType comp : dataComponent) {
			component = new MComponent(mGUIStructure, guiStructure, comp, null,
					this);
			lComponent.add(component);
		}
	}

	public MComponent getFirstComponentByName(String sName) {
		MComponent retComp = null;
		for (MComponent component : lComponent) {
			retComp = component.getFirstComponentByName(sName);
			if (retComp != null)
				return retComp;
		}
		return retComp;
	}

	/**
	 * @return the data
	 */
	public GUIType getData() {
		return data;
	}
	
	
	
	public String getTitle() {
		String sTitle = MExplorer.getFirstPropertyByName(
				MExplorer.TITLE_TAG_NAME, this.attributes);
		
		return sTitle;
	}
	
	

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(GUIType data) {
		this.data = data;
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof MGUI))
			return false;
		
		MGUI other = (MGUI)obj;  
		
		String sMyTitle = getTitle();
		String sOtherTitle = other.getTitle();

		return sMyTitle.equals(sOtherTitle);

	}

}
