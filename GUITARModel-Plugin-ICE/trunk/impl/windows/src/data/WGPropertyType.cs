
using System.Collections.Generic;

namespace guitar.model.data
{

public class WGPropertyType : PropertyTypeDisp_
{
    private PropertyTypePrx proxy;

    internal WGPropertyType()
    {
        Name = string.Empty;
        Values = new List<string>();

        Ice.ObjectPrx base_ = Adapter.Instance.addWithUUID(this);
        proxy = PropertyTypePrxHelper.checkedCast(base_);
    }

    internal WGPropertyType(string name, List<string> values)
        : this()
    {
        Name = name;
        foreach (string s in values)
        {
            this.Values.Add(s);
        }
    }

    public PropertyTypePrx Proxy
    {
        get
        {
            return proxy;
        }
    }

    internal string Name
    {
        get;
        set;
    }

    internal List<string> Values
    {
        get;
        set;
    }

    public override string getName(Ice.Current context__)
    {
        return Name;
    }

    public override string[] getValue(Ice.Current context__)
    {
        return Values.ToArray();
    }
}

}
