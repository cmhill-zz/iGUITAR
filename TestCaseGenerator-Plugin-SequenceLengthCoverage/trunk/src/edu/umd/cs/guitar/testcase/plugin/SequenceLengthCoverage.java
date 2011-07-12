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

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.EventGraphType;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.model.data.TestCase;
import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * Plugin to cover a certain length path in the EFG
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class SequenceLengthCoverage extends TCPlugin {

   int nMaxNumber;

   /**
    * 
    */
   public SequenceLengthCoverage() {

   }

   /*
    * (non-Javadoc)
    * 
    * @see edu.umd.cs.guitar.testcase.plugin.TCPlugin#isValidArgs()
    */
   @Override
   public boolean isValidArgs() {
      if (TestCaseGeneratorConfiguration.LENGTH == null) {
         return false;
      }

      return true;
   }

   /*
    * (non-Javadoc)
    * 
    * @see edu.umd.cs.guitar.testcase.plugin.TCPlugin#generate()
    */
   @Override
   public void
   generate(EFG efg, String outputDir, int nMaxNumber)
   {
      new File(outputDir).mkdir();

      this.efg = efg;
      this.nMaxNumber = nMaxNumber;

      initialize();

      List<EventType> eventList = efg.getEvents().getEvent();

      // Debug data
      debug();

      for (EventType aEvent : eventList) {
         LinkedList<EventType> initialList = new LinkedList<EventType>();

         initialList.add(aEvent);

         generateWithLength(TestCaseGeneratorConfiguration.LENGTH,
                            initialList);
      }
   }

   /**
    * Write debug data
    */
   private void
   debug()
   {
      GUITARLog.Info("*** Start: SequenceLengthCoverage debug info");

      List<EventType> eventList = efg.getEvents().getEvent();

      for (EventType e : eventList) {
         GUITARLog.Info("--- Predecessor list ---");
         GUITARLog.Info("Event " + e.getEventId() + "-"
                        + e.getWidgetId());

         List<EventType> predEventList = preds.get(e);

         String sEventList = "Pred: [";
         if (predEventList != null) {
            for (EventType predEvent : predEventList) {
               sEventList += (predEvent.getEventId() + "-"
                              + predEvent.getWidgetId() + ", ");

            }
         }

         sEventList += "]";
         GUITARLog.Info(sEventList);

         GUITARLog.Info("--- Successor list ---");
         GUITARLog.Info("Event " + e.getEventId() + "-"
                        + e.getWidgetId());

         List<EventType> succEventList = succs.get(e);

         sEventList = "Succ: [";
         if (succEventList != null) {
            for (EventType succEvent : succEventList) {
               sEventList += (succEvent.getEventId() + "-"
                              + succEvent.getWidgetId() + ", ");

            }
         }

         sEventList += "]";
         GUITARLog.Info(sEventList);
      }

      GUITARLog.Info("Stop: *** SequenceLengthCoverage debug info");
   }

   int index = 0;

   private void
   generateWithLength(int length, LinkedList<EventType> postfix)
   {
      if (index >= nMaxNumber && nMaxNumber != 0)
         return;

      if (length <= 1) {
         TestCase tc = factory.createTestCase();
         List<StepType> lStep = new ArrayList<StepType>();

         // Step 1: Generate output file name
         String sTestName = TEST_NAME_PREFIX;

         if (TestCaseGeneratorConfiguration.LENGTH < 30) {
            for (EventType event : postfix) {
               sTestName += ("_" + event.getEventId());
            }
         } else {
            sTestName += ("_" + index);
         }
         sTestName += TEST_NAME_SUFIX;

         // Step 2: Get path to root if possible
         LinkedList<EventType> path = getPathToRoot(postfix.getFirst());

         if (path != null) {
            // Remove the root event itself to avoid duplication
            if (path.size() > 0) {
               path.removeLast();
            }

            // Add path-to-root to steps
            for (EventType e : path) {
               StepType step = factory.createStepType();

               step.setEventId(e.getEventId());
               step.setReachingStep(true);
               lStep.add(step);
            }
         }

         // Step 3: Add testcase to steps
         for (EventType e : postfix) {
            StepType step = factory.createStepType();

            step.setEventId(e.getEventId());
            step.setReachingStep(false);
            lStep.add(step);
         }

         // Add combined steps to the testcase
         tc.setStep(lStep);

         // Step 4: Write to file
         String sPath = TestCaseGeneratorConfiguration.OUTPUT_DIR
                           + File.separator + sTestName;
         System.out.println("Writting to " + sPath );
         IO.writeObjToFile(tc, sPath);

         index++;
      } else {

         EventType lastEvent = postfix.getLast();

         for (EventType succEvent : succs.get(lastEvent)) {
            LinkedList<EventType> extendedPostfix =
               new LinkedList<EventType>(postfix);

            extendedPostfix.addLast(succEvent);
            generateWithLength(length - 1, extendedPostfix);
         }
      }
   }

   /**
    * 
    */
   private void
   printGraph()
   {
      List<EventType> eventList = efg.getEvents().getEvent();
      int eventGraphSize = eventList.size();
      EventGraphType eventGraph = efg.getEventGraph();

      GUITARLog.log.info("*** Graph Begin ***");

      for (int row = 0; row < eventGraphSize; row++) {
         EventType currentEvent = eventList.get(row);
         Vector<EventType> s = new Vector<EventType>();

         for (int col = 0; col < eventGraphSize; col++) {

            EventType event = eventList.get(col);

            int relation = eventGraph.getRow().get(row).getE().get(col);

            if (relation == GUITARConstants.FOLLOW_EDGE) {
               GUITARLog.log.info(currentEvent.getEventId() + ","
                  + currentEvent.getWidgetId() + "->"
                  + event.getEventId() + "," + event.getWidgetId());
            }
         }
      }

      GUITARLog.log.info("*** Graph End ***");

   } // functin end

} // class
