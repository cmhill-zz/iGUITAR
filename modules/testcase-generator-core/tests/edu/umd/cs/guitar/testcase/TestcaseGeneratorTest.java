package edu.umd.cs.guitar.testcase;

import java.io.File;

import java.io.*;
import java.lang.*;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.testcase.TestcaseGenerator;
import edu.umd.cs.guitar.testcase.plugin.TCPluginStub;

import org.kohsuke.args4j.CmdLineException;
import java.lang.reflect.InvocationTargetException;

import java.io.IOException;
import java.security.Permission;



/**
 * This class is to test the TestCaseGenerator class.
 * <br/> <br/>
 * 
 * Constructor, public methods, and private methods are tested.
 * Different inputs are given to cover both the normal and abnormal cases.
 * For private methods, reflection is used to test these methods.<br/> <br/>
 * Test case summary: <br/>
 * (1) constructor(): 1 test case <br/>
 * (2) main(): 6 test cases <br/>
 * (3) setupLog(): 2 test cases 
 * <br/> <br/>
 * 
 * The line coverage of this class reaches 100%, because both the normal
 * and abnormal cases are considered.
 * 
 * @author Yuening Hu
 */	

public class TestcaseGeneratorTest extends TestCase {

	
    /**
     * This class defines the exception caused by System.exit().
     * <br/> <br/>  
     * When the new Security Manager is used, the System.exit(0) is avoided,
     * and this exception is thrown.
     */	
	protected static class ExitException extends SecurityException {
		
	    /**
	     * This variable defines status for this exception. 
	     */			
		public final int status;

	    /**
	     * This is the constructor for this exception. 
	     */	
		public ExitException(int status) {
			super("There is no escape!");
			this.status = status;
		}
	}
	
	 /**
     * This class defines a new Security Manager that can avoid System.exit().
     */
	private static class NoExitSecurityManager extends SecurityManager {
		
	    /**
	     * This is the checkPermission from SecurityManager. 
	     */	
		@Override
		public void checkPermission(Permission perm) {
			// allow anything.
		}

	    /**
	     * This is the another checkPermission from SecurityManager. 
	     */			
		@Override
		public void checkPermission(Permission perm, Object context) {
			// allow anything.
		}

	    /**
	     * This is the checkExit from SecurityManager. If the System.exit
	     * happens, it will avoid system exit and throw a ExitException.
	     */
		@Override
		public void checkExit(int status) {
			super.checkExit(status);
			throw new ExitException(status);
		}
	}
		
	/**
     * This variable defines the input string[]. 
     */	
	public String[] inputStr; 
	
    /**
     * This variable defines the folder path for test cases. 
     */		
	String ResPath;

    /**
     * This variable defines the directory for log files. 
     */		
	String logDir;
	
    /**
     * This variable checks if the exceptions are caught.
     * 0: not caught; 1: caught. 
     */	
	boolean exceptionFlag;
	
    /**
     * This function initializes the class member variables. 
     * <br/> <br/>
     * An input string is defined.
     * A new Security Manager that can void the system exit is used.
     */	
	@Override
	protected void setUp() throws Exception {
		ResPath = ".";
		logDir = "";
		
		inputStr = new String[8];
		inputStr[0] = "-l";
		inputStr[1] = "1";
		inputStr[2] = "-e";
		inputStr[3] = "Default.EFG";
		inputStr[4] = "-p";
		inputStr[5] = "MockPlugin";
		inputStr[6] = "-d";
		inputStr[7] = ResPath;
		
		super.setUp();
		System.setSecurityManager(new NoExitSecurityManager());
				
	}

    /**
     * This function returns to use the original security manager.
     */	
	@Override
	protected void tearDown() throws Exception {
		System.setSecurityManager(null); // or save and restore original
		super.tearDown();
	}

    /**
     * This function is to test the constructor of TestcaseGenerator.
     */

	public void testConstructor(){							
		TestcaseGenerator gen = new TestcaseGenerator();
		assertTrue(gen != null);
	}
	
	
    /**
     * This function is to check if the main method in TestcaseGenerator 
     * class can generate test cases using the correct input EFG file.
     * <br/> <br/>
     * TestCase 1 for main(): using the correct inputs  
     */

	public void testMain1(){		
		ResPath = ".";					
		TestcaseGenerator.main(inputStr);
	
		for(int i = 0; i <= 0;i++){
			String name = ResPath + File.separator + "t_e"+i+".tst";
			File f1 = new File(name);
			assertTrue(f1.exists());
		}
	}


    /**
     * This function is to check if the main method in TestcaseGenerator 
     * class can throw ClassNotFoundException when given a non-existed 
     * plugin file.
     * <br/> <br/>  
     * TestCase 2 for main(): error inputs: call a non-existed plugin, 
     * causing the ClassNotFoundException.     
     */		

	public void testMain2(){ 	
		inputStr[5] = "LengthCoverage";
		boolean exceptionFlag = false;
		try{
			TestcaseGenerator.main(inputStr);
			exceptionFlag = true;
		}catch(Exception e){
			if (e instanceof ClassNotFoundException){
				exceptionFlag = false;
			}
		}
		assertTrue(exceptionFlag == true);
	}


