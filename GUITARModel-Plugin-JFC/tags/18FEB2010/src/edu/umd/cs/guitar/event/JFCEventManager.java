/**
 * 
 */
package edu.umd.cs.guitar.event;

import java.util.HashMap;

/**
 * Manage all events supported by GUITAR  
 * <p>
 * @author Bao Nguyen
 *
 */
public class JFCEventManager {
    
    static HashMap<String, Class<?>> mEventMapper;
    static {
        mEventMapper.put("click", JFCActionHandler.class);
    }
}
