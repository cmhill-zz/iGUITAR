package edu.umd.cs.guitar.guitestrunner;

import java.awt.Component;

import javax.swing.JFrame;

import org.netbeans.jemmy.ComponentChooser;

public class GUITARWindowChooser implements ComponentChooser {

	private WindowName windowName;
	
	public GUITARWindowChooser(WindowName windowName) {
		this.windowName = windowName;
	}

	//JDK6 @Override
	public boolean checkComponent(Component comp) {
		JFrame frame = (JFrame) comp;
		
		if( comp.getName().equals(windowName.getName()) )
		{
			return true;
		}
		
		if( windowName.getName().equals(frame.getTitle()) )
		{
			return true;
		}
		
		if( frame.getClass().getName().equals(windowName.getName()) )
		{
			return true;
		}
		
		return false;
	}

	//JDK6 @Override
	public String getDescription() {
		return "Looking for frame/window " + windowName.getName();
	}

}
