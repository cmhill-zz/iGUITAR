##############################
# DEVELOPER NOTES
##############################


I. CHECKING OUT AND BUILDING:
==========================================

1. Checkout guitar trunk
	svn co https://guitar.svn.sourceforge.net/svnroot/guitar/trunk guitar

2. Move to source code directory 
	cd guitar 
	
3. Run ant at the top level without any parameter  for help on various tasks supported 
	ant 


II. DIRECTORY LAYOUT
==========================================

|-dist: 
|	|- guitar:built artifacts
|	|
|	|- tools: release tools
|
|-example-aut: aut for running sample workflow
|
|-lib: third party libraries
|	|- platform: platform specific libs
|
|-manuals: manual
|
|-modules: module specific code and resources
|	|
|	|- <module name>
|	|	|- src: source code
|	|	|
|	|	|- bin: local binary
|	|	|
|	|	|- resources: resources 
|
|-scripts: misc scripts 


III. ADDING A NEW MODULE 
==========================================

1. add a <module> directory directly under modules

2. copy build.xml file from any other module to  the root of <module>

3. add the appropriate build.properties file with module specific variables and dependencies

4. add necessary libraries to lib directory

For more detail on each module design and implementation  refer to java doc under dist/guitar/docs

	
IV. MISCELLANEOUS 
==========================================

- The default log4j level is set to DEBUG. To run GUITAR tool with a different log4j configuration file add  the 
flag -Dlog4j.configuration=<log4j configuration file> to the java call
	
	Example: Run with the INFO level we can use the  edu/umd/cs/guitar/log/guitar-clean.glc configuration file instead of the default one
	
	java -Dlog4j.configuration=edu/umd/cs/guitar/log/guitar-clean.glc <other arguments>

