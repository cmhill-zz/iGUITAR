package edu.umd.cs.guitar.guitestrunner;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.text.JTextComponent;

import org.apache.log4j.Logger;
import org.netbeans.jemmy.ComponentChooser;


/**
 * Find a component in a container.
 * 
 * @author Scott McMaster
 * 
 */
public class ComponentFinder
{
	private static final int DEFAULT_WAIT = 1000;

	private static final Logger LOG = Logger.getLogger(ComponentFinder.class);
	
	private Container rootContainer;
	
	/**
	 * Indexable list of child components of the root container.
	 */
	private List<Component> components;
	
	private Object firstMenuBar;
	private JMenuBar secondMenuBar;

	public ComponentFinder(Container rootContainer)
	{
		this.rootContainer = rootContainer;
		buildWindowComponentList();
	}

	/**
	 * Find the component with the given spec.
	 * @param chooser
	 * @return
	 */
	public Component findComponent(GUITARComponentChooser chooser)
	{
		System.out.println("Finding component " + chooser.getDescription());
		
		// As near as I can tell, this is the way that the old replayer works:
		//	1. See if the component at the specified index doesn't NOT match.
		//		If it's the right class and doesn't have any non-matching properties, use it.
		//	2. If it doesn't, look for it at other locations in the GUI tree.
		
		//  1.
		int index = chooser.getStep().getComponentId().getIndex();
		if( index >= 0 && index < components.size() )
		{
			Component componentAtIndex = components.get(index);
			if( chooser.matchComponent(componentAtIndex) )
			{
				System.out.println("Found component " + chooser.getDescription() + " at index " + index);
				return componentAtIndex;
			}
		}
		
		//  2.
		return findComponentByProperties(rootContainer, chooser);
	}

	private Component findComponentByProperties(Container cont,
			ComponentChooser chooser) {
		Component[] components;

		if (cont instanceof JMenu) {
			components = ((JMenu) cont).getMenuComponents();
		} else {
			components = cont.getComponents();
		}

		for (Component component : components) {
			if (component != null) {
				if (chooser.checkComponent(component)) {
					return component;
				}

				if (component instanceof Container) {
					Component target;
					if ((target = findComponentByProperties((Container) component,
							chooser)) != null) {
						return target;
					}
				}
			}
		}

		return null;
	}

	/**
	 * Get an indexed list of the components underneath the given window.
	 * 
	 * @param rootContainer
	 */
	private void buildWindowComponentList()
	{
		components = new ArrayList<Component>();

		JRootPane jrp = null;
		Container cont = null;
		Component[] comp = null;
		Frame prntframe = null;
		Dialog prntDlg = null;

		if (rootContainer instanceof javax.swing.JDialog
				|| rootContainer instanceof java.awt.Dialog) {
			prntDlg = (Dialog) rootContainer;
		} else {
			if (rootContainer instanceof javax.swing.JFrame
					|| rootContainer instanceof java.awt.Frame)
				prntframe = (Frame) rootContainer;
		}
		if (prntDlg != null) {
			if (rootContainer instanceof javax.swing.JDialog) {
				firstMenuBar = ((javax.swing.JDialog) prntDlg).getJMenuBar();
				jrp = ((javax.swing.JDialog) prntDlg).getRootPane();
			}
		} else {
			if (rootContainer instanceof javax.swing.JFrame) {
				firstMenuBar = ((JFrame) prntframe).getJMenuBar();
				jrp = ((JFrame) prntframe).getRootPane();
			}
		}
		if (jrp != null) {
			jrp.revalidate();
			cont = jrp.getContentPane();
		}
		if (cont != null) {
			comp = cont.getComponents();
		}
		if (rootContainer instanceof java.awt.Frame) {
			comp = ((java.awt.Frame) rootContainer).getComponents();
		}
		if (rootContainer instanceof java.awt.Dialog) {
			comp = ((java.awt.Dialog) rootContainer).getComponents();
		}
		if (comp != null) {
			for (int j = 0; j < comp.length; j++) {
				int componentCount = 0;
				if (comp[j] instanceof JMenu) {
					componentCount = ((JMenu) comp[j])
							.getMenuComponentCount();
				} else {
					componentCount = ((Container) comp[j])
							.getComponentCount();
				}
				if (shouldTraverseContainer(comp[j])) {
					if (componentCount > 0) {
						if (!(comp[j] instanceof javax.swing.JPopupMenu)) {
							if (isReplayable(comp[j])) {
								components.add(comp[j]);
							}
						}
						components.addAll(addContainerComponents(comp[j]));
					} else {
						if (isReplayable(comp[j])) {
							components.add(comp[j]);
						}
					}
				}
			}
		}
	}

