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
package edu.umd.cs.guitar.TestCaseGeneratorPlugins;

import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.guitar.model.data.*;



/**
 * This class implements the interface testCaseType, building stacks specific min-coverage algorythm
 * Those implementations are used when running the generate() methode of TestCaseGenerator
 * @author <a href="mailto:charlie.biger@gmail.com"> Charlie BIGER </a>
 *
 */
public class MinEventCoverage implements TestCaseType{

	
	
	public Class<?> getInputType() {
		return EFG.class;
	}
	
	public List<Object> createPreSequenceStack(List<Integer> preSequence, List<Integer> invokingEvents) {
		List<Object> list = new ArrayList<Object>();
		list.add(preSequence);
		list.add(TestCaseGeneratorTools.keepInvokingEventOnly(preSequence, invokingEvents));
		return list;
	} 	

	public List<Object> createCurrentSequenceStack(List<Integer> localInteractionEventsList , List<Integer> localInvokingEventsList, List<Object> visited) {
		List<Object> list = new ArrayList<Object>();
		List<Integer> list2 = new ArrayList<Integer>();
		for(Integer index : localInteractionEventsList){
			if(!TestCaseGeneratorTools.isTreated(visited,index)){
				list2.add(index);
			}
		}
		list.add(list2);
		TestCaseGeneratorTools.declareSequenceAsTreated(list2, visited);
		return list;
	}
	
	public List<Object> createPostSequenceStack(List<Integer> localInteractionEventsList , List<Integer> localInvokingEventsList, List<Object> visited) {
		List<Object> list = new ArrayList<Object>();
			
		for(Integer index : localInvokingEventsList){
			if(!TestCaseGeneratorTools.isTreated(visited,index)){
				List<Integer> temp = new ArrayList<Integer>();
				temp.add(index);
				list.add(temp);
				TestCaseGeneratorTools.declareSequenceAsTreated(temp, visited);
			}
		}			
		return list;
	}

	public boolean isEdge() {		
		return false;
	}
	
	


	
}
	
