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

import java.util.List;

import org.apache.http.NoHttpResponseException;
import org.apache.http.conn.HttpHostConnectException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.ContentsType;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;

/**
 * Implementation for {@link GWindow} for Java Swing
 * 
 * @see GWindow
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class WebWindow extends GWindow {
	private WebDriver driver;
	private String windowHandle;
	
	public WebWindow(WebDriver wd, String windowHandle) {
		super();
		driver = wd;
		this.setHandle(windowHandle);
	}
	
	/*public WebWindow(String url) {
		super();
		driver = new FirefoxDriver();
		driver.get(url);
	}*/
	
	@Override
	public boolean equals(Object window) {
		if(window instanceof WebWindow)
			return windowHandle.equals(((WebWindow) window).getHandle());
		return false;
	}

	@Override
	public GUIType extractGUIProperties() {
		GUIType retGUI;

		//System.out.println("Extracting GUI Properties from window ");
		ObjectFactory factory = new ObjectFactory();
		retGUI = factory.createGUIType();

		// Window

		ComponentType dWindow = factory.createComponentType();
		ComponentTypeWrapper gaWindow = new ComponentTypeWrapper(dWindow);
		dWindow = gaWindow.getDComponentType();

		gaWindow.addValueByName("Title", driver.getTitle());
		gaWindow.addValueByName("URL", driver.getCurrentUrl());

		retGUI.setWindow(dWindow);

		// Container

		ComponentType dContainer = factory.createContainerType();
		ComponentTypeWrapper gaContainer = new ComponentTypeWrapper(dContainer);

		gaWindow.addValueByName("Title", driver.getTitle());
		dContainer = gaContainer.getDComponentType();

		ContentsType dContents = factory.createContentsType();
		((ContainerType) dContainer).setContents(dContents);

		retGUI.setContainer((ContainerType) dContainer);

		return retGUI;
	}

	@Override
	public GComponent getContainer() {
		switchIfNecessary();
		WebComponent wc = new WebComponent(driver.findElement(By.tagName("body")),null, "/html/body", this);
		return wc;
	}

	public void switchIfNecessary() {
		if(!windowHandle.equals(driver.getWindowHandle()))
				driver.switchTo().window(windowHandle);
	}

	@Override
	public boolean isModal() {
		return true;
	}

	@Override
	public boolean isValid() {
		return driver.getWindowHandles().contains(windowHandle);
	}

	@Override
	public List<PropertyType> getGUIProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitle() {
		switchIfNecessary();
		return driver.getCurrentUrl();
	}
	
	public String getDomainName() {
		switchIfNecessary();
		if(driver instanceof HtmlUnitDriver)
			return "";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("return document.domain").toString();
	}

	@Override
	public int getX() {
		return 0;
	}

	@Override
	public int getY() {
		return 0;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setHandle(String windowHandle) {
		this.windowHandle = windowHandle;
	}

	public String getHandle() {
		return windowHandle;
	}
}
