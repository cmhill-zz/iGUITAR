
using System.Reflection;
using System.Collections.Generic;

using System;

using guitar.model;

namespace guitar.@event {

[AttributeUsage(AttributeTargets.Assembly, Inherited = false, AllowMultiple = true)]
sealed class RegisterEventAttribute : Attribute
{
    public RegisterEventAttribute(Type typeName)
    {
        PropertyInfo propInfo = typeName.GetProperty("Instance");
        ActionDisp_ action = propInfo.GetValue(null, null) as ActionDisp_;

        if (action != null)
        {
            EventFactory.registerEvent(action);
        }
        else
        {
            Console.Error.WriteLine("Could not register event {0}", typeName);
        }
    }
}

public class EventFactory
{
    private static bool loaded = false;
    private static List<ActionPrx> actionList = new List<ActionPrx>();

    private static void loadAssembly()
    {
        // Only do this once
        if (!loaded)
        {
            // Query assembly for list of possible events
            Assembly.GetExecutingAssembly().GetCustomAttributes(false);
            loaded = true;
        }
    }

    public static void registerEvent(ActionDisp_ action)
    {
        Ice.Communicator ic = Ice.Application.communicator();
        Ice.ObjectPrx base_ = Adapter.Instance.add(
            action, ic.stringToIdentity(action.getEventType()));
        ActionPrx proxy = ActionPrxHelper.checkedCast(base_);
        actionList.Add(proxy);
    }

    public static ActionPrx[] getEventList(ComponentPrx component)
    {
        loadAssembly();

        // Check which events support the component
        List<ActionPrx> proxies = new List<ActionPrx>();
        foreach (ActionPrx action in actionList)
        {
            if (action.isSupportedBy(component))
                proxies.Add(action);
        }
        return proxies.ToArray();
    }
}

}
