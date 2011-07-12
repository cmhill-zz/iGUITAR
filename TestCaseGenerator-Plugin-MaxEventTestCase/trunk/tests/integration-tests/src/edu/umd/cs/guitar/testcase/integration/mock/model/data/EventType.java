package edu.umd.cs.guitar.model.data;

/** 
 * Mock implementation of <code>EventGraphType</code>, gives basically the same
 * functionality.  The existing implementation is very simple, and it was
 * effecitvely copied verbatim after removing the auto-generated Xml
 * declaratives.  An <code>EventGraphType</code> contains information about the
 * adjacency matrix of an EFG.
 *
 * @author Sam Huang
 */
public class EventType {

  protected String eventId;
  protected String widgetId;
  //protected String type;
  protected boolean initial;
  //protected String action;
  //protected AttributesType optional;

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String value) {
    this.eventId = value;
  }

  public String getWidgetId() {
    return widgetId;
  }

  public void setWidgetId(String value) {
    this.widgetId = value;
  }

  /**
   * This is used but the return value is never considered, so we just return the empty string right now. 
   */
  public String getType() {
    return "";
  }

  public void setType(String value) {
  }

  public boolean isInitial() {
    return initial;
  }

  public void setInitial(boolean value) {
    this.initial = value;
  }

  public String getAction() {
    //return action;
    throw new UnsupportedOperationException();
  }

  public void setAction(String value) {
    //this.action = value;
    throw new UnsupportedOperationException();
  }

  public AttributesType getOptional() {
    //return optional;
    throw new UnsupportedOperationException();
  }

  public void setOptional(AttributesType value) {
    //this.optional = value;
    throw new UnsupportedOperationException();
  }

}
