package edu.umd.cs.guitar.event;

import java.util.Hashtable;
import java.util.List;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.model.IphCommServer;
import edu.umd.cs.guitar.model.IphComponent;
import edu.umd.cs.guitar.model.data.PropertyType;

public class IphTouchEvent implements GEvent{

	@Override
	public void perform(GComponent gComponent, List<String> parameters,
			Hashtable<String, List<String>> optionalData) {
		System.out.println("Send TOUCH message w/ parameters to iphone client.");
		if (!(gComponent instanceof IphComponent)) {
			System.out.println("Error: expected IphComponent but received: " + gComponent.getClass());
			return;
		}
		
		IphComponent comp = (IphComponent) gComponent;
		
		// Get address and UIView name.
		IphCommServer.requestAndHear("INVOKE TOUCH " + comp.getClassVal() + " " + comp.getAddress());
	}

	@Override
	public void perform(GComponent gComponent,
			Hashtable<String, List<String>> optionalData) {
		System.out.println("Send TOUCH message to iphone client.");
		if (!(gComponent instanceof IphComponent)) {
			System.out.println("Error: expected IphComponent but received: " + gComponent.getClass());
			return;
		}
		
		IphComponent comp = (IphComponent) gComponent;
		
		// Get address and UIView name.
		IphCommServer.requestAndHear("INVOKE TOUCH " + comp.getClassVal() + " " + comp.getAddress());
	}

	@Override
	public boolean isSupportedBy(GComponent gComponent) {
		if (!(gComponent instanceof IphComponent)) {
			return false;
		}
		
		IphComponent comp = (IphComponent) gComponent;
		
		// Go through the properties and find if one contains
		// INVOKE, TOUCH.
		for (PropertyType property : comp.getGUIProperties()) {
			if (property.getName().equals("INVOKE") &&
					property.getValue().get(0).equals("TOUCH")) {
				System.out.println("IphComponent : " + comp.getClassVal()
						+ ", supports TOUCH");
				return true;
			}
		}
		return false;
	}

}
