package edu.umd.cs.guitar.guitestrunner;


/**
 * 
 * @author Scott McMaster
 * 
 */
public class TestCaseStep {
    private static final long serialVersionUID = 1L;

    private WindowName windowName;

    private ComponentId componentId;

    private Action action;

    private String actionType;

    private String componentClassName;

    private String tooltip;

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TestCaseStep) {
            TestCaseStep another = (TestCaseStep) obj;
            boolean result = (componentId != null && componentId.equals(another.componentId));
            result = result && (action != null && action.equals(another.action));
            return result;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return componentId.hashCode();
    }

    @Override
    public String toString() {
        return windowName.toString() + ":" + componentId.toString() + ":" + action.toString() + ":" + actionType.toString();
    }
}
