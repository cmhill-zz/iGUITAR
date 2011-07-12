package edu.umd.cs.guitar.model;

import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.data.EFG;
import edu.umd.cs.guitar.model.data.EventGraphType;
import edu.umd.cs.guitar.model.data.EventType;
import edu.umd.cs.guitar.model.data.EventsType;
import edu.umd.cs.guitar.model.data.RowType;

/**
 * Helper class to the mock IO implementation; returns a set different objects
 * that normal calls to readObjFromFile would return, allows for controlled
 * access to objects in memory if the need arises when developing test oracles.
 *
 * @author Sam Huang
 */
public class IOMockFactory {

  /**
   * Field for holding a singleton of the SingleEventEFG.
   */
  public static EFG m_singleEvent;

  /**
   * Field for holding a singleton of the TwoCycleEFG..
   */
  public static EFG m_twoCycle;

  /**
   * Field for holding a singleton of the ReachingEFG..
   */
  public static EFG m_reaching;

  /**
   * Field for holding a singleton of the ThreeEventEFG.
   */
  public static EFG m_threeEvent;

  /**
   * Field for holding a singleton of the DisjointEFG.
   */
  public static EFG m_disjoint;

  /**
   * Field for holding a singleton of the finiteEFG.
   */
  public static EFG m_finite;

  /**
   * Returns a toy example of an EFG with one node looping back to itself,
   * marked initial.  The test cases generated from it are straight forward.
   */
  public static EFG getSingleEventEFG() {
    if(m_singleEvent == null) {
      m_singleEvent = new EFG();
      m_singleEvent.setEvents(new EventsType());

      EventType e = new EventType();
      e.setInitial(true);
      e.setEventId("e0");
      e.setWidgetId("w0");
      m_singleEvent.getEvents().getEvent().add(e);

      m_singleEvent.setEventGraph(new EventGraphType());

      m_singleEvent.getEventGraph().getRow().add(new RowType());
      m_singleEvent.getEventGraph().getRow().get(0).getE().add(GUITARConstants.FOLLOW_EDGE);
    }

    return m_singleEvent;
  }

  /**
   * Returns a toy example of an EFG with two nodes forming a cycle, with only
   * one node marked as initial.  This allows for deterministic test
   * generation, even with generation algorithms involving randomness (given a
   * length l, only one valid test case exists of length l).
   */
  public static EFG getTwoCycleEFG() {
    if(m_twoCycle == null) {
      m_twoCycle = new EFG();
      m_twoCycle.setEvents(new EventsType());

      EventType e = new EventType();
      e.setInitial(true);
      e.setEventId("e0");
      e.setWidgetId("w0");
      m_twoCycle.getEvents().getEvent().add(e);

      e = new EventType();
      e.setInitial(false);
      e.setEventId("e1");
      e.setWidgetId("w1");
      m_twoCycle.getEvents().getEvent().add(e);

      m_twoCycle.setEventGraph(new EventGraphType());

      m_twoCycle.getEventGraph().getRow().add(new RowType());
      m_twoCycle.getEventGraph().getRow().get(0).getE().add(GUITARConstants.NO_EDGE);
      m_twoCycle.getEventGraph().getRow().get(0).getE().add(GUITARConstants.FOLLOW_EDGE);

      m_twoCycle.getEventGraph().getRow().add(new RowType());
      m_twoCycle.getEventGraph().getRow().get(1).getE().add(GUITARConstants.FOLLOW_EDGE);
      m_twoCycle.getEventGraph().getRow().get(1).getE().add(GUITARConstants.NO_EDGE);
    }

    return m_twoCycle;
  }

  /**
   * Returns a toy example of an EFG with two nodes connected to each other and
   * themselves, with all "reaching" edges, designed to test the code handling
   * the REACHING_EDGE type.
   */
  public static EFG getReachingEFG() {
    if(m_reaching == null) {
      m_reaching = new EFG();
      m_reaching.setEvents(new EventsType());

      EventType e = new EventType();
      e.setInitial(true);
      e.setEventId("e0");
      e.setWidgetId("w0");
      m_reaching.getEvents().getEvent().add(e);

      e = new EventType();
      e.setInitial(false);
      e.setEventId("e1");
      e.setWidgetId("w1");
      m_reaching.getEvents().getEvent().add(e);

      m_reaching.setEventGraph(new EventGraphType());

      m_reaching.getEventGraph().getRow().add(new RowType());
      m_reaching.getEventGraph().getRow().get(0).getE().add(GUITARConstants.REACHING_EDGE);
      m_reaching.getEventGraph().getRow().get(0).getE().add(GUITARConstants.REACHING_EDGE);

      m_reaching.getEventGraph().getRow().add(new RowType());
      m_reaching.getEventGraph().getRow().get(1).getE().add(GUITARConstants.REACHING_EDGE);
      m_reaching.getEventGraph().getRow().get(1).getE().add(GUITARConstants.REACHING_EDGE);
    }

    return m_reaching;
  }

