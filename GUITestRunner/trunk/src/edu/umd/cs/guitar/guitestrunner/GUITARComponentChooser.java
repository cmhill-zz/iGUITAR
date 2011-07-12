package edu.umd.cs.guitar.guitestrunner;

import java.awt.Component;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.apache.log4j.Logger;
import org.netbeans.jemmy.ComponentChooser;

public class GUITARComponentChooser implements ComponentChooser {

	private static final Logger LOG = Logger.getLogger(GUITARComponentChooser.class);
	
	private TestcaseStep step;

	public GUITARComponentChooser(TestcaseStep step) {
		this.step = step;
	}

	//JDK6 @Override
	public boolean checkComponent(Component comp) {
		// 1. Make sure it's of the desired class.
		if (!comp.getClass().getName().equals(step.getComponentClassName())) {
			return false;
		}

		// 2. Check name.
		if (comp.getName() != null
				&& comp.getName().equals(step.getComponentId().getName())) {
			return true;
		}

		// 3. Check name against text.
		String compText = getCompText(comp);
		if (compText != null
				&& compText.equals(step.getComponentId().getName())) {
			return true;
		}

		// 4. Check tooltip.
		if (comp instanceof JComponent) {
			String tooltip = ((JComponent) comp).getToolTipText();
			if (tooltip != null && tooltip.length() > 0
					&& tooltip.equals(step.getTooltip())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Get the text property out of a component, if it has any. This will
	 * require a downcast.
	 * 
	 * @param comp
	 * @return
	 */
	private String getCompText(Component comp) {
		if (comp instanceof JButton) {
			return ((JButton) comp).getText();
		}
		if (comp instanceof JMenu) {
			return ((JMenu) comp).getText();
		}
		if (comp instanceof JMenuItem) {
			return ((JMenuItem) comp).getText();
		}

		return null;
	}

	//JDK6 @Override
	public String getDescription() {
		return step.getComponentId().toString();
	}

	public void setStep(TestcaseStep step) {
		this.step = step;
	}

	public TestcaseStep getStep() {
		return step;
	}

	/**
	 * Determine if the given component doesn't NOT match on any of the list of
	 * properties that we check. This is not exactly the opposite of
	 * checkComponent.
	 * 
	 * @param comp
	 * @return
	 */
	public boolean matchComponent(Component comp)
	{
		// check class
		String className = comp.getClass().getName();
		if (!className.equals(step.getComponentClassName())) {
			return false;
		}

		// if AbstractButton, first check the label
		if (comp instanceof AbstractButton) {
			className = ((AbstractButton) comp).getText();
			if (className != null && !className.equals("")) {
				String componentTitle = step.getComponentId().getName();
				if (componentTitle.equals(className)
						|| className.indexOf(componentTitle) == 0) {
					return true;
				} else {
					return false;
				}
			}
		}

		className = ((JComponent) comp).getToolTipText();
		if (className == null)
			className = "null";
		if (!className.equals(step.getTooltip())) {
			return false;
		}

		LOG.debug("Found from property comparison!\n");
		return true;
	}

}
