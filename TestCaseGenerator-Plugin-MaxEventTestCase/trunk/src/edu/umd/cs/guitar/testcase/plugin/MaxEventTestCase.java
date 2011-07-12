/*    
 *  Copyright (c) 2009-@year@. The GUITAR group at the University of Maryland. Names of owners of this group may
 *  be obtained by sending an e-mail to atif@cs.umd.edu
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 *  documentation files (the "Software"), to deal in the Software without restriction, including without 
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *    the Software, and to permit persons to whom the Software is furnished to do so, subject to the following 
 *    conditions:
 * 
 *    The above copyright notice and this permission notice shall be included in all copies or substantial 
 *    portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 *    LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 *    EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 *    IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 *    THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package edu.umd.cs.guitar.testcase.plugin;

import java.io.File;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.PriorityQueue;

import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.model.data.RowType;
import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;
import edu.umd.cs.guitar.util.GUITARLog;

/* 
 *  Generate a set of MaxEvent test cases based on a series of constraints.
 *
 *  Extending TCPlugin gives abstract methods generate() and isValidArgs().  It also
 *  allows for gives the methods writeToFile() given a path of events and pathToRoot()
 *  given an event.
 */
public class MaxEventTestCase extends TCPlugin {
    
    /* Constraints */
    int number;
//    EFG efg;
    
    /* Default constructor. */
    public MaxEventTestCase()
    {  }
    
    /* 
     * Overridden generate method.  Generates test cases!
     * 
     * Algorithm:
     * 1)   
     */
    @Override
    public void generate(EFG efg, String outputDir, int number)
    {
        List<RowType> adjacencyMatrix = efg.getEventGraph().getRow();
        List<EventTypeWeight> allEvents = new ArrayList<EventTypeWeight>();
        
        new File(outputDir).mkdir();

        this.number=number;
        this.efg=efg;    
    
        initialize();
    
         PriorityQueue<EventTypeWeight> queue = new PriorityQueue<EventTypeWeight>(10, (Comparator<? super EventTypeWeight>) new EventComparator());

        /* Populating events map. */
        for (EventType i : efg.getEvents().getEvent()){
            EventTypeWeight e = new EventTypeWeight(i);
            queue.add(e);
            allEvents.add(e);
        }
        
        List<Integer> initialEvents = getInitialEvents();
        EventTypeWeight curr = null;
        /* Generates one test case per iteration.  Continues until every event has been
         * included in a test case and (should it be or?) the max number of test cases has been created. */
        for (int i = 1; i <= number; i++)
        {
        
        	Integer first = initialEvents.remove(0);
        	curr = allEvents.get(first.intValue());
			initialEvents.add(first);
			queue.remove(curr);
            
            /* List of events (in order) that the current testcase will traverse. */
            LinkedList<EventType> eventList = new LinkedList<EventType>();
            
            /* Fills the eventList according to the above algorithm. 
                Note: I don't know how to properly use EventList so take it with a grain of salt -Jon  */
     
            while (curr!=null)
            {
                /* Find the highest priority adjacent event */                 
                PriorityQueue<EventTypeWeight> temp = new PriorityQueue<EventTypeWeight>(10, new EventComparator());
//                List<Integer> allAdjacent = adjacencyMatrix.get(allEvents.indexOf(curr.getEventType().getEventId())).getE();
                List<Integer> allAdjacent = adjacencyMatrix.get(allEvents.indexOf(curr)).getE();
                Integer highestPriority = null;

                for (int k=0; k<allAdjacent.size();k++){
                	Integer currInt = allAdjacent.get(k);
                	if (currInt > 0 && (allEvents.get(k).timesVisited() < (MaxEventTestCaseConfiguration.REPEATS+1))){
                		temp.offer(allEvents.get(k));
                	}
                }

                curr = temp.poll();
                if (curr == null /*|| curr.timesUsed() > 0*/)
                	break;
                //unvisitedEvents.remove(unvisitedEvents.indexOf(curr));
                curr.incTimesVisited();
                curr.incTimesUsed();
                eventList.add(curr.getEventType());
                queue.offer(curr); //re-insert after incrementing properties
                /*Create a new queue for all EventTypes connected to curr.*/
                curr=queue.poll(); //similar to "getHighestPriorityEvent"
            }

            /* Writes the current test case to file. */
            String filename = outputDir+"/";
            filename += TEST_NAME_PREFIX;
            filename += String.format("%02d", i); // Pads zeroes to length of 2
            filename += TEST_NAME_SUFIX;
            writeToFile(filename, eventList);
            
            /*reset timesVisited for each event type*/
            PriorityQueue<EventTypeWeight> temp = new PriorityQueue<EventTypeWeight>(10, new EventComparator());
            EventTypeWeight e = null;
            while (queue.size() > 0) {
                    e = queue.poll();
                    e.resetTimesVisited();
                    temp.offer(e);
            }
            queue = temp;

        }
        
    }

