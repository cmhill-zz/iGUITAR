package edu.umd.cs.guitar.graph.plugin;
import junit.framework.*;
import edu.umd.cs.guitar.graph.*;
import edu.umd.cs.guitar.graph.plugin.*;

import java.io.FileNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.InstantiationException;
import java.lang.IllegalAccessException;

/**
 *
 * The BadConverter class is used to simulate the
 * throwing of exceptions by the EFGConverter plugin.
 * Because the EFGConverter source should not be modified
 * directly, this class is used as a substitute as necessary.
 * The class includes two public static boolean flags, ThrowInst
 * and ThrowAccess, that can be set from within test cases to
 * generate exceptions.
 *
 * @author Bryan Robbins
 *
 */
 
public class BadConverter implements GraphConverter {

	public static boolean ThrowInst = false;
	public static boolean ThrowAccess = false;
	/**
	 * The getInputType method is included for completeness.
	 * No exceptions are thrown by this method.
	 */
	public Class<?> getInputType() {
		return Object.class;
	}
	/**
	 * The generate method is capable of throwing Instantiation
	 * and IllegalAccess Exceptions based on the setting of the
	 * static flags in this class.
	 */
	public Object generate(Object obj) throws InstantiationException, IllegalAccessException {
		if(ThrowInst)
			throw new InstantiationException("MOCK INSTANTIATION EXCEPTION");
		if(ThrowAccess)
			throw new IllegalAccessException("MOCK ILLEGAL ACCESS EXCEPTION");
		return new Object();
	}
}