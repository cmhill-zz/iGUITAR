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
 * Instead of reading EFG file from disk, this mock class defines an empty EventGraph and events.
 * <br/> <br/> 
 * @author Yuening Hu
 */	
public class EFGVoidStub extends EFG{
	
	
	/**
	 * This is the constructor method. Empty EventGraph and events are created.
	 */	
	public EFGVoidStub(){
		eventGraph = new EventGraphType();
		events = new EventsType();
	}

}
