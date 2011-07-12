package edu.umd.cs.guitar.guitestrunner.monitor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import edu.umd.cs.guitar.guitestrunner.TestcaseStepEventArgs;
import edu.umd.cs.guitar.guitestrunner.TestcaseStepMonitor;

/**
 * Write a time log for the test case.
 * @author Scott McMaster
 *
 */
public class TimeMonitor implements TestcaseStepMonitor {

	/**
	 * Where we write our output.
	 * TODO this should come from a configuration file.
	 */
	private static final String OUTPUT_FILENAME = "TimeMonitor.log";

	private BufferedWriter writer;
	
	public void beforeStep(TestcaseStepEventArgs args)
	{
		try {
			writer.write(String.format("STEP\t%tc\n", Calendar.getInstance()));
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException("Error writing step start time to time monitor", e);
		}
	}

	public void init() {
		try {
			writer = new BufferedWriter(new FileWriter(OUTPUT_FILENAME));
			writer.write(String.format("START\t%tc\n", Calendar.getInstance()));
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException("Error initializing time monitor", e);
		}
	}

	public void term() {
		try {
			writer.write(String.format("END\t%tc", Calendar.getInstance()));
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException("Error initializing time monitor", e);
		}
	}

}
