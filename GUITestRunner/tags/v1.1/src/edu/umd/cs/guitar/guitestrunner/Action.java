package edu.umd.cs.guitar.guitestrunner;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Scott McMaster, Si Huang
 * 
 */
public class Action {
    private String name;

    private List<String> parameterTypes;

    private List<String> parameterValues;

    public Action(String rawActionText) {
        String[] actionParts = rawActionText.split("_");

        name = actionParts[0];

        parameterTypes = new ArrayList<String>();
        parameterValues = new ArrayList<String>();
        boolean populateType = true;
        for (int i = 1; i < actionParts.length; ++i) {
            if (populateType) {
                parameterTypes.add(actionParts[i]);
            } else {
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

    public int hashCode() {
        int result = 0;
        if (name != null) {
            result += name.hashCode();
        }
        if (parameterTypes != null) {
            result += parameterTypes.hashCode();
        }
        if (parameterValues != null) {
            result += parameterValues.hashCode();
        }

        return result;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Action) {
            Action another = (Action) obj;
            boolean result = ("" + name).equals(another.name);
            result = result && ((parameterTypes == null && another.parameterTypes == null) ||
                                (parameterTypes != null && parameterTypes.equals(another.parameterTypes)));
            result = result && ((parameterValues == null && another.parameterValues == null) ||
                                (parameterValues != null && parameterValues.equals(another.parameterValues)));
            return result;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(name);
        for (int i = 0; i < parameterTypes.size(); ++i) {
            sb.append("_");
            sb.append(parameterTypes.get(i));
            sb.append("_");
            sb.append(parameterValues.get(i));
        }
        return sb.toString();
    }
}
