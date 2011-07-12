package edu.umd.cs.guitar.model.data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

public class TestCase {

  protected List<StepType> step;

  public List<StepType> getStep() {
    if (step == null) {
      step = new ArrayList<StepType>();
    }
    return this.step;
  }

  public void setStep(List<StepType> step) {
    this.step = step;
  }

}
