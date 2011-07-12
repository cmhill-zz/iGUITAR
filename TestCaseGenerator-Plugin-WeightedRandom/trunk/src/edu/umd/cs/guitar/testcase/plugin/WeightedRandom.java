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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Vector;
import java.util.Random;
import java.util.Hashtable;

import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.EventGraphType;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.model.data.EventsType;
import edu.umd.cs.guitar.model.data.RowType;
import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;
import edu.umd.cs.guitar.testcase.TestcaseGenerator;
import edu.umd.cs.guitar.util.GUITARLog;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * Plugin to create shortest paths to events, and random paths with probabilities set by a weighted graph file.
 */
public class WeightedRandom extends TCPlugin {

	private EventGraphType weights;
	
	// Used to correlate event IDs to a given row of weights
	private Hashtable<EventType, List<Integer>> eventWeights;

	int nMaxNumber;

	/**
     * 
     */
	public WeightedRandom() {

	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.testcase.plugin.TCPlugin#getConfiguration()
	 */
	@Override
	public TestCaseGeneratorConfiguration getConfiguration() {
		WeightedRandomConfiguration configuration = new WeightedRandomConfiguration();
		return configuration;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.testcase.plugin.TCPlugin#isValidArgs()
	 */
	@Override
	public boolean isValidArgs() {
		if (WeightedRandomConfiguration.LENGTH == null)
			return false;

		if (WeightedRandomConfiguration.WEIGHTS.equals(""))
			return false;
		
		EFG weightEFG = (EFG) IO.readObjFromFile(WeightedRandomConfiguration.WEIGHTS, EFG.class);
		if (weightEFG == null)
			return false;

		weights = weightEFG.getEventGraph();
		EFG efg = (EFG) IO.readObjFromFile(WeightedRandomConfiguration.EFG_FILE, EFG.class);
		return verifyWeights(efg);
		// check that any plugin-specific arguments are not NULL
	}

	/*
	 * Check the provided weighted graph is valid for the provided EFG:<br/>
	 * - Possesses the same number of events.<br/>
	 * - Possesses no negative weights.<br/>
	 * - If an edge in the EFG graph has value NO_EDGE, the corresponding weight is zero.
	 */
	private boolean verifyWeights(EFG efg) {
		if (efg == null || weights == null)
			return false;

		if (efg.getEventGraph() == null)
			return false;
		
		List<RowType> efgRows = efg.getEventGraph().getRow();
		List<RowType> weightRows = weights.getRow();
		
		if (efgRows == null || weightRows == null)
			return false;
		if (efgRows.size() != weightRows.size())
			return false;
		
		for (int i = 0; i < efgRows.size(); i++) {
			List<Integer> efgRow = efgRows.get(i).getE();
			List<Integer> weightRow = weightRows.get(i).getE();
			if (efgRow.size() != weightRow.size())
				return false;
			
			for (int j = 0; j < efgRow.size(); j++) {
				Integer weightInt = weightRow.get(j);
				if (weightInt < 0)
					return false;

				Integer efgInt = efgRow.get(j);
				if (efgInt == GUITARConstants.NO_EDGE && weightInt != 0) {
					return false;
				}
			}
		}
		
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.testcase.plugin.TCPlugin#generate()
	 */
	@Override
	public void generate(EFG efg, String outputDir, int nMaxNumber) {

		new File(outputDir).mkdir();

		this.efg = efg;
		this.nMaxNumber = nMaxNumber;

		initialize();		
		
		eventWeights = new Hashtable<EventType, List<Integer>>();
		List<EventType> events = efg.getEvents().getEvent();
		for (int i = 0; i < events.size(); i++) {
			EventType event = events.get(i);
			eventWeights.put(event, weights.getRow().get(i).getE());
		}

		// Insert generate code
		int index = generateShortestPaths(eventWeights, outputDir);
		generateRandomPaths(index, WeightedRandomConfiguration.LENGTH);

	}
	
	
	
	/* Private class used for implementing Djikstra's shortest path algorithm */
	private class DjikstraEvent implements Comparable<DjikstraEvent>{
		private EventType event;
		private int length;
		
		public DjikstraEvent(EventType event, int length) {
			this.event = event;
			this.length = length;
		}
		
		public int getLength() {
			return length;
		}
		
		public EventType getEvent() {
			return event;
		}
		
		public int compareTo(DjikstraEvent other) {
			if(getLength() < other.getLength()) {
				return -1;
			} else if(getLength() > other.getLength()) {
				return 1;
			} else {
				return getEvent().getEventId().compareTo(
						other.getEvent().getEventId());
			}
		}
	}
	
	/*
	 * Returns the shortest path to each reachable node based on the given weights file.
	 *
	 * @return	the number of paths generated
	 */
	private int generateShortestPaths(Hashtable<EventType, List<Integer>> eventWeights, String outputDir) {
		
		/* Start by mapping all events to their respective index in the efg matrix */
		HashMap<EventType,Integer> eventIndices = new HashMap<EventType,Integer>();
		List<EventType> events = efg.getEvents().getEvent();
		for (int i = 0; i < events.size(); i++) {
			EventType event = events.get(i);
			eventIndices.put(event, new Integer(i));
		}
		
		/* map each destination to its shortestPath 
		 * (from whichever initial event gives its shortestPath 
		 */
		HashMap<EventType, ArrayList<Path>> shortestPaths = 
			new HashMap<EventType, ArrayList<Path>>();
		
		for(EventType destination: eventWeights.keySet()) {
			
			ArrayList<Path> allPaths = new ArrayList<Path>();
			
			/* Use Djikstra's to find shortest path from this initial to each possible destination */
			for(EventType rootEvent: initialEvents) {
				
				HashMap<EventType, Integer> settledEvents = 
					new HashMap<EventType, Integer>();
				PriorityQueue<DjikstraEvent> unsettledEvents = 
					new PriorityQueue<DjikstraEvent>();
				
				/* the predecessor of each vertex ON THE SHORTEST PATH from the source */
				HashMap<EventType, EventType> previousEvent = 
					new HashMap<EventType, EventType>();
				
				/* current shortest distance from root to specified event */
				HashMap<EventType, Integer> shortestDistanceFound = 
					new HashMap<EventType, Integer>();
				
				/* if a path has been found */
				boolean pathFound = false;

				/* initialize the shortest distances for each MAX_VALUE to be infinity
				 * 
				 * TWO (2) PROBLEMS WITH THIS:
				 * 
				 * 1. Integer.MAX_VALUE is not the same as 
				 * 		Double.POSITIVE_INFINITY
				 * 2. This causes to Djikstra's to reset 
				 * 		the shortestDistance for each event
				 * 		everytime this (inner) for loop loops. Ideally, 
				 * 		shortestDistanceFound shouldn't be 
				 * 		changed until a new initialEvent is analyzed (outer for loop)
				 */
				for (EventType event: eventWeights.keySet()) {
					shortestDistanceFound.put(event, Integer.MAX_VALUE);
				}
				
				unsettledEvents.add(new DjikstraEvent(rootEvent,0));
				shortestDistanceFound.put(rootEvent, 0);
				previousEvent.put(rootEvent,null);
				
				
				while (!unsettledEvents.isEmpty()) {
					/*
					 * remove the event with the smallest weight from the root event
					 * from priority queue of unsettled events
					 */
					DjikstraEvent djikstraEventToSettle = unsettledEvents.poll();
					EventType eventToSettle = djikstraEventToSettle.getEvent();
					


					/* check to see if path to end has been found */
					if (eventToSettle.equals(destination)) {
						/* path has been found, break out of loop */
						pathFound = true;
						break;
					}

					if (!settledEvents.containsKey(eventToSettle)) {
						/* add city to map of settled cities */
						settledEvents.put(eventToSettle, djikstraEventToSettle.getLength());

						/*
						 * relax the neighbors of the event to be settled by looking at
						 * the events connected to it
						 * 
						 * NEED TO VERIFY THAT "succs" WILL GIVE ME ALL ADJACENT EVENTS
						 * (I think so)
						 */
						for (EventType adjacentEvent : succs.get(eventToSettle) ) {
							
							if (!settledEvents.containsKey(adjacentEvent)) {
								/*
								 * if the adjacent event has not been settled, get its
								 * distance to the root event (initial)
								 */
								int adjacentEventDistance = shortestDistanceFound.get(adjacentEvent);
								
								/* this looks confusing but it's just finding the weight between
								 * eventToSettle and adjacentEvent
								 */
								List<Integer> adjacentWeights = eventWeights.get(eventToSettle);
								int index = eventIndices.get(adjacentEvent);
								int distanceViaEventToSettle = shortestDistanceFound.get(eventToSettle)
										+ adjacentWeights.get(index);
								// Treat a weighted edge of 0 as infinite distance.
								if (adjacentWeights.get(index) == 0)
									distanceViaEventToSettle = Integer.MAX_VALUE;

								/* compare distances from initial event*/
								if (adjacentEventDistance > distanceViaEventToSettle) {
									/*
									 * if this new distance is smaller, update the
									 * shortest distance found for the adjacent event
									 */
									shortestDistanceFound.put(adjacentEvent, distanceViaEventToSettle);

									/* update the other data structures */
									previousEvent.put(adjacentEvent, eventToSettle);
									unsettledEvents.offer(new DjikstraEvent(
											adjacentEvent, distanceViaEventToSettle));
								}
							}
						}
					}
				}
				
				if(pathFound) {
					Path path = new Path(shortestDistanceFound.get(destination));
					
					EventType curr = destination;
					while(curr != null) {
						path.addEvent(curr);
						curr = previousEvent.get(curr);
					}
					
					allPaths.add(path);
				}
				else {
					/* Do nothing */
				}
			}
			
			/* map all of these shortestPath to its destination */
			if(!allPaths.isEmpty()) {
				shortestPaths.put(destination, allPaths);
			}
		}
		
		int numTestCases = 0;
		
			for(EventType destination : shortestPaths.keySet()) {
				int length = Integer.MAX_VALUE;
				LinkedList<EventType> chosenPath = null;
				for(Path path : shortestPaths.get(destination)) {
					if(path.getLength() < length){ /* 'ties' in path length don't matter */
						chosenPath = path.getPathList();
						length = path.getLength();
					}
				}

				if(chosenPath != null) {
					numTestCases++;
					String sTestName = TEST_NAME_PREFIX + "_shortestPath_" + destination.getEventId() + TEST_NAME_SUFIX;
					writeToFile(outputDir + File.separator + sTestName, chosenPath);
				}
				
				if(WeightedRandomConfiguration.MAX_NUMBER != 0 
						&& numTestCases == 
							WeightedRandomConfiguration.MAX_NUMBER) {
					break;		/* ewww */
				}
			}


		return numTestCases;
	}
	
	private class Path{
		private LinkedList<EventType> pathList;
		private int length;
		
		public Path(int length) {
			pathList = new LinkedList<EventType>();
			this.length = length;
		}
		
		public void addEvent(EventType e) {
			pathList.addFirst(e);
		}
		
		public LinkedList<EventType> getPathList() {
			return this.pathList;
		}
		
		public int getLength() {
			return this.length;
		}
	}
	
	/*
	 * Randomly traverses the EFG using the weights as probabilities.
	 *
	 * @ param index	the number of paths already generated 
	 *
	 * @ param length	the length of the path to be generated
	 */
	private void generateRandomPaths(int index, int length) {
		// Nothing to do.
		if (length <= 0)
			return;
		
		// Create an additional Hashtable of events to the sum of their weights.
		Hashtable<EventType, Integer> eventRanges = new Hashtable<EventType, Integer>();
		for (EventType event : efg.getEvents().getEvent()) {
			int range = 0;
			for (Integer i : eventWeights.get(event)) {
				range += i.intValue();
			}
			eventRanges.put(event, new Integer(range));
		}		
		
		Random rand = new Random();
		int initialRange = 0;
		for (EventType event : initialEvents) {
			initialRange += eventRanges.get(event).intValue();
		}
		
		int i = 0;
		while (index + i < nMaxNumber) {
			LinkedList<EventType> path = new LinkedList<EventType>();
			EventType current = initialEvents.get(0);
			
			// Weight chance of picking a given initial event by whichever event has highest sum of outward edges.
			if (initialRange == 0) {
				// No outward edges from any initial events. Add the first initial event to the path.
				// Ensuing for loop won't do anything as a result.
				path.add(current);
				initialRange = 1;
			}
			int choice = rand.nextInt(initialRange);
			for (EventType event : initialEvents) {
				choice -= eventRanges.get(event).intValue();
				if (choice < 0) {
					current = event;
					path.add(event);
					break;
				}
			}
			
			int j = 1;
			while (j < length) {
				List<Integer> currentWeights = eventWeights.get(current);
				
				if (eventRanges.get(current).intValue() == 0) {
					// No outward edges. Dead end.
					break;
				}
				
				choice = rand.nextInt(eventRanges.get(current).intValue());
				for (int k = 0; k < currentWeights.size(); k++) {
					choice -= currentWeights.get(k).intValue();
					if (choice < 0) {
						current = efg.getEvents().getEvent().get(k);
						path.add(current);
						break;
					}
				}
				
				j++;
			}
			
			String sTestName = TEST_NAME_PREFIX + "_random" + i + TEST_NAME_SUFIX;
			writeToFile(TestCaseGeneratorConfiguration.OUTPUT_DIR
					+ File.separator + sTestName, path);
			i++;
		}
	}

}
