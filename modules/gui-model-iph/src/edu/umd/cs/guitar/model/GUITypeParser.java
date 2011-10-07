package edu.umd.cs.guitar.model;

import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.guitar.model.data.PropertyType;

public class GUITypeParser {

	/**
	 * Return the property value associated with the given property name
	 * @param iWindow the owner of the property
	 * @param pName property name
	 * @return list of property values
	 */
	public static List<String> getValueByName(IphWindow iWindow, String pName) {
		List<String> values = new ArrayList<String>();
		List<PropertyType> pList = iWindow.guiType.getWindow().getAttributes().getProperty();
		for (PropertyType p : pList) {
			if (p.getName().equals(pName)) {
				values.addAll(p.getValue());
			}
		}
		return values;
	}
	
	/**
	 * Return the property value associated with the given property name
	 * @param iComponent the owner of the property
	 * @param pName property name
	 * @return list of property values
	 */
	public static List<String> getValueByName(IphComponent iComponent, String pName) {
		List<String> values = new ArrayList<String>();
		List<PropertyType> pList = iComponent.componentType.getAttributes().getProperty();
		for (PropertyType p : pList) {
			if (p.getName().equals(pName)) {
				values.addAll(p.getValue());
			}
		}
		return values;
	}
	
}
