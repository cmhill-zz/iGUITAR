package edu.umd.cs.guitar.ripper;

import java.lang.Class;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.apache.commons.cli.ParseException;

public class Launcher {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, Exception {
        try {
            // First argument is the name of the factory class
            if (args.length == 0) {
                throw new Exception("Expected factory class as first argument");
            }
            
            // Retrieve plugin info from the command line
            Class class_ = Class.forName(args[0]);
            Constructor<PluginInfo> init = class_.getConstructor();
            PluginInfo plugin = init.newInstance();
            
            String[] pluginArgs = Arrays.copyOfRange(args, 1, args.length);
            RipperMain ripper = PluginFactory.createRipper(pluginArgs, plugin);
            ripper.execute();
        } catch (ParseException e) {
            // Crash and die fucker
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.exit(0);
    }
}
