package edu.umd.cs.guitar.guitestrunner;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.operators.AbstractButtonOperator;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JCheckBoxMenuItemOperator;
import org.netbeans.jemmy.operators.JCheckBoxOperator;
import org.netbeans.jemmy.operators.JColorChooserOperator;
import org.netbeans.jemmy.operators.JComponentOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JFileChooserOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JMenuOperator;
import org.netbeans.jemmy.operators.JTextAreaOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.operators.JTextPaneOperator;
import org.netbeans.jemmy.operators.WindowOperator;

import edu.umd.cs.guitar.util.Utils;

/**
 * 
 * @author Scott McMaster, Si Huang
 * 
 */
public class GUITARTestCaseRunner extends AbstractTestCaseRunner {
    private boolean modalDialogUp = false;

    private List<TestCaseStepMonitor> monitors = new ArrayList<TestCaseStepMonitor>();

    /**
     * Stack of currently-open dialogs.
     */
    private Stack<DialogType> currentDialogs = new Stack<DialogType>();

    // /**
    // * Used for clean up. (Si Huang)
    // */
    // private Set<Window> usedWindows = new HashSet<Window>();
    /**
     * The entry of the application under test.
     */
    private Class<?> entry = null;

    /**
     * Previous security manager.
     */
    private SecurityManager previousSecurityManager;

    /**
     * Whether the runner is started.
     */
    private volatile boolean started = false;

    private boolean justHadTerminalStep = false;

    public GUITARTestCaseRunner() {
        // Empty.
    }

    // XXX Don't always start from a script file.
    // public GUITARTestCaseRunner(String testFilename) {
    // super(testFilename);
    // }

    public Class<?> getEntryOfApplicationUnderTest() {
        return entry;
    }

    public void setEntryOfApplicationUnderTest(Class<?> entry) {
        this.entry = entry;
    }

