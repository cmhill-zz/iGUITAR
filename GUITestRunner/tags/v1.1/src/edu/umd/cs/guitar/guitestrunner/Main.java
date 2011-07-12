package edu.umd.cs.guitar.guitestrunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.netbeans.jemmy.Timeout;
import org.netbeans.jemmy.TimeoutExpiredException;

import edu.umd.cs.guitar.util.Utils;
import edu.unl.cse.shuang.guts.rule.DefaultRule;
import edu.unl.cse.shuang.guts.rule.StartupRule;
import edu.unl.cse.shuang.guts.rule.TerminationRule;
import edu.unl.cse.shuang.guts.rule.TestCaseStepRule;

public class Main {
    private String testFilename = null;
    private String resultFilename = null;
    private String ruleFilename = null;
    private String guiStructureFilename = null;

    private TestCaseStep[] steps = new TestCaseStep[0];
    private TestCaseStepRule[] executionRules = new TestCaseStepRule[0];
    private Map<TestCaseStepRule, Integer> timesLeftForRules = new HashMap<TestCaseStepRule, Integer>();

    private GUITARTestCaseRunner runner = new GUITARTestCaseRunner();
    private TestCaseStepReaderWriter rw = new TestCaseStepReaderWriter();

    private static final String USAGE = "Usage: Main main-class test-filename result-filename rule-filename gui-structure-filename";

    private static final long MAX_EXECUTION_TIME = 240000;
    private int failurePoint = 0;

    public static void main(String[] args) {
        if (args.length != 3 && args.length != 5) {
            usage();
            return;
        }

        Utils.LOG.logln("Start logging!");
        final Main main = new Main();
        main.setMainClassName(args[0]);
        main.setTestFilename(args[1]);
        main.setResultFilename(args[2]);
        if (args.length == 5) {
            main.setRuleFilename(args[3]);
            main.setGuiStructureFilename(args[4]);
        }

        Runnable totalTimeout = new Runnable() {
            public void run() {
                Utils.LOG.logln("Start Timing!");
                Timeout to = new Timeout("Total Timeout", MAX_EXECUTION_TIME);
                to.start();
                try {
                    while (!main.runner.isStarted()) {
                        Utils.sleep(1000);
                        to.check();
                    }
                    while (!main.runner.isTerminated()) {
                        Utils.sleep(1000);
                        to.check();
                    }
                } catch (TimeoutExpiredException e) {
                    Utils.LOG.logln("Timed out!");
                    main.runner.terminate();
                    System.exit(1);
                }
            }
        };
        new Thread(totalTimeout).start();

        try {
            main.run();
        } catch (Exception e) {
            if (Utils.LOG.isLogging()) {
                e.printStackTrace(Utils.LOG.getOutput());
            }
        }
        System.exit(0);
    }

    /**
     * Print usage information.
     */
    private static void usage() {
        System.err.println(USAGE);
    }

    public String getMainClassName() {
        return runner.getEntryOfApplicationUnderTest().getCanonicalName();
    }

