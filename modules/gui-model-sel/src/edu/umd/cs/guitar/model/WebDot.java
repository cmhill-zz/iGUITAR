/*  
 *  Copyright (c) 2009-@year@. The GUITAR group at the University of Maryland. Names of owners of this group may
 *  be obtained by sending an e-mail to atif@cs.umd.edu
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 *  documentation files (the "Software"), to deal in the Software without restriction, including without 
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *  the Software, and to permit persons to whom the Software is furnished to do so, subject to the following 
 *  conditions:
 * 
 *  The above copyright notice and this permission notice shall be included in all copies or substantial 
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 *  LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 *  EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 *  THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package edu.umd.cs.guitar.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.HashMap;
import java.util.HashSet;

/* Class used to represent a directed graph of the link structure of a website */
public class WebDot {

	/* Store the root page's URL */
	private String rootUrl;
	
	/* Store directed graph structure */
	private HashMap<String,HashSet<String>> dotGraph;
	
	/* Store info of each node */
	private HashSet<DotNode> dotGraphNodes;
	
	public enum NodeType {
		LINK, IMAGE;
	}
	
	public WebDot() {
		rootUrl = "";
		dotGraph = new HashMap<String,HashSet<String>>();
		dotGraphNodes = new HashSet<DotNode>();
	}
	
	/* Sets the root URL of the graph */
	public void setRootUrl(String url) {
		rootUrl = url;
	}
	
	/* */
	public boolean addNode(String url, NodeType nodeType) {
		dotGraphNodes.add(new DotNode(url,nodeType));
		return true;
	}
	
	/* */
	public boolean addNode(String url, String pageTitle, NodeType nodeType) {
		dotGraphNodes.add(new DotNode(url,pageTitle,nodeType));
		return true;
	}
	
	/* Adds a directed relation to the DOT file. Returns true
	 * for success and false for failure. */
	public boolean addRelation(String url1, String url2) {
		HashSet<String> links = dotGraph.get(url1);
		
		if (links == null)
			links = new HashSet<String>();
		links.add(url2);
		
		dotGraph.put(url1, links);
		return true;
	}
	
	/* Returns a string of what the DOT file is for this graph */
	public String getDotFile() {
		StringBuilder dotFile = new StringBuilder();
		
		dotFile.append("digraph graphname {\r\n");
		
		for (DotNode dotNode : dotGraphNodes) {
			dotFile.append("\"" + dotNode.getUrl() + "\"" + getNodePropertiesByUrl(dotNode.getUrl()) + ";\r\n");
		}
		
		if ((dotGraph.keySet().size() == 0) && (!rootUrl.equals("")))
			dotFile.append("\"" + rootUrl + "\"" + getNodePropertiesByUrl(rootUrl) + ";\r\n");
		
		
		if (dotGraph.get(rootUrl) != null) {
			for (String url2 : dotGraph.get(rootUrl)) {
				dotFile.append("\"" + rootUrl + "\" -> \"" + url2 + "\";\r\n");
			}
		}
		
		for (String url1 : dotGraph.keySet()) {
			if (!url1.equals(rootUrl) && dotGraph.get(url1) != null) {
				for (String url2 : dotGraph.get(url1)) {
					dotFile.append("\"" + url1 + "\" -> \"" + url2 + "\";\r\n");
				}
			}
		}

		dotFile.append("}");
		
		return dotFile.toString();
	}
	
	/* Outputs this graph to dotFile */
	public void outputGraph(String dotFile, String rootUrl) {
		if (dotFile != null && !dotFile.equals("")) {
			// Create DOT file
			addNode(rootUrl,NodeType.LINK);
			setRootUrl(rootUrl);
			
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(dotFile));
				if (dotGraph != null)
					out.write(getDotFile());
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Output Graphviz-based JPEG image from DOT file
			try {
				 Runtime rt = Runtime.getRuntime() ;
				 Process proc = rt.exec("dot " + dotFile + " -Tjpg -O");
				 //proc.destroy();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private String getNodePropertiesByUrl(String url) {
		String props = " [shape=box] [label=\"" + addNewLineChars(getPageTitleFromUrl(url)) + "\"]";
		return props;
	}
	
	private String getPageTitleFromUrl(String url) {
		for (DotNode dotNode : dotGraphNodes) {
			if (url.equals(dotNode.getUrl())) {
				return dotNode.getPageTitle();
			}
		}
		return url;
	}
	
	/* Adds a newline character every n characters for graph readability */
	private static String addNewLineChars(String str) {
		int n = 20;
		if (str.length() < n)
			return str;
			
		String ret = "";
		int start = 0;
		for (int i = n; i < str.length(); i += n) {
			ret += str.substring(start,i) + "&#10;";
			start = i;
		}
		ret += str.substring(start,str.length());
		
		// DOT doesn't like labels that ended in a newline character
		if ((ret.length() > 0) && ret.charAt(ret.length() - 1) == '\n' )
			ret = ret.substring(0,ret.length() - 1);
		
		return ret;
	}
	
	/* Inner class used to store directed graph node information */
	private class DotNode {
		
		private String URL;
		private String pageTitle;
		private NodeType nodeType;
		
		public DotNode() {
			URL = "";
			nodeType = NodeType.LINK;
		}
		
		public DotNode(String URL, NodeType nodeType) {
			this.URL = URL;
			this.pageTitle = "";
			this.nodeType = nodeType;
		}
		
		public DotNode(String URL, String pageTitle, NodeType nodeType) {
			this.URL = URL;
			this.pageTitle = pageTitle;
			this.nodeType = nodeType;
		}
		
		public boolean equals(DotNode node) {
			if (this == node) return true;
			if (this.URL.equals(node.getUrl()))
				return true;
			else
				return false;
		}
		
		public String getUrl() {
			return URL;
		}
		
		public String getPageTitle() {
			if ((pageTitle == null) || (pageTitle.equals("")))
				return URL;
			else
				return pageTitle;
		}
		
	}
	
}