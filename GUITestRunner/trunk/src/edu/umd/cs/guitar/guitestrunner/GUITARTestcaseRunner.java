package edu.umd.cs.guitar.guitestrunner;


import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.netbeans.jemmy.operators.AbstractButtonOperator;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JCheckBoxMenuItemOperator;
import org.netbeans.jemmy.operators.JCheckBoxOperator;
import org.netbeans.jemmy.operators.JColorChooserOperator;
import org.netbeans.jemmy.operators.JComponentOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JMenuOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.operators.WindowOperator;


public class GUITARTestcaseRunner implements TestcaseRunner
{
	private static final Logger LOG = Logger.getLogger(GUITARTestcaseRunner.class);
	
	private int stepCount;
	
	private boolean modalDialogUp = false;
	
	private List<TestcaseStepMonitor> monitors = new ArrayList<TestcaseStepMonitor>();
	
	/**
	 * Keep track of the current step.
	 */
	private int currentStepIndex;
	
	/**
	 * Stack of currently-open dialogs.
	 */
	private Stack<DialogType> currentDialogs = new Stack<DialogType>();
	
	/**
	 * The steps in this testcase.
	 */
	List<TestcaseStep> steps;

	/**
	 * User-settable name of the testcase.
	 */
	private String testcaseName;
	
	public GUITARTestcaseRunner(String testcaseName, List<TestcaseStep> steps)
	{
		this.testcaseName = testcaseName;
		this.steps = new ArrayList<TestcaseStep>(steps);
	}
	
	public void run()
	{
		LOG.debug("Running test " + testcaseName);
		
		fireInit();
		
		for( TestcaseStep step : steps )
		{
			LOG.debug("RUNNING STEP " + currentStepIndex);
			System.out.println("RUNNING STEP " + currentStepIndex);
			System.out.println();
			runStepNode(step);
			++currentStepIndex;
		}
		
		fireTerm();
		LOG.debug("ALL DONE");
		System.exit(0);
	}

	/**
	 * Fire the monitor term event.
	 */
	private void fireTerm() {
		for( TestcaseStepMonitor monitor : monitors )
		{
			monitor.term();
		}
	}

	/**
	 * Fire the monitor init event.
	 */
	private void fireInit() {
		for( TestcaseStepMonitor monitor : monitors )
		{
			monitor.init();
		}
	}

	/**
	 * Run a Step.
	 * @param step
	 */
	private void runStepNode(TestcaseStep step)
	{
		WindowOperator windowOperator = findWindowForStep(step);

		ComponentFinder finder = new ComponentFinder(windowOperator.getWindow());
		Component comp = finder.waitComponent(new GUITARComponentChooser(step), 5);
		if( comp == null )
		{
			throw new TestRunnerException("Component not found: " + step.getComponentId().toString());
		}

		JComponentOperator operator = mapComponentToOperator(comp, step);
		if( operator != null )
		{
			operateOnComponent(step, comp, operator);
		}
		// else exception???
		
		// Reset dialog flags.
		updateDialogsAfterStep(step);
		
		++stepCount;
	}

	/**
	 * Perform the required step action on the component.
	 * @param step
	 * @param comp
	 * @param operator
	 */
	private void operateOnComponent(TestcaseStep step, Component comp,
			JComponentOperator operator) {
		
		System.out.println("About to perform " + step + " on " + comp.getClass().getName() + ", " + comp.getName());
		
		Utils.sleep(2000);
		fireBeforeStep(new TestcaseStepEventArgs(step, comp));
		Utils.sleep(2000);
		
		if( "doClick".equals(step.getAction().getName()) )
		{
			AbstractButtonOperator buttonOperator = (AbstractButtonOperator) operator;
			clickWithCorrectThreadingModel(step, buttonOperator);
		}
		else if( "setColor".equals(step.getAction().getName()) )
		{
			JColorChooserOperator colorOperator = (JColorChooserOperator) operator;
			int r = Integer.parseInt(step.getAction().getParameterValues().get(0));
			int g = Integer.parseInt(step.getAction().getParameterValues().get(1));
			int b = Integer.parseInt(step.getAction().getParameterValues().get(2));
			colorOperator.setColor(r, g, b);
		}
		else if( "setText".equals(step.getAction().getName()) )
		{
			JTextFieldOperator fieldOperator = (JTextFieldOperator) operator;
			fieldOperator.setText(step.getAction().getParameterValues().get(0));
		}
		else
		{
			throw new TestRunnerException("Unsupported action: " + step.getAction().getName());
		}
	}

	/**
	 * Flag if we are running the last step in the test case.
	 */
	private boolean isOnLastStep()
	{
		return (currentStepIndex + 1 >= steps.size());
	}
	
