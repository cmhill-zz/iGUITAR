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
package edu.umd.cs.guitar.ripper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.openqa.selenium.RenderedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import edu.umd.cs.guitar.event.EventManager;
import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.WebComponent;
import edu.umd.cs.guitar.model.WebConstants;
import edu.umd.cs.guitar.model.WebWindow;
import edu.umd.cs.guitar.model.WebWindowHandler;
import edu.umd.cs.guitar.model.WebDot;
import edu.umd.cs.guitar.model.WebDot.NodeType;
import edu.umd.cs.guitar.ripper.filter.WebExpandFilter;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * 
 * Monitor for the ripper to handle Web specific features
 * 
 * @see GRipperMonitor
 * 
 * @author Philip Anderson
 */
public class WebRipperMonitor extends GRipperMonitor {
	private WebRipperConfiguration config;
	private WebDriver driver;
	private HtmlUnitDriver driverHtml;
	private WebWindowHandler handler;
	private List<Integer> widths;
	private WebDot dotGraph;
	private String domainName;
	
	public WebRipperMonitor(NewGRipperConfiguration config) {
		super();
		this.config = (WebRipperConfiguration) config;
	}

	@Override
	public void cleanUp() {
		Set<String> copy = new TreeSet<String>();
		
		copy.addAll(driver.getWindowHandles());
		
		for(String handle : copy) {
			driver.switchTo().window(handle);
			driver.close();
			WebWindowHandler.findByDriver(driver).close(handle);
		}
		
		// Needed for DOT
		driverHtml.get(config.WEBSITE_URL);
		String correctRootUrl = driverHtml.getCurrentUrl();
		
		driver.quit();
		
		// Output DOT file and Graphviz graph as JPG
		dotGraph.outputGraph(config.DOT_FILE,correctRootUrl);
	}

	@Override
	public void closeWindow(GWindow window) {
		if(window instanceof WebWindow) {
			driver.switchTo().window(((WebWindow) window).getHandle());
			driver.close();
			driver.switchTo().window(handler.getLegalWindow());
			WebWindowHandler.findByDriver(driver).close(window);
			widths.remove(widths.size()-1);
		}
	}

	@Override
	public void expandGUI(GComponent component) {
		if(!(component instanceof WebComponent))
			return;
		
		WebElement el = ((WebComponent) component).getElement();
		
		if(!(el instanceof RenderedWebElement)) {
			return;
		} else {
			if(!((RenderedWebElement) el).isDisplayed())
				return;
		}
		
		//Check to see if this is a link of the form: <a href="URL"></a>
		if(el.getAttribute("href") != null && el.getTagName().equals("a")) { 
			//New Window is opening, presumably
			String href = el.getAttribute("href");
			String finalURL = "";
			
			//Not sure exactly how to do this. It would be nice if there was a robust way already written
			//Needs to check domain, root links, etc.
			if(href.startsWith("http://") || href.startsWith("https://"))
				finalURL = href;
			else {
				//Must not be a full url
				String current = driver.getCurrentUrl();
				
				if(href.startsWith("/")) {
					//Link is a direct reference from root
					//7 = "http://".length
					int third = 7 + current.substring(7).indexOf('/');
					finalURL = current.substring(0, third) + href;
				} else {
					//Not from root
					String end = current.substring(0, current.lastIndexOf('/') + 1);
					finalURL = end + href;
				}
			}


			
			//Handle DOT stuff
			dotGraph.addNode(finalURL,NodeType.LINK);
			dotGraph.addRelation(((WebComponent)component).getWindow().getTitle(),finalURL);
			
			if(widths.get(widths.size() - 1) >= config.WIDTH || handler.getNumOpenWindows() >= config.DEPTH) {
				//Don't open the link since it would break out WIDTH and DEPTH constraints
				return;
			} else {
				//Load a new page with finalURL
				WebWindowHandler wwh = WebWindowHandler.findByDriver(driver);
				
				if(wwh.createNewWindow(finalURL) != null) {
					widths.set(widths.size()-1, widths.get(widths.size()-1) + 1);
					widths.add(0);
				}
				
				return;
			}
		} else {
			if(!((WebComponent) component).hasLinkParent()) {
				((WebComponent) component).getElement().click();
			}
				
		}
	}

