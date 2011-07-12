
using System;
using System.Collections.Generic;
using System.Windows;
using System.Windows.Automation;

using guitar.@event;
using guitar.model.data;

namespace guitar.model {

public class WGComponent : ComponentDisp_ {
    private AutomationElement element;
    private ComponentPrx proxy;

    private string title;
    private int id;
    private Rect boundingRect;

    private static int counter = 0;
    private static Dictionary<int, AutomationElement> cache =
        new Dictionary<int, AutomationElement>();

    public WGComponent(AutomationElement element)
    {
        this.element = element;

        title = element.Current.Name;
        boundingRect = element.Current.BoundingRectangle;

        /* Add this automation element to the cache */
        cache.Add(counter, element);
        id = counter;
        counter++;

        /* Add this component to the adapter */
        Ice.ObjectPrx base_ = Adapter.Instance.addWithUUID(this);
        proxy = ComponentPrxHelper.checkedCast(base_);
    }

    public AutomationElement Element
    {
        get
        {
            return element;
        }
    }

    public ComponentPrx Proxy
    {
        get
        {
            return proxy;
        }
    }

    public override ActionPrx[] getEventList(Ice.Current context__)
    {
        return EventFactory.getEventList(Proxy);
    }

    public override ComponentPrx[] getChildren(Ice.Current context__)
    {
        AutomationElementCollection childrenList =
            element.FindAll(TreeScope.Children, Condition.TrueCondition);

        ComponentPrx[] components = new ComponentPrx[childrenList.Count];
        for (int i = 0; i < childrenList.Count; i++)
        {
            components[i] = new WGComponent(childrenList[i]).Proxy;
        }
        return components;
    }

    public override ComponentPrx getParent(Ice.Current context__)
    {
        return null;
    }

    public override string getTitle(Ice.Current context__)
    {
        return title;
    }

    public override int getX(Ice.Current context__)
    {
        return (int) boundingRect.X;
    }

    public override int getY(Ice.Current context__)
    {
        return (int) boundingRect.Y;
    }

    public override bool isEnable(Ice.Current context__)
    {
        bool enabled = (bool) element.GetCurrentPropertyValue(
            AutomationElement.IsEnabledProperty);
        return enabled;
    }

    public override PropertyTypePrx[] getGUIProperties(Ice.Current context__)
    {
        List<PropertyTypePrx> properties = new List<PropertyTypePrx>();

        // Name property
        {
            WGPropertyType nameProperty = new WGPropertyType();
            nameProperty.Name = "Title";
            nameProperty.Values.Add(getTitle());
            properties.Add(nameProperty.Proxy);
        }

        // Bounding Box
        {
            WGPropertyType boundingBoxProperty = new WGPropertyType();
            boundingBoxProperty.Name = "Bounding Box";
            boundingBoxProperty.Values.Add(getX().ToString());
            boundingBoxProperty.Values.Add(getY().ToString());
            properties.Add(boundingBoxProperty.Proxy);
        }

        return properties.ToArray();
    }

    public override int getId(Ice.Current context__)
    {
        return id;
    }

    public static AutomationElement fromId(int id)
    {
        return cache[id];
    }
}

} // namespace guitar.model