	/*
		Returns whether the arguments are valid
	*/
    public boolean isValidArgs()
    {
       
        if (MaxEventTestCaseConfiguration.REPEATS == null)
                        return false;
                return true;

    }


	/* 
		Returns the initial events from the efg.
	*/
    List<Integer> getInitialEvents() {
        List<Integer> initialEvents = new ArrayList<Integer>();
        List<EventType> eventList = efg.getEvents().getEvent();
        for (EventType event : eventList) {
            if (event.isInitial())
                initialEvents.add(eventList.indexOf(event));
        }

        return initialEvents;
    }
	
	public TestCaseGeneratorConfiguration getConfiguration() {
		MaxEventTestCaseConfiguration configuration = new MaxEventTestCaseConfiguration();
		return configuration;
	}

    

   
 

/*
	A wrapper for the EventType which puts a weight on the event based on how many times it is used in the test case, 
	how often it is visited in the test case, and whether it is a terminal node.
*/
class EventTypeWeight {
    private int timesUsed;
    private int timesVisited;
    private boolean isTerminalNode;
    private EventType e;

    public EventTypeWeight(EventType e){
        timesUsed=0;
        timesVisited=0;
        isTerminalNode= e.getType().equals("TERMINAL"); //may not work
        this.e = e;
    }
	/*
		Returns whether it is a terminal node.
	*/
    public boolean isTerminalNode(){
        return isTerminalNode;
    }  
	/*
		Increments the number of times the event is used.
	*/  
    public void incTimesUsed(){
        timesUsed++;
    }
	/*
		Increments the number of times the event is visited
	*/
    public void incTimesVisited(){
        timesVisited++;
    }
	/*
		Returns the number of times the event is used in the tests.
	*/
    public int timesUsed(){
        return timesUsed;
    }
	/*
		Returns the number of times the event is visited in the tests.
	*/
    public int timesVisited(){
        return timesVisited;
    }
	/*
		Resets the number of times visited back to 0
	*/
    public void resetTimesVisited() {
        timesVisited=0;
    }
	/*
		Returns the Event Type that the object wraps.
	*/
    public EventType getEventType(){
        return e;
    }
}

/*
	Used to compare the EventTypeWeights. Used for O(nlogn) sorting.
*/
class EventComparator implements Comparator<EventTypeWeight>{
    public int compare(EventTypeWeight a, EventTypeWeight b){        //-1 if a is smaller, 0 if equal, 1 if a is greater

       //First most important: Has it already been used in any test case?
        if (a.timesUsed() != b.timesUsed())
            return ((Integer)a.timesUsed()).compareTo((Integer)b.timesUsed());

        //Second most important: Number of times visited during this run-through
        if (a.timesVisited() != b.timesVisited())
            return ((Integer)a.timesVisited()).compareTo((Integer)b.timesVisited());

        //Third most important: Terminal node is last (or fancy stuff if we choose to add that for dif. component types)
        if (a.isTerminalNode() && !b.isTerminalNode())
            return -1;
        if (b.isTerminalNode() && !a.isTerminalNode())
            return 1;

        //Last: Just give up and compare alphabetically (or by type? with terminal at end?, etc, by other types...figureitout...)
        return a.getEventType().getEventId().compareTo(b.getEventType().getEventId());

    }

    public boolean equals(EventTypeWeight t){
        return this.equals(t);
    }
}
}




