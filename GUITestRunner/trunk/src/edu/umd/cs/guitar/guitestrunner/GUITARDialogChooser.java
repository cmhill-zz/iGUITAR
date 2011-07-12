package edu.umd.cs.guitar.guitestrunner;

import java.awt.Component;

import javax.swing.JDialog;

import org.netbeans.jemmy.ComponentChooser;

public class GUITARDialogChooser implements ComponentChooser {

	private WindowName windowName;
	
	public GUITARDialogChooser(WindowName windowName) {
		this.windowName = windowName;
	}

	//JDK6 @Override
	public boolean checkComponent(Component comp) {
		JDialog dlg = (JDialog) comp;
		
		if( comp.getName().equals(windowName.getName()) )
		{
			return true;
		}
		
		if( windowName.getName() == null || windowName.getName().length() == 0 ||
				"javax.swing.JDialog".equals(windowName.getName()) )
		{
			// GUITAR has no name recorded for the component.
			if( dlg.getTitle() == null || dlg.getTitle().trim().length() == 0 )
			{
				return true;
			}
		}
		
		if( windowName.getName().equals(dlg.getTitle()) )
		{
			return true;
		}
		
		return false;
	}

	//JDK6 @Override
	public String getDescription() {
		return "Looking for dialog " + windowName.getName();
	}

}
