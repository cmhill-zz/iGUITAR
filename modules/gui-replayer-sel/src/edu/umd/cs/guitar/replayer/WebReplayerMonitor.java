package edu.umd.cs.guitar.replayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.event.WebEvent;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.WebApplication;
import edu.umd.cs.guitar.model.WebConstants;
import edu.umd.cs.guitar.model.WebWindow;
import edu.umd.cs.guitar.model.WebWindowHandler;
import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.util.GUITARLog;

import edu.umd.cs.guitar.replayer.sel.NewGReplayerConfiguration;


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


/**
 * Replayer monitor for Java Swing (JFC) application
 * 
 * <p>
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class WebReplayerMonitor extends GReplayerMonitor {
	private FirefoxDriver driver;
	private WebWindowHandler handler;

	public WebReplayerMonitor(NewGReplayerConfiguration config) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void cleanUp() {
		Set<String> copy = new TreeSet<String>();
		
		copy.addAll(driver.getWindowHandles());
		
		for(String handle : copy) {
			driver.switchTo().window(handle);
			driver.close();
		}
		
		driver.quit();
	}

	@Override
	public void connectToApplication() {
		application = new WebApplication(null);
	}

	@Override
	public GEvent getAction(String actionName) {
		GEvent retAction = null;
		
		try {
			Class<?> c = Class.forName(actionName);
			Object action = c.newInstance();

			retAction = (GEvent) action;

		} catch (Exception e) {
			GUITARLog.log.error("Error in getting action named " + actionName, e);
		}

		return retAction;
	}

	@Override
	public Object getArguments(String action) {
		return null;
	}

	@Override
	public GWindow getWindow(String sWindowTitle) {
		GWindow returned = handler.getWindowByURL(sWindowTitle);
		
		if(returned != null)
			return returned;
		else
			System.out.println("Window not found... creating a new one.");
			
		return handler.createNewWindow(sWindowTitle);
	}

	@Override
	public List<PropertyType> selectIDProperties(ComponentType comp) {
		//Select which properties are to be used for disambiguation between elements
		if (comp == null)
			return new ArrayList<PropertyType>();

		List<PropertyType> retIDProperties = new ArrayList<PropertyType>();

		AttributesType attributes = comp.getAttributes();
		List<PropertyType> lProperties = attributes.getProperty();
		for (PropertyType p : lProperties) {
			if (WebConstants.ID_PROPERTIES.contains(p.getName()))
				retIDProperties.add(p);
		}
		return retIDProperties;
	}

	@Override
	public void setUp() {
		driver = new FirefoxDriver();
		
		handler = new WebWindowHandler(driver);
		handler.setUp();
	}

    @Override
    public void delay(int delay)
    {
        // TODO Auto-generated method stub
        
    }
}