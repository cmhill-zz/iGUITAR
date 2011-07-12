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
 * Main frame of all test case generators. Used to manage stack for MinCoverageAlgorithm.
 * 
 * @author <a href="mailto:charlie.biger@gmail.com"> Charlie BIGER </a>
 *
 */
public class TestCaseGenerator {

	EFG efg = new EFG();
	List<Integer> invokingEvents = new ArrayList<Integer>();
	List<Integer> terminalEvents = new ArrayList<Integer>();
	List<Object> visited = new ArrayList<Object>();
	List<Integer> treatedInvokingEvents = new ArrayList<Integer>();
	TestCaseType testCaseType;
	Integer testCaseNumber = 0;
	Integer limitTestCaseNb;
	String tstFileNamePrefix;
	String tstFileNameSuffix;
	String converterType;
	
	public TestCaseGenerator(){
		
	}
	/**
	 * Generate the bunch of test case related to the input EFG, with the type of convertion defines by converter
	 * @param obj
	 * @param converter
	 * @throws Exception
	 */
	public void generate(Object obj, TestCaseType converter , String testFileNamePrefix, String testFileNameSuffix, Integer limitTestCaseNumber) throws Exception{
		try{
 			this.efg = (EFG) obj;
			this.invokingEvents = TestCaseGeneratorTools.getInvokingEvents(efg);
			this.terminalEvents = TestCaseGeneratorTools.getTerminalEvents(efg);
			this.testCaseType = converter;
			this.visited = TestCaseGeneratorTools.createTreatmentMap(efg,converter.isEdge());
			this.limitTestCaseNb = limitTestCaseNumber;
			this.tstFileNamePrefix = testFileNamePrefix;
			this.tstFileNameSuffix = testFileNameSuffix;
			this.converterType = converter.getClass().getName();
				
			
			List<Integer> firstSequence = TestCaseGeneratorTools.getOneRootEvent(efg, invokingEvents);
			System.out.println(firstSequence);
			findSequence(firstSequence);
			
		    System.out.println(invokingEvents);
		    System.out.println(testCaseNumber);

			
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private void findSequence(List<Integer> preSequence) throws Exception{
		
		try{
			
			List<Integer> localInteractionEventsList = TestCaseGeneratorTools.getLocalInteractionEventsList(preSequence, invokingEvents, efg);
			List<Integer> localInvokingEventsList = TestCaseGeneratorTools.getLocalPostInvokingList(preSequence, invokingEvents, efg);
			System.out.println(preSequence);
			System.out.println(localInteractionEventsList);
			System.out.println(localInvokingEventsList);
			//Definition of the 3 stacks
			
			List<Object> preSequenceStack = testCaseType.createPreSequenceStack(preSequence, invokingEvents);
			List<Object> currentSequenceStack = testCaseType.createCurrentSequenceStack(localInteractionEventsList, localInvokingEventsList, visited);
			List<Object> postSequenceStack = testCaseType.createPostSequenceStack(localInteractionEventsList, localInvokingEventsList ,   visited);
			//firstCase : the only event in the sequence is a interactionn event -> avoid repetition
			if(preSequence.size() == 1 && invokingEvents.indexOf(preSequence.get(0)) == -1){
				preSequence.clear();
			}
						
			//Building sequence from the 3 stacks
			Integer preStLastIndex= preSequenceStack.size() - 1;
			Integer currentStLastIndex= currentSequenceStack.size() - 1;
			Integer postStLastIndex = postSequenceStack.size() - 1;
			Integer maxStackSize = TestCaseGeneratorTools.getMaxStackSize( currentStLastIndex, postStLastIndex);			
			List<Integer> sequence = new ArrayList<Integer>();
			System.out.println(preSequenceStack);
			System.out.println(currentSequenceStack);
			System.out.println(postSequenceStack);
			Integer step = 0;
			boolean ind1;
			boolean ind2;
			while(step <= maxStackSize){
				
				ind1=false;
				ind2=false;
				//PreStack
				if(step == 0){ sequence.addAll((List<Integer>)preSequenceStack.get(step)) ; }
				else{ sequence.addAll((List<Integer>)preSequenceStack.get(preStLastIndex)); }
				
				//CurrentStack
				if(step <= currentStLastIndex){ 
					sequence.addAll((List<Integer>)currentSequenceStack.get(step));
					ind1=true;
				}
				
				//PostStack
				
				if(step <= postStLastIndex && ((List<Integer>) postSequenceStack.get(step)).isEmpty() == false){
					List<Integer> postSequence = (List<Integer>)postSequenceStack.get(step);
					sequence.addAll(postSequence);
					findSequence(sequence);
					ind2=true;
				}
				if( ind2 == false && (ind1==true  || (ind1==false && step == 0 && converterType.equals("edu.umd.cs.guitar.TestCaseGeneratorPlugins.CoverAllEvents") == false))){
					TestCaseGeneratorTools.writeTestCase(sequence , efg , testCaseNumber, tstFileNamePrefix, tstFileNameSuffix);
					testCaseNumber++;
				}
				
				sequence.clear();
				step++;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
	}
	
	
	
}
