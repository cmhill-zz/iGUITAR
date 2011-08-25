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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.RowType;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.model.wrapper.GUIStructureWrapper;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao N. Nguyen </a>
 */
public class EFG2DL
{

    private static final String SPLITER = " ";

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        if (args.length < 3)
        {
            System.out.println("Usage:" + EFG2DL.class.getName() + "<gui file> <efg file>  <DL file> ");
            System.exit(1);
        }

        String guiFile;
        String efgFile;
        String dlFile;

        guiFile = args[0];
        efgFile = args[1];
        dlFile = args[2];

        convert(guiFile, efgFile, dlFile);

        System.out.println("DONE");

    }

    public static void convert(String guiFile, String efgFile, String dlFile)
    {
        EFG efg = (EFG) IO.readObjFromFile(efgFile, EFG.class);
        GUIStructure gui = (GUIStructure) IO.readObjFromFile(guiFile, GUIStructure.class);
        StringBuffer result = toCVS(gui, efg);

        try
        {
            // Create file
            FileWriter fstream = new FileWriter(dlFile);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(result.toString());
            // Close the output stream
            out.close();
        }
        catch (Exception e)
        {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }

    }

    public static StringBuffer toCVS(GUIStructure gui, EFG efg)
    {

        GUIStructureWrapper wGUI = new GUIStructureWrapper(gui);

        StringBuffer result = new StringBuffer();

        List<EventType> lEvents = efg.getEvents().getEvent();
        String header = "DL N=" + lEvents.size();
        header += ("\n");
        header += "format = fullmatrix";
        header += ("\n");
        result.append(header);

        // Set up node
        String label = "labels:\n";

        for (EventType event : efg.getEvents().getEvent())
        {
            String widgetID = event.getWidgetId();
            ComponentTypeWrapper component = wGUI.getComponentFromID(widgetID);
            String title=" ";
            if (component != null)
                title= component.getFirstValueByName(GUITARConstants.TITLE_TAG_NAME);
            title = title.replaceFirst(",", ":");
            // Set up label
            label += (title + ",");
        }
        result.append(label);
        result.append("\n");

        // Matrix
        result.append("Data:\n");
        List<RowType> lRows = efg.getEventGraph().getRow();

        for (int row = 0; row < lRows.size(); row++)
        {
            List<Integer> lE = lRows.get(row).getE();
            String line = "";
            // line += event.getEventId();

            for (int col = 0; col < lE.size(); col++)
            {
                line = line + SPLITER + lE.get(col);
            }
            result.append(line);
            result.append("\n");
        }

        return result;
    }
}
