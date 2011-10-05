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
package edu.umd.cs.guitar.replayer;

import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.guitar.model.GUITARConstants;


/**
 * A structure representing a event  with 
 * event name and its parameters. Will be changed in near future  
 * 
 * <p>
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 *
 */
@Deprecated
public class EventData {
	String name;
	List<String> parameters;

	public EventData(String sFullEvent){
		String[] sEvents = sFullEvent.split(GUITARConstants.NAME_SEPARATOR);
		name = sEvents[0];
		parameters = new ArrayList<String>();
		
		for(int i=1;i<sEvents.length;i++){
			parameters.add(sEvents[i]);
		}
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the parameters
	 */
	public List<String> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

}
