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
 *  This class implements the interface testCaseType, building stacks specific min-coverage algorythm
 *  Those implementations are used when running the generate() methode of TestCaseGenerator
 * @author <a href="mailto:charlie.biger@gmail.com"> Charlie BIGER </a>
 *
 */
public class MinEdgeCoverage implements TestCaseType {

	public boolean isEdge() {
		return true;
	}
	
	public Class<?> getInputType() {
		return EFG.class;
	}
	
	public List<Object> createPreSequenceStack(List<Integer> preSequence, List<Integer> invokingEvents) {
		List<Object> list = new ArrayList<Object>();
		list.add(preSequence);
		list.add(TestCaseGeneratorTools.keepInvokingEventOnly(preSequence, invokingEvents));		
		return list;
	}

	

	public List<Object> createCurrentSequenceStack(List<Integer> localInteractionEventsList, List<Integer> localInvokingEventsList, List<Object> visited) {
		List<Object> list = new ArrayList<Object>();
		List<Integer> nonTreatedEventsList = TestCaseGeneratorTools.keepNonTreatedEvents(localInteractionEventsList, visited);
		List<Integer> nonTreatedInvokingEvents = TestCaseGeneratorTools.keepNonTreatedEvents(localInvokingEventsList,visited);
		List<Integer> treatedInvokingEvents = TestCaseGeneratorTools.keepTreatedEvents(localInvokingEventsList,visited);
		//Line 1
		//System.out.println(nonTreatedEventsList);
		
		List<Integer> list2 = new ArrayList<Integer>();
		
		if(nonTreatedEventsList.size() > 0){
			list2.addAll(TestCaseGeneratorTools.getMinSizeSequenceToLinkGroup(nonTreatedEventsList));
			if(treatedInvokingEvents.isEmpty() == false){
				list2.add(treatedInvokingEvents.get(0));
			}
			list.add(list2);
		}
		
		//Edge invoker -> Interaction -> invoker
		
		//Block 0
		for(int i = 1 ; i < nonTreatedEventsList.size(); i++){
			List<Integer> list3 = new ArrayList<Integer>();
			list3.add(nonTreatedEventsList.get(i));
			
			if(localInvokingEventsList.isEmpty() == false ){
				list3.add(localInvokingEventsList.get(0));
			}
			list.add(list3);
		}
		
		//Block 1 -> n
		Integer var1;
		Integer var2;
		if(!treatedInvokingEvents.isEmpty()){
			var1=1;
			var2=0;
		}
		else{
			var1=0;
			var2=1;
		}
		for(int i2 = var1 ; i2 < treatedInvokingEvents.size() ; i2++){
			for(int i3 = 0; i3 < nonTreatedEventsList.size(); i3++){
				List<Integer> list4 = new ArrayList<Integer>();
				list4.add(nonTreatedEventsList.get(i3));				
				list4.add(treatedInvokingEvents.get(i2));
				list.add(list4);
			}
		}
		for(int i2 = var2 ; i2 < nonTreatedInvokingEvents.size() ; i2++){
			if(!nonTreatedEventsList.isEmpty()){	
				for(int i3 = 0; i3 < nonTreatedEventsList.size(); i3++){
					List<Integer> list4 = new ArrayList<Integer>();
					list4.add(nonTreatedEventsList.get(i3));
					if( i3 != 0){
						list4.add(localInvokingEventsList.get(i2));
					}
					list.add(list4);
				}
			}
			else{
				List<Integer> list4 = new ArrayList<Integer>();
				list4.add(nonTreatedInvokingEvents.get(i2));
				list.add(list4);
			}
		}
				
		List<Integer> treatedEventsList = new ArrayList<Integer>();
		if(localInteractionEventsList.size() > 0){
			treatedEventsList = TestCaseGeneratorTools.keepTreatedEvents(localInteractionEventsList, visited);
		}
		for(int i3 = 0 ; i3 < treatedEventsList.size(); i3++){
			list.add(TestCaseGeneratorTools.getMinSizeSequenceToLinkOneEventToGroup(treatedEventsList.get(i3), nonTreatedEventsList));
			for(int i4 = 0 ; i4 < nonTreatedInvokingEvents.size() ; i4++){
				List<Integer> list3 = new ArrayList<Integer>();
 				list3.add(treatedEventsList.get(i3));
				list3.add(nonTreatedInvokingEvents.get(i4));
				list.add(list3);
			}			
		}
		//System.out.println(list);
		return list;
	}

	public List<Object> createPostSequenceStack(List<Integer> localInteractionEventsList, List<Integer> localInvokingEventsList, List<Object> visited) {
		
		List<Object> list = new ArrayList<Object>();
		
		if(localInteractionEventsList.isEmpty() == false){
			List<Integer> nonTreatedEventsList = TestCaseGeneratorTools.keepNonTreatedEvents(localInteractionEventsList, visited);
			List<Integer> nonTreatedInvokingEventsList = TestCaseGeneratorTools.keepNonTreatedEvents(localInvokingEventsList, visited);
			List<Integer> treatedInvokingEventsList = TestCaseGeneratorTools.keepTreatedEvents(localInvokingEventsList, visited);			
			
			for(int i2  = 0 ; i2 < treatedInvokingEventsList.size() ; i2++){			
				for(int i3 = 0 ; i3 < nonTreatedEventsList.size() ; i3++){
					List<Integer> list3 = new ArrayList<Integer>();
					list.add(list3);
				}		
			}
			//System.out.println("non " +nonTreatedInvokingEventsList);
			for(int i2  = 0 ; i2 < nonTreatedInvokingEventsList.size() ; i2++){			
				List<Integer> list2 = new ArrayList<Integer>();
				list2.add(nonTreatedInvokingEventsList.get(i2));
				list.add(list2);
				for(int i3 = 0 ; i3 < nonTreatedEventsList.size()-1 ; i3++){
					List<Integer> list3 = new ArrayList<Integer>();
					list.add(list3);
				}		
			}
			TestCaseGeneratorTools.declareAllEdgeOfEventsAsTreated(localInteractionEventsList, visited);
			TestCaseGeneratorTools.declareAllEdgeOfEventsAsTreated(nonTreatedInvokingEventsList, visited);
		}
		else{
			List<Integer> nonTreatedInvokingEventsList = TestCaseGeneratorTools.keepNonTreatedEvents(localInvokingEventsList, visited);
			for(int i3 = 0 ; i3 < nonTreatedInvokingEventsList.size() ; i3++){
				List<Integer> list3 = new ArrayList<Integer>();
				list3.add(nonTreatedInvokingEventsList.get(i3));
				list.add(list3);
			}
			
			TestCaseGeneratorTools.declareAllEdgeOfEventsAsTreated(nonTreatedInvokingEventsList, visited);
		}
		//System.out.println(list);
		return list;
	}
}

	


