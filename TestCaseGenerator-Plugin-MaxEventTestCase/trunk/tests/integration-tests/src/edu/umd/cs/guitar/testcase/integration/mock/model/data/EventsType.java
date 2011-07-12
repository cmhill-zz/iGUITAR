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
public class EventsType {

  protected List<EventType> event;

  public List<EventType> getEvent() {
    if (event == null) {
      event = new ArrayList<EventType>();
    }
    return this.event;
  }

  public void setEvent(List<EventType> event) {
    this.event = event;
  }

}
