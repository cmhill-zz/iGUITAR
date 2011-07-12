package edu.umd.cs.guitar.model.data;

import java.util.ArrayList;

/**
* Mock object.  Returns constant values that are set by test cases.
*/
public class RowType {

    /**
    * Set only in test cases
    */
    public ArrayList<Integer> e;

    /**
    * Accessor used by RandomTestCase
    */
    public ArrayList<Integer> getE() {
        return this.e;
    }
}
