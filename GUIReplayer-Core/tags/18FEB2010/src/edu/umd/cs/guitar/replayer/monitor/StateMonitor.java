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
package edu.umd.cs.guitar.replayer.monitor;

import java.util.List;

import edu.umd.cs.guitar.model.GApplication;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.model.data.TestCase;
import edu.umd.cs.guitar.replayer.GReplayerMonitor;
import edu.umd.cs.guitar.replayer.Replayer;
import edu.umd.cs.guitar.replayer.monitor.GTestMonitor;
import edu.umd.cs.guitar.util.Debugger;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class StateMonitor extends GTestMonitor {

    // Log log;
    // ExecutionInfo dExeInfo = new ExecutionInfo();
    String sStateFile;
    TestCase outTestCase;
    static ObjectFactory factory = new ObjectFactory();

    /**
     * @param sStateFile
     */
    public StateMonitor(String sStateFile) {
        super();
        this.sStateFile = sStateFile;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.replayer.GTestMonitor#init()
     */
    @Override
    public void init() {
        outTestCase = factory.createTestCase();
        
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.umd.cs.guitar.replayer.GTestMonitor#afterStep(edu.umd.cs.guitar.replayer
     * .TestStepEventArgs)
     */
    @Override
    public void afterStep(TestStepEndEventArgs eStep) {

        GUITARLog.log.info("Recording GUI state....");

        List<StepType> lSteps = outTestCase.getStep();

        StepType step = eStep.getStep();

        GReplayerMonitor monitor = replayer.getMonitor();
        GApplication gApplication = monitor.getApplication();
        GUIStructure gCurrentState = gApplication.getCurrentState();

        step.setGUIStructure(gCurrentState);
        lSteps.add(step);
        outTestCase.setStep(lSteps);
        GUITARLog.log.info("DONE");
        GUITARLog.log.info("Dumping out state ... ");
        IO.writeObjToFile(outTestCase, sStateFile);
        GUITARLog.log.info("DONE");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.umd.cs.guitar.replayer.GTestMonitor#beforeStep(edu.umd.cs.guitar.
     * replayer.TestStepEventArgs)
     */
    @Override
    public void beforeStep(TestStepStartEventArgs eStep) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.replayer.GTestMonitor#term()
     */
    @Override
    public void term() {
//        GUITARLog.log.info("Dumping GUI states");
//        IO.writeObjToFile(outTestCase, sStateFile);
//        GUITARLog.log.info("DONE");
    }

}
