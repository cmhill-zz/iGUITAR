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

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleAction;
import javax.accessibility.AccessibleContext;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import edu.umd.cs.guitar.event.EventManager;
import edu.umd.cs.guitar.event.GEvent;
import edu.umd.cs.guitar.event.JFCActionHandler;
import edu.umd.cs.guitar.event.JFCEditableTextHandler;
import edu.umd.cs.guitar.event.JFCSelectionHandler;
import edu.umd.cs.guitar.event.JFCValueHandler;
import edu.umd.cs.guitar.model.data.PropertyType;
import edu.umd.cs.guitar.model.wrapper.AttributesTypeWrapper;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * Implementation for {@link GWindow} for Java Swing
 * 
 * @see GWindow
 * 
 * 
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class JFCXComponent extends GComponent {

	Component component;

	// Accessible aComponent;

	// /**
	// * @return the aComponent
	// */
	// public Accessible getAComponent() {
	// return aComponent;
	// }

	// /**
	// * @param aComponent
	// */
	// public JFCXComponent(Accessible aComponent) {
	// super();
	// this.aComponent = aComponent;
	// this.component = (Component) aComponent;
	// }

	/**
	 * @param component
	 */
	public JFCXComponent(Component component, GWindow window) {

		super(window);

		this.component = component;
		// this.aComponent = (Accessible) component;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.model.GComponent#getGUIProperties()
	 */
	@Override
	public List<PropertyType> getGUIProperties() {
		List<PropertyType> retList = new ArrayList<PropertyType>();
		// Other properties

		PropertyType p;
		List<String> lPropertyValue;
		String sValue;

		// Title
		sValue = null;
		sValue = getTitle();
		if (sValue != null) {
			p = factory.createPropertyType();
			p.setName(JFCConstants.TITLE_TAG);
			lPropertyValue = new ArrayList<String>();
			lPropertyValue.add(sValue);
			p.setValue(lPropertyValue);
			retList.add(p);
		}

		// Icon
		sValue = null;
		sValue = getIconName();
		if (sValue != null) {
			p = factory.createPropertyType();
			p.setName(JFCConstants.ICON_TAG);
			lPropertyValue = new ArrayList<String>();
			lPropertyValue.add(sValue);
			p.setValue(lPropertyValue);
			retList.add(p);
		}

		// Index in parent
		if (isSelectedByParent()) {

			// Debugger.pause("GOT HERE!!!");

			sValue = null;
			sValue = getIndexInParent().toString();

			p = factory.createPropertyType();
			p.setName(JFCConstants.INDEX_TAG);
			lPropertyValue = new ArrayList<String>();
			lPropertyValue.add(sValue);
			p.setValue(lPropertyValue);
			retList.add(p);

		}

		// Get bean properties
		List<PropertyType> lBeanProperties = getGUIBeanProperties();
		retList.addAll(lBeanProperties);

		// Get Screenshot
		return retList;

	}

	/**
	 * Get component index in its parent
	 * 
	 * @return
	 */
	private Integer getIndexInParent() {

		// AccessibleContext aContext = aComponent.getAccessibleContext();
		AccessibleContext aContext = component.getAccessibleContext();
		if (aContext != null) {
			return aContext.getAccessibleIndexInParent();
		}

		return 0;
	}

	/**
	 * Check if the component is activated by an action in parent
	 * 
	 * @return
	 */
	private boolean isSelectedByParent() {
		// if (aComponent instanceof Component) {
		Container parent = ((Component) this.component).getParent();

		if (parent == null)
			return false;

		if (parent instanceof JTabbedPane)
			return true;
		// }
		return false;
	}

	/**
	 * Get all bean properties of the component
	 * 
	 * @return
	 */
	private List<PropertyType> getGUIBeanProperties() {
		List<PropertyType> retList = new ArrayList<PropertyType>();
		Method[] methods = component.getClass().getMethods();
		PropertyType p;
		List<String> lPropertyValue;

		for (Method m : methods) {
			if (m.getParameterTypes().length > 0) {
				continue;
			}
			String sMethodName = m.getName();
			String sPropertyName = sMethodName;

			if (sPropertyName.startsWith("get")) {
				sPropertyName = sPropertyName.substring(3);
			} else if (sPropertyName.startsWith("is")) {
				sPropertyName = sPropertyName.substring(2);
			} else
				continue;

			// make sure property is in lower case
			sPropertyName = sPropertyName.toLowerCase();

			if (JFCConstants.GUI_PROPERTIES_LIST.contains(sPropertyName)) {

				Object value;
				try {
					// value = m.invoke(aComponent, new Object[0]);
					value = m.invoke(component, new Object[0]);
					if (value != null) {
						p = factory.createPropertyType();
						lPropertyValue = new ArrayList<String>();
						lPropertyValue.add(value.toString());
						p.setName(sPropertyName);
						p.setValue(lPropertyValue);
						retList.add(p);
					}
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				}
			}
		}
		return retList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.model.GXComponent#getChildren()
	 */
	@Override
	public List<GComponent> getChildren() {

		// GUITARLog.log.debug(component.getClass().getName());

		// GUITARLog.log.debug("ENTERING getChildren...");
		List<GComponent> retList = new ArrayList<GComponent>();

		if (component instanceof Container) {

			Container container = (Container) component;
			//
			// GUITARLog.log.debug("\t Component Chidren: "
			// + container.getComponentCount());

			try {
				int nChildren = 0;

				AccessibleContext aContext = container.getAccessibleContext();

				if (aContext == null)
					return retList;
				nChildren = aContext.getAccessibleChildrenCount();

				// GUITARLog.log.debug("\t Accessible Chidren: " + nChildren);
				// for(int i = 0; i < nChildren; i ++){
				// Accessible childAccessible = aContext.getAccessibleChild(i);
				// GUITARLog.log.debug("\t\t Accessible Name:" +
				// childAccessible.getAccessibleContext().getAccessibleName());
				// GUITARLog.log.debug("\t\t Accessible Class:" +
				// childAccessible.getClass().getName());
				// }

				// Check accessible children first
				if (nChildren > 0) {
					for (int i = 0; i < nChildren; i++) {

						Accessible aChild = aContext.getAccessibleChild(i);

						if (aChild instanceof Component) {
							Component cChild = (Component) aChild;

							GComponent gChild = new JFCXComponent(cChild,
									window);
							retList.add(gChild);
						}
					}

					// Then try to search for component level in addition to
					// accessibility level
				} else {
					nChildren = container.getComponentCount();
					for (int i = 0; i < nChildren; i++) {
						Component cChild = container.getComponent(i);
						GComponent gChild = new JFCXComponent(cChild, window);
						retList.add(gChild);
					}
				}

			} catch (Exception e) {
				GUITARLog.log.error("getChildren");
				GUITARLog.log.error(e);
			}
		}
		// try {
		//
		// if (component instanceof Container) {
		//				
		// System.out.println("GOT HERE:" + component .getClass());
		// AccessibleContext aComp = component.getAccessibleContext();
		//				
		// System.out.println("Accessible children:" +
		// aComp.getAccessibleChildrenCount()) ;
		// System.out.println("Component children:" + ((Container)
		// component).getComponents().length) ;
		//				
		//				
		//				
		// Container comp = (Container) component ;
		// int nChildren = comp.getComponentCount();
		//
		// for (int i = 0; i < nChildren; i++) {
		//
		// Component aChild = comp.getComponent(i);
		// if (aChild instanceof Accessible) {
		//
		// GComponent gChild = new JFCXComponent(
		// aChild);
		// retList.add(gChild);
		// }
		// }
		// } else
		// return retList;
		//
		// } catch (Exception e) {
		// }

		return retList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.model.GXComponent#getParent()
	 */
	@Override
	public GComponent getParent() {
		// AccessibleContext aContext = this.aComponent.getAccessibleContext();
		// if (aContext == null)
		// return null;
		// Accessible jParent = aContext.getAccessibleParent();
		//
		// if (jParent == null)
		// return null;

		Component parent = this.component.getParent();

		return new JFCXComponent(parent, window);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.model.GXComponent#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		// AccessibleContext xContext = aComponent.getAccessibleContext();
		AccessibleContext xContext = component.getAccessibleContext();

		if (xContext == null)
			return false;

		// TODO: Check this
		int nChildren = xContext.getAccessibleChildrenCount();

		if (nChildren > 0)
			return true;

		if (component instanceof Container) {
			Container container = (Container) component;

			if (container.getComponentCount() > 0)
				return true;

		}

		return false;
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see edu.umd.cs.guitar.model.GXObject#getID()
	// */
	// @Override
	// @Deprecated
	// public String getTitle() {
	// String retID = "";
	//
	// String sName = getName();
	// if (sName == null)
	// sName = "null";
	// retID += sName;
	//
	// return retID;
	// }

	/**
	 * @return the component
	 */
	public Component getComponent() {
		return component;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {

		// System.err.println("HASHING: " + getTitle() + ".....");

		final int prime = 31;

		int result = 1;

		List<PropertyType> lProperties = getGUIProperties();
		for (PropertyType property : lProperties) {
			if (JFCConstants.ID_PROPERTIES.contains(property.getName())) {

				String name = property.getName();
				result = prime * result + (name == null ? 0 : name.hashCode());

				List<String> valueList = property.getValue();
				if (valueList != null)
					for (String value : valueList) {
						result = prime * result
								+ (value == null ? 0 : value.hashCode());

					}

			}
		}

		// Class
		result = prime * result + GUITARConstants.CLASS_TAG_NAME.hashCode();
		result = prime * result + getClassVal().hashCode();

		// result = prime * result + GUITARConstants.TITLE_TAG_NAME.hashCode();
		// result = prime * result + getTitle().hashCode();

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JFCXComponent other = (JFCXComponent) obj;
		if (component == null) {
			if (other.component != null)
				return false;
		} else if (!component.equals(other.component))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.model.GXObject#getName()
	 */
	@Override
	public String getTitle() {

		String sName = "";
		if (component == null)
			return "";
		AccessibleContext aContext = component.getAccessibleContext();

		if (aContext == null)
			return "";

		sName = aContext.getAccessibleName();

		if (sName != null)
			return sName;

		if (sName == null)
			sName = getIconName();

		if (sName != null)
			return sName;

		// if (aComponent instanceof Component) {
		// Component comp = (Component) aComponent;
		sName = component.getName();

		// In the worst case we must use the screen position to
		// identify the widget
		if (sName == null) {
			sName = "Pos(" + component.getX() + "," + component.getY() + ")";
		}
		// }
		return sName;
	}

	/**
	 * Parse the icon name of a widget from the resource's absolute path.
	 * 
	 * <p>
	 * 
	 * @param component
	 * @return
	 */
	private String getIconName() {
		String retIcon = null;
		try {
			Class<?> partypes[] = new Class[0];
			Method m = component.getClass().getMethod("getIcon", partypes);

			String sIconPath = null;
			// if (m != null) {
			// Object obj = (m.invoke(aComponent, new Object[0]));
			// if (obj != null)
			// sIconPath = obj.toString();
			// }

			if (m != null) {
				Object obj = (m.invoke(component, new Object[0]));

				if (obj != null) {
					sIconPath = obj.toString();
				}
			}

			if (sIconPath == null || sIconPath.contains("@"))
				return null;

			String[] sIconElements = sIconPath.split(File.separator);
			retIcon = sIconElements[sIconElements.length - 1];

		} catch (SecurityException e) {
			// e.printStackTrace();
			return null;
		} catch (NoSuchMethodException e) {
			// e.printStackTrace();
			return null;
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			// e.printStackTrace();
			return null;
		} catch (InvocationTargetException e) {
			// e.printStackTrace();
			return null;
		}
		return retIcon;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.model.GXComponent#getEventList()
	 */
	@Override
	public List<GEvent> getEventList() {
		List<GEvent> retEvents = new ArrayList<GEvent>();
		EventManager em = EventManager.getInstance();

		for (Class<? extends GEvent> event : em.getEvents()) {
			Constructor<? extends GEvent> constructor;
			try {

				constructor = event.getConstructor(new Class[] {});
				Object obj = constructor.newInstance();
				if (obj instanceof GEvent) {
					GEvent gEvent = (GEvent) obj;
					if (gEvent.isSupportedBy(this))
						retEvents.add(gEvent);
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// AccessibleContext aContext = component.getAccessibleContext();
		//
		// if (aContext == null)
		// return retEvents;
		//
		// Object event;
		//
		// // Text
		// event = aContext.getAccessibleEditableText();
		// if (event != null) {
		// retEvents.add(new JFCEditableTextHandler());
		// return retEvents;
		// }

		// // Action
		// event = aContext.getAccessibleAction();
		// if (event != null) {
		// retEvents.add(new JFCActionHandler());
		// return retEvents;
		// }

		// Selection
		// event = aContext.getAccessibleSelection();
		// if (event != null)
		// retEvents.add(new JFCSelectionHandler());

		// // Value
		// event = aContext.getAccessibleValue();
		// if (event != null)
		// retEvents.add(new JFCValueHandler());

		return retEvents;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.model.GXComponent#getType()
	 */
	@Override
	public String getClassVal() {

		// AccessibleContext aContext = aComponent.getAccessibleContext();
		// String role = aContext.getAccessibleRole().toString();
		// return role;
		return component.getClass().getName();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.model.GXComponent#getTypeVal()
	 */
	@Override
	public String getTypeVal() {

		String retProperty;

		// if (isActivatedByParent())
		// retProperty = GUITARConstants.ACTIVATED_BY_PARENT;

		// else
		if (isTerminal())
			retProperty = GUITARConstants.TERMINAL;
		else
			retProperty = GUITARConstants.SYSTEM_INTERACTION;
		return retProperty;
	}

	/**
	 * Check if this component is a terminal widget
	 * 
	 * <p>
	 * 
	 * @return
	 */
	@Override
	public boolean isTerminal() {

		AccessibleContext aContext = component.getAccessibleContext();

		if (aContext == null)
			return false;

		AccessibleAction aAction = aContext.getAccessibleAction();

		if (aAction == null)
			return false;

		String sName = getTitle();

		List<AttributesTypeWrapper> termSig = JFCConstants.sTerminalWidgetSignature;
		for (AttributesTypeWrapper sign : termSig) {
			String titleVals = sign.getFirstValByName(JFCConstants.TITLE_TAG);

			if (titleVals == null)
				continue;

			if (titleVals.equalsIgnoreCase(sName))
				return true;

		}

		return false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.model.GComponent#isEnable()
	 */
	@Override
	public boolean isEnable() {
		//

		try {
			Class[] types = new Class[] {};
			Method method = component.getClass().getMethod("isEnabled", types);
			Object result = method.invoke(component, new Object[0]);

			if (result instanceof Boolean)
				return (Boolean) result;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Check if the component is activated by its parent. For example, a tab
	 * panel is enable by a select item call from its parent JTabPanel
	 * 
	 * <p>
	 * 
	 * @return
	 */
	public boolean isActivatedByParent() {

		if (component instanceof Component) {
			Container parent = ((Component) component).getParent();
			if (parent instanceof JTabbedPane) {
				return true;
			}

		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.model.GComponent#getX()
	 */
	@Override
	public int getX() {
		Component pointer = component;

		if (pointer == null || pointer instanceof Window)
			return 0;

		// Component pointerParent = component.getParent();

		int x = 0;

		while (!(pointer instanceof Window)) {
			x += pointer.getX();
			pointer = pointer.getParent();
			if (pointer == null)
				break;
		}

		return x;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.umd.cs.guitar.model.GComponent#getY()
	 */
	@Override
	public int getY() {
		Component pointer = component;

		if (pointer == null || pointer instanceof Window)
			return 0;

		// Component pointerParent = component.getParent();

		int y = 0;

		while (!(pointer instanceof Window)) {
			y += pointer.getY();
			pointer = pointer.getParent();
			if (pointer == null)
				break;
		}

		return y;
	}

	// // ---------------------------------------
	// // Capture images
	// private void captureImage() {
	// // Toolkit.getDefaultToolkit().get
	// Robot robot;
	//
	// try {
	// robot = new Robot();
	// Component comp = (Component) this.component;
	//
	// Point pos = comp.getLocationOnScreen();
	// Dimension dim = comp.getSize();
	// Rectangle bounder = new Rectangle(pos, dim);
	//
	// BufferedImage screenshot = robot.createScreenCapture(bounder);
	// File outputfile = new File("images/" + getID() + ".png");
	// ImageIO.write(screenshot, "png", outputfile);
	//
	// } catch (IOException e) {
	//
	// } catch (AWTException e) {
	// // TODO Auto-generated catch block
	// GUITARLog.log.error(e);
	// } catch (Exception e) {
	// GUITARLog.log.error(e);
	// }
	//
	// }
}
