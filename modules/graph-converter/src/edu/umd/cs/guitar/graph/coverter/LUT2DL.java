package edu.umd.cs.guitar.graph.coverter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.model.data.GUIMap;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.RowType;
import edu.umd.cs.guitar.model.data.WidgetMapElementType;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.model.wrapper.GUIMapWrapper;
import edu.umd.cs.guitar.model.wrapper.GUIStructureWrapper;

public class LUT2DL
{

    private static final String SPLITER = " ";

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        if (args.length < 3)
        {
            System.out.println("Usage:" + LUT2DL.class.getName() + "<MAP file> <efg file>  <DL file> ");
            System.exit(1);
        }

        String lutFile;
        String efgFile;
        String dlFile;

        lutFile = args[0];
        efgFile = args[1];
        dlFile = args[2];

        convert(lutFile, efgFile, dlFile);

        System.out.println("DONE");

    }

    public static void convert(String lutFile, String efgFile, String dlFile)
    {
        EFG efg = (EFG) IO.readObjFromFile(efgFile, EFG.class);
        GUIMap map = (GUIMap) IO.readObjFromFile(lutFile, GUIMap.class);
        StringBuffer result = toCVS(map, efg);

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

    public static StringBuffer toCVS(GUIMap map, EFG efg)
    {

        GUIMapWrapper wMap = new GUIMapWrapper(map);

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
            String eventID = event.getEventId();
            WidgetMapElementType wm = wMap.getWidgetMap(eventID);
            ComponentType component = wm.getComponent();
            ComponentTypeWrapper wComponent = new ComponentTypeWrapper(component);

            String title = " ";
            if (component != null)
                title = wComponent.getFirstValueByName(GUITARConstants.TITLE_TAG_NAME);
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
