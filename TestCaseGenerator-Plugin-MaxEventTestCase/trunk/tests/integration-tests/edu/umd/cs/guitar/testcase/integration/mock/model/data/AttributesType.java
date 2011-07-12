package edu.umd.cs.guitar.model.data;

import java.util.ArrayList;
import java.util.List;

public class AttributesType {

  protected List<PropertyType> property;

  public List<PropertyType> getProperty() {
    if (property == null) {
      property = new ArrayList<PropertyType>();
    }
    return this.property;
  }

  public void setProperty(List<PropertyType> property) {
    this.property = property;
  }

}
