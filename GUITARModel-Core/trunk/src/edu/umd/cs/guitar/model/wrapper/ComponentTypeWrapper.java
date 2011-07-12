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
package edu.umd.cs.guitar.model.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GHashcodeGenerator;
import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.ContentsType;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.model.data.PropertyType;

/**
 * Adapter class to process ComponentType.
 * 
 * <p>
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 * 
 */
public class ComponentTypeWrapper {

	ComponentType dComponentType;

	// -------------------------------
	// Parsed structured data
	// -------------------------------

	/**
	 * Container window
	 */
	GUITypeWrapper window;

	/**
	 * List of window invoked by the component
	 */
	List<GUITypeWrapper> invokedWindows;

	/**
	 * @return the invokedWindows
	 */
	public List<GUITypeWrapper> getInvokedWindows() {
		return invokedWindows;
	}

	/**
	 * Parent node
	 */
	ComponentTypeWrapper parent;

	/**
	 * Children nodes
	 */
	List<ComponentTypeWrapper> children;

	/**
	 * 
	 * A lazy method to parse data
	 * 
	 * <p>
	 * 
	 * @param dGUIStructure
	 * @param wGUIStructure
	 */
	public void parseData(GUIStructure dGUIStructure,
			GUIStructureWrapper wGUIStructure) {

		List<String> sInvokeList = getValueListByName(GUITARConstants.INVOKELIST_TAG_NAME);

		// Parse invoked windows
		if (sInvokeList != null) {

			this.invokedWindows = new ArrayList<GUITypeWrapper>();

			for (String invokedWinTitle : sInvokeList) {
				GUITypeWrapper invokedWin = wGUIStructure
						.getGUIByTitle(invokedWinTitle);
				if (invokedWin == null)
					continue;

				if (invokedWin.equals(this.getWindow().getTitle()))
					continue;

				if (invokedWin != null) {
					invokedWindows.add(invokedWin);
					invokedWin.setInvoker(this);

					// Use an invoker list instead of a single invoker
					invokedWin.getInvokerList().add(this);

					List<GUITypeWrapper> lGUIs = wGUIStructure.getGUIs();
					if (!lGUIs.contains(invokedWin)) {
						wGUIStructure.getGUIs().add(invokedWin);
						invokedWin.parseData(dGUIStructure, wGUIStructure);
					}
				}
			}
		}

		// Create a children list
		if (dComponentType instanceof ContainerType) {

			List<ComponentType> dChildrenList = ((ContainerType) dComponentType)
					.getContents().getWidgetOrContainer();
			if (dChildrenList != null) {
				this.children = new ArrayList<ComponentTypeWrapper>();

				for (ComponentType dChild : dChildrenList) {
					ComponentTypeWrapper wChild = new ComponentTypeWrapper(
							dChild);
					wChild.setParent(this);
					wChild.setWindow(this.window);
					wChild.parseData(dGUIStructure, wGUIStructure);
					children.add(wChild);
				}

			}

		}
	}

	/**
	 * Parse Data without invoke list
	 */
	public void parseData() {
		// Create a children list
		if (dComponentType instanceof ContainerType) {

			List<ComponentType> dChildrenList = ((ContainerType) dComponentType)
					.getContents().getWidgetOrContainer();
			if (dChildrenList != null) {
				this.children = new ArrayList<ComponentTypeWrapper>();

				for (ComponentType dChild : dChildrenList) {
					ComponentTypeWrapper wChild = new ComponentTypeWrapper(
							dChild);
					wChild.setParent(this);
					wChild.setWindow(this.window);
					wChild.parseData();
					children.add(wChild);
				}

			}

		}
	}

	/**
	 * @return the parent
	 */
	public ComponentTypeWrapper getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(ComponentTypeWrapper parent) {
		this.parent = parent;
	}

	/**
	 * @return the invokedWindowList
	 */
	public List<GUITypeWrapper> getInvokedWindowList() {
		return invokedWindows;
	}

	/**
	 * @return the children
	 */
	public List<ComponentTypeWrapper> getChildren() {
		return children;
	}

	/**
	 * @param window
	 *            the window to set
	 */
	public void setWindow(GUITypeWrapper window) {
		this.window = window;
	}

