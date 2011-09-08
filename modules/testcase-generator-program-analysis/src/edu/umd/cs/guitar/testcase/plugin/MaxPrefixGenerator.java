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

public class MaxPrefixGenerator implements TCGeneratorMethod {
	
	private List<EventNode> edg;
	
	@Override
	public void generate(List<EventNode> edg, List<EventType> initialEvents,
			Hashtable<EventType, Vector<EventType>> preds,
			Hashtable<EventType, Vector<EventType>> succs, Output out,
			int maxTC, int lengthTC) {
		
		// init
		this.edg = edg;
		
		// iterate the EDG
		for ( EventNode e : edg ) {
			// create list of event sequences
			LinkedList<LinkedList<EventType>> seq = new LinkedList<LinkedList<EventType>>();
			
			// first, find sequences
			findSeq(lengthTC, e, seq);
			
			// then, choose sequence
			chooseSeq(seq);
		}
	}
	
	/**
	 * 
	 * @param seq
	 */
	protected void findSeq(int prefixLength, EventNode e, LinkedList<LinkedList<EventType>> seq) {
	}
	
	/**
	 * 
	 * @param seq
	 */
	protected void chooseSeq(LinkedList<LinkedList<EventType>> seq) {
	}
		
			
		/*
		try {
			// iterate the EDG
			for ( EventNode n : edg ) {			
				// create sequence and add initial event
				LinkedList<EventType> seq = new LinkedList<EventType>();
				seq.push(n.getEvent());
				
				// consider prefix length
				EventNode e = n;
				for ( int i = 0; i < lengthTC; i++ ) {
					e = findMaxPrefix(e);
					
					// no prefix found?
					if ( null == e ) {
						break;
					}
					
					// add prefix event
					seq.push(e.getEvent());
				}
				
				// write the test case
				if (out.createSequenceTC(seq)) {
					// if test case was created test if maxTC is reached
					countTC++;
					if (!(maxTC <= 0) && countTC >= maxTC)
						throw new MaxTCReachedException();
				}
			}
		}
		catch ( MaxTCReachedException e ) {
			e.printStackTrace();
		}
	}*/
	
	/**
	 * Finds the maximum prefix of e
	 * @param e Event which gets prefixed
	 * @return Max prefix of e
	 */
	/*
	private EventNode findMaxPrefix(EventNode e) {
		EventNode currentPrefix = null;
		int currentDependency = 0;
		
		for ( EventNode p : edg ) {
			// ignore terminal events
			if ( TCUtil.isTerminalEvent(p.getEvent()) )
				continue;			
			
			// greater dependency found?
			int d = p.dependencyToReader(e);
			if ( d > currentDependency ) {
				currentPrefix = p;
				currentDependency = d;
			}
		}
		
		return currentPrefix;
	}
	*/
}
