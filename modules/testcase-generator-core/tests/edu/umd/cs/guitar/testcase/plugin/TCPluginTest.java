package edu.umd.cs.guitar.testcase.plugin;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import edu.umd.cs.guitar.model.IO;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.testcase.TestCaseGeneratorConfiguration;
import edu.umd.cs.guitar.testcase.TestcaseGenerator;
import edu.umd.cs.guitar.testcase.plugin.TCPlugin;
import edu.umd.cs.guitar.testcase.plugin.TCPluginStub;
import edu.umd.cs.guitar.testcase.plugin.TCPluginPrivateStub;
import edu.umd.cs.guitar.testcase.plugin.EFGStub;
import edu.umd.cs.guitar.testcase.plugin.EFGVoidStub;
import edu.umd.cs.guitar.testcase.plugin.EFG5EventsStub;

import java.lang.StackOverflowError;

/**
 * This class is to test the TCPlugin class. <br/>
 * <br/>
 * 
 * Since it is an abstract class, the mock class TCPluginStub, EFGStub, 
 * EFGVoidStub and EFG5EventsStubare created to test TCPlugin. All the 
 * methods including three package-private methods and three private 
 * methods are tested through giving the mock and inputs. <br/> <br/>
 * Test case summary: <br/>
 * (1) parseInitialEvents(): 3 test cases <br/>
 * (2) parseFollowRelations(): 3 test cases <br/>
 * (3) Initialize(): 3 test cases <br/>
 * (4) DFS(): 3 test cases <br/>
 * (5) testGetPathToRoot(): 5 test cases <br/>
 * (6) testWriteToFile(): 1 test case <br/>
 * <br/>
 * 
 * The line coverage of this class reaches 100%, because both the normal 
 * and abnormal cases are considered.
 * 
 * @author Yuening Hu
 */

public class TCPluginTest extends TestCase {

	/**
	 * This variable is to get an instance of TCPluginStub, so the methods in
	 * TCPlugin class can be tested. Its EFG variable is EFGStub.
	 */
	private TCPluginStub m_TCPluginStub;

	/**
	 * This variable is to get an instance of TCPluginStub, so the methods in
	 * TCPlugin class can be tested. Its EFG variable is EFGVoidStub.
	 */
	private TCPluginStub m_TCPluginVoidStub;

	/**
	 * This variable is to get an instance of TCPluginStub, so the methods in
	 * TCPlugin class can be tested. Its EFG variable is EFGVoidStub.
	 */
	private TCPluginStub m_TCPlugin5EventsStub;
	
	/**
	 * This function initializes the class member variables. <br/>
	 * <br/>
	 * A new object of TCPluginStub is created. An EFG object is provided to get
	 * the actual result.
	 */
	@Override
	protected void setUp() {
		this.m_TCPluginStub = new TCPluginStub();
		this.m_TCPluginStub.efg = new EFGStub();

		this.m_TCPluginVoidStub = new TCPluginStub();
		this.m_TCPluginVoidStub.efg = new EFGVoidStub();
		
		this.m_TCPlugin5EventsStub = new TCPluginStub();
		this.m_TCPlugin5EventsStub.efg = new EFG5EventsStub();

                m_TCPluginStub.initialize();
                m_TCPluginVoidStub.initialize();
                m_TCPlugin5EventsStub.initialize();

	}

	
	/**
	 * This function accesses the private method ParseInitialEvents() in
	 * TCPlugin class. <br/>
	 * Since ParseInitialEvents() is a private method, the reflection is used to
	 * test it.
	 */
	private void accessParseInitialEvents(TCPlugin object) {
		// using reflection to call the private methods
		try {
			Method m = TCPlugin.class.getDeclaredMethod("parseInitialEvents");
			m.setAccessible(true);
			m.invoke(object, null);
		} catch (NoSuchMethodException e) {
			System.out.println("No such method is found");
		} catch (IllegalAccessException e) {
			System.out.println("Access is not allowed");
		} catch (InvocationTargetException e) {
			System.out.println("Invocation failed");
		}
	}

