
using System.Windows.Automation;


using guitar.model;

[assembly: guitar.@event.RegisterEvent(typeof(guitar.@event.InvokeEvent))]

namespace guitar.@event {

public class InvokeEvent : ActionDisp_
{
    private static InvokeEvent instance = new InvokeEvent();
    public static readonly string Name = "Invoke";

    public static InvokeEvent Instance
    {
        get
        {
            return instance;
        }
    }

    public override void perform(ComponentPrx component, Ice.Current context__)
    {
        AutomationElement element = WGComponent.fromId(component.getId());
        if (isActable(element))
        {
            InvokePattern pattern =
                element.GetCurrentPattern(InvokePattern.Pattern) as InvokePattern;
            pattern.Invoke();
        }
    }

    public override bool isSupportedBy(ComponentPrx component, Ice.Current context__)
    {
        AutomationElement element = WGComponent.fromId(component.getId());
        return (bool)element.GetCurrentPropertyValue(
            AutomationElement.IsInvokePatternAvailableProperty);
    }

    public override string getEventType(Ice.Current context__)
    {
        return InvokeEvent.Name;
    }

    private bool isActable(AutomationElement element)
    {
        return element != null && element.Current.IsEnabled;
    }
}

}