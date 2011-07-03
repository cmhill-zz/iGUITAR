package edu.umd.cs.guitar.ripper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * This class will extract links from a website. It will
 * use a configuration file to determine which tags from
 * the site it will extract. After every link has been 
 * extracted it will write and xml document that follows
 * the GUI structure schema.
 * 
 * @author Team Web
 * @version 1.0
 * @since 1.0
 *
 */
public class WebRipper {
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+ 						PROPERTIES								+
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	// toExtractLinks - Links that still need to be crawled
	// extractedLinks - Links that have been crawled
	private ArrayList<String> toExtractLinks, extractedLinks;
	
	// width - specifies how many links on a page we will click
	// depth - specifies how deep into pages we will go
	private int width, depth;
	
	// the final xml document we will output (this is what will use to create
	// elements and to output the final xml)
	private Document guiXML;
	
	// the root node in the gui xml (this is what we will append to)
	private Element root;
	
	// contains tags we will extract and its properties. this also
	// specifies what gui tag will be associated with it
	private HashMap<String, HashMap<String, Object>> configHash;
	
	// the name of the configuration file
	private final String CONF_FILE = "/resources/config/ripperConfigFile.txt";
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+ 						MAIN METHODS							+
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	/**
	 * This is the main constructer of WebRipper. This will init
	 * all class properties.
	 * 
	 * @param url 	The url of the root website
	 * @param w		The width of how many links to crawl
	 * @param d		The depth of how many websites to crawl
	 * 
	 * @author Luis Sanchez
	 * @since 1.0
	 */
	public WebRipper(String url, int w, int d){
		width = w;
		depth = d;
		extractedLinks = new ArrayList<String>();
		toExtractLinks = new ArrayList<String>();
		toExtractLinks.add(url);

		try {
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder p = f.newDocumentBuilder();
			guiXML = p.newDocument();
			root = guiXML.createElement("GUIStructure");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		parseConfigFile();
		crawler(url);
		
	}
	
	public void crawler(String url){
		String url;
		while(!tEL.isEmpty()){
			url = tEL.pop
			if(!aEL.contains(url)){
				tEL.addAll(extract(url));
				aEL.push(url);
			}
		}
		close(); //will append the closing tag of GUI.xml
	}
	
	public ArrayList<String> extract(String url){
		html = Driver.get(url);
		Element gui = new Element("GUI");
		for(key:hash){
			List<WebElement> elements = findElements(By.tag(tag));
			for(WebElement e:elements){
				for(String attribute:hash.get(tag)){
					WebElement.getAttribute(attribute);
					//add all that into xml
				}
			}
		}
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+ 						HELPER METHODS							+
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	/**
	 * Parses the configuration file and places values into a hash
	 * 
	 * @author Luis Sanchez
	 * @since 1.0
	 */
	private void parseConfigFile(){
		try {
			FileInputStream fis = new FileInputStream(CONF_FILE);
			Scanner s = new Scanner(fis);
			
			while(s.hasNextLine()){
				HashMap<String, Object> val = new HashMap<String, Object>();
				String line = s.nextLine();
				
				//Remove Comments
				int index = line.indexOf('#');
				line = line.substring(0, index);
				
				//If line is not a comment then process
				if(line.length() > 0){
					StringTokenizer st = new StringTokenizer(line);
					StringTokenizer props = new StringTokenizer(st.nextToken(";"));
					String htmlTag = props.nextToken(":");
					ArrayList<String> list = new ArrayList<String>();
					
					val.put("guiTag", st.nextToken());
					
					while(props.hasMoreTokens()){
						list.add(props.nextToken(":"));
					}
					
					val.put("properties", list);
					
					configHash.put(htmlTag, val);
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+ 					DEBUG/PRINT METHODS							+
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**
	 * Prints out the config hash in a human readable way
	 * 
	 * @author Luis Sanchez
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	private void printConfigHash(){
		Set<String> keys = configHash.keySet();
		
		System.out.println("*******************************************");
		
		for(String htmlTag : keys){
			HashMap<String, Object> hash = configHash.get(htmlTag);
			String guiTag = (String)hash.get("guiTag");
			ArrayList<String> props = (ArrayList<String>)hash.get("properties");
			
			System.out.println("HTML Tag: " + htmlTag);
			System.out.println("GUI tag: " + guiTag);
			System.out.println("Properties: ");
			for(String p : props)
				System.out.println("\t" + p);
			
			System.out.println("*******************************************");
		}
	}
}
