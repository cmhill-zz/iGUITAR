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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import java.awt.Point;

import org.openqa.selenium.By;
import org.openqa.selenium.RenderedWebElement;
import org.openqa.selenium.WebElement;

import edu.umd.cs.guitar.event.EventManager;
import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.event.WebEvent;
import edu.umd.cs.guitar.event.WebToggleCheckbox;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * Implementation for {@link GComponent} for Web interfaces
 * 
 * @see GWindow
 * 
 * 
 * @author <a href="mailto:phand@umd.edu"> Philip Anderson </a>
 */
public class WebComponent extends GComponent {
	private WebElement element;
	private WebComponent parent;
	private String title;
	private String xpath;
	
	public WebComponent(WebElement el, WebComponent parent, String xpath, GWindow window) {
		super(window);
		element = el;
		this.parent = parent;
		this.title = el.getTagName();
		this.xpath = xpath;
	}

	@Override
	public List<GComponent> getChildren() {
		switchIfNecessary();
		
		List<WebElement> els = element.findElements(By.xpath("*"));
		List<GComponent> comps = new ArrayList<GComponent>();
		
		for(WebElement el : els) {
			comps.add(new WebComponent(el, this, xpath+"", window));
		}
		
		return comps;
	}

	@Override
	public String getClassVal() {
		return element.getClass().getName();
	}

	@Override
	public List<GEvent> getEventList() {
		switchIfNecessary();
		
		List<GEvent> retEvents = new ArrayList<GEvent>();
		EventManager em = EventManager.getInstance();

		for (Class<? extends GEvent> event : em.getEvents()) {
			Constructor<? extends GEvent> constructor;
			try {

				constructor = event.getConstructor(new Class[] {});
				Object obj = constructor.newInstance();
				if (obj instanceof GEvent) {
					GEvent gEvent = (GEvent) obj;
					if (gEvent.isSupportedBy(this))
						retEvents.add(gEvent);
					else
						GUITARLog.Debug(this + " NSB Not supported by " + event.getCanonicalName());
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return retEvents;
	}

	private void switchIfNecessary() {
		getWindow().switchIfNecessary();
	}

	@Override
	public GComponent getParent() {		
		return parent;
	}

	@Override
	public String getTypeVal() {
		return GUITARConstants.SYSTEM_INTERACTION;
	}

	@Override
	public boolean hasChildren() {
		switchIfNecessary();
		//System.out.println("STEE " + ((WebWindow) window).getHandle() + " " + ((WebWindow) window).getDriver().getWindowHandle());
		return !element.findElements(By.xpath("*")).isEmpty();
	}

	@Override
	public boolean isEnable() {
		return element.isEnabled();
	}

	@Override
	public boolean isTerminal() {
		return false;
	}

	@Override
	public List<PropertyType> getGUIProperties() {
		List<PropertyType> retList = new ArrayList<PropertyType>();
		// Other properties

		PropertyType p;
		List<String> lPropertyValue;
		String sValue;

		// Title
		sValue = null;
		sValue = getTitle();
		if (sValue != null) {
			p = factory.createPropertyType();
			p.setName(WebConstants.TITLE_TAG);
			lPropertyValue = new ArrayList<String>();
			lPropertyValue.add(sValue);
			p.setValue(lPropertyValue);
			retList.add(p);
		}
		// Value
		sValue = null;
		sValue = getValue();
		if (sValue != null) {
			p = factory.createPropertyType();
			p.setName(WebConstants.VALUE_TAG);
			lPropertyValue = new ArrayList<String>();
			lPropertyValue.add(sValue);
			p.setValue(lPropertyValue);
			retList.add(p);
		}
		// ID
		sValue = null;
		sValue = getTagID();
		if (sValue != null) {
			p = factory.createPropertyType();
			p.setName(WebConstants.TAGID_TAG);
			lPropertyValue = new ArrayList<String>();
			lPropertyValue.add(sValue);
			p.setValue(lPropertyValue);
			retList.add(p);
		}


		// href
		sValue = null;
		sValue = getHref();
		if (sValue != null) {
			p = factory.createPropertyType();
			p.setName(WebConstants.HREF_TAG);
			lPropertyValue = new ArrayList<String>();
			lPropertyValue.add(sValue);
			p.setValue(lPropertyValue);
			retList.add(p);
		}
		
		// Get Screenshot
		return retList;
	}

	private String getTagID() {
		return element.getAttribute("id");
	}
	
	private String getHref() {
		return element.getAttribute("href");
	}

	private String getValue() {
		if(!hasChildren()) {
			element.getText();
		}
		return null;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public int getX() {
		if(element instanceof RenderedWebElement) {
			Point p =  ((RenderedWebElement) element).getLocation();
			
			return (int) p.getX();
		}
		return 0;
	}

	@Override
	public int getY() {
		if(element instanceof RenderedWebElement) {
			Point p =  ((RenderedWebElement) element).getLocation();
			
			return (int) p.getY();
		}
		return 0;
	}

	public WebElement getElement() {
		return element;
	}
	
	//Returns parent window
	public WebWindow getWindow() {
		return (WebWindow) window;
	}

	public boolean hasLinkParent() {
		if(title.equals("a"))
			return true;
		
		if(parent == null)
			return false;
		else
			return parent.hasLinkParent();
	}
}
