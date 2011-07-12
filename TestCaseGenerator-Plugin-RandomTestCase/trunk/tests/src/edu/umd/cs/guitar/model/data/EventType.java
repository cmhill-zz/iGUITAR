
package edu.umd.cs.guitar.model.data;

/**
* Mock object.  Returns constant values that are set by test cases.
*/
public class EventType {

    /**
    * Set only in test cases
    */
    public boolean initial;

    /**
    * Accessor used by RandomTestCase
    */
    public boolean isInitial() {
        return initial;
    }

}
