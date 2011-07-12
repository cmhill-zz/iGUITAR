
using System.Windows.Automation;

using guitar.model.data;

namespace guitar.model {

public class WGWindow : WindowDisp_
{
    private WGComponent component;
    private bool modal;
    private WindowPrx proxy;

    public WGWindow(WGComponent component)
    {
        this.component = component;

        WindowPattern wp = component.Element.GetCurrentPattern(
            WindowPattern.Pattern) as WindowPattern;
        modal = wp.Current.IsModal;

        Ice.ObjectPrx base_ = Adapter.Instance.addWithUUID(this);
        proxy = WindowPrxHelper.checkedCast(base_);
    }

    public WGWindow(AutomationElement element)
        : this(new WGComponent(element))
    {
    }

    public WindowPrx Proxy
    {
        get
        {
            return proxy;
        }
    }

    public override bool isValid(Ice.Current context__)
    {
        try
        {
            object valid = component.Element.GetCurrentPropertyValue(
                AutomationElement.IsWindowPatternAvailableProperty);
            return (bool) valid;
        }
        catch (ElementNotAvailableException)
        {
            return false;
        }
    }

    public override ComponentPrx getContainer(Ice.Current context__)
    {
        return component.Proxy;
    }

    public override bool isModal(Ice.Current context__)
    {
        return modal;
    }

    public override PropertyTypePrx[] getGUIProperties(Ice.Current context__)
    {
        return component.getGUIProperties();
    }
}

}