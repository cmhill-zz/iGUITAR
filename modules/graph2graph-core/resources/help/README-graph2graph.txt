SYNTAX:
	ant -v -Dproperties=<properties file> -f Graph2Graph.xml

DETAILS:

	The base directory is the one where Graph2Graph.xml and .properties are.

	- All the .jar needed for the execution must be put in a ./jars directory
	- The input parameters are defined in <properties file>. Those are : 

		- input:	
			- defines input event graph file
	
		- output:
			- defines output event graph file
	 
		- map:
			- defines output mapping file between 2 event graphs
		
		- plugin:
			- defines the type of graph converter you want to use. Write the name of the converter (without "Graph2Graph-Plugin")
 			- copy the Graph2Graph-Plugin-ConverterName.jar file in ./jars


EXAMPLE:
	ant -v -Dproperties=Graph2Graph.properties -f Graph2Graph.xml

