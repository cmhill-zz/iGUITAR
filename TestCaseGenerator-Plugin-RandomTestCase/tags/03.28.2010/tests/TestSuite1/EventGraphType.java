package edu.umd.cs.guitar.model.data;

import java.util.ArrayList;

/**
* Mock object.  Returns constant values that are set by test cases.
*/
public class EventGraphType {

    /**
    * Set only in test cases
    */
    public ArrayList<RowType> row;

    /**
    * Accessor used by RandomTestCase
    */
    public ArrayList<RowType> getRow() {
        return this.row;
    }
}
