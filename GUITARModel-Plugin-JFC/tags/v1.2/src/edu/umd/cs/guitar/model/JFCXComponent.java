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
package edu.umd.cs.guitar.model;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleAction;
import javax.accessibility.AccessibleContext;

import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.event.JFCActionHandler;
import edu.umd.cs.guitar.event.JFCEditableTextHandler;
import edu.umd.cs.guitar.event.JFCSelectionHandler;
import edu.umd.cs.guitar.event.JFCValueHandler;
import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.wrapper.AttributesTypeWrapper;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class JFCXComponent extends GComponent {

    Accessible aComponent;

    /**
     * @return the aComponent
     */
    public Accessible getAComponent() {
        return aComponent;
    }

    /**
     * @param aComponent
     */
    public JFCXComponent(Accessible aComponent) {
        super();
        this.aComponent = aComponent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.model.GComponent#getGUIProperties()
     */
    @Override
    public List<PropertyType> getGUIProperties() {
        List<PropertyType> retList = new ArrayList<PropertyType>();
        // Other properties

        PropertyType p;
        List<String> lPropertyValue;
        String sValue;

        // Name
        sValue = null;
        sValue = getName();
        if (sValue != null) {
            p = factory.createPropertyType();
            p.setName(JFCConstants.TITLE_TAG);
            lPropertyValue = new ArrayList<String>();
            lPropertyValue.add(sValue);
            p.setValue(lPropertyValue);
            retList.add(p);
        }

        // Icon
        sValue = null;
        sValue = getIconName();
        if (sValue != null) {
            p = factory.createPropertyType();
            p.setName(JFCConstants.ICON_TAG);
            lPropertyValue = new ArrayList<String>();
            lPropertyValue.add(sValue);
            p.setValue(lPropertyValue);
            retList.add(p);
        }

        // Get bean properties
        List<PropertyType> lBeanProperties = getGUIBeanProperties();
        retList.addAll(lBeanProperties);

        return retList;

    }

    /**
     * Get all bean properties of the component
     * 
     * @return
     */
    private List<PropertyType> getGUIBeanProperties() {
        List<PropertyType> retList = new ArrayList<PropertyType>();
        Method[] methods = aComponent.getClass().getMethods();
        PropertyType p;
        List<String> lPropertyValue;

        for (Method m : methods) {
            if (m.getParameterTypes().length > 0) {
                continue;
            }
            String sMethodName = m.getName();
            String sPropertyName = sMethodName;

            if (sPropertyName.startsWith("get")) {
                sPropertyName = sPropertyName.substring(3);
            } else if (sPropertyName.startsWith("is")) {
                sPropertyName = sPropertyName.substring(2);
            } else
                continue;

            // make sure property is in lower case
            sPropertyName = sPropertyName.toLowerCase();

            if (JFCConstants.GUI_PROPERTIES_LIST.contains(sPropertyName)) {

                Object value;
                try {
                    value = m.invoke(aComponent, new Object[0]);
                    if (value != null) {
                        p = factory.createPropertyType();
                        lPropertyValue = new ArrayList<String>();
                        lPropertyValue.add(value.toString());
                        p.setName(sPropertyName);
                        p.setValue(lPropertyValue);
                        retList.add(p);
                    }
                } catch (IllegalArgumentException e) {
                } catch (IllegalAccessException e) {
                } catch (InvocationTargetException e) {
                }
            }
        }
        return retList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.model.GXComponent#getChildren()
     */
    @Override
    public List<GComponent> getChildren() {

        List<GComponent> retList = new ArrayList<GComponent>();

        AccessibleContext aContext = aComponent.getAccessibleContext();

        if (aContext == null)
            return retList;

        int nChildren = aContext.getAccessibleChildrenCount();
        for (int i = 0; i < nChildren; i++) {

            Accessible aChild = aContext.getAccessibleChild(i);
            GComponent gChild = new JFCXComponent(aChild);
            retList.add(gChild);
        }
        return retList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.model.GXComponent#getParent()
     */
    @Override
    public GComponent getParent() {
        AccessibleContext aContext = this.aComponent.getAccessibleContext();
        if (aContext == null)
            return null;
        Accessible jParent = aContext.getAccessibleParent();

        if (jParent == null)
            return null;
        return new JFCXComponent(jParent);
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.model.GXComponent#hasChildren()
     */
    @Override
    public boolean hasChildren() {
        AccessibleContext xContext = aComponent.getAccessibleContext();

        if (xContext == null)
            return false;

        int nChildren = xContext.getAccessibleChildrenCount();
        return (nChildren > 0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.model.GXObject#getID()
     */
    @Override
    @Deprecated
    public String getFullID() {
        String retID = "";

        String sName = getName();
        if (sName == null)
            sName = "null";
        retID += sName;

        return retID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.model.GXObject#getName()
     */
    @Override
    public String getName() {
        String sName = "";
        if (aComponent == null)
            return "";
        AccessibleContext aContext = aComponent.getAccessibleContext();

        if (aContext == null)
            return "";

        sName = aContext.getAccessibleName();

        if (sName != null)
            return sName;

        if (sName == null)
            sName = getIconName();

        if (sName != null)
            return sName;

        if (aComponent instanceof Component) {
            Component comp = (Component) aComponent;
            sName = comp.getName();

            // In the worst case we must use the screen position to
            // identify the widget
            if (sName == null) {
                sName = "Pos(" + comp.getX() + "," + comp.getY() + ")";
            }
        }
        return sName;
    }

    /**
     * Parse the icon name of a widget from the resource's absolute path.
     * 
     * <p>
     * 
     * @param component
     * @return
     */
    private String getIconName() {
        String retIcon = null;
        try {
            Class<?> partypes[] = new Class[0];
            Method m = aComponent.getClass().getMethod("getIcon", partypes);

            String sIconPath = null;
            if (m != null) {
                Object obj = (m.invoke(aComponent, new Object[0]));
                if (obj != null)
                    sIconPath = obj.toString();
            }

            if (sIconPath == null)
                return null;

            String[] sIconElements = sIconPath.split("/");
            retIcon = sIconElements[sIconElements.length - 1];

        } catch (SecurityException e) {
            // e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            // e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            // e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            // e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            // e.printStackTrace();
            return null;
        }
        return retIcon;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.model.GXComponent#getEventList()
     */
    @Override
    public List<GEvent> getEventList() {
        List<GEvent> retEvents = new ArrayList<GEvent>();
        // List<String> retEvents = new ArrayList<String>();

        AccessibleContext aContext = aComponent.getAccessibleContext();

        if (aContext == null)
            return retEvents;

        Object event;

        // Text
        event = aContext.getAccessibleEditableText();
        if (event != null) {
            retEvents.add(new JFCEditableTextHandler());
            return retEvents;
        }

        // Action
        event = aContext.getAccessibleAction();
        if (event != null) {
            retEvents.add(new JFCActionHandler());
            return retEvents;
        }

        // Selection
        event = aContext.getAccessibleSelection();
        if (event != null)
            retEvents.add(new JFCSelectionHandler());

        // Value
        event = aContext.getAccessibleValue();
        if (event != null)
            retEvents.add(new JFCValueHandler());

        return retEvents;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.model.GXComponent#getType()
     */
    @Override
    public String getClassVal() {

        // AccessibleContext aContext = aComponent.getAccessibleContext();
        // String role = aContext.getAccessibleRole().toString();
        // return role;
        return aComponent.getClass().getName();

    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.model.GXComponent#getTypeVal()
     */
    @Override
    public String getTypeVal() {
        String retProperty;
        if (isTerminal())
            retProperty = GUITARConstants.TERMINAL;
        else
            retProperty = GUITARConstants.SYSTEM_INTERACTION;
        return retProperty;
    }

    /**
     * Check if this component is a terminal widget
     * 
     * <p>
     * 
     * @return
     */
    @Override
    public boolean isTerminal() {

        AccessibleContext aContext = aComponent.getAccessibleContext();

        if (aContext == null)
            return false;

        AccessibleAction aAction = aContext.getAccessibleAction();

        if (aAction == null)
            return false;

        String sName = getName();

        List<AttributesTypeWrapper> termSig = JFCConstants.sTerminalWidgetSignature;
        for (AttributesTypeWrapper sign : termSig) {
            String titleVals = sign.getFirstValByName(JFCConstants.TITLE_TAG);

            if (titleVals == null)
                continue;

            if (titleVals.equalsIgnoreCase(sName))
                return true;

        }

        return false;

    }
}