	/**
	 * Recursively add the components from the given container, only
	 * including the replayable ones.
	 * @param contcomp
	 * @return
	 */
	private List<Component> addContainerComponents(Component contcomp)
	{
		List<Component> componentList = new ArrayList<Component>();
		if (contcomp instanceof JMenuBar && firstMenuBar == null)
			firstMenuBar = (JMenuBar) contcomp;

		if (contcomp instanceof JMenuBar && secondMenuBar == null)
			secondMenuBar = (JMenuBar) contcomp;

		Container tcont = ((Container) contcomp);
		Component[] components;
		if (contcomp instanceof JMenu) {
			components = ((JMenu) tcont).getMenuComponents();
		} else {
			components = ((Container) tcont).getComponents();
		}

		for (int i = 0; i < components.length; i++) {
			if (components[i] != null) {
				if ((components[i] instanceof Container)) {
					int componentCount = 0;
					if (components[i] instanceof JMenu) {
						componentCount = ((JMenu) components[i])
								.getMenuComponentCount();
					} else {
						componentCount = ((Container) components[i])
								.getComponentCount();
					}

					if (shouldTraverseContainer(components[i])) {
						if (componentCount > 0) {
							if (!(components[i] instanceof javax.swing.JPopupMenu)) {
								if (isReplayable(components[i])) {
									componentList
											.add(components[i]);
								}
							}
							componentList.addAll(addContainerComponents(
									components[i]));
						} else {
							if (isReplayable(components[i])) {
								componentList.add(components[i]);
							}
						}
					}
				}
			}
		}
		
		return componentList;
	}

	/**
	 * Return whether or not we include components below this one.
	 * 
	 * @param component
	 * @return
	 */
	private boolean shouldTraverseContainer(Component component) {
		if ("javax.swing.CellRendererPane".equals(component.getClass()
				.getName())) {
			return false;
		}
		return true;
	}

	private boolean isReplayable(Component comp) {
		if (comp instanceof AbstractButton) {
			return true;
		}
		if (comp instanceof JTextComponent) {
			return true;
		}
		if (comp instanceof JComboBox) {
			return true;
		}
		if (comp instanceof JColorChooser) {
			return true;
		}
		if (comp instanceof JFileChooser) {
			return true;
		}
		if (comp instanceof JTable) {
			return true;
		}
		if (comp instanceof JTree) {
			return true;
		}

		// application self-defined function getReplayableMethod will return
		// back all replayable function names as a vector
		try {
			Class<?> componentClass = comp.getClass();
			Method replayMethod = componentClass.getMethod("getReplayableMethod", (Class<?>) null);
			if (replayMethod != null) {
				return true;
			} else {
				return false;
			}
		} catch (NoSuchMethodException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Component waitComponent(GUITARComponentChooser componentChooser,
			int tries) {
		for( int i = 0; i < tries; ++i )
		{
			Component result = findComponent(componentChooser);
			if( result != null )
			{
				return result;
			}
			Utils.sleep(DEFAULT_WAIT);
		}
		
		return null;
	}

}