	/**
	 * @return the window
	 */
	public GUITypeWrapper getWindow() {
		return window;
	}

	static ObjectFactory factory = new ObjectFactory();

	/**
	 * @return the dComponentType
	 */
	public ComponentType getDComponentType() {
		return dComponentType;
	}

	/**
	 * @param componentType
	 */
	public ComponentTypeWrapper(ComponentType componentType) {
		super();
		dComponentType = componentType;
	}

	public PropertyType getFirstPropertyByName(String sName) {
		AttributesType attributes = this.dComponentType.getAttributes();
		if (attributes == null)
			return null;
		List<PropertyType> lProperty = attributes.getProperty();

		for (PropertyType p : lProperty) {
			if (p.getName().equals(sName)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Find Property by name
	 * 
	 * <p>
	 * 
	 * @param sGUIName
	 * @param guiStructure
	 * @return
	 */

	public String getFirstValueByName(String sName) {

		PropertyType property = getFirstPropertyByName(sName);
		if (property != null && property.getValue().size() > 0)
			return property.getValue().get(0);
		return null;
	}

	/**
	 * Get a list of property by name
	 * 
	 * @param sName
	 * @return
	 */
	public List<String> getValueListByName(String sName) {
		AttributesType attributes = this.dComponentType.getAttributes();
		if (attributes == null)
			return null;
		List<PropertyType> lProperty = attributes.getProperty();

		for (PropertyType p : lProperty) {
			if (p.getName().equals(sName)) {
				return p.getValue();
			}
		}

		return new ArrayList<String>();
	}

	public void setID(String ID) {

		AttributesType attributes = this.dComponentType.getAttributes();
		if (attributes == null) {
			return;
		}

		List<PropertyType> lProperty = attributes.getProperty();
		if (lProperty == null)
			lProperty = new ArrayList<PropertyType>();

		for (PropertyType p : lProperty) {
			if (GUITARConstants.ID_TAG_NAME.equals(p.getName())) {
				lProperty.remove(p);
				break;
			}
		}

		PropertyType pID = factory.createPropertyType();
		pID.setName(GUITARConstants.ID_TAG_NAME);
		pID.getValue().add(ID);
		lProperty.add(0, pID);
	}

	/**
	 * Set a property of child object
	 * 
	 * @param sName
	 * @return
	 */
	public void setValueByName(String sTitle, String sName, String sValue) {
		String sMyTitle = getFirstValueByName(GUITARConstants.ID_TAG_NAME);

		if (sTitle.equals(sMyTitle)) {
			AttributesType attributes = this.dComponentType.getAttributes();

			List<PropertyType> lProperty = attributes.getProperty();

			for (PropertyType p : lProperty) {

				if (p.getName().equals(sName)) {
					lProperty.remove(p);
					List<String> lValue = new ArrayList<String>();
					lValue.add(sValue);
					p.setValue(lValue);
					lProperty.add(p);
				}
			}
			attributes.setProperty(lProperty);
			dComponentType.setAttributes(attributes);
		} else if (dComponentType instanceof ContainerType) {

			ContainerType container = (ContainerType) dComponentType;
			ContentsType contents = container.getContents();
			List<ComponentType> lChildren = container.getContents()
					.getWidgetOrContainer();
			List<ComponentType> lNewChildren = new ArrayList<ComponentType>();

			for (ComponentType child : lChildren) {
				ComponentTypeWrapper childA = new ComponentTypeWrapper(child);
				childA.setValueByName(sTitle, sName, sValue);
				lNewChildren.add(childA.getDComponentType());
			}

			contents.setWidgetOrContainer(lNewChildren);
			((ContainerType) dComponentType).setContents(contents);
		}
	}

	/**
	 * 
	 * Add an attribute value to the current ComponentType
	 * 
	 * <p>
	 * 
	 * @param sName
	 * @param sValue
	 */
	public void addValueByName(String sName, String sValue) {

		AttributesType attributes = dComponentType.getAttributes();

		if (attributes == null)
			attributes = factory.createAttributesType();

		List<PropertyType> lProperty = attributes.getProperty();

		PropertyType property = null;
		for (PropertyType aProperty : lProperty) {
			if (sName.equals(aProperty.getName())) {
				lProperty.remove(aProperty);
				property = aProperty;
				break;
			}
		}

		if (property == null) {
			property = new PropertyType();
			property.setName(sName);
		}

		List<String> lValue = property.getValue();

		if (!lValue.contains(sValue)) {
			lValue.add(sValue);
		}

//		if(lProperty==null){
//			attributes.setProperty(new ArrayList<PropertyType>());
//		}
//		
//		attributes.getProperty().add(property);
		
		property.setValue(lValue);
		lProperty.add(property);
		attributes.setProperty(lProperty);
		dComponentType.setAttributes(attributes);
	}

	/**
	 * 
	 * Add an attribute value to the child ComponentType having name Title
	 * 
	 * @param sTitle
	 * @param sName
	 * @param sValue
	 */
	public void addValueByName(String sTitle, String sName, String sValue) {

		String sMyTitle = getFirstValueByName(GUITARConstants.ID_TAG_NAME);

		if (sTitle.equals(sMyTitle)) {

			AttributesType attributes = this.dComponentType.getAttributes();

			List<PropertyType> lProperty = attributes.getProperty();
			PropertyType p = new PropertyType();
			p.setName(sName);
			List<String> lValue = new ArrayList<String>();
			lValue.add(sValue);
			p.setValue(lValue);
			lProperty.add(p);

			attributes.setProperty(lProperty);
			dComponentType.setAttributes(attributes);
		} else

		if (dComponentType instanceof ContainerType) {

			ContainerType container = (ContainerType) dComponentType;
			ContentsType contents = container.getContents();
			List<ComponentType> lChildren = container.getContents()
					.getWidgetOrContainer();
			List<ComponentType> lNewChildren = new ArrayList<ComponentType>();

			for (ComponentType child : lChildren) {
				ComponentTypeWrapper childA = new ComponentTypeWrapper(child);
				childA.addValueByName(sTitle, sName, sValue);
				lNewChildren.add(childA.getDComponentType());
			}

			contents.setWidgetOrContainer(lNewChildren);
			((ContainerType) dComponentType).setContents(contents);
		}
	}

	/**
	 * Remove a property by name
	 * 
	 * @param sName
	 */
	public void removeProperty(String sName) {

		AttributesType attributes = this.dComponentType.getAttributes();
		if (attributes == null)
			return;

		List<PropertyType> lProperties = attributes.getProperty();

		if (lProperties == null)
			return;

		for (PropertyType property : lProperties) {
			if (sName.equals(property.getName())) {
				lProperties.remove(property);
				break;
			}
		}

	}

	/**
	 * 
	 * Search component by id
	 * 
	 * <p>
	 * 
	 * @param ID
	 * @return
	 */
	public ComponentTypeWrapper getChildByID(String ID) {
		String sTitle = getFirstValueByName(GUITARConstants.ID_TAG_NAME);

		if (ID.equals(sTitle)) {
			return this;
		} else if (dComponentType instanceof ContainerType) {
			ContainerType container = (ContainerType) dComponentType;
			List<ComponentType> lChildrend = container.getContents()
					.getWidgetOrContainer();

			ComponentTypeWrapper retComp = null;

			for (ComponentType child : lChildrend) {
				ComponentTypeWrapper childAdapter = new ComponentTypeWrapper(
						child);
				retComp = childAdapter.getChildByID(ID);
				if (retComp != null)
					return retComp;
			}
		}
		return null;
	}

	/**
	 * Print the information of the component, used for debugging
	 */
	public void printInfo() {
		List<PropertyType> properties = this.getDComponentType()
				.getAttributes().getProperty();
		for (PropertyType p : properties) {
			String out;
			out = p.getName();
			for (String value : p.getValue())
				out += "\t" + value;
			System.out.println(out);
		}

	}

	/**
	 * @return
	 */
	public boolean hasChild() {
		if (children == null)
			return false;

		if (children.size() == 0)
			return false;
		return true;
	}

	/**
	 * @param signature
	 * @param name
	 * @param values
	 */
	public void addValueBySignature(AttributesType signature, String name,
			Set<String> values) {

		if (isMatchSignature(signature)) {
			// System.out.println("SIGNATURE FOUND!!!!");

			for (String value : values)
				this.addValueByName(name, value);

		} else

		if (dComponentType instanceof ContainerType) {
			ContainerType container = (ContainerType) dComponentType;
			ContentsType contents = container.getContents();
			if (contents == null)
				return;
			List<ComponentType> lChildren = contents.getWidgetOrContainer();
			List<ComponentType> lNewChildren = new ArrayList<ComponentType>();

			for (ComponentType child : lChildren) {
				ComponentTypeWrapper childA = new ComponentTypeWrapper(child);
				childA.addValueBySignature(signature, name, values);
				lNewChildren.add(childA.getDComponentType());
			}
		}

	}

	/**
	 * Check if a component matches with a signature
	 * <p>
	 * 
	 * @param signature
	 * @return
	 */
	public boolean isMatchSignature(AttributesType signature) {
		if (signature == null)
			return false;

		AttributesType attributes = this.dComponentType.getAttributes();
		if (attributes == null)
			return false;

		AttributesTypeWrapper wSignature = new AttributesTypeWrapper(signature);
		AttributesTypeWrapper wAttributes = new AttributesTypeWrapper(
				attributes);

		return (wAttributes.containsAll(wSignature));

	}

	/**
	 * @param signature
	 * @return
	 */
	public ComponentTypeWrapper getComponentBySignature(AttributesType signature) {

		if (isMatchSignature(signature)) {
			return this;
		} else if (dComponentType instanceof ContainerType) {
			ContainerType container = (ContainerType) dComponentType;
			ContentsType contents = container.getContents();
			if (contents == null)
				return null;

			List<ComponentType> lChildren = contents.getWidgetOrContainer();
			ComponentTypeWrapper result = null;

			for (ComponentType child : lChildren) {
				ComponentTypeWrapper childA = new ComponentTypeWrapper(child);
				result = childA.getComponentBySignature(signature);
				if (result != null)
					return result;
			}
		}

		return null;
	}

	/**
	 * Generate a signature of component
	 * <p>
	 * 
	 * @param lPropertyName
	 *            List of input property names
	 * @return
	 */
	public AttributesType getSubAttributes(List<String> lPropertyName) {

		AttributesType signature = factory.createAttributesType();
		AttributesType attributes = dComponentType.getAttributes();
		if (attributes == null)
			return signature;

		for (PropertyType property : attributes.getProperty())
			if (lPropertyName.contains(property.getName())) {
				signature.getProperty().add(property);
			}

		return signature;
	}

	/**
	 * @param signature
	 * @param name
	 * @param values
	 */
	public void updateValueBySignature(AttributesType signature, String name,
			Set<String> values) {

		if (isMatchSignature(signature)) {
			for (String value : values)
				this.updateValueByName(name, value);
		} else

		if (dComponentType instanceof ContainerType) {
			ContainerType container = (ContainerType) dComponentType;
			ContentsType contents = container.getContents();
			if (contents == null)
				return;
			List<ComponentType> lChildren = contents.getWidgetOrContainer();
			List<ComponentType> lNewChildren = new ArrayList<ComponentType>();

			for (ComponentType child : lChildren) {
				ComponentTypeWrapper childA = new ComponentTypeWrapper(child);
				childA.updateValueBySignature(signature, name, values);
				lNewChildren.add(childA.getDComponentType());
			}
		}
	}

	/**
	 * @param name
	 * @param value
	 */
	private void updateValueByName(String name, String value) {

		AttributesType attributes = dComponentType.getAttributes();

		if (attributes == null)
			attributes = factory.createAttributesType();

		List<PropertyType> lProperty = attributes.getProperty();

		if (lProperty == null)
			lProperty = new ArrayList<PropertyType>();

		int index = 0;

		for (PropertyType aProperty : lProperty) {
			if (name.equals(aProperty.getName())) {
				index = lProperty.indexOf(aProperty);
				lProperty.remove(index);

				break;
			}
		}

		PropertyType property = null;
		property = new PropertyType();
		property.setName(name);

		List<String> lValue = new ArrayList<String>();
		lValue.add(value);

		property.setValue(lValue);

		attributes.getProperty().add(index, property);
		dComponentType.setAttributes(attributes);

	}

	//
	// /**
	// * @param sPostfix
	// */
	// public void updateID(String sPostfix) {
	// String curID = this.getFirstValueByName(GUITARConstants.ID_TAG_NAME);
	//
	// if (curID != null)
	// updateValueByName(GUITARConstants.ID_TAG_NAME, curID + sPostfix);
	//
	// if (dComponentType instanceof ContainerType) {
	// ContainerType container = (ContainerType) dComponentType;
	// ContentsType contents = container.getContents();
	// if (contents == null)
	// return;
	// List<ComponentType> lChildren = contents.getWidgetOrContainer();
	// List<ComponentType> lNewChildren = new ArrayList<ComponentType>();
	//
	// for (ComponentType child : lChildren) {
	// ComponentTypeWrapper childA = new ComponentTypeWrapper(child);
	// childA.updateID(sPostfix);
	// lNewChildren.add(childA.getDComponentType());
	// }
	// }
	//
	// }

	/**
	 * Get max ID in the tree rooted from this node
	 * 
	 * @return
	 */
	public int getMaxID() {

		String ID = getFirstValueByName(GUITARConstants.ID_TAG_NAME);

		int nMaxID = 0;

		if (ID == null)
			nMaxID = 0;

		else if (ID.length() < 1)
			nMaxID = 0;
		else {
			ID = ID.substring(1);

			try {
				nMaxID = Integer.parseInt(ID);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (dComponentType instanceof ContainerType) {

			ContainerType container = (ContainerType) dComponentType;
			ContentsType contents = container.getContents();
			if (contents == null)
				return 0;

			List<ComponentType> lChildren = contents.getWidgetOrContainer();

			for (ComponentType child : lChildren) {
				ComponentTypeWrapper childA = new ComponentTypeWrapper(child);
				int localMax = childA.getMaxID();
				if (nMaxID < localMax)
					nMaxID = localMax;
			}
		}

		return nMaxID;
	}

	/**
	 * This static variable is used to generate widget ID
	 */
	static int ID_COUNTER = 0;

	/**
	 * Update ID according to the new counter
	 */
	public void updateID() {

		updateValueByName(GUITARConstants.ID_TAG_NAME,
				GUITARConstants.COMPONENT_ID_PREFIX
						+ Integer.toString(ID_COUNTER++));

		if (dComponentType instanceof ContainerType) {
			ContainerType container = (ContainerType) dComponentType;
			ContentsType contents = container.getContents();
			if (contents == null)
				return;
			List<ComponentType> lChildren = contents.getWidgetOrContainer();
			List<ComponentType> lNewChildren = new ArrayList<ComponentType>();

			for (ComponentType child : lChildren) {
				ComponentTypeWrapper childA = new ComponentTypeWrapper(child);
				childA.updateID();
				lNewChildren.add(childA.getDComponentType());
			}
		}

	}

	/**
	 * @param nID_COUNTER
	 *            the iD_COUNTER to set
	 */
	public static void setID_COUNTER(int nID_COUNTER) {
		ID_COUNTER = nID_COUNTER;
	}

	/**
	 * @param hashcodeGenerator
	 * @param guiTypeWrapper
	 */
	public void generateID(GHashcodeGenerator hashcodeGenerator,
			GUITypeWrapper guiTypeWrapper) {

		long hashcode = hashcodeGenerator.getHashcodeFromData(dComponentType,
				guiTypeWrapper.getData());

		setID(GUITARConstants.COMPONENT_ID_PREFIX + hashcode);

		if (dComponentType instanceof ContainerType) {
			ContainerType container = (ContainerType) dComponentType;
			ContentsType contents = container.getContents();
			if (contents == null)
				return;
			List<ComponentType> lChildren = contents.getWidgetOrContainer();
			List<ComponentType> lNewChildren = new ArrayList<ComponentType>();

			for (ComponentType child : lChildren) {
				ComponentTypeWrapper childA = new ComponentTypeWrapper(child);
				childA.generateID(hashcodeGenerator, guiTypeWrapper);
				lNewChildren.add(childA.getDComponentType());
			}
		}

	}

}
