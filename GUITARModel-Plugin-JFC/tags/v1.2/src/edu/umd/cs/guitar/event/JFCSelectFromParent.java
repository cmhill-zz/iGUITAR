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
package edu.umd.cs.guitar.event;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.JFCXComponent;

/**
 * Select a sub-item by calling a selection function form its parent
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class JFCSelectFromParent extends JFCEventHandler {

    /**
     * 
     */
    public JFCSelectFromParent() {
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.umd.cs.guitar.event.ThreadEventHandler#actionPerformImp(edu.umd.cs
     * .guitar.model.GXComponent)
     */
    @Override
    protected void performImpl(GComponent gComponent) {
        Accessible aChild = ((JFCXComponent) gComponent).getAComponent();
        Component cChild = (Component) aChild;

        // Find the closet parent which is support selection
        Accessible aParent = getSelectableParent(aChild);

        if (aParent != null) {
            Method selectionMethod;

            try {
                selectionMethod = aParent.getClass().getMethod(
                        "setSelectedComponent", Component.class);
                selectionMethod.invoke(aParent, cChild);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 
     * A helper method to find the closest ancestor having setSelectedComponent method
     * Presumably this method will select the current element. 
     * 
     * <p>
     *  
     * @param aComponent
     * @return Accessible
     */
    private Accessible getSelectableParent(Accessible aComponent) {
        
        if(aComponent==null)
            return null;
        
        AccessibleContext aContext = aComponent.getAccessibleContext();

        if (aContext == null)
            return null;

        Accessible aParent = aContext.getAccessibleParent();
        if (aParent == null)
            return null;
        
        Method[] methods = aParent.getClass().getMethods();
        for(Method m: methods){
            if("setSelectedComponent".equals(m.getName()))
                return aParent;
        }
        
        return getSelectableParent(aParent);
        
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.umd.cs.guitar.event.ThreadEventHandler#actionPerformImp(edu.umd.cs
     * .guitar.model.GXComponent, java.lang.Object)
     */
    @Override
    protected void performImpl(GComponent gComponent, Object parameters) {
        performImpl(gComponent);
    }

}