    public void launch() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        if (!started) {
            previousSecurityManager = System.getSecurityManager();
            System.setSecurityManager(new ExitHandler());

            ClassReference cr = new ClassReference(entry.getCanonicalName());
            cr.startApplication();

            fireInit();

            started = true;
            justHadTerminalStep = false;

            Utils.LOG.logln("Launched!");
            Utils.sleep(500);
        }
    }

    @Override
    public void runAll(TestCaseStep[] steps) throws TestRunnerException {
        if (!started) {
            throw new TestRunnerException("State not ready for running!");
        }
        Utils.LOG.logln("----------Running Steps----------");
        for (int i = 0; i < steps.length; ++i) {
            // Utils.LOG.logln("Running Step " + i + ":");
            runStepNode(steps[i]);
        }
    }

    @Override
    public void run(TestCaseStep step) throws TestRunnerException {
        if (!started) {
            throw new TestRunnerException("State not ready for running!");
        }
        // Utils.LOG.logln("Running An Independent Step:");
        runStepNode(step);
    }

    public void terminate() {
        if (started) {
            fireTerm();
            // cleanUp();
            System.setSecurityManager(previousSecurityManager);
            started = false;

            Utils.LOG.logln("Terminated!");
        }
    }

    protected void cleanUp() {
        Utils.LOG.logln("-----Dispose windows-----");
        Runnable r = new Runnable() {
            public void run() {
                Window[] windows = Window.getWindows();
                for (Window w : windows) {
                    if (w.isDisplayable()) {
                        w.dispose();
                    }
                }
            }
        };
        try {
            /* if (!currentDialogs.isEmpty()) {
                Utils.LOG.logln("Still have open dialogs: " + currentDialogs);                
            }
            while (!currentDialogs.isEmpty()) {
                Thread.sleep(2000);
            } */
            if (SwingUtilities.isEventDispatchThread()) {
                r.run();
            } else {
                EventQueue.invokeAndWait(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isTerminated() {
        return !started;
    }

    /**
     * Fire the monitor term event.
     */
    private void fireTerm() {
        for (TestCaseStepMonitor monitor : monitors) {
            monitor.term();
        }
    }

    /**
     * Fire the monitor init event.
     */
    private void fireInit() {
        for (TestCaseStepMonitor monitor : monitors) {
            monitor.init();
        }
    }

    /**
     * Run a Step.
     * 
     * @param step
     */
    private void runStepNode(TestCaseStep step) throws TestRunnerException {
        Utils.LOG.logln("Running: " + step);

        WindowOperator windowOperator = findWindowForStep(step);

        ComponentFinder finder = new ComponentFinder(windowOperator.getWindow());
        Component comp = finder.waitComponent(new GUITARComponentChooser(step), 5);
        if (comp == null) {
            throw new TestRunnerException("Component not found: " + step.getComponentId().toString());
        }

        // XXX Si Huang
        if (!comp.isEnabled()) {
            throw new TestRunnerException("Cannot execute!");
        }

        JComponentOperator operator = mapComponentToOperator(comp, step);
        if (operator != null) {
            operateOnComponent(step, comp, operator);
        } // else exception???
        else { // XXX Si Huang
            operateUserDefinedComponent(step, comp);
        }

        // Reset dialog flags.
        updateDialogsAfterStep(step);
    }

    /**
     * Perform the required step action on the component.
     * 
     * @param step
     * @param comp
     * @param operator
     */
    private void operateOnComponent(TestCaseStep step, Component comp, JComponentOperator operator)
            throws TestRunnerException {
        // Utils.LOG.logln("About to perform " + step + " on " + comp.getClass().getName() + ", " + comp.getName());

        // Utils.sleep(2000);
        fireBeforeStep(new TestCaseStepEventArgs(step, comp));
        // Utils.sleep(2000);

        if ("doClick".equals(step.getAction().getName())) {
            AbstractButtonOperator buttonOperator = (AbstractButtonOperator) operator;
            clickWithCorrectThreadingModel(step, buttonOperator);
        } else if ("setColor".equals(step.getAction().getName())) {
            JColorChooserOperator colorOperator = (JColorChooserOperator) operator;
            int r = Integer.parseInt(step.getAction().getParameterValues().get(0));
            int g = Integer.parseInt(step.getAction().getParameterValues().get(1));
            int b = Integer.parseInt(step.getAction().getParameterValues().get(2));
            colorOperator.setColor(r, g, b);
        } else if ("setText".equals(step.getAction().getName())) {
            if (operator instanceof JTextFieldOperator) {
                JTextFieldOperator fieldOperator = (JTextFieldOperator) operator;
                fieldOperator.setText(step.getAction().getParameterValues().get(0));
            } else if (operator instanceof JTextAreaOperator) {
                JTextAreaOperator areaOperator = (JTextAreaOperator) operator;
                areaOperator.setText(step.getAction().getParameterValues().get(0));
            } else {
                JTextPaneOperator paneOperator = (JTextPaneOperator) operator;
                paneOperator.setText(step.getAction().getParameterValues().get(0));
            }
        } else if ("setSelectedFile".equals(step.getAction().getName())) {
            JFileChooserOperator fileChooserOperator = (JFileChooserOperator) operator;
            File selectedFile = new File(step.getAction().getParameterValues().get(0));
            try {
                selectedFile = selectedFile.getCanonicalFile();
            } catch (IOException e) {
                throw new TestRunnerException("Cannot find the specified file!");
            }
            fileChooserOperator.setSelectedFile(selectedFile);
        } else {
            throw new TestRunnerException("Unsupported action: " + step.getAction().getName());
        }

        // Utils.sleep(2000);
    }

    // XXX Si huang
    private void operateUserDefinedComponent(final TestCaseStep step, final Component comp) throws TestRunnerException {
        Runnable r = new Runnable() {
            public void run() {
                try {
                    Class<?> compClass = comp.getClass();
                    String[] typeNames = step.getAction().getParameterTypes().toArray(new String[0]);
                    String[] valueLiterals = step.getAction().getParameterValues().toArray(new String[0]);
                    Class<?>[] types = new Class<?>[typeNames.length];
                    Object[] values = new Object[typeNames.length];
                    for (int i = 0; i < typeNames.length; ++i) {
                        types[i] = Class.forName(typeNames[i]);
                        values[i] = types[i].getConstructor(String.class).newInstance(valueLiterals[i]);
                    }
                    String methodName = step.getAction().getName();
                    Method m = compClass.getMethod(methodName, types);
                    m.invoke(comp, values);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            try {
                SwingUtilities.invokeAndWait(r);
            } catch (Exception e) {
                throw new TestRunnerException(e);
            }
        }
    }

    /**
     * Update the state of the dialog stack after successful completion of the
     * given step.
     * 
     * @param step
     */
    private void updateDialogsAfterStep(TestCaseStep step) {
        if (EventType.TERMINAL.equals(step.getActionType())) {
            if (justHadTerminalStep) {
                currentDialogs.push(DialogType.MODAL);
            }
            currentDialogs.pop();
            justHadTerminalStep = true;
            // If we processed a TERMINAL, and there is a subsequent TERMINAL
            // before we see another RESTRICTED or UNRESTRICTED, we just opened
            // another dialog. Assume it's modal.
            // for (int i = currentStepIndex + 1; i < steps.size(); ++i) {
            // TestCaseStep laterStep = steps.get(i);
            // if (EventType.RESTRICTED.equals(laterStep.getActionType())
            // || EventType.NONRESTRICTED.equals(laterStep.getActionType())) {
            // break;
            // }
            // if (EventType.TERMINAL.equals(laterStep.getActionType())) {
            // currentDialogs.push(DialogType.MODAL);
            // }
            // }
        } else if (EventType.RESTRICTED.equals(step.getActionType())) {
            currentDialogs.push(DialogType.MODAL);
            justHadTerminalStep = false;
        } else if (EventType.NONRESTRICTED.equals(step.getActionType())) {
            currentDialogs.push(DialogType.MODELESS);
            justHadTerminalStep = false;
        }
    }

    /**
     * Get the operator for the component out of the given window that the given
     * step is using.
     * 
     * @param window
     * @param step
     * @return the operator, or none if we're not able to find one.
     */
    private JComponentOperator mapComponentToOperator(Component comp, TestCaseStep step) throws TestRunnerException {
        JComponentOperator operator = null;

        if (comp instanceof JCheckBoxMenuItem) {
            operator = checkDirectMenuItemClick(step, comp);
            if (operator == null) {
                operator = new JCheckBoxMenuItemOperator((JCheckBoxMenuItem) comp);
            }
        } else if (comp instanceof JMenuItem) {
            operator = checkDirectMenuItemClick(step, comp);
            if (operator == null) {
                operator = new JMenuItemOperator((JMenuItem) comp);
            }
        } else if (comp instanceof JMenu) {
            operator = new JMenuOperator((JMenu) comp);
        } else if (comp instanceof JButton) {
            operator = new JButtonOperator((JButton) comp);
        } else if (comp instanceof JColorChooser) {
            operator = new JColorChooserOperator((JColorChooser) comp);
        } else if (comp instanceof JTextField) {
            operator = new JTextFieldOperator((JTextField) comp);
        } else if (comp instanceof JTextArea) {
            operator = new JTextAreaOperator((JTextArea) comp);
        } else if (comp instanceof JTextPane) {
            operator = new JTextPaneOperator((JTextPane) comp);
        } else if (comp instanceof JCheckBox) {
            operator = new JCheckBoxOperator((JCheckBox) comp);
        } else if (comp instanceof JFileChooser) {
            operator = new JFileChooserOperator((JFileChooser) comp);
        }

        return operator;
    }

    /**
     * Get an operator to the window that this step is supposed to act on.
     * 
     * @param step
     * @return
     */
    private WindowOperator findWindowForStep(TestCaseStep step) throws TestRunnerException {
        WindowOperator windowOperator;

        updateDialogFlags();
        if (modalDialogUp) {
            Utils.LOG.logln("Looking for MODAL window");
            windowOperator = findModalDialog(step);
        } else {
            Utils.LOG.logln("Looking for MODELESS window");
            windowOperator = findWindow(step);
        }
        // usedWindows.add(windowOperator.getWindow());
        return windowOperator;
    }

    /**
     * Calculate if there is a dialog up, and if so, what type.
     */
    private void updateDialogFlags() {
        modalDialogUp = false;

        if (currentDialogs.isEmpty()) {
            return;
        }

        if (currentDialogs.peek() == DialogType.MODAL) {
            modalDialogUp = true;
        }
    }

    private WindowOperator findWindow(TestCaseStep step) {
        JFrame frame = JFrameOperator.waitJFrame(new GUITARWindowChooser(step.getWindowName()));
        return new JFrameOperator(frame);
    }

    private WindowOperator findModalDialog(TestCaseStep step) throws TestRunnerException {
        JDialog dialog = JDialogOperator.waitJDialog(new GUITARDialogChooser(step.getWindowName()));
        if (dialog != null) {
            return new JDialogOperator(dialog);
        }

        throw new TestRunnerException("Dialog " + step.getWindowName() + " not found.");
    }

    /**
     * Click the given button with the threading necessary to ensure that we
     * don't get blocked.
     * 
     * @param step
     * @param buttonOperator
     */
    private void clickWithCorrectThreadingModel(TestCaseStep step, AbstractButtonOperator buttonOperator) {
        if (EventType.SYSTEM_INTERACTION.equals(step.getActionType()) || EventType.TERMINAL.equals(step.getActionType())) {
            Utils.LOG.logln("Clicking SYNC for step " + step);
            buttonOperator.doClick();
        } else {
            Utils.LOG.logln("Clicking ASYNC for step " + step);
            buttonOperator.pushNoBlock();
            Utils.sleep(3000);
        }
    }

    /**
     * Notify monitors that we are about to run a step.
     * 
     * @param testcaseStepEventArgs
     */
    private void fireBeforeStep(TestCaseStepEventArgs testcaseStepEventArgs) {
        for (TestCaseStepMonitor monitor : monitors) {
            monitor.beforeStep(testcaseStepEventArgs);
        }
    }

    /**
     * GUITAR apparently allows you to click on an item in a popup menu without
     * first popping up the menu. Jemmy cannot deal with this, so this special
     * code tries to find the menu to pop first, before returning an operator
     * for the correct item to click.
     * 
     * @param comp
     * @return
     */
    private JMenuItemOperator checkDirectMenuItemClick(TestCaseStep step, Component comp) throws TestRunnerException {

        if (!comp.isValid()) {
            if (!comp.isEnabled()) {
                throw new TestRunnerException("Attempt to click disabled menu item: " + comp.toString());
            }
            // Need to drop down the parent menu.
            JMenuItem item = (JMenuItem) comp;

            JPopupMenu parent = (JPopupMenu) comp.getParent();
            JMenuOperator parentOp = new JMenuOperator((JMenu) parent.getInvoker());
            parentOp.doClick();

            JMenuItemOperator itemOp = new JMenuItemOperator(item);
            return itemOp;
        }

        return null;
    }

    /**
     * Create a monitor of the given type.
     * 
     * @param monitorClassName
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void addTestcaseMonitor(String monitorClassName) throws InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        TestCaseStepMonitor monitor = (TestCaseStepMonitor) Class.forName(monitorClassName).newInstance();
        monitors.add(monitor);
    }

    private class ExitHandler extends NoExitSecurityManager {
        public void fireExit() {
            terminate();
        }
    }
}
