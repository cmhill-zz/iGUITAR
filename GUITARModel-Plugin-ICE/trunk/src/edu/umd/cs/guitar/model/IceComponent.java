package edu.umd.cs.guitar.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.wrapper.AttributesTypeWrapper;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.event.IceEvent;

public class IceComponent extends GComponent {
    private guitar.model.ComponentPrx component;

    public IceComponent(guitar.model.ComponentPrx component, GWindow parent)
    {
        super(parent);
        this.component = component;
    }

    public guitar.model.ComponentPrx getProxy()
    {
        return component;
    }

    @Override
    public String getClassVal()
    {
        return this.getClass().getName();
    }

    @Override
    public String getTypeVal()
    {
        String retProperty;

        if (isTerminal())
            retProperty = GUITARConstants.TERMINAL;
        else
            retProperty = GUITARConstants.SYSTEM_INTERACTION;
        return retProperty;
    }

    @Override
    public boolean isTerminal()
    {
        return matchesProperties(this, IceConstants.sTerminalWidgetSignature);
    }

    public static boolean matchesProperties(
        GComponent component, List<AttributesTypeWrapper> signatures)
    {
        List<PropertyType> properties = component.getGUIProperties();
        for (AttributesTypeWrapper wrapperType : signatures) {
            // If all fields are null or match the wrapperType, then
            // this is a terminal widget
            boolean match = true;
            for (PropertyType property : properties) {
                PropertyType configProperty =
                    wrapperType.getPropertyByName(property.getName());
                if (configProperty != null &&
                    !configProperty.getValue().equals(property.getValue())) {
                    match = false;
                    break;
                }
            }

            if (match)
                return true;
        }

        return false;
    }

    @Override
    public boolean isEnable()
    {
        return component.isEnable();
    }

    @Override
    public boolean hasChildren()
    {
        guitar.model.ComponentPrx[] children = component.getChildren();
        return children.length > 0;
    }

    @Override
    public GComponent getParent()
    {
        guitar.model.ComponentPrx parent = component.getParent();
        return new IceComponent(parent, window);
    }

    @Override
    public List<GComponent> getChildren()
    {
        guitar.model.ComponentPrx[] children = component.getChildren();
        ArrayList<GComponent> array = new ArrayList<GComponent>();
        for (guitar.model.ComponentPrx proxy : children) {
            array.add(new IceComponent(proxy, window));
        }
        return array;
    }

    @Override
    public List<GEvent> getEventList()
    {
        guitar.event.ActionPrx[] events = component.getEventList();
        ArrayList<GEvent> array = new ArrayList<GEvent>();
        for (guitar.event.ActionPrx e : events) {
            array.add(new IceEvent(e));
        }
        return array;
    }

    @Override
    protected void extractEvents(ComponentTypeWrapper componentAdapter)
    {
        List<GEvent> eventList = getEventList();
        for (GEvent event : eventList) {
            componentAdapter.addValueByName(
                GUITARConstants.EVENT_TAG_NAME,
                ((IceEvent) event).getEventType());
        }
    }

    @Override
    public String getTitle()
    {
        return component.getTitle();
    }

    @Override
    public int getX()
    {
        return component.getX();
    }

    @Override
    public int getY()
    {
        return component.getY();
    }

    @Override
    public List<PropertyType> getGUIProperties()
    {
        guitar.model.data.PropertyTypePrx[] properties =
            component.getGUIProperties();

        List<PropertyType> propList =
            new ArrayList<PropertyType>(properties.length);

        for (guitar.model.data.PropertyTypePrx property : properties) {
            PropertyType wrapper = factory.createPropertyType();
            wrapper.setName(property.getName());
            wrapper.setValue(Arrays.asList(property.getValue()));

            propList.add(wrapper);
        }

        return propList;
    }
}
