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

import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.GUIType;

/**
 * A wrapper for  {@link GUIStructure} to handle the relation between 
 * GUI components
 *  
 * <p>
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 * @see edu.umd.cs.guitar.model.data.GUIStructure  
 */
public class MGUIStructure {
	
	GUIStructure data;
	List<MGUI> lGUI= new ArrayList<MGUI>();
	

	/**
	 * @param dGuiStructure
	 */
	public MGUIStructure(GUIStructure dGuiStructure) {
		super();
		this.data = dGuiStructure;
		
		GUIType dRootwidow = getDRoot();
		MGUI mRootWindow = new MGUI(this, dGuiStructure, dRootwidow,null);
		lGUI.add(mRootWindow);
	}

	
	/**
	 * Return root window of a GUI file
	 * @return GUIType
	 */
	private GUIType getDRoot() {
		int nGUI = data.getGUI().size();
		// Assuming that the last window is the root win
		// We may also check the Rootwindow property for a more accurate result  
		return data.getGUI().get(nGUI-1);
	}
	
	public MGUI getMRoot() {
		int nGUI = lGUI.size();
		// Assuming that the last window is the root win
		// We may also check the Rootwindow property for a more accurate result  
		return lGUI.get(nGUI-1);
		
	}
	
	
	public MComponent getFirstChildByName(String sName){
		MComponent retComp = null;
		for (MGUI  gui : lGUI) {
			retComp = gui.getFirstComponentByName(sName);
			if (retComp != null)
				return retComp;
		}
		return retComp;
	}

	/**
	 * @return the lGUI
	 */
	public List<MGUI> getLGUI() {
		return lGUI;
	}
	

}
