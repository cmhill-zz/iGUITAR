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
 * The TestFileNotFoundExc class implements a JUnit TestCase 
 * for testing the handling of a bad input filename. A static
 * boolean variable is available in the IO class to enable
 * the throwing of a FileNotFound exception. 
 *
 * @author Bryan Robbins
 *
 */
 
public class TestFileNotFoundExc extends TestCase {
	/**
	* Because the IO object in our integration testing is 
	* actually a mock object, the actual input file name
	* passed to the main method is ignored. The critical
	* of this test is the setting of the flag IO.ThrowFNF
	* to true.  The test then verifies that the message 
	* "Input File name is not correct " is printed to 
	* System.out.
	*/
	public void testFNF()
	{
		edu.umd.cs.guitar.model.IO.ThrowFNF = true;
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
		
		int pos = out.toString().indexOf("Input File name is not correct ");
		
		assertTrue(pos != -1);
	}
}
