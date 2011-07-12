package edu.umd.cs.guitar.model.data;

import java.util.ArrayList;
import java.util.List;

public class StepType {

  protected String eventId;
  //protected boolean reachingStep;
  //protected List<String> parameter;
  //protected AttributesType optional;
  //protected GUIStructure guiStructure;

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String value) {
    this.eventId = value;
  }

  public boolean isReachingStep() {
    throw new UnsupportedOperationException();
    //return reachingStep;
  }

  public void setReachingStep(boolean value) {
    throw new UnsupportedOperationException();
    //this.reachingStep = value;
  }

  public List<String> getParameter() {
    throw new UnsupportedOperationException();
    //if (parameter == null) {
      //parameter = new ArrayList<String>();
    //}
    //return this.parameter;
  }

  public AttributesType getOptional() {
    throw new UnsupportedOperationException();
    //return optional;
  }

  public void setOptional(AttributesType value) {
    throw new UnsupportedOperationException();
    //this.optional = value;
  }

  public GUIStructure getGUIStructure() {
    throw new UnsupportedOperationException();
    //return guiStructure;
  }

  public void setGUIStructure(GUIStructure value) {
    throw new UnsupportedOperationException();
    //this.guiStructure = value;
  }

  public void setParameter(List<String> parameter) {
    throw new UnsupportedOperationException();
    //this.parameter = parameter;
  }

}
