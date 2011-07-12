package edu.umd.cs.guitar.ripper;
import edu.umd.cs.guitar.model.data.FullComponentType;

/**
 * The AccessibleStub class is a mock object for the class FullComponentType.  
 * A FullComponentType is made up of a component and window
 *
 */
public class FullComponentTypeStub extends FullComponentType{
	/** Component associated with object. Implemented using the mock Object ComponentTypeStub*/ 
	ComponentTypeStub component=new ComponentTypeStub();
	/** Window associated with object. Implemented using the mock Object ComponentTypeStub*/
	ComponentTypeStub window=new ComponentTypeStub();
	
	/**
     * Blank/Dummy Constructor
     */
	FullComponentTypeStub(){
		
	}
	
	/**
     * Sets the ComponentTypeStub component to whatever is passed in 
     *
     * @param c componenttype to set 
     *
     */
	public void setComponent(ComponentTypeStub c){
		component=c;
	}
	/**
     * Sets the ComponentTypeStub window to whatever is passed in 
     *
     * @param w componenttype to set 
     *
     */
	public void setWindow(ComponentTypeStub w){
		window=w;
	}
	/**
     * Returns the componenttype for the component in this object
     *
     * @return ComponentTypeStub
     */
	public ComponentTypeStub getComponent(){
		return component;
	}
	/**
     * Returns the componenttype for the window in this object
     *
     * @return ComponentTypeStub
     */
	public ComponentTypeStub getWindow(){
		return window;
	}
}