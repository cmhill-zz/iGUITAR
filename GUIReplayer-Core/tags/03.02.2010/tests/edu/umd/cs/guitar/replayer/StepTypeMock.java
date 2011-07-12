package edu.umd.cs.guitar.replayer;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.StepType;
/**
 * This is the mock object for StepType
 */
public class StepTypeMock extends StepType {
	@XmlElement(name = "EventId", required = true)
    protected String eventId;
    @XmlElement(name = "ReachingStep")
    protected boolean reachingStep;
    @XmlElement(name = "Parameter")
    protected List<String> parameter;
    @XmlElement(name = "Optional")
    protected AttributesType optional;
    @XmlElement(name = "GUIStructure")
    protected GUIStructure guiStructure;
    /**
     * Default constructor
     */
    public StepTypeMock(){
    	init();
    	eventId = "e0";
    	parameter =new ArrayList<String>();
    }
    /**
     * constructor
     */
    public StepTypeMock(String st){
    	init();
    	eventId = st;
    //	parameter =new ArrayList<String>();
    	
    	
    }
    /**
     *initialize
     */
	private void init() {
		
		reachingStep=false;
		parameter =null;
		optional = null;
		guiStructure = null;
		
		
	}
	/**
     * @return eventId
     */
	public String getEventId() {
        return eventId;
    }
	/**
     * @return reachingStep
     */
	public boolean isReachingStep() {
        return reachingStep;
    }
	/**
     * @return parameter
     */
	 public List<String> getParameter() {
	        
	        return parameter;
	    }
	 /**
	     * @return optional
	     */
	 public AttributesType getOptional() {
	        return optional;
	    }
	 
	 /**
	     * @return guiStructure
	     */
	  public GUIStructure getGUIStructure() {
	        return guiStructure;
	    }
}
