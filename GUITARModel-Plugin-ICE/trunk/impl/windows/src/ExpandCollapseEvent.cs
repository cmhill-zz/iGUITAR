
using System.Windows.Automation;

using guitar.model;

[assembly: guitar.@event.RegisterEvent(typeof(guitar.@event.ExpandCollapseEvent))]

namespace guitar.@event
{

public class ExpandCollapseEvent : ActionDisp_
{
    private static ExpandCollapseEvent instance = new ExpandCollapseEvent();
    public static readonly string Name = "ExpandCollapse";

    public static ExpandCollapseEvent Instance
    {
        get
        {
            return instance;
        }
    }

    public override void perform(ComponentPrx component, Ice.Current context__)
    {
        AutomationElement element = WGComponent.fromId(component.getId());
        ExpandCollapsePattern patternMenu =
            element.GetCurrentPattern(
                ExpandCollapsePattern.Pattern) as ExpandCollapsePattern;

        // Pattern is not available for this component
        if (patternMenu == null)
            // TODO: Log message
            return;

        ExpandCollapseState state = patternMenu.Current.ExpandCollapseState;
        if (state == ExpandCollapseState.Expanded)
            patternMenu.Collapse();
        else if (state == ExpandCollapseState.PartiallyExpanded ||
                 state == ExpandCollapseState.Collapsed)
            patternMenu.Expand();
    }

    public override bool isSupportedBy(ComponentPrx component, Ice.Current context__)
    {
        AutomationElement element = WGComponent.fromId(component.getId());
        return (bool) element.GetCurrentPropertyValue(
            AutomationElement.IsExpandCollapsePatternAvailableProperty);
    }

    public override string getEventType(Ice.Current context__)
    {
        return ExpandCollapseEvent.Name;
    }
}

}
