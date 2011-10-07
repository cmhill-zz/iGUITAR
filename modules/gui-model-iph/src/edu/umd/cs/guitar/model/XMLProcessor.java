package edu.umd.cs.guitar.model;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringBufferInputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.Configuration;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.ripper.IphRipperConfiguration;

public class XMLProcessor {

	/**
	 * Parse an xml file into Document object
	 * @param file xml source
	 * @return Document object
	 */
	public static Document parse(File file) {
			try {
				return parse(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				return null;
			}
	}
	
	/**
	 * Parse an xml string into Document object
	 * @param inputString xml string
	 * @return Document object
	 */
	public static Document parse(String inputString) {
		InputStream is = new StringBufferInputStream(inputString);
		return parse(is);
	}
	
	/**
	 * Parse an xml InputStream into Document object
	 * @param is InputStream
	 * @return Document object
	 */
	public static Document parse(InputStream is) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			doc = docBuilder.parse(is);
			return doc;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void parseProperties(Map<String, String> nameValueMap, String inputString) {
		if (nameValueMap == null) 
			nameValueMap = new HashMap<String, String>();
		
		Document doc = parse(inputString);
		
		NodeList nodeList = doc.getChildNodes();
		NodeList currentList;
		Node currentNode;
		
		Stack<NodeList> all = new Stack<NodeList>(); //Stack for traverse the doc
		all.push(nodeList);
		while (all.size() != 0) {
			currentList = all.pop();
			for (int i = 0; i < currentList.getLength(); i++) {
				currentNode = currentList.item(i);
				if (currentNode.getNodeName() == "Attributes") {
					currentList = currentNode.getChildNodes();
					for (int j = 0; j < currentList.getLength(); j++) {
						currentNode = currentList.item(j);
						nameValueMap.put(currentNode.getNodeName(), currentNode.getNodeValue());
					}
					return;
				} else {
					all.push(currentNode.getChildNodes());
				}
			}
		}
	}
	
	public static void parseProperties(Map<String, String> nameValueMap, File file) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			parseProperties(nameValueMap, br.readLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperty(List<PropertyType> pList, String propertyName) {
		for (PropertyType p : pList) {
			 String name =  p.getName();
			 if (name == IphConstants.WINDOW_CLASS) 
				 return p.getValue().get(0);
			 if (name == IphConstants.WINDOW_TITLE) 
				 return p.getValue().get(0);
			 if (name == IphConstants.WINDOW_X) 
				 return p.getValue().get(0);
			 if (name == IphConstants.WINDOW_Y) 
				 return p.getValue().get(0);
			 if (name == IphConstants.WINDOW_WIDTH) 
				 return p.getValue().get(0);
			 if (name == IphConstants.WINDOW_HEIGHT) 
				 return p.getValue().get(0);
			 if (name == IphConstants.WINDOW_ENABLED) 
				 return p.getValue().get(0);
			 if (name == IphConstants.WINDOW_VISIBLE)
				 return p.getValue().get(0);
			 if (name == IphConstants.WINDOW_MODAL) 
				 return p.getValue().get(0);
			 if (name == IphConstants.WINDOW_ROOTWINDOW) 
				 return p.getValue().get(0);			 
		 }
		return null;
	}
	
	/**
	 * Get the value associated with the given property name in an xml file
	 * @param file
	 * @param propertyName
	 * @return property value
	 */
	public static String getValue(File file, String propertyName) {
		Document doc = parse(file);
		NodeList nodeList = doc.getChildNodes();
		NodeList currentList;
		Node currentNode;
		Stack<NodeList> all = new Stack<NodeList>(); //Stack for traverse the doc
		all.push(nodeList);
		while (all.size() != 0) {
			currentList = all.pop();
			for (int i = 0; i < currentList.getLength(); i++) {
				currentNode = currentList.item(i);
				if (!currentNode.hasChildNodes()) {
					if (currentNode.getParentNode().getNodeName() == propertyName) {
						return currentNode.getNodeValue();
					}
				} else {
					all.push(currentNode.getChildNodes());
				}
			}
		}
		return null;
	}
	
	/**
	 * Write xml into file
	 * @param xmlContent
	 * @param fileName
	 */
	public static void writeToFile(String xmlContent, String fileName) {
		try {
			Document doc = parse(xmlContent);
			File resultFile = new File(fileName);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(resultFile);
			// Output to console for testing
		    //StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("File saved!");
	}
	
	/**
	 * Create xml file
	 * @return
	 */
	public static File createXmlFile() {
		// Function test
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("company");
			doc.appendChild(rootElement);
			
			// staff elements
			Element staff = doc.createElement("Staff");
			rootElement.appendChild(staff);
	 
			// set attribute to staff element
			Attr attr = doc.createAttribute("id");
			attr.setValue("1");
			staff.setAttributeNode(attr);
			
			// shorten way
			// staff.setAttribute("id", "1");
	 
			// firstname elements
			Element firstname = doc.createElement("firstname");
			firstname.appendChild(doc.createTextNode("yong"));
			staff.appendChild(firstname);
	 
			// lastname elements
			Element lastname = doc.createElement("lastname");
			lastname.appendChild(doc.createTextNode("mook kim"));
			staff.appendChild(lastname);
	 
			// nickname elements
			Element nickname = doc.createElement("nickname");
			nickname.appendChild(doc.createTextNode("mkyong"));
			staff.appendChild(nickname);
	 
			// salary elements
			Element salary = doc.createElement("salary");
			salary.appendChild(doc.createTextNode("100000"));
			staff.appendChild(salary);
			
			// write the content into xml file
			File resultFile = new File("G:\\file.xml");
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(resultFile);
	 
			// Output to console for testing
		    //StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(source, result);
			Document doc1 = docBuilder.newDocument();
			System.out.println("File saved!");
			
			doc1 = docBuilder.parse(resultFile);
			System.out.println(getValue(resultFile, "salary"));

			return resultFile;
		  } catch (ParserConfigurationException pce) {
			  pce.printStackTrace();
		  } catch (TransformerException tfe) {
			  tfe.printStackTrace();
		  } catch (SAXException se) {
			  se.printStackTrace();
		  } catch (IOException ioe) {
			  ioe.printStackTrace();
		  }
		  return null;
	}
}