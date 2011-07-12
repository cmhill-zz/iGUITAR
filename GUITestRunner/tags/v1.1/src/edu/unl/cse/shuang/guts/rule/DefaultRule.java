package edu.unl.cse.shuang.guts.rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.umd.cs.guitar.guitestrunner.TestCaseStep;

public class DefaultRule implements TestCaseStepRule {
    private int times = 0;
    private List<StepPosition> positions = Collections.emptyList();
    private TestCaseStep targetStep = null;
    private boolean hasBefore = false;
    private boolean hasAround = false;
    private boolean hasAfter = false;
    private List<TestCaseStep> beforeExecutions = Collections.emptyList();
    private List<TestCaseStep> aroundExecutions = Collections.emptyList();
    private List<TestCaseStep> afterExecutions = Collections.emptyList();

    public DefaultRule() {
        // Empty.
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public List<StepPosition> getPositions() {
        return Collections.unmodifiableList(positions);
    }

    public void setPositions(List<StepPosition> positions) {
        this.positions = new ArrayList<StepPosition>(positions);
    }

    public boolean isAtCorrectPosition(int index, int lastIndex) {
        boolean result = false;
        result = result || (index == 0 && positions.contains(StepPosition.FIRST));
        result = result || (index == lastIndex && positions.contains(StepPosition.LAST));
        result = result || (index != 0 && index != lastIndex && positions.contains(StepPosition.MIDDLE));

        return result;
    }

    public TestCaseStep getTargetStep() {
        return targetStep;
    }

    public void setTargetStep(TestCaseStep targetStep) {
        this.targetStep = targetStep;
    }

    /**
     * Check if this rule has a before block.
     * 
     * @return
     * @see #setBeforeExecutions(List)
     */
    public boolean hasBefore() {
        return hasBefore;
    }

    /**
     * Check if this rule has an around block.
     * 
     * @return
     * @see #setAroundExecutions(List)
     */
    public boolean hasAround() {
        return hasAround;
    }

    /**
     * Check if this rule has an after block.
     * 
     * @return
     * @see #setAfterExecutions(List)
     */
    public boolean hasAfter() {
        return hasAfter;
    }

    public List<TestCaseStep> getBeforeExecutions() {
        return Collections.unmodifiableList(beforeExecutions);
    }

    /**
     * If the given parameter is <code>null</code>, the previous before block is
     * cleared and method <code>hasBefore</code> returns false; otherwise, the
     * current before block is set to the be the given list and method
     * <code>hasBefore</code> returns true.
     * 
     * @param beforeExecutions
     * @see #hasBefore()
     */
    public void setBeforeExecutions(List<TestCaseStep> beforeExecutions) {
        if (beforeExecutions == null) {
            this.beforeExecutions = Collections.emptyList();
            hasBefore = false;
        } else {
            this.beforeExecutions = new ArrayList<TestCaseStep>(beforeExecutions);
            hasBefore = true;
        }
    }

    public List<TestCaseStep> getAroundExecutions() {
        return Collections.unmodifiableList(aroundExecutions);
    }

    /**
     * If the given parameter is <code>null</code>, the previous around block is
     * cleared and method <code>hasAround</code> return false; otherwise, the
     * current around block is set to be the given list and method
     * <code>hasAround</code> return true.
     * 
     * @param aroundExecutions
     * @see #hasAround()
     */
    public void setAroundExecutions(List<TestCaseStep> aroundExecutions) {
        if (aroundExecutions == null) {
            this.aroundExecutions = Collections.emptyList();
            hasAround = false;
        } else {
            this.aroundExecutions = new ArrayList<TestCaseStep>(aroundExecutions);
            hasAround = true;
        }
    }

    public List<TestCaseStep> getAfterExecutions() {
        return Collections.unmodifiableList(afterExecutions);
    }

    /**
     * If the given parameter is <code>null</code>, the previous after block is
     * cleared and method <code>hasAfter</code> return false; otherwise, the
     * current after block is set to be the given list and method
     * <code>hasAfter</code> return true.
     * 
     * @param afterExecutions
     * @see #hasAfter()
     */
    public void setAfterExecutions(List<TestCaseStep> afterExecutions) {
        if (afterExecutions == null) {
            this.afterExecutions = Collections.emptyList();
            hasAfter = false;
        } else {
            this.afterExecutions = new ArrayList<TestCaseStep>(afterExecutions);
            hasAfter = true;
        }
    }

    public List<TestCaseStep> getWholeSequence() {
        List<TestCaseStep> result = new ArrayList<TestCaseStep>();
        if (hasBefore()) {
            result.addAll(beforeExecutions);
        }
        if (hasAround()) {
            result.addAll(aroundExecutions);
        } else {
            result.add(targetStep);
        }
        if (hasAfter()) {
            result.addAll(afterExecutions);
        }

        return result;
    }
}
