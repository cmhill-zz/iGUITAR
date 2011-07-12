package edu.unl.cse.shuang.guts.rule;

import java.util.List;

import edu.umd.cs.guitar.guitestrunner.TestCaseStep;

public interface TestCaseStepRule {
    public List<TestCaseStep> getWholeSequence();
}
