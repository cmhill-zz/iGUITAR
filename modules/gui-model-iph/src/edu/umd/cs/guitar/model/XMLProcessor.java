package edu.umd.cs.guitar.model;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

public class XMLProcessor {
 
//	public static void main(String[] args) {
//		(new XMLProcessor()).createXmlFile();
//	}
	public static Document parse(File file) {
			try {
				return parse(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				return null;
			}
	}
	
	public static Document parse(InputStream is) {
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		try {
			return parse(br.readLine());
		} catch (IOException e) {
			return null;
		}
	}
	public static Document parse(String inputString) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			doc = docBuilder.parse(inputString);
			return doc;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/* Desired XML format for IphWindow
		-<Attributes>
			-<Property>
				<Name>Title</Name>
				<Value>window_A<Value>
			-</Property>
			...
			...
		-</Attributes>
	*/
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
	
	
	
	/* Desired XML format for IphWindow
		- <Window>
			-<Attributes>
				-<Property>
					<Name>Title</Name>
					<Value>window_A<Value>
				-</Property>
				...
				...
			-</Attributes>
		-</Window>            
	*/
	public static void parseWindowList(List<IphWindow> lWindow, String xmlContent) {
		Document doc = parse(xmlContent);
		
		NodeList windowList = doc.getChildNodes();
		Node currentWindow;
		ArrayList<String> it;
		
		String className = null;
		String title = null;
		int x = 0;
		int y = 0;
		int width = 0;
		int height = 0;
		boolean isVisible = false;
		boolean isModal = false;;
		boolean isEnabled = false;;
		boolean isRoot = false;;
		for (int i = 0; i < windowList.getLength(); i++) {
			currentWindow = windowList.item(i);
			NodeList propertyList = currentWindow.getChildNodes().item(0).getChildNodes();
			for (int j = 0; j < propertyList.getLength(); j++) {
				String nodeName = propertyList.item(j).getNodeName();
				switch (nodeName) {
				case "Class":
					className = propertyList.item(j).getNodeValue();
					break;
				case "Title":
					title = propertyList.item(j).getNodeName();
					break;
				case "X":
					x = Integer.valueOf(propertyList.item(j).getNodeValue());
					break;
				case "Y":
					y = Integer.valueOf(propertyList.item(j).getNodeValue());
					break;
				case "Width":
					width = Integer.valueOf(propertyList.item(j).getNodeValue());
					break;
				case "Height":
					height = Integer.valueOf(propertyList.item(j).getNodeValue());
					break;
				case "Modal":
					isModal = Boolean.valueOf(propertyList.item(j).getNodeValue());
					break;
			    case "Visible":
			    	isVisible = Boolean.valueOf(propertyList.item(j).getNodeValue());
					break;
			    case "Rootwindow":
					isRoot = Boolean.valueOf(propertyList.item(j).getNodeValue());
					break;
				case "Enabled":
					isEnabled = Boolean.valueOf(propertyList.item(j).getNodeValue());
					break;
				default:
					break;
				}
				IphWindow iWindow = new IphWindow(title, className, x, y, width, height, isVisible, isModal, isEnabled, isRoot);
				lWindow.add(iWindow);
			
			}
		}
	}
	
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