package edu.umd.cs.guitar.model.data;

import java.util.ArrayList;
import java.util.List;

/** 
 * Mock implementation of <code>RowType</code>, gives basically the same
 * functionality.  The existing implementation is very simple, and it was
 * effecitvely copied verbatim after removing the auto-generated Xml
 * declaratives.
 *
 * @author Sam Huang
 */
public class RowType {

  protected List<Integer> e;

  /**
   * Implementation of a row accessor, namely all out-edges of an event.
   */
  public List<Integer> getE() {
    if (e == null) {
      e = new ArrayList<Integer>();
    }
    return this.e;
  }

  /**
   * Implementation of a row setter.
   */
  public void setE(List<Integer> e) {
    this.e = e;
  }

}