    public void setMainClassName(String mainClassName) {
        try {
            runner.setEntryOfApplicationUnderTest(Class.forName(mainClassName));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String getTestFilename() {
        return testFilename;
    }

    public void setTestFilename(String testFilename) {
        this.testFilename = testFilename;
    }

    public String getResultFilename() {
        return resultFilename;
    }

    public void setResultFilename(String resultFilename) {
        this.resultFilename = resultFilename;
    }

    public String getRuleFilename() {
        return ruleFilename;
    }

    public void setRuleFilename(String ruleFilename) {
        this.ruleFilename = ruleFilename;
    }

    public String getGuiStructureFilename() {
        return guiStructureFilename;
    }

    public void setGuiStructureFilename(String guiStructureFilename) {
        this.guiStructureFilename = guiStructureFilename;
    }

    private void initializeExecution() throws TestRunnerException {
        steps = buildTestCaseSteps(testFilename).toArray(new TestCaseStep[0]);

        if (ruleFilename != null) {
            executionRules = rw.createTestCaseStepRules(steps, new File(ruleFilename), new File(guiStructureFilename));
            timesLeftForRules.clear();
            for (int i = 0; i < executionRules.length; ++i) {
                TestCaseStepRule rule = executionRules[i];
                if (rule instanceof DefaultRule) {
                    int times = ((DefaultRule) rule).getTimes();
                    timesLeftForRules.put(rule, times == 0 ? -1 : times);
                }
            }
        }
    }

    public void run() throws Exception {
        // Make sure failurePoint starts from 0 even if unexpected errors occur.
        failurePoint = 0;
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    writeResults();
                } catch (IOException e) {
                    Utils.LOG.logln("Failed to write results!");
                    e.printStackTrace();
                    throw new RuntimeException();
                }
            }
        });
        try {
            initializeExecution();
            runner.launch();
            Utils.LOG.logln("Actual execution: ");

            // Execute startup.
            List<TestCaseStep> startup = new ArrayList<TestCaseStep>();
            for (int i = 0; i < executionRules.length; ++i) {
                if (executionRules[i] instanceof StartupRule) {
                    startup.addAll(executionRules[i].getWholeSequence());
                }
            }
            for (int i = 0; i < startup.size(); ++i) {
                try {
                    execute(startup.get(i));
                } catch (Exception e) {
                    Utils.LOG.logln(e);
                    throw new TestRunnerException(e);
                }
            }

            // Execute main part.
            for (; failurePoint < steps.length; ++failurePoint) {
                try {
                    execute(steps[failurePoint], failurePoint, steps.length - 1);
                } catch (Exception e) {
                    if (runner.isTerminated()) {
                        failurePoint++;
                        Utils.LOG.logln("Exited!");
                    } else {
                        if (Utils.LOG.isLogging()) {
                            e.printStackTrace(Utils.LOG.getOutput());
                        }
                    }
                    break;
                }
            }

            // Execute termination.
            if (!runner.isTerminated()) {
                List<TestCaseStep> termination = new ArrayList<TestCaseStep>();
                for (int i = 0; i < executionRules.length; ++i) {
                    if (executionRules[i] instanceof TerminationRule) {
                        termination.addAll(executionRules[i].getWholeSequence());
                    }
                }
                for (int i = 0; i < termination.size(); ++i) {
                    try {
                        execute(termination.get(i));
                    } catch (Exception e) {
                        if (runner.isTerminated()) {
                            Utils.LOG.logln("Exited!");
                            break;
                        } else {
                            Utils.LOG.logln(e);
                            throw new TestRunnerException(e);
                        }
                    }
                }
            }
            runner.terminate();
        } catch (ClassNotFoundException e) {
            System.err.println("Specified main-class not found!");
            throw e;
        } catch (InvocationTargetException e) {
            System.err.println("Exception from the application!");
            throw e;
        } catch (NoSuchMethodException e) {
            System.err.println("Application is lack of program entry!");
            throw e;
        }
    }

    private void writeResults() throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(resultFilename));

        out.write("FAILURE_POINT=" + failurePoint);

        out.close();
    }

    protected List<TestCaseStep> preprocess(TestCaseStep step, int index, int lastIndex) {
        for (int j = 0; j < executionRules.length; ++j) {
            TestCaseStepRule r = executionRules[j];
            // Startup and termination rules not applied here!
            if (r instanceof DefaultRule) {
                DefaultRule rule = (DefaultRule) r;
                int timesLeft = timesLeftForRules.get(rule);
                // Including the case of -1, which means to apply every time.
                if (timesLeft != 0 && rule.isAtCorrectPosition(index, lastIndex)) {
                    TestCaseStep targetStep = rule.getTargetStep();
                    if (targetStep != null && targetStep.equals(step)) {
                        timesLeftForRules.put(rule, timesLeft - 1);
                        return rule.getWholeSequence();
                    }
                }
            }
        }
        List<TestCaseStep> extended = new ArrayList<TestCaseStep>();
        extended.add(step);
        return extended;
    }

    protected void execute(TestCaseStep step, int index, int length) throws TestRunnerException {
        List<TestCaseStep> extendedSteps = preprocess(step, index, length);
        for (int j = 0; j < extendedSteps.size(); ++j) {
            Utils.LOG.logln(extendedSteps.get(j) + ", ");
            runner.run(extendedSteps.get(j));
        }
    }

    protected void execute(TestCaseStep step) throws TestRunnerException {
        Utils.LOG.logln(step + ",");
        runner.run(step);
    }

    /**
     * Build the list of test cases from the xml Step elements in the given
     * file.
     */
    private List<TestCaseStep> buildTestCaseSteps(String testFilename) throws TestRunnerException {
        Builder builder = new Builder();
        Document testcaseDoc = null;
        try {
            testcaseDoc = builder.build(new File(testFilename));
        } catch (ValidityException e) {
            throw new TestRunnerException(e);
        } catch (ParsingException e) {
            throw new TestRunnerException(e);
        } catch (IOException e) {
            throw new TestRunnerException(e);
        }

        List<TestCaseStep> steps = new ArrayList<TestCaseStep>();
        Elements stepElems = testcaseDoc.getRootElement().getChildElements("Step");
        for (int i = 0; i < stepElems.size(); ++i) {
            Element stepElem = stepElems.get(i);
            TestCaseStep step = rw.createTestCaseStep(stepElem);
            steps.add(step);
        }

        return steps;
    }
}
