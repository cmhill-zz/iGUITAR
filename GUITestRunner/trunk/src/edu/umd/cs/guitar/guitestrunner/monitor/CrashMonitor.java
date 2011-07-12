package edu.umd.cs.guitar.guitestrunner.monitor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.netbeans.jemmy.JemmyProperties;

import edu.umd.cs.guitar.guitestrunner.TestcaseStepEventArgs;
import edu.umd.cs.guitar.guitestrunner.TestcaseStepMonitor;

/**
 * Record crashes.
 * @author Scott McMaster
 *
 */
public class CrashMonitor implements TestcaseStepMonitor
{
	private JemmyTestOutExceptionGrabber grabber;
	
	/**
	 * Where we write our output.
	 * TODO this should come from a configuration file.
	 */
	private static final String OUTPUT_FILENAME = "CrashMonitor.log";
	
	public void beforeStep(TestcaseStepEventArgs args)
	{
		grabber.incrementStepNumber();
	}

	public void init() {
		grabber = new JemmyTestOutExceptionGrabber();
		JemmyProperties.setCurrentOutput(grabber);
	}

	/**
	 * Writes observed exceptions to output file.
	 */
	public void term()
	{
		if( grabber.getExceptions().isEmpty() ) 
		{
			// Don't write output if there are no exceptions observed.
			return;
		}
		
		BufferedWriter writer = null;
		try
		{
			writer = new BufferedWriter(new FileWriter(OUTPUT_FILENAME));
			for( ExceptionInfo info : grabber.getExceptions() )
			{
				writer.write(String.format("%d\t%tc", info.getStepNumber(), info.getExceptionTime()));
				writer.write('\n');
				final Writer result = new StringWriter();
			    final PrintWriter printWriter = new PrintWriter(result);
				info.getThrowable().printStackTrace(printWriter);
				writer.write(result.toString());
				writer.write('\n');
			}
		} catch (IOException e) {
			throw new RuntimeException("Error writing crash monitor output", e);
		}
		finally
		{
			if( writer != null )
			{
				try { writer.close(); } catch(Exception ex) {}
			}
		}
	}

}