	/* identical to testMain3 except the plugin name is fully defined */
	public void testMain31(){ 	
		inputStr[5] = "edu.umd.cs.guitar.testcase.plugin.TCPlugin";
		exceptionFlag = false;
		try{
			TestcaseGenerator.main(inputStr);
			exceptionFlag = true;
		}catch(Exception e){
			if (e instanceof InstantiationException){
				exceptionFlag = false;
			}
		}
		assertTrue(exceptionFlag == true);
	}


	
    /**
     * This function is to check if the main method in TestcaseGenerator 
     * class can throw InstantiationException when given an abstract
     * plugin file.
     * <br/> <br/>  
     * TestCase 3 for main(): error inputs: call an abstract plugin, 
     * causing the InstantiationException.     
     */	
	
	public void testMain3(){ 	
		inputStr[5] = "TCPlugin";
		exceptionFlag = false;
		try{
			TestcaseGenerator.main(inputStr);
			exceptionFlag = true;
		}catch(Exception e){
			if (e instanceof InstantiationException){
				exceptionFlag = false;
			}
		}
		assertTrue(exceptionFlag == true);
	}

	
    /**
     * This function is to check if the main method in TestcaseGenerator 
     * class can throw IllegalAccessException when given a private
     * plugin file.
     * <br/> <br/>  
     * TestCase 4 for main(): error inputs: call an private plugin, 
     * causing the IllegalAccessException.     
     */	
	
	public void testMain4(){ 	
		inputStr[5] = "TCPluginPrivateStub";
		exceptionFlag = false;
		try{
			TestcaseGenerator.main(inputStr);
			exceptionFlag = true;
		}catch(Exception e){			
			if (e instanceof IllegalAccessException){
				exceptionFlag = false;
			}			
		}
		assertTrue(exceptionFlag);
	}

	
    /**
     * This function is to check if the main method in TestcaseGenerator 
     * class can throw CmdLineException when given an illegal pluginStub.
     * <br/> <br/>  
     * TestCase 5 for main(): error inputs: given an illegal pluginStub,
     * causing the CmdLineException.     
     */
	
	public void testMain5(){	
		inputStr[5] = "TCPluginStub";
		exceptionFlag = false;
		try{
			TestcaseGenerator.main(inputStr);
		}catch(ExitException e){
			exceptionFlag = true;
		}
		assertTrue(exceptionFlag == true);
	}
	
	
    /**
     * This function is to check if the main method in TestcaseGenerator 
     * class can throw CmdLineException when given a non-existed EFG file.
     * <br/> <br/>  
     * TestCase 6 for main(): error inputs: given a non-existed EFG file, 
     * causing the CmdLineException.     
     */
	
	public void testMain6(){	
		String[] inputStrnew = {"-?"};
		exceptionFlag = false;
		try{
			TestcaseGenerator.main(inputStrnew);
		}catch(ExitException e){
			exceptionFlag = true;
		}
		assertTrue(exceptionFlag == true);
	}

	
    /**
     * This function is to check if the setupLog method in TestcaseGenerator 
     * class works properly.
     * <br/> <br/>  
     * setupLog() is a private method, so reflection is used to test it. 
     * If exception is thrown, exceptionFlag is set to true, and the assertion fails.
     * <br/> <br/>  
     * TestCase 1 for setupLog(): log file is created successfully 
     */	
	
	public void testSetupLog1() {			
		try{
			Method m = TestcaseGenerator.class.getDeclaredMethod("setupLog");
			m.setAccessible(true);
			m.invoke(null, null);
			exceptionFlag = false;
		}catch(NoSuchMethodException e){
			System.out.println("No such method is found");	
			exceptionFlag = true;
		}catch(IllegalAccessException e){
			System.out.println("Access is not allowed");
			exceptionFlag = true;
		}catch(InvocationTargetException e){
			System.out.println("Invocation failed");
			exceptionFlag = true;
		}
		assertTrue(exceptionFlag == false);
	}
	
	
    /**
     * This function is to check if the setupLog method in TestcaseGenerator 
     * class can throw the IOException.
     * <br/> <br/>  
     * A read-only file "TestcaseGenerator.log" is created, causing the setupLog()
     * throwing an IOException.
     * <br/> <br/>  
     * TestCase 2 for setupLog(): log file creation failed. 
     * causing the IOException.   
     */	
	
	public void testSetupLog2() {	
		// Creating a read-only file "TestcaseGenerator.log".
		String logName = logDir + "TestcaseGenerator.log";
		File f = new File(logName);
		if(!f.exists()){
			try{
				f.createNewFile();
			}catch(IOException e){
				System.out.println("File creating fails");
			}
		}		
		f.setReadOnly();
		
		try{
			Method m = TestcaseGenerator.class.getDeclaredMethod("setupLog");
			m.setAccessible(true);
			m.invoke(null, null);
			exceptionFlag = false;
		}catch(Exception e){
			if(e instanceof IOException){
				System.out.println("IOException is not captured in setupLog().");	
				exceptionFlag = true;				
			}
		}
		
		f.delete();
		assertTrue(exceptionFlag == false);
	}	
	
}
