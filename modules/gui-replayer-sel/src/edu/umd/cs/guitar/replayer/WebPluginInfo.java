package edu.umd.cs.guitar.replayer;

import java.lang.Class;

public class WebPluginInfo implements PluginInfo {
    public Class configType() {
        return WebReplayerConfiguration.class;
    }

    public Class replayerType() {
        return WebReplayerMain.class;
    }
}
