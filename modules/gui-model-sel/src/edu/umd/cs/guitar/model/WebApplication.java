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

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.umd.cs.guitar.exception.ApplicationConnectException;

/**
 * Implementation for {@link GApplication} for Web
 * 
 * @see GApplication
 * 
 * @author <a href="mailto:phand@umd.edu"> Philip Anderson </a>
 */
public class WebApplication extends GApplication {
	private String originURL;
	private WebDriver created;
	
	public WebApplication(String url) {
		super();

		Set<URL> lURLs = new HashSet<URL>();
		URLClassLoader sysLoader = (URLClassLoader) ClassLoader
				.getSystemClassLoader();

		URL urls[] = sysLoader.getURLs();
		for (int i = 0; i < urls.length; i++) {
			lURLs.add(urls[i]);
		}

		URL[] arrayURLs = (lURLs.toArray(new URL[lURLs.size()]));

		URLClassLoader loader = new URLClassLoader(arrayURLs);
		originURL = url;
	}
	
	@Override
	public void connect() throws ApplicationConnectException {
		
	}

	@Override
	public void connect(String[] args) throws ApplicationConnectException {
		//Initialize any thing having to do with Chromium and Firefox plugins, for example. 
		//The type of WebDriver to use.
		created = new HtmlUnitDriver();
		created.get(originURL);
	}

	@Override
	public Set<GWindow> getAllWindow() {
		Set<GWindow> s = new HashSet<GWindow>();
		WebWindowHandler wwh = WebWindowHandler.getWebWindowHandler();
		
		s.addAll(wwh.getAllWindows());
		
		return s;
	}
}
