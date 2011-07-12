
using System;
using System.Diagnostics;
using System.Threading;

namespace guitar.model {

public class WGApplication : ApplicationDisp_ {
    private string appPath;
    private int delay;

    private static WGApplication instance = null;

    public WGApplication(string appPath, int delay)
    {
        Debug.Assert(instance == null, "Only one instance of WGApplication can exist");
        this.appPath = appPath;
        this.delay = delay;

        Adapter.Instance.add(this, Ice.Application.communicator().
            stringToIdentity("Application"));
        instance = this;
    }

    public WGApplication Instance
    {
        get
        {
            return instance;
        }
    }

    public override ProcessPrx connect(string[] args, Ice.Current current__)
    {
        /* Create the process and sleep for the specified delay */
        WGProcess process = new WGProcess(appPath, args);
        Thread.Sleep(delay);

        /* Add this process to the adapter and return it */
        return ProcessPrxHelper.uncheckedCast(
            Adapter.Instance.addWithUUID(process));
    }
}

}
