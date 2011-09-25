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

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import local.edg.EventNode;
import local.edg.Field;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.testcase.plugin.ProgramAnalysis.Output;

public abstract class SequenceGenerator implements TCGeneratorMethod {

	public static final int MAX_PREDECESSOR_DEPENDENCIES = 1;
	public static final int MAX_EDGE_DEPENDENCIES = 2;

	private int maxLength;
	protected HashSet<Field> reads;
	protected Comparator<EventNode> comp;

	public SequenceGenerator(int evaluationMethod) {
		switch (evaluationMethod) {
		case MAX_PREDECESSOR_DEPENDENCIES:
			comp = MAX_DEP_PRED;
			break;
		case MAX_EDGE_DEPENDENCIES:
			comp = MAX_DEP_EDGE;
			break;
		default:
			System.out.println("Should not happen.");
			break;
		}
	}

	@Override
	public void generate(List<EventNode> edg, List<EventType> initialEvents,
			Hashtable<EventType, Vector<EventType>> preds,
			Hashtable<EventType, Vector<EventType>> succs, Output out,
			int maxTC, int lengthTC) {
		int tcCount = 0;
		maxLength = lengthTC;
		setUp();
		for (EventNode n : edg) {
			if (!n.isEmpty()) {
				LinkedList<EventType> seq = generateEventSequence(n, edg);
				out.createSequenceTC(seq);
				if (!(maxTC <= 0) && ++tcCount >= maxTC)
					break;
			}
		}
	}

	/**
	 * Invoked before the testcase generation.
	 */
	protected void setUp() {
	}

	/**
	 * This function generates a sequence of events, which are dependent
	 * 
	 * @param endpoint
	 *            End of the sequence.
	 * @param entryList
	 *            List with all possible nodes, that can be used in the
	 *            sequence.
	 * @return Sequence of depending events.
	 */
	private LinkedList<EventType> generateEventSequence(EventNode endpoint,
			Collection<EventNode> entryList) {
		setUpSeq();
		LinkedList<EventType> seq = new LinkedList<EventType>();
		visit(endpoint);
		seq.addLast(endpoint.getEvent());
		reads = new HashSet<Field>(endpoint.getReads());
		EventNode next = findMaxMatch(reads, entryList);
		while (next != null && seq.size() < maxLength) {
			seq.push(next.getEvent());
			visit(next);
			reads.removeAll(next.getWrites());
			reads.addAll(next.getReads());
			next = findMaxMatch(reads, entryList);
		}
		return seq;

	}

	/**
	 * Invoked before the generation of every sequence.
	 */
	protected void setUpSeq() {
	}

	/**
	 * Finds Event (EDG Node), which is the best match for the given reads
	 * w.r.t. the {@link RWEntry#specialCompareTo(RWEntry)} function.
	 * 
	 * @param reads
	 *            Fields that are read.
	 * @param tomatch
	 *            Collection with candidates for matching.
	 * @return Best match.
	 */
	private EventNode findMaxMatch(Set<Field> reads, Collection<EventNode> tomatch) {
		EventNode match = null;
		for (EventNode n : tomatch) {
			if (!TCUtil.isTerminalEvent(n.getEvent()) && available(n)
					&& n.writesTo(reads)) {
				if (match == null) {
					match = n;
				} else if (comp.compare(n, match) > 0) {
					match = n;
				}
			}
		}
		return match;
	}

	/**
	 * Function to mark EDGNodes as available for the sequence generation.
	 * 
	 * @param n
	 *            Node to check.
	 * @return true if node is available for the sequence generation.
	 */
	protected abstract boolean available(EventNode n);

	/**
	 * Is executed if a node is visited by a sequence.
	 * 
	 * @param n
	 *            node that is visited.
	 */
	protected abstract void visit(EventNode n);

	private final Comparator<EventNode> MAX_DEP_PRED = new Comparator<EventNode>() {
		@Override
		public int compare(EventNode e1, EventNode e2) {
			int thisdeps = e1.getReads().size();
			int otherdeps = e2.getReads().size();
			if (thisdeps < otherdeps) {
				return -1;
			}
			if (thisdeps > otherdeps) {
				return 1;
			}
			return 0;
		}
	};

	private final Comparator<EventNode> MAX_DEP_EDGE = new Comparator<EventNode>() {
		@Override
		public int compare(EventNode e1, EventNode e2) {
			int thisdeps = EventNode.dependency(reads, e1.getWrites());
			int otherdeps = EventNode.dependency(reads, e2.getWrites());
			if (thisdeps < otherdeps) {
				return -1;
			}
			if (thisdeps > otherdeps) {
				return 1;
			}
			return 0;
		}
	};

}
