package edu.umd.cs.guitar.guitestrunner;

import nu.xom.Element;
import nu.xom.Elements;

/**
 * Encapsulate a testcase step.
 * @author Scott McMaster
 *
 */
public class TestcaseStep
{
	private WindowName windowName;
	
	private ComponentId componentId;
	
	private Action action;
	
	private String actionType;
	
	private String componentClassName;
	
	private String tooltip;
	
	/**
	 * Construct a step from 
	 * @param stepNode
	 * @return
	 */
	public static TestcaseStep fromStepElement(Element stepElem)
	{
		TestcaseStep retval = new TestcaseStep();
		
		Element windowElem = stepElem.getFirstChildElement("Window");
		WindowName windowName = new WindowName(windowElem.getValue());
		retval.setWindowName(windowName);
		
		Element componentElem = stepElem.getFirstChildElement("Component");
		ComponentId componentId = new ComponentId(componentElem.getValue());
		retval.setComponentId(componentId);
		
		Element actionElem = stepElem.getFirstChildElement("Action");
		String actionText = actionElem.getValue();
		retval.setAction(new Action(actionText));
		
		String actionType = "SYSTEM INTERACTION";
		Elements properties = stepElem.getFirstChildElement("Attributes").getChildElements("Property");
		for( int i = 0; i < properties.size(); ++i )
		{
			Element property = properties.get(i);
			Element name = property.getFirstChildElement("Name");
			Element value = property.getFirstChildElement("Value");
			if( name.getValue().equals("Type") )
			{
				actionType = value.getValue();
			}
			else if(name.getValue().equals("Class"))
			{
				retval.setComponentClassName(value.getValue());
			}
			else if(name.getValue().equals("ToolTip"))
			{
				retval.setTooltip(value.getValue());
			}
		}
		retval.setActionType(actionType);
		
		return retval;
	}
	
	@Override
	public String toString() {
		return windowName.toString() + ':' + 
				componentId.toString() + ':' + action.toString();
	}
	
	public void setWindowName(WindowName windowName) {
		this.windowName = windowName;
	}

	public WindowName getWindowName() {
		return windowName;
	}

	public void setComponentId(ComponentId componentId) {
		this.componentId = componentId;
	}

	public ComponentId getComponentId() {
		return componentId;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getActionType() {
		return actionType;
	}

	public void setComponentClassName(String componentClassName) {
		this.componentClassName = componentClassName;
	}

	public String getComponentClassName() {
		return componentClassName;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Action getAction() {
		return action;
	}
}
