package edu.umd.cs.guitar.replayer;

import java.lang.Class;

public interface PluginInfo {
    public Class configType();
    public Class replayerType();
}
