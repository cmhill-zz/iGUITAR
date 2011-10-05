package edu.umd.cs.guitar.testcase.plugin;

import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.EventGraphType;
import edu.umd.cs.guitar.model.data.EventsType;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.model.data.RowType;

/**
 * This class is the mock of EFG class. 
 * <br/> <br/>
 * 
 * Instead of reading EFG file from disk, this mock class defines the events and
 * EventGraph: <br/>
 * events: e0, e1, e2 <br/>
 * eventGraph: <br/>
 * 1 1 0 <br/>
 * 1 1 1 <br/>
 * 0 1 1 <br/> 
 * 
 * <br/> <br/>
 * 
 * @author Yuening Hu
 */	

public class EFGStub extends EFG{
	
	
	/**
	 * This is the constructor method. Three events and an eventGraph are created.
	 */		
	public EFGStub(){
		eventGraph = new EventGraphType();
		events = new EventsType();
		
		ArrayList<Integer> e1 = new ArrayList<Integer>();
		e1.add(1);
		e1.add(1);
		e1.add(0);
		RowType row1 = new RowType();
		row1.setE(e1);
		
		ArrayList<Integer> e2 = new ArrayList<Integer>();
		e2.add(1);
		e2.add(1);
		e2.add(1);
		RowType row2 = new RowType();
		row2.setE(e2);		

		ArrayList<Integer> e3 = new ArrayList<Integer>();
		e3.add(0);
		e3.add(1);
		e3.add(1);
		RowType row3 = new RowType();
		row3.setE(e3);
		
		ArrayList<RowType> rowlist = new ArrayList<RowType>();
		rowlist.add(row1);
		rowlist.add(row2);
		rowlist.add(row3);
		
		eventGraph.setRow(rowlist);
		
		EventType event1 = new EventType();
		event1.setEventId("e0");
		event1.setInitial(true);
		EventType event2 = new EventType();
		event2.setEventId("e1");	
		event2.setInitial(true);
		EventType event3 = new EventType();
		event3.setEventId("e2");	
		event3.setInitial(true);
		ArrayList<EventType> eList = new ArrayList<EventType>();
		eList.add(event1);
		eList.add(event2);
		eList.add(event3);
		events.setEvent(eList);		
	}
}