  /**
   * Returns a toy example of an EFG with three nodes connected by reaching edges in succession,
   * with each node also connecting to itself. Designed to test code concerning the predecessor
   * and successor natures of nodes.
   */
  public static EFG getThreeEventEFG() {
    if(m_threeEvent == null) {
      m_threeEvent = new EFG();
      m_threeEvent.setEvents(new EventsType());

      EventType e = new EventType();
      e.setInitial(false);
      e.setEventId("e0");
      e.setWidgetId("w0");
      m_threeEvent.getEvents().getEvent().add(e);

      e = new EventType();
      e.setInitial(true);
      e.setEventId("e1");
      e.setWidgetId("w1");
      m_threeEvent.getEvents().getEvent().add(e);

      e = new EventType();
      e.setInitial(true);
      e.setEventId("e2");
      e.setWidgetId("w2");
      m_threeEvent.getEvents().getEvent().add(e);

      m_threeEvent.setEventGraph(new EventGraphType());

      m_threeEvent.getEventGraph().getRow().add(new RowType());
      m_threeEvent.getEventGraph().getRow().get(0).getE().add(GUITARConstants.FOLLOW_EDGE);
      m_threeEvent.getEventGraph().getRow().get(0).getE().add(GUITARConstants.REACHING_EDGE);
      m_threeEvent.getEventGraph().getRow().get(0).getE().add(GUITARConstants.NO_EDGE);

      m_threeEvent.getEventGraph().getRow().add(new RowType());
      m_threeEvent.getEventGraph().getRow().get(1).getE().add(GUITARConstants.NO_EDGE);
      m_threeEvent.getEventGraph().getRow().get(1).getE().add(GUITARConstants.FOLLOW_EDGE);
      m_threeEvent.getEventGraph().getRow().get(1).getE().add(GUITARConstants.NO_EDGE);

      m_threeEvent.getEventGraph().getRow().add(new RowType());
      m_threeEvent.getEventGraph().getRow().get(2).getE().add(GUITARConstants.REACHING_EDGE);
      m_threeEvent.getEventGraph().getRow().get(2).getE().add(GUITARConstants.REACHING_EDGE);
      m_threeEvent.getEventGraph().getRow().get(2).getE().add(GUITARConstants.FOLLOW_EDGE);
    }
    
    return m_threeEvent;
  }

  /**
   * Returns a toy example of a disjoint EFG. Designed to test code where an event has no path to the root.
   */
  public static EFG getDisjointEFG() {
    if(m_disjoint == null) {
      m_disjoint = new EFG();
      m_disjoint.setEvents(new EventsType());

      EventType e = new EventType();
      e.setInitial(true);
      e.setEventId("e0");
      e.setWidgetId("w0");
      m_disjoint.getEvents().getEvent().add(e);

      e = new EventType();
      e.setInitial(false);
      e.setEventId("e1");
      e.setWidgetId("w1");
      m_disjoint.getEvents().getEvent().add(e);

      e = new EventType();
      e.setInitial(true);
      e.setEventId("e2");
      e.setWidgetId("w1");
      m_disjoint.getEvents().getEvent().add(e);
      
      m_disjoint.setEventGraph(new EventGraphType());

      m_disjoint.getEventGraph().getRow().add(new RowType());
      m_disjoint.getEventGraph().getRow().get(0).getE().add(GUITARConstants.FOLLOW_EDGE);
      m_disjoint.getEventGraph().getRow().get(0).getE().add(GUITARConstants.NO_EDGE);
      m_disjoint.getEventGraph().getRow().get(0).getE().add(GUITARConstants.NO_EDGE);

      m_disjoint.getEventGraph().getRow().add(new RowType());
      m_disjoint.getEventGraph().getRow().get(1).getE().add(GUITARConstants.NO_EDGE);
      m_disjoint.getEventGraph().getRow().get(1).getE().add(GUITARConstants.FOLLOW_EDGE);
      m_disjoint.getEventGraph().getRow().get(1).getE().add(GUITARConstants.REACHING_EDGE);

      m_disjoint.getEventGraph().getRow().add(new RowType());
      m_disjoint.getEventGraph().getRow().get(2).getE().add(GUITARConstants.NO_EDGE);
      m_disjoint.getEventGraph().getRow().get(2).getE().add(GUITARConstants.FOLLOW_EDGE);
      m_disjoint.getEventGraph().getRow().get(2).getE().add(GUITARConstants.FOLLOW_EDGE);
    }
    
    return m_disjoint;
  }

  /**
   * Returns a toy example of an EFG which only has finite length paths.
   */
  public static EFG getFiniteEFG() {
    if(m_finite == null) {
      m_finite = new EFG();
      m_finite.setEvents(new EventsType());

      EventType e = new EventType();
      e.setInitial(true);
      e.setEventId("e0");
      e.setWidgetId("w0");
      m_finite.getEvents().getEvent().add(e);

      e = new EventType();
      e.setInitial(true);
      e.setEventId("e1");
      e.setWidgetId("w1");
      m_finite.getEvents().getEvent().add(e);

      e = new EventType();
      e.setInitial(true);
      e.setEventId("e2");
      e.setWidgetId("w1");
      m_finite.getEvents().getEvent().add(e);
      
      m_finite.setEventGraph(new EventGraphType());

      m_finite.getEventGraph().getRow().add(new RowType());
      m_finite.getEventGraph().getRow().get(0).getE().add(GUITARConstants.NO_EDGE);
      m_finite.getEventGraph().getRow().get(0).getE().add(GUITARConstants.FOLLOW_EDGE);
      m_finite.getEventGraph().getRow().get(0).getE().add(GUITARConstants.FOLLOW_EDGE);

      m_finite.getEventGraph().getRow().add(new RowType());
      m_finite.getEventGraph().getRow().get(1).getE().add(GUITARConstants.NO_EDGE);
      m_finite.getEventGraph().getRow().get(1).getE().add(GUITARConstants.NO_EDGE);
      m_finite.getEventGraph().getRow().get(1).getE().add(GUITARConstants.FOLLOW_EDGE);

      m_finite.getEventGraph().getRow().add(new RowType());
      m_finite.getEventGraph().getRow().get(2).getE().add(GUITARConstants.NO_EDGE);
      m_finite.getEventGraph().getRow().get(2).getE().add(GUITARConstants.NO_EDGE);
      m_finite.getEventGraph().getRow().get(2).getE().add(GUITARConstants.NO_EDGE);
    }
    
    return m_finite;
  }
}
