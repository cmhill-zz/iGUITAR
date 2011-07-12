/*	
 *  Copyright (c) 2009-@year@. The GUITAR group at the University of Maryland. Names of owners of this group may
 *  be obtained by sending an e-mail to atif@cs.umd.edu
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 *  documentation files (the "Software"), to deal in the Software without restriction, including without 
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *	the Software, and to permit persons to whom the Software is furnished to do so, subject to the following 
 *	conditions:
 * 
 *	The above copyright notice and this permission notice shall be included in all copies or substantial 
 *	portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 *	LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 *	EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 *	IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 *	THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package edu.umd.cs.guitar.graph;

import java.io.FileNotFoundException;

import edu.umd.cs.guitar.graph.plugin.GraphConverter;
import edu.umd.cs.guitar.model.IO;

/**
 * Entry point to convert GUI structure to different kind of graphs
 * 
 * @author <a href="mailto:charlie.biger@gmail.com"> Charlie BIGER </a>
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao N. Nguyen </a>
 * 
 * @version 1.0
 */
public class GUIStructure2GraphConverter {

    /**
     * @param args
     */
    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
            if (args.length < 2) {
                String help = "";
                System.out.println(help);
            } else {

                // TODO: Make a configuration for parameters
                String PLUGIN = args[0];
                String INPUT_FILE = args[1];
                String OUTPUT_FILE = args[2];

                System.out
                        .println("===========================================");
                System.out.println("GUIStructure2GraphConverter");
                System.out.println();
                System.out.println("\tPlugin: \t" + PLUGIN);
                System.out.println("\tInput file: \t" + INPUT_FILE);
                System.out.println("\tOutput file: \t" + OUTPUT_FILE);

                System.out
                        .println("===========================================");

                Class<?> converterClass = Class.forName(GraphConverter.class
                        .getPackage().getName()
                        + "." + PLUGIN);
                GraphConverter converter = (GraphConverter) converterClass
                        .newInstance();

                Object obj = IO.readObjFromFile(INPUT_FILE, converter
                        .getInputType());

                IO.writeObjToFile(converter.generate(obj), OUTPUT_FILE);
                System.out.println("DONE");

            }
        } catch (ClassNotFoundException e) {
            System.out
                    .println("The converter can not be found. Please make ensure that the converter name is correct and the corresponding .jar file can be reached.");
            
        } catch (InstantiationException e) {
            System.out
                    .println("The converter is defined as an Abstract class, or an interface, or its constructor is not accessible without parameters.");
            System.out.println("Please Report this bug");
            
        } catch (IllegalAccessException e) {
            System.out.println("The converter is not accessible");
            
        } catch (FileNotFoundException e) {
            System.out.println("Input File name is not correct ");
            
        } catch (Exception e) {
            System.out.println("Unknown ERROR");
            e.printStackTrace();
        }
    }
}
