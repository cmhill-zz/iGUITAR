package edu.umd.cs.guitar.model;

/**
 * Helper class for testing.  Contains some globally-accessible variables that
 * we reuse in several places, avoids retyping, etc.
 *
 * @author Sam Huang
 */
public class EFGConfiguration {
  /** File name to request a toy EFG example involving a single event. */
  public static final String SINGLE_EVENT_EFG_FILE = "SingleEvent.EFG.xml";

  /** File name to request a toy EFG example involving a two-event cycle. */
  public static final String TWO_CYCLE_EFG_FILE = "TwoCycle.EFG.xml";

  /** File name to request a toy EFG example involving a two-event EFG with a reaching edges. */
  public static final String REACHING_EFG_FILE = "Reaching.EFG.xml";

  /** File name to request a toy EFG example involving a three-event EFG with a reaching edges. */
  public static final String THREE_EVENT_EFG_FILE = "ThreeEvent.EFG.xml";

  /** File name to request a toy EFG example involving a two-event disjoint EFG. */
  public static final String DISJOINT_EFG_FILE = "Disjoint.EFG.xml";

  /** File name to request a toy EFG example involving a three-event EFG with a cycle. */
  public static final String FINITE_EFG_FILE = "Finite.EFG.xml";

  /* Make private so no instantiations. */
  private EFGConfiguration() { }
}
