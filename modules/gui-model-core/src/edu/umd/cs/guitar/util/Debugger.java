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
package edu.umd.cs.guitar.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 * 
 */
public class Debugger {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    public static void pause() {
        pause("");
    }

    /**
     * Pause for a while
     */
    public static void pause(Object msg) {
        System.out.println("GUITAR DEBUG - Pause: "+msg);
        // Pause
        BufferedReader stdin = new BufferedReader(new InputStreamReader(
                System.in));
        try {
            stdin.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void println(Object msg) {
        System.err.println(msg);
    }

    /**
     * Get name of methods supported by an object
     * 
     * @param obj
     */
    public static void printlnMethodSupported(Object obj) {
        Method[] methods = obj.getClass().getMethods();
        for (Method m : methods) {
            System.out.println(m.getName());
        }
    }

    /**
     * Get name of methods supported by an object
     * 
     * @param obj
     */
    public static void printlnBeanMethodSupported(Object obj) {
        Method[] methods = obj.getClass().getMethods();
        String sMethodName;
        System.out.println("set");
        for (Method m : methods) {
            sMethodName = m.getName();
            if (sMethodName.startsWith("set"))
                System.out.println(sMethodName);
        }
        System.out.println();
        System.out.println("get");
        for (Method m : methods) {
            sMethodName = m.getName();
            if (sMethodName.startsWith("get"))
                System.out.println(sMethodName);
        }
    }

}
