package edu.umd.cs.guitar.guitestrunner.monitor;

import java.util.Calendar;

/**
 * Track information about an observed exception.
 * @author Scott McMaster
 *
 */
public class ExceptionInfo
{
	/**
	 * @return the stepNumber
	 */
	public int getStepNumber() {
		return stepNumber;
	}

	/**
	 * @return the exceptionTime
	 */
	public Calendar getExceptionTime() {
		return exceptionTime;
	}

	/**
	 * @return the throwable
	 */
	public Throwable getThrowable() {
		return throwable;
	}

	public ExceptionInfo(int stepNumber, Calendar exceptionTime,
			Throwable throwable) {
		super();
		this.stepNumber = stepNumber;
		this.exceptionTime = exceptionTime;
		this.throwable = throwable;
	}

	/**
	 * Most recently executed step number.
	 */
	private int stepNumber;
	
	/**
	 * Time the exception was observed.
	 */
	private Calendar exceptionTime;
	
	/**
	 * The exception.
	 */
	private Throwable throwable;
}
