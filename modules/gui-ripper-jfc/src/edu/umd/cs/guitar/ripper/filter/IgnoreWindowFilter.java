/**
 * 
 */
package edu.umd.cs.guitar.ripper.filter;

import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.ripper.GWindowFilter;

/**
 * @author Bao Nguyen
 *
 */
public class IgnoreWindowFilter extends GWindowFilter {

    /**
     * 
     */
    public IgnoreWindowFilter() {
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see edu.umd.cs.guitar.ripper.GWindowFilter#isProcess(edu.umd.cs.guitar.model.GWindow)
     */
    @Override
    public boolean isProcess(GWindow window) {
        String ID = window.getTitle();
        return false;
    }

    /* (non-Javadoc)
     * @see edu.umd.cs.guitar.ripper.GWindowFilter#ripWindow(edu.umd.cs.guitar.model.GWindow)
     */
    @Override
    public GUIType ripWindow(GWindow window) {
        return null;
    }

}
