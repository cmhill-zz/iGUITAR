
using System;

namespace guitar {

public class Adapter
{
    private static Ice.ObjectAdapter instance = null;

    private Adapter()
    {
    }

    public static Ice.ObjectAdapter createAdapter(
        Ice.Communicator ic, string adapterName)
    {
        instance = ic.createObjectAdapterWithEndpoints(
            adapterName, "default -p 10000");
        instance.activate();

        model.WGConstants.initialize();

        return instance;
    }

    public static Ice.ObjectAdapter Instance
    {
        get
        {
            return instance;
        }
    }
}

}
