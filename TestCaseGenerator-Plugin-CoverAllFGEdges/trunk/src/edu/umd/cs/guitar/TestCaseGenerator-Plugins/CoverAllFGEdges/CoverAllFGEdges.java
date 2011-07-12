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
import edu.umd.cs.guitar.model.data.RowType;

import edu.umd.cs.guitar.model.data.EFG;

/**
 * CoverAllGFEdges algorithm. Cover all the edges of the EFG
 * @author <a href="mailto:charlie.biger@gmail.com"> Charlie BIGER </a>
 *
 */
public class CoverAllFGEdges {
	
	EFG efg;
	List<String> listEdges = new ArrayList<String>();
	
	List<Object> listPath = new ArrayList<Object>();
	
	
	List<Integer> invokingEvents = new ArrayList<Integer>();
	List<Integer> path = new ArrayList<Integer>();
	List<Integer> treatedInvokingEvents = new ArrayList<Integer>();
	private String tstFileNamePrefix;
	private String tstFileNameSuffix;
	public CoverAllFGEdges(){
		
	}
	
	public void generate(Object obj, String testFileNamePrefix, String testFileNameSuffix, Integer limitTestCaseNumber) throws Exception{

		this.efg = (EFG) obj;
		this.invokingEvents = TestCaseGeneratorTools.getInvokingEvents(efg);
		this.tstFileNamePrefix = testFileNamePrefix;
		this.tstFileNameSuffix = testFileNameSuffix;
		List<RowType> eventMatrix = efg.getEventGraph().getRow();
		Integer matrixSize = eventMatrix.size();
		
		Integer tstNumber = 0;
		
		findAllPath(TestCaseGeneratorTools.getOneRootEvent(efg, invokingEvents));

		for(int i1 = 0 ; i1 < matrixSize ; i1++){
			List<Integer> row = eventMatrix.get(i1).getE();
		
			for(int i2 = 0 ; i2 < matrixSize ; i2 ++){
			
				if(row.get(i2) > 0 && i1 != i2){
					Integer index = listEdges.indexOf(Integer.toString(i1)+":"+Integer.toString(i2));
					if(index != -1){	
						List<Integer> sequence = findShorterPath(i1,i2);
						sequence.add(i1);
						sequence.add(i2);
						TestCaseGeneratorTools.writeTestCase(sequence, efg, tstNumber, tstFileNamePrefix, tstFileNameSuffix);
						tstNumber ++ ;
					}
				}
			}
		}

	}			
			
			
		
	
	private void findAllPath(List<Integer> preSequence) {
		
		List<Integer> localInvokingEvents = TestCaseGeneratorTools.getLocalPostInvokingList(preSequence, invokingEvents, efg);
		List<Integer> localInteractionEvents = TestCaseGeneratorTools.getLocalInteractionEventsList(preSequence, invokingEvents, efg);
		List<Integer> path = TestCaseGeneratorTools.keepInvokingEventOnly(preSequence, invokingEvents);
		for(Integer eventId1 : localInteractionEvents ){
			for(Integer eventId2 : localInteractionEvents){
					declare(eventId1 , eventId2 , path);
					declare(eventId2 , eventId1, path);
				
			}			
		}
		for(Integer eventId1 : localInteractionEvents){
			for(Integer eventId2 : localInvokingEvents){
					declare(eventId1 , eventId2 , path);
			}
		}
		if(path.size()>0){
			for(Integer eventId1 : localInteractionEvents){
				Integer save = TestCaseGeneratorTools.getLastEvent(preSequence);
				path.remove(path.size()-1);
				if(save != eventId1){
					declare(save, eventId1 , path);
				}
				path.add(save); 
			}
			
			for(Integer eventId1 : localInvokingEvents){
				Integer save = TestCaseGeneratorTools.getLastEvent(preSequence);
				path.remove(path.size()-1);
				if(save != eventId1){
					declare(save, eventId1 , path);
				}
				path.add(save);
			}	
		}
		for(Integer eventId : localInvokingEvents){
			if(treatedInvokingEvents.indexOf(eventId) == -1){
				path.add(eventId);
				treatedInvokingEvents.add(eventId);
				findAllPath(path);
				path.remove(path.size()-1);
			}
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	private void declare(Integer eventId1, Integer eventId2, List<Integer> path) {
		Integer index = listEdges.indexOf(Integer.toString(eventId1)+":"+Integer.toString(eventId2));
		List<Integer> path1 = new ArrayList<Integer>();
		for(Integer eventId : path){
			path1.add(eventId);
		}
		if(index == -1){
			listEdges.add(Integer.toString(eventId1)+":"+Integer.toString(eventId2));			
			listPath.add(path1);
		}
		else{
			if( ((List<Integer>)listPath.get(index)).size() > path.size()){
				listPath.set(index, path1);
			}
		}	
	}
	@SuppressWarnings("unchecked")
	private List<Integer> findShorterPath(Integer x, Integer y) {
		return (List<Integer>)listPath.get(listEdges.indexOf(Integer.toString(x)+":"+Integer.toString(y)));
	}	
}

