package edu.umd.cs.guitar.graph.plugin;

import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.EventGraphType;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.model.data.EventsType;
import edu.umd.cs.guitar.model.data.GUIStructure;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.model.data.RowType;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.model.wrapper.EventWrapper;
import edu.umd.cs.guitar.model.wrapper.GUIStructureWrapper;
import edu.umd.cs.guitar.model.wrapper.GUITypeWrapper;

/**
 * Gui Structure to Event Flow Graph converter
 * 
 * <p>
 * 
 * @author <a href="mailto:charlie.biger@gmail.com"> Charlie BIGER </a>
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao N. Nguyen </a>
 * 
 * @version 1.1
 */
public class PraveenConverter3 implements GraphConverter {

    /**
     * 
     */
    private static final String EVENT_ID_PREFIX = "e";
    ObjectFactory factory = new ObjectFactory();

    EFG efg;
    EventsType dEventList;
    List<List<String>> eventGraph;

    /**
     * Event list wrapper
     */
    List<EventWrapper> wEventList = new ArrayList<EventWrapper>();

    /*
     * (non-Javadoc)
     * 
     * @see edu.umd.cs.guitar.graph.plugin.GraphConverter#getInputType()
     */
    public Class<?> getInputType() {
        return GUIStructure.class;
    }

    /**
     * Convert the GUI Structure to an Event Flow Graph and Return the Event
     * Flow Graph Object
     * 
     * @param url
     *            GUI Structure to convert
     * @return the Event Flow Graph corresponding to the i,put GUI Structure
     * @see GraphConverter
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.umd.cs.guitar.graph.plugin.GraphConverter#generate(java.lang.Object)
     */
    public Object generate(Object obj) throws InstantiationException {
		return null;
    }

   
    

}

