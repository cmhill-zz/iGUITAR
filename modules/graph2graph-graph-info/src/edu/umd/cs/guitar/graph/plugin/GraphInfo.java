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
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import edu.umd.cs.guitar.graph.Graph2GraphConfiguration;
import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao N. Nguyen </a>
 * 
 */
public class GraphInfo extends EFG2EIG {

   int nTotalEvents = 0;
   int nTotalEdges  = 0;

   /**
    * @param inputGraph
    */
   public
   GraphInfo(EFG inputGraph) {
      super(inputGraph);
   }


   /**
    * (non-Javadoc)
    * 
    * @see
    * edu.umd.cs.guitar.graph.plugin.G2GPlugin#parseGraph(edu.umd.cs.guitar
    * .model.data.EFG)
    */
   @Override
   public boolean
   parseGraph()
   {
      parseFollowRelations();

      try {
         List<EventType> lEventList = getAllEventList();

         BufferedWriter bw = new BufferedWriter(
                                new FileWriter(
                                   Graph2GraphConfiguration.EXTRA_ARG
                                )
                             );

         for (EventType event : lEventList) {
            nTotalEvents++;

            // Write eventID
            bw.write(event.getEventId());
            if (event.isInitial()) {
               bw.write(" I ");
            }
            bw.newLine();

            // Otherwise write list of edge pairs
            for (EventType succEvent : succs.get(event)) {
               nTotalEdges++;
               bw.write(event.getEventId() + " -> " +
                        succEvent.getEventId());
               bw.newLine();
            }
         }

         bw.flush();
         bw.close();

      } catch (IOException e) {
         e.printStackTrace();
      }

      return true;
   }


   /**
    * Indicate if an output graph file needs to be generated for
    * this plugin.
    */
   @Override
   public boolean
   shouldGenerateGraph()
   {
      return false;
   }


   /**
    * Indicate if a .MAP file needs to be generated for this plugin.
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
      return true;
   }


   /**
    * Print info after operation is complete.
    */
   @Override
   public void
   printInfo()
   {
      System.out.println("Optional output         : " +
                         Graph2GraphConfiguration.EXTRA_ARG);
      System.out.println("Total input events      : " + nTotalEvents);
      System.out.println("Total input edges       : " + nTotalEdges);
   }

} // End of class
