package edu.umd.cs.guitar.ripper;

import java.util.List;
import java.util.LinkedList;

import org.netbeans.jemmy.QueueTool;

import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.event.IceEvent;
import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.IceComponent;
import edu.umd.cs.guitar.model.IceConstants;
import edu.umd.cs.guitar.model.IceWindow;
import edu.umd.cs.guitar.util.GUITARLog;

public class IceRipperMonitor extends GRipperMonitor
{
    private guitar.ripper.RipperMonitorPrx monitor;
    private IceRipperConfiguration configuration;

    public IceRipperMonitor(IceRipperConfiguration config)
    {
        this.configuration = config;

        Ice.Communicator ic = Ice.Application.communicator();
        assert ic != null : "Ice Communicator uninitialized";

        Ice.ObjectPrx base = ic.stringToProxy("RipperMonitor:default -p " + IceConstants.PORT);
        monitor = guitar.ripper.RipperMonitorPrxHelper.checkedCast(base);
    }

    public List<GWindow> getRootWindows()
    {
        guitar.model.WindowPrx[] windows = monitor.getRootWindows();
        List<GWindow> list = new LinkedList<GWindow>();
        for (guitar.model.WindowPrx w : windows) {
            list.add(new IceWindow(w));
        }
        return list;
    }

    public void setUp()
    {
        monitor.setUp();
    }

    public void cleanUp()
    {
        monitor.cleanUp();
    }

    public boolean isNewWindowOpened()
    {
        List<GWindow> openedWindows = getOpenedWindowCache();
        return openedWindows.size() > 0;
    }

    public boolean isWindowClosed()
    {
        List<GWindow> closedWindows = getClosedWindowCache();
        return closedWindows.size() > 0;
    }

    public LinkedList<GWindow> getOpenedWindowCache()
    {
        guitar.model.WindowPrx[] windows = monitor.getOpenedWindowCache();
        LinkedList<GWindow> windowList = new LinkedList<GWindow>();
        for (int i = 0; i < windows.length; i++) {
            windowList.add(new IceWindow(windows[i]));
        }
        return windowList;
    }

    public void resetWindowCache()
    {
        monitor.resetWindowCache();
    }

    public void closeWindow(GWindow window)
    {
        guitar.model.WindowPrx proxy = ((IceWindow) window).getProxy();
        monitor.closeWindow(proxy);
    }

    public boolean isIgnoredWindow(GWindow window)
    {
        boolean ignored =
            IceComponent.matchesProperties(
                window.getContainer(), IceConstants.sIgnoredWidgetSignature);
        return ignored;
    }

    public void expandGUI(GComponent component)
    {
        if (component == null)
            return;

        GUITARLog.log.info("Expanding *" + component.getTitle() + "*...");

        monitor.expandGUI(((IceComponent) component).getProxy());

        GUITARLog.log.info("Waiting " + configuration.DELAY
                           + "ms for a new window to open");

        new QueueTool().waitEmpty(configuration.DELAY);
    }

    boolean isExpandable(GComponent component, GWindow window)
    {
        if (component.getTypeVal().equals(GUITARConstants.TERMINAL)) {
            GUITARLog.log.info("Ignoring terminal widget *" +
                               component.getTitle() + "*");
            return false;
        }

        IceComponent c = (IceComponent) component;
        IceWindow w = (IceWindow) window;

        return monitor.isExpandable(c.getProxy(), w.getProxy());
    }

    LinkedList<GWindow> getClosedWindowCache()
    {
        guitar.model.WindowPrx[] windows = monitor.getClosedWindowCache();
        LinkedList<GWindow> windowList = new LinkedList<GWindow>();
        for (int i = 0; i < windows.length; i++) {
            windowList.add(new IceWindow(windows[i]));
        }
        return windowList;
    }
}
