/*  
 *  Copyright (c) 2009-@year@. The GUITAR group at the University of Maryland. Names of owners of this group may
 *  be obtained by sending an e-mail to atif@cs.umd.edu
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 *  documentation files (the "Software"), to deal in the Software without restriction, including without 
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *  the Software, and to permit persons to whom the Software is furnished to do so, subject to the following 
 *  conditions:
 * 
 *  The above copyright notice and this permission notice shall be included in all copies or substantial 
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 *  LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 *  EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 *  THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package edu.umd.cs.guitar.model.wrapper;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import edu.umd.cs.guitar.model.data.EventEffectSetType;
import edu.umd.cs.guitar.model.data.EventEffectType;
import edu.umd.cs.guitar.model.data.EventTrace;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.model.data.TestCase;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao N. Nguyen </a>
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "guiStructure", "step" })
@XmlRootElement(name = "TestCase")
public class TestCaseWrapper extends TestCase {

	ObjectFactory factory = new ObjectFactory();

	/**
	 * 
	 */
	public TestCaseWrapper(TestCase testcase) {
		this.guiStructure = testcase.getGUIStructure();
		this.step = testcase.getStep();
	}

	public TestCase subSteps(int startIndex, int endIndex) {
		TestCase testcase = factory.createTestCase();

		for (int i = startIndex; i < endIndex; i++) {
			StepType step = this.step.get(i);
			testcase.getStep().add(i - startIndex, step);
		}
		return testcase;
	}

	public List<String> subSequence(int startIndex, int endIndex) {
		List<String> sequence = new ArrayList<String>();
		for (int i = startIndex; i < endIndex; i++) {
			StepType step = this.step.get(i);
			if (step == null)
				continue;
			sequence.add(i - startIndex, step.getEventId());
		}
		return sequence;
	}

	/**
	 * @return
	 */
	public int size() {
		return this.step.size();
	}

}
