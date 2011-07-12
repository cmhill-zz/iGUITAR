//package tests.integration;

import junit.framework.*;
import edu.umd.cs.guitar.graph.*;
import edu.umd.cs.guitar.graph.plugin.*;

import java.io.FileNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.InstantiationException;

/**
 *
 * The TestIllegalAccessExc class implements a JUnit TestCase 
 * for testing the handling of an access exception thrown by
 * the EFGConverter plugin. Access exceptions indicate that
 * all or part of the converter is not accessible. A flag 
 * "ThrowAccess"in the adapted converter class BadConverter
 * signals that an exception should be thrown.
 *
 * @author Bryan Robbins
 *
 */
 
public class TestIllegalAccessExc extends TestCase {
	/** 
	 * This test sets the BadConverter.ThrowAccess flag to true
	 * so that an Illegal Access exception is thrown by the 
	 * converter plugin. The test verifies that the message 
	 * "The converter is not accessible" is printed to System.out.
	 *
	 */
	public void testIllegal()
	{
		edu.umd.cs.guitar.graph.plugin.BadConverter.ThrowAccess = true;
		PrintStream originalOut = System.out;
		PrintStream originalErr = System.err;
		OutputStream out = new ByteArrayOutputStream();
		OutputStream err = new ByteArrayOutputStream();

		PrintStream ops = new PrintStream(out);
		PrintStream eps = new PrintStream(err);

		System.setOut(ops);
		System.setErr(eps);

		String[] tArgs = new String[3];
		tArgs[0] = "BadConverter";
		tArgs[1] = "inputs/ignore-in.gui.xml";
		tArgs[2] = "outputs/ignore-out.efg.xml";
		GUIStructure2GraphConverter.main(tArgs);
		
		System.setOut(originalOut);
		System.setErr(originalErr);
		System.out.print(out.toString());
		System.out.print(err.toString());
		
		int pos = out.toString().indexOf("The converter is not accessible");
		
		assertTrue(pos != -1);
	}
}