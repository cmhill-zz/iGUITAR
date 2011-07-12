import junit.framework.*;
import java.io.*;
import edu.umd.cs.guitar.graph.*;

/**
* The TestExample class calls the main function in GUIStructure2GraphConverter.java which takes input
* three arguments namely, the converter to use, the input file and the output file
*/


public class TestExample2 extends TestCase {

/**
* args1 is a String array which contains one element ("ConverterNotPresent") which denotes a converter name that is not present
*/
public	String[] args1={"ConverterNotPresent"};	

/**
* args2 is a String array which contains three elements ("PraveenConverter1", ".//input.GUI", ".//output1.EFG") where PraveenConverter1
* implements GraphConverter interface and its generate() function returns an empty string and the other two elements specify the input
* and output files (and respective paths)
*/
public	String[] args2 = {"PraveenConverter1",".//input.GUI", ".//output1.EFG"};

/**
* args3 is a String array which contains three elements ("PraveenConverter2", ".//input.GUI", ".//output2.EFG") where PraveenConverter2
* implements GraphConverter interface and its generate() function is the generic function to create the EFG objectand the other two elements specify the 
* input  and output files (and respective paths)
*/

public	String[] args3 = {"PraveenConverter2",".//input.GUI", ".//output2.EFG"};

/**
* args4 is a String array which contains three elements ("PraveenConverter3", ".//input.GUI", ".//output3.EFG") where PraveenConverter3
* implements GraphConverter interface and its generate() function returns null and the other two elements specify the input
* and output files (and respective paths)
*/

public	String[] args4 = {"PraveenConverter3",".//input.GUI", ".//output3.EFG"};

/**
* args5 is a String array which contains three elements ("GraphConverter", ".//input.GUI", ".//output4.EFG") where GraphConverter
* is an interface with generate() function declared without any body and the other two elements specify the input
* and output files (and respective paths)
*/

public	String[] args5 = {"GraphConverter",".//input.GUI", ".//output4.EFG"};

/** args6 is a String array with three elements ("ConverterNotPresent",".//input.GUI", ".//output5.EFG") where ConverterNotPresent is an undeclared
* converter name and the remaining two elements specify the input and output filenames with their respective paths
*/


public	String[] args6 = {"ConverterNotPresent",".//input.GUI", ".//output5.EFG"};



/** This function aggregates the unit test assertions by calling the GUIStructure2GraphConverter::main function with 
* varying converter names, for which corresponding classes have been created. For the converters used, the generate() method 
* in their implementation overrides the generate() method in the GraphConverter interface by either returning null, an empty
* string or by generating the EFG object
*/
    public void testOne()
    {
	
	GUIStructure2GraphConverter.main(args1);


	GUIStructure2GraphConverter.main(args2);

	File f1 = new File(".//output1.EFG");
	assertTrue(f1.exists() && f1.length()==0);

	GUIStructure2GraphConverter.main(args3);
	File f2 = new File(".//output2.EFG");
	assertTrue(f2.exists() && f2.length()> 0);
	
	GUIStructure2GraphConverter.main(args4);
	File f3 = new File(".//output3.EFG");
	assertTrue(f3.exists() && f3.length()==0);
	
	GUIStructure2GraphConverter.main(args5);
	GUIStructure2GraphConverter.main(args6);
	








    }
}
