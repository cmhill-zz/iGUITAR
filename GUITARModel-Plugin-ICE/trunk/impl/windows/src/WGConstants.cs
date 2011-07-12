
using System.Diagnostics;

namespace guitar.model {

public class WGConstants : ConstantsDisp_ {
    private static WGConstants instance = null;

    private WGConstants()
    {
        Adapter.Instance.add(this, Ice.Application.communicator().
            stringToIdentity("Constants"));
        instance = this;
    }

    public WGConstants Instance {
        get {
            return initialize();
        }
    }

    public static WGConstants initialize()
    {
        if (instance == null)
            instance = new WGConstants();
        return instance;
    }

    private string[] ID_PROPERTIES = { "Title", "Class" };
    public override string[] getIdProperties(Ice.Current current__)
    {
        return ID_PROPERTIES;
    }
}

}
