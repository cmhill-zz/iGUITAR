package edu.umd.cs.guitar.ripper;

import java.util.List;

import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.model.*;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.PropertyType;
/**
 * GComponentStub is a Mock Object Class for GComponent. This class
 * hold the GUITAR represenation of a compoent
 */
public class GComponentStub extends JFCXComponent{

	/** Component Type */
	String Type;
	/** Name of Window */
	String Name;
	/** Window ComponentType using the mock object ComponentTypeStub*/
	ComponentTypeStub ct= new ComponentTypeStub();
	
	/** Is component terminal*/
	boolean terminal=false;
	
	/** Dummy Constructor...initializes variables*/
	public GComponentStub() {
		// TODO Auto-generated constructor stub
		super(null);
		Name="";
		Type="";
	}
	/**
     * Returns component name
     * 
     * @return "Name"
     */
	public String getName(){
		return "Name";
	}
	/**
     * Returns properties associated with the component in the form of a ComponentType
     * 
     * @return ComponentType
     */
	public ComponentType extractProperties(){
		return ct;
	}
	/**
     * Returns Children comonents
     * 
     * @return null
     */
	@Override
	public List<GComponent> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
     * Returns Class Value
     * 
     * @return null
     */
	@Override
	public String getClassVal() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
     * Returns component events
     * 
     * @return null
     */
	@Override
	public List<GEvent> getEventList() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
     * Returns list of component properties
     * 
     * @return null
     */
	@Override
	public List<PropertyType> getGUIProperties() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
     * Returns component parent
     * 
     * @return this
     */
	@Override
	public GComponent getParent() {
		// TODO Auto-generated method stub
		return this;
	}
	/**
     * Returns component type
     * 
     * @return String
     */
	@Override
	public String getTypeVal() {
		// TODO Auto-generated method stub
		return Type;
	}
	/**
     * Sets component type
     * @param t String component type
     * 
     */
	public void setTypeVal(String t) {
		// TODO Auto-generated method stub
		Type=t;
	}
	/**
     * Returns if component has children
     * 
     * @return false
     */
	@Override
	public boolean hasChildren() {
		// TODO Auto-generated method stub
		return false;
	}
	/**
     * Returns if component is terminal
     * 
     * @return boolean
     */
	@Override
	public boolean isTerminal() {
		// TODO Auto-generated method stub
		return terminal;
	}
	/**
     * Sets if component is terminal
     * @param t bool if component is terminal
     */
	public void setTerminal(boolean t) {
		// TODO Auto-generated method stub
		terminal =t;
	}
	/**
     * Returns component ID
     * 
     * @return null
     */
	public String getFullID() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
     * Sets component type
     * @param c ComponentType value to set in component
     * 
     */
	public void setComponentType(ComponentType c){
		ct=(ComponentTypeStub)c;
	}
	
	public boolean isEnable(){
	return false;//real value needs to be put here
	}
	
	//New
	//Returns "Name" since this is what was used for name above
	public String getTitle(){
	return "Name";//need to return actual value here
	}
}
