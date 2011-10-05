//package tests.integration;
import junit.framework.*;
import edu.umd.cs.guitar.graph.*;
import edu.umd.cs.guitar.graph.plugin.*;

import java.io.FileNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 *
 * The TestGenericExc class implements a JUnit TestCase 
 * for testing the handling of an unknown and otherwise
 * uncaught exception. A flag "ThrowGeneric" is available
 * in the IO class to trigger the exception. However, it
 * is not necessarily the case that all exceptions will
 * be thrown from the IO class.
 *
 * @author Bryan Robbins
 *
 */
 
public class TestGenericExc extends TestCase {
	/** 
	 * This test sets the IO.ThrowGeneric flag to true
	 * so that a generic exception of type java.lang.Exception
	 * is thrown. The test verifies that 
	 * verifies that the message "Unknown ERROR"
	 * is printed to System.out.
	 *
	 */
	public void testGenericExc()
	{
		edu.umd.cs.guitar.model.IO.ThrowGeneric = true;
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
		tArgs[1] = "inputs/bad.gui.xml";
		tArgs[2] = "outputs/output.efg.xml";
		GUIStructure2GraphConverter.main(tArgs);
		
		System.setOut(originalOut);
		System.setErr(originalErr);
		System.out.print(out.toString());
		System.out.print(err.toString());
		
		int pos = out.toString().indexOf("Unknown ERROR");
		
		assertTrue(pos != -1);
	}
}
