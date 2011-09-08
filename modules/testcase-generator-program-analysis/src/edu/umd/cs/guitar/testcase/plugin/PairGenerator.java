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

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import local.edg.EventNode;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.testcase.plugin.ProgramAnalysis.Output;

public class PairGenerator implements TCGeneratorMethod {
	@Override
	public void generate(List<EventNode> edg, List<EventType> initialEvents,
			Hashtable<EventType, Vector<EventType>> preds,
			Hashtable<EventType, Vector<EventType>> succs, Output out,
			int maxTC, int lengthTC) {
		int tcCount = 0;
		for (EventNode n1 : edg) {

			boolean usedN1 = false;
			// does the event influence other events
			if (!n1.isEmpty() && !TCUtil.isTerminalEvent(n1.getEvent())
					&& n1.hasWrite()) {
				// create all pairs
				for (EventNode n2 : edg) {
					if (n1 != n2 && n1.writesTo(n2)) {
						if (out.createSequenceTC(makePair(n1.getEvent(),
								n2.getEvent()))) {
							usedN1 = true;
							if (!(maxTC <= 0) && ++tcCount >= maxTC)
								break;// break inner loop
						}
					}

				}
			}
			if (!usedN1) {
				// create single element TC to obtain a better comparable
				// coverage
				if (out.createSequenceTC(makeSingle(n1.getEvent())))
					tcCount++;
			}
			if (!(maxTC <= 0) && tcCount >= maxTC)
				break;// break outer loop

		}
	}

	private static LinkedList<EventType> makePair(EventType e1, EventType e2) {
		LinkedList<EventType> l = new LinkedList<EventType>();
		l.add(e1);
		l.add(e2);
		return l;
	}

	private static LinkedList<EventType> makeSingle(EventType e) {
		LinkedList<EventType> l = new LinkedList<EventType>();
		l.add(e);
		return l;
	}

}
