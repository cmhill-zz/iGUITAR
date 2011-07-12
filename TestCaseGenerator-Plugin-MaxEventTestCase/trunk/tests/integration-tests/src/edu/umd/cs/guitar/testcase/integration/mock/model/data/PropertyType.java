package edu.umd.cs.guitar.model.data;

import java.util.ArrayList;
import java.util.List;

public class PropertyType {

  protected String name;
  protected List<String> value;

  public String getName() {
    return name;
  }

  public void setName(String value) {
    this.name = value;
  }

  public List<String> getValue() {
    if (value == null) {
      value = new ArrayList<String>();
    }
    return this.value;
  }

  public void setValue(List<String> value) {
    this.value = value;
  }

}
