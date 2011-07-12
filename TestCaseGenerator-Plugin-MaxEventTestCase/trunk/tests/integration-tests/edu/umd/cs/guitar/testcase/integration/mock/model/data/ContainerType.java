package edu.umd.cs.guitar.model.data;

public class ContainerType extends ComponentType {

  protected ContentsType contents;

  public ContentsType getContents() {
    return contents;
  }

  public void setContents(ContentsType value) {
    this.contents = value;
  }

}
