package edu.umd.cs.guitar.guitestrunner;

import java.util.ArrayList;
import java.util.List;

public class Action
{
	private String name;
	
	private List<String> parameterTypes;
	
	private List<String> parameterValues;

	public Action(String rawActionText)
	{
		String[] actionParts = rawActionText.split("_");
		
		name = actionParts[0];
		
		parameterTypes = new ArrayList<String>();
		parameterValues = new ArrayList<String>();
		boolean populateType = true;
		for( int i = 1; i < actionParts.length; ++i )
		{
			if( populateType )
			{
				parameterTypes.add(actionParts[i]);
			}
			else
			{
				parameterValues.add(actionParts[i]);
			}
			populateType = !populateType;
		}
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setParameterTypes(List<String> parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public List<String> getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterValues(List<String> parameterValues) {
		this.parameterValues = parameterValues;
	}

	public List<String> getParameterValues() {
		return parameterValues;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
