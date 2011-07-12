package edu.umd.cs.guitar.replayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.event.IceEvent;
import edu.umd.cs.guitar.exception.ComponentNotFound;
import edu.umd.cs.guitar.exception.InvalidProxyException;
import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.IceApplication;
import edu.umd.cs.guitar.model.IceConstants;
import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.util.GUITARLog;

public class IceReplayerMonitor extends GReplayerMonitor {

    public void connectToApplication() {
        // Load command line arguments
        String[] args;

        if (IceReplayerConfiguration.ARGUMENT_LIST != null) {
            args = IceReplayerConfiguration.ARGUMENT_LIST
                .split(GUITARConstants.CMD_ARGUMENT_SEPARATOR);
        } else {
            args = new String[0];
        }

        // Start the application on the server
        application.connect(args);
    }

    public void setUp() {
        application = new IceApplication();
    }

    public void cleanUp() {
        ((IceApplication) application).disconnect();
    }

    public GWindow getWindow(String title) {
        Set<GWindow> set = application.getAllWindow();

        for (GWindow win : set) {
            if (title.equals(win.getTitle())) {
                return win;
            }
        }

        throw new ComponentNotFound();
    }

    public GEvent getAction(String actionName) {
        GUITARLog.log.info("Getting action *" + actionName + "*");

        Ice.Communicator ic = Ice.Application.communicator();
        Ice.ObjectPrx base =
            ic.stringToProxy(actionName + ":default -p " + IceConstants.PORT);
        guitar.event.ActionPrx action =
            guitar.event.ActionPrxHelper.checkedCast(base);
        if (action == null) {
            GUITARLog.log.error("Error in getting action *" +
                                actionName + "*");
            return null;
        } else {
            return new IceEvent(action);
        }
    }

    public Object getArguments(String action) {
        EventData event = new EventData(action);
        return event.getParameters();
    }

    public List<PropertyType> selectIDProperties(ComponentType component) {
        if (component == null)
            return new ArrayList<PropertyType>();

        List<PropertyType> retIDProperties = new ArrayList<PropertyType>();

        AttributesType attributes = component.getAttributes();
        List<PropertyType> properties = attributes.getProperty();
        for (PropertyType p : properties) {
            if (IceConstants.getIdProperties().contains(p.getName()))
                retIDProperties.add(p);
        }
        return retIDProperties;
    }

}