	/**
	 * This function tests the ParseInitialEvents() method in TCPlugin class. <br/>
	 * <br/>
	 * parseInitialEvents() method gets the events from EFG.events. Since it is
	 * a private method, the reflection is used to test it. <br/>
	 * <br/>
	 * Testcase1 for ParseInitialEvents(): using m_TCPluginStub
	 */
	public void testParseInitialEvents1() {

		// define the expected result
		String nameList = "e0e1e2";

		// get the real result
		String name = "";
		for (EventType event : m_TCPluginStub.initialEvents) {
			name += event.getEventId();
		}

		// comparing
		assertEquals(name, nameList);
	}

	/**
	 * This function tests the ParseInitialEvents() method in TCPlugin class. <br/>
	 * <br/>
	 * parseInitialEvents() method gets the events from EFG.events. Since it is
	 * a private method, the reflection is used to test it. <br/>
	 * <br/>
	 * Testcase2 for ParseInitialEvents(): m_TCPluginVoidStub
	 */
	public void testParseInitialEvents2() {
		// comparing
		assertEquals(m_TCPluginVoidStub.initialEvents.size(), 0);
	}

	/**
	 * This function tests the ParseInitialEvents() method in TCPlugin class. <br/>
	 * <br/>
	 * parseInitialEvents() method gets the events from EFG.events. Since it is
	 * a private method, the reflection is used to test it. <br/>
	 * <br/>
	 * Testcase3 for ParseInitialEvents(): using m_TCPlugin5EventsStub
	 */
	public void testParseInitialEvents3() {

		// define the expected result
		String nameList = "e0";

		// get the real result
		String name = "";
		for (EventType event : m_TCPlugin5EventsStub.initialEvents) {
			name += event.getEventId();
		}

		// comparing
		assertEquals(name, nameList);
	}	
	
	/**
	 * This function tests the ParseFollowRelations() method in TCPlugin class. <br/>
	 * <br/>
	 * parseInitialEvents() method gets the events from EFG.events. Since it is
	 * a private method, the reflection is used to test it. <br/>
	 * <br/>
	 * Testcase1 for ParseFollowRelations(): using m_TCPluginStub
	 */
	public void testParseFollowRelations1() {

		// define the expected result
		String[] preList = { "", "", "" };
		String[] sucList = { "e0e1", "e0e1e2", "e1e2" };

		// using reflection to call the private methods

		// get the actual results;
		String[] pre = new String[3];
		String[] suc = new String[3];

		int index = 0;
		for (EventType event : m_TCPluginStub.initialEvents) {

			Vector<EventType> list = m_TCPluginStub.preds.get(event);
			pre[index] = "";
			if (list != null) {
				for (EventType e : list) {
					pre[index] += e.getEventId();
				}
			}

			suc[index] = "";
			list = m_TCPluginStub.succs.get(event);
			if (list != null) {
				for (EventType e : list) {
					suc[index] += e.getEventId();
				}
			}

			assertEquals(preList[index], pre[index]);
			assertEquals(suc[index], suc[index]);
			index++;
		}
	}

	/**
	 * This function tests the ParseFollowRelations() method in TCPlugin class. <br/>
	 * <br/>
	 * parseInitialEvents() method gets the events from EFG.events. Since it is
	 * a private method, the reflection is used to test it. <br/>
	 * <br/>
	 * Testcase2 for ParseFollowRelations(): using m_TCPluginVoidStub
	 */
	public void testParseFollowRelations2() {

		// get the actual results;
		assertEquals(m_TCPluginVoidStub.preds.size(), 0);
		assertEquals(m_TCPluginVoidStub.succs.size(), 0);
	}	

