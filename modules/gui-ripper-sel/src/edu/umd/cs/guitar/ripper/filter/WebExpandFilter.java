package edu.umd.cs.guitar.ripper.filter;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebElement;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.GUITARConstants;
import edu.umd.cs.guitar.model.GWindow;
import edu.umd.cs.guitar.model.WebComponent;
import edu.umd.cs.guitar.model.data.ComponentType;
import edu.umd.cs.guitar.model.data.ContainerType;
import edu.umd.cs.guitar.model.data.FullComponentType;
import edu.umd.cs.guitar.model.data.GUIType;
import edu.umd.cs.guitar.model.data.ObjectFactory;
import edu.umd.cs.guitar.model.wrapper.ComponentTypeWrapper;
import edu.umd.cs.guitar.ripper.GRipperMonitor;
import edu.umd.cs.guitar.ripper.WebRipperMonitor;
import edu.umd.cs.guitar.util.GUITARLog;


public class WebExpandFilter extends GComponentFilter {
	//local variable ripper has Ripper to ripWindow, etc.
	
	@Override
	public boolean isProcess(GComponent component, GWindow window) {
		//Use this filter for only the body element
		if(!(component instanceof WebComponent))
			return false;
		
		WebElement e = ((WebComponent) component).getElement();
		
		if(e.getTagName().equals("body"))
			return true;
		
		return false;
	}

	static ObjectFactory factory = new ObjectFactory();
	
	@Override
	public ComponentType ripComponent(GComponent component, GWindow window) {

		GUITARLog.log.info("");
		GUITARLog.log.info("----------------------------------");
		GUITARLog.log.info("Ripping component using specialized filter: ");

		System.out.println("Ripping a component " + component.getTitle());
		WebRipperMonitor monitor = (WebRipperMonitor) ripper.getMonitor();
		
		// GUITARLog.log.debug("No filter applied");

		// 2. Rip regular components
		ComponentType retComp = null;
		try {
			// 2.1 Try to perform action on the component
			// to reveal more windows/components

			// clear window opened cache before performing actions
			monitor.resetWindowCache();

			if (monitor.isExpandable(component, window))
				monitor.expandGUI(component);
			else {
				GUITARLog.log.info("Component is Unexpandable");
			}

			// Extract properties from the current component
			retComp = component.extractProperties();
			ComponentTypeWrapper compA = new ComponentTypeWrapper(retComp);

			retComp = compA.getDComponentType();

			// Trigger terminal widget

			LinkedList<GWindow> lClosedWindows = monitor.getClosedWindowCache();

			String sWinID = window.getTitle();

			if (lClosedWindows.size() > 0) {
				// if (monitor.isWindowClosed()) {

				// LinkedList<GWindow> lClosedWindows = monitor
				// .getClosedWindowCache();

				GUITARLog.log.debug("!!!!! Window closed");

				for (GWindow closedWin : lClosedWindows) {
					String sClosedWinTitle = closedWin.getTitle();

					// Only consider widget closing the current window
					if (sWinID.equals(sClosedWinTitle)) {

						GUITARLog.log.debug("\t" + sClosedWinTitle);
						List<FullComponentType> lCloseComp = ripper.getlCloseWindowComp().getFullComponent();
						FullComponentType cCloseComp = factory.createFullComponentType();
						cCloseComp.setWindow(closedWin.extractWindow()
								.getWindow());
						cCloseComp.setComponent(retComp);
						lCloseComp.add(cCloseComp);
						ripper.getlCloseWindowComp().setFullComponent(lCloseComp);
					}

				}

			}

			if (monitor.isNewWindowOpened()) {
				
				List<FullComponentType> lOpenComp = ripper.getlOpenWindowComps()
						.getFullComponent();
				FullComponentType cOpenComp = factory.createFullComponentType();
				cOpenComp.setWindow(window.extractWindow().getWindow());
				cOpenComp.setComponent(retComp);
				lOpenComp.add(cOpenComp);
				ripper.getlOpenWindowComps().setFullComponent(lOpenComp);

				LinkedList<GWindow> lNewWindows = monitor
						.getOpenedWindowCache();
				
				monitor.resetWindowCache();
				
				GUITARLog.log.info(lNewWindows.size()
						+ " new window(s) opened!!!");
				for (GWindow newWins : lNewWindows) {
					GUITARLog.log
							.info("*\t Title:*" + newWins.getTitle() + "*");
				}

				// Process the opened windows in a FIFO order
				while (!lNewWindows.isEmpty()) {

					GWindow gNewWin = lNewWindows.pollLast();
					GComponent gWinComp = gNewWin.getContainer();

					if (gWinComp != null) {
						// Add invokelist property for the component
						String sWindowTitle = gNewWin.getTitle();
						compA = new ComponentTypeWrapper(retComp);
						compA.addValueByName(
								GUITARConstants.INVOKELIST_TAG_NAME,
								sWindowTitle);

						GUITARLog.log.debug(sWindowTitle + " recorded");

						retComp = compA.getDComponentType();

						// Check ignore window
						if (!monitor.isIgnoredWindow(gNewWin)) {

							if (!monitor.isRippedWindow(gNewWin)) {
								// if
								// (!this.lRippedWindowTitles.contains(gNewWin
								// .getTitle())) {
								gNewWin.setRoot(false);
								// lRippedWindow.add(gNewWin);
								// lRippedWindowTitles.add(gNewWin.getTitle());
								monitor.addRippedList(gNewWin);

								GUIType dWindow = ripper.ripWindow(gNewWin);
								
								if (dWindow != null)
									ripper.getResult().getGUI().add(dWindow);
							} else {
								GUITARLog.log.info("Window is ripped!!!");
							}

						} else {
							GUITARLog.log.info("Window is ignored!!!");
						}
					}
					monitor.closeWindow(gNewWin);

					// // Check if there is a new window open
					// lNewWindows = monitor.getOpenedWindowCache();
					// monitor.resetWindowCache();
				}
			}

			// TODO: check if the component is still available after ripping
			// its child window
			List<GComponent> gChildrenList = component.getChildren();
			int nChildren = gChildrenList.size();

			// Debug

			String lChildren = "[";
			for (int j = 0; j < nChildren; j++) {
				lChildren += gChildrenList.get(j).getTitle() + " - "
						+ gChildrenList.get(j).getClassVal() + "; ";
			}
			lChildren += "]";
			GUITARLog.log.debug("*" + component.getTitle() + "* in window *"
					+ window.getTitle() + "* has " + nChildren + " children: "
					+ lChildren);

			// End debug

			for ( GComponent gChild : gChildrenList ) {
				ComponentType guiChild = ripComponent(gChild, window);
				
				if (guiChild != null)
					((ContainerType) retComp).getContents()
							.getWidgetOrContainer().add(guiChild);
			}
			
		} catch (Exception e) {
			// logOld.println("ripComponent exception");
			// e.printStackTrace();
			GUITARLog.log.error("ripComponent exception", e);
			System.exit(-1);
			return null;
		}
		return retComp;
	}
}
