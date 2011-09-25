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

import java.util.Comparator;
import java.util.HashMap;

import local.edg.EventNode;

public class SequenceMark extends SequenceGenerator {

	private HashMap<EventNode, Integer> visits;
	
	private class VisitsComparator implements Comparator<EventNode> {
		private Comparator<EventNode> _comp;

		public VisitsComparator(Comparator<EventNode> c) {
			_comp = c;
		}

		@Override
		public int compare(EventNode e1, EventNode e2) {
			// the less visits the more it should be used
			if (getVisits(e1) < getVisits(e2)) {
				return 1;
			}
			if (getVisits(e1) > getVisits(e2)) {
				return -1;
			}
			return _comp.compare(e1, e2);
		}

	}

	public SequenceMark(int evaluationMethod) {
		super(evaluationMethod);
		comp = new VisitsComparator(comp);

	}



	@Override
	protected void setUp() {
		visits = new HashMap<EventNode, Integer>();
	}

	@Override
	protected void visit(EventNode n) {
		Integer i = visits.get(n);
		if (i == null)
			i = 0;
		visits.put(n, i + 1);
	}

	private int getVisits(EventNode n) {
		Integer i = visits.get(n);
		return (i == null) ? 0 : i;
	}

	@Override
	protected boolean available(EventNode n) {
		return true;
	}

}
