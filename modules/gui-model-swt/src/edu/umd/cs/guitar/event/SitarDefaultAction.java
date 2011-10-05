/*	
 *  Copyright (c) 2011-@year@. The GUITAR group at the University of Maryland. Names of owners of this group may
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

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.SitarConstants;
import edu.umd.cs.guitar.model.swtwidgets.SitarWidget;

/**
 * The default action supported by most {@code SitarWidget}s.
 * {@code SitarDefaultAction} simulates a user interacting with a widget by
 * notifying all event listeners registered on the widget.
 * 
 * @author Gabe Gorelick
 * 
 * @see SitarWidget#getEventList()
 */
public class SitarDefaultAction extends SitarAction {
		
	/**
	 * Execute all events that the given widget is listening for.
	 * 
	 * @param gComponent
	 *            the component to perform this action on
	 *            
	 * @see Widget#isListening(int)
	 */
	public void perform(GComponent gComponent) {
		if (gComponent == null) {
			return;
		}

		final Widget widget = getWidget(gComponent);
		
		widget.getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				Event event = new Event();
				
				for (int eventType : SitarConstants.SWT_EVENT_LIST) {
					if (widget.isListening(eventType)) {
						event.type = eventType;
						widget.notifyListeners(eventType, event);
					}
				}	
			}
		});		
	}

}
