To run GUIStructure -> EFG Converter on your GUIStructure file : 
	The base directory is the one where GUIStructure2GraphConvert.xml and GUIStructure2GraphConvert.properties are.
	
	- All the .jar needed for the execution must be put in a ./jars directory
	- The input parameters are defined in TestCaseGeneratorCore.properties. Those are : 
		- inputFile : 
			- define the name of the input file (.GUI file). 
			- copy the input file in the base directory

		- outputFile : 
			- define the name of the output file (.EFG file). 
			- it will be created in the base directory
	
		- converter :
			- define the type of GUIStructure -> EFG converter you want to use. Write the name of the converter (without "TestCaseConverterPlugins-")
 			- copy the GUIStructure2GraphPlugins-ConverterName.jar file in ./jars	

For example
ant -v -f GUIStructure2GraphConvert.xml
