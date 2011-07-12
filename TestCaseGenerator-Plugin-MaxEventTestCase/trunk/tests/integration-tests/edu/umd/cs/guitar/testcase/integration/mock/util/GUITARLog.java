package edu.umd.cs.guitar.util;

import org.apache.log4j.Logger;


/**
 *
 * Mock logging implementation for GUITARLog, which is actually the same class.
 * This can be done because all that GUITARLog (currently) does is outsource
 * its work to the apache log4j logging class, which we assume to work fine (do
 * unit tests for apache's code if you want...)
 *  
 * @author Sam Huang
 *
 */
public class GUITARLog {

  /** Defer all work to the apache logging library. */
  public static Logger log = Logger.getLogger(GUITARLog.class);

  /* No instantiations. */
  private GUITARLog() { }
}
