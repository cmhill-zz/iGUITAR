package edu.umd.cs.guitar.ripper;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import junit.framework.TestCase;

/**
 * Test cases for Leyla's website test 
 * @author brian
 * 
 */

public class terpConnectTest extends TestCase {
	static DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
	static Document doc = null;
	static DocumentBuilder p = null;
	
	/**
	 * 1st assert checks to make sure all windows are in the terpconnect domain
	 * 2nd assert checks to make sure that the root window is http://terpconnect.umd.edu/~leylan/helloworld.html
	 * 3rd assert makes sure that only 1 window was ripped
	 * @throws Exception
	 * @author brian
	 */
	public void testHelloWorld() throws Exception {
		//Execute
		String[] args = new String[]{"--website-url", "http://terpconnect.umd.edu/~leylan/helloworld.html", "-w", "1", "-d", "1","--gui-file","helloWorldGUI.GUI"};
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
			doc = p.parse("../../GUIRipper-Plugin-Web/test/helloWorldGUI.GUI");
		} catch (SAXException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();
		}
		Element docElement = doc.getDocumentElement();
		NodeList nodes = docElement.getChildNodes();
		int windows = 0;
		for(int i = 0; i < nodes.getLength(); ++i) {
			Node command = nodes.item(i);
			if (command.getNodeName() == "GUI"){
				NodeList guiChildren = command.getChildNodes();
				for (int j = 0; j < guiChildren.getLength(); ++j){
					Node command2 = guiChildren.item(j);
					if (command2.getNodeName() == "Window"){
						String domain = command2.getChildNodes().item(1).getTextContent();
						assertTrue(domain.contains("terpconnect"));
						windows++;
						if (domain.equals("http://terpconnect.umd.edu/~leylan/helloworld.html")){
							String isRoot = command2.getChildNodes().item(1).getTextContent();
							assertTrue(isRoot.contains("Rootwindow") && isRoot.contains("true"));
						}
					}
				}
			}
		}
		assertTrue(windows == 1);
	}
	
	/** 
	 * Test terpconnect.umd.edu/~leyland/links.html to make sure it doesn't leave domain and go to google, cnn or facebook
	 * width 4 depth 1
	 * 
	 */
	public void testLinksDomain() throws Exception {
		//Execute
		String[] args = new String[]{"--website-url", "http://terpconnect.umd.edu/~leyland/links.html", "-w", "4", "-d", "1","--gui-file","linksGUI.GUI"};
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
			doc = p.parse("../../GUIRipper-Plugin-Web/test/linksGUI.GUI");
		} catch (SAXException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();
		}
		Element docElement = doc.getDocumentElement();
		NodeList nodes = docElement.getChildNodes();
		for(int i = 0; i < nodes.getLength(); ++i) {
			Node command = nodes.item(i);
			if (command.getNodeName() == "GUI"){
				NodeList guiChildren = command.getChildNodes();
				for (int j = 0; j < guiChildren.getLength(); ++j){
					Node command2 = guiChildren.item(j);
					if (command2.getNodeName() == "Window"){
						String domain = command2.getChildNodes().item(1).getTextContent();
						assertTrue(domain.contains("terpconnect"));
					}
				}
			}
		}
	}
}