package edu.umd.cs.guitar.replayer;

import java.lang.Class;
import java.lang.reflect.Constructor;
import java.util.Arrays;

public class Launcher {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new Exception("Expected factory class as first argument");
        }

        // Retrieve plugin info from the command line
        Class class_ = Class.forName(args[0]);
        Constructor<PluginInfo> init = class_.getConstructor();
        PluginInfo plugin = init.newInstance();
        
        String[] pluginArgs = Arrays.copyOfRange(args, 1, args.length);
        ReplayerMain replayer = PluginFactory.createReplayer(pluginArgs, plugin);
        replayer.execute();
    }
}
