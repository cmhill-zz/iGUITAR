package edu.umd.cs.guitar.replayer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import edu.umd.cs.guitar.replayer.sel.NewGReplayerConfiguration;

public class PluginFactory {

    public static ReplayerMain createReplayer(String[] args, PluginInfo plugin)
        throws Exception
    {
        // Find constructor for the configuration
        Constructor<NewGReplayerConfiguration> pluginInit =
            plugin.configType().getConstructor();
        NewGReplayerConfiguration configuration = pluginInit.newInstance();

        configuration.parseArguments(args);

        // Create the ReplayerMain with this configuration
        Constructor replayerInit =
            plugin.replayerType().getConstructor(NewGReplayerConfiguration.class);
        return (ReplayerMain) replayerInit.newInstance(configuration);
    }

}
