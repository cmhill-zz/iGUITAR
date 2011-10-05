/*  
 *  Copyright (c) 2009-@year@. The GUITAR group at the University of Maryland. Names of owners of this group may
 *  be obtained by sending an e-mail to atif@cs.umd.edu
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 *  documentation files (the "Software"), to deal in the Software without restriction, including without 
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *  the Software, and to permit persons to whom the Software is furnished to do so, subject to the following 
 *  conditions:
 * 
 *  The above copyright notice and this permission notice shall be included in all copies or substantial 
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 *  LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 *  EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 *  THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package edu.umd.cs.guitar.model.wrapper;

import edu.umd.cs.guitar.model.data.WidgetMapElementType;
import edu.umd.cs.guitar.model.data.WidgetMapType;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao N. Nguyen </a>
 */
public class WidgetMapWrapper extends WidgetMapType
{

    public WidgetMapWrapper(WidgetMapType wm)
    {
        this.widgetMapElement = wm.getWidgetMapElement();
    }

    /**
     * Get widget map element using id
     * <p>
     * 
     * @param id
     * @return
     */
    WidgetMapElementType getWidgetMap(String id)
    {
        for (WidgetMapElementType  wm: widgetMapElement)
        {
            if (id.equals(wm.getWidgetId()))
                return wm;
        }
        return null;

    }
}
