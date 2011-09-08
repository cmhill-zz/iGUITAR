/*	
 *  Copyright (c) 2011. The GREYBOX group at the University of Freiburg, Chair of Software Engineering.
 *  Names of owners of this group may be obtained by sending an e-mail to arlt@informatik.uni-freiburg.de
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

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Vector;

import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.data.EventType;

public class TCUtil {
	
	/**
	 * Retrieves if the event is a terminal event.
	 * @param e Event
	 * @return true if Terminal
	 */
	public static boolean isTerminalEvent(EventType e) {
		return GUITARConstants.TERMINAL.equals(e.getType());
	}
	
	/**
	 * Breadth-first-search from event to event in the EFG.
	 * 
	 * @param start
	 *            Start event.
	 * @param goal
	 *            Goal event to reach.
	 * @param successor
	 *            Map with successor lists for given events.
	 * @return Event sequence between start and goal.
	 */
	public static LinkedList<EventType> bfsEvent2Event(EventType start,
			EventType goal, Hashtable<EventType, Vector<EventType>> successor) {
		LinkedList<EventType> retPath = null;
		EventType found = null;
		//to avoid cycles and trace the path
		HashMap<EventType, EventType> visitedBy = new HashMap<EventType, EventType>();
		LinkedList<EventType> queue = new LinkedList<EventType>();
		queue.add(start);
		// BFS to find the shortest path from start to goal
		while (found == null && !queue.isEmpty()) {
			EventType e = queue.remove();
			// expand node
			for (EventType succ : successor.get(e)) {
				if (!visitedBy.containsKey(succ)) {
					visitedBy.put(succ, e);
					if (succ == goal) {
						found = succ;
						break;// stop searching
					} else {
						queue.add(succ);
					}
				}
			}

		}
		// generate path
		if (found != null) {
			retPath = new LinkedList<EventType>();
			EventType cursor = visitedBy.get(found);
			while (cursor != start) {
				retPath.addFirst(cursor);
				cursor = visitedBy.get(cursor);
			}
		}
		return retPath;
	}
	
	/* Local testing (todo: make unit tests)*/
	public static void main(String[] args) {
		Hashtable<EventType, Vector<EventType>> succ = new
		 Hashtable<EventType, Vector<EventType>>();
		 EventType e1 = new EventType();
		 e1.setEventId("1");
		 EventType e2 = new EventType();
		 e2.setEventId("2");
		 EventType e3 = new EventType();
		 e3.setEventId("3");
		 EventType e4 = new EventType();
		 e4.setEventId("4");
		 EventType e5 = new EventType();
		 e5.setEventId("5");
		 EventType e6 = new EventType();
		 e6.setEventId("5");
		
		 succ.put(e1, makeVec(e1, e2, e3));
		 succ.put(e2, makeVec(e1, e2, e3));
		 succ.put(e3, makeVec(e1, e2, e3, e4, e5));
		 succ.put(e4, makeVec(e1, e2, e3));
		 succ.put(e5, makeVec(e1, e2, e3));
		
		 printPath(bfsEvent2Event(e1, e1, succ));
		 printPath(bfsEvent2Event(e1, e2, succ));
		 printPath(bfsEvent2Event(e1, e3, succ));
		 printPath(bfsEvent2Event(e1, e4, succ));
		 printPath(bfsEvent2Event(e1, e5, succ));
		 printPath(bfsEvent2Event(e4, e5, succ));
		 printPath(bfsEvent2Event(e4, e6, succ));
	}
	private static Vector<EventType> makeVec(EventType... es) {
		Vector<EventType> r = new Vector<EventType>(es.length);
		for (EventType e : es) {
			r.add(e);
		}
		return r;
	}

	private static void printPath(LinkedList<EventType> path) {
		if (path == null) {
			System.out.println("no path available");
		} else {
			boolean arrow = false;

			for (EventType e : path) {
				if (arrow)
					System.out.print("-->");
				System.out.print(e.getEventId());
				arrow = true;
			}
			if (path.isEmpty())
				System.out.println("direct reach");
			else
				System.out.println();
		}
	}
}
