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
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.EventGraphType;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.testcase.*;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * Plugin to cover a certain length path in the EFG
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class SequenceLengthCoverage extends TCPlugin {



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
    public void generate(EFG efg, String outputDir, int nMaxNumber) {

        new File(outputDir).mkdir();

        this.efg = efg;

        initialize();

        List<EventType> eventList = efg.getEvents().getEvent();

        for (EventType e : eventList) {
            // GUITARLog.log.info("================================");
            // GUITARLog.log.info("Analyzing " + e.getEventId());

            generateWithLength(TestCaseGeneratorConfiguration.LENGTH,
                    new LinkedList<EventType>(), e);

            // GUITARLog.log.info("Done " + e.getEventId());
        }
    }

    /**
     * 
     */
    private void printGraph() {
        // GUITARLog.log.info("Graph");

        List<EventType> eventList = efg.getEvents().getEvent();
        int eventGraphSize = eventList.size();
        EventGraphType eventGraph = efg.getEventGraph();

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
            GUITARLog.log.info("");
        }

    }

    /**
     * Just for debug
     */
    private void debug() {

        GUITARLog.log.info("Debugging.........");
        List<EventType> eventList = efg.getEvents().getEvent();
        for (EventType e : eventList) {
            GUITARLog.log.info("================================");
            GUITARLog.log.info("Analyzing " + e.getEventId() + "-"
                    + e.getWidgetId());
            List<EventType> predEventList = preds.get(e);

            String sEventList = "Pred: [";
            for (EventType predEvent : predEventList) {
                sEventList += (predEvent.getEventId() + "-"
                        + predEvent.getWidgetId() + ", ");

            }
            sEventList += "]";
            GUITARLog.log.info(sEventList);
        }

        GUITARLog.log.info("Done Debugging.........");

    }

    int index = 0;

    private void generateWithLength(int length, LinkedList<EventType> posfix,
            EventType root) {
        if (length <= 1) {
            LinkedList<EventType> path = getPathToRoot(root);
            if (path != null) {
                path.addAll(posfix);

                writeToFile(TestCaseGeneratorConfiguration.OUTPUT_DIR
                        + File.separator + TEST_NAME_PREFIX + (index) + TEST_NAME_SUFIX,
                        path);
                index++;

            } else {
                GUITARLog.log.info("root: " + root.getWidgetId());
                GUITARLog.log.info(posfix + " is unreachable");

            }

        } else {
            for (EventType succEvent : succs.get(root)) {
                LinkedList<EventType> extendedPosfix = new LinkedList<EventType>(
                        posfix);
                extendedPosfix.addLast(root);
                generateWithLength(length - 1, extendedPosfix, succEvent);
            }
        }
    }

}
