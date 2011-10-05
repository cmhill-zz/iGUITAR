package edu.umd.cs.guitar.replayer;



import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.guitar.model.data.StepType;
import edu.umd.cs.guitar.model.data.TestCase;
/**
 * This is the mock object for TestCase
 * @see edu.umd.cs.guitar.model.data.TestCase
 */
public class TestCaseMock extends TestCase {
	private List<StepType> m_step;
	private StepTypeMock m_stm;
	/**
     * constructor
     */
	public TestCaseMock(){
		m_stm = new StepTypeMock();
		m_step = new ArrayList<StepType>();
		m_step.add(0, m_stm);
		m_step.add(m_stm);
		
	}	
	/*public TestCaseMock(String ts){
		m_stm = new StepTypeMock(ts);
		m_step = new ArrayList<StepType>();
		//m_step.add(ts);
		
	}	*/
	/**
     * @return new ArrayList<StepType>
     */
	public List<StepType> getStep() {
		return  new ArrayList<StepType> (m_step);
		//return m_step;
	}
	
	
}
