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

import edu.umd.cs.guitar.exception.GException;
import edu.umd.cs.guitar.replayer.Replayer;
import edu.umd.cs.guitar.util.GUITARLog;

//import edu.umd.cs.guitar.guitestrunner.TestCaseStepEventArgs;

/**
 * Interface to be implemented by classes that wish to be notified when test
 * case steps run.
 * 
 * @author Scott McMaster (modified by Bao Nguyen)
 * 
 */

public abstract class GTestMonitor {

    /**
	 * 
	 */
	public GTestMonitor() {
		super();
		GUITARLog.log.info("Test monitor: " + this.getClass().getCanonicalName() +" is created");
	}

	Replayer replayer;

    /**
     * @return the replayer
     */
    public Replayer getReplayer() {
        return replayer;
    }

    /**
     * @param replayer the replayer to set
     */
    public void setReplayer(Replayer replayer) {
        this.replayer = replayer;
    }

    /**
     * Called on application startup, before the first step is run.
     */
    public abstract void init();

    /**
     * Called after the last step is through executing.
     */
    public abstract void term();
    
    
    /**
     * Handle GUITAR exception thrown through executing. 
     */
    public abstract void exceptionHandler(GException e);

    /**
     * Notification right before a step runs.
     * 
     * @param args
     */
    public abstract void beforeStep(TestStepStartEventArgs step);

    /**
     * Notification right after a step runs.
     * 
     * @param args
     */
    public abstract void afterStep(TestStepEndEventArgs eStep) ;

}
