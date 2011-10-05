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
package edu.umd.cs.guitar.graph.plugin;

import java.awt.Event;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import java.util.StringTokenizer;
import org.apache.log4j.Level;


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
 * Read an input graph and generate the corresponding output graph.
 * The edges added to the output graph are a subset of the input
 * graph. They are the edges present in the ECIG data read from the
 * ECIG data file.
 *
 * Note: If the input graph contains duplicate events, then the
 *       output graph will contain the same.
 *
 * Conversions:
 *
 *    1. EFG -> ECIG
 *
 * As of now ALL events from the input graph are transferred to the
 * output graph. This needs to be pruned.
 *
 * @author <a href="mailto:ishan@cs.umd.edu"> Ishan Banerjee </a>
 */
public class EFG2ECIG extends EFG2EIG {
   int eventInputGraph  = 0;
   int edgeInputGraph   = 0;
   int eventOutputGraph = 0;
   int edgeOutputGraph  = 0;
   int ecigPair         = 0;
   int eventNotFound    = 0;


   /**
    * @param inputGraph
    */
   public EFG2ECIG(EFG inputGraph) {
      super(inputGraph);
   }


   /*
    * (non-Javadoc)
    * 
    * @see
    * edu.umd.cs.guitar.graph.plugin.G2GPlugin#parseGraph(edu.umd.cs.guitar
    * .model.data.EFG)
    */
   @Override
   public boolean parseGraph() {
      // Initialise output ECIG graph generation
      boolean result = true;
      boolean count  = true;

      outputGraph = factory.createEFG();

      String ecigFilename = "/tmp/ecig.txt";

      List<String[]> ecigData = getEventInteractionData(ecigFilename);

      // Prepare list of events 
      EventsType eventList      = factory.createEventsType();
      EventGraphType eventGraph = factory.createEventGraphType();

      // Read ALL the list of events for validation
      List<EventType> lAllEventList = getAllEventList();

      eventInputGraph = lAllEventList.size();

      // Read the list of events of interest for output graph
      List<EventType> lEventList = getEventList();

      /*
       * ECIG generation
       *
       * * Validate input ECIG data against ALL events
       * * Build new edge list from ECIG data.
       */

      /*
       * Validate input ECIG data against ALL events
       * 
       * Validate  against ALL event list from input graph.
       * The input ECIG event pair must exist as an edge in the
       * input graph.
       */

      for (String[] strArray : ecigData) {
         boolean found = false;
   
         for (EventType firstEvent : lAllEventList) {
            int indexFirst = lAllEventList.indexOf(firstEvent);


            for (EventType secondEvent : lAllEventList) {
               int indexSecond = lAllEventList.indexOf(secondEvent);
 
               int curCellValue = this.inputGraph.getEventGraph().getRow().
                                  get(indexFirst).getE().get(indexSecond);

               // Count # edges in input graph
               if (curCellValue >= 1 && count) {
                  edgeInputGraph++;
               }

               if (strArray[0].equals(firstEvent.getEventId()) &&
                   strArray[1].equals(secondEvent.getEventId())) {
                     found = true;
               } // if
            } // if
            
         } // for

         count = false;

         if (found == false) {
            GUITARLog.Error(" ECIG edge " + strArray[0] + " -> "
                            + strArray[1] + " not found in input graph");
            result = false;
            eventNotFound++;
         }

         ecigPair++;
      } // for

      // Exit if validation failed
      if (result == false) {
         GUITARLog.Error("Input ECIG data : " +
                        eventNotFound + " bad events of " +
                        ecigPair + " edges from ECIG data");

        return result;
      }


      // Set output set of events same as input set of events
      eventList.setEvent(lEventList);
      outputGraph.setEvents(eventList);

      /*
       * Build new edge list from ECIG data.
       *
       * For each event pair, retain it in the output graph
       * only if there exists a data point in the ECIG data.
       * Use the ALL edge list to prepare the output graph. It
       * can be pruned later to remove unwanted event types.
       */
      List<RowType> lRowList = new ArrayList<RowType>();

      for (EventType firstEvent : lEventList) {
         int indexFirst = lEventList.indexOf(firstEvent);

         RowType row = factory.createRowType();

         eventOutputGraph++;
 
         for (EventType secondEvent : lEventList) {
            int indexSecond = lEventList.indexOf(secondEvent);
 
            int newCellValue = 0;

            for (String[] strArray : ecigData) {

               // Check and skip event pair from ECIG data
               if (!strArray[0].equals(firstEvent.getEventId()) ||
                   !strArray[1].equals(secondEvent.getEventId())) {
                  continue;
               }

               // The edge has been found in the ECIG data
               newCellValue = 1;

               GUITARLog.Debug("Adding edge:" + firstEvent.getEventId() +
                               " -> " + secondEvent.getEventId());

               edgeOutputGraph++;
            }   // for

            row.getE().add(indexSecond, newCellValue);
         }   // for

         lRowList.add(indexFirst, row);
      }   // for

      /*
       * Set the output value for ECIG and MAP
       */

      // Output ECIG
      eventGraph.setRow(lRowList);
      outputGraph.setEventGraph(eventGraph);


      return result;
   }


   /**
    * Read a file contain comma-separated event code interaction event pairs.
    *
    * The data is of the form:
    *
    * e1a,e2a
    * e1b,e2b
    *  ...
    *
    * @return
    */
   List<String[]> getEventInteractionData(String filename) {

      List<String[]> list = new ArrayList<String[]>();

      try {
         BufferedReader br = new BufferedReader( new FileReader(filename));

         String string = null;
         StringTokenizer st = null;
 
         while( (string = br.readLine()) != null) {
            int      i = 0;
            String[] e = new String[2];

            // Split comma separated line using ","
            st = new StringTokenizer(string, ",");

            // Although a while is used, we expect 2 tokens 
            while(st.hasMoreTokens()) {
               e[i++] = "e" + st.nextToken();
            }

            // Only two tokens expected
            if (e[0] != null && e[1] != null) {
               list.add(e);
            }
         }
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }

      return list;
   }


   /**
    * Indicate if an output graph file needs to be generated for
    * this plugin.
    */
   @Override
   public boolean
   shouldGenerateGraph()
   {
      return true;
   }


   /**
    * Indicate if MAP file needs to be generated for this plugin
    */
   @Override
   public boolean
   shouldGenerateMap()
   {
      return false;
   }


   /**
    * Requires extra arguments
    */
   @Override
   public boolean
   requireExtraArgs()
   {
      return false;
   }


   /**
    * Print info after operation is complete.
    */
   @Override
   public void
   printInfo()
   {
      // Stats / logs
      GUITARLog.Info("Input graph       : " + eventInputGraph + " events");
      GUITARLog.Info("Input graph       : " + edgeInputGraph + " edges");
      GUITARLog.Info("Input ECIG data   : " + ecigPair + " event pairs");
      GUITARLog.Info("Output graph      : " + eventOutputGraph + " events");
      GUITARLog.Info("Output graph      : " + edgeOutputGraph + " edges");
   }

} // End of class
