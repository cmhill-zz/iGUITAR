package edu.umd.cs.guitar.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import edu.umd.cs.guitar.model.wrapper.AttributesTypeWrapper;

public class IceConstants {
	public static String RESOURCE_DIR = "resources";
	public static String CONFIG_DIR = RESOURCE_DIR + File.separator + "config";

	public static String TERMINAL_WIDGET_FILE = "terminal_widget.ign";

	public static List<AttributesTypeWrapper> sTerminalWidgetSignature =
        new LinkedList<AttributesTypeWrapper>();
	public static List<AttributesTypeWrapper> sIgnoredWidgetSignature =
        new LinkedList<AttributesTypeWrapper>();

	static List<String> GUI_PROPERTIES_LIST = Arrays.asList("opaque", "x", "y",
			"height", "width", "foreground", "background", "visible",
			"tooltip", "font", "accelerator", "enabled", "focusable",
			"selected", "text");

	public static List<String> getIdProperties() {
        if (ID_PROPERTIES == null) {
            guitar.model.ConstantsPrx constants = getProxy();
            ID_PROPERTIES = Arrays.asList(constants.getIdProperties());
        }
        return ID_PROPERTIES;
    }
    private static List<String> ID_PROPERTIES = null;

    public static final String LOG4J_PROPERTIES_FILE = "log4j.properties";

	public static int ICE_REPLAYER_TIMEOUT = 20000;

    private static guitar.model.ConstantsPrx getProxy() {
        if (constants == null) {
            Ice.Communicator ic = Ice.Application.communicator();
            Ice.ObjectPrx base = ic.stringToProxy("Constants:default -p " + PORT);
            constants = guitar.model.ConstantsPrxHelper.checkedCast(base);
        }
        return constants;
    }
    private static guitar.model.ConstantsPrx constants = null;

    public static short PORT = 10000;
}
