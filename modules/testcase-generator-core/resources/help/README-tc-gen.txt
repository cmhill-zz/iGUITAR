To run Test Case Generator on your EFG file : 
	The base directory is the one where TestCaseConverter.xml and TestCaseConverter.properties are.

	- All the .jar needed for the execution must be put in a ./jars directory
	- The input parameters are defined in TestCaseGenerator.properties. Those are : 

		- converter :
			- defines the type of EFG -> TST converter you want to use. Write the name of the converter (without "TestCaseConverterPlugins-")
 			- copy the TestCaseConverterPlugins-ConverterName.jar file in ./jars

		- inputFile : 
			- defines the name of the input file (.EFG file). 
			- copy the input file in the base directory	
		
		- testCaseFilePrefix :
			- defines the prefix of the filename in which test cases will be written
		
		- testCaseFileSuffix :
			- defines the prefix of the filename in which test cases will be written

			example : 
				testCaseFilePrefix = testCase/test
				testCaseFileSuffix = .TST
				will generate test1.TST , test2.TST etc.. in the testCase directory

		- testCaseNumber :
			- this parameter is needed to run the RandomTestCase plugin. It defines the number of Test Case to be created.

For example
ant -v -f TestCaseGenerator.xml

