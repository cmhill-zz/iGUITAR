
package edu.umd.cs.guitar.model;

import java.util.List;

public class IceDefaultIDGenerator extends DefaultIDGenerator {

    private static IceDefaultIDGenerator instance = null;

    public static IceDefaultIDGenerator getInstance() {
        if (instance == null)
            instance = new IceDefaultIDGenerator();
        return instance;
    }

    private IceDefaultIDGenerator() {
        super(IceConstants.getIdProperties());
    }
}