	/**
	 * This function tests the ParseFollowRelations() method in TCPlugin class. <br/>
	 * <br/>
	 * parseInitialEvents() method gets the events from EFG.events. Since it is
	 * a private method, the reflection is used to test it. <br/>
	 * <br/>
	 * Testcase3 for ParseFollowRelations(): using m_TCPlugin5EventsStub
	 */
	public void testParseFollowRelations3() {

		// define the expected result
		String[] preList = { "", "e0", "e0", "e2" };
		String[] sucList = { "e0e1e2", "e0e1", "e0e2e3","e3e4" };

		// get the actual results;
		String[] pre = new String[4];
		String[] suc = new String[4];

		int index = 0;
		for (EventType event : m_TCPlugin5EventsStub.initialEvents) {
			Vector<EventType> list = m_TCPlugin5EventsStub.preds.get(event);
			pre[index] = "";
			if (list != null) {
				for (EventType e : list) {
					pre[index] += e.getEventId();
				}
			}
			System.out.print(" ");
			suc[index] = "";
			list = m_TCPlugin5EventsStub.succs.get(event);
			if (list != null) {
				for (EventType e : list) {
					suc[index] += e.getEventId();
				}
			}

			assertEquals(preList[index], pre[index]);
			assertEquals(suc[index], suc[index]);
			index++;
		}
	}	
	
	/**
	 * This function tests the Initialize method in TCPlugin class. <br/>
	 * <br/>
	 * Initialize() method initializes three variables: initialEvents, preds,
	 * and succs According to the given EFG file, the expected result is
	 * generated in the form of string arrays. Then assertEquals is used to
	 * compare the results. <br/>
	 * <br/>
	 * Testcase 1 for Initialize(): using m_TCPluginStub <br/>
	 * events: e0, e1, e2 <br/>
	 * eventGraph: <br/>
	 * 1 1 0 <br/>
	 * 1 1 1 <br/>
	 * 0 1 1
	 */
	
	public void testInitialize1() {

		String[] nameList = { "e0", "e1", "e2" };
		String[] preList = { "", "", "" };
		String[] sucList = { "e0e1", "e0e1e2", "e1e2" };

		String[] name = new String[3];
		String[] pre = new String[3];
		String[] suc = new String[3];

		// Called in setups
		m_TCPluginStub.initialize();

		int index = 0;
		for (EventType event : m_TCPluginStub.initialEvents) {

			name[index] = event.getEventId();

			Vector<EventType> list = m_TCPluginStub.preds.get(event);
			pre[index] = "";
			if (list != null) {
				for (EventType e : list) {
					System.out.print(e.getEventId());
					pre[index] += e.getEventId();
				}
			}

			suc[index] = "";
			list = m_TCPluginStub.succs.get(event);
			if (list != null) {
				for (EventType e : list) {
					suc[index] += e.getEventId();
				}
			}
			System.out.println();

			assertEquals(nameList[index], name[index]);
			assertEquals(preList[index], pre[index]);
			assertEquals(suc[index], suc[index]);
			index++;
		}

	}

	/**
	 * This function tests the Initialize method in TCPlugin class. <br/>
	 * <br/>
	 * Initialize() method initializes three variables: initialEvents, preds,
	 * and succs According to the given EFG file, the expected result is
	 * generated in the form of string arrays. Then assertEquals is used to
	 * compare the results. <br/>
	 * <br/>
	 * Testcase 2 for Initialize(): using m_TCPluginVoidStub. 
	 * Events and eventGraph are null.
	 */
	public void testInitialize2() {
		// called in setUp()
		this.m_TCPluginVoidStub.initialize();

		assertEquals(m_TCPluginVoidStub.initialEvents.size(), 0);
		assertEquals(m_TCPluginVoidStub.preds.size(), 0);
		assertEquals(m_TCPluginVoidStub.succs.size(), 0);
	}

