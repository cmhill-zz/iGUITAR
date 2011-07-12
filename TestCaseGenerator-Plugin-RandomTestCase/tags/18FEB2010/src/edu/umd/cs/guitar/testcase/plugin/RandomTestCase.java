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
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.model.data.RowType;

/**
 * 
 * Random Test Case Generator : generate a ramdomly a fixed number of TestCase.
 * 
 * @author <a href="mailto:charlie.biger@gmail.com"> Charlie BIGER </a>
 * 
 */
public class RandomTestCase extends TCPlugin {

    EFG efg;
    List<Integer> listEvent = new ArrayList<Integer>();
    List<Object> listPath = new ArrayList<Object>();
    List<Integer> invokingEvents = new ArrayList<Integer>();
    List<Integer> path = new ArrayList<Integer>();
    List<Integer> treatedInvokingEvents = new ArrayList<Integer>();

    public RandomTestCase() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.umd.cs.guitar.testcase.plugin.TCPlugin#generate(edu.umd.cs.guitar
     * .model.data.EFG, int)
     */
    @Override
    public void generate(EFG efg, String outputDir, int number) {
        new File(outputDir).mkdir();

        this.efg = efg;
        List<RowType> eventMatrix = efg.getEventGraph().getRow();

        Integer index = number;
        Random randomGenerator = new Random();

        List<Integer> initialEvents = getInitialEvent();
        List<EventType> eventList = efg.getEvents().getEvent();
        index = 0;

        while (index < number) {

            Integer currentStep = randomGenerator.nextInt(initialEvents.size());
            LinkedList<EventType> sequence = new LinkedList<EventType>();
            // Intial event
            sequence.add(eventList.get(currentStep));

            int count = 0;
            while (count < MAX_STEP && currentStep != -1) {
                currentStep = getNextRandomStep(currentStep, randomGenerator);
                if (currentStep != -1)
                    sequence.addLast(eventList.get(currentStep));
                count++;
            }

            writeToFile(outputDir + File.separator + TEST_NAME_PREFIX + index
                    + TEST_NAME_SUFIX, sequence);
            index++;
        }
    }

    int MAX_STEP = 5;

    int getNextRandomStep(int eventID, Random randomGenerator) {

        List<Integer> allEvents = efg.getEventGraph().getRow().get(eventID)
                .getE();
        List<Integer> followingEvents = new ArrayList();

        for (int i = 0; i < allEvents.size(); i++) {
            if (allEvents.get(i) == GUITARConstants.NO_EDGE) {
                followingEvents.add(i);
            }
        }

        if (followingEvents.size() > 0) {
            Integer x = randomGenerator.nextInt(followingEvents.size());
            return followingEvents.get(x);
        } else
            return -1;

    }

    List<Integer> getInitialEvent() {
        List<Integer> initialEvents = new ArrayList<Integer>();
        List<EventType> eventList = efg.getEvents().getEvent();
        for (EventType event : eventList) {
            if (event.isInitial())
                initialEvents.add(eventList.indexOf(event));
        }

        return initialEvents;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.testcase.plugin.TCPlugin#isValidArgs()
     */
    @Override
    public boolean isValidArgs() {
        return true;
    }

}
