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
	
	private int maxPrefix;
	
	private Output out;
	
	private int maxTC;
	
	private int countTC;
	
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
		
		// iterate the EDG
		for ( EventNode e : edg ) {			
			// create list of sequences
			LinkedList<LinkedList<EventNode>> allseq = new LinkedList<LinkedList<EventNode>>();
			
			// start with e
			LinkedList<EventNode> seq = new LinkedList<EventNode>();
			seq.add(e);
			
			// first, find prefix sequences
			findSeq(allseq, seq);
			
			// then, choose a prefix sequence
			chooseSeq(allseq);
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
	
	/**
	 * Chooses a prefix sequence
	 * @param allseq
	 */
	protected void chooseSeq(LinkedList<LinkedList<EventNode>> allseq) {
		// selected sequence and selected dependency
		LinkedList<EventNode> selseq = null;
		int seldep = -1;
		
		// iterate list of sequences
		for ( LinkedList<EventNode> seq : allseq ) {
			int curdep = 0;
			for ( int i = 0; i < seq.size() - 1; i++ ) {
				// add current dependency
				curdep += seq.get(i).dependencyToWriter(seq.get(i + 1));;
			}
			
			if ( curdep > seldep ) {
				// new dependency and new sequence
				seldep = curdep;
				selseq = seq;
			}
		}
		
		try {
			// convert to EventType list
			LinkedList<EventType> selseq2 = new LinkedList<EventType>();
			for ( EventNode e : selseq ) {
				selseq2.add(e.getEvent());
			}
			
			// write the test case
			if (out.createSequenceTC(selseq2)) {
				// if test case was created test if maxTC is reached
				countTC++;
				if (!(maxTC <= 0) && countTC >= maxTC)
					throw new MaxTCReachedException();
			}
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
}