	/**
	 * This function tests the Initialize method in TCPlugin class. <br/>
	 * <br/>
	 * Initialize() method initializes three variables: initialEvents, preds,
	 * and succs According to the given EFG file, the expected result is
	 * generated in the form of string arrays. Then assertEquals is used to
	 * compare the results. <br/>
	 * <br/>
	 * Testcase 3 for Initialize(): using m_TCPlugin5EventsStub <br/>
	 * events: e0, e1, e2, e3, e4(the last one is set to fault) <br/>
	 * eventGraph: <br/>
	 * 1 2 2 0 0 <br/>
	 * 1 1 0 0 0 <br/>
	 * 1 0 1 2 0 <br/>
	 * 0 0 0 1 2 <br/>
	 * 1 0 0 0 1 <br/>
	 */
	public void testInitialize3() {

		String[] nameList = { "e0", "e1", "e2", "e3" };
		String[] preList = { "", "e0", "e0", "e2" };
		String[] sucList = { "e0e1e2", "e0e1", "e0e2e3","e3e4" };

		String[] name = new String[4];
		String[] pre = new String[4];
		String[] suc = new String[4];

		// Called in setups
		m_TCPlugin5EventsStub.initialize();

		int index = 0;
		for (EventType event : m_TCPlugin5EventsStub.initialEvents) {

			name[index] = event.getEventId();

			Vector<EventType> list = m_TCPlugin5EventsStub.preds.get(event);
			pre[index] = "";
			if (list != null) {
				for (EventType e : list) {
					System.out.print(e.getEventId());
					pre[index] += e.getEventId();
				}
			}

			suc[index] = "";
			list = m_TCPlugin5EventsStub.succs.get(event);
			if (list != null) {
				for (EventType e : list) {
					suc[index] += e.getEventId();
				}
			}
			System.out.println();

			assertEquals(nameList[index], name[index]);
			assertEquals(preList[index], pre[index]);
			assertEquals(suc[index], suc[index]);
			index++;
		}

	}
	
	/**
	 * This function accesses the private method DFS() in TCPlugin class. <br/>
	 * <br/>
	 * DFS() is a private method with two inputs and a boolean return. The
	 * reflection is used to test it. The inputs are defined in the Class array,
	 * and the invoked method return an object. Then the object can be changed
	 * to boolean.
	 */
	private boolean accessDFS(TCPlugin object, EventType event,
			LinkedList<EventType> path) {
		// using reflection to call the private methods
		try {
			Class[] inputs = new Class[2];
			inputs[0] = EventType.class;
			inputs[1] = LinkedList.class;

			Method m = TCPlugin.class.getDeclaredMethod("DFS", inputs);
			m.setAccessible(true);
			Object obj = m.invoke(object, event, path);
			boolean res = (Boolean) obj;
			return res;
		} catch (NoSuchMethodException e) {
			System.out.println("No such method is found");
		} catch (IllegalAccessException e) {
			System.out.println("Access is not allowed");
		} catch (InvocationTargetException e) {
			System.out.println("Invocation failed");
		}

		return false;
	}

	/**
	 * This function tests the DFS() method in TCPlugin class. <br/>
	 * <br/>
	 * Testcase1 for DFS(): using the m_TCPluginStub
	 */
	public void testDFS1() {

		// using reflection to call the private methods
		this.m_TCPluginStub.initialize();
		LinkedList<EventType> path = new LinkedList<EventType>();
		EventType event = this.m_TCPluginStub.initialEvents.get(0);
		assertTrue(accessDFS(m_TCPluginStub, event, path));
	}

	/**
	 * This function tests the DFS() method in TCPlugin class. <br/>
	 * <br/>
	 * Testcase2 for DFS(): using the m_TCPluginVoidStub
	 */
	public void testDFS2() {
		// using reflection to call the private methods
		this.m_TCPluginVoidStub.initialize();
		LinkedList<EventType> path = new LinkedList<EventType>();
		EventType event = new EventType();
		assertFalse(accessDFS(m_TCPluginVoidStub, event, path));
	}	

	/**
	 * This function tests the DFS() method in TCPlugin class. <br/>
	 * <br/>
	 * Testcase3 for DFS(): using the m_TCPlugin5EventsStub
	 */
	public void testDFS3() {

		// using reflection to call the private methods
		this.m_TCPlugin5EventsStub.initialize();
		LinkedList<EventType> path = new LinkedList<EventType>();
		EventType event = this.m_TCPlugin5EventsStub.initialEvents.get(0);
		assertTrue(accessDFS(m_TCPlugin5EventsStub, event, path));
	}
	
