package edu.umd.cs.guitar.guitestrunner.monitor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.netbeans.jemmy.TestOut;

/**
 * Override Jemmy's TestOut to make it possible to grab exceptions.
 * @author Scott McMaster
 *
 */
public class JemmyTestOutExceptionGrabber extends TestOut
{
	private List<ExceptionInfo> exceptions = new ArrayList<ExceptionInfo>();
	
	/**
	 * The most recently executed step.
	 */
	private int stepNumber;

	@Override
	public void printStackTrace(Throwable e)
	{
		super.printStackTrace(e);
		ExceptionInfo info = new ExceptionInfo(stepNumber, Calendar.getInstance(), e);
		exceptions.add(info);
	}

	public List<ExceptionInfo> getExceptions() {
		return exceptions;
	}

	public void incrementStepNumber()
	{
		++stepNumber;
	}
}
