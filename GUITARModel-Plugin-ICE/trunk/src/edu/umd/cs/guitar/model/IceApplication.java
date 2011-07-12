package edu.umd.cs.guitar.model;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import edu.umd.cs.guitar.exception.ApplicationConnectException;
import edu.umd.cs.guitar.exception.InvalidProxyException;
import edu.umd.cs.guitar.util.GUITARLog;

public class IceApplication extends GApplication {
    private guitar.model.ApplicationPrx application;
    private guitar.model.ProcessPrx process;

    /**
     * Connects to the Application servant. There must be an Application
     * servant available on port 10000 at the given host.
     *
     * This can only be used after an instance of Ice.Application has been
     * initialized.
     *
     * @param host The host to connect to.
     * @throws InvalidProxyException If the Application servant doesn't exist
     *                               at the host.
     */
    public IceApplication()
    {
        // Get the Ice Communicator from the singleton Ice.Application
        Ice.Communicator ic = Ice.Application.communicator();
        assert ic != null : "Ice Communicator uninitialized";

        // Retrieve the application proxy at host:PORT
        Ice.ObjectPrx base = ic.stringToProxy(
            "Application:default -p " + IceConstants.PORT);
        application = guitar.model.ApplicationPrxHelper.checkedCast(base);

        // Throw an invalid proxy if we couldn't successfully retrieve the proxy
        if (application == null) {
            throw new InvalidProxyException("Application");
        }
    }

    /**
     * Connects to the application with no arguments.
     *
     * @throws ApplicationConnectException An exception occurred while
     *         connecting to the application.
     */
    public void connect() throws ApplicationConnectException
    {
        connect(new String[]{});
    }

    /**
     * Connects to the application with the given arguments. This method should
     * only be called once for the given lifetime of the server and client
     * programs.
     *
     * It is undefined behavior to call this function more than once.
     *
     * @throws ApplicationConnectException An exception occurred while
     *         connecting to the application.
     */
    public void connect(String[] args)
        throws ApplicationConnectException
    {
        if (process != null) {
            GUITARLog.log.warn("Ignoring second connect attempt");
            return;
        }

        try {
            process = application.connect(args);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationConnectException();
        }
    }

    /**
     * Disconnects/shutsdown the application on the server side.
     *
     * Disconnects the currently connected process. If no process is
     * connected to this application, this does nothing.
     */
    public void disconnect()
    {
        if (process != null) {
            process.disconnect();
            process = null;
        }
    }

    /**
     * Gets all of the root windows for the currently connected process.
     * Returns an empty set if no process is connected.
     */
    public Set<GWindow> getAllWindow()
    {
        if (process != null) {
            // Ice does not have a Set type, so we retrieve a map instead
            Map<Integer, guitar.model.WindowPrx> windowMap =
                process.getAllWindow();
            
            // Convert the values of the map to a set
            HashSet<GWindow> windows = new HashSet<GWindow>();
            for (guitar.model.WindowPrx proxy : windowMap.values()) {
                windows.add(new IceWindow(proxy));
            }
            return windows;
        } else {
            return new HashSet<GWindow>();
        }
    }
}
