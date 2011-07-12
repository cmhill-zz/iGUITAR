package edu.umd.cs.guitar.model.data;

import java.util.ArrayList;
import java.util.List;


/** 
 * Mock implementation of <code>EventGraphType</code>, gives basically the same
 * functionality.  The existing implementation is very simple, and it was
 * effecitvely copied verbatim after removing the auto-generated Xml
 * declaratives.  An <code>EventGraphType</code> contains information about the
 * adjacency matrix of an EFG.
 *
 * @author Sam Huang
 */
public class EventGraphType {

  /**
   * An ordered list of rows, overall representing the adjacency matrix of an EFG.
   */
  protected List<RowType> row;

  /**
   * Implementation of an accessor of the adjacency matrix accessor.
   */
  public List<RowType> getRow() {
    if (row == null) {
      row = new ArrayList<RowType>();
    }
    return this.row;
  }

  /**
   * Implementation of a setter of the adjacency matrix.
   */
  public void setRow(List<RowType> row) {
    this.row = row;
  }

}