	/**
	 * This function tests the getPathToRoot method in TCPlugin class. <br/>
	 * <br/>
	 * GetPathToRoot method gets path to root of an event, and returns null if
	 * the event is unreachable. <br/>
	 * <br/>
	 * Testcase 1 for getPathToRoot(): using m_TCPluginStub.
	 */
	public void testGetPathToRoot1() {
		EventType event = new EventType();
		this.m_TCPluginStub.initialize();
		assertEquals(this.m_TCPluginStub.getPathToRoot(event), null);
	}

	/**
	 * This function tests the getPathToRoot method in TCPlugin class. <br/>
	 * <br/>
	 * GetPathToRoot method gets path to root of an event, and returns null if
	 * the event is unreachable. <br/>
	 * <br/>
	 * Testcase 2 for getPathToRoot(): using m_TCPluginStub.
	 */
	public void testGetPathToRoot2() {
		this.m_TCPluginStub.initialize();
		EventType event = new EventType();
		Vector<EventType> tmp = new Vector<EventType>();
		m_TCPluginStub.preds.put(event, tmp);
		assertEquals(this.m_TCPluginStub.getPathToRoot(event), null);
	}
	
	/**
	 * This function tests the getPathToRoot method in TCPlugin class. <br/>
	 * <br/>
	 * GetPathToRoot method gets path to root of an event, and returns null if
	 * the event is unreachable. <br/>
	 * <br/>
	 * Testcase 3 for getPathToRoot(): using m_TCPluginStub.
	 */
	public void testGetPathToRoot3() {
		EventType event = new EventType();
		Vector<EventType> tmp = new Vector<EventType>();
		tmp.add(event);
		tmp.add(event);
		m_TCPluginStub.preds.put(event, tmp);
		try
		{
			m_TCPluginStub.getPathToRoot(event);
			fail("Did not throw Stack Overflow in cycle!");
		}
		catch (StackOverflowError soe)
		{
			assertTrue(true);
		}
	}

	/**
	 * This function tests the getPathToRoot method in TCPlugin class. <br/>
	 * <br/>
	 * GetPathToRoot method gets path to root of an event, and returns null if
	 * the event is unreachable. <br/>
	 * <br/>
	 * Testcase 4 for getPathToRoot(): using m_TCPluginVoidStub.
	 */
	public void testGetPathToRoot4() {
		this.m_TCPluginVoidStub.initialize();
		EventType event = new EventType();
		assertEquals(this.m_TCPluginVoidStub.getPathToRoot(event), null);
	}
	
	/**
	 * This function tests the getPathToRoot method in TCPlugin class. <br/>
	 * <br/>
	 * GetPathToRoot method gets path to root of an event, and returns null if
	 * the event is unreachable. <br/>
	 * <br/>
	 * Testcase 5 for getPathToRoot(): using m_TCPlugin5EventsStub.
	 */
	public void testGetPathToRoot5() {
		EventType event = new EventType();
		this.m_TCPlugin5EventsStub.initialize();
		assertEquals(this.m_TCPlugin5EventsStub.getPathToRoot(event), null);
	}

	/* tests if the event argument is the root */	
	public void testGetPathToRoot6() {
		this.m_TCPluginStub.initialize();
		EventType event = new EventType();
		m_TCPluginStub.initialEvents.add(event);

		LinkedList<EventType> tmp = new LinkedList<EventType>();
		tmp.add(event);
		assertEquals(this.m_TCPluginStub.getPathToRoot(event), tmp);
	}
	
	/**
	 * This function tests the WriteToFile() method in TCPlugin class. <br/>
	 * <br/>
	 * WriteToFile() method writes the test case to file. The two inputs: the
	 * sequence path and name for test case are simulated to call this method.
	 * Then assertTrue is used to check if the file is successfully written.
	 */
	public void testWriteToFile() {
		LinkedList<EventType> path = new LinkedList<EventType>();
		EventType event = new EventType();
		path.add(event);
		path.add(event);
		String tc = "test.txt";
		this.m_TCPluginStub.writeToFile(tc, path);
		File f1 = new File(tc);
		assertTrue(f1.exists());
	}

}
