package edu.umd.cs.guitar.model.data;

import java.util.ArrayList;
import java.util.List;

public class ContentsType {

  protected List<ComponentType> widgetOrContainer;

  public List<ComponentType> getWidgetOrContainer() {
    if (widgetOrContainer == null) {
      widgetOrContainer = new ArrayList<ComponentType>();
    }
    return this.widgetOrContainer;
  }

  public void setWidgetOrContainer(List<ComponentType> widgetOrContainer) {
    this.widgetOrContainer = widgetOrContainer;
  }

}
