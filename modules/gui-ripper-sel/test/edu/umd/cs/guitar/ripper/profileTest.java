package edu.umd.cs.guitar.ripper;

import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;



import junit.framework.TestCase;

/**
 * Test cases for Leyla's website test 
 * @author brian
 * 
 */

public class profileTest extends TestCase {
	static DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
	static Document doc = null;
	static DocumentBuilder p = null;
	
	/**
	 * 1st assert checks to see if Firefox profile switching works (example: does Yahoo mail stay logged in across multiple tests)
	 * @throws Exception
	 * @author brian
	 */
	public void testStaysSignedOn() throws Exception {
		
		//Login to Yahoo mail with separate WebDriver
		System.setProperty("webdriver.firefox.profile", "default");
		WebDriver driver = new FirefoxDriver();
		driver.get("https://login.yahoo.com/");
        WebElement login = driver.findElement(By.id("username"));
        login.sendKeys("cmsc435umd");
        WebElement password = driver.findElement(By.id("passwd"));
        password.sendKeys("guitar2011");
        password.submit();
        
        
		driver.quit();
		
		
		
		
		//Check to see if the Ripper can correctly use the profile used to login to Yahoo
		//Execute
		String[] args = new String[]{"--website-url", "https://mail.yahoo.com/", "-w", "1", "-d", "1","--gui-file","YahooGUI.GUI","-p","default"};
		WebRipperConfiguration wrc = new WebRipperConfiguration();
		wrc.parseArguments(args);
		WebRipperMain wrm = new WebRipperMain(wrc);
		wrm.execute();
		
		try {
			p = f.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		try {
			doc = p.parse("../../GUIRipper-Plugin-Web/test/YahooGUI.GUI");
		} catch (SAXException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();
		}
		Element docElement = doc.getDocumentElement();
		NodeList nodes = docElement.getChildNodes();
		
		assertTrue(getStringOfDocument(doc).contains("cmsc435umd - Yahoo! Mail"));
	}


	public static String getStringOfDocument(Document doc)
	{
		try
		{
		   DOMSource domSource = new DOMSource(doc);
		   StringWriter writer = new StringWriter();
		   StreamResult result = new StreamResult(writer);
		   TransformerFactory tf = TransformerFactory.newInstance();
		   Transformer transformer = tf.newTransformer();
		   transformer.transform(domSource, result);
		   return writer.toString();
		}
		catch(TransformerException ex)
		{
		   ex.printStackTrace();
		   return "";
		}

		
	}




}
