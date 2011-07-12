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
package edu.umd.cs.guitar.testcase.plugin;

import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.guitar.model.data.EFG;
/**
 * CoverAllEvents. Cover all event of the EFG
 * 
 * @author <a href="mailto:charlie.biger@gmail.com"> Charlie BIGER </a>
 *
 */
public class CoverAllEvents implements TestCaseType{

	public boolean isEdge(){
		return false;
	}

	public Class<?> getInputType() {
		return EFG.class;
	}

	public List<Object> createPreSequenceStack(List<Integer> preSequence, List<Integer> invokingEvents) {
		List<Object> list = new ArrayList<Object>();
		list.add(TestCaseGeneratorTools.keepInvokingEventOnly(preSequence, invokingEvents));
		return list;
	}
	
	public List<Object> createCurrentSequenceStack(List<Integer> localInteractionEventsList, List<Integer> localInvokingEventsList, List<Object> visited) {
		List<Object> list = new ArrayList<Object>();
		
		for(Integer eventId : TestCaseGeneratorTools.keepNonTreatedEvents(localInteractionEventsList, visited)){
			List<Integer> list2 = new ArrayList<Integer>();
			list2.add(eventId);	
			list.add(list2);	
		}
		for(Integer eventId : TestCaseGeneratorTools.keepNonTreatedEvents(localInvokingEventsList, visited)){
			List<Integer> list2 = new ArrayList<Integer>();
			list2.add(eventId);
			list.add(list2);
		}
		return list;
	}

	public List<Object> createPostSequenceStack(List<Integer> localInteractionEventsList, List<Integer> localInvokingEventsList, List<Object> visited) {
		List<Object> list = new ArrayList<Object>();
		
		for(Integer eventId : TestCaseGeneratorTools.keepNonTreatedEvents(localInteractionEventsList,visited)){
			List<Integer> list2 = new ArrayList<Integer>();
			list.add(list2);
			TestCaseGeneratorTools.declareAsTreated(visited, eventId);
		}
		for(Integer eventId : TestCaseGeneratorTools.keepNonTreatedEvents(localInvokingEventsList,visited)){
			List<Integer> list2 = new ArrayList<Integer>();
			list.add(list2);
		}
		for(Integer eventId : TestCaseGeneratorTools.keepNonTreatedEvents(localInvokingEventsList, visited)){
			List<Integer> list2 = new ArrayList<Integer>();
			list2.add(eventId);
			TestCaseGeneratorTools.declareAsTreated(visited, eventId);
			list.add(list2);
		}
		return list;
	}

	

}
