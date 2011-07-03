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

/**
 * 
 */
package edu.umd.cs.guitar.util;

import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Global runtime log for GUITAR
 * 
 * <p>
 * 
 * @author Bao Nguyen
 * 
 */
public class GUITARLog {
 
   /**
    * 
    */
   public static final
   String LOGFILE_NAME_SYSTEM_PROPERTY = "logfile.name";
 
   public static
   Logger log;
 
 
   /**
    * 
    */
   private static final
   String LOG_LAYOUT_PATTERN = "%-6r [%t] %-5p - %m%n";


   /**
    * 
    */
   private static final
   String GUITAR_DEFAULT_LOG = "GUITAR-Default.log";


   /**
    * Logging level
    */
   private static
   Level level = Level.DEBUG;


   static {
      log = Logger.getLogger("GUITARLog");

      PatternLayout layout = new org.apache.log4j.PatternLayout();
      layout.setConversionPattern(LOG_LAYOUT_PATTERN);

      ConsoleAppender stdout = new ConsoleAppender(layout);
      log.addAppender(stdout);

      FileAppender file = null;

      String logFileName = System.getProperty(LOGFILE_NAME_SYSTEM_PROPERTY);

      if (logFileName == null) {
         logFileName = GUITAR_DEFAULT_LOG;
      }

      try {
         file = new FileAppender(layout, logFileName, false);
      } catch (IOException e) {
         e.printStackTrace();
      }

      log.addAppender(file);
      log.setLevel(level);
   }

   /**
    * Log the given string 'str' as Debug
    *
    * @return
    */
   public static
   void Debug(String str) {
      if (Level.DEBUG.isGreaterOrEqual(GUITARLog.level)) {
         System.out.println("Debug: " + str);
      }
   }

   /**
    * Log the given string 'str' as Info.
    *
    * @return
    */
   public static
   void Info(String str) {
      if (Level.INFO.isGreaterOrEqual(GUITARLog.level)) {
         System.out.println("Info: " + str);
      }
   }

   /**
    * Log the given string 'str' as Error.
    *
    * @return
    */
   public static
   void Error(String str) {
      if (Level.ERROR.isGreaterOrEqual(GUITARLog.level)) {
         System.out.println("Error: " + str);
      }
   }

} // End of class
