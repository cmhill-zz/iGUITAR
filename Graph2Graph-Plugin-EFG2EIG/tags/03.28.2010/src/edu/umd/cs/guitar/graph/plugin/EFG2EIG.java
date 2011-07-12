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
package edu.umd.cs.guitar.graph.plugin;

import java.awt.Event;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.EdgeMappingListType;
import edu.umd.cs.guitar.model.data.EdgeMappingType;
import edu.umd.cs.guitar.model.data.EventGraphType;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.model.data.EventsType;
import edu.umd.cs.guitar.model.data.InitialMappingListType;
import edu.umd.cs.guitar.model.data.InitialMappingType;
import edu.umd.cs.guitar.model.data.MappingType;
import edu.umd.cs.guitar.model.data.PathType;
import edu.umd.cs.guitar.model.data.RowType;
import edu.umd.cs.guitar.model.wrapper.EventWrapper;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao N. Nguyen </a>
 * 
 */
public class EFG2EIG extends G2GPlugin {

	/**
	 * Map of <event, all events used to reveal it> Note that we don't store all
	 * predecessor events here
	 */
	Hashtable<EventType, Vector<EventType>> preds;

	/**
	 * Map of <event, all successor events>
	 */
	Hashtable<EventType, Vector<EventType>> succs;

	List<EventType> initialEvents = null;

	/**
	 * @param inputGraph
	 */
	public EFG2EIG(EFG inputGraph) {
		super(inputGraph);
	}

