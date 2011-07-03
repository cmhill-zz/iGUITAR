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
package edu.umd.cs.guitar.graph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

import edu.umd.cs.guitar.graph.plugin.GraphConverter;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * Entry point to convert GUI structure to different kind of graphs
 * 
 * @author <a href="mailto:charlie.biger@gmail.com"> Charlie BIGER </a>
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao N. Nguyen </a>
 * 
 * @version 1.0
 */
public class GUIStructure2GraphConverter {
	// These parameters are only used for debuggin
	// do not visible to the users
	static String GRAPHVIZ_FILE = "";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		setupLog();

		try {
			if (args.length < 2) {
				String help = "";
				System.out.println(help);
			} else {

				// TODO: Make a configuration for parameters
				String PLUGIN = args[0];
				String INPUT_FILE = args[1];
				String OUTPUT_FILE = args[2];

				GUITARLog.log
						.info("===========================================");
				GUITARLog.log.info("GUIStructure2GraphConverter");
				GUITARLog.log.info("");
				GUITARLog.log.info("\tPlugin: \t" + PLUGIN);
				GUITARLog.log.info("\tInput file: \t" + INPUT_FILE);
				GUITARLog.log.info("\tOutput file: \t" + OUTPUT_FILE);

				GUITARLog.log
						.info("===========================================");

				String converterFullName = GraphConverter.class.getPackage()
						.getName()
						+ "." + PLUGIN;

				Class<?> converterClass = Class.forName(converterFullName);

				GraphConverter converter = (GraphConverter) converterClass
						.newInstance();

				Object obj = IO.readObjFromFile(INPUT_FILE, converter
						.getInputType());

				Object graph = converter.generate(obj);
				IO.writeObjToFile(graph, OUTPUT_FILE);

				// Debug only
				// Print out graphviz file
				if (args.length > 3) {
					GRAPHVIZ_FILE = args[3];
					// StringBuffer graphviz = ((EFGConverter) converter)
					// .printGraphviz();
					// graphviz.insert(0, "digraph " + GRAPHVIZ_FILE);
					// if (converter instanceof EFGConverter) {
					//
					// FileWriter fstream = new FileWriter(GRAPHVIZ_FILE);
					// BufferedWriter out = new BufferedWriter(fstream);
					// out.write(graphviz.toString());
					// System.out.println(graphviz);
					// out.close();
					// }
				}

				// GUITARLog.log.info("DONE");

			}
		} catch (ClassNotFoundException e) {
			System.out
					.println("The converter can not be found. Please make ensure that the converter name is correct and the corresponding .jar file can be reached.");

		} catch (InstantiationException e) {
			System.out
					.println("The converter is defined as an Abstract class, or an interface, or its constructor is not accessible without parameters.");
			GUITARLog.log.info("Please Report this bug");

		} catch (IllegalAccessException e) {
			GUITARLog.log.info("The converter is not accessible");

			// } catch (FileNotFoundException e) {
			// GUITARLog.log.info("Input File name is not correct ");

		} catch (Exception e) {
			e.printStackTrace();
			GUITARLog.log.info("Unknown ERROR");
		}
	}

	/**
     * 
     */
	private static void setupLog() {
			System.setProperty(GUITARLog.LOGFILE_NAME_SYSTEM_PROPERTY,
					"GUIStructure2GraphConverter.log");

			// GUITARLog.log =
			// Logger.getLogger(GUIStructure2GraphConverter.class
			// .getSimpleName());
			//
			// final File logFile = new File("GUIStructure2GraphConverter.log");
			// final String LOG_PATTERN = "%m%n";
			// final PatternLayout pl = new PatternLayout(LOG_PATTERN);
			//
			// final FileAppender rfp = new RollingFileAppender(pl, logFile
			// .getCanonicalPath(), true);
			//
			// final ConsoleAppender cp = new ConsoleAppender(pl);
			//
			// GUITARLog.log.addAppender(rfp);
			// GUITARLog.log.addAppender(cp);

	}
}
