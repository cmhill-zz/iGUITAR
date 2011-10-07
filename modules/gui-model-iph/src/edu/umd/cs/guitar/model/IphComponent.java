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
package edu.umd.cs.guitar.model;

import java.util.ArrayList;
import java.util.List;
import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.ContentsType;
import edu.umd.cs.guitar.model.data.PropertyType;
/**
 * Implementation for {@link GWindow} for Java Swing
 * 
 * @see GWindow
 * 
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Hua He </a>
 */
public class IphComponent extends GComponent {

	ComponentType componentType = null;
	ContentsType contentsType = null;
	IphComponent parent = null;
	
	public IphComponent(GWindow window) {
		super(window);
		componentType = ((IphWindow) window).guiType.getContainer();
		contentsType = ((IphWindow) window).guiType.getContainer().getContents();
	}

	public IphComponent(ComponentType childComp, IphComponent parent) {
		super(parent.window);
		
		this.componentType = childComp;
		
		if (childComp instanceof ContainerType) {
			this.contentsType = ((ContainerType) childComp).getContents();
		}
		this.parent = parent;
	}
	
	@Override
	public String getTitle() {
		return componentType.getAttributes().getProperty().get(4).getValue().get(0);
	}

	@Override
	public int getX() {
		return new Integer(componentType.getAttributes().getProperty().get(0).getValue().get(0));
	}
	
	@Override
	public int getY() {
		return new Integer(componentType.getAttributes().getProperty().get(1).getValue().get(0));
	}
	
	private int getWidth() {
		return new Integer(componentType.getAttributes().getProperty().get(2).getValue().get(0));
	}
	
	private int getHeight() {
		return new Integer(componentType.getAttributes().getProperty().get(3).getValue().get(0));
	}
	
	private String getClassName() {
		return componentType.getAttributes().getProperty().get(4).getValue().get(0);
	}

	@Override
	public String getClassVal() {
		// TODO Auto-generated method stub
		return componentType.getAttributes().getProperty().get(4).getValue().get(0);
	}

	@Override
	public List<PropertyType> getGUIProperties() {
		// TODO Auto-generated method stub
		return componentType.getAttributes().getProperty();
	}

	@Override
	public List<GEvent> getEventList() {
		// TODO Auto-generated method stub
		//return null;
		return new ArrayList<GEvent>();
	}

	@Override
	public List<GComponent> getChildren() {
		// TODO Auto-generated method stub
		List<GComponent> iphChildren = new ArrayList<GComponent>();
		
		if (this.componentType instanceof ContainerType) {
			if (((ContainerType) componentType).getContents() == null) {
				
			} else {
				for (ComponentType child : ((ContainerType) componentType).getContents().getWidgetOrContainer()) {
					iphChildren.add(new IphComponent(child, this));
				}
			}
		} else {
		
			/*for (ComponentType child : ) {
				//iphChildren.add(new IphComponent(child, this));
			}*/
		}
		
		return iphChildren;
		//return null;
	}

	@Override
	public GComponent getParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	@Override
	public String getTypeVal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren() {
		// TODO Auto-generated method stub
		if ( this.contentsType != null && this.contentsType.getWidgetOrContainer().size() > 0)
			return true;
		
		return false;
	}

	@Override
	public boolean isTerminal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnable() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean isClickable() {
		// Rongjian Lan: I need this API, please implement it
		return false;
	}

	

}
