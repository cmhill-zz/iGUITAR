/*
 *  Copyright (c) 2009-@year@. The  GUITAR group  at the University of
 *  Maryland. Names of owners of this group may be obtained by sending
 *  an e-mail to atif@cs.umd.edu
 *
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files
 *  (the "Software"), to deal in the Software without restriction,
 *  including without limitation  the rights to use, copy, modify, merge,
 *  publish,  distribute, sublicense, and/or sell copies of the Software,
 *  and to  permit persons  to whom  the Software  is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included
 *  in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 *  OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY,  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *  IN NO  EVENT SHALL THE  AUTHORS OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY
 *  CLAIM, DAMAGES OR  OTHER LIABILITY,  WHETHER IN AN  ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 *  SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package edu.umd.cs.guitar.util;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.EventGraphType;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.util.GUITARLog;


/**
 * Graph utility class
 */
public class GraphUtil {

   // Graph data to manupulate
   EFG inputGraph;


   /**
    * Map of <event, all events used to reveal it> Note that we
    * don't store all predecessor events here
    */
   Hashtable<EventType, Vector<EventType>> preds;


   /**
    * Map of <event, all successor events>
    */
   Hashtable<EventType, Vector<EventType>> succs;


   /**
    * Events marked as "initial" in the input graph.
    */
   List<EventType> initialEvents = null;


   /**
    * Initialise state.
    */
   public GraphUtil(EFG inputGraph)
   {
      this.inputGraph = inputGraph;
      parseFollowRelations();
      parseInitialEvents();
   }


   /**
    * Determine the initial events in the input graph.
    */
   protected void
   parseInitialEvents()
   {
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
    * Get follow relations in the input graph
    */
   private void
   parseFollowRelations()
   {
      List<EventType> eventList = inputGraph.getEvents().getEvent();
      int eventGraphSize = eventList.size();
      EventGraphType eventGraph = inputGraph.getEventGraph();

      if (inputGraph == null) {
         return;
      }

      succs = new Hashtable<EventType, Vector<EventType>>();
      preds = new Hashtable<EventType, Vector<EventType>>();

      for (int row = 0; row < eventGraphSize; row++) {
         EventType currentEvent = eventList.get(row);
         Vector<EventType> s = new Vector<EventType>();

         for (int col = 0; col < eventGraphSize; col++) {
            int relation = eventGraph.getRow().get(row).getE().get(col);

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
               } // if
            } // if

            succs.put(currentEvent, s);
         } // for
      } // for
   }


   /**
    * Find path to root
    *
    * @param event
    * @return
    */
   public LinkedList<EventType>
   pathToRoot(EventType event)
   {
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
               LinkedList<EventType> predPathToRoot = pathToRoot(pred);

               if (predPathToRoot == null) {
                  continue;
               } else if (!predPathToRoot.contains(event)) {
                  predPathToRoot.add(event);
                  return predPathToRoot;
               }
            } // for

            return null;
         }
      }
   }

   /**
    * Lookup event structure in 'efg' given the event ID.
    */
   public EventType
   lookupEvent(String eventID)
   {
      if (this.inputGraph == null) {
         return null;
      }

      List<EventType> lInputEvents = inputGraph.getEvents().getEvent();

      for (EventType event : lInputEvents) {
         if (event.getEventId().equals(eventID)) {
            return event;
         }
      }

      return null;
   }

} // Class
