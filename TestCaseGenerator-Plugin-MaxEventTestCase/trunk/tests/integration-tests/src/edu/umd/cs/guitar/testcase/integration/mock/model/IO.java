package edu.umd.cs.guitar.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import edu.umd.cs.guitar.model.data.EFG;

import java.util.Hashtable;
import java.util.Map;

/**
 * 
 * Mock IO object to be used in conjunction with TestCastGenerator integrated
 * tests.  Returns simple but contrived EFG objects to calls made by
 * <code>readObjFromFile</code> (the only objects that are requested by the
 * TestCaseGenerator tool).
 * 
 * @author Sam  Huang
 */
public class IO {

  /**
   * A global map of all objects requested to be written to file, where the
   * keys are the objects and the values are the filenames.  This would be
   * broken if we ask IO to write the same object to multiple places.
   */
  public static Map <Object, String> objs2fileNames = new Hashtable<Object, String> ();

  /* We don't want any instantiations, as IO is designed to have only static
   * methods. */
  private IO() {
  }

  /**
   * Mock implentation of the <code>readObjFromFile</code>, with an input stream as an
   * argument.  This is not needed, and we throw an
   * <code>UnsupportedOperationException</code> if called.
   */
  public static Object readObjFromFile(FileInputStream is, Class<?> cls) {
    throw new UnsupportedOperationException();
  }

  /**
   * Mock implementation of the <code>readObjFromFile</code>, with a String
   * file name as an argument.  This is simply a series of special-case checks,
   * for which we return something from our <code>IOMockFactory</code> helper
   * class.
   */
  public static Object readObjFromFile(String sFileName, Class<?> cls) {
    if(sFileName.equals(EFGConfiguration.SINGLE_EVENT_EFG_FILE) && cls == EFG.class) {
      return IOMockFactory.getSingleEventEFG();
    } else if(sFileName.equals(EFGConfiguration.TWO_CYCLE_EFG_FILE) && cls == EFG.class) {
      return IOMockFactory.getTwoCycleEFG();
    } else if(sFileName.equals(EFGConfiguration.REACHING_EFG_FILE) && cls == EFG.class) {
      return IOMockFactory.getReachingEFG();
    } else if(sFileName.equals(EFGConfiguration.THREE_EVENT_EFG_FILE) && cls == EFG.class) {
      return IOMockFactory.getThreeEventEFG();
    } else if(sFileName.equals(EFGConfiguration.DISJOINT_EFG_FILE) && cls == EFG.class) {
      return IOMockFactory.getDisjointEFG();
    } else if(sFileName.equals(EFGConfiguration.FINITE_EFG_FILE) && cls == EFG.class) {
      return IOMockFactory.getFiniteEFG();
    }

    return null;
  }

  /**
   * Unsupported version, use the version with the String argument instead.
   */
  public static void writeObjToFile(Object object, OutputStream os) {
    throw new UnsupportedOperationException();
  }

  /**
   * Mock implementation of writing objects to files, stores in a map for later
   * lookup.
   */
  public static void writeObjToFile(Object object, String sFileName) {
    System.out.println("IO: Writing " + object +  " to " + sFileName);
    objs2fileNames.put(object, sFileName);
  }

  /**
   * Clears the mapping stored that records where each object is requested to
   * be written to.
   * */
  public static void clearOutputRecord() {
    objs2fileNames.clear();
  }
}
