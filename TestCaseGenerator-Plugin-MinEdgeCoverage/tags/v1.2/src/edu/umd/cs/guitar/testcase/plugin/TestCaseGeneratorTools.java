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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.*;

/**
 * Provides tools use in test case generators
 * 
 *@author <a href="mailto:charlie.biger@gmail.com"> Charlie BIGER </a>
 *
 */
public class TestCaseGeneratorTools {
	
	@SuppressWarnings("unchecked")
	public static void declareAllEdgeOfEventsAsTreated(List<Integer> events, List<Object> treatmentMap){
		for(Integer eventId : events){
			List<Integer> row = (List<Integer>) treatmentMap.get(eventId);
			for(int i = 0 ; i < row.size(); i++){
				if(row.get(i) == 0){
					row.set(i, 1);
				}
			}
		}
	}
	
	public static List<Integer> getMinSizeSequenceToLinkOneEventToGroup(Integer eventId , List<Integer> groupEvents){
		List<Integer> list = new ArrayList<Integer>();
		for(Integer index : groupEvents){
			list.add(eventId);
			list.add(index);
		}
		list.add(eventId);
		return list;
	}
	
	public static List<Integer> getMinSizeSequenceToLinkGroup(List<Integer> groupEvents){
		Integer size = groupEvents.size();
		//initialisation of the matrix
		List<Integer> treated = new ArrayList<Integer>();
		for(int i1 = 0 ; i1 < (size-1) * size ; i1++){
			treated.add(0);
		}
		
		//Awaited number of edges
		Integer awaitedNumber= size * (size-1);
	
		//Creation of the first sequence, all the edge are covered : 
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(groupEvents.get(0));
		while(list.size() <= awaitedNumber){
			Integer lastEvent = groupEvents.indexOf(list.get(list.size()-1));
			for(int i3 = lastEvent * (size -1) ; i3 < (lastEvent+1) * (size -1)  ; i3++){
				if(treated.get(i3) == 0){
					list.add(groupEvents.get( (lastEvent + (i3 % (size-1) + 1)) % size));
					treated.set(i3, 1);
					break;
				}
			}
		}			
		return list;
	}	
	
