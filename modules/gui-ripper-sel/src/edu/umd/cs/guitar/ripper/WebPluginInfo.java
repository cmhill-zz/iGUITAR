package edu.umd.cs.guitar.ripper;

import java.lang.Class;

public class WebPluginInfo implements PluginInfo {
    public Class configType() {
        return WebRipperConfiguration.class;
    }

    public Class ripperType() {
        return WebRipperMain.class;
    }
}
