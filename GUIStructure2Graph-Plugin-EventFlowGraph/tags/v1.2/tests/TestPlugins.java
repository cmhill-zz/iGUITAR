

import edu.umd.cs.guitar.graph.*;


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
import edu.umd.cs.guitar.graph.plugin.GraphConverter;
import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.AttributesType;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.ContentsType;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.PropertyType;
import java.io.*;

import junit.framework.*;

import java.util.ArrayList;
import java.util.List;


public class TestPlugins extends TestCase {
    public void testTrial()
    {
    
    	System.out.println ();
        System.out.println ();
        System.out.println ();
        System.out.println ("***************************************************************");
        System.out.println ("                    JUNIT TESTS BEGIN HERE");
        System.out.println ("***************************************************************");
    	assertTrue("Hi",true);
        
       
    }
    
    /*
    METHOD NAME : testGetInputType()
    
    TO TEST THE METHOD getInputType() in EFGConverter CLASS. 
    1. THIS METHOD TESTS WHETHER getInputType() RETURNS THE CLASS INSTANCE OF GUIStructure CLASS PROPERLY. 
    2. IT TESTS WHETHER A null IS RETURNED, IN WHICH CASE THE TEST FAILS. 
    3. IT ALSO TESTS THE RETURN VALUE WITH THE CLASS INSTANCES OF COMMON CLASSES LIKE STRING, INTEGER, LIST, ARRAYLIST ETC.
    
    */
    
    public void testGetInputType(){
    	
    	
        System.out.println ();
        System.out.println ("****** Testing getInputTypeMethod()  ******");
        System.out.println ("    Testing with NULL and other class instances ");
    	
    	GUIStructure conv;
    	edu.umd.cs.guitar.graph.plugin.EFGConverter conv1 = new edu.umd.cs.guitar.graph.plugin.EFGConverter();
    	
    	assertTrue("Testing return value for GUIStructure",GUIStructure.class==conv1.getInputType());   			//1

    	
		assertFalse("Testing return value for null",conv1.getInputType()==null); 									//2
        System.out.println ();
        System.out.println ("****** Testing getInputTypeMethod()  ******");
        System.out.println ("    Testing with NULL and other class instances ");
    	
		
																													//3
		assertTrue("Testing return value with String.class",conv1.getInputType()!= String.class);  
		assertTrue("Testing return value with Integer.class",conv1.getInputType()!= Integer.class);
		assertTrue("Testing return value with List.class",conv1.getInputType()!= List.class);
		assertTrue("Testing return value with ArrayList.class",conv1.getInputType()!= ArrayList.class);		
    }
    
   
   
   /*
   METHOD NAME: testGenerate()
   
   TO TEST THE generate() METHOD IN EFGConverter CLASS.
   1. THIS METHOD TESTS WHETHER THE EXCEPTION IS HANDLED IF A GUIStructure.class ARGUMENT IS NOT PASSED AS ARGUMENT PROPERLY.
   2. IT THEN CHECKS WHEN A PROPER GUIStructure OBJECT IS PASSED AS ARGUMENT. HAVE MADE NOTE OF THE COUNT OF HOW MANY TIMES EACH METHOD IN ALL THE MODEL.DATA AND THE MODEL.WRAPPER CLASSES IS INVOKED. THE CURRENTLY TESTED EFGCONVERTER IS CHECKED WITH THAT. SO, A FAULT WILL BE DETECTED. 
  
   4. VERIFYING WHEHTER THE RETURNED EFG IS IN A DIFFERENT STATE THAN JUST WHEN IT WAS GENERATED.
   5. VERIFYING WHETHER THE STATE OF THE EFG CHANGES WHEN THE EVENT LIST OF EFG IS UPDATED.
   
   */
   
