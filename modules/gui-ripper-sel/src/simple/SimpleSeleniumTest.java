package simple;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.RenderedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/*
 * Just a simple WebDriver test file to use when developing.
 */
public class SimpleSeleniumTest {
	public static void main(String[] args) throws MalformedURLException {
		System.setProperty("webdriver.chrome.bin", "/usr/bin/chromium-browser");
		WebDriver wd = new FirefoxDriver();
		
		wd.get("file://localhost/home/phand/shared/Documents/School/Spring%202011/CMSC435/hg/test.html");
		
		wd.get("http://www.phand.net");
		System.out.println(wd.getCurrentUrl());
		wd.get("http://www.phand.net/");
		System.out.println(wd.getCurrentUrl());
		
	}
}
