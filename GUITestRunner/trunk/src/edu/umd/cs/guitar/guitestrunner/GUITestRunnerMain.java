package edu.umd.cs.guitar.guitestrunner;


import org.netbeans.jemmy.ClassReference;



public class GUITestRunnerMain {

	public static void main(String[] args) throws Exception
	{
		if( args.length < 2 )
		{
			usage();
			return;
		}
		
		String mainClassName = args[0];
		String testFilename = args[1];

		// Run the test case.
		GUITARTestcaseLoader loader = new GUITARTestcaseLoader(testFilename);
		TestcaseRunner runner = new GUITARTestcaseRunner(loader.getFilename(), loader.getSteps());
		
		// Assume arguments past the third are monitors that
		// we want to install.
		for( int i = 2; i < args.length; ++i )
		{
			((GUITARTestcaseRunner) runner).addTestcaseMonitor(args[i]);
		}
		
		new ClassReference(mainClassName).startApplication();
		runner.run();
	}

	/**
	 * Print usage information.
	 */
	private static void usage() {
		System.err.println("Usage:  guitestrunner mainclassname testfilename <monitor1> <monitor2> <...>");
	}
}
