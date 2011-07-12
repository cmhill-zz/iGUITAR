
using System;

using guitar.model;

namespace guitar
{

public class Application : Ice.Application
{
    public override int run(string[] args)
    {
        string appPath = args[0];

        string[] arguments = new string[args.Length - 1];
        for (int i = 1; i < args.Length; i++)
        {
            arguments[i - 1] = args[1];
        }

        Adapter.createAdapter(communicator(), appName());
        WGApplication app = new WGApplication(appPath, 5000);

        communicator().waitForShutdown();

        return 0;
    }

    public static int Main(string[] args)
    {
        if (args.Length < 1)
        {
            Console.Error.WriteLine("Must specify an executable to run");
            return 1;
        }

        Application app = new Application();
        return app.main(args);
    }
}

}
