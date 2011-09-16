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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import local.edg.EventNode;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.testcase.plugin.ProgramAnalysis.Output;

public class MaxPrefixGenerator implements TCGeneratorMethod {
	
	private List<EventNode> edg;
	
	private int maxPrefix;
	
	private Output out;
	
	private int maxTC;
	
	private int countTC;
	
	private HashMap<String, Integer> deps = new HashMap<String, Integer>();
	
	@Override
	public void generate(List<EventNode> edg, List<EventType> initialEvents,
			Hashtable<EventType, Vector<EventType>> preds,
			Hashtable<EventType, Vector<EventType>> succs, Output out,
			int maxTC, int lengthTC) {
		
		// init
		this.edg = edg;
		this.maxPrefix = lengthTC;
		this.out = out;
		this.maxTC = maxTC;
		this.countTC = 0;
		
		int i = 0;
		for ( EventNode e : edg ) {
			for ( EventNode f : edg ) {
				String key = e.getEvent().getEventId() + "_" + f.getEvent().getEventId();
				int dep = e.dependencyToWriter(f);
				System.out.println(++i + " " + e.getEvent().getEventId() + "_" + f.getEvent().getEventId()
						+ " = " + dep);
				
				deps.put(key, dep);
			}
		}		
		
		// iterate the EDG
		for ( EventNode e : edg ) {			
			// create list of sequences
			LinkedList<LinkedList<EventNode>> allseq = new LinkedList<LinkedList<EventNode>>();
			
			// start with e
			LinkedList<EventNode> seq = new LinkedList<EventNode>();
			seq.add(e);
			
			// first, find prefix sequences
			System.out.println("finding...");
			findSeq(allseq, seq);
			
			// then, choose a prefix sequences
			System.out.println("choosing...");
			chooseSeq(allseq, 100);
		}
	}
	
	/**
	 * Finds prefix sequences
	 * @param allseq
	 * @param seq
	 */
	protected void findSeq(LinkedList<LinkedList<EventNode>> allseq, LinkedList<EventNode> seq) {
		boolean prefixFound = false;
		
		// max prefix reached?
		if ( seq.size() == maxPrefix + 1 ) {
			allseq.add(seq);
			return;
		}		
		
		// iterate EDG
		for ( EventNode e : edg ) {
			// ignore terminal events
			if ( TCUtil.isTerminalEvent(e.getEvent()) )
				continue;
			
			if (e.getEvent().getEventId().equals(seq.getLast().getEvent().getEventId()))
				continue;
			
			// dependency available?
			if ( e.writesTo(seq.getLast()) ) {
				prefixFound = true;
				
				// create new sequence
				LinkedList<EventNode> newseq = new LinkedList<EventNode>(seq);
				newseq.add(e);				
				findSeq(allseq, newseq);
			}
		}
		
		// no prefix found?
		if ( !prefixFound ) {
			allseq.add(seq);
		}
	}
	
	class QualityPair {
		public int i;
		public int q;
	}
	
	/**
	 * Chooses prefix sequences
	 * @param allseq
	 * @param max
	 */
	protected void chooseSeq(LinkedList<LinkedList<EventNode>> allseq, int max) {
		/*// sort all sequences by dependency
		Collections.sort(allseq, new Comparator<LinkedList<EventNode>>() {
			public int compare(LinkedList<EventNode> l1, LinkedList<EventNode> l2) {
				int dep1 = getDependency(l1);
				int dep2 = getDependency(l2);
				
				if ( dep1 > dep2 )
					return 1;
				else if (dep1 < dep2)
					return -1;
				
				return 0;
			}
			
			protected int getDependency(LinkedList<EventNode> l) {
				int dep = 0;				
				for ( int i = 0; i < l.size() - 1; i++ ) {
					dep += l.get(i).dependencyToWriter(l.get(i + 1));;
				}
				return dep;
			}			
		});*/
		
		List<QualityPair> qpl = new ArrayList<QualityPair>();		
		for ( int i = 0; i < allseq.size(); i++ ) {
			System.out.println(i + 1 + " / " + allseq.size());
			int dep = 0;
			LinkedList<EventNode> l = allseq.get(i);
			
			for ( int j = 0; j < l.size() - 1; j++ ) {
				EventNode e1 = l.get(j);
				EventNode e2 = l.get(j + 1);
				
				dep += deps.get(e1.getEvent().getEventId() + "_" + e2.getEvent().getEventId());
			}
			
			QualityPair qp = new QualityPair();
			qp.i = i;
			qp.q = dep;			
			qpl.add(qp);
		}
		
		Collections.sort(qpl, new Comparator<QualityPair>() {			
			public int compare(QualityPair q1, QualityPair q2) {
				if ( q1.q < q2.q ) {
					return -1;
				}
				if ( q1.q > q2.q ) {
					return 1;
				}
				return 0;
			}
		});
		
		try {
			
			for ( int i = qpl.size() - 1; i >= 0; i-- ) {
				if ( 0 == max )
					break;
				
				QualityPair qp = qpl.get(i);
				LinkedList<EventNode> l1 = allseq.get(qp.i);
				
				// convert to EventType list
				LinkedList<EventType> l2 = new LinkedList<EventType>();
				for ( EventNode e : l1 ) {
					l2.add(e.getEvent());
				}
				
				// write the test case
				if (out.createSequenceTC(l2)) {
					// if test case was created if maxTC is reached
					countTC++;
					if (!(maxTC <= 0) && countTC >= maxTC)
						throw new MaxTCReachedException();
				}
				
				max--;
			}
			
			/*
			// choose the top sequences
			for ( int i = allseq.size() - 1; i >= 0; i-- ) {
				if ( 0 == max )
					break;
				
				// convert to EventType list
				LinkedList<EventType> l2 = new LinkedList<EventType>();
				for ( EventNode e : allseq.get(i) ) {
					l2.add(e.getEvent());
				}
				
				// write the test case
				if (out.createSequenceTC(l2)) {
					// if test case was created if maxTC is reached
					countTC++;
					if (!(maxTC <= 0) && countTC >= maxTC)
						throw new MaxTCReachedException();
				}
				
				max--;
			}
			*/
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
}
