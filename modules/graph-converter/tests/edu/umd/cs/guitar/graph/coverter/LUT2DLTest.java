/*  
 *  Copyright (c) 2009-@year@. The GUITAR group at the University of Maryland. Names of owners of this group may
 *  be obtained by sending an e-mail to atif@cs.umd.edu
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 *  documentation files (the "Software"), to deal in the Software without restriction, including without 
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *  the Software, and to permit persons to whom the Software is furnished to do so, subject to the following 
 *  conditions:
 * 
 *  The above copyright notice and this permission notice shall be included in all copies or substantial 
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 *  LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 *  EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 *  THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package edu.umd.cs.guitar.graph.coverter;

import java.io.File;

import org.junit.Test;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao N. Nguyen </a>
 */
public class LUT2DLTest
{
    String dataDir = "tests-data/viz";
    // String guiFile = dataDir + File.separator + "ArgoUML.GUI";
    // String efgFile = dataDir + File.separator+ "ArgoUML.EFG";
    // String dlFile = dataDir + File.separator+ "ArgoUML.DL";

    String lutFile = dataDir + File.separator + "ArgoUML-l2_2_5.LUT";
    String efgFile = dataDir + File.separator + "ArgoUML-l2_2_5.EFG";
    String dlFile = dataDir + File.separator + "ArgoUML-l2_2_5.DL";

    @Test
    public void testEFG2DL()
    {
        for (int i = 0; i < 6; i++)
        {
            String lutFile = dataDir + File.separator + "ArgoUML-l2_2_"+i+".LUT";
            String efgFile = dataDir + File.separator + "ArgoUML-l2_2_"+i+".EFG";
            String dlFile = dataDir + File.separator + "ArgoUML-l2_2_"+i+".DL";
            String[] args = new String[] { lutFile, efgFile, dlFile };
            System.out.println(efgFile);
            LUT2DL.main(args);
        }

        
    }
}
