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
package edu.umd.cs.guitar.event;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleAction;
import javax.accessibility.AccessibleContext;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.JFCXComponent;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class JFCActionHandler extends JFCEventHandler {

    /**
     * 
     */
    public JFCActionHandler() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void performImpl(GComponent gComponent) {

        if (gComponent == null) {
            return;
        }

        Accessible aComponent = getAccessible(gComponent);

        if (aComponent == null)
            return;

        AccessibleContext aContext = aComponent.getAccessibleContext();

        if (aContext == null)
            return;
        AccessibleAction aAction = aContext.getAccessibleAction();

        if (aAction == null)
            return;

        try {
            int nActions = aAction.getAccessibleActionCount();
            if (nActions > 0)
                aAction.doAccessibleAction(0);
        } catch (Exception e) {
            GUITARLog.log.error("Cannot expand", e);

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.umd.cs.guitar.event.JFCEventHandler#actionPerformImp(edu.umd.cs.guitar
     * .model.GXComponent, java.lang.Object)
     */
    @Override
    protected void performImpl(GComponent gComponent, Object parameters) {
        performImpl(gComponent);
    }
}