	/**
	 * Delete events already treated from the input sequence
	 * @param sequence
	 * @param treatmentMap
	 * @return cleaned sequence
	 */
	@SuppressWarnings("unchecked")
	public static List<Integer>  keepNonTreatedEvents(List<Integer> sequence, List<Object> treatmentMap){
		if(treatmentMap.size() == 1){
			List<Integer> seq = new ArrayList<Integer>();
			for(Integer index : sequence){
				if(isTreated(treatmentMap,index)==false){
					seq.add(index);
				}
			}
			return seq;
		}
		else{
			List<Integer> seq = new ArrayList<Integer>();
			for(int i = 0 ; i < sequence.size() ; i++){
				List<Integer> row = (List<Integer>)treatmentMap.get(sequence.get(i));
				boolean treatedOnce = false;
				for(int i2 = 0 ; i2 < row.size() ; i2++){
					if(row.get(i2) == 1 ){
						treatedOnce = true;
					}
				}
				if(treatedOnce == false){
					seq.add(sequence.get(i));
				}
			}
			return seq;
		}		
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<Integer>  keepTreatedEvents(List<Integer> sequence, List<Object> treatmentMap){
		if(treatmentMap.size() == 1){
			List<Integer> seq = new ArrayList<Integer>();
			for(Integer index : sequence){
				if(isTreated(treatmentMap,index)== true){
					seq.add(index);
				}
			}
			return seq;
		}
		else{
			List<Integer> seq = new ArrayList<Integer>();
			for(int i = 0 ; i < sequence.size() ; i++){
				List<Integer> row = (List<Integer>)treatmentMap.get(sequence.get(i));
				boolean treatedOnce = false;
				for(int i2 = 0 ; i2 < row.size() ; i2++){
					if(row.get(i2) == 1 ){
						treatedOnce = true;
					}
				}
				if(treatedOnce == true){
					seq.add(sequence.get(i));
				}
			}
			return seq;
		}		
	} 
	/**
	 * Declare all events from a sequence as "treated"
	 * @param sequence
	 * @param treatmentMap
	 */
	@SuppressWarnings("unchecked")
	public static void declareSequenceAsTreated(List<Integer> sequence, List<Object> treatmentMap){
		if(treatmentMap.size() == 1){
			for(Integer index : sequence){
				declareAsTreated(treatmentMap,index);
			}		
		}
		else{
			for(int i = 0 ; i < sequence.size()-1 ; i++){
				declareAsTreated(treatmentMap, sequence.get(i), sequence.get(i+1));
			}
		}
	}
	/**
	 * Create, initialise and return a map describing events or edges already visited
	 * @param efg
	 * @param dimension
	 * @return treatmentMap
	 */
	public static List<Object> createTreatmentMap( EFG efg , boolean edge){
		List<Object> treatmentMap = new ArrayList<Object>();
		
		if(edge==false){
			List<Integer> list = new ArrayList<Integer>();
			Integer numberOfEvents = efg.getEvents().getEvent().size();
			for(int i = 0 ; i < numberOfEvents ; i++){
				list.add(0);
			}
			treatmentMap.add(list);
		}
		
		else{
			List<RowType> eventMatrix = efg.getEventGraph().getRow();
			for(int i1 = 0 ; i1 < eventMatrix.size(); i1++){
				List<Integer> list = new ArrayList<Integer>();
				for(int i2 = 0 ; i2 < eventMatrix.size() ; i2 ++ ){
					if(eventMatrix.get(i1).getE().get(i2) > 0){
						list.add(0);
					}
					else{
						list.add(2);
					}
				}
				treatmentMap.add(list);
			}
		}
		return treatmentMap;		
	}
	
	/**
	 * return false if an event has not been already treated, return true either
	 * @param treatmentMap
	 * @param x
	 * @return
	 */
	@SuppressWarnings("unchecked")
	static boolean isTreated (List<Object> treatmentMap, Integer x ){
		if(treatmentMap.size() == 1){
			List<Integer> list = (List<Integer>) treatmentMap.get(0);
			if(list.get(x) == 0){
				return false;
			}
			else{
				return true;
			}
		}
		else{
			List<Integer> list = (List<Integer>) treatmentMap.get(x);
			for(Integer num : list){
				if(num == 1){
					return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * return false if an edge has not been already treated, return true either
	 * @param treatmentMap
	 * @param x
	 * @param y
	 * @return
	 */
	@SuppressWarnings("unchecked")
	static boolean isTreated(List<Object> treatmentMap, Integer x, Integer y ){
		List<Integer> list = (List<Integer>) treatmentMap.get(x); 	
		if(list.get(y) == 0){
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * declare an event as treated in the treatment map
	 * @param treatmentMap
	 * @param x
	 */
	@SuppressWarnings("unchecked")
	static void declareAsTreated(List<Object> treatmentMap , Integer x){
		((List<Integer>) treatmentMap.get(0)).set(x,1);
	}
	
	
	/**
	 * return an edge as treated in the treatment map
	 * @param treatmentMap
	 * @param x
	 * @param y
	 */
	@SuppressWarnings("unchecked")
	static void declareAsTreated(List<Object> treatmentMap , Integer x, Integer y){
		((List<Integer>) treatmentMap.get(x)).set(y,1);
	}
	
	/**
	 * Return the list of terminal events in the efg
	 * @param efg
	 * @return
	 */
	public static List<Integer> getTerminalEvents(EFG efg){
		List<Integer> terminalEvents = new ArrayList<Integer>();
		List<RowType> eventMatrix = efg.getEventGraph().getRow();
		for(RowType row : eventMatrix){
			for(int i = 0 ; i < eventMatrix.size() ; i++){
				if(row.getE().get(i) == 3 && terminalEvents.indexOf(i) == -1){
					terminalEvents.add(i);
				}
			}
		}
		return terminalEvents;
		
	}
	/**
	 * Find the max between a, b and c
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public static Integer getMaxStackSize(Integer a, Integer b){
		Integer max;
		if(a>=b){
			max = a;
		}
		else{
			max = b;
		}
		if(max<0){
			return 0;
		}
		else{
			return max;
		}
	}
	
	
	
	/**
	 * Find if the preSequence can be linked with a sequence available in currentStack. Return the index of the sequence, or -1 if none is found
	 * @param preSequence
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Integer getLinkedSequenceIndex(List<Integer> preSequence, List<Object> list){		
		for(int i = 0 ; i < list.size(); i++){
			List<Integer> currentList = (List<Integer>) list.get(i);
			Integer lastEventInPreSequence = getLastEvent(preSequence);
			Integer firtEventInCurrentList = currentList.get(0);
			if(firtEventInCurrentList == lastEventInPreSequence){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Get the index of the last event in the current sequence
	 * @param path
	 * @return the last event index
	 */
	public static Integer getLastEvent(List<Integer> sequence){
		return sequence.get(sequence.size()-1);
	}
	
	/**
	 * Remove all non-invoking events from the current sequence
	 * @param fullsequence
	 * @param invokingEvents
	 * @return the sequence without System Interaction Event
	 */
	public static List<Integer> keepInvokingEventOnly(List<Integer> fullSequence, List<Integer> invokingEvents){
		List<Integer> cleanSequence = new ArrayList<Integer>();
		for(int i = 0 ; i < fullSequence.size() ; i++){
			Integer eventIndex = fullSequence.get(i);
			if(invokingEvents.indexOf(eventIndex) != -1){
				cleanSequence.add(eventIndex);
			}
		}
		return cleanSequence;
	}
		
	/**
	 * Get the list of invoking event reachable from the last event in the preSequence
	 * @param preSequence
	 * @param invokingEvents
	 * @param efg
	 * @return the list of invoking event
	 */
	public static List<Integer> getLocalPostInvokingList(List<Integer> preSequence, List<Integer> invokingEvents ,EFG efg){
		List<Integer> localPostInvokingList = new ArrayList<Integer>();
		Integer lastInvokingEvent = getLastEvent(preSequence);
		List<RowType> eventMatrix = efg.getEventGraph().getRow();
		System.out.println(eventMatrix.get(lastInvokingEvent).getE());
		for(int i=0; i<eventMatrix.size() ; i++){
			
			Integer num = eventMatrix.get(lastInvokingEvent).getE().get(i);
			if(num == 2 || num == 3 ){
				localPostInvokingList.add(i);
			}			
		}
		return localPostInvokingList;
	}
	
	/**
	 * Get the list of System Interaction Event reachable from the last event in the preSequence 
	 * @param preSequence
	 * @param invokingEvents
	 * @param efg
	 * @return list of System Interaction Events
	 */
	public static List<Integer> getLocalInteractionEventsList(List<Integer> preSequence, List<Integer> invokingEvents ,EFG efg){
		List<Integer> localInteractionEventsList = new ArrayList<Integer>();
		Integer lastInvokingEvent = getLastEvent(preSequence);
		List<RowType> eventMatrix = efg.getEventGraph().getRow();
		for(int i=0; i<eventMatrix.size() ; i++){
			if(eventMatrix.get(lastInvokingEvent).getE().get(i) == 1 ){
				localInteractionEventsList.add(i);
			}			
		}
		return localInteractionEventsList;
	}
	
	
	/**
	 * Get one of the event from the root window(the first in the list of event in the EFG
	 * @param efg
	 * @return the index of the first root event
	 */
	public static List<Integer> getOneRootEvent(EFG efg, List<Integer> invokingEvents){
		List<Integer> firstSequence = new ArrayList<Integer>();
		List<Integer> firstSequenceOnlyInvokers = new ArrayList<Integer>();
		List<EventType> listEvent = efg.getEvents().getEvent();
		boolean find = false;
		for(int i = 0 ; i < listEvent.size(); i++){
			if( listEvent.get(i).getEventId().indexOf("r") != -1 ){
				if( invokingEvents.indexOf(i) == -1){
					firstSequence.add(i);
					find = true;
					break;
				}
				firstSequenceOnlyInvokers.add(i);
			}
		}
		if(find == false){
			System.out.println("No System Interaction root event detected in this efg");
			return firstSequenceOnlyInvokers;
		}
		return firstSequence;	
	}
	
	/**
	 * Get the list of all invoking event in the EFG
	 * @param efg
	 * @return the list of invoking event
	 */
	public static List<Integer> getInvokingEvents(EFG efg){
		List<Integer> invokingEvent = new ArrayList<Integer>();
		List<RowType> eventMatrix = efg.getEventGraph().getRow();
		for(int i1 = 0 ; i1 < eventMatrix.size() ; i1++ ){
			List<Integer> row = eventMatrix.get(i1).getE();
			for(int i2 = 0 ; i2 < eventMatrix.size(); i2++){
				if(row.get(i2) > 1 && invokingEvent.indexOf(i2) == -1){
					invokingEvent.add(i2);
				}
			}
		}
		return invokingEvent;		
	}
	
	/**
	 * Write the test case represented by the sequence
	 * @param sequence
	 * @param efg
	 * @param testCaseNumber
	 * @param tstFileNameSuffix 
	 * @param tstFileNamePrefix 
	 * @throws Exception
	 */
	public static void writeTestCase(List<Integer> sequence, EFG efg, Integer testCaseNumber, String tstFileNamePrefix, String tstFileNameSuffix) throws Exception{
		try {
		     ObjectFactory factory = new ObjectFactory();
			 TestCase testCase = factory.createTestCase();
			
			 String eventId;
			 for(int i=0; i<sequence.size()-1;i++){
			 		StepType reachingStep = factory.createStepType();
			 		eventId = ((EventType) ((EventsType) efg.getEvents()).getEvent().get(sequence.get(i))).getEventId();
			 		reachingStep.setReachingStep(true);
			 		reachingStep.setEventId(eventId);
			  		testCase.getStep().add(reachingStep);
			  }
			 if(sequence.size() > 0){
				 eventId=((EventType) ((EventsType) efg.getEvents()).getEvent().get(sequence.get(sequence.size()-1))).getEventId();
				 StepType step = factory.createStepType();
				 step.setReachingStep(false);
				 step.setEventId(eventId);
				 testCase.getStep().add(step);
				 
				 String fileName = tstFileNamePrefix +  testCaseNumber + tstFileNameSuffix;
				 IO.writeObjToFile(testCase , fileName);
			 }
		} catch (Exception e) {
			 e.printStackTrace();
		     throw(e);
		}	
	}

}
