package edu.unl.cse.shuang.guts.rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.umd.cs.guitar.guitestrunner.TestCaseStep;

public class TerminationRule implements TestCaseStepRule {
    private List<TestCaseStep> executions;

    public TerminationRule() {
        // Empty.
    }

    public List<TestCaseStep> getExecutions() {
        return Collections.unmodifiableList(executions);
    }

    public void setExecutions(List<TestCaseStep> executions) {
        if (executions == null) {
            this.executions = Collections.emptyList();
        } else {
            this.executions = new ArrayList<TestCaseStep>(executions);
        }
    }

    public List<TestCaseStep> getWholeSequence() {
        return getExecutions();
    }
}