	/**
	 * Get follow relations in the input graph
	 */
	private void parseFollowRelations() {

		if (inputGraph == null)
			return;

		List<EventType> eventList = inputGraph.getEvents().getEvent();
		int eventGraphSize = eventList.size();
		EventGraphType eventGraph = inputGraph.getEventGraph();

		succs = new Hashtable<EventType, Vector<EventType>>();
		preds = new Hashtable<EventType, Vector<EventType>>();

		for (int row = 0; row < eventGraphSize; row++) {

			EventType currentEvent = eventList.get(row);
			// String currentEventType = currentEvent.getType();

			Vector<EventType> s = new Vector<EventType>();

			for (int col = 0; col < eventGraphSize; col++) {

				// EventType other = eventList.get(col);
				// String eventType = other .getType();

				int relation = eventGraph.getRow().get(row).getE().get(col);
				// int relation = eventGraph.getRow().get(col).getE().get(row);

				// Other is followed by current event: current -> other
				if (relation != GUITARConstants.NO_EDGE) {

					EventType otherEvent = eventList.get(col);
					s.add(otherEvent);

					if (relation == GUITARConstants.REACHING_EDGE
							&& !otherEvent.getEventId().equals(
									currentEvent.getEventId())) {

						// Create preds list
						Vector<EventType> p = preds.get(otherEvent);
						if (p == null) {
							p = new Vector<EventType>();
						}
						p.add(currentEvent);
						preds.put(otherEvent, p);
					}
				}
				succs.put(currentEvent, s);
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.graph.plugin.G2GPlugin#parseGraph(edu.umd.cs.guitar
	 * .model.data.EFG)
	 */
	@Override
	public void parseGraph() {
		// initialize
		parseFollowRelations();
		parseInitialEvents();

		outputGraph = factory.createEFG();
		map = factory.createMapping();

		EventsType eventList = factory.createEventsType();
		EventGraphType eventGraph = factory.createEventGraphType();

		// select interaction events
		List<EventType> lEventList = getEventList();

		// set initial events
		InitialMappingListType lInitialMappingList = factory
				.createInitialMappingListType();

		for (EventType event : lEventList) {
			LinkedList<EventType> pathToRoot = pathToRoot(event);

			if (pathToRoot != null) {
				pathToRoot.removeLast();
				if (pathToRoot.size() > 0) {

					event.setInitial(true);
					InitialMappingType initialMapping = factory
							.createInitialMappingType();

					initialMapping.setEventId(event.getEventId());

					PathType mappingPathToRoot = factory.createPathType();
					for (EventType eventToRoot : pathToRoot)
						mappingPathToRoot.getEventId().add(
								eventToRoot.getEventId());

					initialMapping.setPath(mappingPathToRoot);
					lInitialMappingList.getIntialMapping().add(initialMapping);
				}
			}
		}

		map.setInitialMappingList(lInitialMappingList);

		eventList.setEvent(lEventList);
		outputGraph.setEvents(eventList);

		// mapping edge
		EdgeMappingListType edgeMappingList = factory
				.createEdgeMappingListType();

		// build edge
		List<RowType> lRowList = new ArrayList<RowType>();
		for (EventType firstEvent : lEventList) {
			int indexFirst = lEventList.indexOf(firstEvent);
			// System.out.println("Anlyzing row: " + indexFirst);
			RowType row = factory.createRowType();

			for (EventType secondEvent : lEventList) {
				int indexSecond = lEventList.indexOf(secondEvent);

				List<EventType> path = getInteractionFreePath(firstEvent,
						secondEvent);
				int cellValue;

				if (path != null) {

					cellValue = 1;

					if (path.size() > 0) {
						EdgeMappingType edgeMapping = factory
								.createEdgeMappingType();
						edgeMapping.setSourceId(firstEvent.getEventId());
						edgeMapping.setTargetId(secondEvent.getEventId());

						PathType eventPath = factory.createPathType();

						for (EventType event : path)
							eventPath.getEventId().add(event.getEventId());

						edgeMapping.setPath(eventPath);
						edgeMappingList.getEdgeMapping().add(edgeMapping);
					}

				} else {
					cellValue = 0;
				}

				row.getE().add(indexSecond, cellValue);
			}

			lRowList.add(indexFirst, row);
		}

		eventGraph.setRow(lRowList);
		outputGraph.setEventGraph(eventGraph);

		map.setEdgeMappingList(edgeMappingList);

		// debug
		System.out.println("===========================");
		System.out.println("OUTPUT GRAPH");
		printGraph(outputGraph);
	}

	/**
	 * Get event list of interest. For example System interaction and Terminal
	 * events in EIG
	 * 
	 * @return
	 */
	List<EventType> getEventList() {

		List<EventType> lOutputEvents = new ArrayList<EventType>();

		if (this.inputGraph == null)
			return lOutputEvents;
		List<EventType> lInputEvents = inputGraph.getEvents().getEvent();

		for (EventType event : lInputEvents) {
			if (GUITARConstants.SYSTEM_INTERACTION.equals(event.getType())
					|| GUITARConstants.TERMINAL.equals(event.getType()))
				lOutputEvents.add(event);
		}

		return lOutputEvents;
	}

	/**
	 * Get interaction-free path between too events
	 * 
	 * <p>
	 * 
	 * @param firstEvent
	 * @param secondEvent
	 * @return the path between two events null if there is no path
	 */

	private LinkedList<EventType> getInteractionFreePath(EventType firstEvent,
			EventType secondEvent) {
		path = new LinkedList<EventType>();
		traversed = new LinkedList<EventType>();
		if (dfsFindPath(firstEvent, secondEvent))
			return path;
		else
			return null;
	}

	/**
	 * Global variable used to find path between events
	 */
	private LinkedList<EventType> traversed;
	private LinkedList<EventType> path;

	/**
	 * Helper method to DFS to find path between two events
	 * 
	 * <p>
	 * 
	 * @param firstEvent
	 * @param secondEvent
	 * @return
	 */
	private boolean dfsFindPath(EventType firstEvent, EventType secondEvent) {

		if (succs.get(firstEvent).contains(secondEvent)
		// || firstEvent.equals(secondEvent)
		) {
			return true;
		}

		List<EventType> neighbors = preds.get(secondEvent);

		if (neighbors != null)
			for (EventType neighbor : neighbors) {
				if (!traversed.contains(neighbor) && !path.contains(neighbor)) {
					path.push(neighbor);
					boolean found = dfsFindPath(firstEvent, neighbor);
					if (found)
						return true;
					path.remove(neighbor);
				}
			}
		return false;

	}

	/**
	 * Get initial events
	 */
	private void parseInitialEvents() {

		initialEvents = new ArrayList<EventType>();

		if (inputGraph == null)
			return;

		List<EventType> eventList = inputGraph.getEvents().getEvent();
		for (EventType event : eventList) {
			if (event.isInitial() && preds.get(event) == null) {
				initialEvents.add(event);
			}
		}
	}

	/**
	 * 
	 * Find path to root
	 * 
	 * @param event
	 * @return
	 */
	private LinkedList<EventType> pathToRoot(EventType event) {
		if (initialEvents.contains(event)) {
			LinkedList<EventType> path = new LinkedList<EventType>();
			path.add(event);
			return path;
		} else {
			Vector<EventType> predEventList = preds.get(event);
			if (predEventList == null)
				return null;
			else if (predEventList.size() == 0) {
				System.out.println(event.getEventId()
						+ " has empty predEventList");
				return null;
			} else {
				for (EventType pred : predEventList) {
					// System.out.println("------------------------");
					// System.out.println("EventID: "+pred.getEventId());
					// System.out.println("PredID: "+pred.getEventId());
					//					
					LinkedList<EventType> predPathToRoot = pathToRoot(pred);
					if (predPathToRoot == null) {
						continue;
					} else if (!predPathToRoot.contains(event)) {
						predPathToRoot.add(event);
						return predPathToRoot;
					}
				}
				return null;
			}
		}
	}

	private void printGraph(EFG graph) {
		StringBuffer buff = new StringBuffer();

		List<EventType> eventList = graph.getEvents().getEvent();
		List<RowType> lRow = graph.getEventGraph().getRow();

		for (int row = 0; row < lRow.size(); row++) {
			List<Integer> rowVals = lRow.get(row).getE();

			for (int col = 0; col < rowVals.size(); col++) {

				int cell = rowVals.get(col);
				if (cell > 0) {
					EventType firstEvent = eventList.get((row));
					EventType secondEvent = eventList.get(col);

					buff.append("\t" + "\"" + firstEvent.getEventId() + "\""
							+ "->" + "\"" + secondEvent.getEventId() + "\"");
					buff.append("\t/*" + cell + "*/");

					buff.append("\n");
				}
			}
		}

		System.out.println(buff);
	}
}