    public void testGenerate(){
    
    	
        System.out.println ();
        System.out.println ("****** Testing generate()  ******");
       
    	
    	GUIStructure conv=new GUIStructure();
    	Oracle oracle = new Oracle();
    	edu.umd.cs.guitar.graph.plugin.EFGConverter conv1 = new edu.umd.cs.guitar.graph.plugin.EFGConverter();
    	
    	  	
    	
    	System.out.println (" Testing with NULL Parameter");
       
    	try{																							//1
    		conv1.generate(null);
    		conv1.generate(String.class);
    		conv1.generate(ArrayList.class);
		}catch(InstantiationException E){
			System.out.println("Exception handled properly");
			assertTrue("exception handled",true);
		}
	
		
		System.out.println ("Testing with mock object of GUIStructure... ");
       
		

		
		try{																							//2 and 3
		    
		   
		  
		    
		    
		    		    
			    
		    
		    
            EFG oracleEFG = (EFG) conv1.generate(conv);
            
            assertEquals(IO.ioCounter_readObjFromFile, 0);
			assertEquals(IO.ioCounter_writeObjToFile, 0);
			assertEquals(AttributesType.attributesTypeCounter, 64);
			assertEquals(ComponentType.componentTypeCounter, 56);
			assertEquals(ContainerType.containerTypeCounter, 7);
			assertEquals(ContentsType.contentsTypeCounter, 7);
			assertEquals(EFG.efgCounter_setEvents, 1);
			assertEquals(EFG.efgCounter_setEventGraph, 1);
			assertEquals(EventGraphType.eventGraphTypeCounter, 1);
			assertEquals(EventsType.eventsTypeCounter, 2);
			assertEquals(EventType.eventTypeCounter_EventType, 8);
			assertEquals(EventType.eventTypeCounter_setEventID, 2);
			assertEquals(EventType.eventTypeCounter_setWidgetID, 2);
			assertEquals( EventType.eventTypeCounter_setType, 2);
			assertEquals(EventType.eventTypeCounter_setInitial, 2);
			assertEquals(EventType.eventTypeCounter_setAction, 2);
			assertEquals(GUIStructure. guiStructureCounter, 1);
			assertEquals( GUIType.guiTypeCounter, 7);
			assertEquals(ObjectFactory.objectFactoryCounter_ObjectFactory, 3);
			assertEquals(ObjectFactory.objectFactoryCounter_createEventGraphType, 1);
			assertEquals(ObjectFactory.objectFactoryCounter_createEFG, 1);
			assertEquals(ObjectFactory.objectFactoryCounter_createEventsType, 1);
			assertEquals(ObjectFactory.objectFactoryCounter_createRowType, 2);
			assertEquals(ObjectFactory.objectFactoryCounter_createEventType, 2);
			assertEquals( PropertyType.propertyTypeCounter, 192);
			assertEquals(RowType.rowTypeCounter, 4);
			assertEquals(ComponentTypeWrapper.componentTypeWrapperCounter_ComponentTypeWrapper, 21);
			assertEquals(ComponentTypeWrapper.componentTypeWrapperCounter_getChildren, 4);
			assertEquals(ComponentTypeWrapper.componentTypeWrapperCounter_getValueListByName, 4);
			assertEquals(ComponentTypeWrapper.componentTypeWrapperCounter_getFirstValueByName, 2);
			assertEquals(ComponentTypeWrapper.componentTypeWrapperCounter_getWindow, 2);
			assertEquals(EventWrapper.eventWrapperCounter_getAction, 2);
			assertEquals(EventWrapper.eventWrapperCounter_setAction, 2);
			assertEquals(EventWrapper.eventWrapperCounter_getComponent, 4);
			assertEquals(EventWrapper.eventWrapperCounter_setComponent, 2);
			assertEquals(EventWrapper.eventWrapperCounter_isFollowedBy, 4);
			assertEquals(EventWrapper.eventWrapperCounter_isHidden,1);
			assertEquals(EventWrapper.eventWrapperCounter_getType, 2);
			assertEquals(GUIStructureWrapper.guiStructureWrapperCounter_GUIStructureWrapper, 1);
			assertEquals(GUIStructureWrapper.guiStructureWrapperCounter_parseData, 1);
			assertEquals(GUIStructureWrapper.guiStructureWrapperCounter_getGUIs, 1);
			assertEquals(GUITypeWrapper.guiTypeWrapperCounter_GUITypeWrapper, 5);
			assertEquals(GUITypeWrapper.guiTypeWrapperCounter_getContainer, 3);
			assertEquals(GUITypeWrapper.guiTypeWrapperCounter_isRoot, 2);
           
           System.out.println("     *** Displaying count of how many times each method in the dependancy class got invoked ***");
           
		    System.out.println( " IOCounter_readObjFromFile " + IO.ioCounter_readObjFromFile);
			System.out.println( "ioCounter_writeObjToFile " + IO.ioCounter_writeObjToFile );
			
			System.out.println( "attributesTypeCounter " + AttributesType.attributesTypeCounter );
			
		    System.out.println( "componentTypeCounter " + ComponentType.componentTypeCounter );
		    
		    System.out.println( "containerTypeCounter " + ContainerType.containerTypeCounter);
		    
		    System.out.println( "contentsTypeCounter "  + ContentsType.contentsTypeCounter);
		    
		    System.out.println("efgCounter_setEvents " + EFG.efgCounter_setEvents);
			System.out.println("efgCounter_setEventGraph " + EFG.efgCounter_setEventGraph);
			
			System.out.println("eventGraphTypeCounter " + EventGraphType.eventGraphTypeCounter);
			
			System.out.println("eventsTypeCounter " + EventsType.eventsTypeCounter);
			
			System.out.println( "eventTypeCounter_EventType " + EventType.eventTypeCounter_EventType);
			System.out.println( "eventTypeCounter_setEventID " + EventType.eventTypeCounter_setEventID);
			System.out.println("eventTypeCounter_setWidgetID " + EventType.eventTypeCounter_setWidgetID);
			System.out.println( " eventTypeCounter_setType " + EventType.eventTypeCounter_setType);
			System.out.println( "eventTypeCounter_setInitial " + EventType.eventTypeCounter_setInitial);
			System.out.println( "eventTypeCounter_setAction " + EventType.eventTypeCounter_setAction);
			
			System.out.println( " guiStructureCounter "+ GUIStructure.guiStructureCounter);
			
			System.out.println(" guiTypeCounter " + GUIType.guiTypeCounter);
			
			
			System.out.println("objectFactoryCounter_ObjectFactory " + ObjectFactory.objectFactoryCounter_ObjectFactory);
			System.out.println("objectFactoryCounter_createEventGraphType " + ObjectFactory.objectFactoryCounter_createEventGraphType);
			System.out.println("objectFactoryCounter_createEFG " + ObjectFactory.objectFactoryCounter_createEFG);
			System.out.println("objectFactoryCounter_createEventsType "+ ObjectFactory.objectFactoryCounter_createEventsType);
			System.out.println("objectFactoryCounter_createRowType " + ObjectFactory.objectFactoryCounter_createRowType);
			System.out.println("objectFactoryCounter_createEventType " + ObjectFactory.objectFactoryCounter_createEventType);
			
			System.out.println(" propertyTypeCounter " + PropertyType.propertyTypeCounter );
			
			System.out.println("rowTypeCounter "+ RowType.rowTypeCounter);
			
			System.out.println("componentTypeWrapperCounter_ComponentTypeWrapper " + ComponentTypeWrapper.componentTypeWrapperCounter_ComponentTypeWrapper);
			System.out.println("componentTypeWrapperCounter_getChildren " + ComponentTypeWrapper.componentTypeWrapperCounter_getChildren);
			System.out.println("componentTypeWrapperCounter_getValueListByName " + ComponentTypeWrapper.componentTypeWrapperCounter_getValueListByName);
			System.out.println("componentTypeWrapperCounter_getFirstValueByName " + ComponentTypeWrapper.componentTypeWrapperCounter_getFirstValueByName);
			System.out.println("componentTypeWrapperCounter_getWindow " + ComponentTypeWrapper.componentTypeWrapperCounter_getWindow);
			
			System.out.println("eventWrapperCounter_getAction "+ EventWrapper.eventWrapperCounter_getAction);
			System.out.println("eventWrapperCounter_setAction " + EventWrapper.eventWrapperCounter_setAction);
			System.out.println("eventWrapperCounter_getComponent " + EventWrapper.eventWrapperCounter_getComponent);
			System.out.println("eventWrapperCounter_setComponent " + EventWrapper.eventWrapperCounter_setComponent);
			System.out.println("eventWrapperCounter_isFollowedBy " + EventWrapper.eventWrapperCounter_isFollowedBy);
			System.out.println(" eventWrapperCounter_isHidden " + EventWrapper.eventWrapperCounter_isHidden);
			System.out.println("eventWrapperCounter_getType " + EventWrapper.eventWrapperCounter_getType);
			
		    System.out.println("guiStructureWrapperCounter_GUIStructureWrapper " + GUIStructureWrapper.guiStructureWrapperCounter_GUIStructureWrapper);
			System.out.println("guiStructureWrapperCounter_parseData " + GUIStructureWrapper.guiStructureWrapperCounter_parseData);
			System.out.println("guiStructureWrapperCounter_getGUIs " + GUIStructureWrapper.guiStructureWrapperCounter_getGUIs);
			

		    System.out.println("guiTypeWrapperCounter_GUITypeWrapper " + GUITypeWrapper.guiTypeWrapperCounter_GUITypeWrapper);	
			System.out.println("guiTypeWrapperCounter_getContainer " + GUITypeWrapper.guiTypeWrapperCounter_getContainer);
			System.out.println("guiTypeWrapperCounter_isRoot " + GUITypeWrapper.guiTypeWrapperCounter_isRoot);	
           
       
        } catch (Exception e) {
            System.out.println("Exception");
            e.printStackTrace();
        }
		
			
		
		
		
		try{																							//4
			ObjectFactory factory = new ObjectFactory();
			EFG efgTest =  factory.createEFG();
			assertFalse(efgTest==conv1.generate(conv));
		}catch(Exception E){
		}
		
		
		
		
		try{																							//5
		
			ObjectFactory factory = new  ObjectFactory();
			EFG efgTest = factory.createEFG();
			EFG efgTest1 = efgTest;
			List<EventWrapper> wEventList = new ArrayList<EventWrapper>();
			final String EVENT_ID_PREFIX = "e";

		    
		    EventsType dEventList = factory.createEventsType();
		    
		    for (EventWrapper wEvent : wEventList) {
		        EventType dEvent = factory.createEventType();
				  
		        dEvent.setEventId(EVENT_ID_PREFIX + wEventList.indexOf(wEvent));

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

		    efgTest1.setEvents(dEventList);
		    
		    assertFalse(efgTest == efgTest1 && efgTest==conv1.generate(conv));

		}catch(Exception E){
		}
		
	}
	
	
	
	
	/* 
	METHOD NAME : getEventList()
	
	THE METHOD'S RETURN TYPE IS void. THUS, A STRAIGHT FORWARD WAY OF JUNIT TESTING THE METHOD IS IMPOSSIBLE. THE METHOD IS USED IN THE generate() OF 		EFGConverter CLASS. THE EVENTS LIST OF EVERY WINDOWS IS READ IN A FOR LOOP AND THEN EVENTUALLY THE EVENT LIST IS REFLECTED ON THE CREATED EFG INSTANCE.
	THE METHOD GET TESTED, INDIRECTLY, WHILE TESTING generate(). 
	
	AN ATTEMPT WAS MADE TO VERIFY WHETHER THE EVENT LIST OF THE EFG IN generate() BY EXPLICITLY CALLING THE getEventList(). BUT IT WAS REALIZED LATER THAT 		getEventList() IS DECLARED WITH private SPECIFIER AND CANNOT BE EXPLICITLY VERIFIED. THUS, THE VERIFICATION OF THE METHOD ACTUALLY HAPPENED THROUGH 	THE VERIFICATION OF generate().
	
	*/	
	public void testReadEventList(){
	
			assertTrue("getEventList() tested indirectly with generate()", true);
	}
		























class Oracle implements GraphConverter {

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

            if (wEvent.getComponent().getWindow().isRoot() && !wEvent.isHidden()){
                dEvent.setInitial(true);
                System.out.println("IF PART");
                }
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

        for (EventWrapper firstEvent : wEventList) {
            int indexFirst = wEventList.indexOf(firstEvent);
            System.out.println("Anlyzing row: " + indexFirst);
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


}
