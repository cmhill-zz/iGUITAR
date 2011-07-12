package edu.umd.cs.guitar.testcase.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;

/**
 * This plugin will generate testcases which will go from each node to
 * every other possible node.
 * @author Jason
 *
 */
public class NodeToAllOtherNodePlugin extends TCPlugin {
	
	/**
	 * Generates testscases from the given efg and writes it to the
	 * given outputDir. If the number of testcases exceeds nMaxNumber,
	 * the method will terminate.
	 * 
	 * @param efg
	 * @param outputDir
	 * @param nMaxNumber
	 */
	public void generate(EFG efg, String outputDir, int nMaxNumber) {
		// checking the given parameters
		boolean allTypes = NodeToAllOtherNodeConfiguration.TYPES.trim().length() == 0;
		List<String> types = Arrays.asList(NodeToAllOtherNodeConfiguration.TYPES.trim().split(","));
		boolean allWidgets = NodeToAllOtherNodeConfiguration.WIDGETS.trim().length() == 0;
		List<String> widgets = Arrays.asList(NodeToAllOtherNodeConfiguration.WIDGETS.trim().split(","));
		
		// create the output directory
		File output = new File(outputDir);
		output.mkdir();
		
		// set the efg, initialize it, and get all the events
		this.efg = efg;
		initialize();
		List<EventType> eventList = efg.getEvents().getEvent();
		
		// process the events and create the testcases
		int numTestCases = 0;
		for(EventType start : eventList) {
			// check to see if you have enough testcases
			if(nMaxNumber > 0 && numTestCases >= nMaxNumber) {
				break;
			}
			
			// checking the event type and event widget
			if(!(allTypes || types.contains(start.getType()))) {
				continue;
			}
			if(!(allWidgets || widgets.contains(start.getAction()))) {
				continue;
			}
			
			// generating the sequence of events from root to start
			LinkedList<EventType> rootToStart = pathToRoot(start);
			
			// could not reach an initial event
			if(rootToStart == null) {
				continue;
			}
			
			// trying to reach all the other nodes
			for(EventType end : eventList) {
				// check to see if you have enough testcases
				if(nMaxNumber > 0 && numTestCases >= nMaxNumber) {
					break;
				}
				
				// checking the event type and event widget
				if(!(allTypes || types.contains(end.getType()))) {
					continue;
				}
				if(!(allWidgets || widgets.contains(end.getAction()))) {
					continue;
				}
				
				// building the sequence of nodes
				LinkedList<EventType> startToEnd = null;
				
				// search for a path from start to end
				Stack<EventPath> queue = new Stack<EventPath>();
				queue.add(new EventPath(start, new ArrayList<EventType>()));
				do {
					EventPath eventPath = queue.pop();
					EventType event = eventPath.event;
					ArrayList<EventType> path = eventPath.path;
					path.add(event);
					// found the end event
					if(event.equals(end)) {
						path.remove(0);
						startToEnd = new LinkedList<EventType>();
						startToEnd.addAll(rootToStart);
						startToEnd.addAll(path);
						break;
					}
					// depth first search
					for(EventType next : succs.get(event)) {
						// checking the event type and event widget
						if(!(allTypes || types.contains(next.getType()))) {
							continue;
						}
						if(!(allWidgets || widgets.contains(next.getAction()))) {
							continue;
						}
						// checking for cycles
						if(path.contains(next)) {
							continue;
						}
						queue.push(new EventPath(next, path));
					}
				} while(!queue.isEmpty());
				
				// write the testcase and increase the generated testcase count
				if(startToEnd != null) {
					writeToFile(outputDir + File.separator + TEST_NAME_PREFIX + 
							start.getEventId() + "-" + end.getEventId() + 
							TEST_NAME_SUFIX, startToEnd);
					++numTestCases;
				}
			}
		}
	}
	
	public NodeToAllOtherNodeConfiguration getConfiguration() {
		NodeToAllOtherNodeConfiguration configuration = new NodeToAllOtherNodeConfiguration();
		return configuration;
	}
	
	/**
	 * Returns if the given arguments were valid. Tests to see that the
	 * max number of test cases is valid. 
	 * 
	 * @return
	 */
	public boolean isValidArgs() {
		if(TestCaseGeneratorConfiguration.MAX_NUMBER < 0) {
			return false;
		}
		if(NodeToAllOtherNodeConfiguration.TYPES == null) {
			return false;
		}
		if(NodeToAllOtherNodeConfiguration.WIDGETS == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * Private class used to hold the current event and a path to it.
	 * @author Jason
	 *
	 */
	private class EventPath {
		EventType event;
		ArrayList<EventType> path;
		EventPath(EventType event, ArrayList<EventType> path) {
			this.event = event;
			this.path = path;
		}
	}
	
}
