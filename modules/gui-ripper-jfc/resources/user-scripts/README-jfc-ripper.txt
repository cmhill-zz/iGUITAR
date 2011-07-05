To run jfcripper for your application:

	1. Modify the parameters starting with "application." in file jfcripper.properites  
	for you application under test (AUT).
	
	2. Run the ant command:
		ant -Dproperties=jfcripper.properties -f jfcripper.xml
		
		
	NOTE: You can also create new property files for each of your applications and run the customized command: 		
		
		ant -Dproperties=<The Ripper configuration file for your AUT> -f jfcripper.xml
		
		Example:  
		
			ant -Dproperties=Project.properties -f jfcripper.xml
		
		  
