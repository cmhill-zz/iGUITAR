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
public class PraveenConverter2 implements GraphConverter {

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
        

        GUIStructure dGUIStructure = (GUIStructure) obj;
        GUIStructureWrapper wGUIStructure = new GUIStructureWrapper(
                dGUIStructure);

        wGUIStructure.parseData();

        List<GUITypeWrapper> wWindowList = wGUIStructure.getGUIs();

        System.out.println("size: " + wWindowList.size());

        for (GUITypeWrapper window : wWindowList) {
            readEventList(window.getContainer());
        }

        efg = factory.createEFG();

        // -------------------------------------
        // Reading event name
        // -------------------------------------
        dEventList = factory.createEventsType();
        for (EventWrapper wEvent : wEventList) {
            EventType dEvent = factory.createEventType();

            // TODO: Change to a hash function
            dEvent.setEventId(EVENT_ID_PREFIX + wEventList.indexOf(wEvent));

            dEvent.setWidgetId(wEvent.getComponent().getFirstValueByName(
                    GUITARConstants.ID_TAG_NAME));

            dEvent.setType(wEvent.getType());

            dEvent.setAction(wEvent.getAction());

            dEvent.setInitial(false);

            dEventList.getEvent().add(dEvent);
        }

        efg.setEvents(dEventList);

        // -----------------------------
        // Building graph
        // -----------------------------
        eventGraph = new ArrayList<List<String>>();
        //
        EventGraphType dEventGraph = factory.createEventGraphType();

        List<RowType> lRowList = new ArrayList<RowType>();

        for (EventWrapper firstEvent : wEventList) {
            int indexFirst = wEventList.indexOf(firstEvent);
//            System.out.println("Anlyzing row: " + indexFirst);
            RowType row = factory.createRowType();

            // List<EventWrapper> follows = getFollows(firstEvent);

            for (EventWrapper secondEvent : wEventList) {
                int indexSecond = wEventList.indexOf(secondEvent);

                int cellValue = firstEvent.isFollowedBy(secondEvent);

                row.getE().add(indexSecond, cellValue);

//                // Debug
//                if (cellValue > 0) {
//                    System.out.println(firstEvent.getComponent()
//                            .getFirstValueByName("ID")
//                            + "->"
//                            + secondEvent.getComponent().getFirstValueByName(
//                                    "ID") + ": " + cellValue);
//
//                }
            }

            lRowList.add(indexFirst, row);
        }
        dEventGraph.setRow(lRowList);
        efg.setEventGraph(dEventGraph);

        return efg;
    }

    /**
     * Get the event list contained in a component
     * 
     * @param component
     * @return
     */
    private void readEventList(ComponentTypeWrapper component) {

        List<String> sActionList = component
                .getValueListByName(GUITARConstants.EVENT_TAG_NAME);

        if (sActionList != null)
            for (String action : sActionList) {
                EventWrapper wEvent = new EventWrapper();
                wEvent.setAction(action);
                wEvent.setComponent(component);
                wEventList.add(wEvent);
            }

        List<ComponentTypeWrapper> wChildren = component.getChildren();
        if (wChildren != null)
            for (ComponentTypeWrapper wChild : wChildren) {
                readEventList(wChild);
            }
    }

}