	/**
	 * Update the state of the dialog stack after successful completion of the given step.
	 * @param step
	 */
	private void updateDialogsAfterStep(TestcaseStep step) {
		if( EventType.TERMINAL.equals(step.getActionType()) )
		{
			currentDialogs.pop();
			// If we processed a TERMINAL, and there is a subsequent TERMINAL before
			// we see another RESTRICTED or UNRESTRICTED, we just opened another dialog.
			// Assume it's modal.
			for( int i = currentStepIndex + 1; i < steps.size(); ++i )
			{
				TestcaseStep laterStep = steps.get(i);
				if( EventType.RESTRICTED.equals(laterStep.getActionType()) ||
						EventType.NONRESTRICTED.equals(laterStep.getActionType()) )
				{
					break;
				}
				if( EventType.TERMINAL.equals(laterStep.getActionType()) )
				{
					currentDialogs.push(DialogType.MODAL);
				}
			}
		}
		else if( EventType.RESTRICTED.equals(step.getActionType()) )
		{
			currentDialogs.push(DialogType.MODAL);
			
			// If this is the last step, we should attempt to wait for the dialog to appear
			// before proceeding (to exit), because no subsequent step is going to do a wait
			// for us.
			// TODO I'm not sure how to implement this correctly...
			if( isOnLastStep() )
			{
				
			}
		}
		else if( EventType.NONRESTRICTED.equals(step.getActionType()) )
		{
			currentDialogs.push(DialogType.MODELESS);
		}
	}

	/**
	 * Get the operator for the component out of the given window that the given
	 * step is using.
	 * @param window
	 * @param step
	 * @return the operator, or none if we're not able to find one.
	 */
	private JComponentOperator mapComponentToOperator(Component comp, TestcaseStep step)
	{
		JComponentOperator operator = null;

		if( comp instanceof JCheckBoxMenuItem )
		{
			operator = checkDirectMenuItemClick(step, comp);
			if(operator == null)
			{
				operator = new JCheckBoxMenuItemOperator((JCheckBoxMenuItem) comp);
			}			
		}
		else if( comp instanceof JMenuItem )
		{
			operator = checkDirectMenuItemClick(step, comp);
			if(operator == null)
			{
				operator = new JMenuItemOperator((JMenuItem) comp);
			}
		}
		else if( comp instanceof JMenu )
		{
			operator = new JMenuOperator((JMenu) comp);
		}
		else if( comp instanceof JButton )
		{
			operator = new JButtonOperator((JButton) comp);
		}
		else if( comp instanceof JColorChooser )
		{
			operator = new JColorChooserOperator((JColorChooser) comp);
		}
		else if( comp instanceof JTextField )
		{
			operator = new JTextFieldOperator((JTextField) comp);
		}
		else if( comp instanceof JCheckBox )
		{
			operator = new JCheckBoxOperator((JCheckBox) comp);
		}
		
		return operator;
	}

	/**
	 * Get an operator to the window that this step is supposed to act on.
	 * @param step
	 * @return
	 */
	private WindowOperator findWindowForStep(TestcaseStep step) {
		WindowOperator windowOperator;
		
		updateDialogFlags();
		if( modalDialogUp )
		{
			System.out.println("Looking for MODAL window");
			windowOperator = findModalDialog(step);
		}
		else
		{
			System.out.println("Looking for MODELESS window");
			windowOperator = findWindow(step);
		}
		return windowOperator;
	}

	/**
	 * Calculate if there is a dialog up, and if so, what type.
	 */
	private void updateDialogFlags()
	{
		modalDialogUp = false;

		if( currentDialogs.isEmpty() )
		{
			return;
		}
		
		if( currentDialogs.peek() == DialogType.MODAL )
		{
			modalDialogUp = true;
		}
	}

	private WindowOperator findWindow(TestcaseStep step) {
		JFrame frame = JFrameOperator.waitJFrame(new GUITARWindowChooser(step.getWindowName()));
		return new JFrameOperator(frame);
	}

	private WindowOperator findModalDialog(TestcaseStep step) {
		JDialog dialog = JDialogOperator.waitJDialog(new GUITARDialogChooser(step.getWindowName()));
		if( dialog != null )
		{
			return new JDialogOperator(dialog);
		}

		throw new TestRunnerException("Didn't find dialog " + step.getWindowName());
	}

	/**
	 * Click the given button with the threading necessary to ensure that we don't get blocked.
	 * @param step
	 * @param buttonOperator
	 */
	private void clickWithCorrectThreadingModel(TestcaseStep step,
			AbstractButtonOperator buttonOperator) {
		if( EventType.SYSTEM_INTERACTION.equals(step.getActionType()) )
		{
			System.out.println("Clicking SYNC for step " + step);
			buttonOperator.doClick();
		}
		else
		{
			System.out.println("Clicking ASYNC for step " + step);
			buttonOperator.pushNoBlock();
			//Utils.sleep(2000);
		}
	}

	/**
	 * Notify monitors that we are about to run a step.
	 * @param testcaseStepEventArgs
	 */
	private void fireBeforeStep(TestcaseStepEventArgs testcaseStepEventArgs) {
		for( TestcaseStepMonitor monitor : monitors )
		{
			monitor.beforeStep(testcaseStepEventArgs);
		}
	}

	/**
	 * 	GUITAR apparently allows you to click on an item in a popup menu
	 *	without first popping up the menu.  Jemmy cannot deal with this,
	 *	so this special code tries to find the menu to pop first, before
	 *  returning an operator for the correct item to click.
	 * @param comp
	 * @return
	 */
	private JMenuItemOperator checkDirectMenuItemClick(TestcaseStep step, Component comp) {

		if( !comp.isValid() )
		{
			if( !comp.isEnabled() )
			{
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
	 * @param monitorClassName
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void addTestcaseMonitor(String monitorClassName) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		TestcaseStepMonitor monitor = (TestcaseStepMonitor) Class.forName(monitorClassName).newInstance();
		monitors.add(monitor);
	}
}
