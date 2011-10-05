
package edu.umd.cs.guitar.replayer;

import edu.umd.cs.guitar.model.wrapper.PropertyTypeWrapper;

import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.data.PropertyType;

/**
 * This is a mock object for GComponent
 * 
 * @author Ran
 *@see edu.umd.cs.guitar.model.GComponent;
 */
public class GComponentMock extends GComponent {

	/**
	 * Default constructor
	 */
	public GComponentMock() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Constructor
	 */
	public GComponentMock(String str) {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see edu.umd.cs.guitar.model.GComponent#getChildren()
	 */
	@Override
	public List<GComponent> getChildren() {
		// TODO Auto-generated method stub
		return new ArrayList<GComponent>();
	}

	/**
	 * @return "window"
	 * @see edu.umd.cs.guitar.model.GComponent#getClassVal()
	 */
	@Override
	public String getClassVal() {
		// TODO Auto-generated method stub
		return "window";
	}

	/**
	 * @return new ArrayList<GEvent>()
	 * @see edu.umd.cs.guitar.model.GComponent#getEventList()
	 */
	@Override
	public List<GEvent> getEventList() {
		// TODO Auto-generated method stub
		return new ArrayList<GEvent>();
	}

	/**
	 * @return new ArrayList<PropertyType>()
	 * @see edu.umd.cs.guitar.model.GComponent#getGUIProperties()
	 */
	@Override
	public List<PropertyType> getGUIProperties() {
		// TODO Auto-generated method stub
		return new ArrayList<PropertyType>();
	}

	/**
	 * @return new GComponentMock()
	 * @see edu.umd.cs.guitar.model.GComponent#getParent()
	 */
	@Override
	public GComponent getParent() {
		// TODO Auto-generated method stub
		return new GComponentMock();
	}

	/**
	 * @return "window"
	 * @see edu.umd.cs.guitar.model.GComponent#getTypeVal()
	 */
	@Override
	public String getTypeVal() {
		// TODO Auto-generated method stub
		return "window";
	}

	/**
	 * @return false
	 * @see edu.umd.cs.guitar.model.GComponent#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @return false
	 * @see edu.umd.cs.guitar.model.GComponent#isTerminal()
	 */
	@Override
	public boolean isTerminal() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @return "w1"
	 * @see edu.umd.cs.guitar.model.GObject#getFullID()
	 */
	/*@Override
	public String getFullID() {
		// TODO Auto-generated method stub
		return "w1";
	}*/
	
	@Override
	public String getTitle() {
		return "wl";
	}

	/**
	 * @return "window1"
	 * @see edu.umd.cs.guitar.model.GObject#getName()
	 */
	/*@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "window1";
	}*/
	
	/**
	 * Override the function getFirstChild(List<PropertyTypeWrapper> lIDProperties)
	 * @return new GComponentMock()
	 */
	@Override
	public GComponent getFirstChild(List<PropertyTypeWrapper> lIDProperties) {
		return new GComponentMock();
	 }

	@Override
	public boolean isEnable() {
		return true;
	}
}
