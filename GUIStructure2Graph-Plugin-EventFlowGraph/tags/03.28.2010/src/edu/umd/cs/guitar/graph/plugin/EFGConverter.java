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
public class EFGConverter implements GraphConverter {

	/**
	 * 
	 */
	private static final String EVENT_ID_SPLITTER = "_";
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
		if (!(obj instanceof GUIStructure)) {
			throw new InstantiationException("The input class should be "
					+ GUIStructure.class.getName());
		}

		GUIStructure dGUIStructure = (GUIStructure) obj;

		dGUIStructure.getGUI().get(0).getContainer().getContents()
				.getWidgetOrContainer();
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

			String index = getIndexFromWidget(wEvent);

			// dEvent.setEventId(EVENT_ID_PREFIX + index);
			dEvent.setEventId(wEvent.getID());

			dEvent.setWidgetId(wEvent.getComponent().getFirstValueByName(
					GUITARConstants.ID_TAG_NAME));

			dEvent.setType(wEvent.getType());

			dEvent.setAction(wEvent.getAction());

			if (wEvent.getComponent().getWindow().isRoot()
					&& !wEvent.isHidden())
				dEvent.setInitial(true);
			else
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

		System.out.println("Edges in Graphviz format:");
		System.out.println("{");
		for (EventWrapper firstEvent : wEventList) {
			int indexFirst = wEventList.indexOf(firstEvent);
			// System.out.println("Anlyzing row: " + indexFirst);
			RowType row = factory.createRowType();

			// List<EventWrapper> follows = getFollows(firstEvent);

			for (EventWrapper secondEvent : wEventList) {
				int indexSecond = wEventList.indexOf(secondEvent);

				int cellValue = firstEvent.isFollowedBy(secondEvent);

				row.getE().add(indexSecond, cellValue);

				printGraphviz(firstEvent, secondEvent, cellValue);
			}

			lRowList.add(indexFirst, row);
		}
		System.out.println("}");
		dEventGraph.setRow(lRowList);
		efg.setEventGraph(dEventGraph);

		return efg;
	}

	/**
	 * @param firstEvent
	 * @param secondEvent
	 */
	private void printGraphviz(EventWrapper firstEvent,
			EventWrapper secondEvent, int cellValue) {
		if (cellValue > 0) {
			String sSourceTitle = firstEvent.getComponent()
					.getFirstValueByName(GUITARConstants.TITLE_TAG_NAME);
			String sTargetTitle = secondEvent.getComponent()
					.getFirstValueByName(GUITARConstants.TITLE_TAG_NAME);

			System.out.println("\"" + sSourceTitle + "\"->\"" + sTargetTitle
					+ "\"");

		}
	}

	/**
	 * @param wEvent
	 * @return
	 */
	private String getIndexFromWidget(EventWrapper wEvent) {
		// TODO Auto-generated method stub

		String index = wEvent.getComponent().getFirstValueByName(
				GUITARConstants.ID_TAG_NAME);
		index = index.substring(1);

		return index;

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

				// Calculate event ID
				String sWidgetID = component
						.getFirstValueByName(GUITARConstants.ID_TAG_NAME);

				sWidgetID = sWidgetID.substring(1);

				String sEventID = EVENT_ID_PREFIX + sWidgetID;

				String posFix = (sActionList.size() <= 1) ? ""
						: EVENT_ID_SPLITTER
								+ Integer.toString(sActionList.indexOf(action));
				sEventID = sEventID + posFix;

				wEvent.setID(sEventID);
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
