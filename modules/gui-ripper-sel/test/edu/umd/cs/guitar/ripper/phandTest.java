package edu.umd.cs.guitar.ripper;

import java.io.*;
import java.util.ArrayList;

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
 * Test cases for Phil's website test assumes width 3 and depth 2
 * @author brian
 * 
 */

public class phandTest extends TestCase {
	static DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
	static Document doc = null;
	static DocumentBuilder p = null;
	
	/**
	 * 1st assert checks to make sure all windows are in the phand domain
	 * 2nd assert checks to make sure that the root window is http://www.phand.net
	 * 3rd assert makes sure that only 5 windows were ripped
	 * @throws Exception
	 * @author brian
	 */
	public void testPhandNet() throws Exception {
		//Execute
		String[] args = new String[]{"--website-url", "http://www.phand.net", "-w", "3", "-d", "2","-g","dotFile","--gui-file","phandTestGui.GUI"};
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
			doc = p.parse("../../GUIRipper-Plugin-Web/test/phandTestGui.GUI");
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
						assertTrue(domain.contains("phand.net"));
						windows++;
						if (domain.equals("http://www.phand.net/")){
							String isRoot = command2.getChildNodes().item(1).getTextContent();
							assertTrue(isRoot.contains("Rootwindow") && isRoot.contains("true"));
						}
					}
				}
			}
		}
		assertTrue(windows == 4);
		
	}
	
	/**
	 * Checks to make sure dotFile outputted is correct
	 * 
	 * @author Brian
	 */
	public void testDotFile() throws Exception{
		try{
			FileInputStream fstream = new FileInputStream("../../GUIRipper-Plugin-Web/test/dotFile");
			FileInputStream fstream2 = new FileInputStream("../../correctDot");
			
			DataInputStream in = new DataInputStream(fstream);
	        BufferedReader br = new BufferedReader(new InputStreamReader(in));
	        
	        DataInputStream in2 = new DataInputStream(fstream2);
	        BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
			
	        ArrayList<String> lines = new ArrayList<String> ();
	        String line = br.readLine();
	        String line2 = br2.readLine();
	             
	        while (line != null){
	        	lines.add(line);
	        	line = br.readLine();
	        }
	        
	        while (line2 != null) {
	        	assertTrue(lines.contains(line2));
	        	line2 = br2.readLine();
	        }
	        	        
	        assertTrue(line == null && line2 == null); 
	              
		} catch(Exception e){fail();}
	}
}
