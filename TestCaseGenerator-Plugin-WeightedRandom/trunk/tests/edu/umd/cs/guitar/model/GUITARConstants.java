package edu.umd.cs.guitar.model;

/**
* Mock object.  Returns constant value constitient with the test cases.
*/
public interface GUITARConstants {

    /**
     * No follow realationship between 2 events
     */
    public static final int NO_EDGE = 0;

    /**
     * Normal edges
     */
    public static final int FOLLOW_EDGE = 1;
    
    /**
     * The edges used to reach an event  
     */
    public static final int REACHING_EDGE = 2;

}
