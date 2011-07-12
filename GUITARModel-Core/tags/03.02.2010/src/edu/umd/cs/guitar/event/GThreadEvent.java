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

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * Abstract class for all GUITAR events requiring to run in a separate thread.
 * All subclasses must implement the <code> actionPerformImp </code> methods.
 * 
 * <p>
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public abstract class GThreadEvent implements GEvent {

	/**
     * 
     */
	public GThreadEvent() {
		super();
		if (threadGroup == null) {
			threadGroup = new DispatchThreadGroup("GThreadEvent group");
		}
	}

	static DispatchThreadGroup threadGroup;

	@Override
	public final void perform(GComponent gComponent, Object parameters) {
		Thread t = new Thread(threadGroup, new DispatchThread(gComponent,
				parameters));

		t.start();

	}

	@Override
	public final void perform(GComponent gComponent) {
		Thread t = new Thread(threadGroup, new DispatchThread(gComponent));
		// Thread t = new Thread(new DispatchThread(gComponent));
		t.start();
	}

	/**
	 * The actual implementation of the event without parameters
	 * 
	 * <p>
	 * 
	 * @param gComponent
	 */
	protected abstract void performImpl(GComponent gComponent);

	/**
	 * 
	 * The actual implementation of the event with parameters
	 * 
	 * @param gComponent
	 * @param parameters
	 */
	protected abstract void performImpl(GComponent gComponent, Object parameters);

	/**
	 * A helper class to group all event dispatching threads
	 * 
	 * <p>
	 * 
	 * @author <a href="mailto:baonn@cs.umd.edu"> Bao N. Nguyen </a>
	 * 
	 */
	class DispatchThreadGroup extends ThreadGroup {

		/**
		 * @param name
		 */
		public DispatchThreadGroup(String name) {
			super(name);
		}

		@Override
		public void uncaughtException(Thread t, Throwable e) {

			GUITARLog.log.error(this.getName() + " uncaught Exception!!!", e);
			throw (RuntimeException) e;
		}
	}

	/**
	 * A helper class to run action in a separate thread.
	 * 
	 * <p>
	 * 
	 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
	 */
	// private class DispatchThread extends Thread {
	private class DispatchThread implements Runnable {

		GComponent gComponent;
		Object parameters = null;

		/**
		 * @param gComponent
		 */
		public DispatchThread(GComponent gComponent) {
			super();
			this.gComponent = gComponent;
		}

		/**
		 * @param gComponent
		 */
		public DispatchThread(GComponent gComponent, Object parameters) {
			this.gComponent = gComponent;
			this.parameters = parameters;
		}

		@Override
		public void run() {
			synchronized (gComponent) {

				if (parameters == null)
					performImpl(gComponent);
				else
					performImpl(gComponent, parameters);

			}
		}
	}
}