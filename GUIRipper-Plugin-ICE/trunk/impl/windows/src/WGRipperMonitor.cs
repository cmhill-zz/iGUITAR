
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Windows.Automation;

using guitar.model;
using guitar.@event;

namespace guitar.ripper {

public class WGRipperMonitor : RipperMonitorDisp_
{
    private WGApplication application;
    private ProcessPrx process;
    private string[] arguments;

    private List<WGWindow> openedWindows;
    private List<WGWindow> closedWindows;

    private static WGRipperMonitor instance = null;

    public WGRipperMonitor(string name, string[] args, int delay)
    {
        Debug.Assert(instance == null, "Only one instance of WGRipperMonitor can exist");
        application = new WGApplication(name, delay);
        arguments = args;

        openedWindows = new List<WGWindow>();
        closedWindows = new List<WGWindow>();

        /* Set up self and application as servants */
        Adapter.Instance.add(this, Ice.Application.communicator().
            stringToIdentity("RipperMonitor"));
        instance = this;
    }

    public static WGRipperMonitor Instance
    {
        get
        {
            return instance;
        }
    }

    public override WindowPrx[] getRootWindows(Ice.Current context__)
    {
        // Check based on pid and that they are ControlType.Window
        PropertyCondition pidCondition =
            new PropertyCondition(AutomationElement.ProcessIdProperty,
                process.pid());
        PropertyCondition windowCondition =
            new PropertyCondition(AutomationElement.ControlTypeProperty,
                ControlType.Window);

        // Check the children of the automation root for windows
        AutomationElement root = AutomationElement.RootElement;
        AutomationElementCollection children =
            root.FindAll(TreeScope.Children,
            new AndCondition(pidCondition, windowCondition));
        Console.WriteLine("Found {0} windows", children.Count);

        // Register the windows
        WindowPrx[] windows = new WindowPrx[children.Count];
        for (int i = 0; i < children.Count; i++)
        {
            windows[i] = new WGWindow(children[i]).Proxy;
        }

        return windows;
    }

    public override void setUp(Ice.Current context__)
    {
        Automation.AddAutomationEventHandler(
            WindowPattern.WindowOpenedEvent,
            AutomationElement.RootElement, TreeScope.Subtree,
            OnWindowOpened);

        process = application.connect(arguments);
    }

    public override void cleanUp(Ice.Current context__)
    {
        process.disconnect();
        openedWindows.Clear();
        closedWindows.Clear();
        Automation.RemoveAllEventHandlers();
    }

    public override WindowPrx[] getOpenedWindowCache(Ice.Current context__)
    {
        WindowPrx[] windows = new WindowPrx[openedWindows.Count];
        for (int i = 0; i < windows.Length; i++)
        {
            windows[i] = openedWindows[i].Proxy;
        }
        return windows;
    }

    public override void resetWindowCache(Ice.Current context__)
    {
        openedWindows.Clear();
        closedWindows.Clear();
    }

    public override void closeWindow(WindowPrx window, Ice.Current context__)
    {
    }

    public override bool isExpandable(
        ComponentPrx component, WindowPrx window, Ice.Current context__)
    {
        return component.getEventList().Length > 0;
    }

    public override WindowPrx[] getClosedWindowCache(Ice.Current context__)
    {
        WindowPrx[] windows = new WindowPrx[closedWindows.Count];
        for (int i = 0; i < windows.Length; i++)
        {
            windows[i] = closedWindows[i].Proxy;
        }
        return windows;
    }

    private void OnWindowOpened(object src, AutomationEventArgs args)
    {
        AutomationElement openedWin = src as AutomationElement;
        if (openedWin != null)
        {
            WGWindow window = new WGWindow(openedWin);
            openedWindows.Add(window);

            AutomationEventHandler closeFunction =
                (object sender, AutomationEventArgs e) =>
                {
                    openedWindows.Remove(window);
                    closedWindows.Add(window);
                };

            Automation.AddAutomationEventHandler(
                WindowPattern.WindowClosedEvent,
                openedWin, TreeScope.Element, closeFunction);
        }
    }

    public override void expandGUI(ComponentPrx component, Ice.Current context__)
    {
        ActionPrx[] eventList = component.getEventList();

        // Use the first event if one is available
        if (eventList.Length > 0)
            eventList[0].perform(component);
    }
}

}