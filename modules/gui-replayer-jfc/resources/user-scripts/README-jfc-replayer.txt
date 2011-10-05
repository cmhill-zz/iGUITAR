To run jfcreplayer for your application:

	1. Modify the parameters starting with "application." in file jfcreplayer.properites  
	for you application under test (see the example in jfcreplayer.properties).
	
	2. Run the ant command:
		ant -Dproperties=<Your replayer properties file>  -f jfcreplayer.xml
		
		Example: ant -Dproperties=jfcreplayer.properties -f jfcreplayer.xml 
		  
