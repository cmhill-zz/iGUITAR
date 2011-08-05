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
package edu.umd.cs.guitar.testcase;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.model.data.TestCase;
import edu.umd.cs.guitar.testcase.Abstract2ExecutableTCConfiguration;
import edu.umd.cs.guitar.util.GraphUtil;
import edu.umd.cs.guitar.util.GUITARLog;


/**
 * Prefix a testcase with the path to root event, if not already
 * prefixed.
 * 
 * <p>
 * 
 * @author <a href="mailto:ishan@cs.umd.edu"> Ishan Banerjee </a>
 * 
 */
public class PathToRoot
{
   // EFG to use for patching testcase
   EFG efg;

   // Graph utility for computing path to root
   GraphUtil graphUtil;

   // Global factory
   ObjectFactory factory = new ObjectFactory();


   /**
    * @param map
    */
   public
   PathToRoot(EFG efg)
   {
      super();
      this.efg = efg;
      this.graphUtil = new GraphUtil(efg);
   }

   public static void
   main(String args[])
   {
      Abstract2ExecutableTCConfiguration configuration =
         new Abstract2ExecutableTCConfiguration();
      CmdLineParser parser = new CmdLineParser(configuration);

      try {
         parser.parseArgument(args);
         checkArgs();

         EFG efg =
            (EFG) IO.readObjFromFile(
             Abstract2ExecutableTCConfiguration.EFG_FILE, EFG.class);
         PathToRoot pathToRoot = new PathToRoot(efg);

         String sInputName = Abstract2ExecutableTCConfiguration.INPUT_FILE;
         String sOutputName = Abstract2ExecutableTCConfiguration.OUTPUT_FILE;
         boolean isExpandDir = Abstract2ExecutableTCConfiguration.IS_DIRECTORY;

         if (isExpandDir) {
            pathToRoot.patchTestSuite(sInputName, sOutputName);
         } else {
            pathToRoot.patchTestCase(sInputName, sOutputName);
         }
      } catch (CmdLineException e) {
         System.err.println(e.getMessage());
         System.err.println();
         System.err.println("Usage: java [JVM options] "
                            + TestCaseExpander.class.getName()
                            + " [TestCaseExpander  converter options] \n");
         System.err.println(
            "where [TestCaseExpander converter options] include:");
         System.err.println();
         parser.printUsage(System.err);

        System.exit(0);
     }
   }


   /**
    * Patch all testcases in a directory.
    *
    * @param sInputName
    * @param sOutputName
    */
   private void
   patchTestSuite(String sInputName, String sOutputName)
   {
      File iDir = new File(sInputName);

      if (!iDir.isDirectory()) {
         GUITARLog.Error(sInputName + " is not a directory");

         return;
      }

      new File(sOutputName).mkdir();
      File[] files = iDir.listFiles();

      if (files != null) {
         for (int i = 0; i < files.length; i++) {

            String inFilePath = files[i].getPath();
            String outFilePath = sOutputName + File.separator
                                 + files[i].getName();
            try {
               GUITARLog.Info("--------------------------------------");
               GUITARLog.Info("#: " + i + " " +
                              inFilePath + " -> " + outFilePath);
               patchTestCase(inFilePath, outFilePath);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      }
   }


  /**
   * Patch a single testcase.
   *
   * @param sInputFile
   * @param sOutputFile
   */
   private void
   patchTestCase(String sInputFile, String sOutputFile)
   {
      TestCase iTestCase = (TestCase) IO.readObjFromFile(sInputFile,
                                                         TestCase.class);

      TestCase oTestCase = factory.createTestCase();

      List<StepType> iStepList = iTestCase.getStep();

      if (iStepList == null) {
         return;
      }
      if (iStepList.size() == 0) {
         return;
      }

      // Expand path to root
      StepType initStep = iStepList.get(0);
      LinkedList<EventType> pathToRoot =
         graphUtil.pathToRoot(
                              graphUtil.lookupEvent(iStepList.
                                 get(0).
                                 getEventId())
                             );

      if (pathToRoot != null) {
         if (pathToRoot.size() > 0) {
            pathToRoot.removeLast();
         }

         for (int i = 0; i < pathToRoot.size(); i++) {
            StepType step = factory.createStepType();

            step.setEventId(pathToRoot.get(i).getEventId());
            step.setReachingStep(true);
            oTestCase.getStep().add(step);
         } // for
      }

      // Add the input testcase steps to the output testcase
      for (StepType step : iStepList) {
         step.setReachingStep(false);
         oTestCase.getStep().add(step);
      }

      // Write the new patched testcase
      IO.writeObjToFile(oTestCase, sOutputFile);
   }


   /**
    * 
    * Check for command-line arguments
    * 
    * @throws CmdLineException
    * 
    */
   private static void
   checkArgs() throws CmdLineException
   {
      // Check argument
      if (Abstract2ExecutableTCConfiguration.HELP) {
         throw new CmdLineException("");
      }

      boolean isPrintUsage = false;

      if (Abstract2ExecutableTCConfiguration.INPUT_FILE == null) {
         System.err.println("missing '-i' argument");
         isPrintUsage = true;
      }

      if (Abstract2ExecutableTCConfiguration.OUTPUT_FILE == null) {
         System.err.println("missing '-o' argument");
         isPrintUsage = true;
      }

      if (Abstract2ExecutableTCConfiguration.EFG_FILE == null) {
         System.err.println("missing '-e' argument");
         isPrintUsage = true;
      }

      if (isPrintUsage) {
         throw new CmdLineException("");
      }
   }


   /**
    * Print statistics.
    */
   private static void
   printInfo()
   {
      GUITARLog.Info("Intput File : "
         + Abstract2ExecutableTCConfiguration.INPUT_FILE);
      GUITARLog.Info("Output File : "
         + Abstract2ExecutableTCConfiguration.OUTPUT_FILE);
      GUITARLog.Info("EFG File    : "
         + Abstract2ExecutableTCConfiguration.EFG_FILE);
   }

} // Class
