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
package edu.umd.cs.guitar.model.wrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.GUIType;

/**
 * 
 * Wrapper class to process GUIStructure.
 * 
 * <p>
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 * 
 */
public class GUIStructureWrapper {
	GUIStructure dGUIStructure;
	List<GUITypeWrapper> lGUI;

	/**
     * 
     */
	public void parseData() {
		lGUI = new ArrayList<GUITypeWrapper>();

		Set<GUITypeWrapper> listRootWindows = getRootWindows();

		for (GUITypeWrapper rootWin : listRootWindows) {
			lGUI.add(rootWin);
			rootWin.parseData(dGUIStructure, this);
		}

	}

	/**
	 * @param data
	 */
	public GUIStructureWrapper(GUIStructure data) {
		super();
		this.dGUIStructure = data;
	}

	public void addGUI(GUIType dGUI) {
		this.dGUIStructure.getGUI().add(dGUI);
	}

	public void addGUI(GUITypeWrapper mGUI) {
		this.dGUIStructure.getGUI().add(mGUI.getData());
	}

	public void removeGUI(GUITypeWrapper mGUI) {
		// this.dGUIStructure.getGUI().remove(mGUI);
		List<GUIType> tempGUIList = dGUIStructure.getGUI();
		tempGUIList.remove(mGUI.getData());
		this.dGUIStructure.setGUI(tempGUIList);
	}

	public GUITypeWrapper getGUIByTitle(String sTitle) {
		List<GUIType> lGUI = dGUIStructure.getGUI();

		for (GUIType gui : lGUI) {
			GUITypeWrapper guiA = new GUITypeWrapper(gui);
			String sGUITitle = guiA.getTitle();
			if (sTitle.equals(sGUITitle))
				return guiA;
		}

		return null;
	}

	public boolean contains(GUITypeWrapper obj) {

		if (!(obj instanceof GUITypeWrapper))
			return false;

		for (GUIType gui : dGUIStructure.getGUI()) {
			GUITypeWrapper guiA = new GUITypeWrapper(gui);
			if (guiA.equals(obj))
				return true;
		}
		return false;
	}

	/**
	 * @return the data
	 */
	public GUIStructure getData() {
		return dGUIStructure;
	}

	/**
	 * Get root window from GUIStructure
	 * 
	 * @return
	 */
	public GUITypeWrapper getRoot() {
		// List<GUIType> lGUI = dGUIStructure.getGUI();
		//
		// for (GUIType gui : lGUI) {
		// GUITypeWrapper guiA = new GUITypeWrapper(gui);
		// if (guiA.isRoot())
		// return guiA;
		// }
		// return null;

		Set<GUITypeWrapper> guiList = getRootWindows();
		if (guiList == null)
			return null;

		if (guiList.size() == 0)
			;

		return (GUITypeWrapper) (guiList.toArray())[0];
	}

	/**
	 * 
	 * Find a component in the GUI using its ID
	 * 
	 * <p>
	 * 
	 * @param ID
	 * @return
	 */
	public ComponentTypeWrapper getComponentFromID(String ID) {
		ComponentTypeWrapper retComp = null;

		for (GUIType gui : this.dGUIStructure.getGUI()) {
			GUITypeWrapper guiAdapter = new GUITypeWrapper(gui);
			retComp = guiAdapter.getChildByID(ID);
			if (retComp != null)
				break;
		}
		return retComp;
	}

	/**
	 * @param signature
	 * @param name
	 * @param values
	 */
	public void addValueBySignature(AttributesType signature, String name,
			Set<String> values) {
		for (GUIType dGUI : dGUIStructure.getGUI()) {
			GUITypeWrapper wGUI = new GUITypeWrapper(dGUI);
			wGUI.addValueBySignature(signature, name, values);
		}
		
	}

	public void addValueByName(String sTitle, String sName, String sValue) {
		for (GUIType dGUI : dGUIStructure.getGUI()) {
			GUITypeWrapper wGUI = new GUITypeWrapper(dGUI);
			wGUI.addValueByName(sTitle, sName, sValue);
		}
	}

	/**
	 * Get the list of root windows
	 * 
	 * @return
	 */
	private Set<GUITypeWrapper> getRootWindows() {

		Set<GUITypeWrapper> rootWindows = new HashSet<GUITypeWrapper>();

		for (GUIType dGUI : dGUIStructure.getGUI()) {
			GUITypeWrapper wGUI = new GUITypeWrapper(dGUI);
			if (wGUI.isRoot())
				rootWindows.add(wGUI);
		}
		return rootWindows;

	}

	/**
	 * @return
	 */
	public List<GUITypeWrapper> getGUIs() {
		return this.lGUI;
	}

}
