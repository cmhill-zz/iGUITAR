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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


import edu.umd.cs.guitar.model.wrapper.AttributesTypeWrapper;

/**
 * Constants specific to Iph GUITAR.
 * 
 * <p>
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class IphConstants {

	// ------------------------------
	// Ignored components
	// ------------------------------

	public static String RESOURCE_DIR = "resources";
	public static String CONFIG_DIR = RESOURCE_DIR + File.separator + "config";

	public static String TERMINAL_WIDGET_FILE = "terminal_widget.ign";


	public static List<AttributesTypeWrapper> sTerminalWidgetSignature = new LinkedList<AttributesTypeWrapper>();
	public static List<String> sIgnoredWins = new ArrayList<String>();

	
	/**
	 * List of interested GUI properties
	 */
	static List<String> GUI_PROPERTIES_LIST = Arrays.asList("opaque", "height",
			"width", "foreground", "background", "visible", "tooltip", "font",
			"accelerator", "enabled", "editable", "focusable", "selected",
			"text");

	static List<String> WINDOW_PROPERTIES_LIST = Arrays.asList("layout", "x",
			"y", "height", "width", "opaque", "visible", "alwaysOnTop",
			"defaultLookAndFeelDecorated", "font", "foreground", "insets",
			"resizable", "background", "colorModel", "iconImage", "locale");
	
	// Window Attributes
	
	public static final String WINDOW_TITLE = "Title";
	public static final String WINDOW_CLASS = "Class";
	public static final String WINDOW_ENABLED = "Enabled";
	public static final String WINDOW_VISIBLE = "Visible";
	public static final String WINDOW_MODAL = "Modal";
	public static final String WINDOW_ROOTWINDOW = "Rootwindow";
	public static final String WINDOW_WIDTH = "Width";
	public static final String WINDOW_HEIGHT = "Height";
	public static final String WINDOW_X = "X";
	public static final String WINDOW_Y = "Y";
	public static final String WINDOW_JPEG = "JPEG";
	/**
	 * List of properties used to identify a widget on the GUI
	 */
	public static List<String> ID_PROPERTIES = Arrays.asList("className", "x", "y");
//	public static List<String> ID_PROPERTIES = Arrays.asList("Title", "Class",
//			"Icon", "Index");
	//"className", "address"
	/**
	 * Iph specific tags
	 * 
	 */

	public static final String TITLE_TAG = "Title";
	public static final String ICON_TAG = "Icon";
	public static final String INDEX_TAG = "Index";

	// ------------------------------
	// GUITAR LOG
	public static final String LOG4J_PROPERTIES_FILE = // CONFIG_DIR
	// + File.separator +
	"log4j.properties"; // This name needs to be a command-line parameter

}
