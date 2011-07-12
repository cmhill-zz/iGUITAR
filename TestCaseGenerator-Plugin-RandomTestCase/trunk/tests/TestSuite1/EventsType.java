package edu.umd.cs.guitar.model.data;

import java.util.ArrayList;

/**
* Mock object.  Returns constant values that are set by test cases.
*/
public class EventsType {

    /**
    * Set only in test cases
    */
    public ArrayList<EventType> event;

    /**
    * Accessor used by RandomTestCase
    */
    public ArrayList<EventType> getEvent() {
        return this.event;
    }
}
