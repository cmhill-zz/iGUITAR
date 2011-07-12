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
package edu.umd.cs.guitar.testcase.plugin;

import java.io.File;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.EventGraphType;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * Plugin to cover a certain length path in the EFG
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class OpeningSequenceCoverage extends TCPlugin {

	int nMaxNumber;
	private Hashtable<EventType, Boolean> visited;
	private List<LinkedList<EventType>> paths;

	/**
     * 
     */
	public OpeningSequenceCoverage() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.testcase.plugin.TCPlugin#isValidArgs()
	 */
	/**
	 *Checks to see if the arguements for the test case generator are valid for this plugin
	 *
	 *@return	returns true if arguements are valid and false is there was no valid arguement passed
	 */
	@Override
	public boolean isValidArgs() {
		if (TestCaseGeneratorConfiguration.LENGTH == null)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.testcase.plugin.TCPlugin#generate()
	 */
	/**
	 *Creates at most nMaxNumber tests for the efg to the outputDir.
	 *Traverses each node's children adding new lists for each one for at most
	 *LENGTH iterations, then performs a depth first search to add all unvisited nodes.
	 *
	 *@param	efg			The graph to find tests in
	 *@param	outputDir	The directory to send the tests to
	 *@param	nMaxNumber	The most tests to generate
	 *@return	writes test files to outpudDir
	 */
	@Override
	public void generate(EFG efg, String outputDir, int nMaxNumber) {

		new File(outputDir).mkdir();

		this.efg = efg;
		this.nMaxNumber = nMaxNumber;

		initialize();
		List<EventType> eventList = efg.getEvents().getEvent();
		
		visited = new Hashtable<EventType, Boolean>();
		for(EventType e : eventList)
			visited.put(e, Boolean.FALSE);
		
		paths = new ArrayList<LinkedList<EventType>>();
		
		for (EventType initialEvent : initialEvents) {
			LinkedList<EventType> initialList = new LinkedList<EventType>();
			initialList.add(initialEvent);
			visited.put(initialEvent, Boolean.TRUE);
			paths.add(initialList);
		}
		
		List<LinkedList<EventType>> pathsToAdd;
		for(int i = 1; i < TestCaseGeneratorConfiguration.LENGTH; i++) {

			int numAdded = 0;
			pathsToAdd = new ArrayList<LinkedList<EventType>>();
			
			for (LinkedList<EventType> path : paths) {
				
				EventType last = path.getLast();
				List<EventType> successors = succs.get(last);
				if(successors.size() == 0)
					pathsToAdd.add(path);
				else {
					numAdded--;
					for (EventType e : successors) {
						if(paths.size() + numAdded < nMaxNumber || nMaxNumber == 0) {
							numAdded++;
							LinkedList<EventType> list = new LinkedList<EventType>();
							list.addAll(path);
							list.add(e);
							pathsToAdd.add(list);
							visited.put(e, Boolean.TRUE);
						}
					}
				}
			}
			
			paths = pathsToAdd;
		}
		
		do {
			pathsToAdd = new ArrayList<LinkedList<EventType>>();
			pathsToAdd.addAll(paths);
			for(LinkedList<EventType> path : paths) {
					for (EventType e : succs.get(path.getLast())) {
						if((pathsToAdd.size() < nMaxNumber || nMaxNumber == 0) && !visited.get(e)) {
							pathsToAdd.remove(path);
							visited.put(e, Boolean.TRUE);
							LinkedList<EventType> newPath = new LinkedList<EventType>();
							newPath.addAll(path);
							newPath.add(e);
							pathsToAdd.add(newPath);
						}
					}
			}
		} while (pathsToAdd.size() > paths.size());
		
		for(int i = 0; i < paths.size(); i++){
			LinkedList<EventType> path = paths.get(i);
			if (path != null) {

				String sTestName = TEST_NAME_PREFIX;

				if (TestCaseGeneratorConfiguration.LENGTH < 30) {
					for (EventType event : path) {
						sTestName += ("_" + event.getEventId());
					}
				} else {
					sTestName += ("_" + i);
				}
				sTestName += TEST_NAME_SUFIX;
				writeToFile(TestCaseGeneratorConfiguration.OUTPUT_DIR + File.separator + sTestName, path);
			}
		}
	}
}
