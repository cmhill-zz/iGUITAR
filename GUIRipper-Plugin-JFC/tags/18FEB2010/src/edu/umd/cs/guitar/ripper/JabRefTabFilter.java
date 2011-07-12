/*	
 *  Copyright (c) 2009-@year@. The GUITAR group at the University of Maryland. Names of owners of this group may
 *  be obtained by sending an e-mail to atif@cs.umd.edu
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 *  documentation files (the "Software"), to deal in the Software without restriction, including without 
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *	the Software, and to permit persons to whom the Software is furnished to do so, subject to the following 
 *	conditions:
 * 
 *	The above copyright notice and this permission notice shall be included in all copies or substantial 
 *	portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 *	LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 *	EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 *	IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 *	THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package edu.umd.cs.guitar.ripper;

import java.awt.Component;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleSelection;
import javax.swing.JList;

//import net.sf.jabref.GeneralTab;
//import net.sf.jabref.PrefsTab;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.JFCXComponent;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.ripper.GComponentFilter;
import edu.umd.cs.guitar.util.Debugger;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class JabRefTabFilter extends GComponentFilter {

    static GComponentFilter cmFilter = null;

    public synchronized static GComponentFilter getInstance() {
        if (cmFilter == null) {
            cmFilter = new JabRefTabFilter();
        }
        return cmFilter;
    }

    private JabRefTabFilter() {
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.umd.cs.guitar.ripper.GComponentFilter#isProcess(edu.umd.cs.guitar
     * .model.GComponent, edu.umd.cs.guitar.model.GWindow)
     */
    @Override
    public boolean isProcess(GComponent component, GWindow window) {
        JFCXComponent jfcComponent = (JFCXComponent) component;
        Accessible aComponent = jfcComponent.getAComponent();
        // if(aComponent instanceof PrefsTab)
        // if(aComponent instanceof GeneralTab)
        // return true;
        return false;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.umd.cs.guitar.ripper.GComponentFilter#ripComponent(edu.umd.cs.guitar
     * .model.GComponent, edu.umd.cs.guitar.model.GWindow)
     */
    @Override
    public ComponentType ripComponent(GComponent component, GWindow window) {
        JFCXComponent jfcComponent = (JFCXComponent) component;

        // JFCSelectFromParent eSelect = new JFCSelectFromParent();
        // eSelect.actionPerform(component);

        Accessible aComponent = jfcComponent.getAComponent();
        // int i = ((Component)aComponent).get
        // System.err.println("Index in parent:" + i);

        Accessible aParent = aComponent.getAccessibleContext()
                .getAccessibleParent();

        Accessible aGrandparent = aParent.getAccessibleContext()
                .getAccessibleParent();
        Accessible aUncle = aGrandparent.getAccessibleContext()
                .getAccessibleChild(0);
        Accessible aCousin = aUncle.getAccessibleContext()
                .getAccessibleChild(0);
        Accessible aSelectPanel = aCousin.getAccessibleContext()
                .getAccessibleChild(0);

        JList jList = (JList) aSelectPanel;
        // jList.setSelectedIndex(i);

        // Debugger.printlnBeanMethodSupported(aSelectPanel);

        // cParent.get

        // System.err.println("Accessible property");
        // Debugger.printlnBeanMethodSupported(aParent.getAccessibleContext());
     
        Debugger.pause("Press Enter to continue...");
        return ripper.ripComponent(component, window);
    }

}