	/* Check if the URLS are not identical, but still point to the same page */
	@Override
	public boolean isRippedWindow(GWindow window) {
		
        String sWindowName = window.getTitle();
		
		if (lRippedWindow.contains(sWindowName))
			return true;
		
		for (String otherWindowName : lRippedWindow)
			if (areTwoUrlsTheSame(sWindowName,otherWindowName))
				return true;
				
		return false;
    }
		
	
	/* Compares two URLs and their variables*/
	public static boolean areTwoUrlsTheSame(String url1, String url2) {
		ArrayList<String> urlList1 = variablesInUrl(url1);
		ArrayList<String> urlList2 = variablesInUrl(url2);
		Collections.sort(urlList1);
		Collections.sort(urlList2);
			
		if (urlList1.equals(urlList2))
			return true;
		else
			return false;
	}
		
	/* Returns a list of the variables and their values in a URL */
	public static ArrayList<String> variablesInUrl(String url) {
		String[] tempList = null;
		ArrayList<String> variableList = new ArrayList<String>();
	
		url = url.substring(url.indexOf('?') + 1, url.length());
		tempList = url.split("&");
			
		for (String str : tempList)
			variableList.add(str);
		
		return variableList;
	}
    
	@Override
	public
	LinkedList<GWindow> getClosedWindowCache() {
		LinkedList<GWindow> l = new LinkedList<GWindow>();
		return l;
	}

	@Override
	public LinkedList<GWindow> getOpenedWindowCache() {		
		return handler.getOpenedWindowCache();
	}

	@Override
	public List<GWindow> getRootWindows() {
		ArrayList<GWindow> arr = new ArrayList<GWindow>();
		
		WebWindow handle = handler.createNewWindow(config.WEBSITE_URL);
		driver.switchTo().window(handle.getHandle());
		arr.add(handle);
		widths.add(0);
		domainName = handle.getDomainName();

		WebWindowHandler.findByDriver(driver).setDomainName(domainName);
		return arr;
	}

	@Override
	public boolean isExpandable(GComponent component, GWindow window) {		
		return true;/*
		if(component instanceof WebComponent) {
			if (((WebComponent) component).getElement().getTagName().equals("body"))
				return true;

			if (((WebComponent) component).getElement().getTagName().equals("a"))
				return true;
		}
		return false;*/
	}

	@Override
	public boolean isIgnoredWindow(GWindow window) {
		return false;
	}

	@Override
	public boolean isNewWindowOpened() {
		return !getOpenedWindowCache().isEmpty();
	}

	@Override
	public boolean isWindowClosed() {
		return false;
	}
	
	@Override
	public void resetWindowCache() {
		handler.resetWindowCache();
	}

	@Override
	public void setUp() {
		EventManager em = EventManager.getInstance();
		
		for (Class<? extends GEvent> event : WebConstants.DEFAULT_SUPPORTED_EVENTS) {
			em.registerEvent(event);
		}
		
		if(config.PROFILE != null) {
			GUITARLog.Info("Configuring Firefox WebDriver to use desired profile name. "
				+ "If the program exits prematurely at this point, then the profile does not"
				+ "exist. Check your Firefox profiles directory, or open the Firefox profile"
				+ "manager to create a new profile.");
			System.setProperty("webdriver.firefox.profile", config.PROFILE);
			GUITARLog.Info("Profile found.");
		}
		System.out.println("Browser : "  + config.BROWSER);
		if(config.BROWSER == null || config.BROWSER == WebRipperConfiguration.Browser.Firefox)
			driver = new FirefoxDriver();
		else if (config.BROWSER == WebRipperConfiguration.Browser.Chrome) {
			if(config.BROWSER_PATH != null)
				System.setProperty("webdriver.chrome.bin", config.BROWSER_PATH);
			driver = new ChromeDriver();
		} else if (config.BROWSER == WebRipperConfiguration.Browser.HTMLUnit) {
			driver = new HtmlUnitDriver();
		} else {
			driver = new InternetExplorerDriver();
		}
		
		driverHtml = new HtmlUnitDriver();
		
		handler = new WebWindowHandler(driver);
		handler.setUp();
		
		widths = new ArrayList<Integer>();
		dotGraph = new WebDot();
		
		if(ripper != null)
			ripper.addComponentFilter(new WebExpandFilter());
		else
			GUITARLog.Info("Ripper in WebRipperMonitor has not been assigned.");
	}
}