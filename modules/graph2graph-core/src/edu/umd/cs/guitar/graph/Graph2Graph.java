/*
 *  Copyright (c) 2009-@year@. The  GUITAR group  at the University of
 *  Maryland. Names of owners of this group may be obtained by sending
 *  an e-mail to atif@cs.umd.edu
 *
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files
 *  (the "Software"), to deal in the Software without restriction,
 *  including without limitation  the rights to use, copy, modify, merge,
 *  publish,  distribute, sublicense, and/or sell copies of the Software,
 *  and to  permit persons  to whom  the Software  is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included
 *  in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 *  OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY,  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *  IN NO  EVENT SHALL THE  AUTHORS OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY
 *  CLAIM, DAMAGES OR  OTHER LIABILITY,  WHETHER IN AN  ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 *  SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package edu.umd.cs.guitar.graph;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import org.apache.log4j.Level;

import edu.umd.cs.guitar.graph.plugin.G2GPlugin;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.Mapping;
import edu.umd.cs.guitar.model.data.MappingType;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao N. Nguyen </a>
 * 
 */
public class Graph2Graph {
   /**
    * @param args
    */
   public static
   void main(String[] args)
   {
      // Parse command line arguments
      Graph2GraphConfiguration configuration =
         new Graph2GraphConfiguration();

      CmdLineParser parser =
         new CmdLineParser(configuration);
      try {
         parser.parseArgument(args);
         checkMandatoryArgs();

         // By default the plugin is put in the same package with G2GPlugin
         if (!Graph2GraphConfiguration.PLUGIN.contains(
              G2GPlugin.class.getPackage().getName())
            ) {
            Graph2GraphConfiguration.PLUGIN =
               G2GPlugin.class.getPackage().getName()
               + "." + Graph2GraphConfiguration.PLUGIN;
         }

         Class<?> converterClass =
            Class.forName(Graph2GraphConfiguration.PLUGIN);

         EFG inputGraph = (EFG) IO.readObjFromFile(
            Graph2GraphConfiguration.INPUT_FILE, EFG.class);

         Constructor<?> constructor = converterClass
            .getConstructor(new Class[] { EFG.class });

         G2GPlugin converter = (G2GPlugin)
                                constructor.newInstance(inputGraph);

         // Uses 'converter' to validate parameters
         checkOptionalArgs(converter);

         if (converter.parseGraph()) {
            EFG outputGraph = converter.getGraph();
            Mapping mapping = converter.getMap();

            /*
             * Write the output graph if the plugin wants an output
             * graph to be written.
             */
            if (converter.shouldGenerateGraph()) {
               IO.writeObjToFile(outputGraph,
                                 Graph2GraphConfiguration.OUTPUT_FILE);
            }

            /**
             * Write the output mapping file if the map file
             * needs to be generated for this plugin.
             */
            if (converter.shouldGenerateMap()) {
               IO.writeObjToFile(mapping,
                                 Graph2GraphConfiguration.MAPPING_FILE);
            }

         } else {
            GUITARLog.Error("Failed to convert the input graph");
         }

         printInfo(converter);

      } catch (CmdLineException e) {
         // Print usage
         System.err.println(e.getMessage());

         System.err.println("Usage:\n\n\tjava [JVM options]\n\t"
            + Graph2Graph.class.getName()
            + "\n\t[Graph2Graph converter options]\n");

         System.err
            .println("Where [Graph2Graph converter options] are:\n");

         parser.printUsage(System.err);
         System.err.println();

         System.exit(0);

      } catch (ClassNotFoundException e) {
         System.out.println("Plugin cannot be found. Please make sure " +
                            "that the plugin name is correct and the " +
                             "corresponding .jar file can be reached.");

         e.printStackTrace();

      } catch (InstantiationException e) {
         System.out.println("Plugin is defined as an Abstract class, " +
                            "or an interface, or its constructor is not " +
                            "accessible without parameters.");
         e.printStackTrace();

      } catch (IllegalAccessException e) {
         System.out.println("Plugin is not accessible");
         e.printStackTrace();

      } catch (SecurityException e) {
         System.out.println("Plugin is not accessible");
         e.printStackTrace();

      } catch (NoSuchMethodException e) {
         e.printStackTrace();

      } catch (IllegalArgumentException e) {
         e.printStackTrace();

      } catch (InvocationTargetException e) {
         e.printStackTrace();
      }
   }

   /**
    * Print common operation info
    */
   private static
   void printInfo(G2GPlugin converter)
   {
      GUITARLog.Info("Input Graph File  : " +
         Graph2GraphConfiguration.INPUT_FILE);
      GUITARLog.Info("Output Graph File : " +
         Graph2GraphConfiguration.OUTPUT_FILE);
      GUITARLog.Info("Mapping File      : " +
         Graph2GraphConfiguration.MAPPING_FILE);
      GUITARLog.Info("Plugin            : " +
         Graph2GraphConfiguration.PLUGIN);

      // Let the converter print anything it wants
      converter.printInfo();
   }

   /**
    * 
    * Check for command-line arguments
    * 
    * @throws CmdLineException
    * 
    */
   private static
   void checkMandatoryArgs() throws CmdLineException
   {
      boolean isPrintUsage = false;

      // Check argument
      if (Graph2GraphConfiguration.HELP) {
         throw new CmdLineException("");
      }

      // Input graph file is mandatory
      if (Graph2GraphConfiguration.INPUT_FILE == null) {
         System.err.println("missing '-i' argument");
         isPrintUsage = true;
      }

      // Plugin name is mandatory
      if (Graph2GraphConfiguration.PLUGIN == null) {
         System.err.println("missing '-p' argument");
         isPrintUsage = true;
      }

      if (isPrintUsage) {
         throw new CmdLineException("");
      }
   }

   /**
    * 
    * Check for command-line arguments
    * 
    * @throws CmdLineException
    * 
    */
   private static
   void checkOptionalArgs(G2GPlugin converter)
   throws CmdLineException
   {
      boolean isPrintUsage = false;

      // Output graph file is optional
      if (converter.shouldGenerateGraph() &&
          Graph2GraphConfiguration.OUTPUT_FILE == null) {
         System.err.println("missing '-o' argument");
         isPrintUsage = true;
      }

      // Plugin name is optional
      if (converter.shouldGenerateMap() &&
          Graph2GraphConfiguration.MAPPING_FILE == null) {
         System.err.println("missing '-m' argument");
         isPrintUsage = true;
      }

      // Plugin name is optional
      if (converter.requireExtraArgs() &&
          Graph2GraphConfiguration.EXTRA_ARG == null) {
         System.err.println("missing '-x' argument");
         isPrintUsage = true;
      }

      if (isPrintUsage) {
         throw new CmdLineException("");
      }
   }

} // End of class
