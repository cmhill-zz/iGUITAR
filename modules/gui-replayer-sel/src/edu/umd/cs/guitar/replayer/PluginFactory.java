package edu.umd.cs.guitar.replayer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import edu.umd.cs.guitar.replayer.sel.GReplayerConfiguration;

public class PluginFactory {

    public static ReplayerMain createReplayer(String[] args, PluginInfo plugin)
        throws Exception
    {
        // Find constructor for the configuration
        Constructor<GReplayerConfiguration> pluginInit =
            plugin.configType().getConstructor();
        GReplayerConfiguration configuration = pluginInit.newInstance();

        configuration.parseArguments(args);

        // Create the ReplayerMain with this configuration
        Constructor replayerInit =
            plugin.replayerType().getConstructor(GReplayerConfiguration.class);
        return (ReplayerMain) replayerInit.newInstance(configuration);
    }

}
