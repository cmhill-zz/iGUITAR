package edu.umd.cs.guitar.model.data;

public class ObjectFactory {


  public ObjectFactory() {
  }

  public ComponentType createComponentType() {
    throw new UnsupportedOperationException();
    //return new ComponentType();
  }

  public ContentsType createContentsType() {
    throw new UnsupportedOperationException();
    //return new ContentsType();
  }

  public AttributesType createAttributesType() {
    throw new UnsupportedOperationException();
    //return new AttributesType();
  }

  //public ComponentListType createComponentListType() {
    //throw new UnsupportedOperationException();
    ////return new ComponentListType();
  //}

  public EFG createEFG() {
    throw new UnsupportedOperationException();
    //return new EFG();
  }

  public EventGraphType createEventGraphType() {
    throw new UnsupportedOperationException();
    //return new EventGraphType();
  }

  public StepType createStepType() {
    return new StepType();
    //throw new UnsupportedOperationException();
  }

  public PropertyType createPropertyType() {
    //return new PropertyType();
    throw new UnsupportedOperationException();
  }

  public ContainerType createContainerType() {
    //return new ContainerType();
    throw new UnsupportedOperationException();
  }

  //public FullComponentType createFullComponentType() {
    ////return new FullComponentType();
    //throw new UnsupportedOperationException();
  //}

  public GUIStructure createGUIStructure() {
    //return new GUIStructure();
    throw new UnsupportedOperationException();
  }

  public RowType createRowType() {
    //return new RowType();
    throw new UnsupportedOperationException();
  }

  public EventType createEventType() {
    //return new EventType();
    throw new UnsupportedOperationException();
  }

  public TestCase createTestCase() {
    return new TestCase();
  }

  //public Configuration createConfiguration() {
    ////return new Configuration();
    //throw new UnsupportedOperationException();
  //}

  public EventsType createEventsType() {
    //return new EventsType();
    throw new UnsupportedOperationException();
  }

  public GUIType createGUIType() {
    //return new GUIType();
    throw new UnsupportedOperationException();
  }

}
