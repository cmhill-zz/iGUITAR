package edu.umd.cs.guitar.model.data;

/**
* Mock object.  Returns constant values that are set by test cases.
*/
public class EFG {

    /**
    * Set only in test cases
    */
    public EventsType events;

    /**
    * Set only in test cases
    */
    public EventGraphType eventGraph;

    /**
    * Accessor used by RandomTestCase
    */
    public EventsType getEvents() {
        return events;
    }

    /**
    * Accessor used by RandomTestCase
    */
    public EventGraphType getEventGraph() {
        return eventGraph;
    }

}
