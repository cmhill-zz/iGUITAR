package edu.umd.cs.guitar.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/*
 * Handles creation of new windows for a given WebDriver
 */
public class WebWindowHandler {
	private WebDriver driver;
	private WebElement newPage;
	private String newPageHandle;
	private LinkedList<WebWindow> allWindows;
	private LinkedList<GWindow> newWindows;
	private String domainName = null;
	private static HashMap<WebDriver, WebWindowHandler> map;
	
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public WebWindowHandler(WebDriver driver) {
		this.driver = driver;
		this.domainName = domainName;
	}
	
	public void setUp() {
		map = new HashMap<WebDriver, WebWindowHandler>();
		driver.get(WebConstants.NEW_PAGE_URL);
		newPage = driver.findElement(By.id("NewPageLink"));
		newPageHandle = driver.getWindowHandle();
		map.put(driver, this);
		newWindows = new LinkedList<GWindow>();
		allWindows = new LinkedList<WebWindow>();
	}

	public WebWindow createNewWindow() {
		return createNewWindow("about:blank");
	}

	public WebWindow createNewWindow(String URL) {
		//Store previous handle so we can go back to it
		String prev = driver.getWindowHandle();
		Set<String> oldSet = driver.getWindowHandles();
		driver.switchTo().window(newPageHandle);
		newPage.click();
		
		Set<String> newSet = driver.getWindowHandles();
		newSet.removeAll(oldSet);
		String handle = newSet.iterator().next();
		driver.switchTo().window(handle);
		driver.get(URL);
		WebWindow w = new WebWindow(driver, handle);
		
		if(domainName != null && !domainName.equals(w.getDomainName())) {
			driver.close();
			driver.switchTo().window(prev);
			return null;
		}

		allWindows.add(w);
		newWindows.add(w);
		driver.switchTo().window(prev);
		return w;
	}
	
	public static WebWindowHandler findByDriver(WebDriver d) {
		return map.get(d);
	}
	
	public static WebWindowHandler getWebWindowHandler() {
		return map.values().iterator().next();
	}

	public void resetWindowCache() {
		newWindows = new LinkedList<GWindow>();
	}

	public LinkedList<GWindow> getOpenedWindowCache() {
		return newWindows;
	}

	public LinkedList<WebWindow> getAllWindows() {
		return allWindows;
	}
	
	public int getNumOpenWindows() {
		return driver.getWindowHandles().size() - 1;
	}

	public String getLegalWindow() {
		//Returns any legal window for driver
		if(driver.getWindowHandles().isEmpty())
			return null;
		
		return driver.getWindowHandles().iterator().next();
	}

	public WebWindow getWindowByURL(String sWindowTitle) {
		for(WebWindow w : allWindows) {
			if(w.getTitle().equals(sWindowTitle))
				return w;
		}
		
		return null;
	}

	public void close(String handle) {
		for(int i = allWindows.size() - 1; i >= 0; i--) {
			if(allWindows.get(i).getHandle().equals(handle))
				allWindows.remove(i);
		}
	}

	public void close(GWindow window) {
		allWindows.remove(window);		
	}
}
