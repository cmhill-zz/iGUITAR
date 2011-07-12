package edu.umd.cs.guitar.model;

/**
 * Mock version of GUITARConstants file.  Not much going on here, just some
 * basic required static integers to be used.
 *
 * @author Sam Huang
 */
public class GUITARConstants {
  /**
   * No follow realationship between 2 events
   */
  public static final int NO_EDGE = 0;

  /**
   * Normal edges
   */
  public static final int FOLLOW_EDGE = 1;

  /**
   * The edges used to reach an event  
   */
  public static final int REACHING_EDGE = 2;

  /* No instantiations. */
  private GUITARConstants() { }
}
