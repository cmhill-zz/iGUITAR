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
package edu.umd.cs.guitar.testcase.plugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import edu.umd.cs.guitar.testcase.TestCaseGenerator;
import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;
import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.EventGraphType;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.model.data.TestCase;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * 
 * Common interface for all Test case generator plugins
 * 
 * <p>
 * 
 * @author Bao Nguyen
 * 
 */
public abstract class TCPlugin {

   /**
    * 
    */
   final String TEST_NAME_PREFIX = "t";

   /**
    * 
    */
   final String TEST_NAME_SUFIX = ".tst";

   ObjectFactory factory = new ObjectFactory();
   List<EventType> initialEvents = null;


   /**
    * Map of <event, all events used to reveal it> Note that we don't
    * store al predecessor events here
    */
   Hashtable<EventType, Vector<EventType>> preds;


   /**
    * Map of <event, all successor events>
    */
   Hashtable<EventType, Vector<EventType>> succs;
   EFG efg;


   /**
    * Overridable function for retrieving additional plugin-specific
    * arguments.
    * <p>
    * 
    * @return TestCaseGeneratorConfiguration object with required arguments
    */
   public TestCaseGeneratorConfiguration
   getConfiguration()
   {
      TestCaseGeneratorConfiguration configuration =
         new TestCaseGeneratorConfiguration();

      return configuration;
   }


   /**
    * Check arguments for each plugin
    * 
    * <p>
    * 
    * @return
    */
   abstract public boolean isValidArgs();


   /**
    * Generate test cases
    * 
    * <p>
    * 
    * @param efg
    * @param outputDir
    * @param nMaxNumber
    */
   abstract public void generate(EFG efg, String outputDir, int nMaxNumber);


   /**
    * Preparing for test case generation
    */
   void initialize()
   {
      GUITARLog.Info("Parsing follow relations");
      parseFollowRelations();

      GUITARLog.Info("Getting initial events");
      parseInitialEvents();
   }


   /**
    * Get initial events
    */
   private void
   parseInitialEvents()
   {
      initialEvents = new ArrayList<EventType>();
      List<EventType> eventList = efg.getEvents().getEvent();
      for (EventType event : eventList) {
         if (event.isInitial() && preds.get(event) == null) {
            initialEvents.add(event);
         }
      }
      GUITARLog.Info("Initial event count: " + initialEvents.size());
   }


   /**
    * Get follow relations
    */
   private void
   parseFollowRelations()
   {
      List<EventType> eventList = efg.getEvents().getEvent();
      int eventGraphSize = eventList.size();
      EventGraphType eventGraph = efg.getEventGraph();

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
    * Get path to root of an event, return null if the event is unreachable
    * This path does not include the event itself
    * 
    * <p>
    * 
    * @param event
    * @return
    */
   LinkedList<EventType>
   getPathToRoot(EventType event)
   {
      return pathToRoot(event);
   }

 
   LinkedList<EventType> pathToRoot;
 
   private void
   findPathToRoot(LinkedList<EventType> pathToRoot, EventType event)
   {
      if (pathToRoot == null){
        return; 
      }
  
      if (pathToRoot.contains(event)) {
         return; 
      }

      if (initialEvents.contains(event)) {
         pathToRoot.addFirst(event);
      } else {
         Vector<EventType> predEventList = preds.get(event);

         if (predEventList == null) {
            pathToRoot =null;
         } else if (predEventList.size() == 0) {
            GUITARLog.log.info(event.getEventId()
                               + " has empty predEventList");
            pathToRoot = null;
         } else {
            for (EventType pred : predEventList) {
               LinkedList<EventType> predPathToRoot=null;
               findPathToRoot(predPathToRoot, pred);
     
               if (predPathToRoot == null) {
                  continue;
               } else if (!predPathToRoot.contains(event)) {
                  pathToRoot= new LinkedList<EventType>(predPathToRoot);
                  pathToRoot.addLast(event);
               }
            } // for

            pathToRoot=null;
         } // if
      } // if
   }


   /**
    * 
    * DFS searching for the event path to root
    * 
    * <p>
    * 
    * @param event
    * @param path
    * @return
    */
   @Deprecated
   private boolean
   DFS(EventType event, LinkedList<EventType> path)
   {
      if (initialEvents.contains(event)) {
         path.addFirst(event);

         return true;
      } else {
         Vector<EventType> predEventList = preds.get(event);

         if (predEventList == null) {
            GUITARLog.log.info(event.getEventId()
                               + " is unreachable from root");

            return false;
         }

         if (predEventList.size() == 0) {
            GUITARLog.log.info(event.getEventId()
                               + " has empty predEventList");

            return false;
         }

         for (EventType pred : predEventList) {
            // Break the cycle;
            if (path.contains(pred))
               return false;

            path.addFirst(event);
            boolean isFound = DFS(pred, path);
            if (isFound) {
               return true;
            }

            path.removeFirst();
         }
      }

      return false;
   }
 
 
   LinkedList<EventType>
   pathToRoot(EventType event, List<EventType> visitedEvent)
   {
      // Having  a cycle so break 
      if (visitedEvent.contains(event))
         return null;
  
      if (initialEvents.contains(event)) {
         LinkedList<EventType> path = new LinkedList<EventType>();
         path.add(event);
         return path;
      } else {
         Vector<EventType> predEventList = preds.get(event);

         if (predEventList == null)
            return null;
         else if (predEventList.size() == 0) {
            GUITARLog.log.info(event.getEventId()
             + " has empty predEventList");
            return null;
         } else {
    
            if (visitedEvent.size()>15) {
               return null;
            }
    
            for (EventType pred : predEventList) {
               List<EventType> newVistedEvent =
                  new ArrayList<EventType>(visitedEvent);
               newVistedEvent.add(event);
     
               LinkedList<EventType> predPathToRoot =
                  pathToRoot(pred, newVistedEvent);
     
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
 
 
   LinkedList<EventType>
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
            GUITARLog.log.info(event.getEventId()
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
               } // if
            } // for

            return null;
         }
      }
   }

   /**
    * 
    * Write the event sequence to file
    * 
    * <p>
    * 
    * @param tCName
    * @param path
    */
   void
   writeToFile(String tCName, LinkedList<EventType> path)
   {
      TestCase tc = factory.createTestCase();
      List<StepType> lStep = new ArrayList<StepType>();

      for (EventType e : path) {
         StepType step = factory.createStepType();
         step.setEventId(e.getEventId());
         lStep.add(step);
      }

      tc.setStep(lStep);

      System.out.println("Writting to " + tCName);
      IO.writeObjToFile(tc, tCName);
   }

} // End of class
